package com.ipsen.spine.security;

import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    // Test-only keys (base64, >= 256 bits). Not used outside this test.
    private static final String KEY = "dGVzdC1vbmx5LXNpZ25pbmcta2V5LW5vdC1hLXNlY3JldC0wMTIzNDU2Nzg5";
    private static final String OTHER_KEY = "YW5vdGhlci10ZXN0LW9ubHkta2V5LWZvci1zaWduYXR1cmUtY2hlY2tzLTk4NzY1";

    private JwtService jwtService;
    private final UserDetails alice = User.withUsername("alice").password("x").authorities("ADMIN").build();
    private final UserDetails bob = User.withUsername("bob").password("x").authorities("READONLY").build();

    @BeforeEach
    void setUp() {
        jwtService = serviceWithKey(KEY);
    }

    @Test
    void generated_token_contains_username_and_is_valid_for_that_user() {
        String token = jwtService.generateToken(alice);

        assertEquals("alice", jwtService.extractUserName(token));
        assertTrue(jwtService.isTokenValid(token, alice));
    }

    @Test
    void token_is_not_valid_for_a_different_user() {
        String token = jwtService.generateToken(alice);

        assertFalse(jwtService.isTokenValid(token, bob));
    }

    @Test
    void token_signed_with_another_key_is_rejected() {
        String foreignToken = serviceWithKey(OTHER_KEY).generateToken(alice);

        assertThrows(SignatureException.class, () -> jwtService.extractUserName(foreignToken));
    }

    private static JwtService serviceWithKey(String key) {
        JwtService service = new JwtService();
        ReflectionTestUtils.setField(service, "jwtSigningKey", key);
        return service;
    }
}
