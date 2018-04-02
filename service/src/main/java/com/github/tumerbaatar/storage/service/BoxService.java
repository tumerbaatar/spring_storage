package org.github.tumerbaatar.storage.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.tumerbaatar.storage.service.exceptions.BoxNotFoundException;
import org.github.tumerbaatar.storage.service.exceptions.DuplicateBoxException;
import org.github.tumerbaatar.storage.service.exceptions.StorageNotFoundException;
import org.github.tumerbaatar.storage.model.Box;
import org.github.tumerbaatar.storage.model.BoxCreationDTO;
import org.github.tumerbaatar.storage.model.Storage;
import org.github.tumerbaatar.storage.repository.BoxRepository;
import org.github.tumerbaatar.storage.repository.StorageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BoxService {
    private StorageRepository storageRepository;
    private BoxRepository boxRepository;
    private PermanentHashProvider permanentHashProvider;
    private StickerService stickerService;

    @Transactional
    public Iterable<Box> addBoxes(String storageSlug, List<BoxCreationDTO> creationDTOS) {
        Storage storage = storageRepository.findOneBySlug(storageSlug).orElseThrow(StorageNotFoundException::new);

        List<Box> boxesToSave = new ArrayList<>();
        for (BoxCreationDTO jsonBox : creationDTOS) {
            if (boxRepository.findByStorageSlugAndPermanentHash(storageSlug, jsonBox.getName()).isPresent()) {
                throw new DuplicateBoxException("Box with name " + jsonBox.getName() + " is already present in database");
            }
            Box box = new Box(jsonBox.getName());
            box.setStorage(storage);
            box.setSinglePartBox(jsonBox.isSinglePartBox());
            box = permanentHashProvider.setPermanentHash(box);
            boxesToSave.add(box);
        }
        return boxRepository.saveAll(boxesToSave);
    }

    @Transactional
    public Box findByStorageAndPermanentHash(String storageSlug, String permanentHash) {
        return boxRepository.findByStorageSlugAndPermanentHash(storageSlug, permanentHash).orElseThrow(BoxNotFoundException::new);
    }

    @Transactional
    public Box findOne(long boxId) {
        // TODO: 31.03.2018 Написать тесты для проверки исключений BoxNotFoundException
        return boxRepository.findById(boxId).orElseThrow(BoxNotFoundException::new);
    }

    @Transactional
    public byte[] stickersForIds(String storageSlug, List<Long> boxIds) {
        log.info("Requested ids " + boxIds);
        if (boxIds.isEmpty()) {
            return stickerService.generateExcelOfBoxes(boxRepository.findAllByStorageSlug(storageSlug));
        }
        return stickerService.generateExcelOfBoxes(boxRepository.findAllById(boxIds));
    }

    @Transactional
    public Iterable<Box> findAllInStorage(String storageSlug) {
        return boxRepository.findAllByStorageSlug(storageSlug);
    }
}
