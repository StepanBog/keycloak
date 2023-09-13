package ru.bogdanov.ipr.keycloak.repository.acl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.bogdanov.ipr.keycloak.entity.acl.AclSid;

public interface AclSidRepository extends JpaRepository<AclSid, Long> {

    AclSid findAclSidByPrincipal(String principal);
}
