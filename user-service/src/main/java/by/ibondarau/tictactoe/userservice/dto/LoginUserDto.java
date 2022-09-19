package by.ibondarau.tictactoe.userservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class LoginUserDto {
    private String email;
    private String password;

}
