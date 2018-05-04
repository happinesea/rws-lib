package com.happinesea.ec.rws.lib.bean.rakuten.enumerated;

/**
 * 検索結果ソート順の列挙
 *
 */
enum SortDirectionEnum implements ApiResponseEnum{
    ASC('0', '昇順（小さい順、古い順）'),  DESC('1', '降順（大きい順、新しい順）')


    SortDirectionEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
