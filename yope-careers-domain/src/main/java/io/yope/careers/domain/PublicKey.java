package io.yope.careers.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class PublicKey {

    private final String key;

    private final String hash;

    private final String holder;
}
