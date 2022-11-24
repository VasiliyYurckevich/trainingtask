package com.qulix.yurkevichvv.trainingtask.wicket.pages;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;

import org.apache.wicket.model.CompoundPropertyModel;

import java.io.Serializable;

public interface AbstractEntityPageFactory<T extends Entity> extends Serializable {
    AbstractEntityPage<T> createPage(CompoundPropertyModel<T> entityModel);
}
