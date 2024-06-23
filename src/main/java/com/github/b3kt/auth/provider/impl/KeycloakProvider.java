package com.github.b3kt.auth.provider.impl;

import com.github.b3kt.auth.configuration.properties.KeycloakProperties;
import com.github.b3kt.auth.exception.ProviderException;
import com.github.b3kt.auth.provider.IKeycloakProvider;
import com.github.b3kt.auth.provider.model.CreateUserRequest;
import com.github.b3kt.auth.provider.model.CreateUserResponse;
import com.github.b3kt.auth.provider.model.GetAccessTokenRequest;
import com.github.b3kt.auth.provider.model.GetAccessTokenResponse;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserProfileMetadata;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Optional;

@Slf4j
@ApplicationScoped
public class KeycloakProvider extends AbstractProvider<GetAccessTokenResponse> implements IKeycloakProvider {

    @Inject
    KeycloakProperties keycloakProperties;

    @Override
    public Uni<AccessTokenResponse> getAccessToken(GetAccessTokenRequest request) {

        return Uni.createFrom().item(() -> {
            Keycloak keycloak = getKeycloakClient(request.getUsername().trim(), request.getPassword().trim(), keycloakProperties.getClientId());
            return keycloak.tokenManager().getAccessToken();
        }).onItem().invoke(response -> log.info("getAccessToken response: {}", response))
                .onCancellation().invoke(() -> log.info("The downstream does not want our items anymore test!")).onFailure().transform(ProviderException::new).runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Override
    public Uni<CreateUserResponse> createUser(CreateUserRequest createUserRequest) {
        return Uni.createFrom().item(() -> createUserRequest)
                .map(request -> validationHelper.validate(request))
                .map(user -> getKeycloakAdminClient()
                        .realm(keycloakProperties.getRealm())
                        .users()
                        .create(constructUser(user)))
                .onItem()
                .invoke(Unchecked.consumer(response -> {
                    if (HttpStatus.SC_CREATED == response.getStatus()) {
                        log.info("User Created : {}", createUserRequest);
                    } else {
                        log.info("User failed to Create : {} --> {} {}", createUserRequest, response.getStatus(), response.getStatusInfo().getReasonPhrase());
                        throw new ProviderException(response.getStatus(), response.getStatusInfo().getReasonPhrase());
                    }
                }))
                .onCancellation()
                .invoke(() -> log.info("The downstream does not want our items anymore!"))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .onItem()
                .transform(response -> CreateUserResponse.builder().build());
    }

    @Override
    public Uni<UserProfileMetadata> getCurrentSession(String accessToken) {
        getKeycloakClient(accessToken, keycloakProperties.getClientId()).realm(keycloakProperties.getRealm()).users().userProfile().getMetadata();
        return Uni.createFrom().item(() -> accessToken).map(token -> getKeycloakClient(accessToken, keycloakProperties.getClientId()).realm(keycloakProperties.getRealm()).users().userProfile().getMetadata()).runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Override
    public Uni<Void> logout() {
        return Uni.createFrom().item(() -> {
            getKeycloakClient(null, null).tokenManager().logout();
            return null;
        });
    }

    @Override
    public Uni<AccessTokenResponse> refreshAccessToken(String accessToken) {
        return Uni.createFrom().item(() -> accessToken)
                .map(token -> getKeycloakClient(accessToken, keycloakProperties.getClientId())
                        .tokenManager().refreshToken()).runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    private Keycloak getKeycloakClient(String username, String password, String clientId) {
        return KeycloakBuilder.builder().serverUrl(keycloakProperties.getAuthBaseUrl()).realm(keycloakProperties.getRealm()).grantType(OAuth2Constants.PASSWORD).clientId(clientId).clientSecret(keycloakProperties.getClientSecret()).username(username).password(password).build();
    }

    private Keycloak getKeycloakClient(String accessToken, String clientId) {
        return KeycloakBuilder.builder().serverUrl(keycloakProperties.getAuthBaseUrl()).realm(keycloakProperties.getRealm()).grantType(OAuth2Constants.ACCESS_TOKEN).clientId(clientId).clientSecret(keycloakProperties.getClientSecret()).authorization("Bearer " + accessToken).build();
    }

    private Keycloak getKeycloakAdminClient() {
        return getKeycloakClient(keycloakProperties.getAdminUsername(), keycloakProperties.getAdminPassword(), keycloakProperties.getAdminClientId());
    }

    private UserRepresentation constructUser(CreateUserRequest createUserRequest) {
        return Optional.ofNullable(createUserRequest).map(request -> {
            UserRepresentation user = new UserRepresentation();
            user.setEmail(request.getEmail());
            user.setUsername(request.getEmail());
            user.setFirstName(request.getFullName());
            return user;
        }).orElseThrow(ProviderException::new);
    }
}
