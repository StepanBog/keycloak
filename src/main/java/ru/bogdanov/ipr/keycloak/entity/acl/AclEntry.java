package ru.bogdanov.ipr.keycloak.entity.acl;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Accessors(chain = true)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldNameConstants
@Table(name = "acl_entry")
public class AclEntry {


    @Id
    @GeneratedValue
    @Column
    @NotNull
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "acl_object_identity",
            foreignKey = @ForeignKey(name = "acl_entry_acl_object_identity_fk")
    )
    private AclObjectIdentity objectIdentity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "sid",
            foreignKey = @ForeignKey(name = "acl_sid_fk")
    )
    private AclSid sid;

    @Builder.Default
    @ToString.Include
    private int aceOrder = 0;
    @ToString.Include
    private int mask;
    @ToString.Include
    @Builder.Default
    private boolean granting = true;
    @ToString.Include
    @Builder.Default
    private boolean auditSuccess = true;
    @ToString.Include
    @Builder.Default
    private boolean auditFailure = true;
}
