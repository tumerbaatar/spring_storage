package com.github.tumerbaatar.storage.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.tumerbaatar.storage.model.Box;
import org.github.tumerbaatar.storage.model.BoxCreationDTO;
import com.github.tumerbaatar.storage.service.BoxService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/storage")
public class BoxController {
    private BoxService boxService;

    @GetMapping("/{storage}/boxes")
    public Iterable<Box> allBoxes(@PathVariable("storage") String storageSlug) {
        log.info("/boxes");
        return boxService.findAllInStorage(storageSlug);
    }

    @GetMapping("/{storage}/boxes/{permanentHash}")
    public Box getBoxByHash(@PathVariable("storage") String storageSlug, @PathVariable("permanentHash") String boxHash) {
        return boxService.findByStorageAndPermanentHash(storageSlug, boxHash);
    }

    @PostMapping("/{storage}/boxes/add")
    public Iterable<Box> boxAdd(@PathVariable("storage") String storageSlug, @RequestBody List<BoxCreationDTO> creationDTOS) {
        log.info("Storage slug " + storageSlug);
        return boxService.addBoxes(storageSlug, creationDTOS);
    }
}
