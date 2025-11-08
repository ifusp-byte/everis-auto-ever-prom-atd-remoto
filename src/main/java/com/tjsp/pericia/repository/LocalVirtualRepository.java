package com.tjsp.pericia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tjsp.pericia.model.LocalVirtualModel;

@Repository
public interface LocalVirtualRepository extends CrudRepository<LocalVirtualModel, Long>{

}
