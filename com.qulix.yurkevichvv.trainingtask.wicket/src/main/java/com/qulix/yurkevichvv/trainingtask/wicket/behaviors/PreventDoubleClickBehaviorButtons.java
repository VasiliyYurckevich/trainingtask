package com.qulix.yurkevichvv.trainingtask.wicket.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.attributes.AjaxCallListener;

/**
 * Поведение кнопки,решающее проблему двойной отправки формы.
 *
 * @author Q-YVV
 */
public class PreventDoubleClickBehaviorButtons extends AjaxCallListener {

    private static final String enableDisableJavascriptPointerEvents = ";$('#%s').css('pointer-events', '%s');";

    public PreventDoubleClickBehaviorButtons() {
        super();
    }

    @Override
    public CharSequence getBeforeHandler(Component component) {
        return getEnableDisableJavascript(component);
    }

    public static String getEnableDisableJavascript(Component component) {
        return String.format(enableDisableJavascriptPointerEvents, component.getMarkupId(),"none");
    }
}