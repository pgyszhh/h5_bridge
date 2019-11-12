/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/15 0015
 * @ Description：
 * =====================================
 */


/** 获取日期范围显示 */

function getDateRange(_year, _week) {
    var beginDate;
    var endDate;
    if (_year == null || _year == '' || _week == null || _week == '') {
        return "";
    }
    beginDate = getXDate(_year, _week, 4);
    endDate = getXDate(_year, (_week - 0 + 1), 5);
    return getNowFormatDate(beginDate) + " 至 " + getNowFormatDate(endDate);
};

/* 这个方法将取得某年(year)第几周(weeks)的星期几(weekDay)的日期 */
function getXDate(year, weeks, weekDay) {
// 用指定的年构造一个日期对象，并将日期设置成这个年的1月1日
// 因为计算机中的月份是从0开始的,所以有如下的构造方法
    var date = new Date(year, "0", "1");

// 取得这个日期对象 date 的长整形时间 time
    var time = date.getTime();

// 将这个长整形时间加上第N周的时间偏移
// 因为第一周就是当前周,所以有:weeks-1,以此类推
// 7*24*3600000 是一星期的时间毫秒数,(JS中的日期精确到毫秒)
    time += (weeks - 1) * 7 * 24 * 3600000;

// 为日期对象 date 重新设置成时间 time
    date.setTime(time);
    return getNextDate(date, weekDay);
};


/*这个方法将取得 某日期(nowDate) 所在周的星期几(weekDay)的日期*/
function getNextDate(nowDate, weekDay) {
// 0是星期日,1是星期一,...
    weekDay %= 7;
    var day = nowDate.getDay();
    var time = nowDate.getTime();
    var sub = weekDay - day;
    if (sub <= 0) {
        sub += 7;
    }
    time += sub * 24 * 3600000;
    nowDate.setTime(time);
    return nowDate;
};


/*js获取当前指定的前几天的日期*/

function getBeforeDate(n) {
    var n = n;
    var d = new Date();
    var year = d.getFullYear();
    var mon = d.getMonth() + 1;
    var day = d.getDate();
    if (day <= n) {
        if (mon > 1) {
            mon = mon - 1;
        }
        else {
            year = year - 1;
            mon = 12;
        }
    }
    d.setDate(d.getDate() - n);
    year = d.getFullYear();
    mon = d.getMonth() + 1;
    day = d.getDate();
    s = year + "-" + (mon < 10 ? ('0' + mon) : mon) + "-" + (day < 10 ? ('0' + day) : day);
    return s;
}

/**
 *  获取指定区间的日期
 * */



function getFixedStyleDate_current(format) {
    return getDateByStyle(null, format);
}

/**
 *  格式 "yyyy年MM月dd日"
 *  "yyyy-MM-dd
 * */
function getFixedFormatStyleDate(date, format) {
    if (isMyEmpty(date) && !isMyEmpty(format)) {
        console.log(1)
        return getDateByStyle(null, format);
    } else if (!isMyEmpty(date) && isMyEmpty(format)) {
        console.log(2)
        if (testEp(date)) {
            return getDateByStyle(date, null);
        } else {
            return getDateByStyle(null, null);
        }
    } else if (!isMyEmpty(date) && !isMyEmpty(format)) {
        console.log(3)
        if (testEp(date)) {
            console.log(4)
            return getDateByStyle(date, format);
        } else {
            if (testIsNumer(date)) {
                console.log(5)
                console.log(date.toString())
                return getDateByStyle(date, format);
            } else {
                console.log(6)
                return getDateByStyle(null, format);
            }
        }
    }
    return getFixedStyleDate_current();
}

/**
 * 获取时间以及格式化
 * */
function getDateByStyle(date, format) {
    //var mFormat = "yyyy-MM-dd hh:mm:ss";
    var mFormat = "yyyy-MM-dd";
    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1,                 //月份
            "d+": this.getDate(),                    //日
            "h+": this.getHours(),                   //小时
            "m+": this.getMinutes(),                 //分
            "s+": this.getSeconds(),                 //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };

    if (!isMyEmpty(format)) {
        console.log(format)
        mFormat = format;
    }

    if (testEp(date)) {
        console.log("指定日期")
        console.log("sgg  " + date.getTime())
        //date.prototype.Format = proFun;
        var newDate = new Date();
        newDate.setTime(date.getTime());
        return newDate.Format(mFormat);
    } else if (testIsNumer(date)) {
        var year = null;
        var month = null;
        var day = null;
        /*  var index = date.indexOf("-");

         if (index == -1) {*/
        var partten = null;
        if (date.length == 8) {
            var reg = /^(\d{4})(\d{2})(\d{2})$/;
            partten = RegExp(reg);
        } else if (date.length == 6) {
            var reg = /^(\d{4})(\d{1})(\d{1})$/;
            partten = RegExp(reg);
        } else {
            var reg = /^(\d{4})[-]{1}(\d{1,2})[-]{1}(\d{1,2})$/
            partten = RegExp(reg);
        }
        partten.test(date);

        year = RegExp.$1;
        month = parseInt(RegExp.$2) - 1;
        month = month.toString().length < 2 ? "0" + month : month;
        day = RegExp.$3;
        date = year + "/" + RegExp.$2 + "/" + day;
        //20120506
        console.log("转换后日期：" + date);
        var mDate = new Date(year, month, day);
        var newDate = new Date(mDate.getTime());
        console.log("格式化日期：" + newDate.Format(mFormat))
        return newDate.Format(mFormat);
    } else {
        console.log("系统日期")
        return new Date().Format(mFormat);
    }
}


function getPeriodDate(start, end) {
    function getDate(datestr) {
        var temp = datestr.split("-");
        var date = new Date(temp[0], temp[1], temp[2]);
        return date;
    }

    /* var start = "2012-3-25";
     var end = "2012-4-3";*/
    var startTime = getDate(start);
    var endTime = getDate(end);
    var allDates = [];
    allDates.length = 0;
    while ((endTime.getTime() - startTime.getTime()) >= 0) {
        var year = startTime.getFullYear();
        var month = startTime.getMonth().toString().length == 1 ? "0" + startTime.getMonth().toString() : startTime.getMonth();
        var day = startTime.getDate().toString().length == 1 ? "0" + startTime.getDate() : startTime.getDate();
        ///alert(year+"-"+month+"-"+day);
        allDates.push(year + "-" + month + "-" + day)
        startTime.setDate(startTime.getDate() + 1);
    }
    return allDates;
}

/**获取指定的月份*/
function getFixedMonth(beforLen) {
    var allDate = [];
    allDate.length = 0;

    var currentMonth = new Date();
    for (var i = 0; i < parseInt(beforLen); i++) {
        var month = new Date();
        month.setMonth(currentMonth.getMonth() - i);
        var newDate = getFixedFormatStyleDate(month, "yy年MM月");
        allDate.push(newDate)
    }
    return allDate;
};



/**获取指定的天数：一段时间*/
function getFixedDateLimitDay(dayLen) {
    var allDay = [];
    allDay.length = 0;

    var currentMonth = new Date();
    for (var i = 0; i < parseInt(dayLen); i++) {
        var month = new Date();
        month.setDate(currentMonth.getDate() - i);
        var newDate = getFixedFormatStyleDate(month, "yyyy-MM-dd");
        allDay.push(newDate)
    }
    return allDay;
};

/**获取指定的天数：一段时间
 *
 * */
function getFixedDay(dayLen) {
    var allDay = [];
    allDay.length = 0;

    var currentMonth = new Date();
    for (var i = 0; i < parseInt(dayLen); i++) {
        var month = new Date();
        month.setDate(currentMonth.getDate() - i);
        var newDate = getFixedFormatStyleDate(month, "dd日");
        allDay.push(newDate)
    }
    return allDay;
};


/** 判空 */
function isMyEmpty(data) {
    return ("" == data) || data == null || ( "null" == data) || ("undefined" == data) || typeof (data) == "undefined" || data == "NaN";
};


/**
 *
 * @param date 要判断的时间字符串
 * @returns {boolean}
 */
function testIsNumer(date) {
    var reg = /^[0-9]{8}$/  //20120506
    var reg2 = /^[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}$/
    var reg3 = /^[0-9]{6}$/  //201556

    var partten = RegExp(reg);
    var partten2 = RegExp(reg2);
    var partten3 = RegExp(reg3);
    var state = partten.test(date) || partten2.test(date) || partten3.test(date);
    console.log("匹配状态：" + state)
    return state;
}


/**
 * 测试日期
 * */
function testEp(date) {
    if (isMyEmpty(date)) {
        return false;
    }
    ;
    if (typeof (date) == "object") {
        try {
            console.log("成功")
            return true;
        } catch (err) {
            console.log("失败")
            return false;
        }
    } else {
        console.log("非obj")
        return false;
    }
}


