package org.example.repository;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepositoty<T> {

    public abstract void guardar(T entity);

    public abstract boolean eliminarLogico(long id);

    public abstract Optional<T> buscarPorId(long id);

    public abstract List<T> listarActivos();
}
