package com.example.battleships.web;

import com.example.battleships.models.dtos.CreateShipDTO;
import com.example.battleships.services.AuthService;
import com.example.battleships.services.ShipService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ShipController {

    private ShipService shipService;

    private AuthService authService;

    public ShipController(ShipService shipService, AuthService authService) {
        this.shipService = shipService;
        this.authService = authService;
    }

    @ModelAttribute("CreateShipDTO")
    public CreateShipDTO createShipDTO(){
        return new CreateShipDTO();
    }

    @GetMapping("/ships/add")
    public String addShip() {
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }

        return "ship-add";
    }


    @PostMapping("/ships/add")
    public String addShip(@Valid CreateShipDTO createShipDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors() || !this.shipService.create(createShipDTO)) {
            redirectAttributes.addFlashAttribute("CreateShipDTO", createShipDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.CreateShipDTO", bindingResult);

            return "redirect:/ships/add";
        }


        return "redirect:/home";
    }
}
