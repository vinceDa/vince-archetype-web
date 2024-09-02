#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config.interceptor;

import com.alibaba.cola.exception.BizException;
import ${package}.config.TokenUtil;
import ${package}.domain.ErrorCode;
import com.google.common.base.Strings;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 鉴权拦截器
 * @author vince
 */
@Configuration
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    private final static String AUTHORIZATION = "Authorization";
    private final static String BEARER = "Bearer";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("URI:{},METHOD:{}", request.getRequestURI(), request.getMethod());

        // 获取JwtToken
        String jwtToken = request.getHeader(AUTHORIZATION);
        if (Strings.isNullOrEmpty(jwtToken) || !jwtToken.contains(BEARER)) {
            logger.warn("auth token format error");
            ErrorCode userTokenError = ErrorCode.USER_TOKEN_ERROR;
            throw new BizException(userTokenError.getCode(), userTokenError.getMessage());
        }

        // 取出jwt_token
        jwtToken = jwtToken.substring("Bearer ".length() - 1).trim();

        try {
            TokenUtil.getSubjectFromToken(jwtToken);
        } catch (Exception e) {
            logger.warn("auth failed: {}", e.getMessage());
            throw new BizException("auth failed: " + e.getMessage());
        }

        return true;
    }
}
