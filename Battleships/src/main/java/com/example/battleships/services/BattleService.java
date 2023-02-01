package com.example.battleships.services;

import com.example.battleships.models.Ship;
import com.example.battleships.models.dtos.StartBattleDTO;
import com.example.battleships.repositories.ShipRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BattleService {

    private ShipRepository shipRepository;

    public BattleService(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    public void battle(StartBattleDTO startBattleDTO){
        Optional<Ship> attackerOpt = this.shipRepository.findById(startBattleDTO.getAttackerId());
        Optional<Ship> defenderOpt = this.shipRepository.findById(startBattleDTO.getDefenderId());

        if(attackerOpt.isEmpty() || defenderOpt.isEmpty()) {
            throw new NoSuchElementException();
        }

        Ship attacker = attackerOpt.get();
        Ship defender = defenderOpt.get();

        long defenderNewHealth = defender.getHealth() - attacker.getPower();

        if(defenderNewHealth <= 0) {
            this.shipRepository.delete(defender);
        }else {
            defender.setHealth(defenderNewHealth);
            this.shipRepository.save(defender);
        }
    }
}
