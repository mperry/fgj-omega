/*     */ package pkg.hofgj.symtab;
/*     */ 
/*     */ class TpLeTrans extends TpLeProof
/*     */ {
/*     */   public final TpLeProof lf;
/*     */   public final TpLeProof rg;
/*     */ 
/*     */   public TpLeTrans(TpLeProof paramTpLeProof1, TpLeProof paramTpLeProof2)
/*     */   {
/* 386 */     this.lf = paramTpLeProof1;
/* 387 */     this.rg = paramTpLeProof2;
/*     */   }
/*     */ 
/*     */   public AType getLfType() {
/* 391 */     return this.lf.getLfType();
/*     */   }
/*     */ 
/*     */   public AType getRgType() {
/* 395 */     return this.rg.getRgType();
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.TpLeTrans
 * JD-Core Version:    0.6.2
 */