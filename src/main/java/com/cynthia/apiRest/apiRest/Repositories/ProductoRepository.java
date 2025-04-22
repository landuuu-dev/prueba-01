package com.cynthia.apiRest.apiRest.Repositories;

import com.cynthia.apiRest.apiRest.Entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
