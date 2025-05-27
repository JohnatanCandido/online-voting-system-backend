package com.aernaur.votingSystem.service;

import com.aernaur.votingSystem.dto.SearchPeopleDTO;
import com.aernaur.votingSystem.entity.Login;
import com.aernaur.votingSystem.entity.Person;
import com.aernaur.votingSystem.dto.PersonDTO;
import com.aernaur.votingSystem.exceptions.EntityNotFoundException;
import com.aernaur.votingSystem.exceptions.ProfilePicUploadException;
import com.aernaur.votingSystem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PersonService {

    @Value("${aws.bucket.name}")
    private String awsBucketName;

    private final PersonRepository personRepository;
    private final S3Client s3Client;

    public PersonService(PersonRepository personRepository, S3Client s3Client) {
        this.personRepository = personRepository;
        this.s3Client = s3Client;
    }

    public List<PersonDTO> searchPeople(SearchPeopleDTO filters) throws EntityNotFoundException {
        List<PersonDTO> people = new ArrayList<>();
        for (Person person: personRepository.findAll()) {
            var personDTO = new PersonDTO(person);
            personDTO.setProfilePicUrl(getProfilePicUrl(person));
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
            deleteProfilePic(person);
        }
        person.setProfilePicS3Name(uploadProfilePic(personId, multipartFile));
        person = personRepository.save(person);
        return getProfilePicUrl(person);
    }

    private String uploadProfilePic(UUID personId, MultipartFile multipartFile) throws ProfilePicUploadException {
        try {
            File file = convertMultipartToFile(personId, multipartFile);
            s3Client.putObject(PutObjectRequest.builder()
                                               .bucket(awsBucketName)
                                               .key(file.getName())
                                               .build(),
                               RequestBody.fromFile(file));
            Files.delete(file.toPath());
            return file.getName();
        } catch (Exception e) {
            throw new ProfilePicUploadException();
        }
    }

    private File convertMultipartToFile(UUID personId, MultipartFile multipartFile) throws IOException {
        File convFile = new File(personId.toString() + "-" + multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }

    private String getProfilePicUrl(Person person) {
        return s3Client.utilities()
                       .getUrl(GetUrlRequest.builder().bucket(awsBucketName).key(person.getProfilePicS3Name()).build())
                       .toString();
    }

    public void deletePerson(UUID personId) throws EntityNotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(() -> new EntityNotFoundException("person", personId));
        deleteProfilePic(person);
        personRepository.delete(person);
    }

    private void deleteProfilePic(Person person) {
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(awsBucketName).key(person.getProfilePicS3Name()).build());
    }
}
