package lana.controllers;

import lana.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    @ModelAttribute
    public User setUserForm() {
        return new User();
    }

    @GetMapping("/")
    public ModelAndView rootHandle() {
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/login")
    public ModelAndView toLoginPage(@CookieValue(value = "usern", defaultValue = "") String usern,
                                    @CookieValue(value = "userp", defaultValue = "") String userp,
                                    HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("usern", usern);
        modelAndView.addObject("userp", userp);
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView accountHandle(@ModelAttribute("user") User user,
                                      BindingResult bindingResult,
                                      @CookieValue(value = "usern", defaultValue = "") String usern,
                                      @CookieValue(value = "userp", defaultValue = "") String userp,
                                      HttpServletRequest request, HttpServletResponse response) {

        user.validate(user, bindingResult);
        ModelAndView modelAndView = new ModelAndView("login");

        if (bindingResult.hasFieldErrors()) {
            return modelAndView;
        }
        if (user.getUsername().equals("admin") &&
                user.getPassword().equals("123456")) {

            Cookie username = new Cookie("usern", user.getUsername());
            username.setMaxAge(24 * 60 * 60);
            response.addCookie(username);

            Cookie password = new Cookie("userp", user.getPassword());
            password.setMaxAge(24 * 60 * 60);
            response.addCookie(password);

            return new ModelAndView("success");
        }
        String log = "wrong username or password";
        modelAndView.addObject("log", log);
        return modelAndView;
    }


}
