package com.app.controller;

import com.app.rest.model.SavingAccountRequest;
import com.app.rest.model.User;
import com.app.rest.model.savingacc.SavingAccountDetails;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SavingAccountController extends BaseController{

    public SavingAccountController() {
        log = getLogger(this.getClass());
    }

    public boolean validateUser(String userId) {
        String methodName = "validateUser";
        start(methodName);
        boolean result = false;
        final String sql = "SELECT if(COUNT(*)>0,'true','false') FROM saving_account WHERE user_id = :userId;";

        try (Handle handle = getHandle(); Query query = handle.createQuery(sql)) {
            result = query.bind("userId", userId).mapTo(Boolean.class).findOnly();
        } catch (SQLException e) {
            log.error(methodName, e.getMessage());
        }
        completed(methodName);
        return result;
    }

    public List<SavingAccountDetails> getSavingDetailList()
    {
        String methodName = "validateUser";
        start(methodName);
        final String sql = "SELECT user_id, purpose, tenor, first_deposit_amount, monthly_deposit_amount, create_dt " +
                "FROM saving_account";

        List<SavingAccountDetails> result = new ArrayList<>();

        try (Handle handle = getHandle(); Query query = handle.createQuery(sql)) {
            result = query.mapToBean(SavingAccountDetails.class).list();
        } catch (SQLException e) {
            log.error(methodName, e.getMessage());
        }
        completed(methodName);
        return result;
    }

    public List<SavingAccountDetails> getSavingDetailListById(String userId)
    {
        String methodName = "validateUser";
        start(methodName);
        final String sql = "SELECT purpose, tenor, first_deposit_amount, monthly_deposit_amount, create_dt " +
                "FROM saving_account WHERE user_id = :userId;";

        List<SavingAccountDetails> result = new ArrayList<>();

        try (Handle handle = getHandle(); Query query = handle.createQuery(sql)) {
            result = query.bind("userId", userId).mapToBean(SavingAccountDetails.class).list();
        } catch (SQLException e) {
            log.error(methodName, e.getMessage());
        }
        completed(methodName);
        return result;
    }

    public boolean insertSavingAccount(SavingAccountRequest request)
    {
        String methodName = "insertSavingAccount";
        start(methodName);
        boolean result = false;

        String sql = "INSERT INTO saving_account (user_id, purpose, tenor, first_deposit_amount, monthly_deposit_amount, create_dt)" +
                " VALUES( :userId, :purpose, :tenor, :firstDepositAmount, :monthlyDepositAmount, :createDt);";

        try (Handle handle = getHandle(); Update update = handle.createUpdate(sql)) {
            update.bindBean(request);
            result = executeUpdate(update);
        } catch (SQLException e) {
            log.error(methodName, e.getMessage());
        }

        completed(methodName);
        return result;
    }


}
