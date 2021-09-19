package com.app.rest.service;

import com.app.rest.model.ServiceResponse;
import com.app.util.log.AppLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseService {

    protected AppLogger log;

    protected AppLogger getLogger(Class<?> clazz) {
        return new AppLogger(clazz);
    }

    // Session
    protected boolean hasSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            return session.getAttributeNames().hasMoreElements();
        }
        return false;
    }

    protected boolean hasSessionAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        return (session != null && session.getAttribute(key) != null);
    }

    protected String getSessionAttribute(HttpServletRequest request, String key) {

        if (hasSession(request)) {
            return getSessionAttribute(request, key, String.class);
        } else {
            return null;
        }
    }

    protected <T> T getSessionAttribute(HttpServletRequest request, String key, Class<T> clazz) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(key) != null) {
            return clazz.cast(session.getAttribute(key));
        }
        return null;
    }

    protected void removeSessionAttribute(HttpServletRequest request , String key) {
        if(hasSession(request)) {
            request.getSession().removeAttribute(key);
        }
    }

    protected void start(String id, String methodName) {
        log.debug(id, methodName, "Start");
    }

    protected void completed(String id, String methodName) {
        log.debug(id, methodName, "Completed");
    }

    protected void start(String methodName) {
        log.debug(methodName, "Start");
    }

    protected void completed(String methodName) {
        log.debug(methodName, "Completed");
    }

    protected <T> ResponseEntity<T> buildResponse(HttpStatus status, String description) {
        return new ResponseEntity<>((T) new ServiceResponse(status.value(), description), status);
    }

    protected <T> ResponseEntity<T> buildResponse(HttpStatus status, T body) {
        return ResponseEntity.status(status).body(body);
    }

    protected <T> ResponseEntity<T> buildErrorResponse(String description) {
        return new ResponseEntity<>((T) new ServiceResponse(400, description), HttpStatus.BAD_REQUEST);
    }
}
