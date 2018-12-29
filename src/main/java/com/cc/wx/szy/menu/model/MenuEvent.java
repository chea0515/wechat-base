package com.cc.wx.szy.menu.model;

import com.cc.wx.szy.common.BaseEvent;
import com.cc.wx.utils.XMLAdapterCDATA;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name="xml")
public class MenuEvent extends BaseEvent {
    @XmlElement(name = "Event")
    private Event event;
    @XmlJavaTypeAdapter(value = XMLAdapterCDATA.class)
    @XmlElement(name = "EventKey")
    private String eventKey;

    @XmlTransient
    public Event getEvent() {
        return event;
    }
    @XmlTransient
    public String getEventKey() {
        return eventKey;
    }
}
