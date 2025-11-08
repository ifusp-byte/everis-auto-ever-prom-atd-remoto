package com.tjsp.pericia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tjsp.pericia.model.CrimeModel;

@Repository
public interface CrimeRepository extends CrudRepository<CrimeModel, Long>{

}
