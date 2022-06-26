package com.example.kakaopay.exception;

import com.example.kakaopay.common.ErrorCode;
import com.example.kakaopay.common.ErrorResponse;
import com.example.kakaopay.exception.cumtom.BusinessException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> noHandlerFoundExceptionAdvice(ServletException e) {
        return entity(ErrorCode.NOT_SUPPORT_API, e);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> httpMessageNotReadableExceptionAdvice(HttpMessageNotReadableException e) {
        return entity(ErrorCode.NOT_CORRECT_VALUE, e);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> MissingServletRequestParameterExceptionExceptionAdvice(MissingServletRequestParameterException e) {
        return entity(ErrorCode.NOT_CORRECT_VALUE, e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionAdvice(Exception e) {
        return entity(ErrorCode.INTERNAL_EXCEPTION, e);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionAdvice(BusinessException e) {
        return entity(e.getErrorCode(), e, e.getLocalizedMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = getErrorMessageFrom(e.getBindingResult()).orElse(e.getLocalizedMessage());
        return entity(ErrorCode.NOT_CORRECT_VALUE, e, errorMessage);
    }

    @ExceptionHandler({BindException.class})
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        String errorMessage = getErrorMessageFrom(e.getBindingResult()).orElse(e.getLocalizedMessage());
        return entity(ErrorCode.NOT_CORRECT_VALUE, e, errorMessage);
    }

    /**
     * PathVariable 'name'이 유효성 검사를 통과하지 못하면 ConstraintViolationException 발생
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(violation -> {
                    try {
                        String field = StringUtils.substringAfterLast(violation.getPropertyPath().toString(), ".");
                        String fieldErrorMessage = violation.getMessage();
                        Object invalidValue = violation.getInvalidValue();
                        return String.format("[%s](은)는 %s / 입력값: %s", field, fieldErrorMessage, invalidValue);
                    } catch (Exception ex) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(e.getLocalizedMessage());
        return entity(ErrorCode.NOT_CORRECT_VALUE, e, errorMessage);
    }

    private ResponseEntity<ErrorResponse> entity(ErrorCode errorCode, Exception e) {
        return entity(errorCode, e, e.getLocalizedMessage());
    }

    private ResponseEntity<ErrorResponse> entity(ErrorCode errorCode, Exception e, String message) {
        ErrorResponse response = ErrorResponse.of(errorCode, message);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    private Optional<String> getErrorMessageFrom(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(fieldError -> {
                    try {
                        String message = messageSource.getMessage(fieldError, Locale.getDefault());
                        return String.format("[%s](은)는 %s / 입력값: %s", fieldError.getField(), message, fieldError.getRejectedValue());
                    } catch (NoSuchMessageException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .findFirst();
    }
}
