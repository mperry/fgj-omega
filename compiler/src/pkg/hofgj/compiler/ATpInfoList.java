/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ class ATpInfoList extends ATree
/*      */ {
/*      */   private final ATpInfo[] infos;
/*      */ 
/*      */   public ATpInfoList(ATpInfo[] paramArrayOfATpInfo)
/*      */   {
/* 3207 */     this.infos = paramArrayOfATpInfo;
/*      */   }
/*      */ 
/*      */   public ATpInfoList update(ATpInfo[] paramArrayOfATpInfo)
/*      */   {
/* 3214 */     int i = 0;
/* 3215 */     i |= (this.infos != paramArrayOfATpInfo ? 1 : 0);
/* 3216 */     if (i == 0) return this;
/* 3217 */     ATpInfoList localATpInfoList = new ATpInfoList(paramArrayOfATpInfo);
/* 3218 */     localATpInfoList.position(position());
/* 3219 */     return localATpInfoList;
/*      */   }
/*      */ 
/*      */   public ATpInfo[] getInfos() {
/* 3223 */     return this.infos;
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.ATpInfoList
 * JD-Core Version:    0.6.2
 */