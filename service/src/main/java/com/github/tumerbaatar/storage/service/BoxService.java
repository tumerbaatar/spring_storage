package com.github.tumerbaatar.storage.service;

import com.github.tumerbaatar.storage.service.exceptions.StorageNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.tumerbaatar.storage.service.exceptions.BoxNotFoundException;
import com.github.tumerbaatar.storage.service.exceptions.DuplicateBoxException;
import com.github.tumerbaatar.storage.model.Box;
import com.github.tumerbaatar.storage.model.BoxCreationDTO;
import com.github.tumerbaatar.storage.model.Storage;
import com.github.tumerbaatar.storage.repository.BoxRepository;
import com.github.tumerbaatar.storage.repository.StorageRepository;
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
    public Iterable<Box> addBoxes(List<BoxCreationDTO> creationDTOS) {
        List<Box> boxesToSave = new ArrayList<>();
        for (BoxCreationDTO jsonBox : creationDTOS) {
            Storage storage = storageRepository.findBySlug(jsonBox.getStorageSlug()).orElseThrow(StorageNotFoundException::new);
            if (boxRepository.findByStorageSlugAndName(storage.getSlug(), jsonBox.getName()).isPresent()) {
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
    public Box findByPermanentHash(String permanentHash) {
        return boxRepository.findByPermanentHash(permanentHash).orElseThrow(BoxNotFoundException::new);
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
