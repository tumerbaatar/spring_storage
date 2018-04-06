package com.github.tumerbaatar.storage.web.controller;

import lombok.extern.slf4j.Slf4j;
import com.github.tumerbaatar.storage.model.Part;
import com.github.tumerbaatar.storage.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/storage")
public class AttachmentController {
    @Value("${media.folder}")
    private String mediaFolder;
    private AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/parts/images/upload")
    public Part handleFileUpload(
            @RequestParam("part_id") long partId,
            @RequestParam("files[]") List<MultipartFile> files
    ) {
        log.info("part id " + partId);
        return attachmentService.saveImages(partId, files);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(FileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
