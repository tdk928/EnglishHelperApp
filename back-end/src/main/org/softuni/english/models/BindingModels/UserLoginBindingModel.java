package org.softuni.english.models.BindingModels;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserLoginBindingModel {
    private static final String USERNAME_MSG = "Username must be between 5 and 20 symbols";
    private static final String PASSWORD_MSG = "Password must be between 7 and 20 symbols";

    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 7;
    private static final int MAX_PASSWORD_LENGTH = 20;

    private String id;

    @NotEmpty
    @Size(min = MIN_USERNAME_LENGTH,max = MAX_USERNAME_LENGTH,message = USERNAME_MSG)
    private String username;

    @NotEmpty
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH, message = PASSWORD_MSG)
    private String password;

    public UserLoginBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
