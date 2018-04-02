package com.github.tumerbaatar.storage.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.tumerbaatar.storage.model.Storage;
import org.github.tumerbaatar.storage.repository.StorageRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class StorageController {
    private StorageRepository storageRepository;

    @GetMapping("/storages")
    public Iterable<Storage> storageList() {
        return storageRepository.findAll();
    }
}
