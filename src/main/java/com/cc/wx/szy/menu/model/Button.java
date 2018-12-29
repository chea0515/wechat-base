package com.cc.wx.szy.menu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Button {

    private Type type;
    private String name;
    private String key;
    private String url;
    @JsonProperty("media_id")
    private String mediaId;
    private String appid;
    private String pagepath;
    @JsonProperty("sub_button")
    private List<Button> subButton;

}
