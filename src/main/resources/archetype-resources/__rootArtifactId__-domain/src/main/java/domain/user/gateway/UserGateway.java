#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.domain.user.gateway;

import ${package}.domain.user.User;

public interface UserGateway {

    void register(User user);

}
