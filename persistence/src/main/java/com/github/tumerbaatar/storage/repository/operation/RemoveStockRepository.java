package com.github.tumerbaatar.storage.repository.operation;

import org.github.tumerbaatar.storage.model.operation.RemoveStock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemoveStockRepository extends CrudRepository<RemoveStock, Long> {

}
