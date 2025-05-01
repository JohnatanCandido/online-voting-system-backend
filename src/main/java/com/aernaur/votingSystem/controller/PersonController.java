package com.aernaur.votingSystem.controller;

import com.aernaur.votingSystem.dto.SearchPeopleDTO;
import com.aernaur.votingSystem.dto.PersonDTO;
import com.aernaur.votingSystem.exceptions.EntityNotFoundException;
import com.aernaur.votingSystem.exceptions.ProfilePicUploadException;
import com.aernaur.votingSystem.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<PersonDTO> searchPeople(@ModelAttribute SearchPeopleDTO filters) throws EntityNotFoundException {
        return personService.searchPeople(filters);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> savePerson(@RequestBody @Valid PersonDTO personDTO) {
        return ResponseEntity.ok(Map.of("id", personService.savePerson(personDTO)));
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<String> deletePerson(@PathVariable("personId") UUID personId) throws EntityNotFoundException {
        personService.deletePerson(personId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{personId}/profile-pic")
    public ResponseEntity<Map<String, Object>> saveProfilePic(@PathVariable("personId") UUID personId, @RequestParam MultipartFile file) throws ProfilePicUploadException {
        String url = personService.saveProfilePic(personId, file);
        return ResponseEntity.ok(Map.of("url", url));
    }
}
