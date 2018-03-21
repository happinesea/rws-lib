package com.happinesea.ec.rws.lib.enumerated;

/**
 * @author loveapple
 *
 */
public enum OrderProgressEnum implements ApiResponseEnum{
    WAILT(100, '注文確認待ち'), PROCESS(200, '楽天処理中'),
    WAIT_DELIVERY(300, '発送待ち'), WAIL_COMMIT(400, '変更確定待ち'),
    DELIVERY(500, '発送済'), PAYING(600, '支払手続き中'),
    PAY_COMPLETE(700, '支払手続き済'), WAIT_CANCEL(800, 'キャンセル確定待ち')


    Integer id
    String description
}
