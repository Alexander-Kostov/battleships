package com.example.battleships.web;

import com.example.battleships.models.dtos.ShipDTO;
import com.example.battleships.models.dtos.StartBattleDTO;
import com.example.battleships.services.AuthService;
import com.example.battleships.services.ShipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {

    private ShipService shipService;

    private AuthService authService;


    public HomeController(ShipService shipService, AuthService authService) {
        this.shipService = shipService;
        this.authService = authService;
    }

    @ModelAttribute("startBattleDTO")
    public StartBattleDTO startBattleDTO() {
        return new StartBattleDTO();
    }


    @GetMapping("/")
    public String loggedOutIndex(){
        if (this.authService.isLoggedIn()) {
            return "redirect:/home";
        }
        return "index";
    }
    @GetMapping("/home")
    public String loggedInIndex(Model model) {
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }

        long loggedUserId = this.authService.getLoggedUserId();
        List<ShipDTO> ownShips = this.shipService.getShipsOwnedBy(loggedUserId);
        List<ShipDTO> enemyShips = this.shipService.getShipsNotOwnedBy(loggedUserId);
        List<ShipDTO> sortedShips = this.shipService.getSortedShips();

        model.addAttribute("ownShips", ownShips);
        model.addAttribute("enemyShips", enemyShips);
        model.addAttribute("sortedShips", sortedShips);

        return "home";
    }


}
