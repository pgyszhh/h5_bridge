# h5_bridge


### android与h5交互框架，交互方式已经定义好，通过动态注入js：

1、可通过预定义好的函数通过js直接调用原生函数，也可通过js调用原生有返回值的函数。

2、在原生函数中可以通过js方法名【参数可选】直接调用加载的h5页面中js方法。

3、可以将js文件作为工具，通过入口动态注入并且可根据函数名【】参数可选】调用js方法。

4、可以实现在原生中动态的调用js中有返回值的方法；


 

 

  
### 引入方式：
    implementation files('libs/h5_bridge.jar')
    
    
    
    
### 使用
   + 直接继承 ProxyWebViewActivity类，复写方法，必须有一下设置
   ```
       @Override
    public WebJNISignalProxy getWebDataProxy() {
        return new WebJNISignalProxy() {
            @Override
            public void commDispatcher(String Alias, String tagName, String data) {
                // js回调方法
            }

            @Override
            public boolean checkForJsCall(String Alias, String tagName, String _json) {
                return false;
            }

            @Override
            public void submit(String Alias, String tagName, String _json) {

            }
        };
    }
```
    
    
    
  **绑定组件和主布局**
    ```
        @Override
    public void setLayoutResConfig(LayoutConfig config) {
        config.bindMainLayout(R.layout.layout);
        config.bindWebViewId(webId,url);
        config.bindWebView();
        config.bindUrlToWebView();
    }
    ```
    
    
   **单个Webview时**
  ```
  
      @Override
    public String getPageNameOrUrl() {
        return "login.html";
    }
   ```
  可以只传递文件的名字，默认文件位置是在：
  ```assets-->web_config--->page中```,可以通过 **StaticConfig**配置文件修改
   
    
    
   ------------------------------------------------------------------------------------
   ### html中可直接使用的函数介绍（无需关心函数，app启动后已经完成动态注入）
   
   ```
   
   /**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/26 0026
 * @ Description：
 *
 *
 *            此文件只是为了说明 js 中可以掉调用
 *            与 Android 交互的方法，这些方法在页面
 *            加载完成后会动态注入到当前页面，直接
 *            调用即可
 *
 *            ---------------------------------
 *            注意：该文件不必导入任
 *                  何网页，只是做函数说明用
 *            ---------------------------------
 *
 *
 *            获取当前页面与原生通信别名：
 *                 ----------------------
 *                   bridegCarry.bridgeTag
 *                 ----------------------
 *
 * =====================================
 */





/**
 *  调用原生toast
 * @param jni_args  要显示的内容
 *
 *  刚方法与原生的   public void runOnAndroidForToast(String msg) 对应，不需要复写
 */
function com_ShowToast(jni_args){};





/**
 *  调用原生要提交方法
 * @param tagName 区别标签名，区分多个Webview
 * @param data  提交的数据，一般是json
 *
 *
 * --------------------------------------------------------------------
 *   与原生对应的方方法：
 *       public void   submit(String Alias,String tagName,String _json);
 *
 */
function com_submit(tagName, data) {}





/**
 *  调用原生校验方法，返回bool值
 * @param tagName 区别标签名，区分多个Webview
 * @param data  校验参数数据，一般是json
 *
 * --------------------------------------------------------------------
 *   与原生对应的方方法：
 *       public boolean   checkForJsCall(String Alias,String tagName,String _json);
 */
function com_check(tagName, data) {}



/**
 *  调用原生图片上传的方法
 * @param tagName  区别标签名，区分多个Webview
 * @param index  在页面中某个位置的索引，将在回
 *         显图片时可传回来作为标记
 *
 * --------------------------------------------------------------------
 *   与原生对应的方方法：
 *       public void startUploadImage(String Alias,String targetName, String index)
 */
function com_uploadImage(tagName, index) {}




/**
 *  调用原生中的方法，并且拿到返回值 的回调方法
 * @param tagName  区别标签名，区分多个Webview
 * @param data  参数，一般是json，也可以是单只数据
 *
 * --------------------------------------------------------------------
 *  与原生对应的方方法：
 *     public String collectionValuePassToJs(String Alias,String targetName, String _json)
 */
function com_appValueFromJava(tagName, data) {}




/**
 *  魔法方法：也成为万能方法
 *
 *  -----------------------------------------------------
 *  公共回调 方法  和原生 约定 tagNane 作为数据判断来源
 *
 * @param tagName  区别标签名，区分多个Webview
 *
 * @param data  参数，js传递给原生参数，单个参数直接传，
 *               多个参数可以用json格式传递
 *
 *
 *  除了有回调返回值的方法，通过 tagName 区分来源，基本可满足
 *  所有js端向Android原生端交互需求
 *
 *
 * --------------------------------------------------------------------
 *  与原生对应的方方法：
 *     public void   commDispatcher(String Alias,String tagName, String data);
 */
function com_commCaller(tagName, data) {}





/**
 *   公共回调方法
 *   与com_commCaller 方法是同一只，只是无数据参数，
 *   只有别名和与原生约定 tagNane 作为数据判断来源
 *
 * @param tagName   区别标签名，区分多个Webview
 *
 *--------------------------------------------------------------------
 *   与原生对应的方方法：
 *     public void   commDispatcher(String Alias,String tagName, String data);
 */
function com_commCallerNoArgs(tagName) {}




/**
 *   与原生交互，显示弹窗的方法
 *
 * @param tagName 区别标签名，区分多个Webview
 * @param jni_args  传递给原生的内容，可以为字符串，
 * 也可以为json，与实际情况而定
 *
 * --------------------------------------------------------------------
 * 与原生对应的方方法：
 *     public void showMsgWindow(String Alias,String tagName, String content)
 */
function com_ShowWindow(tagName, jni_args) {}

/**
 * 关闭当前Activity
 */
function com_closeActivity() {}

```
