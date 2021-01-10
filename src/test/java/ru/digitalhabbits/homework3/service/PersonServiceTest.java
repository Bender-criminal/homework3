package ru.digitalhabbits.homework3.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.digitalhabbits.homework3.dao.DepartmentDaoImpl;
import ru.digitalhabbits.homework3.dao.PersonDaoImpl;
import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.DepartmentRequest;
import ru.digitalhabbits.homework3.model.PersonRequest;
import ru.digitalhabbits.homework3.model.PersonResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PersonServiceImpl.class)
class PersonServiceTest {

    @MockBean
    private PersonDaoImpl personDao;
    @MockBean
    private DepartmentDaoImpl departmentDao;

    @Autowired
    private PersonService personService;

    @Test
    void findAllPersons() {
        // TODO: NotImplemented
        Mockito.when(personDao.findAll()).thenReturn(mockPersons());
        List<PersonResponse> persons = personService.findAllPersons();
        assertEquals(4, persons.size());
        assertThat(persons).extracting(PersonResponse::getFullName).contains("Василий Иванович Пупкин", "Полиграф Полиграфович Шариков", "Пётр Петрович Петров");
    }

    @Test
    void getPerson() {

        Department department = new Department().setId(1).setName("IT").setClosed(false);
        Mockito.when(personDao.findById(1)).thenReturn(Optional.of(new Person().setFirstName("Василий").setMiddleName("Иванович").setLastName("Пупкин").setDepartment(department)));
        Mockito.when(departmentDao.findById(1)).thenReturn(Optional.of(department));
        PersonResponse response = personService.getPerson(1);
        assertEquals("Пупкин Василий Иванович", response.getFullName());
    }

    @Test
    void createPerson() {
        // TODO: NotImplemented
        Department department = new Department().setId(1).setName("IT").setClosed(false);
        Mockito.when(personDao.create(new Person().setDepartment(department))).thenReturn(100500);
        Mockito.when(departmentDao.findById(1)).thenReturn(Optional.of(department));
        Integer result = personService.createPerson(new PersonRequest());
        assertEquals(100500, result);
    }

    @Test
    void updatePerson() {
        // TODO: NotImplemented
    }

    @Test
    void deletePerson() {
        // TODO: NotImplemented
    }

    private List<Person> mockPersons(){
        return new ArrayList<Person>(Arrays.asList(
                new Person()
                        .setId(1)
                        .setAge(30)
                        .setFirstName("Василий")
                        .setMiddleName("Иванович")
                        .setLastName("Пупкин")
                        .setDepartment(new Department().setId(1).setName("IT").setClosed(false)),
                new Person()
                        .setId(2)
                        .setAge(40)
                        .setFirstName("Гадя")
                        .setMiddleName("Петрович")
                        .setLastName("Хренова")
                        .setDepartment(new Department().setId(2).setName("Buh").setClosed(false)),
                new Person()
                        .setId(3)
                        .setAge(35)
                        .setFirstName("Полиграф")
                        .setMiddleName("Полиграфович")
                        .setLastName("Шариков")
                        .setDepartment(new Department().setId(3).setName("Adm").setClosed(false)),
                new Person()
                        .setId(4)
                        .setAge(25)
                        .setFirstName("Пётр")
                        .setMiddleName("Петрович")
                        .setLastName("Петров")
                        .setDepartment(new Department().setId(1).setName("IT").setClosed(false))
                ));
    }

}