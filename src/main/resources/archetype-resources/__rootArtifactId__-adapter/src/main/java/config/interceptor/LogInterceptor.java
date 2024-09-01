#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config.interceptor;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Map;

@Configuration
public class LogInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    private static final String ATTRIBUTE_START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SidManager.resetSid();
        request.setAttribute(ATTRIBUTE_START_TIME, System.currentTimeMillis());
        Map<String, String[]> parameterMap = request.getParameterMap();
        logger.info("requestURL:{}, {},parameter:{}", request.getRequestURI(), request.getMethod(), JSONObject.toJSONString(parameterMap));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) {
        long start = (long) request.getAttribute(ATTRIBUTE_START_TIME);
        long end = System.currentTimeMillis();
        logger.info("requestURL:{}, {},time:{}ms", request.getRequestURI(), request.getMethod(), (end - start));
        SidManager.clear();
    }

}
