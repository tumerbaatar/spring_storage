package com.github.tumerbaatar.storage.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.tumerbaatar.storage.model.Box;
import com.github.tumerbaatar.storage.model.BoxCreationDTO;
import com.github.tumerbaatar.storage.service.BoxService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/storage")
public class BoxController {
    private BoxService boxService;

    @GetMapping("/boxes")
    public Iterable<Box> allBoxes(
            @RequestParam(value = "storage", required = false) String storageSlug,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "results_on_page", required = false, defaultValue = "20") int resultsOnPage
    ) {
        log.info("/boxes");
        // TODO: 06.04.2018 implement search
        if (query == null) {
            return boxService.findAllInStorage(storageSlug);
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/boxes/{permanent_hash}")
    public Box getBoxByHash(
            @PathVariable("permanent_hash") String boxHash
    ) {
        return boxService.findByPermanentHash(boxHash);
    }

    @PostMapping("/boxes/create")
    public Iterable<Box> boxAdd(
            @RequestBody List<BoxCreationDTO> creationDTOS
    ) {
        log.info("Storage " + creationDTOS);
        return boxService.addBoxes(creationDTOS);
    }
}
