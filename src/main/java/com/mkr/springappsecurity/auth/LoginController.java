package com.mkr.springappsecurity.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("login")
public class LoginController {

    @GetMapping
    public String login(
        @RequestParam(value = "error", required = false) Boolean paramError,
        @RequestParam(value = "logout", required = false) Boolean paramLogout,
        Model model
    ) {
        if (paramError != null) {
            model.addAttribute("error", paramError);
        }
        if (paramLogout != null) {
            model.addAttribute("logout", paramLogout);
        }
        return "public/login-page";
    }
}
