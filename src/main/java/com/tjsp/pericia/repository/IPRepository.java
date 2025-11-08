package com.tjsp.pericia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tjsp.pericia.model.IPModel;

@Repository
public interface IPRepository extends CrudRepository<IPModel, Long>{

}
