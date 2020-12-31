package ru.digitalhabbits.homework3.dao;

import org.springframework.data.repository.NoRepositoryBean;
import ru.digitalhabbits.homework3.domain.Person;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CrudOperations<T, ID> {

    Optional<T> findById(@Nonnull ID id);

    List<T> findAll();

    T update(T entity);

    Optional<T> delete(ID id);

    ID create(T entity);
}
