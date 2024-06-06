package com.github.b3kt.auth.provider.impl;

import com.github.b3kt.auth.exception.ProviderException;
import com.github.b3kt.auth.provider.IKeycloakProvider;
import com.github.b3kt.auth.provider.model.CreateUserRequest;
import com.github.b3kt.auth.provider.model.CreateUserResponse;
import com.github.b3kt.auth.provider.model.GetAccessTokenRequest;
import com.github.b3kt.auth.provider.model.GetAccessTokenResponse;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;

import javax.xml.transform.Source;
import java.util.Optional;

@Slf4j
@ApplicationScoped
public class KeycloakProvider extends AbstractProvider<GetAccessTokenResponse> implements IKeycloakProvider {

    @ConfigProperty(name = "app.auth.auth-base-url")
    private String authBaseUrl;

    @ConfigProperty(name = "app.auth.realm")
    private String realm;

    @ConfigProperty(name = "app.auth.admin-username")
    private String adminUsername;

    @ConfigProperty(name = "app.auth.admin-password")
    private String adminPassword;

    @ConfigProperty(name = "app.auth.client-id")
    private String clientId;

    @ConfigProperty(name = "app.auth.admin-client-id")
    private String adminClientId;

    @ConfigProperty(name = "app.auth.client-secret")
    private String clientSecret;

    @Override
    public Uni<AccessTokenResponse> getAccessToken(GetAccessTokenRequest request) {

        return Uni.createFrom().item(() -> {
                    Keycloak keycloak = getKeycloakClient(
                            request.getUsername().trim(),
                            request.getPassword().trim(),
                            clientId);
                    return keycloak.tokenManager().getAccessToken();
                })
                .onItem().invoke(response -> log.info("getAccessToken response: {}", response))
                .onCancellation().invoke(() -> log.info(
                        "The downstream does not want our items anymore!")
                )
                .onFailure().transform(ProviderException::new)
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Override
    public Uni<CreateUserResponse> createUser(CreateUserRequest createUserRequest) {
        return Uni.createFrom().item(() -> createUserRequest)
                .map(request -> validationHelper.validate(request))
                .map(request -> constructUser(request))
                .map(user -> getKeycloakAdminClient()
                        .realm(realm)
                        .users()
                        .create(user)
                 )
                .onItem().invoke(response -> {
                        if(HttpStatus.SC_CREATED == response.getStatus()){
                            log.info("User Created : {}", createUserRequest);
                        }else{
                            log.info("User failed to Create : {} --> {} {}", createUserRequest,
                                    response.getStatus(),
                                    response.getStatusInfo().getReasonPhrase());
                            throw new ProviderException(response.getStatus(), response.getStatusInfo().getReasonPhrase());
                        }
                        })
                .onCancellation().invoke(() -> log.info(
                        "The downstream does not want our items anymore!")
                )
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .onItem()
                .transform(response -> CreateUserResponse.builder().build());
    }

    private Keycloak getKeycloakClient(String username, String password, String clientId){
        return KeycloakBuilder.builder()
                .serverUrl(authBaseUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(username)
                .password(password)
                .build();
    }

    private Keycloak getKeycloakAdminClient(){
        return getKeycloakClient(adminUsername, adminPassword, adminClientId);
    }

    private UserRepresentation constructUser(CreateUserRequest createUserRequest){
        return Optional.ofNullable(createUserRequest)
                .map(request -> {
                    UserRepresentation user = new UserRepresentation();
                    user.setEmail(request.getEmail());
                    user.setUsername(request.getEmail());
                    user.setFirstName(request.getFullName());
                    return user;
                })
                .orElseThrow(ProviderException::new);
    }
}
