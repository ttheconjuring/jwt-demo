package bg.softuni.jwtdemo.service;

import bg.softuni.jwtdemo.model.dto.AuthenticationRequest;
import bg.softuni.jwtdemo.model.dto.AuthenticationResponse;
import bg.softuni.jwtdemo.model.dto.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

}
