package rrs.handler;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class CommonValidatorTest {

    @Test
    public void testNotSetOn() {

        // 文字列長が1以上のとき、チェック結果が取得される
        // 期待結果：falseが返却される

        String val = "a";
        CommonValidator validator = new CommonValidator();
        assertThat(validator.notSetOn(val), is(false));
    }

    @Test
    public void testNotSetOnNull() {

        // nullのとき、チェック結果が取得される
        // 期待結果：trueが返却される

        String val = null;
        CommonValidator validator = new CommonValidator();
        assertThat(validator.notSetOn(val), is(true));
    }

    @Test
    public void testNotSetOnEmpty() {

        // 文字列長が0のとき、チェック結果が取得される
        // 期待結果：trueが返却される

        String val = "";
        CommonValidator validator = new CommonValidator();
        assertThat(validator.notSetOn(val), is(true));
    }

    @Test
    public void testNotNumericOn() {

        // 数値文字列のとき、フィールドに数値がセットされ、チェック結果が取得される
        // 期待結果：falseが返却される
        // 期待結果：getterの結果、1が返却される

        String val = "1";
        int intVal = 1;
        CommonValidator validator = new CommonValidator();
        assertThat(validator.notNumericOn(val), is(false));
        assertThat(validator.getIntVal(), is(intVal));
    }

    @Test
    public void testNotNumericOnFault() {

        // 数値文字列でないとき、チェック結果が取得される
        // 期待結果：trueが返却される

        String val = "a";
        CommonValidator validator = new CommonValidator();
        assertThat(validator.notNumericOn(val), is(true));
    }

    @Test
    public void testNotValidDate() throws ParseException {

        // 日付文字列のとき、フィールドに日付がセットされ、チェック結果が取得される
        // 期待結果：falseが返却される
        // 期待結果：getterの結果、2018-10-01の日付データが返却される

        String val = "2018-10-01";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateVal = formatter.parse(val);
        CommonValidator validator = new CommonValidator();
        assertThat(validator.notValidDate(val), is(false));
        assertThat(validator.getDateVal(), is(dateVal));
    }

    @Test
    public void testNotValidDateFault() {

        // 日付文字列でないとき、チェック結果が取得される
        // 期待結果：trueが返却される

        String val = "2018-10-32";
        CommonValidator validator = new CommonValidator();
        assertThat(validator.notValidDate(val), is(true));
    }

    @Test
    public void testNotValidDateTime() throws ParseException {

        // 日付・時刻文字列のとき、フィールドに日付・時刻がセットされ、チェック結果が取得される
        // 期待結果：falseが返却される
        // 期待結果：getterの結果、2018-10-01 12:00の日付データが返却される

        String val1 = "2018-10-01";
        String val2 = "12:00";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-ddHH:mm");
        Date dateVal = formatter.parse(val1 + val2);
        CommonValidator validator = new CommonValidator();
        assertThat(validator.notValidDate(val1, val2), is(false));
        assertThat(validator.getDateVal(), is(dateVal));
    }

    @Test
    public void testNotValidDateTimeFaultDate() {

        // 日付文字列でないとき、チェック結果が取得される
        // 期待結果：trueが返却される

        String dateVal = "2018-10-32";
        String timeVal = "12:00";
        CommonValidator validator = new CommonValidator();
        assertThat(validator.notValidDate(dateVal, timeVal), is(true));
    }

    @Test
    public void testNotValidDateTimeFaultTime() {

        // 時刻文字列でないとき、チェック結果が取得される
        // 期待結果：trueが返却される

        String dateVal = "2018-10-01";
        String timeVal = "12:99";
        CommonValidator validator = new CommonValidator();
        assertThat(validator.notValidDate(dateVal, timeVal), is(true));
    }

}
