package com.github.tumerbaatar.storage.web.controller;

import com.github.tumerbaatar.storage.model.Part;
import com.github.tumerbaatar.storage.service.PartService;
import com.github.tumerbaatar.storage.service.exceptions.DuplicatePartException;
import com.github.tumerbaatar.storage.service.exceptions.PartNotFoundException;
import com.github.tumerbaatar.storage.web.controller.error.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/storage")
public class PartController {
    private PartService partService;

    @GetMapping("/parts")
    public Iterable<Part> allParts(
            @RequestParam(value = "storage", required = false) String storageSlug,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "results_on_page", required = false, defaultValue = "20") int resultsOnPage
    ) {
        // TODO: 06.04.2018 get user from headers
        log.info("Search storage: ", storageSlug);
        log.info("Search query: ", query );
        log.info("Search page: ", page);
        log.info("Search results on page: ", resultsOnPage);

        // TODO: 06.04.2018 implement search by query

        return partService.findPartsByStorage(storageSlug);
    }

    @GetMapping("/parts/{permanentHash}")
    public Part getPart(@PathVariable("permanentHash") String permanentHash) {
        log.info("Part requested with hash " + permanentHash);
        Part part = partService.findPart(permanentHash);
        log.info(part.getId() + " " + part.getPermanentHash() + " " + part.getName() + " " + part.getPartNumber());
        return part;
    }

    @PostMapping("/parts/create")
    public Part addPart(@RequestBody Part part) throws IOException {
        log.info("Part creation acquired " + part);
        return partService.createPart(part);
    }

    @ExceptionHandler(PartNotFoundException.class)
    public ResponseEntity<ErrorMessage> partNotExists(PartNotFoundException e) {
        ErrorMessage error = new ErrorMessage(404, "Запрошенная запчасть с заданными параметрами не существует", e.getParams());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatePartException.class)
    public ResponseEntity<ErrorMessage> duplicatePart(DuplicatePartException e) {
        ErrorMessage errorMessage = new ErrorMessage(400, "Запчасть с такими параметрами уже существует", e.getParams());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
