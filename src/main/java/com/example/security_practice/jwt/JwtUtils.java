package com.example.security_practice.jwt;

import com.example.security_practice.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import org.springframework.data.util.Pair;

import java.security.Key;
import java.util.Date;

/**
 * @Author: kbs
 */
public class JwtUtils {

    /**
     * 토큰에서 username 찾기
     */
    public static String getUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKeyResolver(SigningKeyResolver.instance) //secret key 가져오는거 정의해서 그거 활용해서 키 찾음.
                .build()
                .parseClaimsJws(token)//검증. 실패시 exception 발생
                .getBody()
                .getSubject(); //username
    }

    /**
     * HEADER : alg, kid
     * PAYLOAD : sub, iat, exp
     * SIGNATURE : JwtKey.getRandomKey로 구한 Secret Key로 HS512 해시
     *
     */
    public static String createToken(User user){
        Claims claims = Jwts.claims().setSubject(user.getUsername()); //sub
        Date now = new Date();
        Pair<String, Key> key = JwtKey.getRandomKey();

        //JWT Token 생성
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + JwtProperties.EXPIRATION_TIME)) // 토큰 만료 시간 설정
                .setHeaderParam(JwsHeader.KEY_ID, key.getFirst()) // kid
                .signWith(key.getSecond()) // signature
                .compact();
    }
}
