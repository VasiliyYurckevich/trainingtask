package com.qulix.yurkevichvv.trainingtask.wicket;

import java.util.ArrayList;
import java.util.UUID;

import org.apache.wicket.Session;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Обработчик токенов страниц.
 *
 * @author Q-YVV
 */
public class TokenHandler {

    /**
     * Список токенов.
     */
    public static final String TOKEN_LIST = "TOKEN_LIST";

    /**
     * Добавляет токен на страницу.
     */
    public static String addToken() {
        ArrayList<String> tokenList = (ArrayList<String>) Session.get().getAttribute(TOKEN_LIST);
        if (!tokenList.isEmpty()) {
            tokenList.remove(tokenList.size() - 1);
        }
        String token = UUID.randomUUID().toString();
        tokenList.add(token);
        return token;
    }

    /**
     * Проверяет токен на странице и записывает результат в запрос.
     */
    public static boolean isFirstSubmit(PageParameters pageParameters) {
        ArrayList<String> tokenList = (ArrayList<String>) Session.get().getAttribute(TOKEN_LIST);
        String token = String.valueOf(pageParameters.get("token"));
        return tokenList.remove(token);
    }
}
