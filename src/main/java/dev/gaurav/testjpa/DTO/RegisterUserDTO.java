package dev.gaurav.testjpa.DTO;

import dev.gaurav.testjpa.models.User;

public record RegisterUserDTO(String username, String email, String name, User.Gender gender, String password) {
}
