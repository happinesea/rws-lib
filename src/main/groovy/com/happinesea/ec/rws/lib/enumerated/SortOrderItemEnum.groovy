package com.happinesea.ec.rws.lib.enumerated;

/**
 * 
 *
 */
public enum SortOrderItemEnum implements ApiResponseEnum{
    ASC('asc', '昇順（小さい順、古い順）'),  DESC('desc', '降順（大きい順、新しい順）')


    SortOrderItemEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
