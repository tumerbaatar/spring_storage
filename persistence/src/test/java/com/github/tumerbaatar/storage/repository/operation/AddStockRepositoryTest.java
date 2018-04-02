package com.github.tumerbaatar.storage.persistence.repository.operation;

import com.github.tumerbaatar.storage.persistence.model.Box;
import com.github.tumerbaatar.storage.persistence.model.Part;
import com.github.tumerbaatar.storage.persistence.model.operation.AddStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

//@RunWith(SpringRunner.class)
//@DataJpaTest
public class AddStockRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddStockRepository addStockRepository;

//    @Test
    public void creationTest() throws Exception {
        String partNumber = "DC92302";
        Part part = new Part(partNumber);
        String permanentHash = "Some hash for QR";
        part.setPermanentHash(permanentHash);

        Box box = entityManager.persist(new Box("box-a"));
        part = entityManager.persist(part);

        int quantityToAdd = 10;
        BigDecimal price = new BigDecimal(10.01);
        String comment = "comment";

        AddStock addStock = addStockRepository.save(new AddStock(part, box, quantityToAdd, price, comment));

        assertNotEquals(0, addStock.getId());
        assertEquals(part, addStock.getPart());
        assertEquals(box, addStock.getBox());
        assertEquals(price, addStock.getPrice());
        assertEquals(quantityToAdd, addStock.getQuantity());

    }
}