package ru.bogdanov.ipr.keycloak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bogdanov.ipr.keycloak.AService;
import ru.bogdanov.ipr.keycloak.entity.acl.Product;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class KeyCloakController {

    private final AService aService;

    @PreAuthorize("hasRole('ipr-user') AND hasPermission(#product,'READ')")
    @GetMapping("/read")
    public List<Product> read() {
        return aService.getAll();
    }

    @PreAuthorize("hasPermission(#product,'WRITE')")
    @GetMapping("/write")
    public Product write(@RequestBody Product product) {
        if (product.getId() != null)
            return aService.save(product);
        return null;
    }

   @PreAuthorize("hasPermission(#product,'CREATE')")
    @GetMapping("/create")
    public Product create(@RequestParam String name) {
        return aService.createNew(name);
    }
}
