package chatserver.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User with such name already exists")
public class UserAlreadyExistsException extends RuntimeException {
}
