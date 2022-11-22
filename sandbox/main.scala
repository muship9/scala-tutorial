
object Main {
  def main(args: Array[String]): Unit = {
    Method.method()
  }
}

/** Method メソッド
  * def キーワードで定義される
  */
object Method {
  def method(): Unit = {
    // メソッド定義
    def add(x: Int, y: Int): Int = x + y
    println(add(1, 2)) // 3

    // 複数のパラメーターリストを受け取ることも可能
    def addThenMultiply(x: Int, y: Int)(multiplier: Int) = (x + y) * multiplier
    println(addThenMultiply(1, 2)(3)) // 9

    // パラメーターリストがないケース
    def name: String = System.getProperty("user.name")
    println(name)

    def getSquareString(input: Double): String = {
      val square = input * input
      square.toString
      val point = new Point(1, 2)
    }
    println(getSquareString(2.5))
  }
}
