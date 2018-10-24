/* Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package rrs.model;

import rrs.dao.DataAccessException;

/**
 * モデル(サービス)インターフェースの定義.
 *
 * @author jinsen
 *
 */
public interface Service {

    /**
     * 業務に依存する入力データチェックをおこなう.
     *
     * @return 問題なければtrue、問題があればfalse
     */
    boolean validate();

    /**
     * 業務処理を実行.
     *
     * @throws DataAccessException データアクセス時の例外
     */
    void execute() throws DataAccessException;

}
