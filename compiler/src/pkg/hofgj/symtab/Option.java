/*     */ package pkg.hofgj.symtab;
/*     */ 
/*     */ abstract class Option<Type>
/*     */ {
/*     */   public abstract boolean isEmpty();
/*     */ 
/*     */   public abstract Type get();
/*     */ 
/*     */   public abstract Type get(Type paramType);
/*     */ 
/*     */   public static class Some<Type> extends Option<Type>
/*     */   {
/*     */     public final Type value;
/*     */ 
/*     */     public Some(Type paramType)
/*     */     {
/* 310 */       this.value = paramType;
/*     */     }
/*     */ 
/*     */     public boolean isEmpty() {
/* 314 */       return false;
/*     */     }
/*     */ 
/*     */     public Type get() {
/* 318 */       return this.value;
/*     */     }
/*     */ 
/*     */     public Type get(Type paramType) {
/* 322 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class None<Type> extends Option<Type>
/*     */   {
/*     */     public boolean isEmpty()
/*     */     {
/* 292 */       return true;
/*     */     }
/*     */ 
/*     */     public Type get() {
/* 296 */       throw new Error("None.get()");
/*     */     }
/*     */ 
/*     */     public Type get(Type paramType) {
/* 300 */       return paramType;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.Option
 * JD-Core Version:    0.6.2
 */