package ru.digitalhabbits.homework3.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonDaoImpl
        implements PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Person> findById(@Nonnull Integer id) {
        return Optional.ofNullable(entityManager.find(Person.class, id));
    }

    @Override
    public List<Person> findAll() {

        return entityManager.createQuery("select person from Person person").getResultList();
    }

    @Override
    @Transactional
    public Person update(Person person) {
        return entityManager.merge(person);
    }

    @Override
    @Transactional
    public Optional<Person> delete(Integer id) {
        Optional<Person> person = findById(id);
        if (person.isPresent())
            entityManager.remove(person.get());
        return person;
    }

    @Override
    @Transactional
    public Integer create(Person person) {
        entityManager.persist(person);
        return person.getId();
    }

    public List<Person> getPersonsFromDepartment(@Nonnull Department department){
        return entityManager.createQuery("select person from Person person where department = ?1")
                .setParameter(1, department)
                .getResultList();
    }

    @Transactional
    public void removePersonsFromDepartment(@Nonnull Department department){
        entityManager.createQuery("update Person set department = null where department = ?1")
                .setParameter(1, department)
                .executeUpdate();
    }
}
