#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config.mybatis;

import ${package}.config.snowflake.SnowflakeIdGenerator;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Properties;

@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
)})
@Component
public class IdGeneratorInterceptor implements Interceptor {

    private final SnowflakeIdGenerator idGenerator;

    public IdGeneratorInterceptor(SnowflakeIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];

        if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
            if (parameter != null) {
                processEntity(parameter);
            }
        }

        return invocation.proceed();
    }

    private void processEntity(Object entity) throws Exception {
        Class<?> clazz = entity.getClass();
        Field idField = getIdField(clazz);
        if (idField != null) {
            idField.setAccessible(true);
            if (idField.get(entity) == null ||
                    (idField.getType() == Long.class && (Long)idField.get(entity) == 0L)) {
                idField.set(entity, idGenerator.nextId());
            }
        }
    }

    private Field getIdField(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class) || field.getName().equalsIgnoreCase("id")) {
                return field;
            }
        }
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}