package com.cc.wx.szy.core;

import com.cc.wx.szy.common.BaseEvent;
import com.cc.wx.szy.common.MsgType;
import com.cc.wx.szy.msg.model.TextEvent;
import com.cc.wx.utils.XMLUtil;
import com.cc.wx.utils.aes.AesException;
import com.cc.wx.utils.aes.SHA1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/wx")
public class CoreController {

    @Value("${weixin.config.token}")
    private String token;

    /** 请求认证 */
    @GetMapping
    public String wxAuth(HttpServletRequest request) {
        log.info("-->wxAuth");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        log.info("signature:{}", signature);
        // valid
        if (checkSignature(token, timestamp, nonce, signature)) {
            return echostr;
        }
        return "";
    }

    /** 消息管理 */
    @PostMapping
    public String wxEvent(HttpServletRequest request, HttpServletResponse response) {
        try {
            StringBuilder sb = new StringBuilder(16);
            ServletInputStream inputStream = request.getInputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) > 0) {
                sb.append(new String(b, 0, len));
            }
            String str = sb.toString();

            BaseEvent recBase = XMLUtil.xml2bean(str, BaseEvent.class);

            TextEvent rep = new TextEvent();
            Optional.ofNullable(recBase).ifPresent(r -> {
                rep.setToUserName(recBase.getFromUserName());
                rep.setFromUserName(recBase.getToUserName());
                rep.setMsgType(MsgType.text);
                rep.setCreateTime(recBase.getCreateTime());
                if (recBase.getMsgType() != MsgType.text) rep.setContent("暂时不支持该类型的数据处理~");
                else {
                    TextEvent recTxt = XMLUtil.xml2bean(str, TextEvent.class);
                    Optional.ofNullable(recTxt).ifPresent(rt -> rep.setContent("您发送的内容是：" + rt.getContent()));
                }
            });
            return XMLUtil.bean2xml(rep);
        } catch (IOException e) {
            log.error("read input stream err: {}", e);
            return "success";
        }
    }

    /**
     * @param timestamp  时间戳
     * @param nonce      随机数
     * @param signature  微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     */
    private boolean checkSignature(String token, String timestamp, String nonce, String signature) {
        try {
            log.info("-->checkSignature");
            String _signature = SHA1.getSHA1(token, timestamp, nonce, "");
            log.info("signature:{}", signature);
            return _signature.equals(signature);
        } catch (AesException e) {
            log.error("-->checkSignature err:{}", e);
            return false;
        }
    }
}
