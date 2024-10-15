package bg.softuni.jwtdemo.service.impl;

import bg.softuni.jwtdemo.model.dto.AuthenticationRequest;
import bg.softuni.jwtdemo.model.dto.AuthenticationResponse;
import bg.softuni.jwtdemo.model.dto.RegisterRequest;
import bg.softuni.jwtdemo.model.entity.User;
import bg.softuni.jwtdemo.model.enums.Role;
import bg.softuni.jwtdemo.repository.UserRepository;
import bg.softuni.jwtdemo.service.AuthenticationService;
import bg.softuni.jwtdemo.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
        this.userRepository.save(user);
        String token = this.jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = this.userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = this.jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

}
