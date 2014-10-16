/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Map;
/*      */ import pkg.hofgj.stree.SLabel;
/*      */ import pkg.hofgj.stree.SSlotBound;
/*      */ import pkg.hofgj.stree.STpParam;
/*      */ import pkg.hofgj.stree.STpParamList;
/*      */ import pkg.util.Option;
/*      */ import pkg.util.P2;
/*      */ 
/*      */ class TpParamScope extends NestedScope<Scope>
/*      */ {
/*      */   private final Map<String, ATpParam> table;
/*      */   private final ATpParam[] params;
/*      */ 
/*      */   public TpParamScope(Scope paramScope, Option<STpParamList> paramOption)
/*      */   {
/* 2970 */     super(paramScope);
/* 2971 */     this.table = new LinkedHashMap();
/* 2972 */     this.params = (paramOption.isEmpty() ? new ATpParam[0] : enter(((STpParamList)paramOption.get()).params));
/*      */   }
/*      */ 
/*      */   public final ATpParam[] getParams()
/*      */   {
/* 2981 */     return this.params;
/*      */   }
/*      */ 
/*      */   private ATpParam[] enter(STpParam[] paramArrayOfSTpParam)
/*      */   {
/* 2988 */     ATpParam[] arrayOfATpParam = new ATpParam[paramArrayOfSTpParam.length];
/* 2989 */     for (int i = 0; i < arrayOfATpParam.length; i++) arrayOfATpParam[i] = enter(paramArrayOfSTpParam[i]);
/* 2990 */     return arrayOfATpParam;
/*      */   }
/*      */ 
/*      */   private ATpParam enter(STpParam paramSTpParam) {
/* 2994 */     String str = paramSTpParam.name.label;
/* 2995 */     TpParamScope localTpParamScope = new TpParamScope(this, paramSTpParam.params);
/* 2996 */     ATpParam localATpParam = new ATpParam(this.context.getSource(), paramSTpParam.position(), str);
/*      */ 
/* 2998 */     localATpParam.setParams(localTpParamScope.getParams());
/* 2999 */     if (paramSTpParam.bound.isEmpty())
/* 3000 */       localATpParam.setBound(this.compiler.getObjectAsTp());
/*      */     else {
/* 3002 */       localATpParam.setBound(this.context.toSlotBound(localATpParam, localTpParamScope, (SSlotBound)paramSTpParam.bound.get()));
/*      */     }
/*      */ 
/* 3005 */     localATpParam.freeze();
/* 3006 */     if (this.table.containsKey(str)) {
/* 3007 */       error(paramSTpParam, "a type parameter named '" + str + "' is already defined");
/*      */     }
/*      */     else {
/* 3010 */       this.table.put(str, localATpParam);
/*      */     }
/* 3012 */     return localATpParam;
/*      */   }
/*      */ 
/*      */   public Option<FTpTerm> lookupTypeLocal(String paramString)
/*      */   {
/* 3019 */     ATpParam localATpParam = (ATpParam)this.table.get(paramString);
/* 3020 */     return localATpParam == null ? Option.None() : Option.Some(new FTpTerm.Abstract(localATpParam));
/*      */   }
/*      */ 
/*      */   public Option<VlTp> lookupFieldLocal(SLabel paramSLabel)
/*      */   {
/* 3026 */     return Option.None();
/*      */   }
/*      */ 
/*      */   public Option<P2<AVlTerm, P2<ATpParam[], ATpTerm[]>>> lookupMethodLocal(SLabel paramSLabel, AVlTerm paramAVlTerm, ACsTerm[] paramArrayOfACsTerm, AVlTerm[] paramArrayOfAVlTerm)
/*      */   {
/* 3032 */     return Option.None();
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.TpParamScope
 * JD-Core Version:    0.6.2
 */