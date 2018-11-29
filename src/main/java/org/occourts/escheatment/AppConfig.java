package org.occourts.escheatment;

import org.occourts.escheatment.util.SpringBeanUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:escheatment.properties")
public class AppConfig {

	@Bean
    public SpringBeanUtil initSpringBeanUtil() {
        return SpringBeanUtil.getInstance();
    }

}

//"C:\install\apache-maven-3.5.4-bin\apache-maven-3.5.4\bin\mvn" clean install -DskipTests=true