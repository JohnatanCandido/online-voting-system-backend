package com.aernaur.votingSystem.service;

import com.aernaur.votingSystem.dto.PersonDTO;
import com.aernaur.votingSystem.dto.SearchPeopleDTO;
import com.aernaur.votingSystem.entity.Login;
import com.aernaur.votingSystem.entity.Person;
import com.aernaur.votingSystem.exceptions.EntityNotFoundException;
import com.aernaur.votingSystem.exceptions.ProfilePicUploadException;
import com.aernaur.votingSystem.repository.PersonRepository;
import com.aernaur.votingSystem.repository.specifictaions.PersonSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonProfilePicService personProfilePicService;
    private final PersonRepository personRepository;

    public PersonService(PersonProfilePicService personProfilePicService,
                         PersonRepository personRepository) {
        this.personProfilePicService = personProfilePicService;
        this.personRepository = personRepository;
    }

    public List<PersonDTO> searchPeople(SearchPeopleDTO filters) {
        Specification<Person> spec = Specification.where(PersonSpecification.withId(filters.personId()))
                                                  .and(PersonSpecification.nameLike(filters.name()));


        List<PersonDTO> people = new ArrayList<>();
        for (Person person: personRepository.findAll(spec)) {
            var personDTO = new PersonDTO(person);
            personDTO.setProfilePicUrl(personProfilePicService.getProfilePicUrl(person));
            people.add(personDTO);
        }
        return people;
    }

    public UUID savePerson(PersonDTO personDTO) {
        Person person;
        if (personDTO.getPersonId() == null) {
            person = new Person();
            person.setLogin(createLogin(person, personDTO.getPersonName()));
        } else {
            person = personRepository.findById(personDTO.getPersonId()).orElseThrow();
        }
        person.setName(personDTO.getPersonName());
        person.setBirthDate(personDTO.getBirthDate());
        person.getLogin().setAdmin(personDTO.isAdmin());
        person = personRepository.save(person);

        return person.getId();
    }

    private Login createLogin(Person person, String name) {
        var login = new Login(person);
        String[] splitName = name.split(" ");
        login.setUsername((splitName[0] + "." + splitName[splitName.length-1]).toLowerCase());
        login.setPassword("12345");
        return login;
    }

    public String saveProfilePic(UUID personId, MultipartFile multipartFile) throws ProfilePicUploadException {
        Person person = personRepository.findById(personId).orElseThrow();
        if (person.getProfilePicS3Name() != null) {
            personProfilePicService.deleteProfilePic(person);
        }
        person.setProfilePicS3Name(personProfilePicService.uploadProfilePic(personId, multipartFile));
        person = personRepository.save(person);
        return personProfilePicService.getProfilePicUrl(person);
    }

    public void deletePerson(UUID personId) throws EntityNotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(() -> new EntityNotFoundException("person", personId));
        personProfilePicService.deleteProfilePic(person);
        personRepository.delete(person);
    }
}
