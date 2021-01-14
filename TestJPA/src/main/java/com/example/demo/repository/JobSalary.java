package com.example.demo.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobSalary {
    private String job;
    private double averagesalary;
}