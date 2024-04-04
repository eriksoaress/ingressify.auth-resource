package jogayjoga.auth;

import lombok.Builder;


@Builder
public record Token (

    String id,
    String name,
    String role

) {
}