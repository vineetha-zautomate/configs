package com.hms.configs.service.impl;

import com.hms.configs.dto.CreateUserRequest;
import com.hms.configs.dto.UpdateUserRequest;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;

@Service
@Slf4j
public class KeycloakService {

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private Keycloak getKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master") // Admin login is in the master realm
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(adminUsername)
                .password(adminPassword)
                .grantType("password")
                .build();
    }

    public String getTokens(String username, String password) {
        String tokenUrl = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);

        return response.getBody();  // This will contain the access_token, refresh_token, etc.
    }

    public String createUser(CreateUserRequest createUserRequest) {
        Keycloak keycloak = getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        UserRepresentation user = getUserRepresentation(createUserRequest);

        Response response = usersResource.create(user);

        if (response.getStatus() == 201) {
            return "User created successfully";
        } else {
            return "Failed to create user: " + response.getStatus();
        }
    }

    private static UserRepresentation getUserRepresentation(CreateUserRequest createUserRequest) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(createUserRequest.getPhoneNumber());  // Use phone number as the username
        user.setEnabled(true);

        // Set user credentials (password)
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setTemporary(false);
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(createUserRequest.getPassword());
        user.setCredentials(Collections.singletonList(cred));
        return user;
    }

    public String updateUserDetails(UpdateUserRequest request) {
        Keycloak keycloak = getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        UserRepresentation user = usersResource.get(request.getUserId()).toRepresentation();
        // Update only the fields that are present
        request.getFirstName().ifPresent(user::setFirstName);
        request.getLastName().ifPresent(user::setLastName);
        request.getEmail().ifPresent(user::setEmail);

        usersResource.get(request.getUserId()).update(user);
        return "User updated successfully";
    }

    // Method to delete a user
    public String deleteUser(String userId) {
        Keycloak keycloak = getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        try {
            // Delete the user by userId
            usersResource.get(userId).remove();
            return "User deleted successfully";
        } catch (NotFoundException e) {
            return "User not found";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "Failed to delete user: " + e.getMessage();
        }
    }
}

