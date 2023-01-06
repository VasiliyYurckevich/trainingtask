package com.qulix.yurkevichvv.trainingtask.servlets.dropdown;

import com.qulix.yurkevichvv.trainingtask.model.entity.Status;

public class StatusConverter implements DropDownItemConverter<Status> {

    @Override
    public DropDownListItem convert(Status status) {
        return new DropDownListItem(status.getId(), status.getStatusTitle());
    }

    @Override
    public Status convert(DropDownListItem dropDownListItem) {
        return Status.getStatusById(dropDownListItem.getId());
    }
}
