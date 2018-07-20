package org.softuni.english.models.BindingModels;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserEditBindingModel {
    private static final String USERNAME_MSG = "Username must be between 4 and 20 symbols";
    private static final String PASSWORD_MSG = "Password must be between 4 and 20 symbols";
    private static final String CONFIRM_PASSWORD_MSG = "You must enter same password again.";
    private static final String PHONE_NUMBER_MSG = "Phone number must be exactly 7 numbers.";
    private static final String EMAIL_REGEX_VALIDATION = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.\\[^<>()[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    private static final int MIN_USERNAME_LENGTH = 4;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MAX_PASSWORD_LENGTH = 100;
    private static final int MIN_PHONE_NUMBER_LENGTH = 7;
    private static final int MAX_PHONE_NUMBER_LENGTH = 7;

    @NotEmpty
    private String id;

    @NotEmpty
    @Size(min = MIN_USERNAME_LENGTH,max = MAX_USERNAME_LENGTH,message = USERNAME_MSG)
    private String username;

    @NotEmpty
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH, message = PASSWORD_MSG)
    private String password;

    @NotEmpty
    @Pattern(regexp = EMAIL_REGEX_VALIDATION)
    private String email;

    @NotEmpty
    @Size(min = MIN_PHONE_NUMBER_LENGTH,max = MAX_PHONE_NUMBER_LENGTH,message = PHONE_NUMBER_MSG)
    private String phoneNumber;

    private String experience;

    private boolean isAdmin;

    public UserEditBindingModel() {
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
