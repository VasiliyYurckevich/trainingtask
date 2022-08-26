package com.qulix.yurkevichvv.trainingtask.wicket.panels;

import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class CustomFeedbackPanel extends FeedbackPanel {

    private static final long serialVersionUID = 1L;

    public CustomFeedbackPanel(String id, IFeedbackMessageFilter filter) {
        super(id,filter);
    }

}