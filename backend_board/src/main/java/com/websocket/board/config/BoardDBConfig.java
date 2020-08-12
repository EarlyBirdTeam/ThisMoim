package com.websocket.board.config;

import com.websocket.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class BoardDBConfig {
    @Autowired
    private Environment env;

    @Primary
    @Bean
    public DataSource boardDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.board.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.board.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.board.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.board.datasource.password"));
        return dataSource;
    }

    @Primary
    @Bean(name = "boardEntityManager")
    public LocalContainerEntityManagerFactoryBean boardEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(boardDataSource())
                .properties(hibernateProperties())
                .packages(User.class)
                .persistenceUnit("userPU")
                .build();
    }

    @Primary
    @Bean(name = "boardTransactionManager")
    public PlatformTransactionManager mysqlTransactionManager(@Qualifier("boardEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Map hibernateProperties() {
        Resource resource = new ClassPathResource("hibernate.properties");

        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);

            return properties.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> e.getValue())
                    );
        } catch (IOException e) {
            return new HashMap();
        }
    }
}
