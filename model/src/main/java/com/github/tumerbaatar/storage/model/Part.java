package com.github.tumerbaatar.storage.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.tumerbaatar.storage.model.util.PersistedEntityIdResolver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.github.tumerbaatar.storage.model.util.StockEntriesSerializer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"stockEntries"}, allowGetters = true, ignoreUnknown = true)
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String partNumber; // Unique identifier for search
    private String name;
    private String description;
    private String manufacturer;
    private String manufacturerPartNumber;
    private String condition; // band-new, attrition, etc.
    private BigDecimal price;
    private String permanentHash;
    @CreationTimestamp
    private Timestamp creationTimestamp;
    @UpdateTimestamp
    private Timestamp updateTimestamp;
    @JsonSerialize(using = StockEntriesSerializer.class)
    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockEntry> stockEntries = new ArrayList<>();
    @ElementCollection
    @CollectionTable(
            name="part_images_paths",
            joinColumns = @JoinColumn(name="part_id")
    )
    private List<String> images = new ArrayList<>();
    @ElementCollection
    @CollectionTable(
            name="part_file_attachments_paths",
            joinColumns = @JoinColumn(name="part_id")
    )
    private List<String> fileAttachments = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name="storage_slug")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "slug",
            resolver = PersistedEntityIdResolver.class,
            scope = Storage.class
    )
    @JsonIdentityReference(alwaysAsId = true)
    private Storage storage;

    public Part(String partNumber) {
        this.partNumber = partNumber;
    }


    public void addStockEntry(StockEntry stockEntry) {
        this.stockEntries.add(stockEntry);
        stockEntry.setPart(this);
    }

    public void removeStockEntry(StockEntry stockEntry) {
        this.stockEntries.remove(stockEntry);
        stockEntry.setPart(null);
    }

    @Override
    public boolean equals(Object another) {
        if (another == null || !(another instanceof Part)) {
            return false;
        }
        Part part = (Part) another;
        return part == this || this.partNumber.equals(part.partNumber);
    }

    @Override
    public int hashCode() {
        return this.partNumber.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Part {%d, %s, entries amount: %d}", id, name, stockEntries.size());
    }
}
