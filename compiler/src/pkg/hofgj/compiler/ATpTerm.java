/*     */ package pkg.hofgj.compiler;
/*     */ 
/*     */ import pkg.util.Promise;
/*     */ import pkg.util.Union;
/*     */ 
/*     */ public abstract class ATpTerm extends ATree
/*     */   implements Union<ATpTerm, Kind>
/*     */ {
/*     */   public abstract boolean isError();
/*     */ 
/*     */   public abstract ATpTerm evaluate();
/*     */ 
/*     */   public static class Delayed extends ATpTerm
/*     */   {
/*     */     public final Promise<ATpTerm> promise;
/*     */ 
/*     */     public Delayed(Promise<ATpTerm> paramPromise)
/*     */     {
/* 114 */       this.promise = paramPromise;
/*     */     }
/*     */     public Delayed update(Promise<ATpTerm> paramPromise) {
/* 117 */       int i = 0;
/* 118 */       i |= (this.promise != paramPromise ? 1 : 0);
/* 119 */       if (i == 0) return this;
/* 120 */       Delayed localDelayed = new Delayed(paramPromise);
/* 121 */       localDelayed.position(position());
/* 122 */       return localDelayed;
/*     */     }
/* 124 */     public ATpTerm.Kind getKind() { return ATpTerm.Kind.DELAYED; } 
/* 125 */     public boolean isError() { return evaluate().isError(); } 
/* 126 */     public ATpTerm evaluate() { return (ATpTerm)this.promise.force(); }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Error extends ATpTerm
/*     */   {
/*     */     public Error update()
/*     */     {
/* 100 */       int i = 0;
/* 101 */       if (i == 0) return this;
/* 102 */       Error localError = new Error();
/* 103 */       localError.position(position());
/* 104 */       return localError;
/*     */     }
/* 106 */     public ATpTerm.Kind getKind() { return ATpTerm.Kind.ERROR; } 
/* 107 */     public boolean isError() { return true; } 
/* 108 */     public ATpTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Abstract extends ATpTerm
/*     */   {
/*     */     public final ATpParam param;
/*     */     public final ACsTerm[] args;
/*     */ 
/*     */     public Abstract(ATpParam paramATpParam, ACsTerm[] paramArrayOfACsTerm)
/*     */     {
/*  79 */       this.param = paramATpParam;
/*  80 */       this.args = paramArrayOfACsTerm;
/*     */     }
/*     */     public Abstract update(ATpParam paramATpParam, ACsTerm[] paramArrayOfACsTerm) {
/*  83 */       int i = 0;
/*  84 */       i |= (this.param != paramATpParam ? 1 : 0);
/*  85 */       i |= (this.args != paramArrayOfACsTerm ? 1 : 0);
/*  86 */       if (i == 0) return this;
/*  87 */       Abstract localAbstract = new Abstract(paramATpParam, paramArrayOfACsTerm);
/*  88 */       localAbstract.position(position());
/*  89 */       return localAbstract;
/*     */     }
/*  91 */     public ATpTerm.Kind getKind() { return ATpTerm.Kind.ABSTRACT; } 
/*  92 */     public boolean isError() { return false; } 
/*  93 */     public ATpTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Concrete extends ATpTerm
/*     */   {
/*     */     public final AClass clasz;
/*     */     public final ACsTerm[] args;
/*     */ 
/*     */     public Concrete(AClass paramAClass, ACsTerm[] paramArrayOfACsTerm)
/*     */     {
/*  58 */       this.clasz = paramAClass;
/*  59 */       this.args = paramArrayOfACsTerm;
/*     */     }
/*     */     public Concrete update(AClass paramAClass, ACsTerm[] paramArrayOfACsTerm) {
/*  62 */       int i = 0;
/*  63 */       i |= (this.clasz != paramAClass ? 1 : 0);
/*  64 */       i |= (this.args != paramArrayOfACsTerm ? 1 : 0);
/*  65 */       if (i == 0) return this;
/*  66 */       Concrete localConcrete = new Concrete(paramAClass, paramArrayOfACsTerm);
/*  67 */       localConcrete.position(position());
/*  68 */       return localConcrete;
/*     */     }
/*  70 */     public ATpTerm.Kind getKind() { return ATpTerm.Kind.CONCRETE; } 
/*  71 */     public boolean isError() { return false; } 
/*  72 */     public ATpTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Integer extends ATpTerm
/*     */   {
/*     */     public Integer update()
/*     */     {
/*  43 */       int i = 0;
/*  44 */       if (i == 0) return this;
/*  45 */       Integer localInteger = new Integer();
/*  46 */       localInteger.position(position());
/*  47 */       return localInteger;
/*     */     }
/*  49 */     public ATpTerm.Kind getKind() { return ATpTerm.Kind.INTEGER; } 
/*  50 */     public boolean isError() { return false; } 
/*  51 */     public ATpTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Boolean extends ATpTerm
/*     */   {
/*     */     public Boolean update()
/*     */     {
/*  28 */       int i = 0;
/*  29 */       if (i == 0) return this;
/*  30 */       Boolean localBoolean = new Boolean();
/*  31 */       localBoolean.position(position());
/*  32 */       return localBoolean;
/*     */     }
/*  34 */     public ATpTerm.Kind getKind() { return ATpTerm.Kind.BOOLEAN; } 
/*  35 */     public boolean isError() { return false; } 
/*  36 */     public ATpTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static enum Kind
/*     */   {
/*  13 */     BOOLEAN, INTEGER, CONCRETE, ABSTRACT, ERROR, DELAYED;
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.ATpTerm
 * JD-Core Version:    0.6.2
 */