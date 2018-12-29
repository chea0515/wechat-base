package com.cc.wx.utils;

import com.cc.wx.szy.menu.model.Menu;
import com.cc.wx.szy.menu.model.MenuInfoResponse;
import com.cc.wx.szy.menu.model.MenuResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WXClient {

    @Value("${weixin.config.apiUrl}")
    private String apiUrl;

    // menu
    public MenuInfoResponse createMenu(Menu menu, String accessToken) {
        return new WXHttp("/menu/create")
                .params("access_token", accessToken)
                .post(menu, MenuInfoResponse.class);
    }

    public MenuResponse getMenu(String accessToken) {
        return new WXHttp("/menu/get")
                .params("access_token", accessToken)
                .get(MenuResponse.class);
    }

    public MenuInfoResponse deleteMenu(String accessToken) {
        return new WXHttp("/menu/delete")
                .params("access_token", accessToken)
                .get(MenuInfoResponse.class);
    }

    // menu event

    class WXHttp {
        private String path = apiUrl;
        private List<String> pathList = new ArrayList<>();
        private Map<String, String> paramsMap = new HashMap<>();

        WXHttp() {}

        WXHttp(String path) {
            pathList.add(path);
        }

        WXHttp path(String path) {
            pathList.add(path);
            return this;
        }

        WXHttp params(String key, String value) {
            paramsMap.put(key, value);
            return this;
        }

        <T> T post(Object data, Class<T> type) {
            String wxPath = buildPath();
            return HttpUtil.post(wxPath, data, type);
        }

        <T> T get(Class<T> type) {
            String wxPath = buildPath();
            return HttpUtil.get(wxPath, type);
        }

        String buildPath() {
            String newUrl = this.path;
            StringBuilder pathSb = new StringBuilder();
            if (pathList.size() > 0) {
                for (String s : pathList) {
                    pathSb.append(s);
                }
                newUrl += pathSb.toString();
            }
            if (paramsMap.size() > 0) {
                StringBuilder paramSb = new StringBuilder();
                paramSb.append("?");
                for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                    paramSb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                paramSb.delete(paramSb.length() - 1, paramSb.length());
                newUrl += paramSb.toString();
            }
            return newUrl;
        }
    }
}
