package ma.enset.presentation.dynamicinst;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Bean implements Serializable {

    private String type;
    private String implementation;

    public Bean() {
    }

    public Bean(String type, String implementation) {
        this.type = type;
        this.implementation = implementation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImplementation() {
        return implementation;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }
}
