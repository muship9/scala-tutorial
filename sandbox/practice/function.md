# 関数

Scala の関数は `Function0` ~ `Function22` までのトレイトの無名サブクラスのインスタンスになる

※トレイトのデフォルトのスーパークラスは AnyRef になり、メソッドとフィールド定義をカプセル化している

```scala
val add = new Function2[Int, Int] {
  //  apply 以外だとコンパイルエラーになる
  def apply(x: Int, y: Int): Int = x + y
}
add.apply(100, 200)
// val res1: Int = 300

add(100, 200)
//val res2: Int = 300
```

### 無名関数

トレイトのインスタンスを生成するためのシンタックスシュガーがある

```scala
// 関数本体に名前がついていないため、無名関数になる
val add = (x: Int, y: Int) => x + y

/**
  * 無名関数の一般的な構文
  * 引数の最大個数は 22 個 -> トレイトの無名サブクラスのインスタンスのため
  * scala 3 からは 23以上の関数も作成可能
  * */
(n1: N1) => B
```

### 関数のカリー化

複数の引数を取る関数を、一時引数関数の連続した呼び出しに置き換えること

```scala
val add = (x: Int, y: Int) => x + y
add(100, 200)
// val res0: Int = 300


val addCurried = (x: Int) => ((y: Int) => x + y)
addCurried(100)(200)
// val res1: Int = 300
```

### メソッドと関数の違い

メソッド = `def` 定義で始まるもの
関数 = トレイトの無名サブクラスのインスタンス

### 高階関数

まとめ中...
