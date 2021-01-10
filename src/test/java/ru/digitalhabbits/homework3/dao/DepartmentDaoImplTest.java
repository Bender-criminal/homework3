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
import ru.digitalhabbits.homework3.domain.Department;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class DepartmentDaoImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    void findById() {
        mockData();
        Department department = departmentDao.findById(2).get();
        assertEquals("Buh", department.getName());
    }

    @Test
    void findAll() {
        mockData();
        List<Department> allDepartments = departmentDao.findAll();
        assertEquals(3, allDepartments.size());
        assertThat(allDepartments).extracting(Department::getName).contains("Administration", "IT", "Buh");
    }

    @Test
    void update() {
         mockData();
        Department department = departmentDao.findById(3).get();
        department.setName("Бездельники");
        departmentDao.update(department);
        assertEquals("Бездельники", departmentDao.findById(3).get().getName());
    }

    @Test
    void delete() {
        mockData();
        departmentDao.delete(3);
        assertEquals( false, departmentDao.findById(3).isPresent());
    }

    private void mockData(){
        entityManager.persist(new Department()
                .setId(1)
                .setName("IT")
                .setClosed(false));
        entityManager.persist(new Department()
                .setId(2)
                .setName("Buh")
                .setClosed(false));
        entityManager.persist(new Department()
                .setId(3)
                .setName("Administration")
                .setClosed(false));
    }
}