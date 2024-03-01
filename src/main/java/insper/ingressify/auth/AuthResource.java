package insper.ingressify.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthResource implements AuthController {

    @Override
    public ResponseEntity<LoginOut> create(RegisterIn in) {
            throw new UnsupportedOperationException("Unimplemented method 'create'");
        
    }

    @Override
    public ResponseEntity<LoginOut> authenticate(CredetalIn in) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'authenticate'");
    }
}