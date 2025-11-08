package com.tjsp.pericia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tjsp.pericia.model.EnderecoVirtualModel;

@Repository
public interface EnderecoVirtualRepository extends CrudRepository<EnderecoVirtualModel, Long>{

}
