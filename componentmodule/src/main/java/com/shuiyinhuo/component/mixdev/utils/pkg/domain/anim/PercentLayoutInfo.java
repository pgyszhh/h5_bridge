package com.shuiyinhuo.component.mixdev.utils.pkg.domain.anim;

import android.support.v4.view.MarginLayoutParamsCompat;
import android.util.Log;
import android.view.ViewGroup;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/9 0009
 * @ Description：
 * =====================================
 */
public class PercentLayoutInfo {
        public float widthPercent;

        public float heightPercent;

        public float leftMarginPercent;

        public float topMarginPercent;

        public float rightMarginPercent;

        public float bottomMarginPercent;

        public float startMarginPercent;

        public float endMarginPercent;

        public float aspectRatio;

        /* package */ final ViewGroup.MarginLayoutParams mPreservedParams;

        public PercentLayoutInfo() {
            widthPercent = -1f;
            heightPercent = -1f;
            leftMarginPercent = -1f;
            topMarginPercent = -1f;
            rightMarginPercent = -1f;
            bottomMarginPercent = -1f;
            startMarginPercent = -1f;
            endMarginPercent = -1f;
            mPreservedParams = new ViewGroup.MarginLayoutParams(0, 0);
        }

        /**
         * Fills {@code ViewGroup.LayoutParams} dimensions based on percentage values.
         */
        public void fillLayoutParams(ViewGroup.LayoutParams params, int widthHint,
                                     int heightHint) {
            // Preserve the original layout params, so we can restore them after the measure step.
            mPreservedParams.width = params.width;
            mPreservedParams.height = params.height;

            // We assume that width/height set to 0 means that value was unset. This might not
            // necessarily be true, as the user might explicitly set it to 0. However, we use this
            // information only for the aspect ratio. If the user set the aspect ratio attribute,
            // it means they accept or soon discover that it will be disregarded.
            final boolean widthNotSet = params.width == 0 && widthPercent < 0;
            final boolean heightNotSet = params.height == 0 && heightPercent < 0;

            if (widthPercent >= 0) {
                params.width = (int) (widthHint * widthPercent);
            }

            if (heightPercent >= 0) {
                params.height = (int) (heightHint * heightPercent);
            }

            if (aspectRatio >= 0) {
                if (widthNotSet) {
                    params.width = (int) (params.height * aspectRatio);
                }
                if (heightNotSet) {
                    params.height = (int) (params.width / aspectRatio);
                }
            }


        }

        /**
         * Fills {@code ViewGroup.MarginLayoutParams} dimensions and margins based on percentage
         * values.
         */
        public void fillMarginLayoutParams(ViewGroup.MarginLayoutParams params, int widthHint,
                                           int heightHint) {
            fillLayoutParams(params, widthHint, heightHint);

            // Preserver the original margins, so we can restore them after the measure step.
            mPreservedParams.leftMargin = params.leftMargin;
            mPreservedParams.topMargin = params.topMargin;
            mPreservedParams.rightMargin = params.rightMargin;
            mPreservedParams.bottomMargin = params.bottomMargin;
            MarginLayoutParamsCompat.setMarginStart(mPreservedParams,
                    MarginLayoutParamsCompat.getMarginStart(params));
            MarginLayoutParamsCompat.setMarginEnd(mPreservedParams,
                    MarginLayoutParamsCompat.getMarginEnd(params));

            if (leftMarginPercent >= 0) {
                params.leftMargin = (int) (widthHint * leftMarginPercent);
            }
            if (topMarginPercent >= 0) {
                params.topMargin = (int) (heightHint * topMarginPercent);
            }
            if (rightMarginPercent >= 0) {
                params.rightMargin = (int) (widthHint * rightMarginPercent);
            }
            if (bottomMarginPercent >= 0) {
                params.bottomMargin = (int) (heightHint * bottomMarginPercent);
            }
            if (startMarginPercent >= 0) {
                MarginLayoutParamsCompat.setMarginStart(params,
                        (int) (widthHint * startMarginPercent));
            }
            if (endMarginPercent >= 0) {
                MarginLayoutParamsCompat.setMarginEnd(params,
                        (int) (widthHint * endMarginPercent));
            }

        }

        @Override
        public String toString() {
            return String.format("PercentLayoutInformation width: %f height %f, margins (%f, %f, "
                            + " %f, %f, %f, %f)", widthPercent, heightPercent, leftMarginPercent,
                    topMarginPercent, rightMarginPercent, bottomMarginPercent, startMarginPercent,
                    endMarginPercent);

        }

        /**
         * Restores original dimensions and margins after they were changed for percentage based
         * values. Calling this method only makes sense if you previously called
         * {@link PercentLayoutHelper.PercentLayoutInfo#fillMarginLayoutParams}.
         */
        public void restoreMarginLayoutParams(ViewGroup.MarginLayoutParams params) {
            restoreLayoutParams(params);
            params.leftMargin = mPreservedParams.leftMargin;
            params.topMargin = mPreservedParams.topMargin;
            params.rightMargin = mPreservedParams.rightMargin;
            params.bottomMargin = mPreservedParams.bottomMargin;
            MarginLayoutParamsCompat.setMarginStart(params,
                    MarginLayoutParamsCompat.getMarginStart(mPreservedParams));
            MarginLayoutParamsCompat.setMarginEnd(params,
                    MarginLayoutParamsCompat.getMarginEnd(mPreservedParams));
        }

        /**
         * Restores original dimensions after they were changed for percentage based values. Calling
         * this method only makes sense if you previously called
         * {@link PercentLayoutHelper.PercentLayoutInfo#fillLayoutParams}.
         */
        public void restoreLayoutParams(ViewGroup.LayoutParams params) {
            params.width = mPreservedParams.width;
            params.height = mPreservedParams.height;
        }

    public interface PercentLayoutParams {
        PercentLayoutInfo getPercentLayoutInfo();
    }
}
