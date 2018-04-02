package com.github.tumerbaatar.storage.repository;

import com.github.tumerbaatar.storage.model.Storage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorageRepository extends CrudRepository<Storage, Long> {
    Optional<Storage> findOneBySlug(String storageSlug);
}
