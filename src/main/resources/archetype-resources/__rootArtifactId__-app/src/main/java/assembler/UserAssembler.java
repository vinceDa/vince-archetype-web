#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.assembler;

import ${package}.domain.user.User;
import ${package}.dto.cmd.UserRegisterCmd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAssembler {

    User toEntity(UserRegisterCmd cmd);

}
