package org.haijun.study.shiro.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JwtTools {

    /**
     * 构建jwt
     */
    public static String createJWT(String name, String userId, String role,
                                   String audience, String issuer, long TTLMillis, String base64Security)
    {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥  base64Security是用来加密数字签名的密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security); // base64Security 私钥，需要保存好
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数（jwt含有三部分：头部（header）、载荷（payload）、签证（signature））
        // 头部一般有两部分信息：声明类型、声明加密的算法（通常使用HMAC SHA256）
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")//.setId("11").set
                //.setSubject("uname") 令牌主人
                .claim("role", role)//可以通过Map传递，然后循环设置，（为木运放在添加过期时间上面
                .claim("unique_name", name)
                .claim("userid", userId)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);// 过期时间，NotBefore在这个时间之前验证JWT是会失败的
        }

        //生成JWT
        return builder.compact();
    }

    /**
     * 解析jwt 可以获取创建设定的值 claims.get("自定义的参数",Long.class); 或者getIssuer();
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security){
        try
        {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();

            return claims;
        }
        catch(Exception ex)
        {
            return null;
        }
    }
}
