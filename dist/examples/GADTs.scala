// ********************************************************************
// language    : FGJ-omega (Featherweight Generic Java omega)
// file        : GADTs.scala
// authors     : Philippe Altherr and Vincent Cremet
// date        : December 2006
// description :
//   Implementation of a safe evaluator using generalized algebraic data
//   types.
// see         : The paper "Abstract Type Constructors for Java-like
//   Languages" by P. Altherr and V. Cremet, submitted to ECOOP'07.
// ********************************************************************

///////////////////////////////////////////////////////////////////////
// Expressions

class Expr[X] {
  def matchWith[R[_]](v: Visitor[R]): R[X]
}
class Visitor[R[_]] {
  def caseIntLit(x: Int): R[Int]
  def caseBoolLit(x: Boolean): R[Boolean]
  def casePlus(x: Expr[Int], y: Expr[Int]): R[Int]
  def caseCompare(x: Expr[Int], y: Expr[Int]): R[Boolean]
  def caseIf[X](x: Expr[Boolean], y: Expr[X], z: Expr[X]): R[X]
}
class IntLit extends Expr[Int] {
  val x: Int
  def matchWith[R[_]](v: Visitor[R]): R[Int] =
    v.caseIntLit(x)
}
class BoolLit extends Expr[Boolean] {
  val x: Boolean
  def matchWith[R[_]](v: Visitor[R]): R[Boolean] =
    v.caseBoolLit(x)
}
class Plus extends Expr[Int] {
  val x: Expr[Int] val y: Expr[Int]
  def matchWith[R[_]](v: Visitor[R]): R[Int] =
    v.casePlus(x, y)
}
class Compare extends Expr[Boolean] {
  val x: Expr[Int] val y: Expr[Int]
  def matchWith[R[_]](v: Visitor[R]): R[Boolean] =
    v.caseCompare(x, y)
}
class If[Y] extends Expr[Y] {
  val x: Expr[Boolean] val y: Expr[Y] val z: Expr[Y]
  def matchWith[R[_]](v: Visitor[R]): R[Y] =
    v.caseIf[Y](x, y, z)
}


///////////////////////////////////////////////////////////////////////
// Safe evaluator

//   def eval[T](e: Expr[T]): T =
//     e.matchWith[[X] => X](new Visitor[[X] => X]{
//       def caseIntLit(x: Int): Int = x
//       def caseBoolLit(x: Boolean): Boolean = x
//       def casePlus(x: Expr[Int], y: Expr[Int]): Int =
//         eval[Int](x) + eval[Int](y)
//       def caseCompare(x: Expr[Int], y: Expr[Int]): Boolean =
//         eval[Int](x) < eval[Int](y)
//       def caseIf[X](x: Expr[Boolean], y: Expr[X] , z: Expr[X]): X =
//         if (eval[Boolean](x)) eval[X](y) else eval[X](z)
//     })

class Eval {
  def eval[T](e: Expr[T]): T =
    e.matchWith[[X] => X](new EvalVisitor(this))
}
class EvalVisitor extends Visitor[[X] => X] {
  val evaluator: Eval
  def caseIntLit(x: Int): Int = x
  def caseBoolLit(x: Boolean): Boolean = x
  def casePlus(x: Expr[Int], y: Expr[Int]): Int =
    evaluator.eval[Int](x) + evaluator.eval[Int](y)
  def caseCompare(x: Expr[Int], y: Expr[Int]): Boolean =
    evaluator.eval[Int](x) < evaluator.eval[Int](y)
  def caseIf[X](x: Expr[Boolean], y: Expr[X] , z: Expr[X]): X =
    if (evaluator.eval[Boolean](x))
      evaluator.eval[X](y)
    else
      evaluator.eval[X](z)
}

///////////////////////////////////////////////////////////////////////
// Tests

class TestGADTs {

  def test1(): Expr[Int] =
    new Plus(new Plus(new IntLit(1), new IntLit(2)), new IntLit(3))
//   def test2(): Expr[Int] =
//     new Plus(new IntLit(4), new BoolLit(false))
  def test3(): Expr[Int] =
    new If[Int](new BoolLit(true), new IntLit(5), new IntLit(6))
//   def test4(): Expr[Int] =
//     new If[Int](new BoolLit(false), new IntLit(7), new BoolLit(true))

  def test5(): Int =
    (new Eval).eval[Int](new IntLit(9))
//   def test6(): Int =
//     (new Eval).eval[Int](new BoolLit(true))

  def test7(): Int =
    (new Eval).eval[Int](
      new If[Int](
          new Compare(new IntLit(3), new IntLit(7)),
          new Plus(new IntLit(4), new IntLit(5)),
          new IntLit(8)))
}

(new TestGADTs).test7()

///////////////////////////////////////////////////////////////////////
