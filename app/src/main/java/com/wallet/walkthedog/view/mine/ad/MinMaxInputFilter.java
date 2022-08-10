package com.wallet.walkthedog.view.mine.ad;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.util.Arrays;

public class MinMaxInputFilter implements InputFilter {
    private final double min;
    private final double max;

    public MinMaxInputFilter(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public static void minMaxLimit(EditText editText, double min, double max) {
        InputFilter[] filters = editText.getFilters();
        InputFilter[] inputFilters = Arrays.copyOf(filters, filters.length + 1);
        inputFilters[inputFilters.length - 1] = new MinMaxInputFilter(min, max);
        editText.setFilters(inputFilters);
    }


    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            //限制大小
            double input = Double.parseDouble(dest + source.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (Exception ignored) {
        }
        return "";
    }

    private boolean isInRange(double a, double b, double c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
