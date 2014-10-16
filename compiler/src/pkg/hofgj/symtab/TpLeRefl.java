/*     */ package pkg.hofgj.symtab;
/*     */ 
/*     */ class TpLeRefl extends TpLeProof
/*     */ {
/*     */   public final AType type;
/*     */ 
/*     */   public TpLeRefl(AType paramAType)
/*     */   {
/* 367 */     this.type = paramAType;
/*     */   }
/*     */ 
/*     */   public AType getLfType() {
/* 371 */     return this.type;
/*     */   }
/*     */ 
/*     */   public AType getRgType() {
/* 375 */     return this.type;
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.TpLeRefl
 * JD-Core Version:    0.6.2
 */