package com.ipsen.spine.security;

import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    // Test-only keys built at runtime (32 repeated bytes = 256 bits); not secrets.
    private static final String KEY = base64Key('a');
    private static final String OTHER_KEY = base64Key('b');

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

    private static String base64Key(char fill) {
        return Base64.getEncoder().encodeToString(String.valueOf(fill).repeat(32).getBytes(StandardCharsets.US_ASCII));
    }

    private static JwtService serviceWithKey(String key) {
        JwtService service = new JwtService();
        ReflectionTestUtils.setField(service, "jwtSigningKey", key);
        return service;
    }
}
