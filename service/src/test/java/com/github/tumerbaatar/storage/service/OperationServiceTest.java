package com.github.tumerbaatar.storage.service;

import com.github.tumerbaatar.storage.model.Box;
import com.github.tumerbaatar.storage.model.Part;
import com.github.tumerbaatar.storage.model.StockEntry;
import com.github.tumerbaatar.storage.model.Storage;
import com.github.tumerbaatar.storage.model.operation.AddStock;
import com.github.tumerbaatar.storage.model.operation.MoveStock;
import com.github.tumerbaatar.storage.model.operation.RemoveStock;
import com.github.tumerbaatar.storage.repository.BoxRepository;
import com.github.tumerbaatar.storage.repository.PartRepository;
import com.github.tumerbaatar.storage.repository.StockEntryRepository;
import com.github.tumerbaatar.storage.repository.operation.AddStockRepository;
import com.github.tumerbaatar.storage.repository.operation.MoveStockRepository;
import com.github.tumerbaatar.storage.repository.operation.RemoveStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@Slf4j
//@RunWith(SpringRunner.class)
public class OperationServiceTest {

    @MockBean
    private PartRepository partRepository;
    @MockBean
    private BoxRepository boxRepository;
    @MockBean
    private AddStockRepository addStockRepository;
    @MockBean
    private MoveStockRepository moveStockRepository;
    @MockBean
    private RemoveStockRepository removeStockRepository;
    @MockBean
    private StockEntryRepository stockEntryRepository;
    @InjectMocks
    private OperationService operationService;
    @InjectMocks
    private PartService partService;

    private Storage storage;
    private Part part;
    private Box boxOne;
    private Box boxTwo;
    private final int QUANTITY = 10;
    private final BigDecimal PRICE = new BigDecimal(10.01);
    private final String COMMENT = "some comment";
    private long sequenceProvider = 1;

    @Before
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);

        storage = new Storage("storageSlug", "storageName");

        String partNumber = "DC280009KS0";
        Long partId = 1001L;
        part = new Part(partNumber);
        part.setId(partId);
        part.setPrice(new BigDecimal(300));
        when(partRepository.save(part)).thenReturn(part);
//        when(partRepository.findById(partId)).thenReturn(part);

        String boxToAddName = "Box_to_add_name";
        long boxId = 2001L;
        String permanentHash = "someBoxHash";
        boxOne = new Box(boxToAddName);
        boxOne.setId(boxId);
        boxOne.setPermanentHash(permanentHash);
        when(boxRepository.save(boxOne)).thenReturn(boxOne);
//        when(boxRepository.findById(boxId)).thenReturn(boxOne);

        String box2name = "Box #2";
        long boxTwoId = 2002L;
        String box2permanentHash = "box2permanentHash";
        boxTwo = new Box(box2name);
        boxTwo.setId(boxTwoId);
        boxTwo.setPermanentHash(box2permanentHash);
        when(boxRepository.save(boxTwo)).thenReturn(boxTwo);
//        when(boxRepository.findById(boxTwoId)).thenReturn(boxTwo);

    }

    @After
    public void afterEach() {
        part = null;
        boxOne = null;
    }

    private void populateEntrySet(Part part, Box box) {
        for (int i = 0; i < QUANTITY; i++) {
            StockEntry entry = new StockEntry(part, box, PRICE, COMMENT);
            entry.setId(300 + sequenceProvider++);
            entry.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
            part.addStockEntry(entry);
            box.addStockEntry(entry);
        }
    }

    //    @Test
    public void addStock() {
        for (int i = 0; i < QUANTITY; i++) {
            StockEntry entry = new StockEntry(part, boxOne, PRICE, COMMENT);
            entry.setId((long) i);
            entry.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
            when(stockEntryRepository.save(entry)).thenReturn(entry);
        }

        AddStock addStock = new AddStock(storage, part, boxOne, QUANTITY, PRICE, COMMENT);

        when(addStockRepository.save(addStock)).thenReturn(addStock);
        AddStock addStockAnswer = operationService.addStock(addStock);

        log.info("Stock entries quantity " + addStock.getPart().getStockEntries().size());
        log.info("Persisted stock entries quantity " + addStockAnswer.getPart().getStockEntries().size());
        assertEquals(QUANTITY, addStockAnswer.getPart().getStockEntries().size());
        assertEquals(QUANTITY, addStockAnswer.getBox().getStockEntries().size());
        assertEquals(boxOne, addStockAnswer.getBox());
    }

    //    @Test
    public void moveStock() {
        populateEntrySet(part, boxOne);
        populateEntrySet(part, boxTwo);

        int quantityToMove = 3;
        MoveStock moveStock = new MoveStock(part, boxOne, boxTwo, quantityToMove);
        when(moveStockRepository.save(moveStock)).thenReturn(moveStock);

        MoveStock moveStockAnswer = operationService.moveStock(moveStock);

        int remainQuantityInBoxOne = QUANTITY - quantityToMove;
        int presentQuantityInBoxTwo = QUANTITY + quantityToMove;

        List<StockEntry> boxFromEntries = moveStockAnswer.getBoxFrom().getStockEntries()
                .stream()
                .filter(e -> e.getBox().equals(boxOne))
                .collect(Collectors.toList());

        List<StockEntry> boxToEntries = moveStockAnswer.getBoxTo().getStockEntries()
                .stream()
                .filter(e -> e.getBox().equals(boxTwo))
                .collect(Collectors.toList());

        assertEquals(remainQuantityInBoxOne, boxFromEntries.size());
        assertEquals(presentQuantityInBoxTwo, boxToEntries.size());
        assertEquals(remainQuantityInBoxOne + presentQuantityInBoxTwo, part.getStockEntries().size());
    }

    //    @Test
    public void removeStock() {
        populateEntrySet(part, boxOne);
        populateEntrySet(part, boxTwo);

        int quantityToRemove = 4;

        RemoveStock removeStock = new RemoveStock(part, boxOne, quantityToRemove);

//        when(partRepository.findById(part.getId())).thenReturn(part);
//        when(boxRepository.findById(boxOne.getId())).thenReturn(boxOne);
        when(removeStockRepository.save(removeStock)).thenReturn(removeStock);

        RemoveStock removeStockAnswer = operationService.removeStock(removeStock);

        List<StockEntry> entriesInBoxOne = removeStockAnswer.getPart().getStockEntries()
                .stream()
                .filter(e -> e.getBox().equals(boxOne))
                .collect(Collectors.toList());

        assertEquals(QUANTITY - quantityToRemove, entriesInBoxOne.size());
    }


}