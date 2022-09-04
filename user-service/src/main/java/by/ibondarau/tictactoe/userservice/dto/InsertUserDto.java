package by.ibondarau.tictactoe.userservice.dto;

import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
public record InsertUserDto(String email, String password) {
}
