package com.github.tumerbaatar.storage.service;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;

import com.github.tumerbaatar.storage.service.exceptions.DuplicatePartException;
import com.github.tumerbaatar.storage.service.exceptions.PartNotFoundException;
import com.github.tumerbaatar.storage.service.exceptions.StorageNotFoundException;
import com.github.tumerbaatar.storage.model.Part;
import com.github.tumerbaatar.storage.model.Storage;
import com.github.tumerbaatar.storage.repository.PartRepository;
import com.github.tumerbaatar.storage.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class PartService {
    @Value("${media.folder}")
    private String mediaFolder;
    @Value("${part.image.placeholder}")
    private String partImagePlaceholder;
    private StorageRepository storageRepository;
    private PartRepository partRepository;
    private PermanentHashProvider hashProvider;
    private StickerService stickerService;
    private RestHighLevelClient client;

    @Autowired
    public PartService(
            StorageRepository storageRepository,
            PartRepository partRepository,
            PermanentHashProvider hashProvider,
            StickerService stickerService,
            RestHighLevelClient client
    ) {
        this.storageRepository = storageRepository;
        this.partRepository = partRepository;
        this.hashProvider = hashProvider;
        this.stickerService = stickerService;
        this.client = client;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void elasticSearchIndexDocuments() throws IOException {
        for (Part part : partRepository.findAll()) {
            indexPart(part);
        }
    }

    @Transactional
    public Part createPart(String storageSlug, Part part) throws IOException {
        log.info("Part to be added " + part.toString());

        if (partRepository.findPartByStorageSlugAndPartNumber(storageSlug, part.getPartNumber()).isPresent()) {
            throw new DuplicatePartException(
                    new HashMap<String, Object>() {{
                        put("storageSlug", storageSlug);
                        put("partNumber", part.getPartNumber());
                    }},
                    "Запчасть с данным парт-номером уже есть на этом складе"
            );
        }

        Storage storage = storageRepository.findOneBySlug(storageSlug).orElseThrow(StorageNotFoundException::new);
        part.setStorage(storage);

        Part savedPart = partRepository.save(part);
        savedPart = hashProvider.setPermanentHash(savedPart);

        if (savedPart.getImages() != null || savedPart.getImages().size() == 0) {
            List<String> images = new ArrayList<>();
            images.add(partImagePlaceholder);
            savedPart.setImages(images);
        }

        partRepository.save(savedPart);
        indexPart(savedPart);

        return savedPart;
    }

    private void indexPart(Part part) throws IOException {
        Storage storage = part.getStorage();
        IndexRequest indexRequest = new IndexRequest(storage.getSlug(), "part", String.valueOf(part.getId()));

        indexRequest.source(
                "partNumber", part.getPartNumber(),
                "name", part.getName(),
                "description", part.getDescription(),
                "manufacturerPartNumber", part.getManufacturerPartNumber()
        );

        IndexResponse response = client.index(indexRequest);
        log.info("Index response status name " + response.status().name());
        log.info("Index response status " + response.status().getStatus());
    }

    @Transactional
    public Part findPart(long partId) {
        return partRepository
                .findById(partId)
                .orElseThrow(
                        () -> new PartNotFoundException(
                                new HashMap<String, Object>() {{
                                    put("partId", partId);
                                }}
                        )
                );
    }

    @Transactional
    public Part findPart(String storageSlug, String permanentHash) {
        return partRepository
                .findOneByStorageSlugAndPermanentHash(storageSlug, permanentHash)
                .orElseThrow(
                        () -> new PartNotFoundException(
                                new HashMap<String, Object>() {{
                                    put("storageSlug", storageSlug);
                                    put("permanentHash", permanentHash);
                                }}
                        )
                );
    }

    @Transactional
    public byte[] stickerForIds(String storageSlug, List<Long> partIds) {
        if (partIds == null || partIds.isEmpty()) {
            return stickerService.generateExcelOfStickers(partRepository.findAllByStorageSlug(storageSlug));
        } else {
            return stickerService.generateExcelOfStickers(partRepository.findAllById(partIds));
        }
    }

    @Transactional
    public Iterable<Part> findPartsByStorage(long storageId) {
        return partRepository.findAllByStorageId(storageId);
    }

    @Transactional
    public Iterable<Part> findPartsByStorage(String storageSlug) {
        return partRepository.findAllByStorageSlug(storageSlug);
    }
}
