package com.tjsp.pericia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tjsp.pericia.model.LocalModel;

@Repository
public interface LocalRepository extends CrudRepository<LocalModel, Long>{

}
