package com.udacity.critter.user;

import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO {
    private long id;
    private String name;
    private String phoneNumber;
    private String notes;
    private List<Long> petIds;
}
