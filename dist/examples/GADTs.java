// ********************************************************************
// language    : FGJ-omega (Featherweight Generic Java omega)
// file        : GADTs.java
// authors     : Philippe Altherr and Vincent Cremet
// date        : May 2007
// description : Implementation of a safe evaluator using generalized
//               algebraic data types.
// see         : The paper "Adding Type Constructor Parameterization to Java"
//               by P. Altherr and V. Cremet, submitted to FTfJP'07.
// ********************************************************************

///////////////////////////////////////////////////////////////////////
// Standard Java Classes

class Boolean {
    boolean value;
    Boolean(boolean value) { super(); this.value = value; }
    boolean booleanValue() { return value; }
}

class Integer {
    int value;
    Integer(int value) { super(); this.value = value; }
    int intValue() { return value; }
}

///////////////////////////////////////////////////////////////////////
// Expressions

abstract class Expr<X> {
    abstract <R<_>> R<X> accept(Visitor<R> v);
}
abstract class Visitor<R<_>> {
    abstract R<Integer> caseIntLit(int x);
    abstract R<Boolean> caseBoolLit(boolean x);
    abstract R<Integer> casePlus(Expr<Integer> x, Expr<Integer> y);
    abstract R<Boolean> caseCompare(Expr<Integer> x, Expr<Integer> y);
    abstract <X> R<X> caseIf(Expr<Boolean> x, Expr<X> y, Expr<X> z);
}
class IntLit extends Expr<Integer> {
    int x;
    <R<_>> R<Integer> accept(Visitor<R> v) { return v.caseIntLit(x); }
}
class BoolLit extends Expr<Boolean> {
    boolean x;
    <R<_>> R<Boolean> accept(Visitor<R> v) { return v.caseBoolLit(x); }
}
class Plus extends Expr<Integer> {
    Expr<Integer> x;  Expr<Integer> y;
    <R<_>> R<Integer> accept(Visitor<R> v) { return v.casePlus(x, y); }
}
class Compare extends Expr<Boolean> {
    Expr<Integer> x;  Expr<Integer> y;
    <R<_>> R<Boolean> accept(Visitor<R> v) { return v.caseCompare(x, y); }
}
class If<Y> extends Expr<Y> {
    Expr<Boolean> x;  Expr<Y> y; Expr<Y> z;
    <R<_>> R<Y> accept(Visitor<R> v) { return v.<Y>caseIf(x, y, z); }
}


///////////////////////////////////////////////////////////////////////
// Safe evaluator

class Eval extends Visitor<<X> => X> {
    <T> T eval(Expr<T> e) { return  e.<<X> => X>accept(this); }
    Integer caseIntLit(int x) {
        return new Integer(x);
    }
    Boolean caseBoolLit(boolean x) {
        return new Boolean(x);
    }
    Integer casePlus(Expr<Integer> x, Expr<Integer> y) {
        return new Integer(this.<Integer>eval(x).intValue() + this.<Integer>eval(y).intValue());
    }
    Boolean caseCompare(Expr<Integer> x, Expr<Integer> y) {
        return new Boolean(this.<Integer>eval(x).intValue() < this.<Integer>eval(y).intValue());
    }
    <X> X caseIf(Expr<Boolean> x, Expr<X> y, Expr<X> z) {
        if (this.<Boolean>eval(x).booleanValue())
            return this.<X>eval(y);
        else
            return this.<X>eval(z);
    }
}

///////////////////////////////////////////////////////////////////////
// Tests

class TestGADTs {

    Expr<Integer> test1() {
        return new Plus(new Plus(new IntLit(1), new IntLit(2)), new IntLit(3));
    }
//     Expr<Integer> test2() {
//         return new Plus(new IntLit(4), new BoolLit(false));
//     }
    Expr<Integer> test3() {
        return new If<Integer>(new BoolLit(true), new IntLit(5), new IntLit(6));
    }
//     Expr<Integer> test4() {
//         return new If<Integer>(new BoolLit(false),new IntLit(7),new BoolLit(true));
//     }

    int test5() {
        return new Eval().<Integer>eval(new IntLit(9)).intValue();
    }
//     int test6() {
//         return new Eval().<Integer>eval(new BoolLit(true)).intValue();
//     }

    int test7() {
        return new Eval().<Integer>eval(
            new If<Integer>(
                new Compare(new IntLit(3), new IntLit(7)),
                new Plus(new IntLit(4), new IntLit(5)),
                new IntLit(8))).intValue();
    }

}

return new TestGADTs().test7();

///////////////////////////////////////////////////////////////////////
