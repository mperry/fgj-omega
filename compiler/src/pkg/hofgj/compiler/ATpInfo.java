/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ class ATpInfo extends ATree
/*      */ {
/*      */   private final ATpInfoList tlist;
/*      */   private final ATpTerm upper;
/*      */ 
/*      */   public ATpInfo(ATpInfoList paramATpInfoList, ATpTerm paramATpTerm)
/*      */   {
/* 3243 */     this.tlist = paramATpInfoList;
/* 3244 */     this.upper = paramATpTerm;
/*      */   }
/*      */ 
/*      */   public ATpInfo update(ATpInfoList paramATpInfoList, ATpTerm paramATpTerm)
/*      */   {
/* 3251 */     int i = 0;
/* 3252 */     i |= (this.tlist != paramATpInfoList ? 1 : 0);
/* 3253 */     i |= (this.upper != paramATpTerm ? 1 : 0);
/* 3254 */     if (i == 0) return this;
/* 3255 */     ATpInfo localATpInfo = new ATpInfo(paramATpInfoList, paramATpTerm);
/* 3256 */     localATpInfo.position(position());
/* 3257 */     return localATpInfo;
/*      */   }
/*      */ 
/*      */   public ATpInfoList getTpInfoList() {
/* 3261 */     return this.tlist;
/*      */   }
/*      */ 
/*      */   public ATpTerm getUpperBound() {
/* 3265 */     return this.upper;
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.ATpInfo
 * JD-Core Version:    0.6.2
 */