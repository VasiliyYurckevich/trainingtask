package com.qulix.yurkevichvv.trainingtask.wicket.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.attributes.AjaxCallListener;

/**
 * Поведение кнопки,решающее проблему двойной отправки формы.
 *
 * @author Q-YVV
 */
public class PreventDoubleClickBehaviorButtons extends AjaxCallListener {

    private static final String JAVASCRIPT_POINTER_EVENTS = ";$('#%s').css('pointer-events', '%s');";

    /**
     * Конструктор.
     */
    public PreventDoubleClickBehaviorButtons() {
        super();
    }

    @Override
    public CharSequence getBeforeHandler(Component component) {
        return getEnableDisableJavascript(component);
    }

    /**
     * Добавление поведения при нажатии кнопки.
     * @param component кнопка.
     * @return js ивент для блокирования двойного нажатия.
     */
    public static String getEnableDisableJavascript(Component component) {
        return String.format(JAVASCRIPT_POINTER_EVENTS, component.getMarkupId(), "none");
    }
}
