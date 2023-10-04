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

    private final PetsService petsService;

    @Autowired
    public PetController(PetsService petsService) {
        this.petsService = petsService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet petToSave = mapPetDTOToEntity(petDTO);
        Pet savedPet = petsService.savePet(petToSave, petDTO.getOwnerId());
        return mapEntityToPetDTO(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petsService.getPetById(petId);
        return mapEntityToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getAllPets() {
        List<Pet> pets = petsService.getAllPets();
        return pets.stream().map(this::mapEntityToPetDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petsService.getPetsByCustomerId(ownerId);
        return pets.stream().map(this::mapEntityToPetDTO).collect(Collectors.toList());
    }

    private PetDTO mapEntityToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setType(pet.getType());
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());
        return petDTO;
    }

    private Pet mapPetDTOToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        return pet;
    }
}
