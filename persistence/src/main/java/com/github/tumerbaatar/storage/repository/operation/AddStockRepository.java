package com.github.tumerbaatar.storage.repository.operation;

import com.github.tumerbaatar.storage.model.operation.AddStock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddStockRepository extends CrudRepository<AddStock, Long> {

}
