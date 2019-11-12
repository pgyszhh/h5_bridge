/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/19 0019
 * @ Description：
 * =====================================
 */
function showTest() {
    return "222";
}

function getbool(myValue) {
    _java_ShowToast(+"元数据："+myValue+"\n类型："+typeof (myValue));
    return  myValue*50;
}

function tests(n,n2) {
    _java_ShowToast(+"元数据：1 = "+n+"  2 = "+n2);
}