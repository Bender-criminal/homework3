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
import ru.digitalhabbits.homework3.model.DepartmentResponse;
import ru.digitalhabbits.homework3.model.DepartmentShortResponse;
import ru.digitalhabbits.homework3.model.PersonInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DepartmentServiceImpl.class)
class DepartmentServiceTest {

    @MockBean
    private DepartmentDaoImpl departmentDao;
    @MockBean
    private PersonDaoImpl personDao;

    @Autowired
    private DepartmentService departmentService;

    @Test
    void findAllDepartments() {

        Mockito.when(departmentDao.findAll()).thenReturn(mockDepartments());
        List<DepartmentShortResponse> departments = departmentService.findAllDepartments();
        assertEquals(3, departments.size());
        assertThat(departments).extracting(DepartmentShortResponse::getName).contains("Administration", "IT", "Buh");
    }

    @Test
    void getDepartment() {

        Mockito.when(departmentDao.findById(1)).thenReturn(Optional.of(new Department().setName("IT")));
        DepartmentResponse response = departmentService.getDepartment(1);
        assertEquals("IT", response.getName());
    }

    @Test
    void createDepartment() {

        Mockito.when(departmentDao.create(new Department().setName("test"))).thenReturn(100500);
        Integer result = departmentService.createDepartment(new DepartmentRequest().setName("test"));
        assertEquals(100500, result);
    }

    @Test
    void updateDepartment() {

        Department department = new Department().setName("Старый");
        Mockito.when(departmentDao.findById(10)).thenReturn(Optional.of(department));
        Mockito.when(departmentDao.update(new Department())).thenReturn(department.setName("Новый"));
        DepartmentResponse response = departmentService.updateDepartment(10, new DepartmentRequest().setName(department.getName()));
        assertEquals("Новый", response.getName());
    }

    @Test
    void deleteDepartment() {
        // TODO: NotImplemented
        Department department = new Department().setId(5).setName("Отдел продаж");
        Mockito.when(departmentDao.findById(5)).thenReturn(Optional.of(department));
        Mockito.when(personDao.getPersonsFromDepartment(department)).thenReturn(new ArrayList<>());

        departmentService.deleteDepartment(5);

        Mockito.when(departmentDao.findById(5)).thenReturn(Optional.empty());
        DepartmentResponse response = departmentService.getDepartment(5);

        assertEquals(0, response.getPersons().size());

    }

    @Test
    void addPersonToDepartment() {

        Department department = new Department().setId(1).setName("IT").setClosed(false);
        Person person = new Person().setId(1).setLastName("Пупкин").setFirstName("Василий").setMiddleName("Иванович").setAge(30).setDepartment(department);
        Mockito.when(departmentDao.findById(1)).thenReturn(Optional.of(department));
        Mockito.when(personDao.findById(1)).thenReturn(Optional.of(person));

        departmentService.addPersonToDepartment(1, 1);
        Mockito.when(personDao.getPersonsFromDepartment(department)).thenReturn(new ArrayList<Person>(Arrays.asList(person)));
        DepartmentResponse response = departmentService.getDepartment(1);
        assertThat(response.getPersons()).extracting(PersonInfo::getFullName).contains("Василий Иванович Пупкин");

    }

    @Test
    void removePersonToDepartment() {

        Department department = new Department().setId(1).setName("IT").setClosed(false);
        Person person = new Person().setId(1).setLastName("Пупкин").setFirstName("Василий").setMiddleName("Иванович").setAge(30).setDepartment(department);
        Mockito.when(departmentDao.findById(1)).thenReturn(Optional.of(department));
        Mockito.when(personDao.findById(1)).thenReturn(Optional.of(person));

        departmentService.removePersonToDepartment(1, 1);
        assertEquals(null, person.getDepartment());
    }

    @Test
    void closeDepartment() {

        Department department = new Department().setId(10).setName("IT").setClosed(false);
        Mockito.when(departmentDao.findById(10)).thenReturn(Optional.of(department));
        Mockito.when(personDao.getPersonsFromDepartment(department)).thenReturn(new ArrayList<>());

        departmentService.closeDepartment(10);
        DepartmentResponse response = departmentService.getDepartment(10);

        assertEquals(true, response.isClosed());
        assertEquals(0, response.getPersons().size());
    }

    private List<Department> mockDepartments(){
        return new ArrayList<Department>(Arrays.asList(
                new Department()
                        .setId(1)
                        .setName("IT")
                        .setClosed(false),
                new Department()
                        .setId(2)
                        .setName("Buh")
                        .setClosed(false),
                new Department()
                        .setId(3)
                        .setName("Administration")
                        .setClosed(false)
        ));
    }
}