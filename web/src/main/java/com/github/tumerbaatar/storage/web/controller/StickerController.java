package com.github.tumerbaatar.storage.web.controller;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.tumerbaatar.storage.service.BoxService;
import com.github.tumerbaatar.storage.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@NoArgsConstructor
@RequestMapping("/storage")
public class StickerController {
    private PartService partService;
    private BoxService boxService;

    @Autowired
    public StickerController(PartService partService, BoxService boxService) {
        this.partService = partService;
        this.boxService = boxService;
    }

    @RequestMapping(path = "/download/part_stickers", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Resource> downloadPartStickers(
            @RequestParam(value = "storage", required = false) String storageSlug,
            @RequestBody List<Long> partIds
    ) {
        log.info("Requested ids " + partIds);
        byte[] file = partService.stickerForIds(storageSlug, partIds);
        ByteArrayResource resource = new ByteArrayResource(file);

        // TODO: 19.03.2018 Create meaningful name for part stickers file
        log.info("Will send file with excel");
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"partStickers.xlsx\"")
                .header("content-type", "xlsx")
                .contentLength(file.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @RequestMapping(path = "/download/box_stickers", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Resource> downloadBoxStickers(
            @RequestParam(value = "storage", required = false) String storageSlug,
            @RequestBody List<Long> boxIds
    ) {
        log.info("Requested ids " + boxIds);
        byte[] file = boxService.stickersForIds(storageSlug, boxIds);
        ByteArrayResource resource = new ByteArrayResource(file);

        log.info("Will send file with excel");
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"boxStickers.xlsx\"")
                .header("content-type", "xlsx")
                .contentLength(file.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
