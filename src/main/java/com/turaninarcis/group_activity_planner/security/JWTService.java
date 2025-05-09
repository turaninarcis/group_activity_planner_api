package com.turaninarcis.group_activity_planner.security;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
    private static final String SECRET_KEY = "12dfjklsdfjlbkgfdfgkldflgdfhljdfgnjldf12344h3iutjdrjlhgrfkdjdhkgbdgsfsfdsd";
    private final SecretKey secretKey;

        public JWTService(){
            this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
        }

        public String generateToken(String username, UUID userId){
            return Jwts.builder()
                .subject(userId.toString())
                .claim("username", username)
                .expiration(Date.from(Instant.now().plus(Duration.ofHours(3))))
                .signWith(secretKey)
                .compact();
        }


        private Claims extractClaims(String token) throws MalformedJwtException{
            return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        }

        public String getToken(String authHeader){
            if(authHeader != null && authHeader.startsWith("Bearer ")){
                return authHeader.substring(7);
            }
            else return null;
        }

        /**This method returns either the username from a token or null in case of error
         * @return username
         * **/
        public String getUserNameFromToken(String token){
            try{
                return extractClaims(token).get("username", String.class);
            }
            catch (JwtException e)
            {
                return null;
            }
        }

        public String getUserIdFromToken(String token){
            try{
                return extractClaims(token).getSubject();
            }catch(JwtException e){
                return null;
            }
        }

        /** This method checks for expiration date and returns true if the token is past current date*
         * 
         * @param token
         * @return boolean
        */
        public boolean isTokenInvalid(String token){
            try{
                Claims claims = extractClaims(token);
                Date expiration = claims.getExpiration();
                //if expiration date is before current date, this means the token is expired -> invalid -> return true
                return expiration.before(new Date());
            }catch(JwtException | IllegalArgumentException e){
                return true;
            }
        }
}
