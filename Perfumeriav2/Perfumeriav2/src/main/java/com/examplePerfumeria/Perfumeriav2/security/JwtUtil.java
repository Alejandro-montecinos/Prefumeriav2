package com.examplePerfumeria.Perfumeriav2.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "mi_clave_secreta_segura";
    private static final long EXPIRATION_TIME = 86400000; // 1 día

    public String generarToken(String correo) {
        return JWT.create()
                .withSubject(correo)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public boolean validarToken(String token) {
        try {
            JWTVerifier v = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
            v.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String obtenerCorreoDesdeToken(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
        return jwt.getSubject();
    }
}