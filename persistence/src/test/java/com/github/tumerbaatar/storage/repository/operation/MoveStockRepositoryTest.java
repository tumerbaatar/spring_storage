package com.github.tumerbaatar.storage.repository.operation;

import com.github.tumerbaatar.storage.model.Box;
import com.github.tumerbaatar.storage.model.Part;
import com.github.tumerbaatar.storage.model.Storage;
import com.github.tumerbaatar.storage.model.operation.MoveStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

//@RunWith(SpringRunner.class)
//@DataJpaTest
public class MoveStockRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MoveStockRepository moveStockRepository;

//    @Test
    public void creationTest() throws Exception {
        String partNumber = "DC92302";
        Part part = new Part(partNumber);
        String permanentHash = "Some hash for QR";
        part.setPermanentHash(permanentHash);

        Box fromBox = entityManager.persist(new Box("box-a"));
        Box toBox = entityManager.persist(new Box("box-b"));
        part = entityManager.persist(part);

        int quantityToMove = 10;

        MoveStock moveStock = moveStockRepository.save(new MoveStock(part, fromBox, toBox, quantityToMove));

        assertNotEquals(0, moveStock.getId());
        assertEquals(part, moveStock.getPart());
        assertEquals(fromBox, moveStock.getBoxFrom());
        assertEquals(toBox, moveStock.getBoxTo());
    }
}


