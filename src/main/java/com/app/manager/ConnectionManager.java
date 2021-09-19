package com.app.manager;

import com.app.util.property.Property;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ConfigurationProperties
public class ConnectionManager extends BaseManager {

    private static ConnectionManager instance;

    private DataSource dataSource;

    @Autowired
    private Environment env;

    private ConnectionManager() {
        log = getLogger(ConnectionManager.class);
        init();
    }

    private void init() {
        final String methodName = "init";
        start(methodName);

        log.debug(methodName, "Loading connection for " + getProp(Property.DB_HOST));
        dataSource = dataSource(env);

        completed(methodName);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    // Database connection
    @Bean
    public DataSource dataSource(Environment env) {
        HikariConfig config = new HikariConfig();
        config.setUsername(getProp(Property.DB_USER));
        config.setPassword(EncryptionManager.getInstance().decrypt(getProp(Property.DB_PASS)));
        config.setDriverClassName(getProp(Property.DB_CLASS));
        config.setJdbcUrl(getProp(Property.DB_URL));

        // Pool Size
        config.setMaximumPoolSize(30);

        return new HikariDataSource(config);
    }
}
