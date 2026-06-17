package com.zipdabackend.global.error;

import com.zipdabackend.global.error.custom.DuplicateEmailException;
import com.zipdabackend.global.error.custom.DuplicateNickException;
import com.zipdabackend.global.error.custom.DuplicateUserException;
import com.zipdabackend.global.error.custom.UserRegistrationFailedException;
import com.zipdabackend.global.response.GlobalResponse;
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

    // ------------------------------------------
//              한지윤 에러 모음
    // ------------------------------------------
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<GlobalResponse<String>> duplicateEmailHandle(DuplicateEmailException e) {
        return ResponseEntity.status(409).body(
                GlobalResponse.<String>builder()
                        .code("E30")
                        .message("중복처리")
                        .data(e.getMessage())
                        .build()
        );
    }
    @ExceptionHandler(DuplicateNickException.class)
    public ResponseEntity<GlobalResponse<String>> duplicateNickHandle(DuplicateNickException e) {
        return ResponseEntity.status(409).body(
                GlobalResponse.<String>builder()
                        .code("E30")
                        .message("중복처리")
                        .data(e.getMessage())
                        .build()
        );
    }
    // DB에 회원이 저장되지 않음
    @ExceptionHandler(UserRegistrationFailedException.class)
    public ResponseEntity<GlobalResponse<String>> userRegistrationFailedHandle(UserRegistrationFailedException e) {
        return ResponseEntity.status(500).body(
                GlobalResponse.<String>builder()
                        .code("E80")
                        .message("회원가입 실패")
                        .data(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<GlobalResponse<String>> duplicateUserHandle(DuplicateUserException e) {
        return ResponseEntity.status(409).body(
                GlobalResponse.<String>builder()
                        .code("E30")
                        .message("중복 데이터")
                        .data(e.getMessage())
                        .build()
        );
    }

    // ----------------------------------------------
//              공통 에러 (dev에서 정의함)
    // ----------------------------------------------
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
