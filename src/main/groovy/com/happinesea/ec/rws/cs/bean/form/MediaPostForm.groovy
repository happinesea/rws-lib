package com.happinesea.ec.rws.cs.bean.form

import java.nio.charset.Charset

import org.apache.http.entity.ContentType

class MediaPostForm extends WpPostForm {

    public static final ContentType MP_TEXT_PLAIN = ContentType.create(
    "text/plain", Charset.forName('UTF-8'));

    InputStream file
    String mimeType
}
