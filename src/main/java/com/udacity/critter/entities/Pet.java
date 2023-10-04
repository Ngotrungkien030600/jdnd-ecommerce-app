package com.udacity.critter.entities;

import com.udacity.critter.pet.PetType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class Pet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private PetType type;
    private String name;

    @ManyToOne(optional = false)
    private Customer customer;

    private LocalDate birthDate;
    private String notes;
}
