package com.app.controller;

import com.app.rest.model.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class UserController extends BaseController{

    public UserController() {
        log = getLogger(this.getClass());
    }

    private static final String VALIDATE_QUERY = "SELECT if(COUNT(*)>0,'true','false') FROM user ";


    public boolean createUser(User user)
    {
        final String methodName = "createUser";
        boolean result = false;
        start(methodName);

        final String sql = "INSERT INTO user (user_id, username, fullname, email, phone, salt, password, create_dt)" +
                " VALUES(:userId, :username, :fullname, :email, :phone, :salt,:password, :createDt)";

        try (Handle handle = getHandle(); Update update = handle.createUpdate(sql)) {
            update.bindBean(user);
            result = executeUpdate(update);
        } catch (SQLException e) {
            log.error(methodName, e.getMessage());
        }
        completed(methodName);
        return result;
    }

    public boolean validateUser(String email) {
        String methodName = "validateUser";
        start(methodName);
        boolean result = false;
        final String sql = VALIDATE_QUERY + "WHERE email = :email;";

        try (Handle handle = getHandle(); Query query = handle.createQuery(sql)) {
            result = query.bind("email", email).mapTo(Boolean.class).findOnly();
        } catch (SQLException e) {
            log.error(methodName, e.getMessage());
        }
        completed(methodName);
        return result;
    }

    public String getSalt(String email) {
        final String methodName = "getSalt";
        start(methodName);
        String result = null;
        final String sql = "SELECT salt from user WHERE email = :email;";

        try (Handle handle = getHandle(); Query query = handle.createQuery(sql)) {
            query.bind("email", email);
            result = query.mapTo(String.class).findOnly();

        } catch (SQLException e) {
            log.error(methodName, e.getMessage());
        }

        completed(methodName);
        return result;
    }

    public boolean authentication(String email, String password) {
        String methodName = "authentication";
        start(methodName);
        boolean result = false;
        final String sql = VALIDATE_QUERY + "WHERE email = :email AND password = :password;";

        try (Handle handle = getHandle(); Query query = handle.createQuery(sql)) {
            result = query.bind("email", email).bind("password", password).mapTo(Boolean.class).findOnly();
        } catch (SQLException e) {
            log.error(methodName, e.getMessage());
        }
        completed(methodName);
        return result;
    }

    public User getUser(String email)
    {
        String methodName = "getUser";
        start(methodName);
        User user = new User();
        final String sql = "SELECT user_id, username, fullname, email, phone, create_dt FROM user WHERE email =:email;";

        try (Handle handle = getHandle(); Query query = handle.createQuery(sql)) {
            query.bind("email", email);
            user = query.mapToBean(User.class).findOnly();

        } catch (SQLException e) {
            log.error(methodName, e.getMessage());
        }
        completed(methodName);
        return user;
    }
}
