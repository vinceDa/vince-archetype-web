#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.mybatis;

import ${package}.snowflake.SnowflakeIdGenerator;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class MyBatisConfig {

    @Bean
    public SnowflakeIdGenerator snowflakeIdGenerator() {
        long workerId;
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            byte[] ip = inetAddress.getAddress();
            // 使用完整的 IP 地址来计算 workerId
            workerId = ((long)(ip[0] & 0xFF) << 24)
                    | ((long)(ip[1] & 0xFF) << 16)
                    | ((long)(ip[2] & 0xFF) << 8)
                    | (long)(ip[3] & 0xFF);

            // 确保 workerId 在 0-31 范围内
            // 等同于 workerId % 32
            workerId = workerId & 31;
        } catch (UnknownHostException e) {
            workerId = 1L;
        }
        return new SnowflakeIdGenerator(workerId);
    }

    @Bean
    public IdGeneratorInterceptor idGeneratorInterceptor(SnowflakeIdGenerator idGenerator) {
        return new IdGeneratorInterceptor(idGenerator);
    }

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> configuration.addInterceptor(idGeneratorInterceptor(snowflakeIdGenerator()));
    }
}