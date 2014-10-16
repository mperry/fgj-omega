/*     */ package pkg.hofgj.symtab;
/*     */ 
/*     */ class ACsVariable
/*     */ {
/*     */   private final ACsVariable[] cparams;
/*     */   private Option<AType> ubound;
/*     */ 
/*     */   public ACsVariable(ACsVariable[] paramArrayOfACsVariable)
/*     */   {
/* 172 */     this.cparams = paramArrayOfACsVariable;
/*     */   }
/*     */ 
/*     */   public ACsVariable[] getCsParameters() {
/* 176 */     return this.cparams;
/*     */   }
/*     */ 
/*     */   public ACsVariable setUpperBound(Option<AType> paramOption) {
/* 180 */     assert ((this.ubound == null) && (paramOption != null));
/* 181 */     this.ubound = paramOption;
/* 182 */     return this;
/*     */   }
/*     */ 
/*     */   public Option<AType> getUpperBound() {
/* 186 */     assert (this.ubound != null);
/* 187 */     return this.ubound;
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.ACsVariable
 * JD-Core Version:    0.6.2
 */