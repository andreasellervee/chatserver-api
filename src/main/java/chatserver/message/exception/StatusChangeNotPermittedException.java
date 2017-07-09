package chatserver.message.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Message status change not permitted")
public class StatusChangeNotPermittedException extends RuntimeException {
}
