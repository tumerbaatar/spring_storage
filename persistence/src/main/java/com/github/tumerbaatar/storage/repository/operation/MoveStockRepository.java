package com.github.tumerbaatar.storage.repository.operation;

import com.github.tumerbaatar.storage.model.operation.MoveStock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoveStockRepository extends CrudRepository<MoveStock, Long> {

}
