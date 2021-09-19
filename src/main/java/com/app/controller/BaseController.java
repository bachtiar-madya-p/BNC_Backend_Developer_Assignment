package com.app.controller;

import com.app.manager.ConnectionManager;
import com.app.util.log.AppLogger;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;

public class BaseController {

    protected AppLogger log;

    public BaseController() {
        // Empty Constructor
    }

    protected AppLogger getLogger(Class<?> clazz) {
        return new AppLogger(clazz);
    }

    protected void start(String methodName) {
        log.debug(methodName, "Start");
    }

    protected void completed(String methodName) {
        log.debug(methodName, "Completed");
    }


    protected Handle getHandle() throws SQLException {
        return Jdbi.open(ConnectionManager.getInstance().getConnection());
    }

    protected Handle getHandle(RowMapper<?>... rowMappers) throws SQLException {
        Handle h = getHandle();

        if (rowMappers != null && rowMappers.length > 0) {
            for (RowMapper<?> mapper : rowMappers) {
                h.registerRowMapper(mapper);
            }
        }
        return h;
    }

    protected boolean executeUpdate(Update update) {
        return update.execute() > 0;
    }

    protected boolean executeBatch(PreparedBatch batch) {
        int[] resultArr = batch.execute();

        for (int result : resultArr) {
            if (result < 0) {
                return false;
            }
        }
        return true;
    }
}
