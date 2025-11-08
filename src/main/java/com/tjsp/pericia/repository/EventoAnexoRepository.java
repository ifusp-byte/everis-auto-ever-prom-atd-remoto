package com.tjsp.pericia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tjsp.pericia.model.EventoAnexoModel;

@Repository
public interface EventoAnexoRepository extends CrudRepository<EventoAnexoModel, Long>{

}
