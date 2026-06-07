package com.auth.model;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenResponse {

    private String token;
    private String type;
    private String validUntil;
}
