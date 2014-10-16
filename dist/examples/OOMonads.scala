// ********************************************************************
// language    : FGJ-omega (Featherweight Generic Java omega)
// file        : OOMonads.scala
// authors     : Philippe Altherr and Vincent Cremet
// date        : December 2006
// description :
//   Library for object-oriented monads.
// see         : The paper "Abstract Type Constructors for Java-like
//   Languages" by P. Altherr and V. Cremet, submitted to ECOOP'07.
// note        : to be compiled with Predef.scala
// ********************************************************************

///////////////////////////////////////////////////////////////////////
// Monads

class MonadModule[Monad[_]] {

  def unit[X](x: X): Monad[X]

//   def bind[X,Y](xs: Monad[X], f: X => Monad[Y]): Monad[Y]
  def bind[X,Y](xs: Monad[X], f: Fun[X,Monad[Y]]): Monad[Y]

//   def map[X,Y](xs: Monad[X], f: X => Y): Monad[Y] =
//     bind[X,Y](xs, (x: X) => unit[Y](f(x)))
  def map[X,Y](xs: Monad[X], f: Fun[X,Y]): Monad[Y] =
    bind[X,Y](xs, new AnonFun1[Monad,X,Y](this,f))

//   def sequence[X](xs: List[Monad[X]]): Monad[List[X]] =
//     if (xs.isEmpty())
//       unit[List[X]](Nil)
//     else
//       bind[X,List[X]](xs.head(),
//       (x: X) =>
//         bind[List[X],List[X]](sequence[X](xs.tail()),
//         (ys: List[X]) =>
//           unit[List[X]](x :: ys)))
  def sequence[X](xs: List[Monad[X]]): Monad[List[X]] =
    if (xs.isEmpty())
      unit[List[X]](new Nil[X])
    else
      bind[X,List[X]](xs.head(), new AnonFun2[Monad,X](this, xs))
}
class AnonFun1[Monad[_],X,Y] extends Fun[X,Monad[Y]] {
  val self: MonadModule[Monad] val f: Fun[X,Y]
  def apply(x: X): Monad[Y] = self.unit[Y](f.apply(x))
}
class AnonFun2[Monad[_],X] extends Fun[X,Monad[List[X]]] {
  val self: MonadModule[Monad] val xs: List[Monad[X]]
  def apply(x: X): Monad[List[X]] =
    self.bind[List[X],List[X]](
      self.sequence[X](xs.tail()), new AnonFun3[Monad,X](self, x))
}
class AnonFun3[Monad[_],X] extends Fun[List[X],Monad[List[X]]] {
  val self: MonadModule[Monad] val x: X
  def apply(ys: List[X]): Monad[List[X]] =
    self.unit[List[X]](new Cons[X](x, ys))
}

///////////////////////////////////////////////////////////////////////
// Options are monads

class OptionMonadModule extends MonadModule[Option] {
  def unit[X](x: X): Option[X] =
    new Some[X](x)
//  def bind[X,Y](xs: Option[X], f: X => Option[Y]): Option[Y] =
  def bind[X,Y](xs: Option[X], f: Fun[X,Option[Y]]): Option[Y] =
    if (xs.isEmpty()) new None[Y] else f.apply(xs.get())
}

///////////////////////////////////////////////////////////////////////
// Lists are monads

class ListMonadModule extends MonadModule[List] {
  def unit[X](x: X): List[X] =
    new Cons[X](x, new Nil[X])
  def bind[X,Y](xs: List[X], f: Fun[X,List[Y]]): List[Y] =
    xs.flatMap[Y](f)
}

///////////////////////////////////////////////////////////////////////
// Zero monads

class ZeroMonadModule[Monad[_]] extends MonadModule[Monad] {
  def zero[X](): Monad[X]

//   def filter[X](xs: Monad[X], f : X => Boolean): Monad[X] =
//     bind[X,X](xs, (x: X) => if (f(x)) unit[X](x) else zero[X]())
  def filter[X](xs: Monad[X], f : Fun[X,Boolean]): Monad[X] =
    bind[X,X](xs, new AnonFun4[Monad,X](this, f))
}
class AnonFun4[Monad[_],X] extends Fun[X,Monad[X]] {
  val self: ZeroMonadModule[Monad] val f: Fun[X,Boolean]
  def apply(x: X): Monad[X] =
    if (f.apply(x)) self.unit[X](x) else self.zero[X]()
}

///////////////////////////////////////////////////////////////////////
// State transformer monads

// type StateT[S,X] = S => Pair[X,S]
// class StateMonadModule[S] extends MonadModule[[Z] => StateT[S,Z]] {
//   def unit[X](x: X): StateT[S,X] =
//     (s: S) => new Pair[X,S](x, s)
//   def bind[X,Y](xs: StateT[S,X], f: X => StateT[S,Y]): StateT[S,Y] =
//     (s: S) => { val p: Pair[X,S] = xs(s); f(p.fst)(p.snd) }
// }
class StateMonadModule[S] extends MonadModule[[Z] => Fun[S,Pair[Z,S]]] {
  def unit[X](x: X): Fun[S,Pair[X,S]] =
    new AnonFun5[S,X](x)
  def bind[X,Y](
    xs: Fun[S,Pair[X,S]], f: Fun[X,Fun[S,Pair[Y,S]]]): Fun[S,Pair[Y,S]]
  =
    new AnonFun6[S,X,Y](xs, f)
}
class AnonFun5[S,X] extends Fun[S,Pair[X,S]] {
  val x: X
  def apply(s: S): Pair[X,S] = new Pair[X,S](x, s)
}
class AnonFun6[S,X,Y] extends Fun[S,Pair[Y,S]] {
  val xs: Fun[S,Pair[X,S]] val f: Fun[X,Fun[S,Pair[Y,S]]]
  def apply(s: S): Pair[Y,S] = (new AnonFun7[S,X,Y](f, xs.apply(s))).apply()
}
class AnonFun7[S,X,Y] {
  val f: Fun[X,Fun[S,Pair[Y,S]]] val p: Pair[X,S]
  def apply(): Pair[Y,S] = f.apply(p.fst).apply(p.snd)
}

///////////////////////////////////////////////////////////////////////
// Tests

class TestMonads {
  def none(): Option[Int] = new None[Int]
  def some(x: Int): Option[Int] = new Some[Int](x)
  def nil(): List[Option[Int]] = new Nil[Option[Int]]
  def xs: List[Option[Int]] = nil().cons(some(1)).cons(some(2)).cons(some(3))
  def ys: List[Option[Int]] = nil().cons(some(1)).cons(none()).cons(some(3))

  def test1(): Option[List[Int]] = (new OptionMonadModule).sequence[Int](xs())
  def test2(): Option[List[Int]] = (new OptionMonadModule).sequence[Int](ys())
}

(new TestMonads).test1()

///////////////////////////////////////////////////////////////////////
