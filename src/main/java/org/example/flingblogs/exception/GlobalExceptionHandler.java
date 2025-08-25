package org.example.flingblogs.exception;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex){
        return buildResponse(HttpStatus.UNAUTHORIZED, "用户名或密码错误", ex.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredJwtException(ExpiredJwtException ex){
        return buildResponse(HttpStatus.UNAUTHORIZED, "Token已过期", ex.getMessage());
    }
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Map<String, Object>> handleSignatureException(SignatureException ex){
        return buildResponse(HttpStatus.UNAUTHORIZED, "Token签名无效", ex.getMessage());
    }
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Map<String, Object>> handleMalformedJwtException(MalformedJwtException ex){
        return buildResponse(HttpStatus.UNAUTHORIZED, "Token格式错误", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex){
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                .orElse("参数验证失败");
        return buildResponse(HttpStatus.BAD_REQUEST, "参数验证失败", detail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "服务器内部错误", ex.getMessage());
    }


    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message, String detail) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", status.value());
        body.put("msg", message);
        body.put("detail", detail);
        return ResponseEntity.status(status).body(body);
    }
}
