// ********************************************************************
// language    : FGJ-omega (Featherweight Generic Java omega)
// file        : Sequences.scala
// authors     : Philippe Altherr and Vincent Cremet
// date        : December 2006
// description :
//   Library for sequences.
// see         : The paper "Abstract Type Constructors for Java-like
//   Languages" by P. Altherr and V. Cremet, submitted to ECOOP'07.
// note        : to be compiled with Predef.scala
// ********************************************************************

///////////////////////////////////////////////////////////////////////
// Sequences

class Seq[A,S[X] <: Seq[X,S]] {
  def map[B](f: Fun[A,B]): S[B]
  def flatMap[B](f: Fun[A,S[B]]): S[B]
  def filter(f: Fun[A,Boolean]): S[A]

  def mapFilter[B](f: Fun[A,B], g: Fun[B,Boolean]): S[B] =
    map[B](f).filter(g)
}

class SeqModule {

  // Math:  { g(x, y) | x in xs, y in ys, f(x, y) }
  // Scala: for (val x <- xs; val y <- ys; f(x,y)) yields g(x, y)
  // Scala: xs.flatMap(x => ys.filter(y => f(x, y)).map(y => g(x, y)))
  def comprehension[S[X] <: Seq[X,S],A,B,C](
    xs: S[A], ys: S[B], f: Fun2[A,B,Boolean], g: Fun2[A,B,C]): S[C]
  =
    xs.flatMap[C](new SeqAnonFlatMap[S,A,B,C](ys, f, g))
}
class SeqAnonFlatMap[S[X] <: Seq[X,S],A,B,C] extends Fun[A,S[C]] {
  val ys: S[B] val f: Fun2[A,B,Boolean] val g: Fun2[A,B,C]
  def apply(x: A): S[C] = ys.filter(
    new SeqAnonFilter[S,A,B](x, f)).map[C](new SeqAnonMap[S,A,B,C](x, g))
}
class SeqAnonFilter[S[X] <: Seq[X,S],A,B] extends Fun[B,Boolean] {
  val x: A val f: Fun2[A,B,Boolean]
  def apply(y: B): Boolean = f.apply(x, y)
}
class SeqAnonMap[S[X] <: Seq[X,S],A,B,C] extends Fun[B,C] {
  val x: A val g: Fun2[A,B,C]
  def apply(y: B): C = g.apply(x, y)
}

///////////////////////////////////////////////////////////////////////
// Lists are sequences

class SeqList[A] extends Seq[A,SeqList] {
  def isEmpty(): Boolean
  def head(): A
  def tail(): SeqList[A]

  def cons(x: A): SeqList[A] = new SeqCons[A](x, this)
  def concat(that: SeqList[A]): SeqList[A] =
    if (isEmpty()) that else new SeqCons[A](head(), tail().concat(that))
  def map[Y](f: Fun[A,Y]): SeqList[Y] =
    if (isEmpty()) new SeqNil[Y]()
    else new SeqCons[Y](f.apply(head()), tail().map[Y](f))
  def flatMap[Y](f: Fun[A,SeqList[Y]]): SeqList[Y] =
    if (isEmpty()) new SeqNil[Y]()
    else f.apply(head()).concat(tail().flatMap[Y](f))
  def filter(f: Fun[A,Boolean]): SeqList[A] =
    if (isEmpty()) this
    else if (f.apply(head())) new SeqCons[A](head(), tail().filter(f))
         else tail().filter(f)
}
class SeqNil[A] extends SeqList[A] {
  def isEmpty(): Boolean = true
  def head(): A =  head()         // undefined
  def tail(): SeqList[A] = tail() // undefined
}
class SeqCons[A] extends SeqList[A] {
  val x: A val xs: SeqList[A]
  def isEmpty(): Boolean = false
  def head(): A =  x
  def tail(): SeqList[A] = xs
}

///////////////////////////////////////////////////////////////////////
// Tests

class TestSequences {
  def xs(): SeqList[Int] = new SeqNil[Int]().cons(3).cons(2).cons(1)
  // test1 = { (x, y) in (xs * xs) | x < y }
  def test1(): SeqList[Pair[Int,Int]] =
    new SeqModule().comprehension[SeqList,Int,Int,Pair[Int,Int]](
      xs(), xs(), new Lt, new MakePair[Int,Int]())
}
class Lt extends Fun2[Int,Int,Boolean] {
  def apply(x: Int, y: Int): Boolean = x < y
}
class MakePair[A,B] extends Fun2[A,B,Pair[A,B]] {
  def apply(x: A, y: B): Pair[A,B] = new Pair[A,B](x, y)
}

new TestSequences().test1()

///////////////////////////////////////////////////////////////////////
