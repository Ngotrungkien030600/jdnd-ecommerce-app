package com.udacity.critter.services;

import com.udacity.critter.entities.Customer;
import com.udacity.critter.entities.Pet;
import com.udacity.critter.repositories.CustomersRepository;
import com.udacity.critter.repositories.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomersService {

    private final CustomersRepository customersRepository;
    private final PetsRepository petsRepository;

    @Autowired
    public CustomersService(CustomersRepository customersRepository, PetsRepository petsRepository) {
        this.customersRepository = customersRepository;
        this.petsRepository = petsRepository;
    }

    public List<Customer> getAllCustomers() {
        return customersRepository.findAll();
    }

    public Customer getCustomerByPetId(long petId) {
        Optional<Pet> petOptional = petsRepository.findById(petId);
        if (petOptional.isPresent()) {
            return petOptional.get().getCustomer();
        } else {
            throw new EntityNotFoundException("Pet with ID " + petId + " not found.");
        }
    }

    public Customer saveCustomer(Customer customer, List<Long> petIds) {
        List<Pet> pets = petIds.stream()
                .map(petId -> petsRepository.findById(petId)
                        .orElseThrow(() -> new EntityNotFoundException("Pet with ID " + petId + " not found.")))
                .collect(Collectors.toList());

        customer.setPets(pets);
        return customersRepository.save(customer);
    }

    public Customer getCustomerById(long customerId) {
        return null;
    }
}
