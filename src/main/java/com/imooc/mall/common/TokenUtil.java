package com.imooc.mall.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.imooc.mall.pojo.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Description token生成工具类
 */
@Slf4j
public class TokenUtil {
    /**
     * 过期时间
     */
    private static final long EXPIRE_TIME = 15*60*1000;
    /**
     * 秘钥
     */
    private static final String TOKEN_SECRET = "ljt54061";
    /**
     * 默认的jwt签发者
     */
    private static final String DEFAULT_ISS = "auth0";

    /**
     * 工具类私有化构造方法
     */
    private TokenUtil() {}

    /**
     * Token字符串生成
     * @param userVO 前端传入的用户信息
     * @return 带有用户信息的token字符串
     */
    public static String sign(UserVO userVO){
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer(DEFAULT_ISS)
                    .withClaim("userId", userVO.getId())
                    .withClaim("userName", userVO.getUserName())
                    .withExpiresAt(expiresAt)
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e){
            throw new OrderException(OrderExceptionType.USER_INPUT_ERROR,"生成token字符串失败");
        }
        return token;
    }

    /**
     * 验证token
     * @param token 从请求头中得到的token字符串
     * @return true验证成功 | false验证失败
     */
    public static boolean verify(String token){
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer(DEFAULT_ISS).build();
            DecodedJWT jwt = jwtVerifier.verify(token);
            log.info("验证通过:");
            log.info("issuer: " + jwt.getIssuer());
            log.info("userId: " + jwt.getClaim("userId").asLong());
            log.info("userName: " + jwt.getClaim("userName").asString());
            log.info("过期时间:" + jwt.getExpiresAt());
            return true;
        } catch (Exception e){
            throw new OrderException(OrderExceptionType.USER_INPUT_ERROR,"token验证失败");
        }
    }
}
