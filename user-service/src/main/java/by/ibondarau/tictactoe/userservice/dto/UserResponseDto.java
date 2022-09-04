package by.ibondarau.tictactoe.userservice.dto;

import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Instant;

@ConstructorBinding
public record UserResponseDto(Integer id, String email, Instant created) {
}
