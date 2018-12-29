package com.cc.wx.szy.menu.model;

public enum Event {

    /** 点击菜单拉取消息时的事件推送 */
    CLICK,
    /** 点击菜单跳转链接时的事件推送 */
    VIEW,
    /** 扫码推事件的事件推送 */
    scancode_push,
    /** 扫码推事件且弹出“消息接收中”提示框的事件推送 */
    scancode_waitmsg,
    /** 弹出系统拍照发图的事件推送 */
    pic_sysphoto,
    /** 弹出拍照或者相册发图的事件推送 */
    pic_photo_or_album,
    /** 弹出微信相册发图器的事件推送 */
    pic_weixin,
    /** 弹出地理位置选择器的事件推送 */
    location_select
}
