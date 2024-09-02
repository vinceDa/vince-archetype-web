#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.gatewayimpl;

import ${package}.convertor.UserConvertor;
import ${package}.domain.user.User;
import ${package}.domain.user.gateway.UserGateway;
import ${package}.gatewayimpl.database.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGatewayImpl implements UserGateway {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private UserConvertor userConvertor;

    @Override
    public void register(User user) {
        userMapper.insert(userConvertor.toDO(user));
    }
}
