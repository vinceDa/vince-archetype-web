#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.convertor;

import ${package}.domain.user.User;
import ${package}.gatewayimpl.database.dataobject.UserDO;
import org.mapstruct.Mapper;

/**
 * @author vince
 */
@Mapper(componentModel = "spring")
public interface UserConvertor {

    UserDO toDO(User user);
}
