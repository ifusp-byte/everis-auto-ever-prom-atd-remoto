package com.tjsp.pericia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tjsp.pericia.model.LeiModel;

@Repository
public interface LeiRepository extends CrudRepository<LeiModel, Long>{

}
