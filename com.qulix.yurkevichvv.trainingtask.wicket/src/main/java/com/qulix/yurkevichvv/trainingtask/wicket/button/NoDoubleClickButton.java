package com.qulix.yurkevichvv.trainingtask.wicket.button;

import org.apache.wicket.markup.html.form.Button;

import com.qulix.yurkevichvv.trainingtask.wicket.behaviors.PreventDoubleClickBehaviorButtons;

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
