package com.cc.wx.utils;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@Slf4j
public class XMLUtil {

    @SuppressWarnings("unchecked")
    public static <T> T xml2bean(String xml, Class<T> clazz) {
        T t;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
            return t;
        } catch (JAXBException e) {
            log.error("xml to bean error: {}", e);
            return null;
        }
    }

    public static String bean2xml(Object bean) {
        return bean2xml(bean, "utf-8");
    }

    public static String bean2xml(Object bean, String encoding) {
        String resultStr;
        try {
            JAXBContext context = JAXBContext.newInstance(bean.getClass());
            Marshaller marshaller = context.createMarshaller();
            // 格式化输出
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // 编码
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            // 是否省略xml头信息
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            // Handler
            marshaller.setProperty("com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler",
                    (CharacterEscapeHandler) (ch, start, len, isAttVal, writer) -> writer.write(ch, start, len));

            StringWriter writer = new StringWriter();
            marshaller.marshal(bean, writer);
            resultStr = new String(writer.getBuffer());

            return resultStr;
        } catch (JAXBException e) {
            e.printStackTrace();
            log.error("xml to bean error: {}", e);
            return null;
        }
    }
}
