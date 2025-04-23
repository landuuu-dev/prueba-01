package com.cynthia.apiRest.apiRest.Repositories;

import com.cynthia.apiRest.apiRest.Entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
