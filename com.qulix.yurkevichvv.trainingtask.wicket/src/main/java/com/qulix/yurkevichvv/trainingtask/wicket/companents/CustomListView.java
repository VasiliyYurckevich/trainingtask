package com.qulix.yurkevichvv.trainingtask.wicket.companents;


import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import java.util.List;

public class CustomListView<T extends Entity>  extends ListView<T> {

    private final Class<Page> clazz;

    public CustomListView(String id, IModel<? extends List<T>> model, Class clazz) {
        super(id, model);
        this.clazz = clazz;
    }

    @Override
    protected void populateItem(ListItem<T> item) {
        item.add(new EditLink("editLink",clazz,item.getModelObject() ))
        .add(new DeleteLink("deleteLink", item.getModelObject()));
    }
}
