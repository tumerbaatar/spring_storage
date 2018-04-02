package com.github.tumerbaatar.storage.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.tumerbaatar.storage.model.operation.AddStock;
import org.github.tumerbaatar.storage.model.operation.MoveStock;
import org.github.tumerbaatar.storage.model.operation.RemoveStock;
import com.github.tumerbaatar.storage.service.OperationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/stock/parts")
public class StockController {
    private OperationService operationService;

    @PostMapping("/add")
    public AddStock setPartLocation(@RequestBody AddStock addStock) {
        return operationService.addStock(addStock);
    }

    @PostMapping("/remove")
    public RemoveStock removeStock(@RequestBody RemoveStock removeStock) {
        return operationService.removeStock(removeStock);
    }

    @PostMapping("/move")
    public MoveStock moveStock(@RequestBody MoveStock moveStock) {
        return operationService.moveStock(moveStock);
    }
}