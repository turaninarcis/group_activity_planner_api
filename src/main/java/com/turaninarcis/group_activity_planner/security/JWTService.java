package com.turaninarcis.group_activity_planner.security;

import java.util.Date;

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

        //How to transform miliseconds into util time => 1000(miliseconds for a second) * 60 (seconds in a minute) * minutes * hours
        private final long EXPIRATION_TIME = 1000*60*60; // one hour
        private final long REFRESH_EXPIRATION_TIME = 1000*60*60*2; // two hours

        public JWTService(){
            this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
        }

        public String generateToken(String userName){
            return Jwts.builder()
                .subject(userName)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
        }

        public String generateRefreshToken(String userName){
            return Jwts.builder()
                .subject(userName)
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
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


        public String getUsernameFromAuthHeader(String authHeader){
            String token = getToken(authHeader);
            return getUserNameFromToken(token);
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
                return extractClaims(token).getSubject();
            }
            catch (JwtException e)
            {
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
