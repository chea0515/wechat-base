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
public class EventScanCodePush extends MenuEvent {

    @JsonProperty("ScanCodeInfo")
    private ScanCodeInfo scanCodeInfo;

    @Getter
    @Setter
    class ScanCodeInfo {
        @JsonProperty("ScanType")
        private ScanType scanType;
        @JsonProperty("ScanResult")
        private String scanResult;
    }

    enum ScanType {
        qrcode
    }
}
