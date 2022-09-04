package by.ibondarau.tictactoe.battleservice.controller.dto;

public record MoveDto(
        Integer playerId,
        Integer x,
        Integer y
) { }
