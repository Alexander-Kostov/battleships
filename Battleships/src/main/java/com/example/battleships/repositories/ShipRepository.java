package com.example.battleships.repositories;

import com.example.battleships.models.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShipRepository extends JpaRepository<Ship, Long> {
    Optional<Ship> findByName(String name);

    List<Ship> findByUserId(long ownerId);

    List<Ship> findByUserIdNot(long ownerId);

    List<Ship> findByOrderByHealthAscNameAscPower();
}
