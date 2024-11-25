package com.juyoung.product.data.util;

import java.text.NumberFormat;
import java.util.Locale;

public class PriceFormatterUtil {

    // "1,900" (comma-분리)
    public static String formatPrice(int price) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);
        return numberFormat.format(price);
    }
    // "1,900" -> 1900
    public static int parsePriceToInt(String price) {
        return Integer.parseInt(price.replaceAll(",", ""));
    }

}