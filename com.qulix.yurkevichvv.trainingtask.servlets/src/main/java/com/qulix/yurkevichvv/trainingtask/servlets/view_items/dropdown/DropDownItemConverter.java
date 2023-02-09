package com.qulix.yurkevichvv.trainingtask.servlets.view_items.dropdown;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Конвертирует список сущностей в соответствующий список для выпадающего списка.
 *
 * @param <T> класс сущности
 * @author Q-YVV
 */
public interface    DropDownItemConverter<T> {

    /**
     * Конвертирует сущность в элемент выпадающего списка.
     *
     * @param object сущность
     * @return элемент выпадающего списка
     */
    DropDownListItem convert(T object);

    /**
     * Конвертирует список сущностей в список элементов для выпадающего списка.
     *
     * @param list список сущностей
     * @return список элементов выпадающего списка
     */
    default List<DropDownListItem> convertList(List<T> list) {
        return list.stream()
                .map(this::convert)
                .collect(Collectors.toList());

    }
}
