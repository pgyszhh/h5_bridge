package com.shuiyinhuo.component.mixdev.utils.html;

import android.content.Context;
import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.cnf.comm.StaticConfig;
import com.shuiyinhuo.component.mixdev.jinbean.WebConfig;
import com.shuiyinhuo.component.mixdev.locationmanager.JavaToJsManager;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.WebViewProxy;

import java.util.ArrayList;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/19 0019
 * @ Description：java调用js转接方法
 * =====================================
 */
public class JsToolsHouseKeeper {

    private static final String logTag="SHOWLOG_TAG";
    //工具类java 与js 通信桥接源码    默认的桥接内容 +logTag+
    public static final String JAVA_TO_JS_DYNAMICSIGNALCOMMUNICATIONORGSOURCE = "var bridegCarry={java_bridgeTag:\"\",isShowMsg:" +logTag+"};function java_vip(a){bridegCarry.isShowMsg=getState(bridegCarry.isShowMsg);if(!isEmpty(a)){bridegCarry.java_bridgeTag=a;if(bridegCarry.isShowMsg){_java_ShowToast(\"工具类桥接设置成功\\n middlewareName :  \"+bridegCarry.java_bridgeTag)}}else{if(bridegCarry.isShowMsg){_java_ShowToast(\"工具类 桥接设置失败,java 无法与 js 通信\")}}}function getState(a){if(typeof(a)==\"boolean\"){return a}else{if(!isEmpty(a+\"\")){return a+\"\"==\"true\"}else{return false}}}function com_check(a,b){return window[bridegCarry.bridgeTag].runOnAndroidCheck(a,b)}function com_appValueFromJava(a,b){return window[bridegCarry.bridgeTag].runOnAndroidForJsRequestValues(a,b)}function ToolsJavaCallJsRequestBackValuesBridge(a){var j=null;var g=null;var i=null;var d=null;if(!checkException(a)){var e=js_StrToObj(a);j=e.methodName;g=e.data}if(!isEmpty(j)){if(!isEmpty(g)){try{var f=\"-v-\";var h=g.indexOf(f);if(h!=-1){var l=g.split(f);if(isEmpty(l)){try{i=window[j]()}catch(k){i=null;d=\"错误信息：\"+k}}else{for(var h=0;h<l.length;h++){var c=l[h];var b=testParse(c);if(!isEmpty(b)){l[h]=b}}try{i=carry_params_caller(j,l);d=null}catch(k){i=null;d=\"错误信息：\"+k}}}else{var b=testParse(g);if(!isEmpty(b)){g=b}try{i=window[j](g);d=null}catch(k){i=null;d=\"错误信息：\"+k}}}catch(k){i=null;d=\"错误信息：\"+k}}else{try{i=window[j]();d=null}catch(k){i=null;d=\"错误信息：\"+k}}middle_backValues(i,d)}else{_java_ShowToast(\"请指定要调用js的方法名\")}}function carry_params_caller(b,d){var a=d.length;var c=null;switch(a){case 1:c=window[b](d[0]);break;case 2:c=window[b](d[0],d[1]);break;case 3:c=window[b](d[0],d[1],d[2]);break;case 4:c=window[b](d[0],d[1],d[2],d[3]);break;case 5:c=window[b](d[0],d[1],d[2],d[3],d[4]);break;case 6:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5]);break;case 7:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6]);break;case 8:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7]);break;case 9:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8]);break;case 10:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9]);break;case 11:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10]);break;case 12:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11]);break;case 13:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12]);break;case 14:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12],d[13]);break;case 15:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12],d[13],d[14]);break;case 16:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12],d[13],d[14],d[15]);break;case 17:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12],d[13],d[14],d[15],d[16]);break;case 18:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12],d[13],d[14],d[15],d[16],d[17]);break;case 19:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12],d[13],d[14],d[15],d[16],d[17],d[18]);break;case 20:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12],d[13],d[14],d[15],d[16],d[17],d[18],d[19]);break}return c}function testParse(e){if(isEmpty(e)){return null}function a(j){try{var h=parseInt(j);if(isEmpty(h)){return false}else{return true}}catch(i){return false}}function b(j){try{var h=parseFloat(j);if(isEmpty(h)){return false}else{return true}}catch(i){return false}}var f=function(h){this.result=false;this.getState_true=function(k){var j=/^true/;var i=k.match(j);return !isEmpty(i)};this.getState_false=function(k){var j=/^false$/;var l=RegExp(j);var i=k.match(j);return !isEmpty(i)};this._test=function(){if(this.getState_true(this.data)){this.result=true;return true}else{if(this.getState_false(this.data)){this.result=false;return true}else{this.result=null;return false}}};if(this instanceof f){this.data=h}else{return new f(h)}};var g=/^\\d+$/;var d=g.test(e.trim());if(d){if(b(e)){return parseFloat(e)}else{if(a(e)){return parseInt(e)}}}else{var c=f(e);if(typeof(e)==\"boolean\"){return e}else{if(c._test()){return c.result}else{return null}}}}function middle_backValues(d,b){if(isEmpty(d)&&isEmpty(b)){var c={err:b};try{window[bridegCarry.java_bridgeTag].runOnAndroidWhenGoHomeFromJsWithVales(null,null)}catch(a){_java_ShowToast(\"方法调用异常：\",\"中间件 key = \"+bridegCarry.bridgeTag+\"\\n\\n错误信息：\"+a)}}else{if(isEmpty(d)&&!isEmpty(b)){var c={err:b};try{window[bridegCarry.java_bridgeTag].runOnAndroidWhenGoHomeFromJsWithVales(null,js_ObjToStr(b))}catch(a){_java_ShowToast(\"方法调用异常：\",\"中间件 key = \"+bridegCarry.bridgeTag+\"\\n\\n错误信息：\"+a)}}else{var c={err:b};try{window[bridegCarry.java_bridgeTag].runOnAndroidWhenGoHomeFromJsWithVales(js_ObjToStr(d),null)}catch(a){_java_ShowToast(\"方法调用异常：\",\"中间件 key = \"+bridegCarry.bridgeTag+\"\\n\\n错误信息：\"+a)}}}}function jsCallerByFunName(b,a){if(!isEmpty(b)){if(isEmpty(a)){window[b]()}else{window[b]()(a)}}}function isEmpty(a){if(typeof(a)==\"number\"){return isNaN(a)}else{if(typeof(a)==\"boolean\"){return false}else{if(typeof(a)==\"function\"){return false}}}return a==\"null\"||a==\"\"||a==null||a==\"undefined\"||typeof(a)==\"undefined\"\n" +
            "}function js_StrToObj(data){if(!checkException(data)){return eval(\"(\"+data+\")\")}}function checkException(data){try{var data=eval(\"(\"+data+\")\");return false}catch(err){_java_ShowToast(\"异常信息：\"+err);return true}}function JavaCallJsRequestBackValuesBridge(a){AndroidCallJsRequestBackValuesBridge(a,bridegCarry.java_bridgeTag)}function _java_ShowToast(a){window[bridegCarry.java_bridgeTag].runOnAndroidForToast(a)}function js_ObjToStr(a){return JSON.stringify(a)};";



    //最终要加载进页面的js配置源码
    private static String HTML_SOURCE="";

    //主文件中最终要被替换成js配置的位置
    private static final String RPTAT ="RPJSContent";
    // 生成js引入路径的文件摸板
    private static final String RPJS="RPJS";

    // 页面摸板
    private static String orgHtmlTag ="<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\">"+RPTAT+"</head><body></body></html> ";

    //js路径摸板
    private static String jsSourceTag ="<script src=\""+RPJS+"\" charset=\"utf-8\"></script>";

    //js 加载代理
    private static WebViewProxy mJsBoxProxy=null;

    //标签
    private static final int tool_dynamic_Tag_Name=JAVA_TO_JS_DYNAMICSIGNALCOMMUNICATIONORGSOURCE.hashCode();

    // java与js通信桥接名字
    private static String aliasKey="";

    //通过动态的js文件生成引入路径
    public static void setJsPath(ArrayList<String > jsPaths){
        if (EmptyAndSizeUtils.isNotEmpty(jsPaths)){
            String allUrl =appendJsFileTogether(jsPaths);
            JNILog.e("js工具类最终路径："+allUrl+"\n");
            HTML_SOURCE = orgHtmlTag.replaceAll(RPTAT,allUrl);
        }else {
            JNILog.e("当前 js文件为空,文件均无法使用：");
        }

    }


    private static String appendJsFileTogether(ArrayList<String> args){
        String allUrl="";
        for (int i=0;i<args.size();i++){
            String url = args.get(i);
            String mJsUrl = WebConfig.getJsUrl(url);
            allUrl += jsSourceTag.replaceAll(RPJS,mJsUrl);
        }
        return allUrl;
    }


    /**
     *  获取 生成的静态页面内容
     * @return
     */
    public static String getWebContent(){
        if (EmptyAndSizeUtils.isNotEmpty(HTML_SOURCE)){
            return HTML_SOURCE;
        }else {
            JNILog.e("当前Web内容为空...");
            return null;
        }
    }

    /**
     *  加载html到Webview，实现java与js通信
     * @param context
     * @param htmlContent
     */
    public static void JsToolBox(Context context,String htmlContent){
        if (!EmptyAndSizeUtils.isNotEmpty(htmlContent)){
            JNILog.e("加载失败，js工具源码无效");
            return;
        }
        WebView mWebView = new WebView(context);
        if (EmptyAndSizeUtils.isEmpty(mJsBoxProxy)){
            mJsBoxProxy = new WebViewProxy(context,mWebView);
        }
        mJsBoxProxy.loadPageSource(htmlContent);
        JNILog.e("------------------------工具类网页内容开始---------------------------------");
        JNILog.e(htmlContent);
        JNILog.e("------------------------工具类网页结束--------------------------------------");
    }

    /**
     * 获得代理
     * @return
     */
    public static WebViewProxy getJsBoxProxy(){
        return mJsBoxProxy;
    }

    public static String getJavaToJsDynamicsignalcommunicationorgsource(boolean isShowMsg){
        return  JAVA_TO_JS_DYNAMICSIGNALCOMMUNICATIONORGSOURCE.replaceAll(logTag,isShowMsg+"");
    }

    /**
     * 获取通信别名
     * @return
     */
    public static String getAliasKey(){
        if (EmptyAndSizeUtils.isEmpty(aliasKey)){
            aliasKey  = UtilsManager.getInstance().getBridgeTagFactory().getRandomTagNameForJsUtils();
        }
        return aliasKey;
    }


}
