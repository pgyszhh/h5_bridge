package com.shuiyinhuo.component.mixdev.jinbean.pro;

import android.text.TextUtils;
import android.widget.TextView;

import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/24 0024
 * @ Description：Webview及其绑定数据的存储器
 * =====================================
 */
public class WebBundleStorager {
    private static WebBundleStorager mBundleStorager;

    private WebBundleStorager() {

    }

    public static WebBundleStorager getInstance() {
        if (EmptyAndSizeUtils.isEmpty(mBundleStorager)) {
            synchronized (WebBundleStorager.class) {
                if (EmptyAndSizeUtils.isEmpty(mBundleStorager)) {
                    mBundleStorager = new WebBundleStorager();
                }
            }
        }
        return mBundleStorager;
    }

    private XXPlayer mXXPlayer;
    private ArrayList<String> mKeys = new ArrayList<>();


    public void addBinder(UrlBinder binder) {
        JNILog.e("------------>加入数据：" + binder.toString());
        if (EmptyAndSizeUtils.isNotEmpty(binder)) {
            if (EmptyAndSizeUtils.isEmpty(mXXPlayer)) {
                mXXPlayer = new XXPlayer();
            }
            checkKeys(binder.getAlias());
            mXXPlayer.addNode(binder);
        }
    }

    public void clearAll() {
        this.mKeys.clear();
        while (EmptyAndSizeUtils.isNotEmpty(mXXPlayer)) {
            mXXPlayer.setAlias("");
            mXXPlayer.getBinders().clear();
            if (EmptyAndSizeUtils.isNotEmpty(mXXPlayer.getPrev())) {
                mXXPlayer.getPrev().setNext(null);
            }
            mXXPlayer = mXXPlayer.getNext();

        }
    }

    public ArrayList<UrlBinder> getBinder(String alias) {
        if (isHasAlias(alias)) {
            if (EmptyAndSizeUtils.isNotEmpty(this.mXXPlayer)) {
                return this.mXXPlayer.findBundle(alias);
            } else {
                JNILog.e("----------> data not find : alias = " + alias);
            }
            return null;
        } else {
            JNILog.e("----------> data not find : alias = " + alias);
            return null;
        }
    }

    public void clearBinder(String alias) {
        if (EmptyAndSizeUtils.isNotEmpty(alias)) {
            if (isHasAlias(alias)) {
                if (EmptyAndSizeUtils.isNotEmpty(this.mXXPlayer)) {
                    this.mXXPlayer.clearNode(alias);
                } else {
                    JNILog.e("---------> Binder dell failed,Root Binder  is NullPointer ,Empty datas");
                }
            } else {
                JNILog.e("----------> data not find : alias = " + alias);
            }
        } else {
            JNILog.e("---------> Binder dell failed,fixed alias name is NullPointer !");
        }
    }

    private void checkKeys(String alias) {
        if (EmptyAndSizeUtils.isNotEmpty(alias)) {
            if (!this.mKeys.contains(alias)) {
                mKeys.add(alias);
            }
        }
    }

    private void realseKeys(String alias) {
        if (EmptyAndSizeUtils.isNotEmpty(alias)) {
            if (this.mKeys.size() != 0) {
                if (this.mKeys.contains(alias)) {
                    this.mKeys.remove(alias);
                }
            }
        }
    }

    public boolean isHasAlias(String alias) {
        return this.mKeys.contains(alias);
    }

    public void showMsg() {
        JNILog.e("----------------> 加载进来的总数：" + mKeys.size());
        if (EmptyAndSizeUtils.isNotEmpty(mXXPlayer)) {
            int i = 1;
            XXPlayer temp = mXXPlayer;
            m(temp.getBinders(), temp.getAlias());
            while (EmptyAndSizeUtils.isNotEmpty(temp)) {
                i++;
                m(temp.getBinders(), temp.getAlias());
                temp = temp.getNext();


            }
            JNILog.e("---------------->总数据：" + i);
        }
    }

    private void m(ArrayList<UrlBinder> binders, String alias) {
        if (null == binders || binders.size() == 0) {
            JNILog.e("当前 别名无数据：" + alias);
        } else {

            for (UrlBinder mBinder : binders) {
                JNILog.e("------------------>检测数据：" + mBinder.toString());
            }
        }
    }


    /**
     * 数据存储器
     */
    private class XXPlayer {
        /**
         * 别名，多个Webview可能会重名
         */
        private String mAlias;
        private ArrayList<UrlBinder> mBinders = new ArrayList<>();
        private XXPlayer mNext = null;
        private XXPlayer mPrev = null;

        private boolean isSingle() {
            return mBinders.size() == 1;
        }


        public String getAlias() {
            return mAlias;
        }

        public void setAlias(String alias) {
            mAlias = alias;
        }

        public XXPlayer getNext() {
            return mNext;
        }

        public void setNext(XXPlayer next) {
            mNext = next;
        }

        public XXPlayer getPrev() {
            return mPrev;
        }

        public void setPrev(XXPlayer prev) {
            mPrev = prev;
        }

        public ArrayList<UrlBinder> getBinders() {
            if (this.mBinders.size() == 0) {
                return null;
            }
            return this.mBinders;
        }

        public void setBinder(UrlBinder binder) {
            this.mBinders.add(binder);
        }

        private void setBinders(ArrayList<UrlBinder> binders) {
            this.mBinders.addAll(binders);
        }

        public ArrayList<UrlBinder> findBundle(String alias) {
            if (EmptyAndSizeUtils.isNotEmpty(alias)) {
                if (TextUtils.equals(this.getAlias(), alias)) {
                    JNILog.e("-------- > find node  : alias = " + alias);
                    return this.getBinders();
                } else {
                    if (EmptyAndSizeUtils.isNotEmpty(this.getNext())) {
                        return this.getNext().findBundle(alias);
                    } else {
                        JNILog.e("-----------> data not find !");
                    }
                }
            } else {
                JNILog.e("-----------> alias is NullPointer !");
            }
            return null;
        }

        public void clearDatas() {
            this.mBinders.clear();
        }

        /**
         * 根据当前别名清除当前节点下的所有元素
         *
         * @param alias
         */
        public void clearNode(String alias) {
            if (TextUtils.equals(this.mAlias, alias)) {
                this.clearDatas();
                this.setAlias(null);
                JNILog.e("----------------> clear data alias =" + alias);
                if (EmptyAndSizeUtils.isNotEmpty(this.getPrev())) {
                    if (EmptyAndSizeUtils.isNotEmpty(this.getNext())) {
                        this.getPrev().setNext(this.getNext());
                    } else {
                        //JNILog.e("--------------> not find prev node;");
                        this.getPrev().setNext(null);
                    }
                    this.setPrev(null);
                    this.setNext(null);
                } else {
                    if (EmptyAndSizeUtils.isNotEmpty(this.getNext())) {
                        XXPlayer mNext = this.getNext();
                        this.setBinders(mNext.getBinders());
                        this.setAlias(mNext.getAlias());
                        this.setNext(mNext.getNext());
                        this.setPrev(null);
                    } else {
                        this.setPrev(null);
                        this.setNext(null);
                    }
                }
            } else {
                if (EmptyAndSizeUtils.isNotEmpty(this.getNext())) {
                    this.getNext().clearNode(alias);
                } else {
                    JNILog.e("----------------> not find node，can't clear！");
                }
            }
        }

        public void addNode(UrlBinder binder) {
            JNILog.e("开始加入......");
            if (EmptyAndSizeUtils.isNotEmpty(binder)) {
                if (TextUtils.equals(this.mAlias, binder.getAlias())) {
                    this.setBinder(binder);
                    this.setAlias(binder.getAlias());
                } else {
                    if (EmptyAndSizeUtils.isEmpty(this.getNext()) && EmptyAndSizeUtils.isEmpty(this.getAlias()) && EmptyAndSizeUtils.isEmpty(this.getPrev()) && EmptyAndSizeUtils.isEmpty(this.getBinders())) {
                        this.setBinder(binder);
                        this.setAlias(binder.getAlias());
                        this.setNext(null);
                        this.setPrev(null);
                    } else {
                        if (EmptyAndSizeUtils.isNotEmpty(this.getNext())) {
                            this.getNext().addNode(binder);
                        } else {
                            XXPlayer mXXPlayer = new XXPlayer();
                            mXXPlayer.setBinder(binder);
                            mXXPlayer.setAlias(binder.getAlias());
                            mXXPlayer.setNext(null);
                            mXXPlayer.setPrev(this);
                            this.setNext(mXXPlayer);
                        }
                    }
                }
            } else {
                JNILog.e("------------> add node failed ,this. UrlBinder is NulPointer!");
            }
        }

    }


}
