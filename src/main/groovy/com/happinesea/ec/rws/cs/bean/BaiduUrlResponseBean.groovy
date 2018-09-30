package com.happinesea.ec.rws.cs.bean

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

class BaiduUrlResponseBean implements ApiResponseNode {
    Integer remain
    Integer success
    List<String> not_same_site
    List<String> not_valid
    Integer error
    String message
}
