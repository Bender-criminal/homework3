package ru.digitalhabbits.homework3.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.digitalhabbits.homework3.dao.PersonDao;
import ru.digitalhabbits.homework3.domain.Person;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PersonServiceImpl.class)
class PersonServiceTest {

    @MockBean
    private PersonDao personDao;

    @Autowired
    private PersonService personService;

    @Test
    void findAllPersons() {
        // TODO: NotImplemented
    }

    @Test
    void getPerson() {
        // TODO: NotImplemented
    }

    @Test
    void createPerson() {
        // TODO: NotImplemented
    }

    @Test
    void updatePerson() {
        // TODO: NotImplemented
    }

    @Test
    void deletePerson() {
        // TODO: NotImplemented
    }
}