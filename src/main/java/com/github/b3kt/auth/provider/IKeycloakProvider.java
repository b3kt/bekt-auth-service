package com.github.b3kt.auth.provider;

import com.github.b3kt.auth.provider.model.CreateUserRequest;
import com.github.b3kt.auth.provider.model.CreateUserResponse;
import com.github.b3kt.auth.provider.model.GetAccessTokenRequest;
import io.smallrye.mutiny.Uni;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserProfileMetadata;

public interface IKeycloakProvider {
    Uni<AccessTokenResponse> getAccessToken(GetAccessTokenRequest request);
    Uni<AccessTokenResponse> refreshAccessToken(String accessToken);
    Uni<CreateUserResponse> createUser(CreateUserRequest request);
    Uni<UserProfileMetadata> getCurrentSession(String accessToken);
    Uni<Void> logout();
}
