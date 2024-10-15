package bg.softuni.jwtdemo.service;

import bg.softuni.jwtdemo.model.AuthenticationResponse;
import bg.softuni.jwtdemo.model.User;
import bg.softuni.jwtdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(User request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        this.userRepository.save(user);
        String token = this.jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(User request) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = this.userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = this.jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

}
