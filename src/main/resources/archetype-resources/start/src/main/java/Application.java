#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

/**
 * Spring Boot Starter
 * @author Frank Zhang
 */
@SpringBootApplication(scanBasePackages = {"${package}", "com.alibaba.cola"})
@MapperScan(basePackages = {"${package}.gatewayimpl.database"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
