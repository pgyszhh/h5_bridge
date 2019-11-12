package com.shuiyinhuo.component.mixdev.cnf.comm;

import android.content.Context;
import android.text.Spanned;
import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.locationmanager.JavaToJsManager;
import com.shuiyinhuo.component.mixdev.utils.html.HtmlUtils;
import com.shuiyinhuo.component.mixdev.utils.html.JsToolsHouseKeeper;
import com.shuiyinhuo.component.mixdev.utils.comm.WebViewProxy;

import java.util.ArrayList;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/18 0018
 * @ Description：Html 工具管理类
 * =====================================
 */
public class HtmlUtilsController {
    public Spanned formatHtmlForSpanned(String sorce) {
        return HtmlUtils.getFormatUseHtml(sorce);
    }

    public Spanned formatAppointColorOfText(String sorce, String color) {
        return HtmlUtils.getAppointColorOfText(sorce, color);
    }
    public Spanned formatAppointColorAndbigBoldOfText(String sorce, String color) {
        return HtmlUtils.getAppointColorAndbigBoldOfText(sorce, color);
    }

    public Spanned formatAppointColorAndBoldOfText(String sorce, String color) {
        return HtmlUtils.getAppointColorAndBoldOfText(sorce, color);
    }

    public Spanned formatAppointColorOfText(String sorce, String color, int sizeOfpx) {
        return HtmlUtils.getAppointColorAndSizeOfText(sorce, color, sizeOfpx);
    }

    public void jsPathSorter(ArrayList<String> path) {
        JsToolsHouseKeeper.setJsPath(path);
    }

    public String getWebContent() {
        return JsToolsHouseKeeper.getWebContent();
    }

    public void initJsToolBox(Context context, String htmlContent) {
        JsToolsHouseKeeper.JsToolBox(context, htmlContent);
    }

    public WebViewProxy getJsBoxProxy() {
        return JsToolsHouseKeeper.getJsBoxProxy();
    }

    public String  getJsBoxBridgeKey() {
        return JsToolsHouseKeeper.getAliasKey();
    }

    public String  getBridgeSource(boolean isShowLog) {
         return JsToolsHouseKeeper.getJavaToJsDynamicsignalcommunicationorgsource(isShowLog);
    }


}
