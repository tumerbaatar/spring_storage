package org.github.tumerbaatar.storage.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.tumerbaatar.storage.model.Box;
import org.github.tumerbaatar.storage.model.Part;
import org.github.tumerbaatar.storage.repository.BoxRepository;
import org.github.tumerbaatar.storage.repository.PartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@AllArgsConstructor
public class PermanentHashProvider {
    private PartRepository partRepository;
    private BoxRepository boxRepository;

    private String generateHash(String data) {
        final MessageDigest md5;
        final StringBuilder hex = new StringBuilder();
        try {
            md5 = MessageDigest.getInstance("MD5");
            final byte[] digestBytes = md5.digest(data.getBytes("utf-8"));
            for (byte b : digestBytes) {
                hex.append(String.format("%02x", b));
            }
            log.debug("Source data to be hashed " + data);
            log.debug("Generated hash " + hex.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hex.toString();
    }

    @Transactional
    public Part setPermanentHash(Part part) {
        String sourceData = part.getPartNumber() + part.getName();

        String permanentHash = generateHash(sourceData);
        while (partRepository.findByPermanentHash(permanentHash).isPresent()) {
            permanentHash = generateHash(sourceData + System.nanoTime());
        }
        part.setPermanentHash(permanentHash);
        return part;
    }

    @Transactional
    public Box setPermanentHash(Box box) {
        String sourceData = box.isSinglePartBox() + box.getName();

        String permanentHash = generateHash(sourceData);
        while (boxRepository.findByPermanentHash(permanentHash).isPresent()) {
            permanentHash = generateHash(sourceData + System.nanoTime());
        }
        box.setPermanentHash(permanentHash);
        return box;
    }
}
