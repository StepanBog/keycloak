package ru.bogdanov.ipr.keycloak.repository.acl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.bogdanov.ipr.keycloak.entity.acl.AclClass;
import ru.bogdanov.ipr.keycloak.entity.acl.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
