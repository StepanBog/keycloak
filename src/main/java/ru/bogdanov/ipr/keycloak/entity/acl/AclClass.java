package ru.bogdanov.ipr.keycloak.entity.acl;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "acl_class")
public class AclClass {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "class")
    private String className;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = AclObjectIdentity.Fields.aclClass)
    private Set<AclObjectIdentity> objectIdentities;
}
