package com.kafu.kafu.gov.DTO;

import lombok.Data;

@Data
public class GovChartDTO {
    private String month;
    private int receivedProblems;   // all problems submitted to the gov
    private int completedProblems;  // all problmes with status completed
    private int auctionedProblmes;  // all problmes with status auctioned

}
