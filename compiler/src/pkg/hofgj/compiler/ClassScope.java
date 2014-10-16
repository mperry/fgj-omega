/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ import java.util.Collection;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
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
/*      */ import pkg.hofgj.stree.SSlotType;
/*      */ import pkg.hofgj.stree.STpParamList;
/*      */ import pkg.hofgj.stree.STree;
/*      */ import pkg.util.Option;
/*      */ import pkg.util.P2;
/*      */ 
/*      */ class ClassScope extends NestedScope<TpParamScope>
/*      */ {
/*      */   private final AClass clasz;
/*      */   private final Map<String, AField> fields;
/*      */   private final Map<String, MethodScope> methods;
/*      */   private Option<ClassScope> superclass;
/*      */   private boolean frozen;
/*      */ 
/*      */   public ClassScope(Scope paramScope, AClass paramAClass, Option<STpParamList> paramOption)
/*      */   {
/* 2618 */     super(new TpParamScope(paramScope, paramOption));
/* 2619 */     this.clasz = paramAClass;
/* 2620 */     this.fields = new LinkedHashMap();
/* 2621 */     this.methods = new LinkedHashMap();
/*      */   }
/*      */ 
/*      */   public ClassScope(Scope paramScope, AClass paramAClass) {
/* 2625 */     this(paramScope, paramAClass, Option.None());
/*      */   }
/*      */ 
/*      */   public ClassScope(Scope paramScope, SClass paramSClass) {
/* 2629 */     this(paramScope, new AClass(paramScope.context.getSource(), paramSClass.position(), paramSClass.name.label), paramSClass.params);
/*      */ 
/* 2633 */     this.clasz.setParams(((TpParamScope)getOuterScope()).getParams());
/* 2634 */     this.clasz.setBase(this.context.toClassBase(this, Option.from(paramSClass.extensions)));
/* 2635 */     this.clasz.setBody(this.context.toClassBody(this, paramSClass.body));
/* 2636 */     if (paramSClass.extensions.length > 1) {
/* 2637 */       error(paramSClass.extensions[1], "a class may only extend one other class");
/*      */     }
/*      */ 
/* 2640 */     if (paramSClass.implementations.length > 0)
/* 2641 */       error(paramSClass.implementations[0], "interface implementation is not supported");
/*      */   }
/*      */ 
/*      */   public AClass getClasz()
/*      */   {
/* 2650 */     return this.clasz;
/*      */   }
/*      */ 
/*      */   public Option<ClassScope> getSuperclass() {
/* 2654 */     getClasz().getBase();
/* 2655 */     assert (this.superclass != null) : this;
/* 2656 */     return this.superclass;
/*      */   }
/*      */ 
/*      */   public AField[] getFields(int paramInt) {
/* 2660 */     getClasz().getBody();
/* 2661 */     AField[] arrayOfAField1 = (AField[])this.fields.values().toArray(new AField[this.fields.size()]);
/* 2662 */     paramInt += arrayOfAField1.length;
/* 2663 */     AField[] arrayOfAField2 = getSuperclass().isEmpty() ? new AField[paramInt] : ((ClassScope)getSuperclass().get()).getFields(paramInt);
/*      */ 
/* 2666 */     for (int i = 0; i < arrayOfAField1.length; i++)
/* 2667 */       arrayOfAField2[(arrayOfAField2.length - paramInt + i)] = arrayOfAField1[i];
/* 2668 */     return arrayOfAField2;
/*      */   }
/*      */ 
/*      */   public Map<String, MethodScope> getMethods() {
/* 2672 */     LinkedHashMap localLinkedHashMap = new LinkedHashMap();
/* 2673 */     ClassScope localClassScope = this;
/*      */     while (true) { localClassScope.getClasz().getBody();
/* 2675 */       for (Map.Entry localEntry : localClassScope.methods.entrySet())
/* 2676 */         if (!localLinkedHashMap.containsKey(localEntry.getKey()))
/* 2677 */           localLinkedHashMap.put(localEntry.getKey(), localEntry.getValue());
/* 2678 */       if (localClassScope.getSuperclass().isEmpty()) return localLinkedHashMap;
/* 2679 */       localClassScope = (ClassScope)localClassScope.getSuperclass().get(); }
/*      */   }
/*      */ 
/*      */   public Option<P2<ClassScope, AField>> lookupField(String paramString)
/*      */   {
/* 2684 */     getClasz().getBody();
/* 2685 */     assert (this.frozen) : this;
/* 2686 */     AField localAField = (AField)this.fields.get(paramString);
/* 2687 */     if (localAField != null) return Option.Some(P2.mk(this, localAField));
/* 2688 */     return getSuperclass().isEmpty() ? Option.None() : ((ClassScope)getSuperclass().get()).lookupField(paramString);
/*      */   }
/*      */ 
/*      */   public Option<P2<ClassScope, MethodScope>> lookupMethod(String paramString)
/*      */   {
/* 2694 */     getClasz().getBody();
/* 2695 */     assert (this.frozen) : this;
/* 2696 */     MethodScope localMethodScope = (MethodScope)this.methods.get(paramString);
/* 2697 */     if (localMethodScope != null) return Option.Some(P2.mk(this, localMethodScope));
/* 2698 */     return getSuperclass().isEmpty() ? Option.None() : ((ClassScope)getSuperclass().get()).lookupMethod(paramString);
/*      */   }
/*      */ 
/*      */   public void setSuperclass(Option<ClassScope> paramOption)
/*      */   {
/* 2707 */     assert ((this.superclass == null) && (paramOption != null)) : this;
/* 2708 */     this.superclass = paramOption;
/*      */   }
/*      */ 
/*      */   public AClassBody freeze() {
/* 2712 */     assert (!this.frozen) : this;
/* 2713 */     this.frozen = true;
/* 2714 */     AField[] arrayOfAField = (AField[])this.fields.values().toArray(new AField[this.fields.size()]);
/*      */ 
/* 2717 */     MethodScope[] arrayOfMethodScope = (MethodScope[])this.methods.values().toArray(new MethodScope[this.methods.size()]);
/*      */ 
/* 2720 */     AMethod[] arrayOfAMethod = new AMethod[arrayOfMethodScope.length];
/* 2721 */     for (int i = 0; i < arrayOfAMethod.length; i++)
/* 2722 */       arrayOfAMethod[i] = arrayOfMethodScope[i].getMethod();
/* 2723 */     return new AClassBody(arrayOfAField, arrayOfAMethod);
/*      */   }
/*      */ 
/*      */   public void enter(SMember paramSMember) {
/* 2727 */     assert (!this.frozen) : this;
/* 2728 */     switch (1.$SwitchMap$pkg$hofgj$stree$SMember$Kind[paramSMember.getKind().ordinal()]) {
/*      */     case 1:
/* 2730 */       enter(((SMember.Class)paramSMember).clasz);
/* 2731 */       return;
/*      */     case 2:
/* 2733 */       enter(((SMember.Field)paramSMember).field);
/* 2734 */       return;
/*      */     case 3:
/* 2736 */       enter(((SMember.Method)paramSMember).method);
/* 2737 */       return;
/*      */     case 4:
/* 2739 */       enter(((SMember.Constructor)paramSMember).constructor);
/* 2740 */       return;
/*      */     }
/* 2742 */     throw new Error("unknown kind " + paramSMember.getKind());
/*      */   }
/*      */ 
/*      */   public void enter(SClass paramSClass)
/*      */   {
/* 2747 */     assert (!this.frozen) : this;
/* 2748 */     error(paramSClass, "inner classes are not supported");
/*      */   }
/*      */ 
/*      */   public void enter(SField paramSField) {
/* 2752 */     assert (!this.frozen) : this;
/* 2753 */     String str = paramSField.name.label;
/*      */ 
/* 2755 */     if (this.fields.containsKey(str)) {
/* 2756 */       error(paramSField, "a field named '" + str + "' is already defined in class '" + getClasz().getName() + "'");
/*      */     }
/*      */     else
/*      */     {
/* 2760 */       if (!getSuperclass().isEmpty()) {
/* 2761 */         localObject = ((ClassScope)getSuperclass().get()).lookupField(str);
/*      */ 
/* 2763 */         if (!((Option)localObject).isEmpty()) {
/* 2764 */           error(paramSField, "a field named '" + str + "' is already inherited from class '" + ((ClassScope)((P2)((Option)localObject).get()).vl0).getClasz().getName() + "'");
/*      */ 
/* 2767 */           break label293;
/*      */         }
/*      */       }
/* 2770 */       Object localObject = new AField(this.context.getSource(), paramSField.position(), str, getClasz());
/*      */ 
/* 2772 */       if (paramSField.type.isEmpty()) {
/* 2773 */         error(paramSField, "field type is missing in definition of field '" + str + "'");
/*      */ 
/* 2775 */         ((AField)localObject).setType(this.compiler.getObjectAsTp());
/*      */       } else {
/* 2777 */         ((AField)localObject).setType(this.context.toSlotType((ASymbol)localObject, this, (SSlotType)paramSField.type.get()));
/*      */       }
/*      */ 
/* 2780 */       this.fields.put(str, localObject);
/*      */     }
/* 2782 */     label293: if (!paramSField.body.isEmpty()) error((STree)paramSField.body.get(), "default field initializations are not supported");
/*      */   }
/*      */ 
/*      */   public void enter(SMethod paramSMethod)
/*      */   {
/* 2787 */     assert (!this.frozen) : this;
/* 2788 */     String str = paramSMethod.name.label;
/* 2789 */     if (this.methods.containsKey(str)) {
/* 2790 */       error(paramSMethod, "a method named '" + str + "' is already defined in class '" + getClasz().getName() + "'");
/*      */     }
/*      */     else
/*      */     {
/* 2794 */       AMethod localAMethod = new AMethod(this.context.getSource(), paramSMethod.position(), str, getClasz());
/*      */ 
/* 2796 */       MethodScope localMethodScope = new MethodScope(this, localAMethod, paramSMethod);
/* 2797 */       this.methods.put(str, localMethodScope);
/* 2798 */       if (!getSuperclass().isEmpty()) {
/* 2799 */         Option localOption = ((ClassScope)getSuperclass().get()).lookupMethod(str);
/*      */ 
/* 2801 */         if (!localOption.isEmpty())
/* 2802 */           this.context.checkOverride(paramSMethod.position(), localAMethod, ((MethodScope)((P2)localOption.get()).vl1).getMethod());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void enter(SConstructor paramSConstructor)
/*      */   {
/* 2811 */     assert (!this.frozen) : this;
/*      */   }
/*      */ 
/*      */   public Option<FTpTerm> lookupTypeLocal(String paramString)
/*      */   {
/* 2820 */     return Option.None();
/*      */   }
/*      */ 
/*      */   public Option<VlTp> lookupFieldLocal(SLabel paramSLabel) {
/* 2824 */     return lookupFieldLocal(paramSLabel, new AVlTerm.This(), this.compiler.getTp(getClasz()));
/*      */   }
/*      */ 
/*      */   public Option<VlTp> lookupFieldLocal(SLabel paramSLabel, AVlTerm paramAVlTerm, ATpTerm paramATpTerm)
/*      */   {
/* 2831 */     ClassScope localClassScope = this;
/*      */     while (true) { AField localAField = (AField)localClassScope.fields.get(paramSLabel.label);
/* 2833 */       if (localAField != null) {
/* 2834 */         return Option.Some(new VlTp(new AVlTerm.Get(paramAVlTerm, localAField), this.compiler.asSeenFrom(localClassScope.getClasz(), localAField.getType(), paramATpTerm)));
/*      */       }
/*      */ 
/* 2840 */       Option localOption = localClassScope.getSuperclass();
/* 2841 */       if (localOption.isEmpty()) break;
/* 2842 */       localClassScope = (ClassScope)localOption.get();
/*      */     }
/* 2844 */     return Option.None();
/*      */   }
/*      */ 
/*      */   public Option<P2<AVlTerm, P2<ATpParam[], ATpTerm[]>>> lookupMethodLocal(SLabel paramSLabel, AVlTerm paramAVlTerm, ACsTerm[] paramArrayOfACsTerm, AVlTerm[] paramArrayOfAVlTerm)
/*      */   {
/* 2850 */     return lookupMethodLocal(paramSLabel, paramAVlTerm, paramArrayOfACsTerm, paramArrayOfAVlTerm, this.compiler.getTp(getClasz()));
/*      */   }
/*      */ 
/*      */   public Option<P2<AVlTerm, P2<ATpParam[], ATpTerm[]>>> lookupMethodLocal(SLabel paramSLabel, AVlTerm paramAVlTerm, ACsTerm[] paramArrayOfACsTerm, AVlTerm[] paramArrayOfAVlTerm, ATpTerm paramATpTerm)
/*      */   {
/* 2858 */     ClassScope localClassScope = this;
/*      */     while (true) { MethodScope localMethodScope = (MethodScope)localClassScope.methods.get(paramSLabel.label);
/* 2860 */       if (localMethodScope != null) {
/* 2861 */         localObject = new AVlTerm.Call(paramAVlTerm, localMethodScope.getMethod(), paramArrayOfACsTerm, paramArrayOfAVlTerm);
/*      */ 
/* 2863 */         P2 localP2 = Cloner.cloneMethod(localClassScope.getClasz().getParams(), this.compiler.getClassArgsFrom(localClassScope.getClasz(), paramATpTerm), localMethodScope.getMethod());
/*      */ 
/* 2867 */         Freezer.instance.rewrite((ATpParam[])localP2.vl0);
/* 2868 */         Freezer.instance.rewrite((ATpTerm[])localP2.vl1);
/* 2869 */         return Option.Some(P2.mk(localObject, localP2));
/*      */       }
/* 2871 */       Object localObject = localClassScope.getSuperclass();
/* 2872 */       if (((Option)localObject).isEmpty()) break;
/* 2873 */       localClassScope = (ClassScope)((Option)localObject).get();
/*      */     }
/* 2875 */     return Option.None();
/*      */   }
/*      */ 
/*      */   public final Option<AClass> getCurrentClass() {
/* 2879 */     return Option.Some(this.clasz);
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/* 2886 */     return "scope of class '" + getClasz().getName() + "'";
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.ClassScope
 * JD-Core Version:    0.6.2
 */