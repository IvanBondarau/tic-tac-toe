package by.ibondarau.tictactoe.userservice.dto;

import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
public record LoginUserDto(String email, String password) {
}
