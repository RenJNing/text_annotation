package com.annotation.shiro;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class MyAuthorizationFilter extends RolesAuthorizationFilter {
	@Override
	@SuppressWarnings({ "unchecked" })
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws IOException {

		Subject subject = getSubject(request, response);
		String[] rolesArray = (String[]) mappedValue;

		if (rolesArray == null || rolesArray.length == 0) {
			// no roles specified, so nothing to check - allow access.
			return true;
		}

		for (int i = 0; i < rolesArray.length; i++) {
			// 若当前用户是rolesArray中的任何一个，则有权限访问
			if (subject.hasRole(rolesArray[i])) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		Subject subject = getSubject(request, response);
		// If the subject isn't identified, redirect to login URL
		if (subject.getPrincipal() == null) {
			saveRequestAndRedirectToLogin(request, response);
		} else {
			// If subject is known but not authorized, redirect to the unauthorized URL if
			// there is one
			// If no unauthorized URL is specified, just return an unauthorized HTTP status
			// code
			String unauthorizedUrl = getUnauthorizedUrl();
			// SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen
			// due to response commit:
			if (StringUtils.hasText(unauthorizedUrl)) {
				WebUtils.issueRedirect(request, response, unauthorizedUrl);
			} else {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=utf-8");
				PrintWriter out = null;
				JSONObject res = new JSONObject();
				try {
					res.put("msg", "当前用户无访问权限！");
					res.put("errCode", -403);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				out = response.getWriter();
				out.append(res.toString());
				if (out != null) {
					out.close();
				}
			}
		}
		return false;
	}
}
