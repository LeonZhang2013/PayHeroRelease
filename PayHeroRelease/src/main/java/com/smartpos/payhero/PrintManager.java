package com.smartpos.payhero;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Creator: Kun
 * Date:2017/6/8
 * Email:kun.zhang@99bill.com
 */

public class PrintManager {

    /**
     * 打印参数项：内容类型
     * 可选字段有”txt”, “jpg”,”one-dimension”(一维码),   “two-dimension”(二维码)  (必填)
     */
    private static final String TAG_CONTENT_TYPE = "content-type";

    /**
     * 打印参数项：字体大小
     * 打印类型为txt:  字体大小, 表示字体占用点阵数,  可选字段为”1”,”2”,”3”. 8个点为一个点阵, size为3表示3个点阵.  (选填)默认为1.
     * 打印类型为two-dimension:  二维码大小, 表示二维码占用点阵数.  (选填)默认为1,代表120像素。可选字段为“1”,“2”,“3”,“4”,“5”,“6”,“7”,“8”，每次递增24像素。
     * 打印类型为one-dimension:  一维码宽度, 表示一维码每一根条码水平方向占用点阵数.”1”表示使用默认宽度  (选填)，可选字段有“1”，“2”，“3”。“1”代表112像素，后续字段像素倍增。
     * 打印类型为jpg:  与size不相关。
     */
    private static final String TAG_SIZE = "size";

    /**
     * 打印参数项：内容
     * 打印的文字或一维码二维码, 打印文字时需要识别”\n”,”\r”为空格和换行.“---”（三个-）表示打印一行虚线打印  默认为空(选填)
     */
    private static final String TAG_CONTENT = "content";

    /**
     * 打印参数项：对齐方式
     * 可选字段有”left”, “center”, “right”  默认为“left”(选填)
     */
    private static final String TAG_POSITION = "position";

    /**
     * 打印参数项：是否粗体
     * “1”表示字体加粗, “0”表示不加粗  默认为0(选填)
     */
    private static final String TAG_BOLD = "bold";

    /**
     * 打印参数项：一维码高度
     * 表示一维码竖直方向占用点阵数.”1”表示使用默认高度70像素，可选字段有“1”，“2”，“3”，每次递增20像素(选填)
     */
    private static final String TAG_HEIGHT = "height";

    /**
     * 打印类型：文本
     */
    private static final String CONTENT_TYPE_TXT = "txt";

    /**
     * 打印类型：图片
     */
    private static final String CONTENT_TYPE_JPG = "jpg";

    /**
     * 打印类型：一维码
     */
    private static final String CONTENT_TYPE_ONE_DIM = "one-dimension";

    /**
     * 打印类型：二维码
     */
    private static final String CONTENT_TYPE_TWO_DIM = "two-dimension";

    /**
     * 封装文本打印JSON对象
     *
     * @param content 文本内容
     * @return 文本打印JSON对象
     */
    public static JSONObject packerTxtPrintJson(String content) {
        return packerTxtPrintJson("1", content);
    }

    /**
     * 封装文本打印JSON对象
     *
     * @param size    字体大小
     * @param content 文本内容
     * @return 文本打印JSON对象
     */
    public static JSONObject packerTxtPrintJson(String size, String content) {
        return packerTxtPrintJson(size, content, "left");
    }

    /**
     * 封装文本打印JSON对象
     *
     * @param size     字体大小
     * @param content  文本内容
     * @param position 对齐方式
     * @return 文本打印JSON对象
     */
    public static JSONObject packerTxtPrintJson(String size, String content, String position) {
        return packerTxtPrintJson(size, content, position, "0");
    }

    /**
     * 封装文本打印JSON对象
     *
     * @param size     字体大小
     * @param content  文本内容
     * @param position 对齐方式
     * @param bold     是否粗体
     * @return 文本打印JSON对象
     */
    public static JSONObject packerTxtPrintJson(String size, String content, String position, String bold) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(TAG_CONTENT_TYPE, CONTENT_TYPE_TXT);
            jsonObject.put(TAG_SIZE, size);
            jsonObject.put(TAG_CONTENT, content);
            jsonObject.put(TAG_POSITION, position);
            jsonObject.put(TAG_BOLD, bold);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 封装图片打印JSON对象
     *
     * @param position 对齐方式
     * @return 图片打印JSON对象
     */
    public static JSONObject packerJPGPrintJson(String position) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(TAG_CONTENT_TYPE, CONTENT_TYPE_JPG);
            jsonObject.put(TAG_POSITION, position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 封装一维码打印JSON对象
     *
     * @param content 文本内容
     * @return 一维码打印JSON对象
     */
    public static JSONObject packerOneDimPrintJson(String content) {
        return packerOneDimPrintJson("1", "1", content, "center");
    }

    /**
     * 封装一维码打印JSON对象
     *
     * @param size     一维码宽度, 表示一维码每一根条码水平方向占用点阵数
     * @param height   一维码高度, 表示一维码竖直方向占用点阵数
     * @param content  文本内容
     * @param position 对齐方式
     * @return 一维码打印JSON对象
     */
    public static JSONObject packerOneDimPrintJson(String size, String height, String content, String position) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(TAG_CONTENT_TYPE, CONTENT_TYPE_ONE_DIM);
            jsonObject.put(TAG_SIZE, size);
            jsonObject.put(TAG_HEIGHT, height);
            jsonObject.put(TAG_CONTENT, content);
            jsonObject.put(TAG_POSITION, position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 封装二维码打印JSON对象
     *
     * @param content 文本内容
     * @return 二维码打印JSON对象
     */
    public static JSONObject packerTwoDimPrintJson(String content) {
        return packerTwoDimPrintJson("1", content, "center");
    }

    /**
     * 封装二维码打印JSON对象
     *
     * @param size     二维码大小, 表示二维码占用点阵数
     * @param content  文本内容
     * @param position 对齐方式
     * @return 二维码打印JSON对象
     */
    public static JSONObject packerTwoDimPrintJson(String size, String content, String position) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(TAG_CONTENT_TYPE, CONTENT_TYPE_TWO_DIM);
            jsonObject.put(TAG_SIZE, size);
            jsonObject.put(TAG_CONTENT, content);
            jsonObject.put(TAG_POSITION, position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
