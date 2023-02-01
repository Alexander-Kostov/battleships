package com.example.battleships.models;

import com.example.battleships.models.enums.ShipType;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(unique = true, nullable = false)
    private ShipType name;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Category(ShipType name) {
        this.name = name;
    }
    public Category() {}

    public long getId() {
        return id;
    }

    public Category setId(long id) {
        this.id = id;
        return this;
    }

    public ShipType getShipType() {
        return name;
    }

    public Category setShipType(ShipType shipType) {
        this.name = shipType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Category setDescription(String description) {
        this.description = description;
        return this;
    }

    public ShipType getName() {
        return name;
    }

    public Category setName(ShipType name) {
        this.name = name;
        return this;
    }


}
