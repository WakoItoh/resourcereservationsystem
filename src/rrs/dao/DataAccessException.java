/* Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package rrs.dao;

/**
 * @author リコーITソリューションズ株式会社 jinsen
 *
 */
public class DataAccessException extends Exception {

    /**
     * @param message 例外メッセージ
     */
    public DataAccessException(final String message) {
        super(message);
    }

    /**
     * @param message 例外メッセージ
     * @param cause この例外の元となった例外
     */
    public DataAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
