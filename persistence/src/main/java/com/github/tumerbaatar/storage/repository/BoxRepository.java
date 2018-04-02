package com.github.tumerbaatar.storage.repository;

import com.github.tumerbaatar.storage.model.Box;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoxRepository extends CrudRepository<Box, Long> {
    Optional<Box> findById(long id);

    Optional<Box> findByPermanentHash(String boxHash);

    Optional<Box> findByName(String name);

    Iterable<Box> findAllByStorageSlug(String storageSlug);

    Optional<Box> findByStorageSlugAndPermanentHash(String storageSlug, String permanentHash);
}

