package com.qulix.yurkevichvv.trainingtask.model.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Объект "Статус".
 *
 * @author Q-YVV
 */
public enum Status implements Serializable {

    /**
     * Статус "Не начата".
     */
    NOT_STARTED(1, "Не начата"),

    /**
     * Статус "В процессе".
     */
    INTERPROCESS(2, "В процессе"),

    /**
     * Статус "Завершена".
     */
    COMPLETED(3, "Завершена"),

    /**
     * Статус "Отложена".
     */
    POSTPONED(4, "Отложена");

    Status(int id, String status) {
        this.id = id;
        this.statusTitle = status;
    }

    /**
     * Идентификатор статуса.
     */
    private final Integer id;

    /**
     * Название статуса.
     */
    private final String statusTitle;

    public Integer getId() {
        return id;
    }

    /**
     * Возвращает статус по идентификатору.
     *
     * @throws IllegalArgumentException если полученный id статуса не найден в списке
     * @return название статуса
     */
    public static Status getStatusById(Integer id) {
        return Arrays.stream(Status.values()).filter(e -> e.id.equals(id)).findFirst()
            .orElseThrow(() -> new IllegalStateException(String.format("Unsupported status %s.", id)));
    }

    /**
     * Возвращает название статуса.
     *
     * @return название статуса
     */
    public String getStatusTitle() {
        return statusTitle;
    }
}
