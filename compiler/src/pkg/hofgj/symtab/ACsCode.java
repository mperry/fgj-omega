/*     */ package pkg.hofgj.symtab;
/*     */ 
/*     */ abstract class ACsCode
/*     */ {
/*     */   public abstract Kind getKind();
/*     */ 
/*     */   public static class Anonymous extends ACsCode
/*     */   {
/*     */     public final ACsVariable[] cparams;
/*     */     public final AType body;
/*     */ 
/*     */     public Anonymous(ACsVariable[] paramArrayOfACsVariable, AType paramAType)
/*     */     {
/* 240 */       this.cparams = paramArrayOfACsVariable;
/* 241 */       this.body = paramAType;
/*     */     }
/*     */     public ACsCode.Kind getKind() {
/* 244 */       return ACsCode.Kind.ANONYMOUS;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Reference extends ACsCode
/*     */   {
/*     */     public final ACsReference constructor;
/*     */ 
/*     */     public Reference(ACsReference paramACsReference)
/*     */     {
/* 229 */       this.constructor = paramACsReference;
/*     */     }
/*     */     public ACsCode.Kind getKind() {
/* 232 */       return ACsCode.Kind.REFERENCE;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum Kind
/*     */   {
/* 222 */     REFERENCE, ANONYMOUS;
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.ACsCode
 * JD-Core Version:    0.6.2
 */