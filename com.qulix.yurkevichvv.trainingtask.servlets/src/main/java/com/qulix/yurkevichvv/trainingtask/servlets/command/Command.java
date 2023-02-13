package com.qulix.yurkevichvv.trainingtask.servlets.command;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Интерфейс Команда.
 *
 * @author Q-YVV
 */
public interface Command {

    /**
     * Основной метод команды для выполнения действия.
     *
     * @param req the {@link HttpServletRequest} объект, содержащий запрос клиента к сервлету
     * @param resp the {@link HttpServletResponse} объект, содержащий ответ, который сервлет возвращает клиенту
     */
    void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;

    default void generateToken(HttpServletRequest req) {
        byte[] randomBytes = new byte[24];
        new SecureRandom().nextBytes(randomBytes);
        String token = Base64.getUrlEncoder().encodeToString(randomBytes);

        List<String> token_list = (List<String>) req.getSession().getAttribute("TOKEN_LIST");
        token_list.add(token);

        req.setAttribute("token", token);
    }


}
