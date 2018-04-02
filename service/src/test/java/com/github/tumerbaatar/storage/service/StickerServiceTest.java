package com.github.tumerbaatar.storage.service;

import com.github.tumerbaatar.storage.persistence.model.Box;
import com.github.tumerbaatar.storage.persistence.model.Part;
import com.github.tumerbaatar.storage.persistence.model.StockEntry;
import org.junit.Before;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StickerServiceTest {
    private StickerService stickerService;
    private Box box1;
    private Box box2;
    private Box box3;

    @Before
    public void beforeEach() {
        box1 = new Box("box1");
        box1.setPermanentHash("box_ph1");

        box2 = new Box("box2");
        box2.setPermanentHash("box_ph2");

        box3 = new Box("box3");
        box3.setPermanentHash("box_ph3");

        stickerService = new StickerService();
    }

    @Test
    public void testGenerateExcelOfBoxes() throws IOException {
        List<Box> boxList = Arrays.asList(box1, box2, box3);
        byte[] file = stickerService.generateExcelOfBoxes(boxList);
        FileOutputStream out = new FileOutputStream("C:\\temp\\boxes.xlsx");
        out.write(file);

    }

    @Test
    public void testGenerateExcelOfStickers() throws IOException {

        List<Part> partList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String partNumber = "partNumber" + i;
            String name = "part name " + i;
            String description = "description " + i;
            String permanentHash = "permanent hash " + i;

            Part part = new Part(partNumber);
            part.setId(i);
            part.setName(name);
            part.setDescription(description);
            part.setPermanentHash(permanentHash);

            StockEntry stockEntry = new StockEntry();
            stockEntry.setBox(box1);
            stockEntry.setPart(part);

            part.addStockEntry(stockEntry);

            partList.add(part);
        }

        byte[] partsStickersExcel = stickerService.generateExcelOfStickers(partList);
        FileOutputStream out = new FileOutputStream("C:\\temp\\sxssf.xlsx");
        out.write(partsStickersExcel);
    }

}