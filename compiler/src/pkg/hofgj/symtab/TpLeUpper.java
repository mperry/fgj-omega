/*     */ package pkg.hofgj.symtab;
/*     */ 
/*     */ class TpLeUpper extends TpLeProof
/*     */ {
/*     */   public final TS ts;
/*     */   public final AType type;
/*     */ 
/*     */   public TpLeUpper(TS paramTS, AType paramAType)
/*     */   {
/* 407 */     this.ts = paramTS;
/* 408 */     this.type = paramAType;
/*     */   }
/*     */ 
/*     */   public AType getLfType() {
/* 412 */     return this.type;
/*     */   }
/*     */ 
/*     */   public AType getRgType() {
/* 416 */     return (AType)this.ts.getUpperBound(this.type).get();
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.TpLeUpper
 * JD-Core Version:    0.6.2
 */