package com.qulix.yurkevichvv.trainingtask.servlets.lists.dropdown;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Конвертирует список сущностей в соответствующий список для выпадающего списка.
 *
 * @param <T> класс сущности
 * @author Q-YVV
 */
public interface DropDownItemConverter<T> {

    /**
     * Конвертирует сущность в элемент выпадающего списка.
     *
     * @param object сущность
     * @return элемент выпадающего списка
     */
    DropDownListItem convert(T object);

    /**
     * Конвертирует элемент в сущность.
     *
     * @param dropDownListItem элемент списка
     * @return сущность
     */
    T convert(DropDownListItem dropDownListItem);

    /**
     * Конвертирует список элементов для выпадающего списка в список сущностей.
     *
     * @param list список элементов выпадающего списка
     * @return список сущностей
     */
    default List<T> convertDropDownList(List<DropDownListItem> list) {
        return list.stream()
                .map(item -> convert(item))
                .collect(Collectors.toList());

    }

    /**
     * Конвертирует список сущностей в список элементов для выпадающего списка.
     *
     * @param list список сущностей
     * @return список элементов выпадающего списка
     */
    default List<DropDownListItem> convertList(List<T> list) {
        return  list.stream()
                .map(item -> convert(item))
                .collect(Collectors.toList());

    }
}
