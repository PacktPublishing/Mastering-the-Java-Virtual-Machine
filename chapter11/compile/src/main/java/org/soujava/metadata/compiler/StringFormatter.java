package org.soujava.metadata.compiler;

import java.text.MessageFormat;

enum StringFormatter {

    INSTANCE;

    public String format(String template, Object... params) {
        return MessageFormat.format(template, params);
    }

}