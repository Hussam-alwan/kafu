package com.kafu.kafu.security;

import com.kafu.kafu.user.User;
import com.kafu.kafu.user.UserDTO;
import com.kafu.kafu.user.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserSyncFilter extends OncePerRequestFilter {
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (isPermittedEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        if (token == null ) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or missing token");
            return;
        }

        UserDTO userDTO = getUserDetails(token);
        if(userDTO != null)
        {

            Optional<User> useroptional = userService.findByEmail(userDTO.getEmail());
            boolean userExists = (useroptional.isPresent());

            User user;
            if (!userExists) {
                user = userService.create(userDTO);
                log.info("User registered successfully");
            } else {
                user = useroptional.get();
                log.info("User already exists, skipping sync");
            }


            request = new CustomHeaderRequestWrapper(request, "X-User-ID", user.getId().toString());//this contains user id in database not keycloak id
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPermittedEndpoint(String path) {
        return path.startsWith("/swagger-ui/") || path.equals("/swagger-ui") ||
               path.startsWith("/v3/api-docs/") || path.equals("/v3/api-docs");
    }

    private UserDTO getUserDetails(String token) {
        if (token == null) return null;

        try {
            String tokenWithoutBearer = token.replace("Bearer ", "").trim();
            SignedJWT signedJWT = SignedJWT.parse(tokenWithoutBearer);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(claims.getStringClaim("email"));
            userDTO.setKeycloakId(claims.getStringClaim("sub"));
            userDTO.setFirstName(claims.getStringClaim("given_name"));
            userDTO.setLastName(claims.getStringClaim("family_name"));
            return userDTO;
        } catch (Exception e) {
            log.error("Error parsing JWT token", e);
            return null;
        }
    }
}
