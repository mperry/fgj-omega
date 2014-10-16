/*     */ package pkg.hofgj.symtab;
/*     */ 
/*     */ abstract class TpLeProof extends Proof<TpLeTheorem>
/*     */ {
/*     */   public abstract AType getLfType();
/*     */ 
/*     */   public abstract AType getRgType();
/*     */ 
/*     */   public TpLeTheorem getTheorem()
/*     */   {
/* 357 */     return new TpLeTheorem(getLfType(), getRgType());
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.TpLeProof
 * JD-Core Version:    0.6.2
 */