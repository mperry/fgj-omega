/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ class AVlInfo extends ATree
/*      */ {
/*      */   private final ATpTerm type;
/*      */ 
/*      */   public AVlInfo(ATpTerm paramATpTerm)
/*      */   {
/* 3318 */     this.type = paramATpTerm;
/*      */   }
/*      */ 
/*      */   public AVlInfo update(ATpTerm paramATpTerm)
/*      */   {
/* 3325 */     int i = 0;
/* 3326 */     i |= (this.type != paramATpTerm ? 1 : 0);
/* 3327 */     if (i == 0) return this;
/* 3328 */     AVlInfo localAVlInfo = new AVlInfo(paramATpTerm);
/* 3329 */     localAVlInfo.position(position());
/* 3330 */     return localAVlInfo;
/*      */   }
/*      */ 
/*      */   public ATpTerm getType() {
/* 3334 */     return this.type;
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.AVlInfo
 * JD-Core Version:    0.6.2
 */