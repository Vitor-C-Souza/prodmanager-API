package teste.autoflex.vitorcsouza.prodmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.AuthReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.UpdateAuthReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.UserDTOReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.UserDTORes;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Role;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {

        UserDTOReq req = new UserDTOReq(
                "vii",
                "Password123",
                "vii@example.com",
                Role.USER
        );

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("vii"))
                .andExpect(jsonPath("$.email").value("vii@example.com"));

    }

    @Test
    void shouldNotRegisterUserWithInvalidPayload() throws Exception {

        UserDTOReq req = new UserDTOReq(
                "",
                "",
                "invalid-email",
                Role.USER
        );

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {

        registerDefaultUser();

        AuthReq req = new AuthReq("vii@example.com", "Password123");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void shouldNotLoginWithWrongPassword() throws Exception {

        registerDefaultUser();

        AuthReq req = new AuthReq("vii@example.com", "wrongpass");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldUpdateOwnUserData() throws Exception {
        UserDTORes user = registerDefaultUser();
        String token = loginAndGetToken(user.email());

        UpdateAuthReq updateReq = new UpdateAuthReq("newname", null, null);

        mockMvc.perform(put("/api/v1/auth/update/{id}", user.id())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newname"));
    }

    @Test
    void shouldReturnForbiddenWhenUpdatingAnotherUser() throws Exception {
        UserDTORes user1 = registerDefaultUser();
        String token = loginAndGetToken(user1.email());

        UserDTORes user2 = registerAnotherUser();

        UpdateAuthReq updateReq = new UpdateAuthReq("hacker", null, null);

        mockMvc.perform(put("/api/v1/auth/update/{id}", user2.id())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnUnauthorizedWhenUpdatingWithoutToken() throws Exception {

        UpdateAuthReq updateReq = new UpdateAuthReq("newname", null, null);

        mockMvc.perform(put("/api/v1/auth/update/{id}", "00000000-0000-0000-0000-000000000000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isUnauthorized());
    }

    private UserDTORes registerDefaultUser() throws Exception {
        String email = UUID.randomUUID() + "@test.com";

        String response = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "username": "vii",
                                      "email": "%s",
                                      "password": "Password123",
                                      "role": "USER"
                                    }
                                """.formatted(email)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(response, UserDTORes.class);
    }


    private String loginAndGetToken(String email) throws Exception {

        String response = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "email": "%s",
                                      "password": "Password123"
                                    }
                                """.formatted(email)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("token").asText();
    }

    private UserDTORes registerAnotherUser() throws Exception {
        String response = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "username": "other",
                                      "email": "other@test.com",
                                      "password": "Password123",
                                      "role": "USER"
                                    }
                                """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(response, UserDTORes.class);
    }

    private String getLoggedUserId(String token) throws Exception {
        return "1";
    }

}