package com.github.tumerbaatar.storage.repository;

import com.github.tumerbaatar.storage.model.StockEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockEntryRepository extends CrudRepository<StockEntry, Long> {

}
