package org.example.repository;

import org.example.model.Base;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepositoty<T extends Base> {

    private final Class<T> entityClass;

    public BaseRepositoty(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
}
