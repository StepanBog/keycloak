package ru.bogdanov.ipr.keycloak.entity.acl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "acl_sid")
public class AclSid {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "principal")
    private Long principal;

    @Column(name = "sid")
    private String sid;
}
