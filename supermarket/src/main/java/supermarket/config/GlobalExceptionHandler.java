package supermarket.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import supermarket.exception.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ExceptionInfo handleException(HttpServletRequest request, Exception ex) {
        LOGGER.error("Responding with generic Internal Error. Exception occurred = {} ", ex.getMessage());
        return new ExceptionInfo(request.getRequestURL().toString(), "Internal Error");
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ExceptionInfo handleProductNotFoundException(HttpServletRequest request, Exception ex) {
        LOGGER.error("Responding with bad request as product could not be found.", ex.getMessage());
        return new ExceptionInfo(request.getRequestURL().toString(), "Bad Request");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ExceptionInfo handleInvalidRequestException(HttpServletRequest request, Exception ex) {
        LOGGER.error("Responding with bad request as request validation failed.", ex.getMessage());
        return new ExceptionInfo(request.getRequestURL().toString(), "Bad Request");
    }

    @AllArgsConstructor
    public class ExceptionInfo {
        @JsonProperty
        private String url;
        @JsonProperty
        private String message;
    }
}
