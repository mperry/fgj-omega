// ********************************************************************
// language    : FGJ-omega (Featherweight Generic Java omega)
// file        : GADTs.scala
// authors     : Philippe Altherr and Vincent Cremet
// date        : April 2008
// description :
//   Implementation of a safe evaluator using generalized algebraic data
//   types.
// see         : The paper "Abstract Type Constructors for Java-like
//   Languages" by P. Altherr and V. Cremet, submitted to ECOOP'07.
// ********************************************************************

///////////////////////////////////////////////////////////////////////
// Expressions

class Expr[F[T1,T2]] {
  def matchWith[R[_],T1,T2](v: Visitor[R,T1,T2]): R[F[T1,T2]]
}
class Visitor[R[_],T1,T2] {
  def caseIntLit(x: Int): R[T1]
  def caseBoolLit(x: Boolean): R[T2]
  def casePlus(x: Expr[[T1,T2]=>T1], y: Expr[[T1,T2]=>T1]): R[T1]
  def caseCompare(x: Expr[[T1,T2]=>T1], y: Expr[[T1,T2]=>T1]): R[T2]
  def caseIf[F[T1,T2]](x: Expr[[T1,T2]=>T2], y: Expr[F], z: Expr[F]): R[F[T1,T2]]
}
class IntLit extends Expr[[T1,T2]=>T1] {
  val x: Int
  def matchWith[R[_],T1,T2](v: Visitor[R,T1,T2]): R[T1] =
    v.caseIntLit(x)
}
class BoolLit extends Expr[[T1,T2]=>T2] {
  val x: Boolean
  def matchWith[R[_],T1,T2](v: Visitor[R,T1,T2]): R[T2] =
    v.caseBoolLit(x)
}
class Plus extends Expr[[T1,T2]=>T1] {
  val x: Expr[[T1,T2]=>T1] val y: Expr[[T1,T2]=>T1]
  def matchWith[R[_],T1,T2](v: Visitor[R,T1,T2]): R[T1] =
    v.casePlus(x, y)
}
class Compare extends Expr[[T1,T2]=>T2] {
  val x: Expr[[T1,T2]=>T1] val y: Expr[[T1,T2]=>T1]
  def matchWith[R[_],T1,T2](v: Visitor[R,T1,T2]): R[T2] =
    v.caseCompare(x, y)
}
class If[F[T1,T2]] extends Expr[F] {
  val x: Expr[[T1,T2]=>T2] val y: Expr[F] val z: Expr[F]
  def matchWith[R[_],T1,T2](v: Visitor[R,T1,T2]): R[F[T1,T2]] =
    v.caseIf[F](x, y, z)
}


///////////////////////////////////////////////////////////////////////
// Safe evaluator

//   def eval[T](e: Expr[T]): T =
//     e.matchWith[[X] => X](new Visitor[[X] => X]{
//       def caseIntLit(x: Int): Int = x
//       def caseBoolLit(x: Boolean): Boolean = x
//       def casePlus(x: Expr[[T1,T2]=>T1], y: Expr[[T1,T2]=>T1]): Int =
//         eval[[T1,T2]=>T1](x) + eval[[T1,T2]=>T1](y)
//       def caseCompare(x: Expr[[T1,T2]=>T1], y: Expr[[T1,T2]=>T1]): Boolean =
//         eval[[T1,T2]=>T1](x) < eval[[T1,T2]=>T1](y)
//       def caseIf[X](x: Expr[[T1,T2]=>T2], y: Expr[X] , z: Expr[X]): X =
//         if (eval[[T1,T2]=>T2](x)) eval[X](y) else eval[X](z)
//     })

class Eval {
  def eval[F[T1,T2]](e: Expr[F]): F[Int,Boolean] =
    e.matchWith[[X]=>X,Int,Boolean](new EvalVisitor(this))
}
class EvalVisitor extends Visitor[[X]=>X,Int,Boolean] {
  val evaluator: Eval
  def caseIntLit(x: Int): Int = x
  def caseBoolLit(x: Boolean): Boolean = x
  def casePlus(x: Expr[[T1,T2]=>T1], y: Expr[[T1,T2]=>T1]): Int =
    evaluator.eval[[T1,T2]=>T1](x) + evaluator.eval[[T1,T2]=>T1](y)
  def caseCompare(x: Expr[[T1,T2]=>T1], y: Expr[[T1,T2]=>T1]): Boolean =
    evaluator.eval[[T1,T2]=>T1](x) < evaluator.eval[[T1,T2]=>T1](y)
  def caseIf[F[T1,T2]](x: Expr[[T1,T2]=>T2], y: Expr[F] , z: Expr[F]): F[Int,Boolean] =
    if (evaluator.eval[[T1,T2]=>T2](x))
      evaluator.eval[F](y)
    else
      evaluator.eval[F](z)
}

class Print {
  def print[F[T1,T2]](e: Expr[F]): String =
    e.matchWith[[_]=>String,Boolean,String](new PrintVisitor(this))
}
class PrintVisitor extends Visitor[[_]=>String,Boolean,String] {
  val printer: Print

  def forInt(x: Int): String = new String();
  def forBoolean(x: Boolean): String = new String();

  def caseIntLit(x: Int): String = forInt(x)
  def caseBoolLit(x: Boolean): String = forBoolean(x)
  def casePlus(x: Expr[[T1,T2]=>T1], y: Expr[[T1,T2]=>T1]): String =
    printer.print[[T1,T2]=>T1](x).append(printer.print[[T1,T2]=>T1](y))
  def caseCompare(x: Expr[[T1,T2]=>T1], y: Expr[[T1,T2]=>T1]): String =
    printer.print[[T1,T2]=>T1](x).append(printer.print[[T1,T2]=>T1](y))
  def caseIf[F[T1,T2]](x: Expr[[T1,T2]=>T2], y: Expr[F] , z: Expr[F]): String =
    printer.print[[T1,T2]=>T2](x)
      .append(printer.print[F](y))
      .append(printer.print[F](z))
}

///////////////////////////////////////////////////////////////////////
// Tests

class TestGADTs {

  def test1(): Expr[[T1,T2]=>T1] =
    new Plus(new Plus(new IntLit(1), new IntLit(2)), new IntLit(3))
//   def test2(): Expr[[T1,T2]=>T1] =
//     new Plus(new IntLit(4), new BoolLit(false))
  def test3(): Expr[[T1,T2]=>T1] =
    new If[[T1,T2]=>T1](new BoolLit(true), new IntLit(5), new IntLit(6))
//   def test4(): Expr[[T1,T2]=>T1] =
//     new If[[T1,T2]=>T1](new BoolLit(false), new IntLit(7), new BoolLit(true))

  def test5(): Int =
    (new Eval).eval[[T1,T2]=>T1](new IntLit(9))
//   def test6(): Int =
//     (new Eval).eval[[T1,T2]=>T1](new BoolLit(true))

  def test7(): Int =
    (new Eval).eval[[T1,T2]=>T1](
      new If[[T1,T2]=>T1](
          new Compare(new IntLit(3), new IntLit(7)),
          new Plus(new IntLit(4), new IntLit(5)),
          new IntLit(8)))
}

(new TestGADTs).test7()

///////////////////////////////////////////////////////////////////////
