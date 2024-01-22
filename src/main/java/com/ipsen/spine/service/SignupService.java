package com.ipsen.spine.service;

import com.ipsen.spine.controller.vo.SignUpRequest;
import com.ipsen.spine.model.Role;
import com.ipsen.spine.model.User;
import com.ipsen.spine.repository.RoleRepository;
import com.ipsen.spine.repository.UserRepository;
import com.ipsen.spine.security.PermissionBeheerGebruikers;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PermissionBeheerGebruikers
    public User signup(SignUpRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleRepository.getReferenceById(request.getRoleId()))
                .build();
        return userRepository.save(user);
    }
}
