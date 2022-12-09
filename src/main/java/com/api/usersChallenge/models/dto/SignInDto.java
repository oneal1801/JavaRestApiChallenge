package com.api.usersChallenge.models.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
    @JsonProperty("email")
    String email;
    @JsonProperty("password")
    String password;
}
