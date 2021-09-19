package com.app.rest.service;


import com.app.controller.UserController;
import com.app.manager.EncryptionManager;
import com.app.rest.model.User;
import com.app.rest.validator.UserValidator;
import com.app.util.helper.DateHelper;
import com.app.util.json.JsonHelper;
import com.app.util.property.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("assessment/rest/auth")
public class AuthenticationService extends BaseService{

    UserValidator validator;

    @Autowired
    UserController userController;

    public AuthenticationService() {
        log = getLogger(this.getClass());
        validator = new UserValidator();
    }

    @GetMapping(value = "/test")
    public ResponseEntity<Void> test() {
        final String methodName ="test";
        start(methodName);

        log.debug(methodName, "Test Application");

        completed(methodName);
        return buildResponse(HttpStatus.OK, "Ok");
    }

    @PostMapping(value = "/register", headers = "Accept=application/json",
            produces = "application/json")
    public ResponseEntity<User> registerUser(@RequestBody User body) {
        final String methodName ="registerUser";
        start(methodName);
        log.debug(methodName, "Request : " +JsonHelper.toJson(body));
        ResponseEntity response = buildErrorResponse("Bad Request");

        if(validator.validateRegistration(body))
        {
            if(!userController.validateUser(body.getEmail()))
            {
                String userId = UUID.randomUUID().toString();
                String salt = EncryptionManager.getInstance().generateRandomString(5);
                String hashedPassword = EncryptionManager.getInstance().hash(body.getPassword(), salt);
                String createDate = DateHelper.formatDateTime(LocalDateTime.now());

                body.setUserId(userId);
                body.setSalt(salt);
                body.setPassword(hashedPassword);
                body.setCreateDt(createDate);

                log.debug(methodName, "Generated request : " +JsonHelper.toJson(body));

                if(userController.createUser(body))
                {
                    log.debug(methodName, "User created");
                    response = buildResponse(HttpStatus.OK, Constants.STATUS_SUCCESS);
                }
            }
            else
            {
                log.error(methodName, "Email already used");
                response = buildErrorResponse("Email already used !!");
            }
        }

        completed(methodName);
        return response;
    }

    @PostMapping(value = "/login", headers = "Accept=application/json",
            produces = "application/json")
    public ResponseEntity<User> login(@RequestBody User body) {
        final String methodName ="login";
        start(methodName);
        log.debug(methodName, "Request : " +JsonHelper.toJson(body));
        ResponseEntity response = buildErrorResponse("Bad Request");

        if(validator.validateLogin(body))
        {
            if(userController.validateUser(body.getEmail()))
            {
                String salt = userController.getSalt(body.getEmail());
                String hashedPassword = EncryptionManager.getInstance().hash(body.getPassword(), salt);

                if(userController.authentication(body.getEmail(), hashedPassword))
                {
                    log.debug(methodName, "authenticated");

                    User user = userController.getUser(body.getEmail());
                    log.debug(methodName, "Response : " +JsonHelper.toJson(user));

                    response = buildResponse(HttpStatus.OK, user);
                }
                else {
                    log.error(methodName, "UNAUTHORIZED");
                    response = buildResponse(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
                }
            }
            else
            {
                log.error(methodName, "User not found");
                response = buildErrorResponse("User not found!!");
            }
        }

        completed(methodName);
        return response;
    }


}
