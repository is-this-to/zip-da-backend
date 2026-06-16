package com.zipdabackend.global.errors;

import com.zipdabackend.global.responses.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GlobalResponse<String>> methodArgumentTypeMismatchHandle(MethodArgumentTypeMismatchException e){
        return ResponseEntity.status(400).body(
                GlobalResponse.<String>builder()
                        .code("E21")
                        .message("요청 파라미터에 이상이 있습니다.")
                        .data(String.format("%s : 필드를 확인해 주세요.", e.getName()))
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<GlobalResponse<Map<String, String>>> methodArgumentNotValidHandle(MethodArgumentNotValidException e){
        Map<String, String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField, // 필드명
                        fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "유효하지 않은 값입니다.",
                        (existing, replacement) -> existing // 중복 필드가 있을 경우 기존 값 유지
                ));

        return ResponseEntity.status(400).body(
                GlobalResponse.<Map<String, String>>builder()
                        .code("E21")
                        .message("요청 파라미터에 이상이 있습니다.")
                        .data(errors)
                        .build()
        );
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<GlobalResponse<String>> sqlHandler(SQLException e){
        log.error("DB 에러",e);
        return ResponseEntity.status(500).body(
                GlobalResponse.<String>builder()
                        .code("E80")
                        .message("DB 에러")
                        .data("현재 서비스 이용이 불가합니다. 잠시후 다시 시도해 주십시오.")
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<String>> ordersHandle(Exception e){
        log.error("시스템 에러",e);
        return ResponseEntity.status(500).body(
                GlobalResponse.<String>builder()
                        .code("E99")
                        .message("시스템 에러")
                        .data("현재 서비스 이용이 불가합니다. 잠시후 다시 시도해 주십시오.")
                        .build()
        );
    }
}
