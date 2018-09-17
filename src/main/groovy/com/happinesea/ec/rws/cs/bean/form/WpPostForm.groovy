package com.happinesea.ec.rws.cs.bean.form

import com.happinesea.ec.rws.lib.bean.form.RwsBaseForm

class WpPostForm extends RwsBaseForm {
    String title
    String content
    String status
    Integer featured_media
    List<String> tags
    List<Integer> categories
}
