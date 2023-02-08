package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import org.apache.wicket.markup.html.form.Button;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityForm;

/**
 * Кнопка предотвращающая двойного щелчок.
 *
 * @author Q-YVV
 */
public class SubmitButton extends Button {

    /**
     * Конструктор.
     *
     * @param id идентификатор
     */
    public SubmitButton(String id) {
        super(id);
    }

    @Override
    public void onSubmit() {
        super.onSubmit();
        AbstractEntityForm<?> form = (AbstractEntityForm<?>) getForm();
        form.onSubmitting();
        form.onChangesSubmitted();
    }
}
