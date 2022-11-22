/**
  * 全ての値がオブジェクト / 全てのメソッドは何かしらのオブジェクトに所属している
  * object キーワードで同じ名前のシングルトンオブジェクトの現在の名前空間の下に一つ定義可能
  * 構文の主な用途
  * 1. ユーティリティメソッドやグローバルな状態の置き場所
  * 2. 同名クラスのオブジェクトのファクトリメソッド
  */

/**
  * ファクトリメソッドの定義
  * new Point() で直接 Point オブジェクトを生成するのに加えて 2点のメリットがある
  * 1. クラス の実装詳細を内部に隠して置ける -> インターフェイスのみを外部に後悔する
  * 2. Point ではなく、そのサブクラスのインスタンスを返すことが可能
  */
class Point(val x: Int, val y: Int)

// Point クラスのファクトリメソッドを定義
object Point {
  def apply(x: Int, y: Int): Point = new Point(x, y)
}

/**
  * case クラスでの短縮
  * クラスのプライマリコンストラクタ全てのフィールドを公開し、オブジェクトの基本的なメソッドをオーバーライドしたクラスを生成
  * -> クラスのインスタンスを生成するためにファクトリメソッドを生成する
  */
case class Point(s: Int, y: Int)

/**
  * コンパニオンオブジェクト
  * クラスと同じファイル名、同じ名前で定義されたシングルトンオブジェクト
  * 対応するクラスに対して特権的なアクセス権を持っている
  * -> private[this] のようなオブジェクトないからのみアクセス可能なクラスのメンバーに対してはアクセスできない
  */

// weight を praivate にした場合

// NG
class Person(name: String, age: Int, private val weight: Int)

object Hoge {
  def printWeight(): Unit = {
    val taro = new Person("Taro", 20, 70)
    println(taro.weight)
  }
}

// OK
class Person(name: String, age: Int, private val weight: Int)

object Person {
  def printWeight(): Unit = {
    val taro = new Person("Taro", 20, 70)
    println(taro.weight)
  }
}
