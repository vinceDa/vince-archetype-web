#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.exception.SysException;
import ${package}.dto.data.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @author vince 2024-08-28 11:27
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionConfig {

    @ExceptionHandler(BizException.class)
    public Response handleBizException(BizException e) {
        log.warn("biz exception: {}", e.getMessage());
        return Response.buildFailure(e.getErrCode(), e.getMessage());
    }

    @ExceptionHandler(SysException.class)
    public Response handleSysException(SysException e) {
        log.error("sys exception: {}", e.getMessage(), e);
        return Response.buildFailure(ErrorCode.SYSTEM_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        log.error("unknown exception: ", e);
        return Response.buildFailure(ErrorCode.SYSTEM_ERROR.getCode(), "系统繁忙，请稍后再试");
    }
}
