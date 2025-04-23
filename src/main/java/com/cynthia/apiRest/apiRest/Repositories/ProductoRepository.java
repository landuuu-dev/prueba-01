package com.cynthia.apiRest.apiRest.Repositories;

import com.cynthia.apiRest.apiRest.Entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoriaId(Long categoriaId);
}
