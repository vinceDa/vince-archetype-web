#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dto.cmd;

import ${package}.domain.base.Password;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 用户注册实体
 */
@Data
public class UserRegisterCmd {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private Password password;
    /**
     * 手机号
     */
    private String mobile;

}
