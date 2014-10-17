/*    */ package pkg.hofgj.compiler;
/*    */ 
/*    */ import pkg.util.Promise;
/*    */ 
/*    */ public abstract class ACsTerm extends ATree
/*    */ {
/*    */   public abstract Kind getKind();
/*    */ 
/*    */   public abstract boolean isError();
/*    */ 
/*    */   public abstract ACsTerm evaluate();
/*    */ 
/*    */   public static class Delayed extends ACsTerm
/*    */   {
/*    */     public final Promise<ACsTerm> promise;
/*    */ 
/*    */     public Delayed(Promise<ACsTerm> paramPromise)
/*    */     {
/* 61 */       this.promise = paramPromise;
/*    */     }
/*    */     public Delayed update(Promise<ACsTerm> paramPromise) {
/* 64 */       int i = 0;
/* 65 */       i |= (this.promise != paramPromise ? 1 : 0);
/* 66 */       if (i == 0) return this;
/* 67 */       Delayed localDelayed = new Delayed(paramPromise);
/* 68 */       localDelayed.position(position());
/* 69 */       return localDelayed;
/*    */     }
/* 71 */     public ACsTerm.Kind getKind() { return ACsTerm.Kind.DELAYED; } 
/* 72 */     public boolean isError() { return evaluate().isError(); } 
/* 73 */     public ACsTerm evaluate() { return (ACsTerm)this.promise.force(); }
/*    */ 
/*    */   }
/*    */ 
/*    */   public static class Error extends ACsTerm
/*    */   {
/*    */     public Error update()
/*    */     {
/* 47 */       int i = 0;
/* 48 */       if (i == 0) return this;
/* 49 */       Error localError = new Error();
/* 50 */       localError.position(position());
/* 51 */       return localError;
/*    */     }
/* 53 */     public ACsTerm.Kind getKind() { return ACsTerm.Kind.ERROR; } 
/* 54 */     public boolean isError() { return true; } 
/* 55 */     public ACsTerm evaluate() { return this; }
/*    */ 
/*    */   }
/*    */ 
/*    */   public static class Function extends ACsTerm
/*    */   {
/*    */     public final ATpParam[] params;
/*    */     public final ATpTerm body;
/*    */ 
/*    */     public Function(ATpParam[] paramArrayOfATpParam, ATpTerm paramATpTerm)
/*    */     {
/* 26 */       this.params = paramArrayOfATpParam;
/* 27 */       this.body = paramATpTerm;
/*    */     }
/*    */     public Function update(ATpParam[] paramArrayOfATpParam, ATpTerm paramATpTerm) {
/* 30 */       int i = 0;
/* 31 */       i |= (this.params != paramArrayOfATpParam ? 1 : 0);
/* 32 */       i |= (this.body != paramATpTerm ? 1 : 0);
/* 33 */       if (i == 0) return this;
/* 34 */       Function localFunction = new Function(paramArrayOfATpParam, paramATpTerm);
/* 35 */       localFunction.position(position());
/* 36 */       return localFunction;
/*    */     }
/* 38 */     public ACsTerm.Kind getKind() { return ACsTerm.Kind.FUNCTION; } 
/* 39 */     public boolean isError() { return false; } 
/* 40 */     public ACsTerm evaluate() { return this; }
/*    */ 
/*    */   }
/*    */ 
/*    */   public static enum Kind
/*    */   {
/* 10 */     FUNCTION, ERROR, DELAYED;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.ACsTerm
 * JD-Core Version:    0.6.2
 */