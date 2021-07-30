package by.tarasiuk.ct.controller;

import by.tarasiuk.ct.entity.Entity;
import by.tarasiuk.ct.manager.AttributeName;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;

public class RequestContent {
    private Locale locale;
    private boolean isValidSession = true;
    private HashMap<String, String> requestParameters;
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, Object> sessionAttributes;

    public void buildContent(HttpServletRequest request) {
        locale = request.getLocale();
        requestParameters = buildRequestParameters(request);
        requestAttributes = buildRequestAttributes(request);
        sessionAttributes = buildSessionAttributes(request);
    }

    public void restore(HttpServletRequest request) {
        requestAttributes.forEach(request::setAttribute);

        if(!isValidSession) {
            request.getSession().invalidate();
        } else {
            HttpSession session = request.getSession(true);
            sessionAttributes.forEach(session::setAttribute);
        }
    }

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

    public Locale getLocale() {
        return locale;
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
            Entity element = (Entity) request.getAttribute(name);

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
}
