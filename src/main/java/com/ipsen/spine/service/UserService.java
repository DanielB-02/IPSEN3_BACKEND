package com.ipsen.spine.service;

import com.ipsen.spine.exception.NotFoundException;
import com.ipsen.spine.model.User;
import com.ipsen.spine.repository.UserRepository;
import com.ipsen.spine.security.PermissionBeheerGebruikers;
import com.ipsen.spine.security.PermissionLezen;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @PermissionBeheerGebruikers
    public void delete(long id) throws NotFoundException {
        Optional<User> optionalUser = this.userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new NotFoundException("User with id: " + id + " not found");
        }

        User user = optionalUser.get();
        this.userRepository.delete(user);
    }

    @PermissionLezen
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
