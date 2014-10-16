// ********************************************************************
// language    : FGJ-omega (Featherweight Generic Java omega)
// file        : Bifunctors.scala
// authors     : Philippe Altherr and Vincent Cremet
// date        : December 2006
// description :
//   Library for bi-functors. Example adapted from the paper "Design
//   Patterns as Higher-Order Datatype-Generic Programs" by Jeremy
//   Gibbons (see original version in Haskell at the end).
// see         : The paper "Abstract Type Constructors for Java-like
//   Languages" by P. Altherr and V. Cremet, submitted to ECOOP'07.
// note        : to be compiled with Predef.scala
// ********************************************************************

///////////////////////////////////////////////////////////////////////
// Bifunctors

class Fix[S[X,Y],A] { val out: S[A,Fix[S,A]] }

class Bifunctor[S[X,Y]] {

  def bimap[A,B,C,D](f: Fun[A,C], g: Fun[B,D], s: S[A,B]): S[C,D]

  def map[A,B](f: Fun[A,B], xs: Fix[S,A]): Fix[S,B] =
    new Fix[S,B](bimap[A,Fix[S,A],B,Fix[S,B]](
      f, new AnonMap[S,A,B](this, f), xs.out))

  def fold[A,B](f: Fun[S[A,B],B], xs: Fix[S,A]): B =
    f.apply(bimap[A,Fix[S,A],A,B](
      new IdFun[A], new AnonFold[S,A,B](this, f), xs.out))

  def unfold[A,B](f: Fun[B,S[A,B]], x: B): Fix[S,A] =
    new Fix[S,A](bimap[A,B,A,Fix[S,A]](
      new IdFun[A], new AnonUnfold[S,A,B](this, f), f.apply(x)))

  def hylo[A,B,C](f: Fun[B,S[A,B]], g: Fun[S[A,C],C], x: B): C =
    g.apply(bimap[A,B,A,C](
      new IdFun[A], new AnonHylo[S,A,B,C](this, f, g), f.apply(x)))

  def build[A](f: Forall[[B] => Fun[Fun[S[A,B],B],B]]): Fix[S,A] =
    f.instantiate[Fix[S,A]]().apply(new AnonBuild[S,A])
}
class AnonMap[S[X,Y],A,B] extends Fun[Fix[S,A],Fix[S,B]] {
  val bf: Bifunctor[S] val f: Fun[A,B]
  def apply(x: Fix[S,A]): Fix[S,B] = bf.map[A,B](f, x)
}
class AnonFold[S[X,Y],A,B] extends Fun[Fix[S,A],B] {
  val bf: Bifunctor[S] val f: Fun[S[A,B],B]
  def apply(x: Fix[S,A]): B = bf.fold[A,B](f, x)
}
class AnonUnfold[S[X,Y],A,B] extends Fun[B,Fix[S,A]] {
  val bf: Bifunctor[S] val f: Fun[B,S[A,B]]
  def apply(x: B): Fix[S,A] = bf.unfold[A,B](f, x)
}
class AnonHylo[S[X,Y],A,B,C] extends Fun[B,C] {
  val bf: Bifunctor[S] val f: Fun[B,S[A,B]]  val g: Fun[S[A,C],C]
  def apply(x: B): C = bf.hylo[A,B,C](f, g, x)
}
class AnonBuild[S[X,Y],A] extends Fun[S[A,Fix[S,A]],Fix[S,A]] {
  def apply(x: S[A,Fix[S,A]]): Fix[S,A] = new Fix[S,A](x)
}

///////////////////////////////////////////////////////////////////////
// The List data type

// data ListF a b = NilF | ConsF a b
class ListF[A,B] { def matchWith[R](v: ListFVisitor[A,B,R]): R }
class ListFVisitor[A,B,R] { def caseNilF(): R def caseConsF(x: A, y: B): R }
class NilF[A,B] extends ListF[A,B] { def matchWith[R](v: ListFVisitor[A,B,R]): R = v.caseNilF() }
class ConsF[A,B] extends ListF[A,B] { val x: A val y: B
  def matchWith[R](v: ListFVisitor[A,B,R]): R = v.caseConsF(x, y)
}

// type List[A] = Fix[ListF,A]
class ListOps[A] {
  def nil(): Fix[ListF,A] = new Fix[ListF,A](new NilF[A,Fix[ListF,A]])
  def cons(x: A, xs: Fix[ListF,A]): Fix[ListF,A] =
    new Fix[ListF,A](new ConsF[A,Fix[ListF,A]](x, xs))
}

// instance Bifunctor ListF where
//   bimap f g NilF = NilF
//   bimap f g (ConsF x y) = ConsF (f x) (g y)
class ListFBifunctor extends Bifunctor[ListF] {
  def bimap[A,B,C,D](f: Fun[A,C], g: Fun[B,D], s: ListF[A,B]): ListF[C,D] =
    s.matchWith[ListF[C,D]](new AnonClass1[A,B,C,D](f, g))
}
class AnonClass1[A,B,C,D] extends ListFVisitor[A,B,ListF[C,D]] {
  val f: Fun[A,C] val g: Fun[B,D]
  def caseNilF(): ListF[C,D] = new NilF[C,D]
  def caseConsF(x: A, y: B): ListF[C,D] =
    new ConsF[C,D](f.apply(x), g.apply(y))
}

///////////////////////////////////////////////////////////////////////
// Tests

class TestBifunctors {
  def lm(): ListOps[Int] = new ListOps[Int]
  def xs(): Fix[ListF,Int] = lm().cons(1, lm().cons(2, lm().nil()))
  def test1(): Fix[ListF,Int] =
    (new ListFBifunctor).map[Int,Int](new DoubleFun, xs())
}
class DoubleFun extends Fun[Int,Int] {
  def apply(x: Int): Int = x + x
}

(new TestBifunctors).test1()

///////////////////////////////////////////////////////////////////////
// Original Haskell program, by Jeremy Gibbons

// class Bifunctor s where

// bimap :: (a -> c) -> (b -> d) -> (s a b) -> (s c d)

// data Fix s a = In (s a (Fix s a))

// out :: Fix s a -> s a (Fix s a)
// out (In x) = x

// map :: Bifunctor s =>
// (a -> b) -> Fix s a -> Fix s b
// map f = In . bimap f (map f) . out

// fold :: Bifunctor s =>
// (s a b -> b) -> Fix s a -> b
// fold f = f . bimap id (fold f ) . out

// unfold :: Bifunctor s =>
// (b -> s a b) -> b -> Fix s a
// unfold f = In . bimap id (unfold f ) . f

// hylo :: Bifunctor s =>
// (b -> s a b) -> (s a c -> c) -> b -> c
// hylo f g = g . bimap id (hylo f g) . f

// build :: Bifunctor s =>
// (forall b. (s a b -> b) -> b) -> Fix s a
// build f = f In

///////////////////////////////////////////////////////////////////////
