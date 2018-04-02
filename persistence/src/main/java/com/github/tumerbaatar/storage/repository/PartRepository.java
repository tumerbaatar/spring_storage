package com.github.tumerbaatar.storage.repository;

import com.github.tumerbaatar.storage.model.Part;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartRepository extends CrudRepository<Part, Long> {

    Optional<Part> findByPermanentHash(String permanentHash);

    Iterable<Part> findAllByStorageId(long storageId);

    Iterable<Part> findAllByStorageSlug(String storageSlug);

    Optional<Part> findOneByStorageSlugAndPermanentHash(String storageSlug, String permanentHash);

    Optional<Part> findPartByStorageSlugAndPartNumber(String storageSlug, String partNumber);
}
