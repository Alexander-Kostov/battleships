package com.example.battleships.repositories;

import com.example.battleships.models.Category;
import com.example.battleships.models.enums.ShipType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(ShipType name);
}
