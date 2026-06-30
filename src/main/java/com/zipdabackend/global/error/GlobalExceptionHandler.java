package com.zipdabackend.global.error;

import com.zipdabackend.global.error.custom.*;
import com.zipdabackend.global.error.custom.auth.*;
import com.zipdabackend.global.error.custom.report.ReportAlreadyExistsException;
import com.zipdabackend.global.response.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ------------------------------------------
    //              한지윤 에러 모음
    // ------------------------------------------

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<GlobalResponse<String>> authenticationHandle(AuthenticationException e) {
        return ResponseEntity.status(401).body(
                GlobalResponse.<String>builder()
                        .code("E02")
                        .message("인증이 필요합니다.")
                        .data(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GlobalResponse<String>> accessDeniedHandle(AccessDeniedException e) {
        return ResponseEntity.status(403).body(
                GlobalResponse.<String>builder()
                        .code("E03")
                        .message("접근 권한이 없습니다.")
                        .data(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(NotRegisteredException.class)
    public ResponseEntity<GlobalResponse<String>> notRegisteredHandle(NotRegisteredException e) {
        return ResponseEntity.status(401).body(
                GlobalResponse.<String>builder()
                        .code("E01")
                        .message("사용자 입력 에러")
                        .data(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<GlobalResponse<String>> authenticationFailedHandle(AuthenticationFailedException e) {
        return ResponseEntity.status(500).body(
                GlobalResponse.<String>builder()
                        .code("E05")
                        .message("로그인 인증 저장 에러")
                        .data(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<GlobalResponse<String>> tokenHandle(TokenException e) {
        return ResponseEntity.status(401).body(
                GlobalResponse.<String>builder()
                        .code("E04")
                        .message("토큰 문제 발생")
                        .data(e.getMessage())
                        .build()
        );
    }

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

    // ------------------------------------------
    //              김민수 에러 모음
    // ------------------------------------------

    @ExceptionHandler(FileManagedException.class)
    public ResponseEntity<GlobalResponse<String>> fileManagedHandle(FileManagedException e) {
        log.error("파일 업로드 에러: {}\n{}", e.getMessage(), Arrays.toString(e.getStackTrace()));
        return ResponseEntity.status(500).body(
                GlobalResponse.<String>builder()
                        .code("E40")
                        .message("파일 업로드 실패")
                        .data(e.getMessage())
                        .build()
        );
    }

    // ------------------------------------------
    //              임호탁 에러 모음
    // ------------------------------------------

    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<GlobalResponse<String>> propertyNotFoundHandle(PropertyNotFoundException e) {
        return ResponseEntity.status(404).body(
                GlobalResponse.<String>builder()
                        .code("E50")
                        .message("매물을 찾을 수 없습니다.")
                        .data(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(PropertyAccessDeniedException.class)
    public ResponseEntity<GlobalResponse<String>> propertyAccessDeniedHandle(PropertyAccessDeniedException e) {
        return ResponseEntity.status(403).body(
                GlobalResponse.<String>builder()
                        .code("E51")
                        .message("매물에 대한 접근 권한이 없습니다.")
                        .data(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(InvalidPropertyPriceException.class)
    public ResponseEntity<GlobalResponse<String>> invalidPropertyPriceHandle(InvalidPropertyPriceException e) {
        return ResponseEntity.status(400).body(
                GlobalResponse.<String>builder()
                        .code("E52")
                        .message("가격 정보가 올바르지 않습니다.")
                        .data(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(OptionNotFoundException.class)
    public ResponseEntity<GlobalResponse<String>> optionNotFoundHandle(OptionNotFoundException e) {
        return ResponseEntity.status(404).body(
                GlobalResponse.<String>builder()
                        .code("E53")
                        .message("옵션을 찾을 수 없습니다.")
                        .data(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(RegionNotFoundException.class)
    public ResponseEntity<GlobalResponse<String>> regionNotFoundHandle(RegionNotFoundException e) {
        return ResponseEntity.status(404).body(
                GlobalResponse.<String>builder()
                        .code("E54")
                        .message("지역 정보를 찾을 수 없습니다.")
                        .data(e.getMessage())
                        .build()
        );
    }

    // ------------------------------------------
//              이예진 에러 모음
    // ------------------------------------------

    // 같은 회원이 같은 매물 중복 신고
    @ExceptionHandler(ReportAlreadyExistsException.class)
    public ResponseEntity<GlobalResponse<String>> reportAlreadyExistsHandle(ReportAlreadyExistsException e) {
        return ResponseEntity.status(409).body(
                GlobalResponse.<String>builder()
                        .code("E30")
                        .message("중복 데이터")
                        .data(e.getMessage())
                        .build()
        );
    }

    // ------------------------------------------
    //              공통 에러
    // ------------------------------------------

    // 정적 파일 404 - 로그 없이 조용히 처리 (처음에 봤던 에러 로그 문제 해결)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> noResourceFoundHandle(NoResourceFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GlobalResponse<String>> illegalArgumentHandle(IllegalArgumentException e){
        return ResponseEntity.status(400).body(
                GlobalResponse.<String>builder()
                        .code("E21")
                        .message("요청 파라미터에 이상이 있습니다.")
                        .data(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GlobalResponse<String>> methodArgumentTypeMismatchHandle(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(400).body(
                GlobalResponse.<String>builder()
                        .code("E21")
                        .message("요청 파라미터에 이상이 있습니다.")
                        .data(String.format("%s : 필드를 확인해 주세요.", e.getName()))
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse<Map<String, String>>> methodArgumentNotValidHandle(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "유효하지 않은 값입니다.",
                        (existing, replacement) -> existing
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
    public ResponseEntity<GlobalResponse<String>> sqlHandler(SQLException e) {
        log.error("DB 에러", e);
        return ResponseEntity.status(500).body(
                GlobalResponse.<String>builder()
                        .code("E80")
                        .message("DB 에러")
                        .data("현재 서비스 이용이 불가합니다. 잠시후 다시 시도해 주십시오.")
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<String>> ordersHandle(Exception e) {
        log.error("시스템 에러", e);
        return ResponseEntity.status(500).body(
                GlobalResponse.<String>builder()
                        .code("E99")
                        .message("시스템 에러")
                        .data("현재 서비스 이용이 불가합니다. 잠시후 다시 시도해 주십시오.")
                        .build()
        );
    }
}