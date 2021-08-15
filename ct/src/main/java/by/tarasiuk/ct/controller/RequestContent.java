package by.tarasiuk.ct.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestContent {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean isValidSession = true;
    private HashMap<String, String> requestParameters;
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, Object> sessionAttributes;

    protected void buildContent(HttpServletRequest request) {
        requestParameters = buildRequestParameters(request);
        requestAttributes = buildRequestAttributes(request);
        sessionAttributes = buildSessionAttributes(request);
    }

    protected void restore(HttpServletRequest request) {
        requestAttributes.forEach(request::setAttribute);

        if(!isValidSession) {
            request.getSession(false).invalidate();
            isValidSession = true;
        } else {
            HttpSession session = request.getSession(true);
            sessionAttributes.forEach(session::setAttribute);
        }
    }

    public void invalidateSession() {
        isValidSession = false;
    }

    public HashMap<String, String> getRequestParameters(){
        return new HashMap<>(requestParameters);
    }

    public HashMap<String, Object> getSessionAttributes() {
        return new HashMap<>(sessionAttributes);
    }

    public Optional<String> findRequestParameter(String key) {
        return Optional.ofNullable(requestParameters.get(key));
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

    public void putRequestAttributes(Map<String, String> attributes) {
        requestAttributes.putAll(attributes);
    }

    private HashMap<String, String> buildRequestParameters(HttpServletRequest request) {
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
}
