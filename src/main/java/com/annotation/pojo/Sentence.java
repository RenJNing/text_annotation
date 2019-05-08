package com.annotation.pojo;

public class Sentence {
    private Integer sentenceId;

    private String content;

    private Integer startIndex;

    private Integer labeled;

    public Integer getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(Integer sentenceId) {
        this.sentenceId = sentenceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getLabeled() {
        return labeled;
    }

    public void setLabeled(Integer labeled) {
        this.labeled = labeled;
    }
}