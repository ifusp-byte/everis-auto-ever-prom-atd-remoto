package com.tjsp.pericia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tjsp.pericia.model.ProvedorConteudoUsuarioModel;

@Repository
public interface ProvedorConteudoUsuarioRepository extends CrudRepository<ProvedorConteudoUsuarioModel, Long>{

}
