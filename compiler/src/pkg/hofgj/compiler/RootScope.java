/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ import java.util.Map;
/*      */ import pkg.hofgj.stree.SClass;
/*      */ import pkg.hofgj.stree.SConstructor;
/*      */ import pkg.hofgj.stree.SField;
/*      */ import pkg.hofgj.stree.SLabel;
/*      */ import pkg.hofgj.stree.SMember;
/*      */ import pkg.hofgj.stree.SMember.Class;
/*      */ import pkg.hofgj.stree.SMember.Constructor;
/*      */ import pkg.hofgj.stree.SMember.Field;
/*      */ import pkg.hofgj.stree.SMember.Method;
/*      */ import pkg.hofgj.stree.SMethod;
/*      */ import pkg.util.Option;
/*      */ import pkg.util.P2;
/*      */ 
/*      */ class RootScope extends Scope
/*      */ {
/*      */   private final Map<String, ClassScope> classes1;
/*      */   private final Map<AClass, ClassScope> classes2;
/*      */ 
/*      */   public RootScope(Context paramContext, Map<String, ClassScope> paramMap, Map<AClass, ClassScope> paramMap1)
/*      */   {
/* 2460 */     super(paramContext);
/* 2461 */     this.classes1 = paramMap;
/* 2462 */     this.classes2 = paramMap1;
/*      */   }
/*      */ 
/*      */   public void enter(SMember paramSMember)
/*      */   {
/* 2469 */     switch (1.$SwitchMap$pkg$hofgj$stree$SMember$Kind[paramSMember.getKind().ordinal()]) {
/*      */     case 1:
/* 2471 */       enter(((SMember.Class)paramSMember).clasz);
/* 2472 */       return;
/*      */     case 2:
/* 2474 */       enter(((SMember.Field)paramSMember).field);
/* 2475 */       return;
/*      */     case 3:
/* 2477 */       enter(((SMember.Method)paramSMember).method);
/* 2478 */       return;
/*      */     case 4:
/* 2480 */       enter(((SMember.Constructor)paramSMember).constructor);
/* 2481 */       return;
/*      */     }
/* 2483 */     throw new Error("unknown kind " + paramSMember.getKind());
/*      */   }
/*      */ 
/*      */   public void enter(SClass paramSClass)
/*      */   {
/* 2488 */     String str = paramSClass.name.label;
/* 2489 */     ClassScope localClassScope = new ClassScope(this, paramSClass);
/* 2490 */     if (paramSClass.isInterface) {
/* 2491 */       error(paramSClass, "interfaces are not supported");
/* 2492 */     } else if (this.classes1.containsKey(str)) {
/* 2493 */       error(paramSClass, "a class named '" + str + "' is already defined");
/*      */     } else {
/* 2495 */       this.classes1.put(str, localClassScope);
/* 2496 */       this.classes2.put(localClassScope.getClasz(), localClassScope);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void enter(SField paramSField) {
/* 2501 */     error(paramSField, "top-level variables are not supported");
/*      */   }
/*      */ 
/*      */   public void enter(SMethod paramSMethod) {
/* 2505 */     error(paramSMethod, "top-level functions are not supported");
/*      */   }
/*      */ 
/*      */   public void enter(SConstructor paramSConstructor) {
/* 2509 */     error(paramSConstructor, "constructors may only appear within a class");
/*      */   }
/*      */ 
/*      */   public Option<AClass> getCurrentClass()
/*      */   {
/* 2516 */     return Option.None();
/*      */   }
/*      */ 
/*      */   public Option<FTpTerm> lookupType(String paramString) {
/* 2520 */     Option localOption = this.compiler.lookupClass(paramString);
/* 2521 */     return localOption.isEmpty() ? Option.None() : Option.Some(new FTpTerm.Concrete(((ClassScope)localOption.get()).getClasz()));
/*      */   }
/*      */ 
/*      */   public VlTp lookupField(SLabel paramSLabel)
/*      */   {
/* 2528 */     if (paramSLabel.label.equals("false"))
/* 2529 */       return new VlTp(new AVlTerm.Boolean(false), new ATpTerm.Boolean());
/* 2530 */     if (paramSLabel.label.equals("true"))
/* 2531 */       return new VlTp(new AVlTerm.Boolean(true), new ATpTerm.Boolean());
/* 2532 */     error(paramSLabel, "cannot resolve variable '" + paramSLabel.label + "'");
/* 2533 */     return new VlTp(new AVlTerm.Error(), new ATpTerm.Error());
/*      */   }
/*      */ 
/*      */   public Option<P2<AVlTerm, P2<ATpParam[], ATpTerm[]>>> lookupMethod(SLabel paramSLabel, AVlTerm paramAVlTerm, ACsTerm[] paramArrayOfACsTerm, AVlTerm[] paramArrayOfAVlTerm)
/*      */   {
/* 2539 */     return Option.None();
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.RootScope
 * JD-Core Version:    0.6.2
 */