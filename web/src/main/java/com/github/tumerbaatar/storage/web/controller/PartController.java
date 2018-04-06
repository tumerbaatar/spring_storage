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

    @GetMapping("/{storage}/parts")
    public Iterable<Part> allParts(@PathVariable("storage") String storageSlug) {
        log.info("All parts send");
        return partService.findPartsByStorage(storageSlug);
    }

    @GetMapping("/{storage}/parts/{permanentHash}")
    public Part getPart(@PathVariable("storage") String storageSlug, @PathVariable("permanentHash") String permanentHash) {
        log.info("Part requested with hash " + permanentHash);
        Part part = partService.findPart(storageSlug, permanentHash);
        log.info(part.getId() + " " + part.getPermanentHash() + " " + part.getName() + " " + part.getPartNumber());
        return part;
    }

    @PostMapping("/{storage}/parts/add")
    public Part addPart(@PathVariable("storage") String storageSlug, @RequestBody Part part) throws IOException {
        return partService.createPart(storageSlug, part);
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
