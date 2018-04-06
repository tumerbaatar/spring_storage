package com.github.tumerbaatar.storage.model.operation;

import com.github.tumerbaatar.storage.model.Box;
import com.github.tumerbaatar.storage.model.Part;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Immutable
@Entity
@Table(name = "move_stock")
public class MoveStock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    @JoinColumn(name = "part_id")
    private Part part;
    @OneToOne
    @JoinColumn(name = "from_box_id")
    private Box boxFrom;
    @OneToOne
    @JoinColumn(name = "to_box_id")
    private Box boxTo;
    private int quantity;
    @CreationTimestamp
    private Timestamp creationTimestamp;

    public MoveStock(Part part, Box boxFrom, Box boxTo, int quantityToMove) {
        this.part = part;
        this.boxFrom = boxFrom;
        this.boxTo = boxTo;
        this.quantity = quantityToMove;
    }
}