package com.cc.wx.szy.common;

import com.cc.wx.utils.XMLAdapterCDATA;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Setter
public class BaseEventAttr {
    @XmlElement(name = "ToUserName")
    @XmlJavaTypeAdapter(value = XMLAdapterCDATA.class)
    private String toUserName;
    @XmlElement(name = "FromUserName")
    @XmlJavaTypeAdapter(value = XMLAdapterCDATA.class)
    private String fromUserName;
    @XmlElement(name = "CreateTime")
    private long createTime;
    @XmlElement(name = "MsgType")
    private MsgType msgType;

    @XmlTransient
    public String getToUserName() {
        return toUserName;
    }
    @XmlTransient
    public String getFromUserName() {
        return fromUserName;
    }
    @XmlTransient
    public long getCreateTime() {
        return createTime;
    }
    @XmlTransient
    public MsgType getMsgType() {
        return msgType;
    }
}
