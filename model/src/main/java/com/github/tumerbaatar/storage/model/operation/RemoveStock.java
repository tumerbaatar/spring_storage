package com.github.tumerbaatar.storage.model.operation;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.github.tumerbaatar.storage.model.Box;
import com.github.tumerbaatar.storage.model.Part;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Immutable
@NoArgsConstructor
@Entity
@Table(name="remove_stock")
public class RemoveStock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    @JoinColumn(name = "part_id")
    private Part part;
    @OneToOne
    @JoinColumn(name = "from_box_id")
    private Box fromBox;
    private int quantity;

    @CreationTimestamp

    private Timestamp creationTimestamp;

    public RemoveStock(Part part, Box fromBox, int quantityToRemove) {
        this.part = part;
        this.fromBox = fromBox;
        this.quantity = quantityToRemove;
    }
}
