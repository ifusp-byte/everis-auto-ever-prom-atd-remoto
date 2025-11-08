package com.tjsp.pericia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tjsp.pericia.model.EnderecoFisicoModel;

@Repository
public interface EnderecoFisicoRepository extends CrudRepository<EnderecoFisicoModel, Long>{

}
