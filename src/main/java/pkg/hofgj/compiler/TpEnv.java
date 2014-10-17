/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ import pkg.util.List;
/*      */ import pkg.util.Option;
/*      */ 
/*      */ class TpEnv
/*      */ {
/*      */   private final Option<AClass> classes;
/*      */   private final List<ATpTerm[]> scopes;
/*      */ 
/*      */   public TpEnv(Option<AClass> paramOption, List<ATpTerm[]> paramList)
/*      */   {
/* 3188 */     this.classes = paramOption;
/* 3189 */     this.scopes = paramList;
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.TpEnv
 * JD-Core Version:    0.6.2
 */