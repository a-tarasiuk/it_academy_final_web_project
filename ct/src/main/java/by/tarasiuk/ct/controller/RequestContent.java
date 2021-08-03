package by.tarasiuk.ct.controller;

import by.tarasiuk.ct.manager.AttributeName;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestContent {
    private boolean isValidSession = true;
    private HashMap<String, String> requestParameters;
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, Object> sessionAttributes;

    public void buildContent(HttpServletRequest request) {
        requestParameters = buildRequestParameters(request);
        requestAttributes = buildRequestAttributes(request);
        sessionAttributes = buildSessionAttributes(request);
    }

    public void restore(HttpServletRequest request) {
        requestAttributes.forEach(request::setAttribute);

        if(!isValidSession) {
            request.getSession(false).invalidate();
            isValidSession = true;
        } else {
            HttpSession session = request.getSession(true);
            sessionAttributes.forEach(session::setAttribute);
        }
    }

    // Version 1
    // put for one object
    public void put(Scope scope, String key, Object value) {
        switch (scope) {
            case REQUEST:
                requestAttributes.put(key, value);
                break;
            case SESSION:
                sessionAttributes.put(key, value);
                break;
        }
    }

    // put for Map object
    public void put(Scope scope, Map<String, Object> map) {
        switch (scope) {
            case REQUEST:
                requestAttributes.putAll(map);
                break;
            case SESSION:
                sessionAttributes.putAll(map);
                break;
        }
    }

    // find object by key
    public Optional<Object> find(Scope scope, String key) {
        switch (scope) {
            case REQUEST:
                return Optional.ofNullable(requestParameters.get(key));
            case SESSION:
                return Optional.ofNullable(sessionAttributes.get(key));
            default:
                return Optional.empty();
        }
    }


    // Version 2
    public HashMap<String, String> getRequestParameters(){
        return new HashMap<>(requestParameters);
    }

    public HashMap<String, Object> getSessionAttributes() {
        return new HashMap<>(sessionAttributes);
    }

    public Optional<Object> findSessionAttribute(String key) {
        return Optional.ofNullable(sessionAttributes.get(key));
    }

    public void putRequestAttribute(String key, Object obj) {
        requestAttributes.put(key, obj);
    }

    public void putSessionAttribute(String key, Object obj) {
        sessionAttributes.put(key, obj);
    }

    public void putRequestAttributes(HashMap<String, String> attributes) {
        requestAttributes.putAll(attributes);
    }

    public void invalidateSession() {
        isValidSession = false;
    }

    private HashMap<String, String> buildRequestParameters(HttpServletRequest request) {
        request.removeAttribute(AttributeName.COMMAND);

        requestParameters = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);

            requestParameters.put(name, value);
        }

        return requestParameters;
    }

    private HashMap<String, Object> buildRequestAttributes(HttpServletRequest request) {
        requestAttributes = new HashMap<>();
        Enumeration<String> attributeNames = request.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            Object element = request.getAttribute(name);

            requestAttributes.put(name, element);
        }

        return requestAttributes;
    }

    private HashMap<String, Object> buildSessionAttributes(HttpServletRequest request) {
        sessionAttributes = new HashMap<>();
        HttpSession session = request.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            Object element = session.getAttribute(name);
            sessionAttributes.put(name, element);
        }

        return sessionAttributes;
    }

    public enum Scope {
        SESSION,
        REQUEST
    }
}
