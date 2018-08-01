# rws-lib

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.happinesea/rws-lib/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.happinesea/rws-lib)
[![CircleCI](https://circleci.com/gh/happinesea/rws-lib/tree/release.svg?style=shield)](https://circleci.com/gh/happinesea/rws-lib/tree/release)
[![Coverage Status](https://coveralls.io/repos/github/happinesea/rws-lib/badge.svg)](https://coveralls.io/github/happinesea/rws-lib)

rws-libは楽天市場を中心として、ECサイト管理するために共通と思われる課題を解決するためのものです。

楽天ペイAPI(RakutenPayOrderAPI)を含めて、RWS(RMS WEB SERVICE)で通信するデータをGroovy/Javaから使いやすい形に変換するライブラリです。

rws-libから、具体的なサービスを何も提供しません。あくまで、CSV、XMLや、JSONのパーサーフレームワーク、API通信フレームワークを含んでおり、ECカートのサードパーティー開発しやすいために、提供するオープンソースライブラリーです。

[MITライセンス](https://github.com/happinesea/rws-lib/blob/master/LICENSE)をもとに提供しておりますので、個人でも、商用でもご自由に利用できます。


Groovyで開発しているので、Javaをフルサポートです。

開発関連、詳しい内容について、[wiki](https://github.com/happinesea/rws-lib/wiki)をご参照ください。

## What's new
<dl>
  <dt>2018/2/15</dt>
  <dl>
    楽天よりAPIのドキュメントを公開されているので、できるところから、開発は順次スタートします。
  </dl>
  <dt>2018/8/1</dt>
  <dl>
    プレビュー版をリリースします。<br>JSON、XMLのパーサーフレームワークを整えました。
  </dl>

</dl>

## Documentation
[Groovy docs](http://lab.happinesea.com/docs/rws-lib/1.0.0_preview/groovydoc/)<br>
[Guides and Tutorials](https://github.com/happinesea/rws-lib-tutorial)(作成中)

### Insatallation
Gradle、Mavenを利用する場合、MavenCentralの依存関係を追記するだけでよい
<pre>
repositories {
    mavenCentral()
}
dependencies {
    compile 'com.happinesea:rws-lib:1.0.0_preview'
}
</pre>

## About support
rws-libのバグをお見つけた場合、issuesの起票をお願いします。<br>
公的な場で共有できない情報を含む場合、直接<a href="https://github.com/happinesea">happinesea</a>の開発者へメールをしてください。

それ以外のことでも、お気軽にお問い合わせください。

## 関連リンク
<li>
  <a href="https://webservice.rms.rakuten.co.jp/merchant-portal/view?contents=/ja/common/1-1_service_index/rakutenpayorderapi" target="_brank">RMS WEB SERVICE : RakutenPayOrderAPI</a>(※RMSにログインした状態でアクセスしてください 
)
</li>
