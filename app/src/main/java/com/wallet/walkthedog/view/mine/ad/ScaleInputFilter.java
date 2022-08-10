package com.wallet.walkthedog.view.mine.ad;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.Arrays;

public class ScaleInputFilter implements InputFilter {


    public static void scale(EditText editText, int scale) {
        InputFilter[] filters = editText.getFilters();
        InputFilter[] inputFilters = Arrays.copyOf(filters, filters.length + 1);
        inputFilters[inputFilters.length - 1] = new ScaleInputFilter(scale);
        editText.setFilters(inputFilters);
    }

    /**
     * 小数点后的数字的位数
     */
    private final int POINTER_LENGTH;


    public ScaleInputFilter(int scale) {
        this.POINTER_LENGTH = scale;
    }

    /**
     * source    新输入的字符串
     * start    新输入的字符串起始下标，一般为0
     * end    新输入的字符串终点下标，一般为source长度-1
     * dest    输入之前文本框内容
     * dstart    原内容起始坐标，一般为0
     * dend    原内容终点坐标，一般为dest长度-1
     */

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {

        String sourceText = source.toString();
        String destText = dest.toString();
        //验证删除等按键
        String POINTER = ".";
        if (TextUtils.isEmpty(sourceText)) {
            if (dstart == 0 && destText.indexOf(POINTER) == 1) {//保证小数点不在第一个位置
                return "0";
            }
            return "";
        }
        //已经输入小数点的情况下，只能输入数字
        if (destText.contains(POINTER)) {
            if (POINTER.contentEquals(source)) { //只能输入一个小数点
                return "";
            }
            //验证小数点精度，保证小数点后只能输入两位
            int index = destText.indexOf(POINTER);
            int length = destText.trim().length() - index;
            if (length > POINTER_LENGTH && dstart > index) {
                return "";
            }
        } else {
            //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点和0
            if ((POINTER.contentEquals(source)) && dstart == 0) {//第一个位置输入小数点的情况
                return "0.";
            } else if ("0".contentEquals(source) && dstart == 0) {
                //用于修复能输入多位0
                return "";
            }
        }
        return null;
    }
}