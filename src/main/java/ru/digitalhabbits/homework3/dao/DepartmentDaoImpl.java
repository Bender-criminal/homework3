package ru.digitalhabbits.homework3.dao;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabbits.homework3.domain.Department;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentDaoImpl
        implements DepartmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Department> findById(@Nonnull Integer id) {
        return Optional.ofNullable(entityManager.find(Department.class, id));
    }

    @Override
    public List<Department> findAll() {

        return entityManager.createQuery("select department from Department department").getResultList();
    }

    @Override
    public Department update(Department department) {

        return entityManager.merge(department);
    }

    @Override
    @Transactional
    public Optional<Department> delete(Integer id) {
        Optional<Department> department = findById(id);
        if (department.isPresent())
            entityManager.remove(department.get());
        return department;
    }

    @Override
    @Transactional
    public Integer create(Department department) {
        entityManager.persist(department);
        return department.getId();
    }
}
