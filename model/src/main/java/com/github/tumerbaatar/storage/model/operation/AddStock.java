package com.github.tumerbaatar.storage.model.operation;

import com.github.tumerbaatar.storage.model.Storage;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.github.tumerbaatar.storage.model.Box;
import com.github.tumerbaatar.storage.model.Part;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Immutable
@NoArgsConstructor
@Entity
@Table(name = "add_stock")
public class AddStock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "part_id")
    private Part part;
    @OneToOne
    @JoinColumn(name = "box_id")
    private Box box;
    private int quantity;
    private BigDecimal price;
    private String comment;
    @CreationTimestamp
    private Timestamp timestamp;
    @OneToOne
    @JoinColumn(name = "storage_slug")
    private Storage storage;

    public AddStock(Storage storage, Part part, Box box, int quantityToAdd, BigDecimal price, String comment) {
        this.storage = storage;
        this.part = part;
        this.box = box;
        this.quantity = quantityToAdd;
        this.price = price;
        this.comment = comment;
    }
}
