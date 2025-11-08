package com.ifusp.empresa.batch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ifusp.empresa.batch.model.CustomerDataFileModel;
import com.ifusp.empresa.batch.model.CustomerModel;

@Repository
public interface CustomerDataFileRepository extends CrudRepository<CustomerModel, Long> {

	Object save(CustomerDataFileModel customerDataFileModel);

}
