package by.ibondarau.tictactoe.userservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(fluent = true, chain = true)
public class UserResponseDto {
    private Integer id;
    private String email;
    private Instant created;
}
