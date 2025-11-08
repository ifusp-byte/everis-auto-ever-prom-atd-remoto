package com.tjsp.pericia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tjsp.pericia.model.LocalFisicoModel;

@Repository
public interface LocalFisicoRepository extends CrudRepository<LocalFisicoModel, Long>{

}
