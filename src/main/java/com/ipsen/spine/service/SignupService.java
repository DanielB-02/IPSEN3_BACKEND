package com.ipsen.spine.service;

import com.ipsen.spine.model.Role;
import com.ipsen.spine.model.User;
import com.ipsen.spine.repository.UserRepository;
import com.ipsen.spine.security.AdminSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ipsen.spine.controller.vo.SignUpRequest;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

//    @AdminSecurity
    public User signup(SignUpRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.isFictRole() ? Role.FICTER : Role.READONLY).build();
        return userRepository.save(user);
    }

}
