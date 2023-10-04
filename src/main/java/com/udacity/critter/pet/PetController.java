package com.udacity.critter.pet;

import com.udacity.critter.entities.Pet;
import com.udacity.critter.pet.PetDTO;
import com.udacity.critter.services.PetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetsService petsService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToEntity(petDTO);
        Pet savedPet = petsService.savePet(pet, petDTO.getOwnerId());
        return convertPetToDTO(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petsService.getPetById(petId);
        return convertPetToDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getAllPets() {
        List<Pet> pets = petsService.getAllPets();
        return pets.stream().map(this::convertPetToDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petsService.getPetsByCustomerId(ownerId);
        return pets.stream().map(this::convertPetToDTO).collect(Collectors.toList());
    }

    private PetDTO convertPetToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setType(pet.getType());
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());
        return petDTO;
    }

    private Pet convertPetDTOToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        return pet;
    }
}
