package org.github.tumerbaatar.storage.service;

import com.google.zxing.EncodeHintType;
import lombok.NoArgsConstructor;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.github.tumerbaatar.storage.model.Box;
import org.github.tumerbaatar.storage.model.Part;
import org.github.tumerbaatar.storage.model.StockEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class StickerService {
    @Value("${site}")
    private String siteAddress;

    public byte[] generateExcelOfBoxes(Iterable<Box> boxes) {
        final SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
        final Sheet sh = wb.createSheet();

        Font font = wb.createFont();
        font.setFontName("Roboto");
        font.setBold(true);

        final CreationHelper helper = wb.getCreationHelper();
        Drawing drawing = sh.createDrawingPatriarch();

        int rowNum = 0;
        for (Box box : boxes) {

            byte[] qrImageBytes = qrImageBytes(siteAddress + "/parts/" + box.getPermanentHash());
            int pictureIdx = wb.addPicture(qrImageBytes, Workbook.PICTURE_TYPE_PNG);

            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
            anchor.setCol1(0);
            anchor.setCol2(1);
            anchor.setRow1(rowNum + 1);
            anchor.setRow2(rowNum + 5);

            final Picture picture = drawing.createPicture(anchor, pictureIdx);
            picture.resize();

            CellStyle boxNameStyle = wb.createCellStyle();
            boxNameStyle.setFont(font);
            boxNameStyle.setBorderTop(BorderStyle.DASH_DOT);
            boxNameStyle.setBorderLeft(BorderStyle.DASH_DOT);
            boxNameStyle.setBorderRight(BorderStyle.DASH_DOT);

            Row boxNameRow = sh.createRow(rowNum);
            Cell boxNameCell = boxNameRow.createCell(0);
            boxNameCell.setCellValue(box.getName());
            boxNameCell.setCellStyle(boxNameStyle);

            rowNum = rowNum + 7;
        }

        return workbookToBytes(wb);
    }

    public byte[] generateExcelOfStickers(Iterable<Part> parts) {

        final SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
        final Sheet sh = wb.createSheet();
        sh.setColumnWidth(0, 6_000);

        Font font = wb.createFont();
        font.setFontName("Roboto");
        font.setBold(true);

        final CreationHelper helper = wb.getCreationHelper();
        Drawing drawing = sh.createDrawingPatriarch();

        int rowNum = 0;
        for (Part part : parts) {
            byte[] qrImageBytes = qrImageBytes(siteAddress + "/boxes/" + part.getPermanentHash());
            int pictureIdx = wb.addPicture(qrImageBytes, Workbook.PICTURE_TYPE_PNG);

            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
            anchor.setCol1(1);
            anchor.setCol2(2);
            anchor.setRow1(rowNum + 1);
            anchor.setRow2(rowNum + 5);

            final Picture picture = drawing.createPicture(anchor, pictureIdx);
            picture.resize();

            CellStyle partNumberStyle = wb.createCellStyle();
            partNumberStyle.setFont(font);

            Row partNumberRow = sh.createRow(rowNum);
            Cell partNumberCell = partNumberRow.createCell(0);
            partNumberCell.setCellValue(part.getPartNumber());
            partNumberCell.setCellStyle(partNumberStyle);

            CellRangeAddress partNumberRegion = new CellRangeAddress(
                    rowNum, //first row (0-based)
                    rowNum, //last row (0-based)
                    0, //first column (0-based)
                    2 //last column (0-based)
            );
            sh.addMergedRegion(partNumberRegion);
            RegionUtil.setBorderTop(BorderStyle.DASH_DOT, partNumberRegion, sh);
            RegionUtil.setBorderBottom(BorderStyle.THIN, partNumberRegion, sh);
            RegionUtil.setBorderLeft(BorderStyle.DASH_DOT, partNumberRegion, sh);
            RegionUtil.setBorderRight(BorderStyle.DASH_DOT, partNumberRegion, sh);

            Row nameRow = sh.createRow(rowNum + 1);
            Cell nameCell = nameRow.createCell(0);
            nameCell.setCellValue(part.getName());

            CellStyle descriptionStyle = wb.createCellStyle();
            descriptionStyle.setAlignment(HorizontalAlignment.LEFT);
            descriptionStyle.setVerticalAlignment(VerticalAlignment.TOP);

            Row descriptionRow = sh.createRow(rowNum + 2);
            Cell descriptionCell = descriptionRow.createCell(0);
            descriptionCell.setCellValue(part.getDescription());
            descriptionCell.setCellStyle(descriptionStyle);

            sh.addMergedRegion(
                    new CellRangeAddress(
                            rowNum + 2, //first row (0-based)
                            rowNum + 6, //last row (0-based)
                            0, //first column (0-based)
                            0 //last column (0-based)
                    )
            );

            Row locationsRow = sh.createRow(rowNum + 7);
            Cell locationsCell = locationsRow.createCell(0);
            Map<Box, Long> entriesByBoxes = part.getStockEntries()
                    .stream()
                    .collect(Collectors.groupingBy(StockEntry::getBox, Collectors.counting()));

            CellRangeAddress locationRegion = new CellRangeAddress(
                    rowNum + 7, //first row (0-based)
                    rowNum + 7, //last row (0-based)
                    0, //first column (0-based)
                    2 //last column (0-based)
            );

            sh.addMergedRegion(locationRegion);
            RegionUtil.setBorderTop(BorderStyle.THIN, locationRegion, sh);
            RegionUtil.setBorderBottom(BorderStyle.DASH_DOT, locationRegion, sh);
            RegionUtil.setBorderLeft(BorderStyle.DASH_DOT, locationRegion, sh);
            RegionUtil.setBorderRight(BorderStyle.DASH_DOT, locationRegion, sh);

            StringBuilder location = new StringBuilder();
            for (Box box : entriesByBoxes.keySet()) {
                location.append(box.getName());
                location.append("(" + entriesByBoxes.get(box) + ")");
            }
            locationsCell.setCellValue(location.toString());

            rowNum = rowNum + 8;
        }
        return workbookToBytes(wb);
    }

    private byte[] qrImageBytes(String value) {
        ByteArrayOutputStream byteArrayOutputStream = QRCode
                .from(value)
                .to(ImageType.PNG)
                .withHint(EncodeHintType.MARGIN, 2)
                .withSize(120, 120)
                .stream();

        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBytes;
    }

    private byte[] workbookToBytes(SXSSFWorkbook workbook) {
        byte[] excelFile = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workbook.write(bos);
            excelFile = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // dispose of temporary files backing this workbook on disk
        workbook.dispose();
        return excelFile;
    }
}
