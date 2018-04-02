package com.github.tumerbaatar.storage.repository.operation;

import com.github.tumerbaatar.storage.model.Box;
import com.github.tumerbaatar.storage.model.Part;
import com.github.tumerbaatar.storage.model.operation.RemoveStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.Assert.assertEquals;

//@RunWith(SpringRunner.class)
//@DataJpaTest
public class RemoveStockRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RemoveStockRepository removeStockRepository;

    //    @Test
    public void creationTest() throws Exception {
        String partNumber = "DC92302";
        Part part = new Part(partNumber);
        String permanentHash = "Some hash for QR";
        part.setPermanentHash(permanentHash);

        Box box = entityManager.persist(new Box("box-a"));
        part = entityManager.persist(part);

        int quantityToRemove = 10;

        RemoveStock removeStock = new RemoveStock(part, box, quantityToRemove);

        removeStock.setPart(part);
        removeStock.setFromBox(box);
        removeStock.setQuantity(quantityToRemove);

        RemoveStock persistedRemoveStock = entityManager.persist(removeStock);
        RemoveStock foundRemoveStock = removeStockRepository
                .findById(persistedRemoveStock.getId())
                .orElseThrow(() -> new Exception("Remove stock entity not found"));

        assertEquals(persistedRemoveStock.getPart(), foundRemoveStock.getPart());
        assertEquals(part, persistedRemoveStock.getPart());
        assertEquals(box, persistedRemoveStock.getFromBox());
    }
}