/*    */ package pkg.util;
/*    */ 
/*    */ public abstract class Promise<Type>
/*    */ {
/*    */   public static <Type> Promise<Type> thunk(Thunk<Type> paramThunk)
/*    */   {
/*  9 */     return new ThunkPromise(paramThunk);
/*    */   }
/*    */ 
/*    */   public static <Type> Promise<Type> value(Type paramType) {
/* 13 */     return new ValuePromise(paramType);
/*    */   }
/*    */ 
/*    */   public abstract boolean isReady();
/*    */ 
/*    */   public abstract Type force();
/*    */ 
/*    */   public abstract <X> Promise<X> map(Function1<Type, X> paramFunction1);
/*    */ 
/*    */   private static class ValuePromise<Type> extends Promise<Type>
/*    */   {
/*    */     private final Type value;
/*    */ 
/*    */     public ValuePromise(Type paramType)
/*    */     {
/* 58 */       this.value = paramType; } 
/* 59 */     public boolean isReady() { return true; } 
/* 60 */     public Type force() { return this.value; } 
/*    */     public <X> Promise<X> map(Function1<Type, X> paramFunction1) {
/* 62 */       return value(paramFunction1.apply(this.value));
/*    */     }
/* 64 */     public String toString() { return String.valueOf(this.value); }
/*    */ 
/*    */   }
/*    */ 
/*    */   private static class ThunkPromise<Type> extends Promise<Type>
/*    */   {
/*    */     private Thunk<Type> thunk;
/*    */     private Type value;
/*    */ 
/*    */     public ThunkPromise(Thunk<Type> paramThunk)
/*    */     {
/* 30 */       assert (paramThunk != null);
/* 31 */       this.thunk = paramThunk;
/*    */     }
/*    */     public boolean isReady() {
/* 34 */       return this.thunk == null;
/*    */     }
/*    */     public Type force() {
/* 37 */       if (this.thunk != null) {
/* 38 */         Object localObject = this.thunk.evaluate();
/* 39 */         if (this.thunk != null) {
/* 40 */           this.thunk = null;
/* 41 */           this.value = localObject;
/*    */         }
/*    */       }
/* 44 */       return this.value;
/*    */     }
/*    */     public <X> Promise<X> map(final Function1<Type, X> paramFunction1) {
/* 47 */       return thunk(new Thunk() {
/* 48 */         public X evaluate() { return paramFunction1.apply(Promise.ThunkPromise.this.force()); } } );
/*    */     }
/*    */ 
/*    */     public String toString() {
/* 52 */       return this.thunk != null ? "<" + this.thunk + ">" : String.valueOf(this.value);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.util.Promise
 * JD-Core Version:    0.6.2
 */