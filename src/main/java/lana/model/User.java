package lana.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class User implements Validator {
    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        String username = user.getUsername();
        String password = user.getPassword();
        ValidationUtils.rejectIfEmpty(errors,"password","empty");
        ValidationUtils.rejectIfEmpty(errors,"username","empty");
        if (username.length()<3){
            errors.rejectValue("username","short");
        }
        if (password.length()<3){
            errors.rejectValue("password","short");
        }

    }
}
