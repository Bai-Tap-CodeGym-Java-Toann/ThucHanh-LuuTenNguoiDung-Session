package lana.controllers;

import lana.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @GetMapping("/")
    public ModelAndView rootHandle() {
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/login")
    public ModelAndView toLoginPage() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView accountHandle(@ModelAttribute("user") User user, BindingResult bindingResult) {
        user.validate(user, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("login");
        }
        if (user.getUsername().equals("admin") ||
                user.getPassword().equals("123456")) {
            return new ModelAndView("success");
        }
        String log = "wrong username or password";
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("log", log);
        return modelAndView;
    }


}
