package ru.bogdanov.ipr.keycloak;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bogdanov.ipr.keycloak.entity.acl.*;
import ru.bogdanov.ipr.keycloak.repository.acl.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AService {

    private final AclClassRepository aclClassRepository;
    private final AclEntryRepository aclEntryRepository;
    private final AclObjectIdentityRepository aclObjectIdentityRepository;
    private final AclSidRepository aclSidRepository;
    private final ProductRepository productRepository;

    @PostConstruct
    void aclData() {
        List<AclSid> aclSids = aclSidRepository.saveAll(List.of(
                AclSid.builder()
                        .principal(0L)
                        .sid("ipr-admin")
                        .build(),
                AclSid.builder()
                        .principal(0L)
                        .sid("ipr-user")
                        .build(),
                AclSid.builder()
                        .principal(0L)
                        .sid("ipr-user-2")
                        .build()
        ));
        List<Product> products = productRepository.saveAll(List.of(
                Product.builder()
                        .name("Product1")
                        .build(),
                Product.builder()
                        .name("Product2")
                        .build()
        ));
        AclClass aclClass = aclClassRepository.save(AclClass.builder().className("ru.bogdanov.ipr.keycloak.entity.acl.Product").build());

        List<AclObjectIdentity> aclObjectIdentities = aclObjectIdentityRepository.saveAll(List.of(
                AclObjectIdentity.builder()
                        .aclClass(aclClass)
                        .objectIdIdentity(products.get(0).getId())
                        .ownerSid(aclSids.get(2))
                        .parentObjectIdentity(null)
                        .entriesInheriting(false)
                        .build(),
                AclObjectIdentity.builder()
                        .aclClass(aclClass)
                        .objectIdIdentity(products.get(1).getId())
                        .ownerSid(aclSids.get(2))
                        .parentObjectIdentity(null)
                        .entriesInheriting(false)
                        .build()
        ));

        aclEntryRepository.saveAll(List.of(
                AclEntry.builder()
                        .objectIdentity(aclObjectIdentities.get(0))
                        .sid(aclSids.get(0))
                        .mask(1)

                        .build(),
                AclEntry.builder()
                        .objectIdentity(aclObjectIdentities.get(0))
                        .sid(aclSids.get(0))
                        .mask(2)
                        .build(),
                AclEntry.builder()
                        .objectIdentity(aclObjectIdentities.get(0))
                        .sid(aclSids.get(0))
                        .mask(4)
                        .build()
        ));
        aclEntryRepository.saveAll(List.of(
                AclEntry.builder()
                        .objectIdentity(aclObjectIdentities.get(1))
                        .sid(aclSids.get(0))
                        .mask(1)
                        .build(),
                AclEntry.builder()
                        .objectIdentity(aclObjectIdentities.get(1))
                        .sid(aclSids.get(0))
                        .mask(2)
                        .build(),
                AclEntry.builder()
                        .objectIdentity(aclObjectIdentities.get(1))
                        .sid(aclSids.get(0))
                        .mask(4)
                        .build()
        ));
        aclEntryRepository.saveAll(List.of(
                AclEntry.builder()
                        .objectIdentity(aclObjectIdentities.get(0))
                        .sid(aclSids.get(1))
                        .mask(1)
                        .build(),
                AclEntry.builder()
                        .objectIdentity(aclObjectIdentities.get(0))
                        .sid(aclSids.get(1))
                        .mask(2)
                        .build()
        ));
        aclEntryRepository.saveAll(List.of(
                AclEntry.builder()
                        .objectIdentity(aclObjectIdentities.get(1))
                        .sid(aclSids.get(1))
                        .mask(1)
                        .build(),
                AclEntry.builder()
                        .objectIdentity(aclObjectIdentities.get(1))
                        .sid(aclSids.get(1))
                        .mask(2)
                        .build()
        ));
        aclEntryRepository.saveAll(List.of(
                AclEntry.builder()
                        .objectIdentity(aclObjectIdentities.get(0))
                        .sid(aclSids.get(2))
                        .mask(1)
                        .build()
        ));
        aclEntryRepository.saveAll(List.of(
                AclEntry.builder()
                        .objectIdentity(aclObjectIdentities.get(1))
                        .sid(aclSids.get(2))
                        .mask(1)
                        .build()
        ));

    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }
    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product createNew(String name) {
        final Product newProduct = productRepository.save(Product.builder().name(name).build());
        final AclObjectIdentity aclObjectIdentity = aclObjectIdentityRepository.save(AclObjectIdentity.builder()
                .objectIdIdentity(newProduct.getId())
                .aclClass(aclClassRepository.findAll().get(0))
                .build());
        aclEntryRepository.saveAll(List.of(
                AclEntry.builder()
                        .mask(1)
                        .sid(aclSidRepository.findAclSidByPrincipal("ipr-admin"))
                        .objectIdentity(aclObjectIdentity)
                        .build(),
                AclEntry.builder()
                        .mask(2)
                        .sid(aclSidRepository.findAclSidByPrincipal("ipr-admin"))
                        .objectIdentity(aclObjectIdentity)
                        .build(),
                AclEntry.builder()
                        .mask(4)
                        .sid(aclSidRepository.findAclSidByPrincipal("ipr-admin"))
                        .objectIdentity(aclObjectIdentity)
                        .build()
                ));
        return newProduct;
    }
}
