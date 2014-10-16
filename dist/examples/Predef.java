// ********************************************************************
// language    : FGJ-omega (Featherweight Generic Java omega)
// file        : Predef.java
// authors     : Philippe Altherr and Vincent Cremet
// date        : May 2007
// description : Basic data structures and operations.
// see         : The paper "Adding Type Constructor Parameterization to Java"
//               by P. Altherr and V. Cremet, submitted to FTfJP'07.
// ********************************************************************

///////////////////////////////////////////////////////////////////////
// Functions

class Fun<X,Y> {
    Y apply(X x);
}

class Fun2<A,B,C> {
    C apply(A x, B y);
}

class IdFun<A> extends Fun<A,A> {
    A apply(A x) { return x; }
}

///////////////////////////////////////////////////////////////////////
// Unit

class Unit { }

///////////////////////////////////////////////////////////////////////
// Pairs

class Pair<X,Y> {
    X fst; Y snd;
    <R> R matchWith(Fun2<X,Y,R> f) { return f.apply(fst, snd); }
    Pair<Y,X> swap() { return new Pair<Y,X>(snd, fst); }
}

///////////////////////////////////////////////////////////////////////
// Options

class Option<X> {
    boolean isEmpty();
    X get();
}
class None<X> extends Option<X> {
    boolean isEmpty() { return true; }
    X get() { return get(); } // undefined
}
class Some<X> extends Option<X> {
    X x;
    boolean isEmpty() { return false; }
    X get() { return x; }
}

///////////////////////////////////////////////////////////////////////
// Lists

class List<X> {
    boolean isEmpty();
    X head();
    List<X> tail();

    List<X> cons(X x) { return new Cons<X>(x, this); }
    List<X> concat(List<X> that) {
        return isEmpty() ? that : new Cons<X>(head(), tail().concat(that));
    }
    <Y> List<Y> flatMap(Fun<X,List<Y>> f) {
        return isEmpty() ? new Nil<Y>() :
            f.apply(head()).concat(tail().<Y>flatMap(f));
    }
    int length() {
        return isEmpty() ? 0 : tail().length() + 1;
    }
}
class Nil<X> extends List<X> {
    boolean isEmpty() { return true; }
    X head() { return  head(); }      // undefined
    List<X> tail() { return tail(); } // undefined
}
class Cons<X> extends List<X> {
    X x; List<X> xs;
    boolean isEmpty() { return false; }
    X head() { return  x; }
    List<X> tail() { return xs; }
}

///////////////////////////////////////////////////////////////////////
// Universal quantifier: "forall A.T{A}" is represented by  Forall<T>

class Forall<T<_>> { <A> T<A> instantiate(); }

///////////////////////////////////////////////////////////////////////
// Predefined functions

class Predef {
    <A,B> B let(A x, Fun<A,B> f) { return f.apply(x); }
    String intToString(int x) { return new String(); }
    String booleanToString(boolean x) { return new String(); }
}

///////////////////////////////////////////////////////////////////////
// Strings

class String {
    String append(String that) { return new String(); }
}

///////////////////////////////////////////////////////////////////////
