package com.thoughtworks.todo_list.common;

public enum EditTextRegexRule {
    PASSWORD_RULE("\\w{6,18}"),
    USERNAME_RULE("[a-zA-Z0-9]{3,12}");

    private String regex;
    EditTextRegexRule(String regex) {
       this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
