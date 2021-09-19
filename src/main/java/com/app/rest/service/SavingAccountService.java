package com.app.rest.service;


import com.app.controller.SavingAccountController;
import com.app.controller.UserController;
import com.app.rest.model.CalculateSavingAccountResponse;
import com.app.rest.model.SavingAccountRequest;
import com.app.rest.model.savingacc.SavingAccount;
import com.app.rest.model.savingacc.SavingAccountDetails;
import com.app.rest.model.savingacc.UserSavingAccountResponse;
import com.app.rest.validator.SavingAccountValidator;
import com.app.util.helper.DateHelper;
import com.app.util.json.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("assessment/rest/savingAccount")
public class SavingAccountService extends BaseService {

    @Autowired
    SavingAccountController savingAccountController;

    @Autowired
    UserController userController;

    private SavingAccountValidator validator;

    public SavingAccountService() {
        log = getLogger(this.getClass());
        validator = new SavingAccountValidator();
    }

    @GetMapping(value = "/get", headers = "Accept=application/json",
            produces = "application/json")
    public ResponseEntity<UserSavingAccountResponse> getUserSavingAccount() {
        final String methodName = "getUserSavingAccount";
        start(methodName);
        ResponseEntity response = buildErrorResponse("Bad Request");

        UserSavingAccountResponse accountResponse = new UserSavingAccountResponse();

        List<SavingAccount> savingAccounts = new ArrayList<>();
        List<SavingAccountDetails> savingAccountDetailsList = savingAccountController.getSavingDetailList();

        SavingAccount savingAccount = new SavingAccount(savingAccountDetailsList);
        savingAccounts.add(savingAccount);

        accountResponse.setSavingAccountList(savingAccounts);
        log.debug(methodName, "Response : " + JsonHelper.toJson(accountResponse));

        response = buildResponse(HttpStatus.OK, accountResponse);

        completed(methodName);
        return response;
    }

    @GetMapping(value = "/get/{userId}", headers = "Accept=application/json",
            produces = "application/json")
    public ResponseEntity<UserSavingAccountResponse> getUserSavingAccountById(@PathVariable String userId) {
        final String methodName = "getUserSavingAccountById";
        start(methodName);
        log.debug(methodName, "Request : " + userId);
        ResponseEntity response = buildErrorResponse("Bad Request");


        UserSavingAccountResponse accountResponse = new UserSavingAccountResponse();

        if (savingAccountController.validateUser(userId)) {

            List<SavingAccount> savingAccounts = new ArrayList<>();
            List<SavingAccountDetails> savingAccountDetailsList = savingAccountController.getSavingDetailListById(userId);

            SavingAccount savingAccount = new SavingAccount(savingAccountDetailsList);
            savingAccounts.add(savingAccount);

            accountResponse.setSavingAccountList(savingAccounts);
            log.debug(methodName, "Response : " + JsonHelper.toJson(accountResponse));

            response = buildResponse(HttpStatus.OK, accountResponse);
        } else {
            accountResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            accountResponse.setDescription("User Not Found");
            accountResponse.setSavingAccountList(null);

            log.error(methodName, "Response : " + JsonHelper.toJson(accountResponse));

            response = buildResponse(HttpStatus.BAD_REQUEST, accountResponse);
        }

        completed(methodName);
        return response;
    }

    @PostMapping(value = "/add", headers = "Accept=application/json",
            produces = "application/json")
    public ResponseEntity<Void> getUserSavingAccountById(@RequestBody SavingAccountRequest body) {
        final String methodName = "getUserSavingAccountById";
        start(methodName);
        log.debug(methodName, "Request : " + JsonHelper.toJson(body));
        ResponseEntity response = buildErrorResponse("Bad Request");
        if (validator.validate(body)) {
            if (userController.validateUserById(body.getUserId())) {
                body.setCreateDt(DateHelper.formatDateTime(LocalDateTime.now()));

                if(savingAccountController.insertSavingAccount(body))
                {
                    log.debug(methodName, "Saving account added");
                    response = buildResponse(HttpStatus.OK, body);
                }
                else
                {
                    log.error(methodName, "Error add saving account");
                    response = buildErrorResponse("Error Adding Saving Account!!");
                }

            } else {
                response = buildErrorResponse("Invalid UserId !!");
            }
        }

        completed(methodName);
        return response;
    }

    @PostMapping(value = "/calculate", headers = "Accept=application/json",
            produces = "application/json")
    public ResponseEntity<Void> calculateSavingAccount(@RequestBody SavingAccountRequest body) {
        final String methodName = "calculateSavingAccount";
        start(methodName);
        log.debug(methodName, "Request : " + JsonHelper.toJson(body));
        ResponseEntity response = buildErrorResponse("Bad Request");
        if (validator.validateCalculation(body)) {
           double grandTotal = body.getMonthlyDepositAmount() * body.getTenor();
           double calculate = calculateGrandTotal(body.getTenor(), grandTotal);

            CalculateSavingAccountResponse entity = new CalculateSavingAccountResponse();
            entity.setPurpose(body.getPurpose());
            entity.setTenor(body.getTenor());
            entity.setFirstDepositAmount(body.getFirstDepositAmount());
            entity.setMonthlyDepositAmount(body.getMonthlyDepositAmount());
            entity.setGrandTotal(grandTotal);
            entity.setCalculation(calculate);

            log.debug(methodName, JsonHelper.toJson(entity));
            response = buildResponse(HttpStatus.OK, entity);
        }
        completed(methodName);
        return response;
    }

    private double calculateGrandTotal(double tenor, double totalAmount) {
        return (((tenor / 12) * 6 / 100) * totalAmount) + totalAmount;
    }
}
