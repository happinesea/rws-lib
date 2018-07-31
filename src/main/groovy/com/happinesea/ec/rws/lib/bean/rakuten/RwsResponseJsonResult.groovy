package com.happinesea.ec.rws.lib.bean.rakuten

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

/**
 * RMSのJSONレスポンスの基底クラス
 * 
 *
 */
class RwsResponseJsonResult implements ApiResponseNode {

    /**
     * メッセージモデルリスト
     */
    public List<MessageModel> MessageModelList

    /**
     * MessageModelListのセッター
     * 
     * @param messageModelList
     * @return
     */
    public setMessageModelList(List<MessageModel> messageModelList) {
	this.MessageModelList = messageModelList
    }
    /**
     * メッセージモデル
     *
     */
    static class MessageModel implements ApiResponseNode{
	String messageType
	String messageCode
	String message
	String orderNumber
    }
}
