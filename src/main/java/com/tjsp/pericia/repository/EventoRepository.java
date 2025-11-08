package com.tjsp.pericia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tjsp.pericia.model.EventoModel;

@Repository
public interface EventoRepository extends CrudRepository<EventoModel, Long>{

}
