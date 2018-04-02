package org.github.tumerbaatar.storage.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.tumerbaatar.storage.service.exceptions.BoxNotFoundException;
import org.github.tumerbaatar.storage.service.exceptions.PartNotFoundException;
import org.github.tumerbaatar.storage.model.Box;
import org.github.tumerbaatar.storage.model.Part;
import org.github.tumerbaatar.storage.model.StockEntry;
import org.github.tumerbaatar.storage.model.operation.AddStock;
import org.github.tumerbaatar.storage.model.operation.MoveStock;
import org.github.tumerbaatar.storage.model.operation.RemoveStock;
import org.github.tumerbaatar.storage.repository.BoxRepository;
import org.github.tumerbaatar.storage.repository.PartRepository;
import org.github.tumerbaatar.storage.repository.StockEntryRepository;
import org.github.tumerbaatar.storage.repository.operation.AddStockRepository;
import org.github.tumerbaatar.storage.repository.operation.MoveStockRepository;
import org.github.tumerbaatar.storage.repository.operation.RemoveStockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class OperationService {
    private PartRepository partRepository;
    private BoxRepository boxRepository;
    private AddStockRepository addStockRepository;
    private MoveStockRepository moveStockRepository;
    private RemoveStockRepository removeStockRepository;
    private StockEntryRepository stockEntryRepository;

    /**
     * В указанную коробку добавляется запчасть в указанном количестве и с назначенной стоимостью за каждую запчасть.
     *
     * @param addStockOperation DTO для добавления запчасти. Получается по REST
     * @return изменённый DTO с заполненными полями
     */
    @Transactional
    public AddStock addStock(AddStock addStockOperation) {
        log.info("Add stock operation requested: " + addStockOperation.toString());

        Part part = partRepository.findById(addStockOperation.getPart().getId()).orElseThrow(PartNotFoundException::new);
        Box box = boxRepository.findById(addStockOperation.getBox().getId()).orElseThrow(BoxNotFoundException::new);
        int quantity = addStockOperation.getQuantity();
        BigDecimal price = addStockOperation.getPrice();
        String comment = addStockOperation.getComment();

        for (int i = 0; i < quantity; i++) {
            StockEntry stockEntry = stockEntryRepository.save(new StockEntry(part, box, price, comment));
            part.addStockEntry(stockEntry);
            box.addStockEntry(stockEntry);
            stockEntryRepository.save(stockEntry);
            log.debug("Stock entry created: " + stockEntry.toString());
        }

        boxRepository.save(box);
        partRepository.save(part);
        addStockOperation.setPart(part);
        addStockOperation.setBox(box);
        return addStockRepository.save(addStockOperation);
    }

    @Transactional
    public MoveStock moveStock(MoveStock moveStock) {
        int quantityToMove = moveStock.getQuantity();
        Part part = partRepository.findById(moveStock.getPart().getId()).orElseThrow(PartNotFoundException::new);
        Box boxFrom =boxRepository.findById(moveStock.getBoxFrom().getId()).orElseThrow(BoxNotFoundException::new);
        Box boxTo = boxRepository.findById(moveStock.getBoxTo().getId()).orElseThrow(BoxNotFoundException::new);

        part.getStockEntries()
                .stream()
                .filter(e -> e.getBox().equals(boxFrom))
                .limit(quantityToMove)
                .forEach(e -> {
                            boxFrom.removeStockEntry(e);
                            boxTo.addStockEntry(e);
                        }
                );

        moveStock.setPart(part);
        moveStock.setBoxFrom(boxFrom);
        moveStock.setBoxTo(boxTo);
        moveStock = moveStockRepository.save(moveStock);
        log.warn("Move stock " + moveStock.toString());
        return moveStock;
    }

    @Transactional
    public RemoveStock removeStock(RemoveStock stockRemove) {
        Part part = partRepository.findById(stockRemove.getPart().getId()).orElseThrow(PartNotFoundException::new);
        Box fromBox = boxRepository.findById(stockRemove.getFromBox().getId()).orElseThrow(BoxNotFoundException::new);
        int quantity = stockRemove.getQuantity();

        final List<StockEntry> stockEntries = new ArrayList<>(part.getStockEntries());

        stockEntries
                .stream()
                .filter(e -> e.getBox().equals(fromBox))
                .limit(quantity)
                .forEach(e -> {
                    fromBox.removeStockEntry(e);
                    part.removeStockEntry(e);
                });

        stockRemove.setPart(part);
        stockRemove.setFromBox(fromBox);
        return removeStockRepository.save(stockRemove);
    }

}
