package insper.ingressify.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class AuthResource implements AuthController {
    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<?> create(RegisterIn in) {
        final String password = in.password().trim();
        if (null == password || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        
        final String id = authService.register(Register.builder()
                .email(in.email())
                .password(in.password())
                .name(in.name())
                .build());
        
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri())
                .build();
        
    }

    @Override
    public ResponseEntity<LoginOut> authenticate(CredentialIn in) {
        return ResponseEntity.ok(authService.authenticate(in.email(), in.password()));
    }

}