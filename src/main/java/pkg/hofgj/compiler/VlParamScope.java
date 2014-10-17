/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Map;
/*      */ import pkg.hofgj.stree.SLabel;
/*      */ import pkg.hofgj.stree.SSlotType;
/*      */ import pkg.hofgj.stree.SVlParam;
/*      */ import pkg.hofgj.stree.SVlParamList;
/*      */ import pkg.util.Option;
/*      */ import pkg.util.P2;
/*      */ 
/*      */ class VlParamScope extends NestedScope<Scope>
/*      */ {
/*      */   private final Map<String, AVlParam> table;
/*      */   private final AVlParam[] params;
/*      */ 
/*      */   public VlParamScope(Scope paramScope, Option<SVlParamList> paramOption)
/*      */   {
/* 3051 */     super(paramScope);
/* 3052 */     this.table = new LinkedHashMap();
/* 3053 */     this.params = (paramOption.isEmpty() ? new AVlParam[0] : enter(((SVlParamList)paramOption.get()).params));
/*      */   }
/*      */ 
/*      */   public final AVlParam[] getParams()
/*      */   {
/* 3062 */     return this.params;
/*      */   }
/*      */ 
/*      */   private AVlParam[] enter(SVlParam[] paramArrayOfSVlParam)
/*      */   {
/* 3069 */     AVlParam[] arrayOfAVlParam = new AVlParam[paramArrayOfSVlParam.length];
/* 3070 */     for (int i = 0; i < arrayOfAVlParam.length; i++) arrayOfAVlParam[i] = enter(paramArrayOfSVlParam[i]);
/* 3071 */     return arrayOfAVlParam;
/*      */   }
/*      */ 
/*      */   private AVlParam enter(SVlParam paramSVlParam) {
/* 3075 */     String str = paramSVlParam.name.label;
/* 3076 */     AVlParam localAVlParam = new AVlParam(this.context.getSource(), paramSVlParam.position(), str);
/*      */ 
/* 3078 */     if (paramSVlParam.type.isEmpty()) {
/* 3079 */       error(paramSVlParam, "type is missing in definition of value parameter '" + str + "'");
/*      */ 
/* 3081 */       localAVlParam.setType(new ATpTerm.Error());
/*      */     } else {
/* 3083 */       localAVlParam.setType(this.context.toSlotType(localAVlParam, getOuterScope(), (SSlotType)paramSVlParam.type.get()));
/*      */     }
/*      */ 
/* 3086 */     if (this.table.containsKey(str)) {
/* 3087 */       error(paramSVlParam, "a value parameter named '" + str + "' is already defined");
/*      */     }
/*      */     else {
/* 3090 */       this.table.put(str, localAVlParam);
/*      */     }
/* 3092 */     return localAVlParam;
/*      */   }
/*      */ 
/*      */   public Option<FTpTerm> lookupTypeLocal(String paramString)
/*      */   {
/* 3099 */     return Option.None();
/*      */   }
/*      */ 
/*      */   public Option<VlTp> lookupFieldLocal(SLabel paramSLabel) {
/* 3103 */     AVlParam localAVlParam = (AVlParam)this.table.get(paramSLabel.label);
/* 3104 */     if (localAVlParam == null) return Option.None();
/* 3105 */     return Option.Some(new VlTp(new AVlTerm.Local(localAVlParam), localAVlParam.getType()));
/*      */   }
/*      */ 
/*      */   public Option<P2<AVlTerm, P2<ATpParam[], ATpTerm[]>>> lookupMethodLocal(SLabel paramSLabel, AVlTerm paramAVlTerm, ACsTerm[] paramArrayOfACsTerm, AVlTerm[] paramArrayOfAVlTerm)
/*      */   {
/* 3112 */     return Option.None();
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.VlParamScope
 * JD-Core Version:    0.6.2
 */