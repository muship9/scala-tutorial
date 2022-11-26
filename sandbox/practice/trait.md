# trait

モジュール化( プログラムの分割 ) の中心的な概念  
クラス間でインターフェイスとフィールドを共有するために使われる  
java における interface の代わりに使用でき interface との違いは実装を持つことができる ことである。  
-> デフォルト実装を各トレイトに記述しミックスインするクラスで実装を使用などに用いられる

- 特徴
    - 複数のトレイトを一つのクラスやトレイトにミックスインできる
    - 直接インスタンス化できない
    - クラスパラメータ ( コンストラクタの引数 ) を取ることができない

### 複数のトレイトを一つのクラスやトレイトにミックスインできる

```scala
trait TraitA

trait TraitB

class ClassA

class ClassB

// コンパイルできる -> 複数トレイトの継承は可能
class ClassC extends ClassA with TraitA with TraitB

// コンパイルエラー -> 複数クラスの継承は不可能
class ClassD extends ClassA with ClassB
```

### 直接インスタンス化できない

```scala
trait TraitA

object ObjectA {
  //  トレイトは単体で使われうことを想定していないための制限
  val a = new TraitA
}

trait TraitA

class ClassA extends TraitA

object ObjectA {
  //  インスタンス化可能
  val a = new ClassA
}
```

### クラスパラメータ（コンストラクタの引数）を取ることができない

```scala
class ClassA(name: String) {
  def printName() = println(name)
}

// error: traits or objects may not have parameters
// パラメーターを取ることができないためコンパイルエラー
trait TraitA(name: String)

↓

trait TraitA {
  val name: String

  def printName(): Unit = println(name)
}

// クラスにして name を上書きする
class ClassA(val name: String) extends TraitA

object ObjectA {
  val a = new ClassA("dwango")

  // name を上書きするような実装を与えても良い
  val a2 = new TraitA {
    val name = "kadokawa"
  }
}

```

### 菱形継承問題 ( ダイヤモンド継承 )

多重継承を持つプログラミング言語が直面する問題

```scala
trait TraitA {
  def greet(): Unit
}

trait TraitB extends TraitA {
  def greet(): Unit = println("Good Morning")
}

trait TraitC extends TraitA {
  def greet(): Unit = println("Good evening")
}

class ClassA extends TraitB with TraitC

// error
// On line 13: error: class ClassA inherits conflicting members:
// def greet(): Unit (defined in trait TraitB) and
// def greet(): Unit (defined in trait TraitC)
// (note: this can be resolved by declaring an `override` in class ClassA.)
```

原因  
TraitB と TraitC でメソッドの実装が衝突していてコンパイラがどっちで動作すればいいかわからなくなっている

解決  
override を指定してメソッドの衝突を防ぐ

```scala
class ClassA extends TraitB with TraitC {
  override def greet(): Unit = println("How are you?")
}

(new ClassA).greet()
// How are you?

// super に型を指定して呼び出すことで継承元のメソッドを指定して使用できる 
class ClassB extends TraitB with TraitC {
  override def greet(): Unit = super[TraitB].greet()
}

(new ClassB).greet()
// Good morning!
```

`TraitB` / `TraitC` 両方のメソッドを呼びたい場合は、両方のクラスを明示して呼び出す

```scala
class ClassA extends TraitB with TraitC {
  override def greet(): Unit = {
    super[TraitB].greet()
    super[TraitC].greet()
  }
}
```

継承関係が複雑になった時に全てを明示的に呼ぶのは大変でなので `線形化( linearization )` で解決できる

### 線形化（linearization）

トレイトがミックスインされた順番をトレイトの継承順番とみなす

```scala
trait TraitA {
  def greet(): Unit
}

trait TraitB extends TraitA {
  override def greet(): Unit = println("Good mornig")

  def test(): Unit = println("test TraitB")
}

trait TraitC extends TraitA {
  override def greet(): Unit = println("Good Evening")

  def tests(): Unit = println("test TraitB")
}

class ClassA extends TraitB with TraitC

// トレイトの継承順番が線形化されて後からミックスインした TraitC の greet メソッドが実行される
(new ClassA).greet()
// Good evening!

// 順番を逆にすると TraitB の greet メソットが実行される
class ClassA extends TraitC with TraitB

(new ClassA).greet()
// Good mornig!
```

`super` を使うことで線形化された親トレイトを使用できる  
( 線形化されたトレイトの中から指定番のメソッドを実行することはできるのかな... )

```scala
trait TraitA {
  def greet(): Unit = println("Hello")
}

trait TraitB extends TraitA {
  override def greet(): Unit = {
    super.greet()
    println("My name is Terebi-chan.")
  }
}

trait TraitC extends TraitA {
  override def greet(): Unit = {
    super.greet()
    println("I like niconico")
  }
}

class ClassA extends TraitB with TraitC

class ClassB extends TraitC with TraitB

// 実行結果
scala > (new ClassA).greet()
// Hello　My name is Terebi -chan.　I like niconico

scala > (new ClassB).greet()
//Hello I like niconicoMy name is Terebi -chan.
```
