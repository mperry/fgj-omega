/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ import pkg.hofgj.stree.SLabel;
/*      */ import pkg.hofgj.stree.STree;
/*      */ import pkg.util.Option;
/*      */ import pkg.util.P2;
/*      */ 
/*      */ abstract class Scope
/*      */ {
/*      */   public final Compiler compiler;
/*      */   public final Context context;
/*      */ 
/*      */   public Scope(Context paramContext)
/*      */   {
/* 2415 */     this.compiler = paramContext.compiler;
/* 2416 */     this.context = paramContext;
/*      */   }
/*      */ 
/*      */   public abstract Option<AClass> getCurrentClass();
/*      */ 
/*      */   public abstract Option<FTpTerm> lookupType(String paramString);
/*      */ 
/*      */   public abstract VlTp lookupField(SLabel paramSLabel);
/*      */ 
/*      */   public abstract Option<P2<AVlTerm, P2<ATpParam[], ATpTerm[]>>> lookupMethod(SLabel paramSLabel, AVlTerm paramAVlTerm, ACsTerm[] paramArrayOfACsTerm, AVlTerm[] paramArrayOfAVlTerm);
/*      */ 
/*      */   public final void error(STree paramSTree, String paramString)
/*      */   {
/* 2435 */     this.context.error(paramSTree, paramString);
/*      */   }
/*      */ 
/*      */   public final void error(int paramInt, String paramString) {
/* 2439 */     this.context.error(paramInt, paramString);
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.Scope
 * JD-Core Version:    0.6.2
 */