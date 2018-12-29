package com.cc.wx.szy.menu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventLocationSelect extends MenuEvent {

    @JsonProperty("SendLocationInfo")
    private SendLocationInfo sendLocationInfo;

    @Getter
    @Setter
    class SendLocationInfo {
        @JsonProperty("Location_X")
        private double locationX;
        @JsonProperty("Location_Y")
        private double locationY;
        @JsonProperty("Scale")
        private int scale;
        @JsonProperty("Label")
        private String label;
        @JsonProperty("Poiname")
        private String poiName;
    }

}
