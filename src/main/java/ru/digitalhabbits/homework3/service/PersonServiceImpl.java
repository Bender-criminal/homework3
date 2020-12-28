package ru.digitalhabbits.homework3.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import ru.digitalhabbits.homework3.dao.PersonDaoImpl;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.PersonRequest;
import ru.digitalhabbits.homework3.model.PersonResponse;

import javax.annotation.Nonnull;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl
        implements PersonService {

    private final PersonDaoImpl dao;

    @Nonnull
    @Override
    public List<PersonResponse> findAllPersons() {
        // TODO: NotImplemented: получение информации о всех людях во всех отделах
        throw new NotImplementedException();
    }

    @Nonnull
    @Override
    public PersonResponse getPerson(@Nonnull Integer id) {
        // TODO: NotImplemented: получение информации о человеке. Если не найдено, отдавать 404:NotFound
        Person person = dao.findById(id);

        return buildPersonResponse(person);

    }

    @Nonnull
    @Override
    public Integer createPerson(@Nonnull PersonRequest request) {

        Person person = new Person()
                .setFirstName(request.getFirstName())
                .setMiddleName(request.getMiddleName())
                .setLastName(request.getLastName())
                .setAge(request.getAge());
        return dao.create(person);
    }

    @Nonnull
    @Override
    public PersonResponse updatePerson(@Nonnull Integer id, @Nonnull PersonRequest request) {
        // TODO: NotImplemented: обновление информации о человеке. Если не найдено, отдавать 404:NotFound
        throw new NotImplementedException();
    }

    @Override
    public void deletePerson(@Nonnull Integer id) {
        // TODO: NotImplemented: удаление информации о человеке и удаление его из отдела. Если не найдено, ничего не делать
        throw new NotImplementedException();
    }

    @Nonnull
    private PersonResponse buildPersonResponse(@Nonnull Person person) {
        return new PersonResponse()
                .setId(person.getId())
                .setAge(person.getAge())
                .setFullName(String.format("%s %s %s", person.getLastName(), person.getFirstName(), person.getMiddleName()))
                .setDepartment(person.getDepartment());
    }
}
