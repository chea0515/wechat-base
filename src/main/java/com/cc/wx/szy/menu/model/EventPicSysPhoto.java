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
public class EventPicSysPhoto extends MenuEvent {

    @JsonProperty("SendPicsInfo")
    private SendPicsInfo sendPicsInfo;

    class SendPicsInfo {
        @JsonProperty("Count")
        private int count;
        @JsonProperty("PicList")
        private PicList picList;
    }

    class PicList {
        private List<Item> item;
    }

    class Item {
        @JsonProperty("PicMd5Sum")
        private int picMd5Sum;
    }
}
