package com.exigen.ldap.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LdapInfo implements Serializable {

    private static final long serialVersionUID = 3216730494821254684L;

    String attribute;
    List<String> value = new ArrayList<>();

    public LdapInfo() {
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
