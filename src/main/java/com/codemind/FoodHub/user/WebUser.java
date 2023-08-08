package com.codemind.FoodHub.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WebUser {

    @NotNull(message = " must be in alphabetic and at least 5 character long")
    @Size(min = 1,message = "is required")
    @Pattern(regexp = "[a-zA-Z]{5,}")
    private String userName;

    @NotNull(message = " should be at least 5 character long and at least one uppercase and lowercase and Special character")
    @Size(min = 1,message = "is required")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{5,}$")
    private String password;

    @NotNull(message = " should be in alphabetic format")
    @Size(min = 1, message = "is required")
    @Pattern(regexp = "[a-zA-Z\\s]+")
    private String firstName;

    @NotNull(message = " should be in alphabetic format")
    @Size(min = 1, message = "is required")
    @Pattern(regexp = "[a-zA-Z\\s]+")
    private String lastName;

    @NotNull(message = " should be in valid format including @ notation")
    @Size(min = 1,message = "is required")
    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;
}
