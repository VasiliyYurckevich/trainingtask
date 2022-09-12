package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import org.apache.wicket.markup.html.form.Button;

/**
 * Кнопка предотвращающая двойногой щелчок.
 *
 * @author Q-YVV
 */
public class NoDoubleClickButton extends Button {

    /**
     * Конструктор.
     *
     * @param id идентифекатор
     */
    public NoDoubleClickButton(String id) {
        super(id);
    }

    @Override
    protected String getOnClickScript() {
        return PreventDoubleClickBehaviorButtons.getEnableDisableJavascript(NoDoubleClickButton.this);
    }
}
