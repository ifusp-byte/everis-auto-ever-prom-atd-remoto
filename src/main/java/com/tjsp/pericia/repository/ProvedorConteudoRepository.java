package com.tjsp.pericia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tjsp.pericia.model.ProvedorConteudoModel;

@Repository
public interface ProvedorConteudoRepository extends CrudRepository<ProvedorConteudoModel, Long>{

}
