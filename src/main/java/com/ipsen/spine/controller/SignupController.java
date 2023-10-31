package com.ipsen.spine.controller;

import com.ipsen.spine.controller.vo.JwtAuthenticationResponse;
import com.ipsen.spine.controller.vo.SignUpRequest;
import com.ipsen.spine.controller.vo.UserResult;
import com.ipsen.spine.model.User;
import com.ipsen.spine.service.SignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {

    @Autowired
    private SignupService signupService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public UserResult signup(@RequestBody @Valid SignUpRequest request) {
        User user = signupService.signup(request);
        UserResult result = new UserResult();
        result.email = user.getEmail();
        result.firstName = user.getFirstName();
        result.lastName = user.getLastName();
        result.role = user.getRole();
        return result;
    }

}
