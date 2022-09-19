package by.ibondarau.tictactoe.userservice.controller;

import by.ibondarau.tictactoe.userservice.dto.InsertUserDto;
import by.ibondarau.tictactoe.userservice.dto.LoginUserDto;
import by.ibondarau.tictactoe.userservice.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> get(@PathVariable Integer id) {
        UserResponseDto userResponseDto = new UserResponseDto()
                .id(id)
                .email("test@test.com")
                .created(ZonedDateTime.now().toInstant());

        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginUserDto loginUserDto) {
        UserResponseDto userResponseDto = new UserResponseDto()
                .id(1)
                .email(loginUserDto.email())
                .created(ZonedDateTime.now().toInstant());

        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> register(@RequestBody InsertUserDto insertUserDto) {
        UserResponseDto userResponseDto = new UserResponseDto()
                .id(1)
                .email(insertUserDto.email())
                .created(ZonedDateTime.now().toInstant());

        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Integer id, @RequestBody InsertUserDto insertUserDto) {
        UserResponseDto userResponseDto = new UserResponseDto()
                .id(id)
                .email(insertUserDto.email())
                .created(ZonedDateTime.now().toInstant());

        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
    }

}
