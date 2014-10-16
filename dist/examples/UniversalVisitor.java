// ********************************************************************
// language    : FGJ-omega (Featherweight Generic Java omega)
// file        : UniversalVisitor.java
// authors     : Philippe Altherr and Vincent Cremet
// date        : April 2008
// description :
//   Implementation of a safe evaluator using generalized algebraic data
//   types.
// see paper   : "Adding Type Constructor Parameterization to Java"
// ********************************************************************

///////////////////////////////////////////////////////////////////////
// Expressions

abstract class Expr<F<T1,T2>> {
    abstract <R<_>,T1,T2> R<F<T1,T2>> accept(Visitor<R,T1,T2> v);
}
abstract class Visitor<R<_>,T1,T2> {
    abstract R<T1> caseIntLit(IntLit expr);
    abstract R<T2> caseBoolLit(BoolLit expr);
    abstract R<T1> casePlus(Plus expr);
    abstract R<T2> caseCompare(Compare expr);
    abstract <F<T1,T2>> R<F<T1,T2>> caseIf(If<F> expr);
}
class IntLit extends Expr<<T1,T2> => T1> {
    int x;
    <R<_>,T1,T2> R<T1> accept(Visitor<R,T1,T2> v) {
	return v.caseIntLit(this);
    }
}
class BoolLit extends Expr<<T1,T2> => T2> {
    boolean x;
    <R<_>,T1,T2> R<T2> accept(Visitor<R,T1,T2> v) {
	return v.caseBoolLit(this);
    }
}
class Plus extends Expr<<T1,T2> => T1> {
  Expr<<T1,T2> => T1> x; Expr<<T1,T2> => T1> y;
  <R<_>,T1,T2> R<T1> accept(Visitor<R,T1,T2> v) {
      return v.casePlus(this);
  }
}
class Compare extends Expr<<T1,T2> => T2> {
  Expr<<T1,T2> => T1> x; Expr<<T1,T2> => T1> y;
  <R<_>,T1,T2> R<T2> accept(Visitor<R,T1,T2> v) {
      return v.caseCompare(this);
  }
}
class If<F<T1,T2>> extends Expr<F> {
    Expr<<T1,T2> => T2> x; Expr<F> y; Expr<F> z;
    <R<_>,T1,T2>R<F<T1,T2>> accept(Visitor<R,T1,T2> v) {
	return v.<F>caseIf(this);
    }
}

///////////////////////////////////////////////////////////////////////
// Safe evaluator

class EvalVisitor extends Visitor<<X> => X,int,boolean> {

    <F<T1,T2>> F<int,boolean> eval(Expr<F> e) {
	return e.<<X> => X,int,boolean>accept(this);
    }

    int caseIntLit(IntLit expr) { return expr.x; }
    boolean caseBoolLit(BoolLit expr) { return expr.x; }
    int casePlus(Plus expr) {
	return <<T1,T2> => T1>eval(expr.x) + <<T1,T2> => T1>eval(expr.y);
    }
    boolean caseCompare(Compare expr) {
	return <<T1,T2> => T1>eval(expr.x) < <<T1,T2> => T1>eval(expr.y);
    }
    <F<T1,T2>> F<int,boolean> caseIf(If<F> expr) {
	return (<<T1,T2> => T2>eval(expr.x)) ?
	<F>eval(expr.y) :
	<F>eval(expr.z);
    }
}

///////////////////////////////////////////////////////////////////////
// Pretty printer

class PrintVisitor extends Visitor<<_> => String,boolean,String> {

    <F<T1,T2>> String print(Expr<F> e) {
	return e.<<_> => String,boolean,String>accept(this);
    }

    String caseIntLit(IntLit expr) { return (new Predef()).intToString(expr.x); }
    String caseBoolLit(BoolLit expr) { return (new Predef()).booleanToString(expr.x); }
    String casePlus(Plus expr) {
	return <<T1,T2> => T1>print(expr.x).append(<<T1,T2> => T1>print(expr.y));
    }
    String caseCompare(Compare expr) {
	return <<T1,T2> => T1>print(expr.x).append(<<T1,T2> => T1>print(expr.y));
    }
    <F<T1,T2>> String caseIf(If<F> expr) {
	return <<T1,T2> => T2>print(expr.x)
	.append(<F>print(expr.y))
	.append(<F>print(expr.z));
    }
}

///////////////////////////////////////////////////////////////////////
// Tests

class TestGADTs {

    Expr<<T1,T2> => T1> test1() {
	return new Plus(new Plus(new IntLit(1), new IntLit(2)), new IntLit(3));
    }
//   def test2(): Expr<<T1,T2> => T1> =
//     new Plus(new IntLit(4), new BoolLit(false))
    Expr<<T1,T2> => T1> test3() {
	return new If<<T1,T2> => T1>(new BoolLit(true), new IntLit(5), new IntLit(6));
    }
//   def test4(): Expr<<T1,T2> => T1> =
//     new If<<T1,T2> => T1>(new BoolLit(false), new IntLit(7), new BoolLit(true))

    int test5() {
	return (new EvalVisitor()).<<T1,T2> => T1>eval(new IntLit(9));
    }
//   def test6(): Int =
//     (new Eval).eval<<T1,T2> => T1>(new BoolLit(true))

    int test7() {
	return (new EvalVisitor()).<<T1,T2> => T1>eval(
        new If<<T1,T2> => T1>(
          new Compare(new IntLit(3), new IntLit(7)),
          new Plus(new IntLit(4), new IntLit(5)),
          new IntLit(8)));
    }
}

return (new TestGADTs()).test7();

///////////////////////////////////////////////////////////////////////
