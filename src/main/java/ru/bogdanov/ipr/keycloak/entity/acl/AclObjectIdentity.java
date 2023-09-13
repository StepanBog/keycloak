package ru.bogdanov.ipr.keycloak.entity.acl;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Хранит связи класса сущности и id защищаемых сущностей. Не хранит пермиссии.
 * Права хранятся в {@link AclEntry}
 */
@Getter
@Setter
@Accessors(chain = true)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldNameConstants
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "acl_object_identity")
public class AclObjectIdentity {

    @Id
    @GeneratedValue
    @Column(updatable = false)
    @NotNull
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "object_id_class",
            foreignKey = @ForeignKey(name = "acl_object_identity_class_fk")
    )
    private AclClass aclClass;

    private Long objectIdIdentity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "owner_sid",
            foreignKey = @ForeignKey(name = "acl_owner_sid_sid_fk")
    )
    private AclSid ownerSid;

    private Long parentObjectIdentity;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = Fields.parentObjectIdentity)
    private Set<AclObjectIdentity> childrenObjectIdentities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = AclEntry.Fields.objectIdentity)
    @Builder.Default
    private Set<AclEntry> aclEntries = new HashSet<>();

    @ToString.Include
    @Builder.Default
    private boolean entriesInheriting = false;
}
