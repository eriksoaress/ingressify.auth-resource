package jogayjoga.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @NoArgsConstructor @AllArgsConstructor @Builder
@Setter @Accessors(fluent=true, chain = true)
public class Credential {
    
    private String email;
    private String password;
}
