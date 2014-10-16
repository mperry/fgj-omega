// ********************************************************************
// language    : FGJ-omega (Featherweight Generic Java omega)
// file        : Predef.scala
// authors     : Philippe Altherr and Vincent Cremet
// date        : December 2006
// description :
//   Basic data structures and operations.
// see         : The paper "Abstract Type Constructors for Java-like
//   Languages" by P. Altherr and V. Cremet, submitted to ECOOP'07.
// ********************************************************************

///////////////////////////////////////////////////////////////////////
// Functions

class Fun[X,Y] {
  def apply(x: X): Y
}

class Fun2[A,B,C] {
  def apply(x: A, y: B): C
}

class IdFun[A] extends Fun[A,A] {
  def apply(x: A): A = x
}

///////////////////////////////////////////////////////////////////////
// Unit

class Unit { }

///////////////////////////////////////////////////////////////////////
// Pairs

class Pair[X,Y] {
  val fst: X val snd: Y
}

///////////////////////////////////////////////////////////////////////
// Options

class Option[X] {
  def isEmpty(): Boolean
  def get(): X
}
class None[X] extends Option[X] {
  def isEmpty(): Boolean = true
  def get(): X =  get() // undefined
}
class Some[X] extends Option[X] {
  val x: X
  def isEmpty(): Boolean = false
  def get(): X =  x
}

///////////////////////////////////////////////////////////////////////
// Lists

class List[X] {
  def isEmpty(): Boolean
  def head(): X
  def tail(): List[X]

  def cons(x: X): List[X] = new Cons[X](x, this)
  def concat(that: List[X]): List[X] =
    if (isEmpty()) that else new Cons[X](head(), tail().concat(that))
  def flatMap[Y](f: Fun[X,List[Y]]): List[Y] =
    if (isEmpty()) new Nil[Y]
    else f.apply(head()).concat(tail().flatMap[Y](f))
}
class Nil[X] extends List[X] {
  def isEmpty(): Boolean = true
  def head(): X =  head()      // undefined
  def tail(): List[X] = tail() // undefined
}
class Cons[X] extends List[X] {
  val x: X val xs: List[X]
  def isEmpty(): Boolean = false
  def head(): X =  x
  def tail(): List[X] = xs
}

///////////////////////////////////////////////////////////////////////
// Universal quantifier: "forall A.T{A}" is represented by  Forall[T]

class Forall[T[_]] { def instantiate[A](): T[A] }

///////////////////////////////////////////////////////////////////////
// Predefined functions

class Predef {
    def let[A,B](x: A, f: Fun[A,B]): B = f.apply(x)
    def intToString(x: Int): String = new String()
    def booleanToString(x: Boolean): String = new String()
}

///////////////////////////////////////////////////////////////////////
// Strings

class String {
  def append(that: String): String = new String()
}

///////////////////////////////////////////////////////////////////////
