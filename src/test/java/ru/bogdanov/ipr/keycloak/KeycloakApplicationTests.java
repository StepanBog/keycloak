package ru.bogdanov.ipr.keycloak;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.bogdanov.ipr.keycloak.entity.acl.AclSid;
import ru.bogdanov.ipr.keycloak.entity.acl.Product;
import ru.bogdanov.ipr.keycloak.repository.acl.AclSidRepository;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class KeycloakApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private AclSidRepository aclSidRepository;
    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = createPostgreSQLContainer();
    public static PostgreSQLContainer<?> createPostgreSQLContainer() {
        return (new PostgreSQLContainer("postgres:12.9")).withDatabaseName("testDb").withUsername("test_user").withPassword("test_password");
    }
    @DynamicPropertySource
    static void setLiquibaseChangeLog(DynamicPropertyRegistry propertyRegistry) {
        Objects.requireNonNull(postgreSQLContainer);
        propertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        Objects.requireNonNull(postgreSQLContainer);
        propertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        Objects.requireNonNull(postgreSQLContainer);
        propertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
      }
    @Test
    void contextLoads() {
    }

    @Test
    @WithMockUser(roles = "ipr-u")
    void testUser() throws Exception {
        String contentAsString = mockMvc.perform(get("/read"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        mockMvc.perform(get("/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(Product.builder()
                                .id(1L)
                                .name("product3")
                                .build()
                        )))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(get("/create")
                        .param("name","product4"))
                .andExpect(status().is4xxClientError());
    }




}
