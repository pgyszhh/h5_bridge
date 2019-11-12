/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：交互代理
 * =====================================
 */


window.onload = function () {
    htmlMethodOne();
};

/**
 * 动态告诉 js 使用的桥接名是什么
 *  这个方法不能被任何一方自动调用，只能自定调用
 */

var bridegCarry = {
    bridgeTag: "",
    isShowMsg: false
};


/**
 * 与js通信 桥接注册vip方法
 * @param key
 */
function vip(key) {
    bridegCarry.isShowMsg = getState(bridegCarry.isShowMsg);
    if (!isEmpty(key)) {
        bridegCarry.bridgeTag = key;
        if (bridegCarry.isShowMsg) {
            com_ShowToast("桥接设置成功 middlewareName:  " + bridegCarry.bridgeTag);
        }
    } else {
        if (bridegCarry.isShowMsg) {
            com_ShowToast("桥接设置失败,无法与js通信")
        }
    }
    /**
     * 回调加载数据方法
     */
    htmlMethodOne();
};

/**
 * 判断js的方法
 * @param data
 * @returns {boolean}
 */
function getState(data) {
    if (typeof (data) == "boolean") {
        return data;
    } else if (!isEmpty(data + "")) {
        return data + "" == "true";
    } else {
        return false;
    }

};

//加载数据初始化方法
function htmlMethodOne() {
    window[bridegCarry.bridgeTag].runOnAndroidJavaScript();
}

//关闭Activity
function com_closeActivity() {
    window[bridegCarry.bridgeTag].runOnAndroidForCloseActivity();
};


/**
 toast 回调方法
 */
function com_ShowToast(jni_args) {
    //window.injs.runOnAndroidForToast(jni_args);
    window[bridegCarry.bridgeTag].runOnAndroidForToast(jni_args);
};


/**
 toast 回调方法
 */
function com_submit(tagName, data) {
    //window.injs.runOnAndroidForSubmit(tagName, data);
    window[bridegCarry.bridgeTag].runOnAndroidForSubmit(tagName, data);
};

/**
 检测 回调方法
 */
function com_check(tagName, data) {
    // return window.injs.runOnAndroidCheck(tagName, data);
    return window[bridegCarry.bridgeTag].runOnAndroidCheck(tagName, data);
};

/**
 图片上传 回调方法
 */
function com_uploadImage(tagName, index) {
    //window.injs.runOnAndroidForImage(tagName, index);
    window[bridegCarry.bridgeTag].runOnAndroidForImage(tagName, index);
};

/**
 调用java中的方法，并且拿到返回值 的回调方法
 */
function com_appValueFromJava(tagName, data) {
    //window.injs.runOnAndroidForImage(tagName, index);
    return window[bridegCarry.bridgeTag].runOnAndroidForJsRequestValues(tagName, data);
};

/**
 公共回调 方法
 和原生 约定 tagNane 作为数据判断来源
 */
function com_commCaller(tagName, data) {
    var dataTemp = "";
    if (isEmpty(data)) {
        dataTemp = "";
    } else {
        dataTemp = data;
    }
    //window.injs.runOnAndroidCommonDispatcher(tagName, dataTemp);
    window[bridegCarry.bridgeTag].runOnAndroidCommonDispatcher(tagName, dataTemp);
};
/**
 公共回调 方法
 和原生 约定 tagNane 作为数据判断来源
 */
function com_commCallerNoArgs(tagName) {
    com_commCaller(tagName, null);
};


/**
 toast 回调方法
 */
function com_ShowWindow(tagName, jni_args) {
    //window.injs.runOnAndroidForWindow(jni_args);
    window[bridegCarry.bridgeTag].runOnAndroidForWindow(tagName, jni_args);
};


/**
 无参数的回调方法：

 bridgeNane：为约定的 JNI 的名字
 jni_method:原生定义的方法名

 */
function com_Caller(bridgeNane, jni_method) {
    com_ArgsCaller(bridgeNane, jni_method, "undefined")
};


/**
 带参数的回调方法：

 bridgeNane：为约定的 JNI 的名字
 jni_method:原生定义的方法名

 */
function com_ArgsCaller(bridgeNane, jni_method, jni_args) {
    array = wrapperParams(bridgeNane, jni_method, jni_args)
    mainCaller(array)
};


/**
 *   Android 调用js 请求返回值的桥接
 * @param calledJsMethod  表调用的js方法
 * @param dataOrgJson  传进来的数据
 * @constructor AndroidCallJsRequestBackValuesBridge
 */
function AndroidCallJsRequestBackValuesBridge(orgData) {
    var calledJsMethod = null;
    var dataOrgJson = null;
    var backValues = null;
    var err = null;
    if (!checkException(orgData)) {
        var obj = js_StrToObj(orgData);
        calledJsMethod = obj.methodName;
        dataOrgJson = obj.data;
    }
    ;
    if (!isEmpty(calledJsMethod)) {
        if (!isEmpty(dataOrgJson)) {
            try {
                var knf = "-v-";
                var index = dataOrgJson.indexOf(knf);
                if (index != -1) {
                    var parms = dataOrgJson.split(knf);
                    if (isEmpty(parms)) {
                        try {
                            backValues = window[calledJsMethod]();
                            err = null;
                        } catch (error) {
                            err = "错误信息：" + error;
                        }
                    } else {
                        for (var index = 0; index < parms.length; index++) {
                            var willTest = parms[index];
                            var testParams = testParse(willTest)
                            if (!isEmpty(testParams)) {
                                parms[index] = testParams;
                            }
                        }
                        ;
                        //多参数封装调用
                        try {
                            backValues = carry_params_caller(calledJsMethod, parms);
                            err = null;
                        } catch (error) {
                            err = "错误信息：" + error;
                        }
                        ;
                    }
                } else {
                    var testParams = testParse(dataOrgJson)
                    if (!isEmpty(testParams)) {
                        dataOrgJson = testParams;
                    }
                    ;
                    try {
                        backValues = window[calledJsMethod](dataOrgJson);
                        err = null;
                    } catch (error) {
                        err = "错误信息：" + error;
                    }
                    ;
                }
            } catch (error) {
                backValues = null;
                err = "错误信息：" + error;
            }
            ;

        } else {
            try {
                backValues = window[calledJsMethod]();
                err = null;
            } catch (error) {
                backValues = null;
                err = "错误信息：" + error;
            }
            ;
        }
        ;
        middle_backValues(backValues, err);
    } else {
        if (isEmpty(bridge_key)) {
            com_ShowToast("请指定要调用js的方法名");
        } else {
            _java_ShowToast("请指定要调用js的方法名");
        }

    }
};

/**
 *  处理Android调用js中有返回值方法
 * @param calledJsMethod
 * @param params
 * @returns {*}
 */
function carry_params_caller(calledJsMethod, params) {
    var len = params.length;
    var backValues = null;
    switch (len) {
        case 1:
            backValues = window[calledJsMethod](params[0]);
            break;
        case 2:
            backValues = window[calledJsMethod](params[0], params[1]);
            break;
        case 3:
            backValues = window[calledJsMethod](params[0], params[1], params[2]);
            break;
        case 4:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3]);
            break;
        case 5:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4]);
            break;
        case 6:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4], params[5]);
            break;
        case 7:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4], params[5], params[6]);
            break;
        case 8:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
            break;
        case 9:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8]);
            break;
        case 10:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9]);
            break;
        case 11:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10]);
            break;
        case 12:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10], params[11]);
            break;
        case 13:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10], params[11], params[12]);
            break;
        case 14:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10], params[11], params[12], params[13]);
            break;
        case 15:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10], params[11], params[12], params[13], params[14]);
            break;
        case 16:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10], params[11], params[12], params[13], params[14], params[15]);
            break;
        case 17:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10], params[11], params[12], params[13], params[14], params[15], params[16]);
            break;
        case 18:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10], params[11], params[12], params[13], params[14], params[15], params[16], params[17]);
            break;
        case 19:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10], params[11], params[12], params[13], params[14], params[15], params[16], params[17], params[18]);
            break;
        case 20:
            backValues = window[calledJsMethod](params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10], params[11], params[12], params[13], params[14], params[15], params[16], params[17], params[18], params[19]);
            break;
    }

    return backValues;
};

/**
 * 测试参数
 * @param orgDate
 * @returns {*}
 */
function testParse(orgDate) {
    if (isEmpty(orgDate)) {
        return null;
    }
    function test_parseInt(orgData) {
        try {

            var res = parseInt(orgData);
            if (isEmpty(res)) {
                return false;
            } else {
                return true;
            }
        }
        catch (err) {
            return false;
        }
    };

    function test_parseFl(orgData) {
        try {
            var result = parseFloat(orgData);
            if (isEmpty(result)) {
                return false;
            } else {
                return true;
            }
        }
        catch (err) {
            return false;
        }
    };

    /**
     * booll 判断工具
     * @param test
     * @returns {bool_manner}
     */
    var bool_manner = function (test) {
        this.result = false;
        this.getState_true = function (data) {
            var reg = /^true/i;
            /*var partten = RegExp(reg);
             partten.test(data);
             var res=RegExp.$1;*/
            var res = data.match(reg);
            return !isEmpty(res);
        };

        this.getState_false = function (data) {
            var reg = /^false$/i;
            var partten = RegExp(reg);
            /*  partten.test(data);
             var res=RegExp.$1;*/
            var res = data.match(reg);
            return !isEmpty(res);
        };

        this._test = function () {
            if (this.getState_true(this.data)) {
                this.result = true;
                return true;
            } else if (this.getState_false(this.data)) {
                this.result = false;
                return true;
            } else {
                this.result = null;
                return false;
            }
            ;
        }
        if (this instanceof bool_manner) {
            this.data = test;
        } else {
            return new bool_manner(test);
        }
    };

    var regx = /^\d+$/;
    var tRest = regx.test(orgDate.trim());
    if (tRest) {
        if (test_parseFl(orgDate)) {
            return parseFloat(orgDate)
        } else if (test_parseInt(orgDate)) {
            return parseInt(orgDate)
        }
    } else {
        var boolmanner = bool_manner(orgDate);
        if (typeof (orgDate) == "boolean") {
            return orgDate;
        } else if (boolmanner._test()) {
            return boolmanner.result;
        } else {
            return null;
        }
    }

};


/**
 *  此方法 不被任何以防手动调用，为中间桥接转接调用
 *
 * 原生掉 js 拿到返回值后，回传给原生 回调方法
 *
 */

function middle_backValues(backValues, error) {
    if (isEmpty(backValues) && isEmpty(error)) {
        //window.injs.runOnAndroidWhenGoHomeFromJsWithVales(null, null);
        var errInfo = {err: error};//js_ObjToStr(errInfo)
        try {
            window[bridegCarry.bridgeTag].runOnAndroidWhenGoBackHomeFromJsWithVales(null, null);
        } catch (errs) {
            com_ShowWindow("方法调用异常：", "中间件 key = " + bridegCarry.bridgeTag + "\n\n错误信息：" + errs);
        }
    } else if (isEmpty(backValues) && !isEmpty(error)) {
        //window.injs.runOnAndroidWhenGoHomeFromJsWithVales(null, error);
        var errInfo = {err: error};//js_ObjToStr(errInfo)
        try {
            window[bridegCarry.bridgeTag].runOnAndroidWhenGoBackHomeFromJsWithVales(null, js_ObjToStr(errInfo));
        } catch (errs) {
            com_ShowWindow("方法调用异常：", "中间件 key = " + bridegCarry.bridgeTag + "\n\n错误信息：" + errs);
        }

    } else {
        //window.injs.runOnAndroidWhenGoHomeFromJsWithVales(js_ObjToStr(backValues), null);
        var errInfo = {err: error};//js_ObjToStr(errInfo)
        try {
            window[bridegCarry.bridgeTag].runOnAndroidWhenGoBackHomeFromJsWithVales(js_ObjToStr(backValues), null);
        } catch (errs) {
            com_ShowWindow("方法调用异常：", "中间件 key = " + bridegCarry.bridgeTag + "\n\n错误信息：" + errs);
        }
    }
};


/**
 *   主要负责 js 与 原生 交互方法
 * @param entity 携带参数的 js 对象
 */
function mainCaller(entity) {
    ps = entity.params;
    if (ps == "" || ps == "null" || ps == "undefined" || typeof (ps) == "undefined") {
        window[entity.jin_name][entity.method]()
    } else {
        window[entity.jin_name][entity.method](ps)
    }
};

/**
 * js 自己调用
 * @param funName
 * @param args
 */
function jsCallerByFunName(funName, args) {
    if (!isEmpty(funName)) {
        if (isEmpty(args)) {
            window[funName]()
        } else {
            window[funName]()(args)
        }
    }
};


/**
 *  封装 参数
 * @param bridgeNane  jni 别名
 * @param jni_method  原生的方法名
 * @param jni_args  原生参数
 * @returns {*}
 */
function wrapperParams(bridgeNane, jni_method, jni_args) {
    list = new Array()
    list[0] = bridgeNane;
    list[1] = jni_method;
    list[2] = jni_args;
    // alert("bridgeNane = "+bridgeNane+"\n jni_method = " +jni_method+"\n jni_args = "+jni_args)
    return wrapperEntity(list);
};

/**
 *
 * @param args  存放原始数据的数组
 * @returns {Object} 返回携带封装参数的 js 对象
 */
function wrapperEntity(args) {
    if (args != "null" && typeof (args) != "undefined" && args != "undefined") {
        caller_proxy = new Object();
        caller_proxy.jin_name = args[0]
        caller_proxy.method = args[1]
        caller_proxy.params = args[2]
        return caller_proxy;
    } else {
        com_ShowToast("调用失败");
    }
};


function addToClass(targe, className) {
    targe.addClass(className);
};

function removeToClass(targe, className) {
    targe.removeClass(className);
};


function isEmpty(data) {
    if (typeof (data) == "number") {
        return isNaN(data);
    } else if (typeof (data) == "boolean") {
        return false;
    } else if (typeof (data) == "function") {
        return false;
    }
    return data == "null" || data == "" || data == null || data == "undefined" || typeof (data) == "undefined";
};

/**
 * 类型转换，将str类型转换为 对象
 */
function js_StrToObj(data) {
    if (!checkException(data)) {
        return eval('(' + data + ')');
    }
};


function js_wrapperAndroidPathToH5Paht(data) {
    var file = data.toString();
    var temp = "file://";
    var index = file.indexOf(temp);
    if (index != -1) {
        try {
            //com_ShowToast("包含文件协议")
        } catch (err) {
            //com_ShowToast("err")
        }
        return data;
    } else {
        //com_ShowToast("不包含文件协议")
        return temp + data;
    }
};
/**
 * 将对象转换为字符串
 * @param data
 * @returns {string}
 */
function js_ObjToStr(data) {

    /*if (!isEmpty(data) && ( typeof (data) == "object" || typeof (data) == "string")) {*/
    return JSON.stringify(data);
    /* }else {
     com_ShowToast("判断失误："+data+"   类型："+typeof (data)+"\n isEmpty(data):"+isEmpty(data));
     }*/
};


function checkException(data) {
    try {
        //运行代码
        var data = eval('(' + data + ')');
        return false;
    } catch (err) {
        //处理错误
        com_ShowToast("异常信息：" + err)
        return true;
    }
};
