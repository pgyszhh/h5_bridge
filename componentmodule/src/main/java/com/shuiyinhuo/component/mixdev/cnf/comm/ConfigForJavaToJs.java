package com.shuiyinhuo.component.mixdev.cnf.comm;

import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.dispatcher.dispatch.MessageDisapther;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.FunctionHunter;
import com.shuiyinhuo.component.mixdev.jinbean.pro.Judger;
import com.shuiyinhuo.component.mixdev.jinbean.pro.UrlBinder;
import com.shuiyinhuo.component.mixdev.locationmanager.JavaToJsManager;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.ToastManager;

import java.util.ArrayList;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/22 0022
 * @ Description：通用配置文件,主工程Android与Webview交互
 * =====================================
 */
public class ConfigForJavaToJs {
    private static final String RPTAG="LOGTAG_";
    public static final int dynamic_Tag_Name="dynamic_Tag".length()+0x78552222;
    // + RPTAG +
    private static final String DynamicSignalCommunicationOrgSouce="var bridegCarry={bridgeTag:\"\",isShowMsg:"+ RPTAG +"};function vip(a){bridegCarry.isShowMsg=getState(bridegCarry.isShowMsg);if(!isEmpty(a)){bridegCarry.bridgeTag=a;if(bridegCarry.isShowMsg){com_ShowToast(\"桥接设置成功 middlewareName:  \"+bridegCarry.bridgeTag)}}else{if(bridegCarry.isShowMsg){com_ShowToast(\"桥接设置失败,无法与js通信\")}}htmlMethodOne()}function getState(a){if(typeof(a)==\"boolean\"){return a}else{if(!isEmpty(a+\"\")){return a+\"\"==\"true\"}else{return false}}}function htmlMethodOne(){window[bridegCarry.bridgeTag].runOnAndroidJavaScript()}function com_closeActivity(){window[bridegCarry.bridgeTag].runOnAndroidForCloseActivity()}function com_ShowToast(a){window[bridegCarry.bridgeTag].runOnAndroidForToast(a)}function com_submit(a,b){window[bridegCarry.bridgeTag].runOnAndroidForSubmit(a,b)}function com_check(a,b){return window[bridegCarry.bridgeTag].runOnAndroidCheck(a,b)}function com_uploadImage(b,a){window[bridegCarry.bridgeTag].runOnAndroidForImage(b,a)}function com_appValueFromJava(a,b){return window[bridegCarry.bridgeTag].runOnAndroidForJsRequestValues(a,b)}function com_commCaller(a,b){var c=\"\";if(isEmpty(b)){c=\"\"}else{c=b}window[bridegCarry.bridgeTag].runOnAndroidCommonDispatcher(a,c)}function com_commCallerNoArgs(a){com_commCaller(a,null)}function com_ShowWindow(b,a){window[bridegCarry.bridgeTag].runOnAndroidForWindow(b,a)}function com_Caller(a,b){com_ArgsCaller(a,b,\"undefined\")}function com_ArgsCaller(b,c,a){array=wrapperParams(b,c,a);mainCaller(array)}function AndroidCallJsRequestBackValuesBridge(a){var j=null;var g=null;var i=null;var d=null;if(!checkException(a)){var e=js_StrToObj(a);j=e.methodName;g=e.data}if(!isEmpty(j)){if(!isEmpty(g)){try{var f=\"-v-\";var h=g.indexOf(f);if(h!=-1){var l=g.split(f);if(isEmpty(l)){try{i=window[j]();d=null}catch(k){d=\"错误信息：\"+k}}else{for(var h=0;h<l.length;h++){var c=l[h];var b=testParse(c);if(!isEmpty(b)){l[h]=b}}try{i=carry_params_caller(j,l);d=null}catch(k){d=\"错误信息：\"+k}}}else{var b=testParse(g);if(!isEmpty(b)){g=b}try{i=window[j](g);d=null}catch(k){d=\"错误信息：\"+k}}}catch(k){i=null;d=\"错误信息：\"+k}}else{try{i=window[j]();d=null}catch(k){i=null;d=\"错误信息：\"+k}}middle_backValues(i,d)}else{if(isEmpty(bridge_key)){com_ShowToast(\"请指定要调用js的方法名\")}else{_java_ShowToast(\"请指定要调用js的方法名\")}}}function carry_params_caller(b,d){var a=d.length;var c=null;switch(a){case 1:c=window[b](d[0]);break;case 2:c=window[b](d[0],d[1]);break;case 3:c=window[b](d[0],d[1],d[2]);break;case 4:c=window[b](d[0],d[1],d[2],d[3]);break;case 5:c=window[b](d[0],d[1],d[2],d[3],d[4]);break;case 6:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5]);break;case 7:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6]);break;case 8:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7]);break;case 9:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8]);break;case 10:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9]);break;case 11:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10]);break;case 12:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11]);break;case 13:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12]);break;case 14:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12],d[13]);break;case 15:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12],d[13],d[14]);break;case 16:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12],d[13],d[14],d[15]);break;case 17:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12],d[13],d[14],d[15],d[16]);break;case 18:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12],d[13],d[14],d[15],d[16],d[17]);break;case 19:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12],d[13],d[14],d[15],d[16],d[17],d[18]);break;case 20:c=window[b](d[0],d[1],d[2],d[3],d[4],d[5],d[6],d[7],d[8],d[9],d[10],d[11],d[12],d[13],d[14],d[15],d[16],d[17],d[18],d[19]);break}return c}function testParse(e){if(isEmpty(e)){return null}function a(j){try{var h=parseInt(j);if(isEmpty(h)){return false}else{return true}}catch(i){return false}}function b(j){try{var h=parseFloat(j);if(isEmpty(h)){return false}else{return true}}catch(i){return false}}var f=function(h){this.result=false;this.getState_true=function(k){var j=/^true/i;var i=k.match(j);return !isEmpty(i)};this.getState_false=function(k){var j=/^false$/i;var l=RegExp(j);var i=k.match(j);return !isEmpty(i)};this._test=function(){if(this.getState_true(this.data)){this.result=true;return true}else{if(this.getState_false(this.data)){this.result=false;return true}else{this.result=null;return false}}};if(this instanceof f){this.data=h}else{return new f(h)}};var g=/^\\d+$/;var d=g.test(e.trim());if(d){if(b(e)){return parseFloat(e)}else{if(a(e)){return parseInt(e)}}}else{var c=f(e);if(typeof(e)==\"boolean\"){return e}else{if(c._test()){return c.result}else{return null}}}}function middle_backValues(d,b){if(isEmpty(d)&&isEmpty(b)){var c={err:b};try{window[bridegCarry.bridgeTag].runOnAndroidWhenGoBackHomeFromJsWithVales(null,null)\n" +
            "}catch(a){com_ShowWindow(\"方法调用异常：\",\"中间件 key = \"+bridegCarry.bridgeTag+\"\\n\\n错误信息：\"+a)}}else{if(isEmpty(d)&&!isEmpty(b)){var c={err:b};try{window[bridegCarry.bridgeTag].runOnAndroidWhenGoBackHomeFromJsWithVales(null,js_ObjToStr(c))}catch(a){com_ShowWindow(\"方法调用异常：\",\"中间件 key = \"+bridegCarry.bridgeTag+\"\\n\\n错误信息：\"+a)}}else{var c={err:b};try{window[bridegCarry.bridgeTag].runOnAndroidWhenGoBackHomeFromJsWithVales(js_ObjToStr(d),null)}catch(a){com_ShowWindow(\"方法调用异常：\",\"中间件 key = \"+bridegCarry.bridgeTag+\"\\n\\n错误信息：\"+a)}}}}function mainCaller(a){ps=a.params;if(ps==\"\"||ps==\"null\"||ps==\"undefined\"||typeof(ps)==\"undefined\"){window[a.jin_name][a.method]()}else{window[a.jin_name][a.method](ps)}}function jsCallerByFunName(b,a){if(!isEmpty(b)){if(isEmpty(a)){window[b]()}else{window[b]()(a)}}}function wrapperParams(b,c,a){list=new Array();list[0]=b;list[1]=c;list[2]=a;return wrapperEntity(list)}function wrapperEntity(a){if(a!=\"null\"&&typeof(a)!=\"undefined\"&&a!=\"undefined\"){caller_proxy=new Object();caller_proxy.jin_name=a[0];caller_proxy.method=a[1];caller_proxy.params=a[2];return caller_proxy}else{com_ShowToast(\"调用失败\")}}function addToClass(b,a){b.addClass(a)}function removeToClass(b,a){b.removeClass(a)}function isEmpty(a){if(typeof(a)==\"number\"){return isNaN(a)}else{if(typeof(a)==\"boolean\"){return false}else{if(typeof(a)==\"function\"){return false}}}return a==\"null\"||a==\"\"||a==null||a==\"undefined\"||typeof(a)==\"undefined\"}function js_StrToObj(data){if(!checkException(data)){return eval(\"(\"+data+\")\")}}function js_wrapperAndroidPathToH5Paht(e){var c=e.toString();var a=\"file://\";var b=c.indexOf(a);if(b!=-1){try{}catch(d){}return e}else{return a+e}}function js_ObjToStr(a){return JSON.stringify(a)}function checkException(data){try{var data=eval(\"(\"+data+\")\");return false}catch(err){com_ShowToast(\"异常信息：\"+err);return true}};";


    private String getDynamicSignalCommunicationOrgSouce(boolean isTag){
            return DynamicSignalCommunicationOrgSouce.replaceAll(RPTAG,isTag+"");
    }

    /**
     * 注册使用
     * 掉用js中的函数
     *
     * @methodName js 中定义的方法名
     */
    public void _register(JavaToJsManager mToJsManager,FunctionHunter hunter, ToastManager toastManager,WebView webView) {
        if (EmptyAndSizeUtils.isNotEmpty(webView)){
            Object mDynamic_tag = webView.getTag(dynamic_Tag_Name);
            if (EmptyAndSizeUtils.isEmpty(mDynamic_tag)){
                if (!EmptyAndSizeUtils.isEmpty(hunter)) {
                    JNILog.e(">>>>>>>>>>>>>>>>>>>>>>>> need dynamic regist js to page");
                    webView.loadUrl("javascript:" + getDynamicSignalCommunicationOrgSouce(StaticConfig.isShowLogin));
                    webView.setTag(dynamic_Tag_Name,true);
                }
            }else {
                JNILog.e(">>>>>>>>>>>>>>>>>>>>>>>> is exist,  need not dynamic regist js to page");
            }

            int tag =0x1255;
            String bridgeName = UtilsManager.getInstance().getBridgeTagFactory().getRandomTagName(tag);
            if (EmptyAndSizeUtils.isNotEmpty(mToJsManager)){
                String mAlias = mToJsManager.getMessageDisapther().getAliasFromWebView(webView);
                ArrayList<UrlBinder> mBinders = mToJsManager.getMessageDisapther().getWebBundleStorager().getBinder(mAlias);
                if (EmptyAndSizeUtils.isNotEmpty(mBinders)){
                    for (UrlBinder binder:mBinders){
                        if (binder.getPageContainer()==webView){
                            bridgeName=binder.getBridgeName();
                            JNILog.e("Net","找到了桥接器...."+bridgeName);
                            break;
                        }
                    }
                }
            }

            // 注册桥接
            Object mTag = webView.getTag(MessageDisapther.webTagName);
            String alias = (String) mTag;
            hunter._registerCallJs(alias,"vip", bridgeName);
        }else {
            if (EmptyAndSizeUtils.isNotEmpty(toastManager)) {
                toastManager.showToast("js动态注册失败，当前Weview 为空");
            }
            JNILog.e(">>>>>>>>>>>>>>>>>>>>>>>>  js动态注册失败，当前Weview 为空");
        }

    }

}
