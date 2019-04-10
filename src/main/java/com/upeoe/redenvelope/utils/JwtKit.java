package com.upeoe.redenvelope.utils;

import com.upeoe.redenvelope.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author upeoe
 * @create 2019/4/11 01:54
 */
public class JwtKit {

    public static String encode(String subject) {
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        long mills = System.currentTimeMillis();
        Date now = new Date(mills);

        SecretKey key = generateKey();
        JwtBuilder builder = Jwts.builder()
                .setId("ahoy")
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(algorithm, key)
                .setExpiration(new Date(mills + Constants.JWT_DEFAULT_EXPIRE_MILLS));

        return builder.compact();
    }

    public static Claims decode(String jwt) {
        return Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(jwt)
                .getBody();
    }

    private static SecretKey generateKey() {
        String key = Constants.JWT_SIGNKEY;
        byte[] bytes = Base64.decodeBase64(key);
        return new SecretKeySpec(bytes, 0, bytes.length, "AES");
    }

}
