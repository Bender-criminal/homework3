package ru.digitalhabbits.homework3.dao;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;
import ru.digitalhabbits.homework3.domain.Department;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DepartmentDaoImpl
        implements DepartmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Department findById(@Nonnull Integer integer) {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }

    @Override
    public List<Department> findAll() {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }

    @Override
    public Department update(Department entity) {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }

    @Override
    public Department delete(Integer integer) {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }

    @Override
    public Integer create(Department entity) {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }
}
