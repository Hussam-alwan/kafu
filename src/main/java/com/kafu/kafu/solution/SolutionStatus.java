package com.kafu.kafu.solution;

public enum SolutionStatus {
    PENDING_APPROVAL,//newly created
    APPROVED, //patch
    REJECTED, //patch
    PENDING_FUNDING, //patch
    WORK_IN_PROGRESS, //patch by gov
    RESOLVED //percentage is 100
}