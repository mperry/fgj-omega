// ********************************************************************
// language    : FGJ-omega (Featherweight Generic Java omega)
// file        : OOMonads.java
// authors     : Philippe Altherr and Vincent Cremet
// date        : May 2007
// description : Library for object-oriented monads.
// see         : The paper "Adding Type Constructor Parameterization to Java"
//               by P. Altherr and V. Cremet, submitted to FTfJP'07.
// note        : to be compiled with Predef.java
// ********************************************************************

///////////////////////////////////////////////////////////////////////
// Monads

class Monad<M<X> extends Monad<M,X,S>,A, S extends MonadStatic<M,S>> {
    M<A> self();
    S getStatic();
    <B> M<B> bind(Fun<A,M<B>> f);
//  <B> M<B> map({A => B} f) {
//      return <B>bind({ A x => getStatic().<B>unit(f(x)) }); }
    <B> M<B> map(Fun<A,B> f) {
        return <B>bind(new AnonFun1<M,A,S,B>(getStatic(), f));
    }
}

class AnonFun1<M<X> extends Monad<M,X,S>,A,S extends MonadStatic<M,S>,B>
    extends Fun<A,M<B>>
{
    S getStatic; Fun<A,B> f;
    M<B> apply(A x) { return getStatic.<B>unit(f.apply(x)); }
}

class MonadStatic<M<X> extends Monad<M,X,S>, S extends MonadStatic<M,S>> {
    S self();
    <X> M<X> unit(X x);
//  <X> M<List<X>> sequence(List<M<X>> xs) {
//      return xs.isEmpty()
//          ? <List<X>>unit(new Nil<X>())
//          : xs.head().<List<X>>bind(
//             { X x => <X>sequence(xs.tail()).<List<X>>bind(
//                 { List<X> ys => <List<X>>unit(ys.cons(x)) }) })
    <X> M<List<X>> sequence(List<M<X>> xs) {
        return xs.isEmpty()
            ? <List<X>>unit(new Nil<X>())
            : xs.head().<List<X>>bind(new AnonFun2<M,S,X>(self(), xs));
    }
}

class AnonFun2<M<A> extends Monad<M,A,S>,S extends MonadStatic<M,S>,X>
    extends Fun<X,M<List<X>>>
{
    S getStatic; List<M<X>> xs;
    M<List<X>> apply(X x) {
        return getStatic.<X>sequence(xs.tail())
            .<List<X>>bind(new AnonFun3<M,S,X>(getStatic, x));
    }
}

class AnonFun3<M<A> extends Monad<M,A,S>,S extends MonadStatic<M,S>,X>
    extends Fun<List<X>,M<List<X>>>
{
    S getStatic; X x;
    M<List<X>> apply(List<X> ys) {
        return getStatic.<List<X>>unit(ys.cons(x));
    }
}

///////////////////////////////////////////////////////////////////////
// Monads with zero and plus

class MonadPlus<M<X> extends MonadPlus<M,X,S>,A,S extends MonadPlusStatic<M,S>>
    extends Monad<M,A,S>
{
    M<A> plus(M<A> xs);
//  M<A> filter({A => boolean} f) {
//      return <A>bind(
//          { A x => f(x) ? getStatic().<A>unit(x) : getStatic().<A>zero() });}
    M<A> filter(Fun<A,boolean> f) {
        return <A>bind(new AnonFun4<M,A,S>(getStatic(), f));
    }
}

class AnonFun4<M<X> extends MonadPlus<M,X,S>,A,S extends MonadPlusStatic<M,S>>
    extends Fun<A,M<A>>
{
    S getStatic; Fun<A,boolean> f;
    M<A> apply(A x) {
        return f.apply(x) ? getStatic.<A>unit(x) : getStatic.<A>zero();
    }
}

class MonadPlusStatic<M<X> extends MonadPlus<M,X,S>,
                      S    extends MonadPlusStatic<M,S>>
    extends MonadStatic<M,S>
{
    <A> M<A> zero();
    M<Unit> guard(boolean x) {
        return  x ? <Unit>unit(new Unit()) : <Unit>zero();
    }
    <A> M<A> sum(List<M<A>> xs) {
        return xs.isEmpty() ? <A>zero() : xs.head().plus(<A>sum(xs.tail()));
    }
}

///////////////////////////////////////////////////////////////////////
// Options are zero-plus monads

class OptionM<A> extends MonadPlus<OptionM,A,OptionMStatic> {
    OptionM<A> self() { return this; }
    OptionMStatic getStatic() { return new OptionMStatic(); }

    boolean isEmpty();
    A get();

    <B> OptionM<B> bind(Fun<A,OptionM<B>> f) {
        return isEmpty() ? new NoneM<B>() : f.apply(get());
    }
    OptionM<A> plus(OptionM<A> that) {
        return (this.isEmpty() && that.isEmpty()) ? new NoneM<A>() :
               (this.isEmpty())                   ? new SomeM<A>(that.get()) :
                                                    new SomeM<A>(this.get());
    }
}

class NoneM<A> extends OptionM<A> {
    boolean isEmpty() { return true; }
    A get() { return  get(); } // undefined
}

class SomeM<A> extends OptionM<A> {
    A x;
    boolean isEmpty() { return false; }
    A get() { return  x; }
}

class OptionMStatic extends MonadPlusStatic<OptionM,OptionMStatic> {
    OptionMStatic self() {  return this; }
    <X> OptionM<X> unit(X x) { return new SomeM<X>(x); }
    <X> OptionM<X> zero() { return new NoneM<X>(); }
}

///////////////////////////////////////////////////////////////////////
// Tests

class TestOOMonads {
    OptionM<int> none() {
        return new NoneM<int>();
    }
    OptionM<int> some(int x) {
        return new SomeM<int>(x);
    }
    List<OptionM<int>> nil() {
        return new Nil<OptionM<int>>();
    }
    List<OptionM<int>> xs() {
        return nil().cons(some(3)).cons(some(2)).cons(some(1));
    }
    List<OptionM<int>> ys() {
        return nil().cons(some(3)).cons(none()).cons(some(1)).cons(none());
    }

    OptionM<List<int>> test1() {
        return (new OptionMStatic()).<int>sequence(xs());
    }
    OptionM<List<int>> test2() {
        return (new OptionMStatic()).<int>sequence(ys());
    }
    OptionM<int> test3() {
        return (new OptionMStatic()).<int>sum(xs());
    }
    OptionM<int> test4() {
        return (new OptionMStatic()).<int>sum(ys());
    }

    <M<X> extends Monad<M,X,S>,A,S extends MonadStatic<M,S>>
    S getStatic(M<A> x) {
        return x.getStatic();
    }
    OptionMStatic test5() {
        return <OptionM,int,OptionMStatic>getStatic(new NoneM<int>());
    }
}

return new TestOOMonads().test1();

///////////////////////////////////////////////////////////////////////
