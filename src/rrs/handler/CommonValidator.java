/* Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package rrs.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 共通入力チェック用クラス. 画面に共通のチェックはこのクラスにメソッドを用意する
 *
 * @author jinsen
 *
 */
public class CommonValidator {

    /**
     * 引数valの内容が設定されているかどうかチェックする.
     *
     * @param val 検証する値
     * @return 設定されていればfalse、設定されていなければtrue
     */
    protected boolean notSetOn(String val) {

        if (val == null) {
            return true;
        }
        if ("".equals(val)) {
            return true;
        }

        return false;

    }

    private int _intVal;

    /**
     * 引数valの内容が数値かどうかチェック.
     *  数値であればフィールドintValにその数値を保存
     *
     * @param val 検証する値
     * @return 数値であればfalse、数値でなければtrue
     */
    protected boolean notNumericOn(String val) {

        try {
            _intVal = Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return true;
        }

        return false;

    }

    /**
     * intValフィールドのgetter.
     *
     * @return intValフィールド
     */
    protected int getIntVal() {
        return _intVal;
    }

    protected static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    protected static final String TIME_FORMAT_PATTERN = "HH:mm";

    private Date _dateVal;

    /**
     * 引数valの内容が日付として正しいかどうかチェック.
     *  日付として正しければフィールドdateValにその数値を保存
     *
     * @param val 検証する値
     * @return 日付として正しければfalse、日付として正しくなければtrue
     */
    protected boolean notValidDate(String val) {

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        formatter.setLenient(false);

        try {
            _dateVal = formatter.parse(val);
        } catch (NullPointerException | ParseException e) {
            return true;
        }

        return false;

    }

    /**
     * 引数valの内容が日付時刻として正しいかどうかチェック.
     *  日付時刻として正しければフィールドdateValにその数値を保存
     *
     * @param dateVal 検証する値（日付）
     * @param timeVal 検証する値（時刻）
     * @return 日付時刻として正しければfalse、日付時刻として正しくなければtrue
     */
    protected boolean notValidDate(String dateVal, String timeVal) {

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_PATTERN + TIME_FORMAT_PATTERN);
        formatter.setLenient(false);

        try {
            _dateVal = formatter.parse(dateVal + timeVal);
        } catch (NullPointerException | ParseException e) {
            return true;
        }

        return false;

    }

    /**
     * dateValフィールドのgetter.
     *
     * @return dateValフィールド
     */
    protected Date getDateVal() {
        return _dateVal;
    }

}
