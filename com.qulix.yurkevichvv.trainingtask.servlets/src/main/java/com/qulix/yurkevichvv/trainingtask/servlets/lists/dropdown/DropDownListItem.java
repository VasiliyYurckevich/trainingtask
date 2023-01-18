package com.qulix.yurkevichvv.trainingtask.servlets.lists.dropdown;

/**
 * Элемент выпадающего списка.
 *
 * @author Q-YVV
 */
public class DropDownListItem {

    /**
     * Идентификатор.
     */
    private Integer id;

    /**
     * Отображающиеся значение.
     */
    private String value;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param value значение для отображения
     */
    public DropDownListItem(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
