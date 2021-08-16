package by.tarasiuk.ct.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The parameters and attributes of the request are retrieved from the object.
 * The object contains methods for getting the attributes and parameters of the request.
 */
public class RequestContent {
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

    /**
     * Setting the session flag to FALSE.
     */
    public void invalidateSession() {
        isValidSession = false;
    }

    /**
     * Get request parameters.
     * @return all request parameters.
     */
    public HashMap<String, String> getRequestParameters(){
        return new HashMap<>(requestParameters);
    }

    /**
     * Get session attributes.
     * @return all session attributes.
     */
    public HashMap<String, Object> getSessionAttributes() {
        return new HashMap<>(sessionAttributes);
    }

    /**
     * Find request parameter by key.
     * Otherwise return null.
     * @param key - String
     * @return Optional object
     */
    public Optional<String> findRequestParameter(String key) {
        return Optional.ofNullable(requestParameters.get(key));
    }

    /**
     * Find session attribute by key.
     * Otherwise return null.
     * @param key - String
     * @return Optional object
     */
    public Optional<Object> findSessionAttribute(String key) {
        return Optional.ofNullable(sessionAttributes.get(key));
    }

    /**
     * Put into request attributes list a new attribute.
     * @param key - String
     * @param obj - Object
     */
    public void putRequestAttribute(String key, Object obj) {
        requestAttributes.put(key, obj);
    }

    /**
     * Put into session attributes list a new attribute.
     * @param key - String
     * @param obj - Object
     */
    public void putSessionAttribute(String key, Object obj) {
        sessionAttributes.put(key, obj);
    }

    /**
     * Put attribute list into the existing attribute list.
     * @param attributes - Map<String, String>
     */
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
