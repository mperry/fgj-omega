/*     */ package pkg.hofgj.symtab;
/*     */ 
/*     */ abstract class Result<Prf extends Proof<?>>
/*     */ {
/*     */   public static <Prf extends Proof<?>> Result<Prf> success(Prf paramPrf)
/*     */   {
/* 735 */     return new Success(paramPrf, null);
/*     */   }
/*     */ 
/*     */   public static <Prf extends Proof<?>> Result<Prf> failure(Theorem paramTheorem, Result<?>[] paramArrayOfResult)
/*     */   {
/* 740 */     return new Failure(paramTheorem, paramArrayOfResult, null);
/*     */   }
/*     */ 
/*     */   public static <Prf extends Proof<?>> Result<Prf> failure(Theorem paramTheorem, Result<?> paramResult)
/*     */   {
/* 745 */     return failure(paramTheorem, new Result[] { paramResult });
/*     */   }
/*     */   public static <Prf extends Proof<?>> Result<Prf> failure(Theorem paramTheorem) {
/* 748 */     return failure(paramTheorem, new Result[0]);
/*     */   }
/*     */   public abstract boolean isSuccess();
/*     */ 
/*     */   public abstract Theorem getTheorem();
/*     */ 
/*     */   public abstract Prf getProof();
/*     */ 
/* 756 */   public final boolean isFailure() { return !isSuccess(); }
/*     */ 
/*     */ 
/*     */   public static class Failure<Prf extends Proof<?>> extends Result<Prf>
/*     */   {
/*     */     public final Theorem theorem;
/*     */     public final Result<?>[] results;
/*     */ 
/*     */     private Failure(Theorem paramTheorem, Result<?>[] paramArrayOfResult)
/*     */     {
/* 779 */       this.theorem = paramTheorem;
/* 780 */       this.results = paramArrayOfResult;
/*     */     }
/*     */     public boolean isSuccess() {
/* 783 */       return false;
/*     */     }
/*     */     public Theorem getTheorem() {
/* 786 */       return this.theorem;
/*     */     }
/*     */     public Prf getProof() {
/* 789 */       throw new Error("Failure.getProof()");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Success<Prf extends Proof<?>> extends Result<Prf>
/*     */   {
/*     */     public final Prf proof;
/*     */ 
/*     */     private Success(Prf paramPrf)
/*     */     {
/* 762 */       this.proof = paramPrf;
/*     */     }
/*     */     public boolean isSuccess() {
/* 765 */       return true;
/*     */     }
/*     */     public Theorem getTheorem() {
/* 768 */       return this.proof.getTheorem();
/*     */     }
/*     */     public Prf getProof() {
/* 771 */       return this.proof;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.Result
 * JD-Core Version:    0.6.2
 */