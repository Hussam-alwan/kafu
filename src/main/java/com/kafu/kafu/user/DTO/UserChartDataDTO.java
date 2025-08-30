package com.kafu.kafu.user.DTO;

import lombok.Data;

@Data
public class UserChartDataDTO {
    private String month;
    private int issues;
    private int contributions;
    private int donations; // Changed from double to int
}
