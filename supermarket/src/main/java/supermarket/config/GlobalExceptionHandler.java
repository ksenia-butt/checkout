package supermarket.config;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionInfo handleException(HttpServletRequest request, Exception ex){
        LOGGER.error("Responding with generic Internal Error. Exception occurred = {} ", ex.getMessage());
        return new ExceptionInfo(request.getRequestURL().toString(), "Internal Error");
    }

    @AllArgsConstructor
    private class ExceptionInfo {
        private String url;
        private String messsage;
    }
}
