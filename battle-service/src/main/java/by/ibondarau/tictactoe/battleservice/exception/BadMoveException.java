package by.ibondarau.tictactoe.battleservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class BadMoveException extends BusinessException {
    public BadMoveException(String message) {
        super(message);
    }

    public BadMoveException(String message, Throwable cause) {
        super(message, cause);
    }
}
