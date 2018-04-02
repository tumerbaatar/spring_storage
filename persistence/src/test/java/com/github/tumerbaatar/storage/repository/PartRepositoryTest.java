package com.github.tumerbaatar.storage.repository;

import com.github.tumerbaatar.storage.model.Part;
import com.github.tumerbaatar.storage.repository.PartRepository;
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
    public void creationTest() throws Exception {
        String partNumber = "DC92302";
        Part cooler = new Part(partNumber);

        String permanentHash = "Some hash for QR";
        cooler.setPermanentHash(permanentHash);

        entityManager.persist(cooler);

        Part stored = repository.findByPermanentHash(permanentHash).orElseThrow(() ->new Exception("part not found"));
        assertEquals(cooler.getPermanentHash(), stored.getPermanentHash());
    }

}