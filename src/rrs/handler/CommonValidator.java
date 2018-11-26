/* Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package rrs.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
    protected static final String TIME_00 = "00:00";
    protected static final String TIME_24 = "24:00";
    protected static final long HOURS_24 = 24 * 60 * 60 * 1000;

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
     * 引数valの内容が日付時刻として正しいかどうかチェック.
     *  日付時刻として正しければフィールドdateValにその数値を保存
     *  時刻が"24:00"の場合は翌"00:00"に換算し正しいとする
     *
     * @param dateVal 検証する値（日付）
     * @param timeVal 検証する値（時刻）
     * @return 日付時刻として正しければfalse、日付時刻として正しくなければtrue
     */
    protected boolean notValidDateExcept24(String dateVal, String timeVal) {

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_PATTERN + TIME_FORMAT_PATTERN);
        formatter.setLenient(false);

        try {
            if (TIME_24.equals(timeVal)) {
                _dateVal = new Date(formatter.parse(dateVal + TIME_00).getTime() + HOURS_24);
            } else {
                _dateVal = formatter.parse(dateVal + timeVal);
            }
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

    private long _hoursVal;

    /**
     * 引数valの内容が時間数（時刻）として正しいかどうかチェック.
     *  時間数（時刻）として正しければフィールドhoursValにその数値を保存
     *  時刻が"24:00"の場合は正しいとする
     *
     * @param val 検証する値
     * @return 時間数として正しければfalse、時間数として正しくなければtrue
     */
    protected boolean notValidHoursExcept24(String val) {

        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_PATTERN);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        formatter.setLenient(false);

        try {
            if (TIME_24.equals(val)) {
                _hoursVal = HOURS_24;
            } else {
                _hoursVal = formatter.parse(val).getTime();
            }
        } catch (NullPointerException | ParseException e) {
            return true;
        }

        return false;

    }

    /**
     * hoursValフィールドのgetter.
     *
     * @return hoursValフィールド
     */
    protected long getHoursVal() {
        return _hoursVal;
    }

    /**
     * 引数valの内容がチェックボックスでONかどうかチェックする.
     *
     * @param val 検証する値
     * @return ONであればtrue、ONでなければfalse
     */
    protected boolean checkOn(String val) {

        if (val == null) {
            return false;
        }
        if (!"on".equals(val)) {
            return false;
        }

        return true;

    }

}
