package com.github.tumerbaatar.storage.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="stock_entry")
public class StockEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private Part part;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Box box;

    // TODO: 27.02.2018 persist with LocalDateTime like in article: https://www.thoughts-on-java.org/persist-localdate-localdatetime-jpa/
    @CreationTimestamp

    private Timestamp creationTimestamp;

    @UpdateTimestamp

    private Timestamp updateTimestamp;

    private String comment;

    private BigDecimal price;

    public StockEntry(Part part, Box box, BigDecimal price, String comment) {
        this.part = part;
        this.box = box;
        this.price = price;
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        return creationTimestamp.hashCode();
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) {
            return true;
        }
        if (!(another instanceof StockEntry)) {
            return false;
        }
        StockEntry stockEntry = (StockEntry) another;
        // TODO 27.02.2018 прояснить вопрос с использованием сгеренрированных полей при сравнении.
        // А сейчас сстаётся уповать на то, что я буду сравнивать только уже сохранённые экземпляры
        return this.id == stockEntry.id || this.creationTimestamp.equals(stockEntry.creationTimestamp);
    }

}
