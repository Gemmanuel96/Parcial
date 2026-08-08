package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "Categoria")
@NamedQueries({
        @NamedQuery(
                name = "Categoria.findActivos",
                query = "select c from Categoria c where c.eliminado = false"
        )
})
public class Categoria extends Base {

        private String nombre;
        private String descripcion;


}
