package com.example.security_practice.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.data.util.Pair;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Map;
import java.util.Random;

/**
 * @Author: kbs
 */
public class JwtKey {
    /**
     * Kid-Key List. 원래는 유출되면 안되는 것.
     */
    private static final Map<String, String> SECRET_KEY_SET = Map.of(
            "key1", "SpringSecurityJWTPracticeProjectIsSoGoodAndThisProjectIsSoFunSpringSecurityJWTPracticeProjectIsSoGoodAndThisProjectIsSoFun",
            "key2", "GoodSpringSecurityNiceSpringSecurityGoodSpringSecurityNiceSpringSecurityGoodSpringSecurityNiceSpringSecurityGoodSpringSecurityNiceSpringSecurity",
            "key3", "HelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurity"
    );
    private static final String[] KID_SET = SECRET_KEY_SET.keySet().toArray(new String[0]);
    private static Random randomIndex = new Random();

    /**
     * SECRET_KEY_SET에서 랜덤 키 가져오기
     */
    public static Pair<String, Key> getRandomKey() {
        String kid = KID_SET[randomIndex.nextInt(KID_SET.length)];
        String secretKey = SECRET_KEY_SET.get(kid);
        return Pair.of(kid, Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * keyid로 key찾기
     */
    public static Key getKey(String kid) {
        String key = SECRET_KEY_SET.getOrDefault(kid, null);
        if (key == null)
            return null;
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }
}
