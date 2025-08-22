package cn.iua.caas.robotdatahub.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static final SecretKey JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static long EXPIRE = 20 * 60 * 1000; // 令牌有效期： 20分钟

    /**
     * 生成JWT令牌
     *
     * @Param claims JWT第二部分负责 payload 中存储的内容
     * @return
     */
    public static String generateToken(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .signWith(JWT_SECRET, SignatureAlgorithm.HS256)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRE))
                .compact();
        System.out.println(jwt);
        return jwt;
    }

    public static Claims parseToken(String jwt) {
        Claims claims = null;
        try{
            claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwt).getBody();
        }catch (Exception e){
            e.printStackTrace();
        }

        return claims;
    }
}
