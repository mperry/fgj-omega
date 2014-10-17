/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ class AVlInfoList extends ATree
/*      */ {
/*      */   private final AVlInfo[] infos;
/*      */ 
/*      */   public AVlInfoList(AVlInfo[] paramArrayOfAVlInfo)
/*      */   {
/* 3283 */     this.infos = paramArrayOfAVlInfo;
/*      */   }
/*      */ 
/*      */   public AVlInfoList update(AVlInfo[] paramArrayOfAVlInfo)
/*      */   {
/* 3290 */     int i = 0;
/* 3291 */     i |= (this.infos != paramArrayOfAVlInfo ? 1 : 0);
/* 3292 */     if (i == 0) return this;
/* 3293 */     AVlInfoList localAVlInfoList = new AVlInfoList(paramArrayOfAVlInfo);
/* 3294 */     localAVlInfoList.position(position());
/* 3295 */     return localAVlInfoList;
/*      */   }
/*      */ 
/*      */   public AVlInfo[] getInfos() {
/* 3299 */     return this.infos;
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.AVlInfoList
 * JD-Core Version:    0.6.2
 */