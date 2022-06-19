package com.restApiTaskNDSU;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "guid",
        "type",
        "listName",
        "children"
})

public class Child_2 {

    @JsonProperty("name")
    private String name;
    @JsonProperty("guid")
    private String guid;
    @JsonProperty("type")
    private String type;
    @JsonProperty("listName")
    private String listName="";
    @JsonProperty("children")
    private List<Child_3> children = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("guid")
    public String getGuid() {
        return guid;
    }

    @JsonProperty("guid")
    public void setGuid(String guid) {
        this.guid = guid;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("listName")
    public String getListName() {
        return listName;
    }

    @JsonProperty("listName")
    public void setListName(String listName) {
        this.listName = listName;
    }

    @JsonProperty("children")
    public List<Child_3> getChildren() {
        return children;
    }

    @JsonProperty("children")
    public void setChildren(List<Child_3> children) {
        this.children = children;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "com.restApiTaskNDSU.Child_2{" +
                "name='" + name + '\'' +
                ", guid='" + guid + '\'' +
                ", type='" + type + '\'' +
                ", listName='" + listName + '\'' +
                ", children=" + children +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
