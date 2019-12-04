package com.komarkova.voteSystem.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class PrintTopicTag  extends SimpleTagSupport{
    private static final String[] topics = {"","programming", "travelling", "home", "business", "technology",
            "culture", "work", "politics", "future", "food", "others"};
    private static final String topic = "topic";

    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        try {
            out.println("<select name=\"" + topic + "\">");
            for (String t : topics) {
                out.println("<option>" + t + "</option>");
            }
            out.println("</select>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
