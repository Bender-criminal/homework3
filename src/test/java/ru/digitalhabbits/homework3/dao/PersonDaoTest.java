package ru.digitalhabbits.homework3.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabbits.homework3.domain.Person;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PersonDaoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonDao personDao;


    @Test
    void findById() {
        mockData();
        Person person = personDao.findById(1).get();
        assertEquals("ПупкинВасилийИванович", person.getLastName() + person.getFirstName() + person.getMiddleName());
    }

    @Test
    void findAll() {
        mockData();
        List<Person> allPersons = personDao.findAll();
        assertEquals(4, allPersons.size());
        assertThat(allPersons).extracting(Person::getLastName).contains("Пупкин", "Хренова", "Шариков", "Петров");
    }

    @Test
    void update() {
        mockData();
        Person person = personDao.findById(1).get();
        person.setLastName("Пузиков").setFirstName("Фёдор").setMiddleName("Филиппович");
        personDao.update(person);
        person = personDao.findById(1).get();
        assertEquals("ПузиковФёдорФилиппович", person.getLastName() + person.getFirstName() + person.getMiddleName());
    }

    @Test
    void delete() {
        mockData();
        personDao.delete(3);
        assertEquals( false, personDao.findById(3).isPresent());
    }

    private void mockData(){

        entityManager.persist(new Person()
                .setId(1)
                .setAge(30)
                .setFirstName("Василий")
                .setMiddleName("Иванович")
                .setLastName("Пупкин"));

        entityManager.persist(new Person()
                .setId(2)
                .setAge(40)
                .setFirstName("Гадя")
                .setMiddleName("Петрович")
                .setLastName("Хренова"));

        entityManager.persist(new Person()
                .setId(3)
                .setAge(35)
                .setFirstName("Полиграф")
                .setMiddleName("Полиграфович")
                .setLastName("Шариков"));

        entityManager.persist(new Person()
                .setId(4)
                .setAge(25)
                .setFirstName("Пётр")
                .setMiddleName("Петрович")
                .setLastName("Петров"));

    }
}