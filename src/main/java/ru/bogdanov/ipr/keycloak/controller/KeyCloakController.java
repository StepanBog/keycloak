package ru.bogdanov.ipr.keycloak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bogdanov.ipr.keycloak.dto.ResponseDto;

import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
public class KeyCloakController {

    @RolesAllowed({"ipr-admin"})
    @GetMapping("/admin-url")
    public ResponseDto get() {
        return ResponseDto.builder().text("admin-url").build();
    }
    @RolesAllowed({"ipr-user"})
    @GetMapping("/user-url")
    public ResponseDto getUser() {
        return ResponseDto.builder().text("user-url").build();
    }
    @RolesAllowed({"ipr-user","ipr-admin"})
    @GetMapping("/user-admin-url")
    public ResponseDto getUserAndAdmin() {
        return ResponseDto.builder().text("user-admin-url").build();
    }
}
