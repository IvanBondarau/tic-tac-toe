package by.ibondarau.tictactoe.battleservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class BadInputFormat extends RuntimeException{
    public BadInputFormat(String message) {
        super(message);
    }

    public BadInputFormat(String message, Throwable cause) {
        super(message, cause);
    }
}
