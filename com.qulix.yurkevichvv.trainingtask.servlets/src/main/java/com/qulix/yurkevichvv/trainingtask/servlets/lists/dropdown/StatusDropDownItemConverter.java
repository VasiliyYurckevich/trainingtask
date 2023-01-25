package com.qulix.yurkevichvv.trainingtask.servlets.lists.dropdown;

import com.qulix.yurkevichvv.trainingtask.model.entity.Status;

/**
 * Конвертер в элемент выпадающего списка для статуса.
 *
 * @author Q-YVV
 */
public class StatusDropDownItemConverter implements DropDownItemConverter<Status> {

    @Override
    public DropDownListItem convert(Status status) {
        return new DropDownListItem(status.getId(), status.getStatusTitle());
    }
}
