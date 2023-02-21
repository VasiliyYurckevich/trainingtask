package com.qulix.yurkevichvv.trainingtask.wicket;

import org.apache.wicket.Session;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.ArrayList;
import java.util.UUID;

public class TokenHandler {
    /**
     * Список токенов.
     */
    public static final String TOKEN_LIST = "TOKEN_LIST";

    public static String addToken() {
        ArrayList<String> tokenList = (ArrayList<String>) Session.get().getAttribute(TOKEN_LIST);
        if (!tokenList.isEmpty()) {
            tokenList.remove(tokenList.size() - 1);
        }
        String token = UUID.randomUUID().toString();
        tokenList.add(token);
        return token;
    }

    public static boolean isFirstSubmit(PageParameters pageParameters){
        ArrayList<String> tokenList = (ArrayList<String>) Session.get().getAttribute(TOKEN_LIST);
        tokenList.forEach(s -> System.out.println("session token " + s));
        String token = String.valueOf(pageParameters.get("token"));
        return tokenList.remove(token);
    }
}
