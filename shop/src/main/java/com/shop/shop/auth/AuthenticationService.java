package com.shop.shop.auth;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.shop.shop.Holder.TokenHolder;
import com.shop.shop.Model.Role;
import com.shop.shop.Model.UserModel;
import com.shop.shop.Repository.UserRepository;
import com.shop.shop.config.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
@Autowired
private UserRepository userRepository;

@Autowired
private PasswordEncoder passwordEncoder;

@Autowired
private JwtService jwtService;

@Autowired
private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(UserModel request) {
        UserModel user = new UserModel();
        user.setFirst_name(request.getFirst_name());
        user.setLast_name(request.getLast_name());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setPicture(request.getPicture());
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        AuthenticationResponse token=AuthenticationResponse.builder().token(jwtToken).build();
        TokenHolder.setToken(jwtToken);
        UserModel person =userRepository.findByEmail(JwtService.extractUsername(jwtToken));
        String State =person.getRole().toString();
        TokenHolder.setState(State);
        System.out.println("the token is  "+TokenHolder.getToken() + "  the email is "+JwtService.extractUsername(jwtToken) + State);
        return token;
    }
    public ResponseEntity<Map<String, String>> authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserModel user = userRepository.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        AuthenticationResponse token=AuthenticationResponse.builder().token(jwtToken).build();
        TokenHolder.setToken(jwtToken);
        UserModel person =userRepository.findByEmail(JwtService.extractUsername(jwtToken));
        String State =person.getRole().toString();
        TokenHolder.setState(State);
        System.out.println("the token is  "+TokenHolder.getToken() + "  the email is "+JwtService.extractUsername(jwtToken) + State);
        Map<String, String> response = new HashMap<>();
        response.put("role", State);
        response.put("token", jwtToken);
        return ResponseEntity.ok(response);
    }

}
