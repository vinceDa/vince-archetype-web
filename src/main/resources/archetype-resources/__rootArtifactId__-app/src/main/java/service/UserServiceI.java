#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.service;

import com.alibaba.cola.dto.Response;
import ${package}.dto.cmd.UserRegisterCmd;

public interface UserServiceI {

    Response register(UserRegisterCmd userRegisterCmd);
}
