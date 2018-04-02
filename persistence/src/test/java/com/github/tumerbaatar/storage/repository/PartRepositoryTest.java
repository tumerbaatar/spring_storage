package com.github.tumerbaatar.storage.persistence.repository;

import com.github.tumerbaatar.storage.persistence.model.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.Assert.assertEquals;

//@RunWith(SpringRunner.class)
//@DataJpaTest
public class PartRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PartRepository repository;

//    @Test
    public void creationTest() {
        String partNumber = "DC92302";
        Part cooler = new Part(partNumber);

        String permanentHash = "Some hash for QR";
        cooler.setPermanentHash(permanentHash);

        entityManager.persist(cooler);

        Part stored = repository.findByPermanentHash(permanentHash);
        assertEquals(cooler.getPermanentHash(), stored.getPermanentHash());
    }

}