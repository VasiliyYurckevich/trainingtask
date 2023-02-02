package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.markup.html.form.Button;

/**
 * Кнопка предотвращающая двойного щелчок.
 *
 * @author Q-YVV
 */
public class NoDoubleClickButton extends Button {

    /**
     * Конструктор.
     *
     * @param id идентификатор
     */
    public NoDoubleClickButton(String id) {
        super(id);
    }

    @Override
    protected String getOnClickScript() {
        return PreventDoubleClickBehaviorButtons.getEnableDisableJavascript(NoDoubleClickButton.this);
    }

    /**
     * Поведение кнопки, решающее проблему двойной отправки формы.
     *
     * @author Q-YVV
     */
    public static class PreventDoubleClickBehaviorButtons extends AjaxCallListener {

        /**
         * JS-скрипт блокировки кнопки.
         */
        private static final String JAVASCRIPT_POINTER_EVENTS = ";$('#%s').css('pointer-events', '%s');";

        @Override
        public CharSequence getBeforeHandler(Component component) {
            return getEnableDisableJavascript(component);
        }

        /**
         * Добавление поведения при нажатии кнопки.
         *
         * @param component кнопка.
         * @return js ивент для блокирования двойного нажатия.
         */
        public static String getEnableDisableJavascript(Component component) {
            return String.format(JAVASCRIPT_POINTER_EVENTS, component.getMarkupId(), "none");
        }
    }
}
