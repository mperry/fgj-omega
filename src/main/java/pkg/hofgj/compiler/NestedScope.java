/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ import pkg.hofgj.stree.SLabel;
/*      */ import pkg.util.Option;
/*      */ import pkg.util.P2;
/*      */ 
/*      */ abstract class NestedScope<ScopeType extends Scope> extends Scope
/*      */ {
/*      */   private final ScopeType outer;
/*      */ 
/*      */   public NestedScope(ScopeType paramScopeType)
/*      */   {
/* 2557 */     super(paramScopeType.context);
/* 2558 */     this.outer = paramScopeType;
/*      */   }
/*      */ 
/*      */   public abstract Option<FTpTerm> lookupTypeLocal(String paramString);
/*      */ 
/*      */   public abstract Option<VlTp> lookupFieldLocal(SLabel paramSLabel);
/*      */ 
/*      */   public abstract Option<P2<AVlTerm, P2<ATpParam[], ATpTerm[]>>> lookupMethodLocal(SLabel paramSLabel, AVlTerm paramAVlTerm, ACsTerm[] paramArrayOfACsTerm, AVlTerm[] paramArrayOfAVlTerm);
/*      */ 
/*      */   public final ScopeType getOuterScope()
/*      */   {
/* 2571 */     return this.outer;
/*      */   }
/*      */ 
/*      */   public Option<AClass> getCurrentClass() {
/* 2575 */     return this.outer.getCurrentClass();
/*      */   }
/*      */ 
/*      */   public Option<FTpTerm> lookupType(String paramString) {
/* 2579 */     Option localOption = lookupTypeLocal(paramString);
/* 2580 */     return localOption.isEmpty() ? this.outer.lookupType(paramString) : localOption;
/*      */   }
/*      */ 
/*      */   public VlTp lookupField(SLabel paramSLabel) {
/* 2584 */     Option localOption = lookupFieldLocal(paramSLabel);
/* 2585 */     return localOption.isEmpty() ? this.outer.lookupField(paramSLabel) : (VlTp)localOption.get();
/*      */   }
/*      */ 
/*      */   public Option<P2<AVlTerm, P2<ATpParam[], ATpTerm[]>>> lookupMethod(SLabel paramSLabel, AVlTerm paramAVlTerm, ACsTerm[] paramArrayOfACsTerm, AVlTerm[] paramArrayOfAVlTerm)
/*      */   {
/* 2591 */     Option localOption = lookupMethodLocal(paramSLabel, paramAVlTerm, paramArrayOfACsTerm, paramArrayOfAVlTerm);
/*      */ 
/* 2593 */     return localOption.isEmpty() ? this.outer.lookupMethod(paramSLabel, paramAVlTerm, paramArrayOfACsTerm, paramArrayOfAVlTerm) : localOption;
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.NestedScope
 * JD-Core Version:    0.6.2
 */