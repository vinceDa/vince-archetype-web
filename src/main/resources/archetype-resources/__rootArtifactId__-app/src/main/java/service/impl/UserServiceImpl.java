#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.service.impl;

import com.alibaba.cola.dto.Response;
import ${package}.assembler.UserAssembler;
import ${package}.domain.user.gateway.UserGateway;
import ${package}.dto.cmd.UserRegisterCmd;
import ${package}.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServiceI {

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private UserAssembler userAssembler;

    @Override
    public Response register(UserRegisterCmd userRegisterCmd) {
        userGateway.register(userAssembler.toEntity(userRegisterCmd));
        return Response.buildSuccess();
    }
}
