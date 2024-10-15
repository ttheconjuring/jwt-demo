package bg.softuni.jwtdemo.service;

import bg.softuni.jwtdemo.model.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtService {

    boolean isValid(String token, UserDetails user);

    boolean isTokenExpired(String token);

    Date extractExpiration(String token);

    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> resolver);

    Claims extractAllClaims(String token);

    String generateToken(User user);

}
