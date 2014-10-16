/*    */ package pkg.hofgj.symtab;
/*    */ 
/*    */ class AVlMethod
/*    */ {
/*    */   private final ACsVariable[] cparams;
/*    */   private final AVlVariable[] vparams;
/*    */   private final AType type;
/*    */ 
/*    */   public AVlMethod(ACsVariable[] paramArrayOfACsVariable, AVlVariable[] paramArrayOfAVlVariable, AType paramAType)
/*    */   {
/* 67 */     this.cparams = paramArrayOfACsVariable;
/* 68 */     this.vparams = paramArrayOfAVlVariable;
/* 69 */     this.type = paramAType;
/*    */   }
/*    */ 
/*    */   public ACsVariable[] getCsParameters() {
/* 73 */     return this.cparams;
/*    */   }
/*    */ 
/*    */   public AVlVariable[] getVlParameters() {
/* 77 */     return this.vparams;
/*    */   }
/*    */ 
/*    */   public AType getType() {
/* 81 */     return this.type;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.AVlMethod
 * JD-Core Version:    0.6.2
 */