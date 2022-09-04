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
        return ResponseEntity.ok(new UserResponseDto(id, "test@test.com", ZonedDateTime.now().toInstant()));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginUserDto loginUserDto) {
        return ResponseEntity.ok(new UserResponseDto(1, loginUserDto.email(), ZonedDateTime.now().toInstant()));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> register(@RequestBody InsertUserDto insertUserDto) {
        return ResponseEntity.ok(new UserResponseDto(1, insertUserDto.email(), ZonedDateTime.now().toInstant()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Integer id, @RequestBody InsertUserDto insertUserDto) {
        return ResponseEntity.ok(new UserResponseDto(id, insertUserDto.email(), ZonedDateTime.now().toInstant()));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
    }

}
