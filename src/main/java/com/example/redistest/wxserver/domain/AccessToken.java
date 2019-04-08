package com.example.redistest.wxserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessToken {

    private String accessToken;
    private long expiresIn;     //7200

}
