package com.zui.vorite.config;

import com.zui.vorite.pojo.ErrorResponseEntity;
import org.jboss.logging.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 全局异常
 * @author Dusk
 * @since  2018/11/25
 */

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    Logger logger=Logger.getLogger(GlobalExceptionHandler.class);

    /**
     * 表单格式异常
     * @param ex
     * @param status
     * @param request
     * @return
     */

    @Override
    public ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrorList = ex.getBindingResult().getFieldErrors();
        List<ErrorResponseEntity> errorResponseEntityList = fieldErrorList.stream().map(f->new ErrorResponseEntity(status.value(), f.getField(), f.getDefaultMessage())).collect(toList());
        return new ResponseEntity<>(errorResponseEntityList, status);
    }

    /*@ExceptionHandler(RuntimeException.class)
    public ErrorResponseEntity runtimeExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        RuntimeException exception = (RuntimeException) e;
        return new ErrorResponseEntity(400, exception.getMessage());
    }*/

/*   public Map<String, Object> handleBindException(BindException ex){
        FieldError fieldError = ex.getBindingResult().getFieldError();
        Map<String, Object> result = new HashMap<>();
        result.put("respCode", "400");
        result.put("respMsg", fieldError.getDefaultMessage());
        return result;
    }*/

}

