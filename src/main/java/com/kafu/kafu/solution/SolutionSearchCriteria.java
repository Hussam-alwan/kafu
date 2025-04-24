package com.kafu.kafu.solution;

public class SolutionSearchCriteria {
    private String description;
    private Long problemId;
    private Long proposedByUserId;
    private Long acceptedByUserId;

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getProblemId() {
        return problemId;
    }
    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }
    public Long getProposedByUserId() {
        return proposedByUserId;
    }
    public void setProposedByUserId(Long proposedByUserId) {
        this.proposedByUserId = proposedByUserId;
    }
    public Long getAcceptedByUserId() {
        return acceptedByUserId;
    }
    public void setAcceptedByUserId(Long acceptedByUserId) {
        this.acceptedByUserId = acceptedByUserId;
    }
}
