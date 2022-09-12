package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * Панель для вывода валидационных сообщений.
 *
 * @author Q-YVV
 */
public class CustomFeedbackPanel extends FeedbackPanel {

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param filter фильтр сообщений об ошибках
     */
    public CustomFeedbackPanel(String id, IFeedbackMessageFilter filter) {
        super(id, filter);
    }

}
