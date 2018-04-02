package org.github.tumerbaatar.storage.service;

import lombok.extern.slf4j.Slf4j;
import org.github.tumerbaatar.storage.service.exceptions.PartNotFoundException;
import org.github.tumerbaatar.storage.model.Part;
import org.github.tumerbaatar.storage.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class AttachmentService {
    @Value("${media.folder}")
    private String mediaFolder;
    @Value("${part.image.placeholder}")
    private String partImagePlaceholder;
    private PartRepository partRepository;

    @Autowired
    public AttachmentService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Transactional
    public Part saveImages(long partId, List<MultipartFile> images) {
        Part part = partRepository.findById(partId).orElseThrow(PartNotFoundException::new);
        List<String> imagePaths = part.getImages();
        imagePaths.remove(partImagePlaceholder);
        log.info("Found part with id = " + part.getId() + " and name " + part.getName());

        long i = part.getId();
        for (MultipartFile image : images) {
            String name = image.getOriginalFilename();
            String extension = name.substring(name.lastIndexOf("."), name.length()); // TODO: 21.03.2018 check extension of file. Should be PNG | JPEG | JPG. Otherwise throw exception and notify client

            long number = System.nanoTime() + i++;
            long n = number << numberSize(i) >> numberSize(i);
            n = n | (i << (19 - numberSize(i)));
            String uniqueName = Long.toString(n, 16) + extension;

            imagePaths.add(0, "/media/parts/images/" + uniqueName);
            Path path = Paths.get(mediaFolder, "parts", "images", uniqueName);

            File filePath = path.toFile();
            try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath))) {
                bufferedOutputStream.write(image.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        part.setImages(imagePaths);
        partRepository.save(part);
        return part;
    }

    private static int numberSize(long x) {
        long p = 10;
        for (int i = 1; i < 19; i++) {
            if (x < p)
                return i;
            p = 10 * p;
        }
        return 19;
    }

}
