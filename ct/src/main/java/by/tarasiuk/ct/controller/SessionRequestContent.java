package by.tarasiuk.ct.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;

public class SessionRequestContent {
    private boolean isValidSession;
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;

    public void getContent(HttpServletRequest request) {
        // извлечение информации из запроса и сессии
    }

    public void restore(HttpServletRequest request) {
        // добавление в запрос и сессию атрибутов для передачи в jsp
    }
}
