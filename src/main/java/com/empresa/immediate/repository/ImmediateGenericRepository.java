package com.empresa.immediate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImmediateGenericRepository extends CrudRepository <Object, Long> {

}
