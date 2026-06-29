package com.zipdabackend.global.error;

import com.zipdabackend.global.error.custom.FileManagedException;
import com.zipdabackend.global.error.custom.agent.AgentApplicationAlreadyExistsException;
import com.zipdabackend.global.error.custom.agent.AgentApplicationFailedException;
import com.zipdabackend.global.error.custom.agent.AgentNotFoundException;
import com.zipdabackend.global.error.custom.auth.*;
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

    // ---------------- security 에러 -------------------
    // 인증 실패: 토큰 없음, 로그인 안 함
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

    // 인가 실패: 로그인은 했지만 권한 없음
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

    // ---------------- 로그인 에러 ------------------
    //  이메일이 없거나 비밀번호가 틀림
    @ExceptionHandler(NotRegisteredException.class)
    public ResponseEntity<GlobalResponse<String>> NotRegisteredHandle(NotRegisteredException e) {
        return ResponseEntity.status(401).body(
                GlobalResponse.<String>builder()
                        .code("E01")
                        .message("사용자 입력 에러")
                        .data(e.getMessage())
                        .build()
        );
    }
    // 로그인 인증은 성공했지만 토큰 생성, 토큰 DB 저장, 인증 응답 생성 과정에서 실패
    @ExceptionHandler(AuthenticationFailedException.class)
    public  ResponseEntity<GlobalResponse<String>> AuthenticationFailedHandle(AuthenticationFailedException e) {
        return ResponseEntity.status(500).body(
                GlobalResponse.<String>builder()
                        .code("E05")
                        .message("로그인 인증 저장 에러")
                        .data(e.getMessage())
                        .build()
        );
    }

    // --------------token 재발급 에러 모음 ------------
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<GlobalResponse<String>> TokenHandle(TokenException e) {
        return ResponseEntity.status(401).body(
                GlobalResponse.<String>builder()
                        .code("E04")
                        .message("토큰 문제 발생")
                        .data(e.getMessage())
                        .build()
        );
    }

    // ------------- 회원가입 에러 모음 -----------------
    // 중복 이메일 회원
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
    // 중복 닉네임 회원
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
    // User의 유니크 속성 중 중복된게 있음
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
    // ----------------공인중개사 인증 에러-------------
    @ExceptionHandler(AgentApplicationAlreadyExistsException.class)
    public ResponseEntity<GlobalResponse<String>> AgentApplicationAlreadyExistsHandle(AgentApplicationAlreadyExistsException e) {
        return ResponseEntity.status(409).body(
                GlobalResponse.<String>builder()
                        .code("E41")
                        .message("이미 공인중개사 회원입니다.")
                        .data(e.getMessage())
                        .build()
        );
    }

    // 공인중개사 인증 DB 올리기 실패
    @ExceptionHandler(AgentApplicationFailedException.class)
    public ResponseEntity<GlobalResponse<String>> AgentApplicationFailedHandle(AgentApplicationFailedException e) {
        return ResponseEntity.status(500).body(
                GlobalResponse.<String>builder()
                        .code("E80")
                        .message("DB 에러")
                        .data(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(AgentNotFoundException.class)
    public ResponseEntity<GlobalResponse<String>> AgentNotFoundHandle(AgentNotFoundException e) {
        return ResponseEntity.status(404).body(
                GlobalResponse.<String>builder()
                        .code("E40")
                        .message("조회 실패")
                        .data(e.getMessage())
                        .build()
        );
    }

    // ------------------------------------------
//              김민수 에러 모음
    // ------------------------------------------

    // fileUpload와 관련한 전체 에러
    @ExceptionHandler(FileManagedException.class)
    public ResponseEntity<GlobalResponse<String>> fileManagedHandle(FileManagedException e){
        log.error("파일 업로드 에러: {}\n{}", e.getMessage(), Arrays.toString(e.getStackTrace()));
        return ResponseEntity.status(500).body(
                GlobalResponse.<String>builder()
                        .code("E40")
                        .message("파일 업로드 실패")
                        .data(e.getMessage())
                        .build()
        );
    }

    // ----------------------------------------------
//              공통 에러 (dev에서 정의함)
    // ----------------------------------------------
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
