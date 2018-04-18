package com.smartpos.payhero;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

/**
 * Creator: Kun
 * Date:2017/3/28
 * Email:kun.zhang@99bill.com
 */

public class Utils {
    /**
     * 统一的交易时间格式
     */
    private static final String TIME_FORMAT = "yyyyMMddHHmmss";

    /**
     * 产生订单号
     */
    public static String generateOrderId() {
        return new SimpleDateFormat(TIME_FORMAT, Locale.CHINA).format(System.currentTimeMillis()) +
                String.format(Locale.CHINA, "%04d", (int) (new Random().nextFloat() * 10000));
    }

    /**
     * 支付回调结果处理
     *
     * @param activity
     * @param result
     */
    public static void handleCallback(Activity activity, String result) {
        Intent intent = new Intent(activity, PaymentResultActivity.class);
        intent.putExtra("result", result);
        activity.startActivity(intent);
    }

    /**
     * 格式化SDK返回数据
     *
     * @param text
     * @return
     */
    public static String formatString(String text) {

        StringBuilder json = new StringBuilder();
        String indentString = "";

        boolean inQuotes = false;
        boolean isEscaped = false;

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);

            switch (letter) {
                case '\\':
                    isEscaped = !isEscaped;
                    break;
                case '"':
                    if (!isEscaped) {
                        inQuotes = !inQuotes;
                    }
                    break;
                default:
                    isEscaped = false;
                    break;
            }

            if (!inQuotes && !isEscaped) {
                switch (letter) {
                    case '{':
                    case '[':
                        json.append("\n" + indentString + letter + "\n");
                        indentString = indentString + "\t";
                        json.append(indentString);
                        break;
                    case '}':
                    case ']':
                        indentString = indentString.replaceFirst("\t", "");
                        json.append("\n" + indentString + letter);
                        break;
                    case ',':
                        json.append(letter + "\n" + indentString);
                        break;
                    default:
                        json.append(letter);
                        break;
                }
            } else {
                json.append(letter);
            }
        }

        return json.toString();
    }

    /**
     * 图片二进制字符串转换Bitmap
     *
     * @param hexString
     * @return
     */
    public static Bitmap hexString2Bitmap(String hexString) {
        byte[] imagebytes = convertHexadecimal2Binary(hexString.getBytes());
        return BitmapFactory.decodeByteArray(imagebytes, 0, imagebytes.length);
    }

    static String HEX_STRING = "0123456789ABCDEF";

    public static byte[] convertHexadecimal2Binary(byte[] hex) {
        int block = 0;
        byte[] data = new byte[hex.length / 2];
        int index = 0;
        boolean next = false;
        for (byte aHex : hex) {
            block <<= 4;
            int pos = HEX_STRING.indexOf(Character.toUpperCase((char) aHex));
            if (pos > -1) {
                block += pos;
            }
            if (next) {
                data[index] = (byte) (block & 0xff);
                index++;
                next = false;
            } else {
                next = true;
            }
        }
        return data;
    }

}
