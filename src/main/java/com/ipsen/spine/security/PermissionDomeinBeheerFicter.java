package com.ipsen.spine.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize("@permissionChecks.hasPermission('DOMEIN_BEHEER_FICTER')")
public @interface PermissionDomeinBeheerFicter {
}
