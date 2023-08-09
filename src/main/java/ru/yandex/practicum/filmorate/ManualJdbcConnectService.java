/*
package ru.yandex.practicum.filmorate;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ManualJdbcConnectService {
    public static final String JDBC_URL = "jdbc:mysql://cat.world:3306/allcats";
    public static final String JDBC_USERNAME = "sa";
    public static final String JDBC_PASSWORD = "password";
    public static final String JDBC_DRIVER = "org.mysql.jdbc.Driver";

    public JdbcTemplate getTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(JDBC_DRIVER);
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(JDBC_USERNAME);
        dataSource.setPassword(JDBC_PASSWORD);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }
}*/
