package com.github.tumerbaatar.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.github.tumerbaatar.storage.model.util.StockEntriesSerializer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(value = {"stockEntries"}, allowGetters = true)
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String permanentHash;

    @JsonSerialize(using = StockEntriesSerializer.class)
    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockEntry> stockEntries = new LinkedList<>();

    private boolean singlePartBox;

    @CreationTimestamp
    private Timestamp creationTimestamp;
    @UpdateTimestamp
    private Timestamp updateTimestamp;

    @ManyToOne
    @JoinColumn(name="storage_id")
    private Storage storage;

    public Box(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return permanentHash.hashCode();
    }

    @Override
    public boolean equals(Object another) {
        if (another == null || !(another instanceof Box)) {
            return false;
        }
        Box box = (Box) another;
        return box == this || this.permanentHash.equals(box.permanentHash);
    }

    @Override
    public String toString() {
        return String.format("Box {%d, %s, entries amount: %d}", id, name, stockEntries.size());
    }

    public void addStockEntry(StockEntry stockEntry) {
        this.stockEntries.add(stockEntry);
        stockEntry.setBox(this);
    }

    public void removeStockEntry(StockEntry stockEntry) {
        this.stockEntries.remove(stockEntry);
        stockEntry.setBox(null);
    }

}
