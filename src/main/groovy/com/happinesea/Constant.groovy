package com.happinesea

/**
 * 全体定数の定義をまとめるクラス 
 *
 */
class Constant {
    /**
     * フラグ：OFF
     */
    final static def FLAG_OFF = 0;
    /**
     * フラグ：ON
     */
    final static def FLAG_ON = 1;

    /**
     * RMSのデフォルト日時フォーマット
     */
    final static def DATETIME_FORMATE_RWS = "yyyy-MM-DDThh:mm:ss+09:00"

    /**
     * ダミーを表す文字
     */
    final static def CSV_DUMMY_STR = "dummy"
    /**
     * CSV固定文字を表す添え字
     */
    final static def CSV_FIX_STR = "fix:"
}
