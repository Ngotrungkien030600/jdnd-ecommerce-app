package com.udacity.critter.user;

import lombok.Data;

import java.time.DayOfWeek;
import java.util.Set;

@Data
public class EmployeeDTO {
    private long id;
    private String name;
    private Set<EmployeeSkill> skills;
    private Set<DayOfWeek> daysAvailable;
}
