package org.example.repository;

import org.example.model.Base;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepositoty<T extends Base> {

    private final Class<T> entityClass;

    public BaseRepositoty(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public abstract T guardar(T entity);

    public abstract boolean eliminarLogico(long id);

    public abstract Optional<T> buscarPorId(long id);

    public abstract List<T> listarActivos();
}
