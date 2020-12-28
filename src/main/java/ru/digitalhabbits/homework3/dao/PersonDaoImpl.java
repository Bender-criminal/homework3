package ru.digitalhabbits.homework3.dao;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;
import ru.digitalhabbits.homework3.domain.Person;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PersonDaoImpl
        implements PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Person findById(@Nonnull Integer id) {
        return entityManager.find(Person.class, id);
    }

    @Override
    public List<Person> findAll() {
        // TODO: NotImplemented
        //throw new NotImplementedException();
        return entityManager.createQuery("select person from Person person").getResultList();
    }

    @Override
    public Person update(Person entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Person delete(Integer integer) {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Person person = findById(integer);
        entityManager.remove(person);
        transaction.commit();
        return person;
    }

    @Override
    public Integer create(Person entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
        return entity.getId();
    }
}
