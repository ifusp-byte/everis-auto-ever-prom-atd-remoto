package com.empresa.immediate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.empresa.immediate.model.ImmediateModel;

@Repository
public interface ImmediateRepository extends CrudRepository <ImmediateModel, Long> {

}
