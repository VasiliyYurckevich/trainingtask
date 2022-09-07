package com.qulix.yurkevichvv.trainingtask.wicket.behaviors;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.Model;

public class PreventSubmitOnEnterBehavior extends Behavior
{
    private static final long serialVersionUID = 1496517082650792177L;

    public PreventSubmitOnEnterBehavior()
    {
    }

    @Override
    public void bind( Component component )
    {
        super.bind( component );
        component.add( AttributeModifier.replace( "onkeydown", Model.of( "if(event.keyCode == 13) {event.preventDefault();}" ) ) );
    }
}