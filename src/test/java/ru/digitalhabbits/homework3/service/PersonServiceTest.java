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
import ru.digitalhabbits.homework3.model.DepartmentInfo;
import ru.digitalhabbits.homework3.model.PersonRequest;
import ru.digitalhabbits.homework3.model.PersonResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

        Mockito.when(personDao.findAll()).thenReturn(mockPersons());
        Mockito.when(departmentDao.findById(anyInt())).thenReturn(Optional.of(new Department()));
        List<PersonResponse> persons = personService.findAllPersons();
        assertEquals(4, persons.size());
        assertThat(persons).extracting(PersonResponse::getFullName).contains("Пупкин Василий Иванович", "Шариков Полиграф Полиграфович", "Петров Пётр Петрович");
    }

    @Test
    void getPerson() {

        Department department = new Department().setId(1).setName("IT").setClosed(false);
        Mockito.when(personDao.findById(anyInt())).thenReturn(Optional.of(new Person().setFirstName("Василий").setMiddleName("Иванович").setLastName("Пупкин").setDepartment(department)));
        Mockito.when(departmentDao.findById(anyInt())).thenReturn(Optional.of(department));
        PersonResponse response = personService.getPerson(1);
        assertEquals("Пупкин Василий Иванович", response.getFullName());
    }

    @Test
    void createPerson() {

        Department department = new Department().setId(1).setName("IT").setClosed(false);
        Person person = new Person().setId(1).setFirstName("Василий").setMiddleName("Иванович").setLastName("Пупкин").setAge(30).setDepartment(department);
        Mockito.when(personDao.create(person)).thenReturn(100500);
        Mockito.when(departmentDao.findById(anyInt())).thenReturn(Optional.of(department));

        Integer result = personService.createPerson(new PersonRequest()
            .setFirstName(person.getFirstName())
            .setMiddleName(person.getMiddleName())
            .setLastName(person.getLastName())
            .setAge(person.getAge())
            .setId(person.getId())
            .setDepartmentInfo(new DepartmentInfo()
                    .setId(department.getId())
                    .setName(department.getName())
            ));

        assertEquals(100500, result);
    }

    @Test
    void updatePerson() {

        Department department = new Department().setId(1).setName("IT").setClosed(false);
        Department newDepartment = new Department().setId(2).setName("Adm").setClosed(false);
        Person person = new Person().setId(1).setFirstName("Василий").setMiddleName("Иванович").setLastName("Пупкин").setAge(30).setDepartment(department);

        Mockito.when(personDao.findById(anyInt())).thenReturn(Optional.of(person));
        Mockito.when(personDao.update(person)).thenReturn(person);
        Mockito.when(departmentDao.findById(2)).thenReturn(Optional.of(newDepartment));

        PersonResponse response = personService.updatePerson(1, new PersonRequest()
                .setLastName("Пузиков")
                .setFirstName("Иван")
                .setMiddleName("Иванович")
                .setAge(50)
                .setId(1)
                .setDepartmentInfo(new DepartmentInfo()
                        .setId(newDepartment.getId())
                        .setName(newDepartment.getName())
                )
        );

        assertEquals("Пузиков Иван Иванович", response.getFullName());
    }

    @Test
    void deletePerson() {
        Mockito.when(personDao.findById(anyInt())).thenReturn(Optional.of(new Person()));

        personService.deletePerson(1);
        verify(personDao, times(1)).findById(anyInt());
        verify(personDao, times(1)).delete(anyInt());

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