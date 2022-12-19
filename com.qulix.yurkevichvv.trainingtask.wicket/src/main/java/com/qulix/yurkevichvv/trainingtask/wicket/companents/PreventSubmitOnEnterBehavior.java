package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.Model;

/**
 * Поведение формы блокирующее отправку при нажатии Enter.
 *
 * @author Q-YVV
 */
public class PreventSubmitOnEnterBehavior extends Behavior {

    @Override
    public void bind(Component component) {
        super.bind(component);
        component.add(AttributeModifier.replace("onkeydown",
            Model.of("if(event.keyCode == 13) {event.preventDefault();}")));
    }
}
