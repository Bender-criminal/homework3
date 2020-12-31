package ru.digitalhabbits.homework3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabbits.homework3.dao.DepartmentDao;
import ru.digitalhabbits.homework3.dao.PersonDaoImpl;
import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.exceptions.ConflictException;
import ru.digitalhabbits.homework3.model.*;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl
        implements DepartmentService {

    private final DepartmentDao departmentDao;
    private final PersonDaoImpl personDao;

    @Nonnull
    @Override
    public List<DepartmentShortResponse> findAllDepartments() {
        return departmentDao.findAll()
                .stream()
                .map(this::buildShortDepartmentResponse)
                .collect(Collectors.toList());
    }

    @Nonnull
    @Override
    public DepartmentResponse getDepartment(@Nonnull Integer id) {

        final Department department = departmentDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department '" + id + "' not found"));

        return buildDepartmentResponse(department);
    }

    @Nonnull
    @Override
    public Integer createDepartment(@Nonnull DepartmentRequest request) {

        Department department = new Department()
                .setId(request.getId())
                .setName(request.getName())
                .setClosed(false);

        return departmentDao.create(department);
    }

    @Nonnull
    @Override
    @Transactional
    public DepartmentResponse updateDepartment(@Nonnull Integer id, @Nonnull DepartmentRequest request) {

        final Department department = departmentDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department '" + id + "' not found"));
        Optional.ofNullable(request.getName()).map(department::setName);
        departmentDao.update(department);

        return buildDepartmentResponse(department);
    }


    @Override
    @Transactional
    public void deleteDepartment(@Nonnull Integer id) {

        final Department department = departmentDao.findById(id).get();

        if (department != null){
            personDao.removePersonsFromDepartment(department);
            departmentDao.delete(id);
        }
    }

    @Override
    @Transactional
    public void addPersonToDepartment(@Nonnull Integer departmentId, @Nonnull Integer personId) {

        final Department department = departmentDao.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department '" + departmentId + "' not found"));
        final Person person = personDao.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person '" + personId + "' not found"));
        if (department.isClosed()) throw new ConflictException("Department '" + departmentId + "' is closed");

        person.setDepartment(department);
        personDao.update(person);
    }

    @Override
    @Transactional
    public void removePersonToDepartment(@Nonnull Integer departmentId, @Nonnull Integer personId) {

       departmentDao.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department '" + departmentId + "' not found"));
        final Person person = personDao.findById(personId).get();

        if (departmentId.equals(person.getDepartment().getId()) && person != null){
            person.setDepartment(null);
            personDao.update(person);
        }

    }

    @Override
    @Transactional
    public void closeDepartment(@Nonnull Integer id) {

        final Department department = departmentDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department '" + id + "' not found"));

        department.setClosed(true);
        personDao.removePersonsFromDepartment(department);
        departmentDao.update(department);
    }

    @Nonnull
    private DepartmentShortResponse buildShortDepartmentResponse(@Nonnull Department department) {
        return new DepartmentShortResponse()
                .setId(department.getId())
                .setName(department.getName());
    }

    @Nonnull
    private DepartmentResponse buildDepartmentResponse(Department department) {
        List<Person> persons = personDao.getPersonsFromDepartment(department);
        return new DepartmentResponse()
                .setId(department.getId())
                .setName(department.getName())
                .setClosed(department.isClosed())
                .setPersons(persons.stream().map(person -> new PersonInfo()
                        .setId(person.getId())
                        .setFullName(person.getFirstName() + " " + person.getMiddleName() + " " + person.getLastName()))
                .collect(Collectors.toList()));
    }
}
