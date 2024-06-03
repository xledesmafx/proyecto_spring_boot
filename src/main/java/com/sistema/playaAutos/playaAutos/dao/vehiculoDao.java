package com.sistema.playaAutos.playaAutos.dao;

import com.sistema.playaAutos.playaAutos.modelos.Auto;
import org.springframework.data.repository.CrudRepository;

public interface vehiculoDao extends CrudRepository<Auto, Long> {
}
