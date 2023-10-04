package com.udacity.critter.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    private String notes;

    @OneToMany
    private List<Pet> pets;

    public void insertPet(Pet pet) {
    }
}
