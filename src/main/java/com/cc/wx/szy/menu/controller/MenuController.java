package com.cc.wx.szy.menu.controller;

import com.cc.wx.szy.menu.model.Menu;
import com.cc.wx.szy.menu.model.MenuInfoResponse;
import com.cc.wx.szy.menu.model.MenuResponse;
import com.cc.wx.utils.WXClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private WXClient client;
    @Value("${weixin.config.accessToken}")
    private String accessToken;

    @PostMapping("/create")
    public MenuInfoResponse createMenu(@RequestBody Menu menu) {
        log.info("create menu start:");
        MenuInfoResponse response = client.createMenu(menu, accessToken);
        log.info("create menu result, code: {}, msg: {}", response.getErrcode(), response.getErrmsg());
        return response;
    }

    @GetMapping("/get")
    public MenuResponse getMenu() {
        log.info("create menu start:");
        return client.getMenu(accessToken);
    }

    @GetMapping("/delete")
    public MenuInfoResponse delMenu() {
        log.info("delete menu start:");
        return client.deleteMenu(accessToken);
    }
}
