package insper.ingressify.auth;

import lombok.Builder;
import lombok.experimental.Accessors;


@Builder
public record Token (

    String id,
    String name,
    String role

) {
}