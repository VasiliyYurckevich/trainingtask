package com.qulix.yurkevichvv.trainingtask.wicket.links;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;

public class DeleteLink extends Link<Void> {
    private ListItem<?> item;

    public DeleteLink(String id, ListItem<?> item) {
        super(id);
        this.item = item;
    }

    @Override
    public void onClick() {

    }
}
