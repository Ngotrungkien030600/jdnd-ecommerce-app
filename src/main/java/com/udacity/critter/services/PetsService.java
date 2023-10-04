package com.udacity.critter.services;

import com.udacity.critter.entities.Customer;
import com.udacity.critter.entities.Pet;
import com.udacity.critter.repositories.CustomersRepository;
import com.udacity.critter.repositories.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetsService {

    private final PetsRepository petsRepository;
    private final CustomersRepository customersRepository;

    @Autowired
    public PetsService(PetsRepository petsRepository, CustomersRepository customersRepository) {
        this.petsRepository = petsRepository;
        this.customersRepository = customersRepository;
    }

    public List<Pet> getAllPets() {
        return petsRepository.findAll();
    }

    public List<Pet> getPetsByCustomerId(long customerId) {
        return petsRepository.findAllByCustomer_Id(customerId);
    }

    public Pet getPetById(long petId) {
        return petsRepository.findById(petId).orElse(null);
    }

    public Pet savePet(Pet pet, long ownerId) {
        Customer customer = customersRepository.findById(ownerId).orElse(null);
        if (customer != null) {
            pet.setCustomer(customer);
            pet = petsRepository.save(pet);
            customer.insertPet(pet);
            customersRepository.save(customer);
        }
        return pet;
    }

    public List<Pet> getPetsByIds(List<Long> petIds) {
        return null;
    }
}
