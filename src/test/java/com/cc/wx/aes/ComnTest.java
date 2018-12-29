package com.cc.wx.aes;

import com.cc.wx.utils.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

public class ComnTest {

    @Test
    public void testHttp() {
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        /*
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credential");
        params.put("appid", "wxadee42a4fe36508e");
        params.put("secret", "b27c61d74d274e3711e1a5d00cbaa367");
        */
        T0 params = new T0();
        params.setGrant_type("client_credential");
        params.setAppid("wxadee42a4fe36508e");
        params.setSecret("b27c61d74d274e3711e1a5d00cbaa367");
        T1 t1 = HttpUtil.get(url, params, T1.class);
        System.err.println("access_token: " + t1.getAccess_token());
    }

    @Test
    public void testField() {
        T0 t0 = new T0();
        t0.setGrant_type("client_credential");
        t0.setAppid("wxadee42a4fe36508e");
        t0.setSecret("b27c61d74d274e3711e1a5d00cbaa367");

        //Type type = t0.getClass();
        /*
        Class c = t0.getClass();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
        */
        ObjectMapper om = new ObjectMapper();
        JsonNode jsonNode = om.convertValue(t0, JsonNode.class);
        //for (Iterator it = node.iterator(); it.hasNext();) {
            //System.out.println(it.next());
        //}

        /*
        for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext();) {
            Map.Entry<String, JsonNode> entry = it.next();
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        */
        jsonNode.fields().forEachRemaining(entry -> {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        });
    }

    @Getter
    @Setter
    class T0 {
        private String grant_type;
        private String appid;
        private String secret;
    }

    @Getter
    @Setter
    static class T1 {
        private String access_token;
        private int expires_in;
    }

    @Test
    public void testBean2XML() {
        User user = new User();
        user.setName("test_name");
        user.setPwd("test_pwd");
        XStream xStream = new XStream();
        xStream.alias("User", User.class);
        //xStream.addImplicitCollection(User.class, "outer-class");
        //xStream.useAttributeFor(User.class, "name");
        //xStream.aliasAttribute("Name", "name");
        //xStream.omitField(User.class, "outerClass");
        xStream.aliasField("Name", User.class, "name");

        String userXMLStr = xStream.toXML(user);
        System.out.println(userXMLStr);
    }

    @Getter
    @Setter
    class User {
        private String name;
        private String pwd;
    }
}
