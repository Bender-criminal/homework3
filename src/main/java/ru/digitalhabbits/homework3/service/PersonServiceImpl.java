package ru.digitalhabbits.homework3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.digitalhabbits.homework3.dao.DepartmentDaoImpl;
import ru.digitalhabbits.homework3.dao.PersonDaoImpl;
import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.DepartmentInfo;
import ru.digitalhabbits.homework3.model.PersonRequest;
import ru.digitalhabbits.homework3.model.PersonResponse;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl
        implements PersonService {

    private final PersonDaoImpl personDao;
    private final DepartmentDaoImpl departmentDao;

    @Nonnull
    @Override
    public List<PersonResponse> findAllPersons() {
        return personDao.findAll()
                .stream()
                .map(this::buildPersonResponse)
                .collect(Collectors.toList());

    }

    @Nonnull
    @Override
    public PersonResponse getPerson(@Nonnull Integer id) {

        final Person person = personDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person '" + id + "' not found"));

        return buildPersonResponse(person);

    }

    @Nonnull
    @Override
    public Integer createPerson(@Nonnull PersonRequest request) {

        Person person = new Person()
                .setId(request.getId())
                .setFirstName(request.getFirstName())
                .setMiddleName(request.getMiddleName())
                .setLastName(request.getLastName())
                .setAge(request.getAge())
                .setDepartment(new Department().setId(request.getDepartmentInfo().getId()).setName(request.getDepartmentInfo().getName()));
        return personDao.create(person);
    }

    @Nonnull
    @Override
    public PersonResponse updatePerson(@Nonnull Integer id, @Nonnull PersonRequest request) {
        final Person person = personDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person '" + id + "' not found"));
        Optional.ofNullable(request.getAge()).map(person::setAge);
        Optional.ofNullable(request.getFirstName()).map(person::setFirstName);
        Optional.ofNullable(request.getLastName()).map(person::setLastName);
        Optional.ofNullable(request.getMiddleName()).map(person::setMiddleName);
        person.setDepartment(new Department().setId(request.getDepartmentInfo().getId()).setName(request.getDepartmentInfo().getName()));
        personDao.update(person);

        return buildPersonResponse(person);
    }

    @Override
    public void deletePerson(@Nonnull Integer id) {
       if (personDao.findById(id).isPresent())
           personDao.delete(id);
    }

    @Nonnull
    private PersonResponse buildPersonResponse(@Nonnull Person person) {
        Optional<Department> department = departmentDao.findById(person.getDepartment().getId());
        return new PersonResponse()
                .setId(person.getId())
                .setAge(person.getAge())
                .setFullName(String.format("%s %s %s", person.getLastName(), person.getFirstName(), person.getMiddleName()))
                .setDepartment(new DepartmentInfo().setId(department.get().getId()).setName(department.get().getName()));

    }
}
