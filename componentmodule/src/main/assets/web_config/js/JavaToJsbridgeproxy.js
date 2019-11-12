/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：交互代理  此文件主要服务工具类，通过
 *
 *  实例化工具类
 *  JavaCallJsUtils mJsUtils = UtilsManager.getInstance().getJsUtils();
 *   注册js工具类文件
 *   mJsUtils.registJs(mContext,"js文件路径");
 *   调用工具类中的方法
 *   mJsUtils.callJsWithBackValues("工具类中js方法名", OnJSUtilsCarryValuesBackInterface,"参数");
 *
 * =====================================
 */

/************************** 以下是js作为 java工调用方法***************/


/**
 * 动态告诉 js 使用的桥接名是什么
 *  这个方法不能被任何一方自动调用，只能自定调用
 */

var bridegCarry = {
    java_bridgeTag: "",
    isShowMsg: false
};


/**
 * 专用原生调用js 作为工具 桥接注册 vip方法
 * @param key
 */
function java_vip(js_key) {
    bridegCarry.isShowMsg = getState(bridegCarry.isShowMsg);
    if (!isEmpty(js_key)) {
        bridegCarry.java_bridgeTag = js_key;
        if (bridegCarry.isShowMsg) {
            _java_ShowToast("工具类桥接设置成功\n middlewareName :  " + bridegCarry.java_bridgeTag);
        }
        ;
    } else {
        if (bridegCarry.isShowMsg) {
            _java_ShowToast("工具类 桥接设置失败,java 无法与 js 通信");
        }
        ;
    }
    ;
};

function getState(data) {
    if (typeof (data) == "boolean") {
        return data;
    } else if (!isEmpty(data + "")) {
        return data + "" == "true";
    } else {
        return false;
    }
};

/**
 检测 回调方法
 */
function com_check(tagName, data) {
    // return window.injs.runOnAndroidCheck(tagName, data);
    return window[bridegCarry.bridgeTag].runOnAndroidCheck(tagName, data);
};


/**
 调用java中的方法，并且拿到返回值 的回调方法
 */
function com_appValueFromJava(tagName, data) {
    //window.injs.runOnAndroidForImage(tagName, index);
    return window[bridegCarry.bridgeTag].runOnAndroidForJsRequestValues(tagName, data);
};


/**
 *   Android 调用js 请求返回值的桥接
 * @param calledJsMethod  表调用的js方法
 * @param dataOrgJson  传进来的数据
 * @constructor AndroidCallJsRequestBackValuesBridge
 */
function ToolsJavaCallJsRequestBackValuesBridge(orgData) {
    var calledJsMethod = null;
    var dataOrgJson = null;
    var backValues = null;
    var err = null;
    if (!checkException(orgData)) {
        var obj = js_StrToObj(orgData);
        calledJsMethod = obj.methodName;
        dataOrgJson = obj.data;
    }
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
                        } catch (error) {
                            backValues = null;
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

                        //多参数封装调用
                        try {
                            backValues = carry_params_caller(calledJsMethod, parms);
                            err = null;
                        } catch (error) {
                            backValues = null;
                            err = "错误信息：" + error;
                        }
                    }
                } else {
                    var testParams = testParse(dataOrgJson)
                    if (!isEmpty(testParams)) {
                        dataOrgJson = testParams;
                    }
                    try {
                        backValues = window[calledJsMethod](dataOrgJson);
                        err = null;
                    } catch (error) {
                        backValues = null;
                        err = "错误信息：" + error;
                    }
                }

            } catch (error) {
                backValues = null;
                err = "错误信息：" + error;
            }

        } else {
            try {
                backValues = window[calledJsMethod]();
                err = null;
            } catch (error) {
                backValues = null;
                err = "错误信息：" + error;
            }
        }
        middle_backValues(backValues, err);
    } else {
        _java_ShowToast("请指定要调用js的方法名");
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
            var reg = /^true/;
            /*var partten = RegExp(reg);
             partten.test(data);
             var res=RegExp.$1;*/
            var res = data.match(reg);
            return !isEmpty(res);
        };

        this.getState_false = function (data) {
            var reg = /^false$/;
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
        var errInfo = {err: error};//js_ObjToStr(errInfo)
        try {
            window[bridegCarry.java_bridgeTag].runOnAndroidWhenGoHomeFromJsWithVales(null, null);
        } catch (errs) {
            _java_ShowToast("方法调用异常：", "中间件 key = " + bridegCarry.bridgeTag + "\n\n错误信息：" + errs);
        }
    } else if (isEmpty(backValues) && !isEmpty(error)) {
        var errInfo = {err: error};//js_ObjToStr(errInfo)
        try {
            window[bridegCarry.java_bridgeTag].runOnAndroidWhenGoHomeFromJsWithVales(null, js_ObjToStr(error));
        } catch (errs) {
            _java_ShowToast("方法调用异常：", "中间件 key = " + bridegCarry.bridgeTag + "\n\n错误信息：" + errs);
        }

    } else {
        var errInfo = {err: error};//js_ObjToStr(errInfo)
        try {
            window[bridegCarry.java_bridgeTag].runOnAndroidWhenGoHomeFromJsWithVales(js_ObjToStr(backValues), null);
        } catch (errs) {
            _java_ShowToast("方法调用异常：", "中间件 key = " + bridegCarry.bridgeTag + "\n\n错误信息：" + errs);
        }
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

function checkException(data) {
    try {
        //运行代码
        var data = eval('(' + data + ')');
        return false;
    } catch (err) {
        //处理错误
        _java_ShowToast("异常信息：" + err)
        return true;
    }
};


/**
 *  供js 工具使用的方法
 * @param orgData
 * @constructor
 */
function JavaCallJsRequestBackValuesBridge(orgData) {
    AndroidCallJsRequestBackValuesBridge(orgData, bridegCarry.java_bridgeTag);
};

/**
 java工具类 toast 回调方法
 */
function _java_ShowToast(jni_args) {
    //window.injs.runOnAndroidForToast(jni_args);
    window[bridegCarry.java_bridgeTag].runOnAndroidForToast(jni_args);
};


/**
 * 将对象转换为字符串
 * @param data
 * @returns {string}
 */
function js_ObjToStr(data) {
    return JSON.stringify(data);
};

