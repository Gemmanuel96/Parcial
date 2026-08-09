package org.example.repository;

import org.example.model.Producto;

public class ProductoRepository extends BaseRepositoty<Producto> {


    public ProductoRepository() {
        super(Producto.class);
    }

}
