/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/26 0026
 * @ Description：
 * =====================================
 */

(function (win, doc) {
    function change() {
        document.documentElement.style.fontSize = document.documentElement.clientWidth * 20 / 320 + 'px';
    }

    win.addEventListener('resize', change, false);
    win.addEventListener('DOMContentLoaded', change, false);
})(window, document);
