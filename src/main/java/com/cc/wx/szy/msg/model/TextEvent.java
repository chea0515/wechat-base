package com.cc.wx.szy.msg.model;

import com.cc.wx.szy.common.BaseEventAttr;
import com.cc.wx.utils.XMLAdapterCDATA;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Setter
@XmlRootElement(name = "xml")
public class TextEvent extends BaseEventAttr {
    @XmlElement(name = "Content")
    @XmlJavaTypeAdapter(XMLAdapterCDATA.class)
    private String content;

    @XmlTransient
    public String getContent() {
        return content;
    }
}
