package com.kafu.kafu.user.Admin;

import lombok.Data;

@Data
public class AdminStatisticsDTO {
    private Long totalProblems;
    private Long totalCompletedProblems;
    private Long totalGovs;
    private Long totalUsers;

}
