/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ import pkg.hofgj.stree.SLabel;
/*      */ import pkg.hofgj.stree.SMethod;
/*      */ import pkg.hofgj.stree.SSlotBody;
/*      */ import pkg.hofgj.stree.SSlotType;
/*      */ import pkg.util.Option;
/*      */ import pkg.util.P2;
/*      */ 
/*      */ class MethodScope extends NestedScope<ClassScope>
/*      */ {
/*      */   private final AMethod method;
/*      */   private final TpParamScope tparams;
/*      */   private final VlParamScope vparams;
/*      */ 
/*      */   public MethodScope(ClassScope paramClassScope, AMethod paramAMethod, SMethod paramSMethod)
/*      */   {
/* 2906 */     super(paramClassScope);
/* 2907 */     this.method = paramAMethod;
/* 2908 */     this.tparams = new TpParamScope(this, paramSMethod.tparams);
/* 2909 */     paramAMethod.setTpParams(this.tparams.getParams());
/* 2910 */     this.vparams = new VlParamScope(this.tparams, paramSMethod.vparams);
/* 2911 */     paramAMethod.setVlParams(this.vparams.getParams());
/* 2912 */     if (paramSMethod.type.isEmpty()) {
/* 2913 */       error(paramSMethod, "return type is missing in definition of method '" + paramAMethod.getName() + "'");
/*      */ 
/* 2915 */       paramAMethod.setType(new ATpTerm.Error());
/*      */     } else {
/* 2917 */       paramAMethod.setType(this.context.toSlotType(paramAMethod, this.vparams, (SSlotType)paramSMethod.type.get()));
/*      */     }
/*      */ 
/* 2920 */     if (paramSMethod.body.isEmpty())
/* 2921 */       paramAMethod.setBody(Option.None());
/*      */     else
/* 2923 */       paramAMethod.setBody(Option.Some(this.context.toSlotBody(paramAMethod, this.vparams, (SSlotBody)paramSMethod.body.get(), paramAMethod.getType())));
/*      */   }
/*      */ 
/*      */   public AMethod getMethod()
/*      */   {
/* 2934 */     return this.method;
/*      */   }
/*      */ 
/*      */   public Option<FTpTerm> lookupTypeLocal(String paramString)
/*      */   {
/* 2941 */     return Option.None();
/*      */   }
/*      */ 
/*      */   public Option<VlTp> lookupFieldLocal(SLabel paramSLabel) {
/* 2945 */     return Option.None();
/*      */   }
/*      */ 
/*      */   public Option<P2<AVlTerm, P2<ATpParam[], ATpTerm[]>>> lookupMethodLocal(SLabel paramSLabel, AVlTerm paramAVlTerm, ACsTerm[] paramArrayOfACsTerm, AVlTerm[] paramArrayOfAVlTerm)
/*      */   {
/* 2951 */     return Option.None();
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.MethodScope
 * JD-Core Version:    0.6.2
 */