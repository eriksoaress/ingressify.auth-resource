package insper.ingressify.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import insper.ingressify.account.AccountController;
import insper.ingressify.account.AccountIn;
import insper.ingressify.account.AccountOut;
import insper.ingressify.account.LoginIn;

@Service
public class AuthService {
    
    @Autowired
    private AccountController accountController;

    @Autowired 
    private JwtService jwtService;

    public String register(Register in) {

        return accountController.create(AccountIn.builder()
                .email(in.email())
                .password(in.password())
                .name(in.name())
                .build()).getBody().id();
    }

    public LoginOut authenticate(String email, String password) {
        ResponseEntity<AccountOut> response = accountController.login(LoginIn.builder()
                .email(email)
                .password(password)
                .build());
        if (response.getStatusCode().isError()) { 
            throw new IllegalArgumentException("Invalid credentials");
        }
        if (null == response.getBody()) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        final AccountOut account = response.getBody();

        //cria um token JWT
        final String token = jwtService.create(account.id(), account.name(), "Regular");

        return LoginOut.builder()
                .token(token)
                .build();
    }
}
