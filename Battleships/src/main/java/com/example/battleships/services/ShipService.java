package com.example.battleships.services;

import com.example.battleships.models.Category;
import com.example.battleships.models.Ship;
import com.example.battleships.models.User;
import com.example.battleships.models.dtos.CreateShipDTO;
import com.example.battleships.models.dtos.ShipDTO;
import com.example.battleships.models.enums.ShipType;
import com.example.battleships.repositories.CategoryRepository;
import com.example.battleships.repositories.ShipRepository;
import com.example.battleships.repositories.UserRepository;
import com.example.battleships.session.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipService {

    private ShipRepository shipRepository;
    private LoggedUser userSession;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    public ShipService(ShipRepository shipRepository, LoggedUser userSession, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.shipRepository = shipRepository;
        this.userSession = userSession;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public boolean create(CreateShipDTO createShipDTO) {

        Optional<Ship> byName = this.shipRepository.findByName(createShipDTO.getName());

        if (byName.isPresent()) {
            return false;
        }

        Ship ship = new Ship();

        ShipType type = switch (createShipDTO.getCategory()){
            case 0 -> ShipType.BATTLE;
            case 1 -> ShipType.CARGO;
            case 2 -> ShipType.PATROL;
            default -> ShipType.BATTLE;
        };

        Category category = this.categoryRepository.findByName(type);
        Optional<User> user = this.userRepository.findById(this.userSession.getId());

        ship.setName(createShipDTO.getName());
        ship.setPower(createShipDTO.getPower());
        ship.setHealth(createShipDTO.getHealth());
        ship.setCreated(createShipDTO.getCreated());
        ship.setCategory(category);

        ship.setUser(user.get());

        this.shipRepository.save(ship);

        return true;
    }

    public List<ShipDTO> getShipsOwnedBy(long ownerId) {
        return this.shipRepository.findByUserId(ownerId)
                .stream().map(ShipDTO::new)
                .collect(Collectors.toList());
    }

    public List<ShipDTO> getShipsNotOwnedBy(long ownerId) {
        return this.shipRepository.findByUserIdNot(ownerId)
                .stream().map(ShipDTO::new)
                .collect(Collectors.toList());
    }

    public List<ShipDTO> getSortedShips() {
        return this.shipRepository.findByOrderByHealthAscNameAscPower()
                .stream().map(ShipDTO::new)
                .collect(Collectors.toList());
    }

}
