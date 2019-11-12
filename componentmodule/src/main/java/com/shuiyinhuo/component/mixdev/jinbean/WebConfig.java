package com.shuiyinhuo.component.mixdev.jinbean;


import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：H5 网页配置信息
 * =====================================
 */
public  class WebConfig {
    // 网页配置路径
    private static final String BASE_URL_PREFIX_ = "file:///android_asset/";
    private static final String BASE_WEB_URL_PREFIX_ =BASE_URL_PREFIX_+ "web_config/";
    /**
     * 网页路径配置
     */
    public static  String PAGE_URL_PREFIX_ = BASE_WEB_URL_PREFIX_+"page/";
    /**
     * 后缀
     */
    private static final String URL_SUFFIX = ".html";
    private static final String URL_SUFFIX2 = ".htm";
    private static final String URL_SUFFIX3 = ".xhtml";
    private static final String URL_SUFFIX4 = ".xhtm";
    private static final String JS_SUFFIX = ".js";
    public static String url;


    public static String getPageUrl(String htmlName) {
        if (EmptyAndSizeUtils.isEmpty(htmlName)){
            return null;
        }
        htmlName=juderUrl(htmlName);
        if (isOuterUrl(htmlName)) {
            return htmlName;
        }
        String url = "";
        if (EmptyAndSizeUtils.isNotEmpty(htmlName)) {
            if (isLegitimateSuffix(htmlName)) {
                url = PAGE_URL_PREFIX_ + htmlName;
            } else {
                url = PAGE_URL_PREFIX_ + htmlName + URL_SUFFIX;
            }
        } else {
            JNILog.e("地址为空");
            return "";
        }
        JNILog.e(url);
        return url;
    }

    private static String  juderUrl(String url){
        if (EmptyAndSizeUtils.isNotEmpty(url)){
            if (url.startsWith("android_asset")){
                url=url.replaceAll("android_asset","file:///android_asset");
            }
        }
        return url;
    }

    public String getRelUrl() {
        url = setPageUrl();
        if (EmptyAndSizeUtils.isNotEmpty(url)) {
            if (url.contains(PAGE_URL_PREFIX_)) {
                return url;
            } else {
                url = getPageUrl(url);
            }
        }
        return url;
    }

    /**
     * 是否为合法后缀
     *
     * @param url
     * @return
     */
    public static boolean isLegitimateSuffix(String url) {
        if (EmptyAndSizeUtils.isEmpty(url)) {
            JNILog.e("url文件名不合法");
            return false;
        }
        return url.endsWith(URL_SUFFIX) || url.endsWith(URL_SUFFIX2) || url.endsWith(URL_SUFFIX3) || url.endsWith(URL_SUFFIX4);
    }


    public static boolean isOuterUrl(String url) {
        return url.contains("file:///android_asset/") || url.contains("file:///");
    }

    public  void setStaticWebUrl(String _url) {
        url = _url;
    }

    public String setPageUrl() {
        return null;
    }


    /**
     * 判断路径中的js文件是否合法
     * @param jsPath 文件或者路径
     * @return
     */
    public static boolean isLegitimateSuffixForJs(String jsPath) {
        if (EmptyAndSizeUtils.isEmpty(jsPath)) {
            JNILog.e("js文件名不合法");
            return false;
        }
        return jsPath.endsWith(JS_SUFFIX);
    }

    /**
     *  通过文件名或者 路径 得到最终的js路径
     *   1、支持sdcard中的文件路径
     *   2、支持在默认js位置通过文件名获取路径（可包含后缀后不包含）
     *   3、也可以是网络地址
     *
     * @param urlOrFileName 文件名或者路径
     * @return
     */
    public static String getJsUrl(String urlOrFileName) {
        if (EmptyAndSizeUtils.isNotEmpty(urlOrFileName)) {
            if (isOuterUrl(urlOrFileName)) {
                if (isLegitimateSuffixForJs(urlOrFileName)) {
                    JNILog.e("合法 js 文件 ");
                    return urlOrFileName;
                } else {
                    JNILog.e("----- 非法法 js 文件,不可用: " + urlOrFileName);
                }
            } else {
                String js_url = "";
                if (!isLegitimateSuffixForJs(urlOrFileName)) {
                    /**

                     *     xx.js
                     *     xx/xx.js
                     *     xx/xx/xx.js
                     *
                     *     /xx.js
                     *     /xx/xx.js

                      */

                    String[] mSplit = urlOrFileName.split("/");
                    if (urlOrFileName.startsWith("/")&& mSplit.length==2){///xx.js
                        js_url = BASE_WEB_URL_PREFIX_ + "js/" + mSplit[1] + JS_SUFFIX;
                    }else if (urlOrFileName.startsWith("/")&& mSplit.length>2){
                        js_url = BASE_WEB_URL_PREFIX_ + urlOrFileName.substring(1,urlOrFileName.length()) + JS_SUFFIX;
                    }else {
                        if (mSplit.length>=2){
                            js_url = BASE_WEB_URL_PREFIX_ + urlOrFileName + JS_SUFFIX;
                        }else {
                            js_url = BASE_WEB_URL_PREFIX_ + "js/" + urlOrFileName + JS_SUFFIX;
                        }
                    }
                   // if (urlOrFileName.split("/"))
                    //js_url = PAGE_URL_PREFIX_ + "/js/" + urlOrFileName + JS_SUFFIX;
                    return js_url;
                } else {
                    js_url = BASE_WEB_URL_PREFIX_ + "js/" + urlOrFileName;
                }
                JNILog.e("通过文件名加载默认位置 js 文件：" + url);
                return js_url;
            }
        } else {
            JNILog.e("当前 js 路径为空");
            return "";
        }
        return "";
    }

}
