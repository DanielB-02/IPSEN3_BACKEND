package com.ipsen.spine;

import com.ipsen.spine.model.Permission;
import org.junit.jupiter.api.Test;

import static com.ipsen.spine.model.Role.ADMIN;
import static com.ipsen.spine.model.Role.READONLY;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoleTest {

    @Test
    public void adminHeeftBeheerGebruikers() {
        assertTrue(ADMIN.hasPermission(Permission.BEHEER_GEBRUIKERS));
    }

    @Test
    public void readonlyHeeftGeenBeheerGebruikers() {
        assertFalse(READONLY.hasPermission(Permission.BEHEER_GEBRUIKERS));
    }
}
