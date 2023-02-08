package com.justudy.backend.login.controller;

import com.justudy.backend.login.dto.request.LoginRequest;
import com.justudy.backend.login.infra.SessionConst;
import com.justudy.backend.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Validated LoginRequest request, HttpSession session) {
        Long loginSequence = loginService.loginProcess(request);
        session.setAttribute(SessionConst.LOGIN_USER, loginSequence);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
