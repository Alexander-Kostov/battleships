package com.example.battleships.web;

import com.example.battleships.models.dtos.StartBattleDTO;
import com.example.battleships.services.AuthService;
import com.example.battleships.services.BattleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BattleController {

    private BattleService battleService;

    private AuthService authService;

    public BattleController(BattleService battleService, AuthService authService) {
        this.battleService = battleService;
        this.authService = authService;
    }

    @PostMapping("/battle")
    public String battle(@Valid StartBattleDTO startBattleDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("startBattleDTO", startBattleDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.startBattleDTO", bindingResult);

            return "redirect:/home";
        }

        this.battleService.battle(startBattleDTO);

        return "redirect:/home";
    }
}
