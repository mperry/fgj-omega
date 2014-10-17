/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import pkg.hofgj.Source;
/*      */ import pkg.hofgj.Util;
/*      */ import pkg.hofgj.stree.SClassBase;
/*      */ import pkg.hofgj.stree.SClassBody;
/*      */ import pkg.hofgj.stree.SLabel;
/*      */ import pkg.hofgj.stree.SMember;
/*      */ import pkg.hofgj.stree.SProgram;
/*      */ import pkg.hofgj.stree.SSlotBody;
/*      */ import pkg.hofgj.stree.SSlotBound;
/*      */ import pkg.hofgj.stree.SSlotType;
/*      */ import pkg.hofgj.stree.STpArgList;
/*      */ import pkg.hofgj.stree.STpTerm;
/*      */ import pkg.hofgj.stree.STpTerm.Abs;
/*      */ import pkg.hofgj.stree.STpTerm.App;
/*      */ import pkg.hofgj.stree.STpTerm.Kind;
/*      */ import pkg.hofgj.stree.STpTerm.Ref;
/*      */ import pkg.hofgj.stree.STree;
/*      */ import pkg.hofgj.stree.SVlArgList;
/*      */ import pkg.hofgj.stree.SVlRef;
/*      */ import pkg.hofgj.stree.SVlSelector;
/*      */ import pkg.hofgj.stree.SVlTerm;
/*      */ import pkg.hofgj.stree.SVlTerm.Binary;
/*      */ import pkg.hofgj.stree.SVlTerm.BinaryOperator;
/*      */ import pkg.hofgj.stree.SVlTerm.Block;
/*      */ import pkg.hofgj.stree.SVlTerm.Boolean;
/*      */ import pkg.hofgj.stree.SVlTerm.If;
/*      */ import pkg.hofgj.stree.SVlTerm.Integer;
/*      */ //import pkg.hofgj.stree.SVlTerm.Kind;
/*      */ import pkg.hofgj.stree.SVlTerm.New;
/*      */ //import pkg.hofgj.stree.SVlTerm.Ref;
/*      */ import pkg.hofgj.stree.SVlTerm.Sel;
/*      */ import pkg.hofgj.stree.SVlTerm.This;
/*      */ import pkg.hofgj.stree.SVlTerm.Unary;
/*      */ import pkg.hofgj.stree.SVlTerm.UnaryOperator;
/*      */ import pkg.util.Option;
/*      */ import pkg.util.P2;
/*      */ import pkg.util.Promise;
/*      */ import pkg.util.Task;
/*      */ import pkg.util.TaskManager;
/*      */ import pkg.util.Thunk;
/*      */ 
/*      */ class Context
/*      */ {
/*      */   public final Compiler compiler;
/*      */   private final TaskManager manager;
/*      */   private final Source source;
/*      */   private Set<AClass> childs;
/*      */   private boolean erroneous;
/*      */ 
/*      */   public Context(Compiler paramCompiler, TaskManager paramTaskManager, Source paramSource)
/*      */   {
/*  599 */     this.compiler = paramCompiler;
/*  600 */     this.manager = paramTaskManager;
/*  601 */     this.source = paramSource;
/*  602 */     this.childs = new LinkedHashSet();
/*      */   }
/*      */ 
/*      */   public void enterTasks()
/*      */   {
/*  609 */     RootScope localRootScope = this.compiler.getRootScope(this);
/*  610 */     Option localOption = this.source.parseProgram();
/*  611 */     if (!localOption.isEmpty()) {
/*  612 */       for (SMember localSMember : ((SProgram)localOption.get()).members) localRootScope.enter(localSMember);
/*      */     }
/*  614 */     if ((!localOption.isEmpty()) && (!((SProgram)localOption.get()).main.isEmpty()))
/*  615 */       toMain(localRootScope, (SVlTerm)((SProgram)localOption.get()).main.get());
/*      */     else
/*  617 */       this.source.setMain(Option.None());
/*      */   }
/*      */ 
/*      */   public Source getSource()
/*      */   {
/*  625 */     return this.source;
/*      */   }
/*      */ 
/*      */   public AClassBase compileClassBase(ClassScope paramClassScope, SClassBase paramSClassBase)
/*      */   {
/*  632 */     Option localOption = compileClassRef(paramSClassBase.clasz);
/*  633 */     if (localOption.isEmpty()) {
/*  634 */       paramClassScope.setSuperclass(Option.Some(this.compiler.getObjectScope()));
/*  635 */       return this.compiler.getObjectAsBase();
/*      */     }
/*  637 */     ClassScope localClassScope = (ClassScope)localOption.get();
/*  638 */     if ((localClassScope.getClasz() == paramClassScope.getClasz()) || (this.childs.contains(localClassScope.getClasz())))
/*      */     {
/*  641 */       error(paramClassScope.getClasz(), "class '" + paramClassScope.getClasz().getName() + "' is cyclic");
/*      */ 
/*  643 */       paramClassScope.setSuperclass(Option.Some(this.compiler.getObjectScope()));
/*  644 */       return this.compiler.getObjectAsBase();
/*      */     }
/*  646 */     this.childs.add(paramClassScope.getClasz());
/*  647 */     localClassScope.getClasz().getBase();
/*  648 */     this.childs.remove(paramClassScope.getClasz());
/*  649 */     paramClassScope.setSuperclass(Option.Some(localClassScope));
/*  650 */     Promise localPromise = toClassSuperArgs(paramClassScope, paramSClassBase);
/*  651 */     return new AClassBase(localClassScope.getClasz(), localPromise);
/*      */   }
/*      */ 
/*      */   public ACsTerm[] compileClassSuperArgs(ClassScope paramClassScope, SClassBase paramSClassBase)
/*      */   {
/*  657 */     STpTerm[] arrayOfSTpTerm = paramSClassBase.args.isEmpty() ? new STpTerm[0] : ((STpArgList)paramSClassBase.args.get()).args;
/*      */ 
/*  659 */     ACsTerm[] arrayOfACsTerm = new ACsTerm[arrayOfSTpTerm.length];
/*  660 */     for (int i = 0; i < arrayOfACsTerm.length; i++)
/*  661 */       arrayOfACsTerm[i] = compileTpTermToCs(paramClassScope.getOuterScope(), arrayOfSTpTerm[i]);
/*  662 */     arrayOfACsTerm = checkConformance(paramSClassBase.clasz.position(), ((ClassScope)paramClassScope.getSuperclass().get()).getClasz().getParams(), arrayOfACsTerm);
/*      */ 
/*  664 */     return arrayOfACsTerm;
/*      */   }
/*      */ 
/*      */   public AClassBody compileClassBody(ClassScope paramClassScope, SClassBody paramSClassBody) {
/*  668 */     for (SMember localSMember : paramSClassBody.members) paramClassScope.enter(localSMember);
/*  669 */     return paramClassScope.freeze();
/*      */   }
/*      */ 
/*      */   public ATpTerm compileSlotBound(Scope paramScope, SSlotBound paramSSlotBound)
/*      */   {
/*  676 */     ATpTerm localATpTerm = compileTpTermToTp(paramScope, paramSSlotBound.bound);
/*  677 */     //switch (9.$SwitchMap$pkg$hofgj$compiler$ATpTerm$Kind[((ATpTerm.Kind)localATpTerm.getKind()).ordinal()]) {
               switch (((ATpTerm.Kind)localATpTerm.getKind()).ordinal()) {
/*      */     case 1:
/*  679 */       return localATpTerm;
/*      */     case 2:
/*  681 */       return localATpTerm;
/*      */     case 3:
/*  683 */       throw new Error();
/*      */     }
/*  685 */     error(paramSSlotBound, "the upper bound must be a class type");
/*  686 */     return new ATpTerm.Error();
/*      */   }
/*      */ 
/*      */   public ATpTerm compileSlotType(Scope paramScope, SSlotType paramSSlotType)
/*      */   {
/*  691 */     return compileTpTermToTp(paramScope, paramSSlotType.type);
/*      */   }
/*      */ 
/*      */   public AVlTerm compileSlotBody(Scope paramScope, SSlotBody paramSSlotBody, ATpTerm paramATpTerm)
/*      */   {
/*  697 */     VlTp localVlTp = compileVlTerm(paramScope, paramSSlotBody.body);
/*  698 */     localVlTp = checkConformance(paramSSlotBody.position(), paramATpTerm, localVlTp);
/*  699 */     return localVlTp.vl;
/*      */   }
/*      */ 
/*      */   public ACsTerm[] compileTpTermsToCs(Scope paramScope, Option<STpArgList> paramOption)
/*      */   {
/*  706 */     if (paramOption.isEmpty()) return new ACsTerm[0];
/*  707 */     return compileTpTermsToCs(paramScope, ((STpArgList)paramOption.get()).args);
/*      */   }
/*      */ 
/*      */   public ACsTerm[] compileTpTermsToCs(Scope paramScope, STpTerm[] paramArrayOfSTpTerm) {
/*  711 */     ACsTerm[] arrayOfACsTerm = new ACsTerm[paramArrayOfSTpTerm.length];
/*  712 */     for (int i = 0; i < arrayOfACsTerm.length; i++)
/*  713 */       arrayOfACsTerm[i] = compileTpTermToCs(paramScope, paramArrayOfSTpTerm[i]);
/*  714 */     return arrayOfACsTerm;
/*      */   }
/*      */ 
/*      */   public ACsTerm compileTpTermToCs(Scope paramScope, STpTerm paramSTpTerm) {
/*  718 */     FTpTerm localFTpTerm = compileTpTerm(paramScope, paramSTpTerm);
/*      */     Object localObject;
/*  719 */     //switch (9.$SwitchMap$pkg$hofgj$compiler$FTpTerm$Kind[localFTpTerm.getKind().ordinal()]) {
               switch (localFTpTerm.getKind().ordinal()) {
/*      */     case 1:
/*  721 */       localObject = (FTpTerm.Concrete)localFTpTerm;
/*  722 */       return Freezer.instance.rewrite(Cloner.cloneToCs(((FTpTerm.Concrete)localObject).clasz));
/*      */     case 2:
/*  725 */       localObject = (FTpTerm.Abstract)localFTpTerm;
/*  726 */       return Freezer.instance.rewrite(Cloner.cloneToCs(((FTpTerm.Abstract)localObject).param));
/*      */     case 3:
/*  729 */       localObject = (FTpTerm.Cs)localFTpTerm;
/*  730 */       return ((FTpTerm.Cs)localObject).term;
/*      */     case 4:
/*  733 */       localObject = (FTpTerm.Tp)localFTpTerm;
/*  734 */       return new ACsTerm.Function(new ATpParam[0], ((FTpTerm.Tp)localObject).term);
/*      */     }
/*      */ 
/*  737 */     throw new Error("" + localFTpTerm.getKind());
/*      */   }
/*      */ 
/*      */   public ATpTerm compileTpTermToTp(Scope paramScope, STpTerm paramSTpTerm)
/*      */   {
/*  742 */     FTpTerm localFTpTerm = compileTpTerm(paramScope, paramSTpTerm);
/*      */     Object localObject;
/*      */     int i;
/*  743 */     //switch (9.$SwitchMap$pkg$hofgj$compiler$FTpTerm$Kind[localFTpTerm.getKind().ordinal()]) {
               switch (localFTpTerm.getKind().ordinal()) {
/*      */     case 1:
/*  745 */       localObject = (FTpTerm.Concrete)localFTpTerm;
/*  746 */       i = ((FTpTerm.Concrete)localObject).clasz.getParams().length;
/*  747 */       if (i == 0) {
/*  748 */         return new ATpTerm.Concrete(((FTpTerm.Concrete)localObject).clasz, new ACsTerm[0]);
/*      */       }
/*  750 */       error(paramSTpTerm, "unexpected type constructor\n expected: a type\n found   : a " + i + "-parameter type constructor");
/*      */ 
/*  753 */       return new ATpTerm.Error();
/*      */     case 2:
/*  757 */       localObject = (FTpTerm.Abstract)localFTpTerm;
/*  758 */       i = ((FTpTerm.Abstract)localObject).param.getParams().length;
/*  759 */       if (i == 0) {
/*  760 */         return new ATpTerm.Abstract(((FTpTerm.Abstract)localObject).param, new ACsTerm[0]);
/*      */       }
/*  762 */       error(paramSTpTerm, "unexpected type constructor\n expected: a type\n found   : a " + i + "-parameter type constructor");
/*      */ 
/*  765 */       return new ATpTerm.Error();
/*      */     case 3:
/*  769 */       localObject = (FTpTerm.Cs)localFTpTerm;
/*  770 */       ACsTerm localACsTerm = ((FTpTerm.Cs)localObject).term.evaluate();
/*  771 */       switch (localACsTerm.getKind()) {
/*      */       case FUNCTION:
/*  773 */         ACsTerm.Function localFunction = (ACsTerm.Function)localACsTerm;
/*  774 */         int j = localFunction.params.length;
/*  775 */         if (j == 0) {
/*  776 */           return localFunction.body;
/*      */         }
/*  778 */         error(paramSTpTerm, "unexpected type constructor\n expected: a type\n found   : a " + "" + j + "-parameter type constructor");
/*      */ 
/*  782 */         return new ATpTerm.Error();
/*      */       case ERROR:
/*  785 */         return new ATpTerm.Error();
/*      */       }
/*  787 */       throw new Error("" + localACsTerm.getKind());
/*      */     case 4:
/*  791 */       localObject = (FTpTerm.Tp)localFTpTerm;
/*  792 */       return ((FTpTerm.Tp)localObject).term;
/*      */     }
/*      */ 
/*  795 */     throw new Error("" + localFTpTerm.getKind());
/*      */   }
/*      */ 
/*      */   public FTpTerm compileTpTerm(Scope paramScope, STpTerm paramSTpTerm)
/*      */   {
/*  800 */     //switch (9.$SwitchMap$pkg$hofgj$stree$STpTerm$Kind[paramSTpTerm.getKind().ordinal()]) {
               switch (paramSTpTerm.getKind().ordinal()) {
               case 1:
/*  801 */       return new FTpTerm.Tp(new ATpTerm.Boolean());
/*      */     case 2:
/*  802 */       return new FTpTerm.Tp(new ATpTerm.Integer());
/*      */     case 3:
/*  803 */       return compileTpTerm(paramScope, (STpTerm.Ref)paramSTpTerm);
/*      */     case 4:
/*  804 */       return compileTpTerm(paramScope, (STpTerm.Abs)paramSTpTerm);
/*      */     case 5:
/*  805 */       return compileTpTerm(paramScope, (STpTerm.App)paramSTpTerm); }
/*  806 */     throw new Error("" + paramSTpTerm.getKind());
/*      */   }
/*      */ 
/*      */   public FTpTerm compileTpTerm(Scope paramScope, STpTerm.Ref paramRef)
/*      */   {
/*  811 */     if (paramRef.selectors.length != 0) {
/*  812 */       error(paramRef, "type prefixes are not supported");
/*  813 */       return new FTpTerm.Cs(new ACsTerm.Error());
/*      */     }
/*  815 */     return compileTypeRef(paramScope, paramRef.label);
/*      */   }
/*      */ 
/*      */   public FTpTerm compileTpTerm(Scope paramScope, STpTerm.Abs paramAbs)
/*      */   {
/*  820 */     TpParamScope localTpParamScope = new TpParamScope(paramScope, Option.Some(paramAbs.params));
/*  821 */     ATpTerm localATpTerm = compileTpTermToTp(localTpParamScope, paramAbs.body);
/*  822 */     return new FTpTerm.Cs(new ACsTerm.Function(localTpParamScope.getParams(), localATpTerm));
/*      */   }
/*      */ 
/*      */   public FTpTerm compileTpTerm(Scope paramScope, STpTerm.App paramApp) {
/*  826 */     FTpTerm localFTpTerm = compileTpTerm(paramScope, paramApp.fun);
/*      */     Object localObject1;
/*      */     Object localObject2;
/*  827 */     //switch (9.$SwitchMap$pkg$hofgj$compiler$FTpTerm$Kind[localFTpTerm.getKind().ordinal()]) {
               switch (localFTpTerm.getKind().ordinal()) {
/*      */     case 1:
/*  829 */       localObject1 = (FTpTerm.Concrete)localFTpTerm;
/*  830 */       localObject2 = compileTpTermArgs(paramScope, ((FTpTerm.Concrete)localObject1).clasz.getParams(), paramApp.args);
/*      */ 
/*  832 */       return new FTpTerm.Tp(new ATpTerm.Concrete(((FTpTerm.Concrete)localObject1).clasz, (ACsTerm[])localObject2));
/*      */     case 2:
/*  835 */       localObject1 = (FTpTerm.Abstract)localFTpTerm;
/*  836 */       localObject2 = compileTpTermArgs(paramScope, ((FTpTerm.Abstract)localObject1).param.getParams(), paramApp.args);
/*      */ 
/*  838 */       return new FTpTerm.Tp(new ATpTerm.Abstract(((FTpTerm.Abstract)localObject1).param, (ACsTerm[])localObject2));
/*      */     case 3:
/*  841 */       localObject1 = (FTpTerm.Cs)localFTpTerm;
/*  842 */       switch (((FTpTerm.Cs)localObject1).term.getKind()) {
/*      */       case FUNCTION:
/*  844 */         localObject2 = (ACsTerm.Function)((FTpTerm.Cs)localObject1).term;
/*  845 */         ACsTerm[] arrayOfACsTerm = compileTpTermArgs(paramScope, ((ACsTerm.Function)localObject2).params, paramApp.args);
/*      */ 
/*  847 */         return new FTpTerm.Tp(this.compiler.apply(((ACsTerm.Function)localObject2).params, arrayOfACsTerm, ((ACsTerm.Function)localObject2).body));
/*      */       case ERROR:
/*  851 */         localObject2 = (ACsTerm.Error)((FTpTerm.Cs)localObject1).term;
/*  852 */         return new FTpTerm.Tp(new ATpTerm.Error());
/*      */       }
/*      */ 
/*  855 */       throw new Error("" + ((FTpTerm.Cs)localObject1).term.getKind());
/*      */     case 4:
/*  859 */       localObject1 = (FTpTerm.Tp)localFTpTerm;
/*  860 */       error(paramApp, "cannot apply arguments to a type");
/*  861 */       return new FTpTerm.Tp(new ATpTerm.Error());
/*      */     }
/*      */ 
/*  864 */     throw new Error("" + localFTpTerm.getKind());
/*      */   }
/*      */ 
/*      */   public ACsTerm[] compileTpTermArgs(Scope paramScope, ATpParam[] paramArrayOfATpParam, STpArgList paramSTpArgList)
/*      */   {
/*  893 */     ACsTerm[] arrayOfACsTerm = new ACsTerm[paramSTpArgList.args.length];
/*  894 */     for (int i = 0; i < arrayOfACsTerm.length; i++)
/*  895 */       arrayOfACsTerm[i] = compileTpTermToCs(paramScope, paramSTpArgList.args[i]);
/*  896 */     return checkConformance(paramSTpArgList.position(), paramArrayOfATpParam, arrayOfACsTerm);
/*      */   }
/*      */ 
/*      */   public VlTp[] compileVlTerms(Scope paramScope, Option<SVlArgList> paramOption)
/*      */   {
/*  916 */     if (paramOption.isEmpty()) return new VlTp[0];
/*  917 */     return compileVlTerms(paramScope, ((SVlArgList)paramOption.get()).args);
/*      */   }
/*      */ 
/*      */   public VlTp[] compileVlTerms(Scope paramScope, SVlTerm[] paramArrayOfSVlTerm) {
/*  921 */     VlTp[] arrayOfVlTp = new VlTp[paramArrayOfSVlTerm.length];
/*  922 */     for (int i = 0; i < arrayOfVlTp.length; i++)
/*  923 */       arrayOfVlTp[i] = compileVlTerm(paramScope, paramArrayOfSVlTerm[i]);
/*  924 */     return arrayOfVlTp;
/*      */   }
/*      */ 
/*      */   public VlTp compileVlTerm(Scope paramScope, SVlTerm paramSVlTerm) {
/*  928 */     //switch (9.$SwitchMap$pkg$hofgj$stree$SVlTerm$Kind[paramSVlTerm.getKind().ordinal()]) {
               switch (paramSVlTerm.getKind().ordinal()) {
               case 1:
/*  929 */       return compileVlTerm(paramScope, (SVlTerm.Boolean)paramSVlTerm);
/*      */     case 2:
/*  930 */       return compileVlTerm(paramScope, (SVlTerm.Integer)paramSVlTerm);
/*      */     case 3:
/*  931 */       return compileVlTerm(paramScope, (SVlTerm.This)paramSVlTerm);
/*      */     case 4:
/*  932 */       return compileVlTerm(paramScope, (SVlTerm.Ref)paramSVlTerm);
/*      */     case 5:
/*  933 */       return compileVlTerm(paramScope, (SVlTerm.Sel)paramSVlTerm);
/*      */     case 6:
/*  934 */       return compileVlTerm(paramScope, (SVlTerm.New)paramSVlTerm);
/*      */     case 7:
/*  935 */       return compileVlTerm(paramScope, (SVlTerm.If)paramSVlTerm);
/*      */     case 8:
/*  936 */       return compileVlTerm(paramScope, (SVlTerm.Unary)paramSVlTerm);
/*      */     case 9:
/*  937 */       return compileVlTerm(paramScope, (SVlTerm.Binary)paramSVlTerm);
/*      */     case 10:
/*  938 */       return compileVlTerm(paramScope, (SVlTerm.Block)paramSVlTerm); }
/*  939 */     throw new Error("" + paramSVlTerm.getKind());
/*      */   }
/*      */ 
/*      */   public VlTp compileVlTerm(Scope paramScope, SVlTerm.Boolean paramBoolean)
/*      */   {
/*  944 */     return new VlTp(new AVlTerm.Boolean(paramBoolean.value), new ATpTerm.Boolean());
/*      */   }
/*      */ 
/*      */   public VlTp compileVlTerm(Scope paramScope, SVlTerm.Integer paramInteger)
/*      */   {
/*  949 */     return new VlTp(new AVlTerm.Integer(paramInteger.value), new ATpTerm.Integer());
/*      */   }
/*      */ 
/*      */   public VlTp compileVlTerm(Scope paramScope, SVlTerm.This paramThis)
/*      */   {
/*  954 */     Option localOption = paramScope.getCurrentClass();
/*  955 */     if (localOption.isEmpty()) {
/*  956 */       error(paramThis, "no current instance is available here");
/*  957 */       return new VlTp(new AVlTerm.Error(), new ATpTerm.Error());
/*      */     }
/*  959 */     return new VlTp(new AVlTerm.This(), this.compiler.getTp((AClass)localOption.get()));
/*      */   }
/*      */ 
/*      */   public VlTp compileVlTerm(Scope paramScope, SVlTerm.Ref paramRef)
/*      */   {
/*  964 */     return compileVlRef(paramScope, paramRef.ref);
/*      */   }
/*      */ 
/*      */   public VlTp compileVlTerm(Scope paramScope, SVlTerm.Sel paramSel) {
/*  968 */     VlTp localVlTp = compileVlTerm(paramScope, paramSel.object);
/*  969 */     return compileVlRef(paramScope, localVlTp, paramSel.selector.ref);
/*      */   }
/*      */ 
/*      */   public VlTp compileVlTerm(Scope paramScope, SVlTerm.New paramNew) {
/*  973 */     Option localOption = compileClassRef(paramNew.clasz);
/*  974 */     if (localOption.isEmpty())
/*  975 */       return new VlTp(new AVlTerm.Error(), new ATpTerm.Error());
/*  976 */     ACsTerm[] arrayOfACsTerm = compileTpTermsToCs(paramScope, paramNew.targs);
/*  977 */     arrayOfACsTerm = checkConformance(paramNew.clasz.position(), ((ClassScope)localOption.get()).getClasz().getParams(), arrayOfACsTerm);
/*      */ 
/*  979 */     ATpTerm.Concrete localConcrete = new ATpTerm.Concrete(((ClassScope)localOption.get()).getClasz(), arrayOfACsTerm);
/*  980 */     VlTp[] arrayOfVlTp = compileVlTerms(paramScope, paramNew.vargs);
/*  981 */     if (!paramNew.body.isEmpty()) {
/*  982 */       error((STree)paramNew.body.get(), "anonymous classes are not supported");
/*  983 */       return new VlTp(new AVlTerm.Error(), new ATpTerm.Error());
/*      */     }
/*  985 */     AField[] arrayOfAField = ((ClassScope)localOption.get()).getFields(0);
/*  986 */     ATpTerm[] arrayOfATpTerm = new ATpTerm[arrayOfAField.length];
/*  987 */     for (int i = 0; i < arrayOfATpTerm.length; i++) {
/*  988 */       arrayOfATpTerm[i] = this.compiler.asSeenFrom(arrayOfAField[i].getOwner(), arrayOfAField[i].getType(), localConcrete);
/*      */     }
/*  990 */     arrayOfVlTp = checkConformance(paramNew.clasz.position(), paramNew.vargs.isEmpty() ? new STree[0] : ((SVlArgList)paramNew.vargs.get()).args, arrayOfATpTerm, arrayOfVlTp);
/*      */ 
/*  995 */     for (MethodScope localMethodScope : ((ClassScope)localOption.get()).getMethods().values()) {
/*  996 */       if (localMethodScope.getMethod().getBody().isEmpty()) {
/*  997 */         error(paramNew, "class '" + ((ClassScope)localOption.get()).getClasz().getName() + "' cannot be instantiated because " + "it has no implementation for method '" + localMethodScope.getMethod().getName() + "' defined in class '" + localMethodScope.getMethod().getOwner().getName() + "'");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1004 */     return new VlTp(new AVlTerm.New(((ClassScope)localOption.get()).getClasz(), arrayOfACsTerm, this.compiler.getVlTerms(arrayOfVlTp)), localConcrete);
/*      */   }
/*      */ 
/*      */   public VlTp compileVlTerm(Scope paramScope, SVlTerm.If paramIf)
/*      */   {
/* 1011 */     VlTp localVlTp1 = compileVlTerm(paramScope, paramIf.test);
/* 1012 */     localVlTp1 = checkConformance(paramIf.test.position(), new ATpTerm.Boolean(), localVlTp1);
/*      */ 
/* 1014 */     VlTp localVlTp2 = compileVlTerm(paramScope, paramIf.success);
/* 1015 */     VlTp localVlTp3 = compileVlTerm(paramScope, paramIf.failure);
/* 1016 */     ATpTerm localATpTerm = computeLUB(paramIf.position(), localVlTp2.tp, localVlTp3.tp);
/* 1017 */     return new VlTp(new AVlTerm.If(localVlTp1.vl, localVlTp2.vl, localVlTp3.vl), localATpTerm);
/*      */   }
/*      */ 
/*      */   public VlTp compileVlTerm(Scope paramScope, SVlTerm.Unary paramUnary) {
/* 1021 */     AVlTerm.UnaryOperator localUnaryOperator = compileOperator(paramUnary.operator);
/* 1022 */     VlTp localVlTp = compileVlTerm(paramScope, paramUnary.arg);
/* 1023 */     localVlTp = checkConformance(paramUnary.arg.position(), getArgType(localUnaryOperator), localVlTp);
/*      */ 
/* 1025 */     return new VlTp(new AVlTerm.Unary(localUnaryOperator, localVlTp.vl), getResultType(localUnaryOperator));
/*      */   }
/*      */ 
/*      */   public VlTp compileVlTerm(Scope paramScope, SVlTerm.Binary paramBinary)
/*      */   {
/* 1031 */     AVlTerm.BinaryOperator localBinaryOperator = compileOperator(paramBinary.operator);
/* 1032 */     VlTp localVlTp1 = compileVlTerm(paramScope, paramBinary.argl);
/* 1033 */     localVlTp1 = checkConformance(paramBinary.argl.position(), getArgLType(localBinaryOperator), localVlTp1);
/*      */ 
/* 1035 */     VlTp localVlTp2 = compileVlTerm(paramScope, paramBinary.argr);
/* 1036 */     localVlTp2 = checkConformance(paramBinary.argr.position(), getArgRType(localBinaryOperator), localVlTp2);
/*      */ 
/* 1038 */     return new VlTp(new AVlTerm.Binary(localVlTp1.vl, localBinaryOperator, localVlTp2.vl), getResultType(localBinaryOperator));
/*      */   }
/*      */ 
/*      */   public VlTp compileVlTerm(Scope paramScope, SVlTerm.Block paramBlock)
/*      */   {
/* 1044 */     for (SMember localSMember : paramBlock.members)
/* 1045 */       error(localSMember, "local declarations are not supported");
/* 1046 */     return compileVlTerm(paramScope, paramBlock.body);
/*      */   }
/*      */ 
/*      */   public VlTp compileVlRef(Scope paramScope, SVlRef paramSVlRef) {
/* 1050 */     if ((paramSVlRef.targs.isEmpty()) && (paramSVlRef.vargs.isEmpty())) {
/* 1051 */       return paramScope.lookupField(paramSVlRef.label);
/*      */     }
/* 1053 */     ACsTerm[] arrayOfACsTerm = compileTpTermsToCs(paramScope, paramSVlRef.targs);
/* 1054 */     VlTp[] arrayOfVlTp = compileVlTerms(paramScope, paramSVlRef.vargs);
/* 1055 */     AVlTerm[] arrayOfAVlTerm = this.compiler.getVlTerms(arrayOfVlTp);
/* 1056 */     Option localOption = paramScope.lookupMethod(paramSVlRef.label, new AVlTerm.This(), arrayOfACsTerm, arrayOfAVlTerm);
/*      */ 
/* 1059 */     if (localOption.isEmpty()) {
/* 1060 */       error(paramSVlRef.label, "cannot resolve function '" + paramSVlRef.label.label + "'");
/*      */ 
/* 1062 */       return new VlTp(new AVlTerm.Error(), new ATpTerm.Error());
/*      */     }
/* 1064 */     return compileVlRefCall(paramSVlRef, arrayOfACsTerm, arrayOfVlTp, localOption);
/*      */   }
/*      */ 
/*      */   public VlTp compileVlRef(Scope paramScope, VlTp paramVlTp, SVlRef paramSVlRef)
/*      */   {
/* 1069 */     if ((paramSVlRef.targs.isEmpty()) && (paramSVlRef.vargs.isEmpty())) {
/* 1070 */       Option<ATpTerm> localObject1 = this.compiler.getUpperClass(paramVlTp.tp);
/* 1071 */       if (((Option)localObject1).isEmpty()) {
/* 1072 */         error(paramSVlRef.label, "value of type '" + this.compiler.toString(paramVlTp.tp) + "' has no field named '" + paramSVlRef.label.label + "'");
/*      */ 
/* 1075 */         return new VlTp(new AVlTerm.Error(), new ATpTerm.Error());
/*      */       }
/* 1077 */       if (((ATpTerm)((Option)localObject1).get()).isError())
/* 1078 */         return new VlTp(new AVlTerm.Error(), new ATpTerm.Error());
/* 1079 */       ATpTerm.Concrete localObject2 = (ATpTerm.Concrete)((Option)localObject1).get();
/* 1080 */       Option localObject3 = this.compiler.getClassScope(((ATpTerm.Concrete)localObject2).clasz).lookupFieldLocal(paramSVlRef.label, paramVlTp.vl, (ATpTerm)localObject2);
/*      */ 
/* 1083 */       if (!((Option)localObject3).isEmpty()) return (VlTp)((Option)localObject3).get();
/* 1084 */       error(paramSVlRef.label, "value of type '" + this.compiler.toString(paramVlTp.tp) + "' has no field named '" + paramSVlRef.label.label + "'");
/*      */ 
/* 1086 */       return new VlTp(new AVlTerm.Error(), new ATpTerm.Error());
/*      */     }
/* 1088 */     Object localObject1 = compileTpTermsToCs(paramScope, paramSVlRef.targs);
/* 1089 */     Object localObject2 = compileVlTerms(paramScope, paramSVlRef.vargs);
/* 1090 */     Object localObject3 = this.compiler.getVlTerms((VlTp[])localObject2);
/* 1091 */     Option localOption1 = this.compiler.getUpperClass(paramVlTp.tp);
/* 1092 */     if (localOption1.isEmpty()) {
/* 1093 */       error(paramSVlRef.label, "value of type '" + this.compiler.toString(paramVlTp.tp) + "' has no method named '" + paramSVlRef.label.label + "'");
/*      */ 
/* 1096 */       return new VlTp(new AVlTerm.Error(), new ATpTerm.Error());
/*      */     }
/* 1098 */     if (((ATpTerm)localOption1.get()).isError())
/* 1099 */       return new VlTp(new AVlTerm.Error(), new ATpTerm.Error());
/* 1100 */     ATpTerm.Concrete localConcrete = (ATpTerm.Concrete)localOption1.get();
/* 1101 */     Option localOption2 = this.compiler.getClassScope(localConcrete.clasz).lookupMethodLocal(paramSVlRef.label, paramVlTp.vl, (ACsTerm[])localObject1, (AVlTerm[])localObject3, localConcrete);
/*      */ 
/* 1104 */     if (localOption2.isEmpty()) {
/* 1105 */       error(paramSVlRef.label, "value of type '" + this.compiler.toString(paramVlTp.tp) + "' has no method named '" + paramSVlRef.label.label + "'");
/*      */ 
/* 1108 */       return new VlTp(new AVlTerm.Error(), new ATpTerm.Error());
/*      */     }
/* 1110 */     return compileVlRefCall(paramSVlRef, (ACsTerm[])localObject1, (VlTp[])localObject2, localOption2);
/*      */   }
/*      */ 
/*      */   public VlTp compileVlRefCall(SVlRef paramSVlRef, ACsTerm[] paramArrayOfACsTerm, VlTp[] paramArrayOfVlTp, Option<P2<AVlTerm, P2<ATpParam[], ATpTerm[]>>> paramOption)
/*      */   {
/* 1121 */     ACsTerm[] arrayOfACsTerm = checkConformance(paramSVlRef.label.position(), (ATpParam[])((P2)((P2)paramOption.get()).vl1).vl0, paramArrayOfACsTerm);
/*      */ 
/* 1123 */     if (arrayOfACsTerm == paramArrayOfACsTerm) {
/* 1124 */       ATpParam[] arrayOfATpParam = (ATpParam[])((P2)((P2)paramOption.get()).vl1).vl0;
/* 1125 */       STree[] arrayOfSVlTerm = paramSVlRef.vargs.isEmpty() ? new STree[0] : ((SVlArgList)paramSVlRef.vargs.get()).args;
/*      */ 
/* 1128 */       ATpTerm[] arrayOfATpTerm = new ATpTerm[((ATpTerm[])((P2)((P2)paramOption.get()).vl1).vl1).length - 1];
/*      */ 
/* 1130 */       for (int i = 0; i < arrayOfATpTerm.length; i++) {
/* 1131 */         arrayOfATpTerm[i] = ((ATpTerm[])((P2)((P2)paramOption.get()).vl1).vl1)[i];
/*      */       }
/*      */ 
/* 1136 */       arrayOfATpTerm = Cloner.cloneTps(arrayOfATpParam, paramArrayOfACsTerm, arrayOfATpTerm);
/* 1137 */       Freezer.instance.rewrite(arrayOfATpTerm);
/*      */ 
/* 1140 */       checkConformance(paramSVlRef.label.position(), arrayOfSVlTerm, arrayOfATpTerm, paramArrayOfVlTp);
/*      */ 
/* 1142 */       ATpTerm localATpTerm = Cloner.cloneTp(arrayOfATpParam, paramArrayOfACsTerm, ((ATpTerm[])((P2)((P2)paramOption.get()).vl1).vl1)[(((ATpTerm[])((P2)((P2)paramOption.get()).vl1).vl1).length - 1)]);
/*      */ 
/* 1144 */       Freezer.instance.rewrite(localATpTerm);
/* 1145 */       return new VlTp((AVlTerm)((P2)paramOption.get()).vl0, localATpTerm);
/*      */     }
/* 1147 */     return new VlTp(new AVlTerm.Error(), new ATpTerm.Error());
/*      */   }
/*      */ 
/*      */   public Option<ClassScope> compileClassRef(SLabel paramSLabel)
/*      */   {
/* 1155 */     Option localOption = this.compiler.lookupClass(paramSLabel.label);
/* 1156 */     if (localOption.isEmpty()) error(paramSLabel, "cannot find class named '" + paramSLabel.label + "'");
/*      */ 
/* 1158 */     return localOption;
/*      */   }
/*      */ 
/*      */   public FTpTerm compileTypeRef(Scope paramScope, SLabel paramSLabel) {
/* 1162 */     String str = paramSLabel.label;
/* 1163 */     Option localOption = paramScope.lookupType(str);
/* 1164 */     if (localOption.isEmpty()) {
/* 1165 */       if ((this.source.isScala()) && (str.equals("Boolean")))
/* 1166 */         return new FTpTerm.Tp(new ATpTerm.Boolean());
/* 1167 */       if ((this.source.isScala()) && (str.equals("Int")))
/* 1168 */         return new FTpTerm.Tp(new ATpTerm.Integer());
/*      */     }
/* 1170 */     if (localOption.isEmpty()) {
/* 1171 */       error(paramSLabel, "cannot resolve type '" + str + "'");
/* 1172 */       return new FTpTerm.Cs(new ACsTerm.Error());
/*      */     }
/* 1174 */     return (FTpTerm)localOption.get();
/*      */   }
/*      */ 
/*      */   public AVlTerm.UnaryOperator compileOperator(SVlTerm.UnaryOperator paramUnaryOperator)
/*      */   {
/* 1182 */     //switch (9.$SwitchMap$pkg$hofgj$stree$SVlTerm$UnaryOperator[paramUnaryOperator.ordinal()]) {
               switch (paramUnaryOperator.ordinal()) {
               case 1:
/* 1183 */       return AVlTerm.UnaryOperator.IPOS;
/*      */     case 2:
/* 1184 */       return AVlTerm.UnaryOperator.INEG;
/*      */     case 3:
/* 1185 */       return AVlTerm.UnaryOperator.INOT;
/*      */     case 4:
/* 1186 */       return AVlTerm.UnaryOperator.ZNOT; }
/* 1187 */     throw new Error("" + paramUnaryOperator);
/*      */   }
/*      */ 
/*      */   public ATpTerm getArgType(AVlTerm.UnaryOperator paramUnaryOperator)
/*      */   {
/* 1192 */     //switch (9.$SwitchMap$pkg$hofgj$compiler$AVlTerm$UnaryOperator[paramUnaryOperator.ordinal()]) {
               switch (paramUnaryOperator.ordinal()) {
               case 1:
/* 1193 */       return new ATpTerm.Integer();
/*      */     case 2:
/* 1194 */       return new ATpTerm.Integer();
/*      */     case 3:
/* 1195 */       return new ATpTerm.Integer();
/*      */     case 4:
/* 1196 */       return new ATpTerm.Boolean(); }
/* 1197 */     throw new Error("" + paramUnaryOperator);
/*      */   }
/*      */ 
/*      */   public ATpTerm getResultType(AVlTerm.UnaryOperator paramUnaryOperator)
/*      */   {
/* 1202 */     //switch (9.$SwitchMap$pkg$hofgj$compiler$AVlTerm$UnaryOperator[paramUnaryOperator.ordinal()]) {
               switch (paramUnaryOperator.ordinal()) {
               case 1:
/* 1203 */       return new ATpTerm.Integer();
/*      */     case 2:
/* 1204 */       return new ATpTerm.Integer();
/*      */     case 3:
/* 1205 */       return new ATpTerm.Integer();
/*      */     case 4:
/* 1206 */       return new ATpTerm.Boolean(); }
/* 1207 */     throw new Error("" + paramUnaryOperator);
/*      */   }
/*      */ 
/*      */   public AVlTerm.BinaryOperator compileOperator(SVlTerm.BinaryOperator paramBinaryOperator)
/*      */   {
/* 1212 */     //switch (9.$SwitchMap$pkg$hofgj$stree$SVlTerm$BinaryOperator[paramBinaryOperator.ordinal()]) {
               switch (paramBinaryOperator.ordinal()) {
               case 1:
/* 1213 */       return AVlTerm.BinaryOperator.IADD;
/*      */     case 2:
/* 1214 */       return AVlTerm.BinaryOperator.ISUB;
/*      */     case 3:
/* 1215 */       return AVlTerm.BinaryOperator.IMUL;
/*      */     case 4:
/* 1216 */       return AVlTerm.BinaryOperator.IDIV;
/*      */     case 5:
/* 1217 */       return AVlTerm.BinaryOperator.IREM;
/*      */     case 6:
/* 1218 */       return AVlTerm.BinaryOperator.ILSL;
/*      */     case 7:
/* 1219 */       return AVlTerm.BinaryOperator.ILSR;
/*      */     case 8:
/* 1220 */       return AVlTerm.BinaryOperator.IASR;
/*      */     case 9:
/* 1221 */       return AVlTerm.BinaryOperator.ILT;
/*      */     case 10:
/* 1222 */       return AVlTerm.BinaryOperator.IGE;
/*      */     case 11:
/* 1223 */       return AVlTerm.BinaryOperator.ILE;
/*      */     case 12:
/* 1224 */       return AVlTerm.BinaryOperator.IGT;
/*      */     case 13:
/* 1225 */       return AVlTerm.BinaryOperator.IEQ;
/*      */     case 14:
/* 1226 */       return AVlTerm.BinaryOperator.INE;
/*      */     case 15:
/* 1227 */       return AVlTerm.BinaryOperator.IOR;
/*      */     case 16:
/* 1228 */       return AVlTerm.BinaryOperator.IXOR;
/*      */     case 17:
/* 1229 */       return AVlTerm.BinaryOperator.IAND;
/*      */     case 18:
/* 1230 */       return AVlTerm.BinaryOperator.ZOR;
/*      */     case 19:
/* 1231 */       return AVlTerm.BinaryOperator.ZXOR;
/*      */     case 20:
/* 1232 */       return AVlTerm.BinaryOperator.ZAND; }
/* 1233 */     throw new Error("" + paramBinaryOperator);
/*      */   }
/*      */ 
/*      */   public ATpTerm getArgLType(AVlTerm.BinaryOperator paramBinaryOperator)
/*      */   {
/* 1238 */     //switch (9.$SwitchMap$pkg$hofgj$compiler$AVlTerm$BinaryOperator[paramBinaryOperator.ordinal()]) {
               switch (paramBinaryOperator.ordinal()) {
               case 1:
/* 1239 */       return new ATpTerm.Integer();
/*      */     case 2:
/* 1240 */       return new ATpTerm.Integer();
/*      */     case 3:
/* 1241 */       return new ATpTerm.Integer();
/*      */     case 4:
/* 1242 */       return new ATpTerm.Integer();
/*      */     case 5:
/* 1243 */       return new ATpTerm.Integer();
/*      */     case 6:
/* 1244 */       return new ATpTerm.Integer();
/*      */     case 7:
/* 1245 */       return new ATpTerm.Integer();
/*      */     case 8:
/* 1246 */       return new ATpTerm.Integer();
/*      */     case 9:
/* 1247 */       return new ATpTerm.Integer();
/*      */     case 10:
/* 1248 */       return new ATpTerm.Integer();
/*      */     case 11:
/* 1249 */       return new ATpTerm.Integer();
/*      */     case 12:
/* 1250 */       return new ATpTerm.Integer();
/*      */     case 13:
/* 1251 */       return new ATpTerm.Integer();
/*      */     case 14:
/* 1252 */       return new ATpTerm.Integer();
/*      */     case 15:
/* 1253 */       return new ATpTerm.Integer();
/*      */     case 16:
/* 1254 */       return new ATpTerm.Integer();
/*      */     case 17:
/* 1255 */       return new ATpTerm.Integer();
/*      */     case 18:
/* 1256 */       return new ATpTerm.Boolean();
/*      */     case 19:
/* 1257 */       return new ATpTerm.Boolean();
/*      */     case 20:
/* 1258 */       return new ATpTerm.Boolean(); }
/* 1259 */     throw new Error("" + paramBinaryOperator);
/*      */   }
/*      */ 
/*      */   public ATpTerm getArgRType(AVlTerm.BinaryOperator paramBinaryOperator)
/*      */   {
/* 1264 */     //switch (9.$SwitchMap$pkg$hofgj$compiler$AVlTerm$BinaryOperator[paramBinaryOperator.ordinal()]) {
               switch (paramBinaryOperator.ordinal()) {
               case 1:
/* 1265 */       return new ATpTerm.Integer();
/*      */     case 2:
/* 1266 */       return new ATpTerm.Integer();
/*      */     case 3:
/* 1267 */       return new ATpTerm.Integer();
/*      */     case 4:
/* 1268 */       return new ATpTerm.Integer();
/*      */     case 5:
/* 1269 */       return new ATpTerm.Integer();
/*      */     case 6:
/* 1270 */       return new ATpTerm.Integer();
/*      */     case 7:
/* 1271 */       return new ATpTerm.Integer();
/*      */     case 8:
/* 1272 */       return new ATpTerm.Integer();
/*      */     case 9:
/* 1273 */       return new ATpTerm.Integer();
/*      */     case 10:
/* 1274 */       return new ATpTerm.Integer();
/*      */     case 11:
/* 1275 */       return new ATpTerm.Integer();
/*      */     case 12:
/* 1276 */       return new ATpTerm.Integer();
/*      */     case 13:
/* 1277 */       return new ATpTerm.Integer();
/*      */     case 14:
/* 1278 */       return new ATpTerm.Integer();
/*      */     case 15:
/* 1279 */       return new ATpTerm.Integer();
/*      */     case 16:
/* 1280 */       return new ATpTerm.Integer();
/*      */     case 17:
/* 1281 */       return new ATpTerm.Integer();
/*      */     case 18:
/* 1282 */       return new ATpTerm.Boolean();
/*      */     case 19:
/* 1283 */       return new ATpTerm.Boolean();
/*      */     case 20:
/* 1284 */       return new ATpTerm.Boolean(); }
/* 1285 */     throw new Error("" + paramBinaryOperator);
/*      */   }
/*      */ 
/*      */   public ATpTerm getResultType(AVlTerm.BinaryOperator paramBinaryOperator)
/*      */   {
/* 1290 */     //switch (9.$SwitchMap$pkg$hofgj$compiler$AVlTerm$BinaryOperator[paramBinaryOperator.ordinal()]) {
               switch (paramBinaryOperator.ordinal()) {
               case 1:
/* 1291 */       return new ATpTerm.Integer();
/*      */     case 2:
/* 1292 */       return new ATpTerm.Integer();
/*      */     case 3:
/* 1293 */       return new ATpTerm.Integer();
/*      */     case 4:
/* 1294 */       return new ATpTerm.Integer();
/*      */     case 5:
/* 1295 */       return new ATpTerm.Integer();
/*      */     case 6:
/* 1296 */       return new ATpTerm.Integer();
/*      */     case 7:
/* 1297 */       return new ATpTerm.Integer();
/*      */     case 8:
/* 1298 */       return new ATpTerm.Integer();
/*      */     case 9:
/* 1299 */       return new ATpTerm.Boolean();
/*      */     case 10:
/* 1300 */       return new ATpTerm.Boolean();
/*      */     case 11:
/* 1301 */       return new ATpTerm.Boolean();
/*      */     case 12:
/* 1302 */       return new ATpTerm.Boolean();
/*      */     case 13:
/* 1303 */       return new ATpTerm.Boolean();
/*      */     case 14:
/* 1304 */       return new ATpTerm.Boolean();
/*      */     case 15:
/* 1305 */       return new ATpTerm.Integer();
/*      */     case 16:
/* 1306 */       return new ATpTerm.Integer();
/*      */     case 17:
/* 1307 */       return new ATpTerm.Integer();
/*      */     case 18:
/* 1308 */       return new ATpTerm.Boolean();
/*      */     case 19:
/* 1309 */       return new ATpTerm.Boolean();
/*      */     case 20:
/* 1310 */       return new ATpTerm.Boolean(); }
/* 1311 */     throw new Error("" + paramBinaryOperator);
/*      */   }
/*      */ 
/*      */   public ACsTerm[] checkConformance(int paramInt, ATpParam[] paramArrayOfATpParam, ACsTerm[] paramArrayOfACsTerm)
/*      */   {
/* 1322 */     ACsTerm[] arrayOfACsTerm = new ACsTerm[paramArrayOfATpParam.length];
/* 1323 */     for (int i = 0; i < arrayOfACsTerm.length; i++)
/* 1324 */       arrayOfACsTerm[i] = new ACsTerm.Error();
/* 1325 */     if (paramArrayOfACsTerm.length != paramArrayOfATpParam.length) {
/* 1326 */       error(paramInt, "wrong number of type arguments\n  expected: " + Util.toString(paramArrayOfATpParam.length, "argument") + "\n  found   : " + Util.toString(paramArrayOfACsTerm.length, "argument"));
/*      */ 
/* 1329 */       return arrayOfACsTerm;
/*      */     }
/* 1331 */     if (!EqualityTester.areConform0(this.compiler, paramArrayOfATpParam, paramArrayOfACsTerm)) {
/* 1332 */       error(paramInt, "signature mismatch\n  expected: " + this.compiler.toString(paramArrayOfATpParam) + "\n  found   : " + this.compiler.toString(paramArrayOfACsTerm));
/*      */ 
/* 1335 */       return arrayOfACsTerm;
/*      */     }
/* 1337 */     toConformance(paramInt, paramArrayOfATpParam, paramArrayOfACsTerm);
/* 1338 */     return paramArrayOfACsTerm;
/*      */   }
/*      */ 
/*      */   public void toConformance(final int paramInt, final ATpParam[] paramArrayOfATpParam, final ACsTerm[] paramArrayOfACsTerm)
/*      */   {
/* 1347 */     promise(new Task(paramArrayOfATpParam) {
/*      */       public Void evaluate() {
/* 1349 */         if (!EqualityTester.areConform(Context.this.compiler, paramArrayOfATpParam, paramArrayOfACsTerm)) {
/* 1350 */           Context.this.error(paramInt, "signature mismatch\n  expected: " + Context.this.compiler.toString(paramArrayOfATpParam) + "\n  found   : " + Context.this.compiler.toString(paramArrayOfACsTerm));
/*      */         }
/*      */ 
/* 1354 */         return null;
/*      */       }
/*      */       public String getTask() {
/* 1357 */         return "check conformance at " + paramInt;
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public VlTp[] checkConformance(int paramInt, STree[] paramArrayOfSTree, ATpTerm[] paramArrayOfATpTerm, VlTp[] paramArrayOfVlTp)
/*      */   {
/* 1365 */     if (paramArrayOfVlTp.length != paramArrayOfATpTerm.length) {
/* 1366 */       error(paramInt, "wrong number of value arguments\n  expected: " + Util.toString(paramArrayOfATpTerm.length, "argument") + "\n  found   : " + Util.toString(paramArrayOfVlTp.length, "argument"));
/*      */ 
/* 1369 */       VlTp[] arrayOfVlTp = new VlTp[paramArrayOfATpTerm.length];
/* 1370 */       for (int j = 0; j < arrayOfVlTp.length; j++)
/* 1371 */         arrayOfVlTp[j] = new VlTp(new AVlTerm.Error(), new ATpTerm.Error());
/* 1372 */       return arrayOfVlTp;
/*      */     }
/* 1374 */     for (int i = 0; i < paramArrayOfATpTerm.length; i++) {
/* 1375 */       paramArrayOfVlTp[i] = checkConformance(paramArrayOfSTree[i].position(), paramArrayOfATpTerm[i], paramArrayOfVlTp[i]);
/*      */     }
/* 1377 */     return paramArrayOfVlTp;
/*      */   }
/*      */ 
/*      */   public VlTp checkConformance(int paramInt, ATpTerm paramATpTerm, VlTp paramVlTp) {
/* 1381 */     if (this.compiler.isSubtypeOf(paramVlTp.tp, paramATpTerm, true)) return paramVlTp;
/* 1382 */     error(paramInt, "type mismatch\n  expected: " + this.compiler.toString(paramATpTerm) + "\n  found   : " + this.compiler.toString(paramVlTp.tp));
/*      */ 
/* 1385 */     return new VlTp(new AVlTerm.Error(), new ATpTerm.Error());
/*      */   }
/*      */ 
/*      */   public void checkOverride(int paramInt, AMethod paramAMethod1, AMethod paramAMethod2) {
/* 1389 */     ATpParam[] arrayOfATpParam1 = paramAMethod2.getOwner().getParams();
/* 1390 */     ACsTerm[] arrayOfACsTerm = this.compiler.getClassArgsFrom(paramAMethod2.getOwner(), this.compiler.getTp(paramAMethod1.getOwner()));
/*      */ 
/* 1392 */     ATpParam[] arrayOfATpParam2 = paramAMethod1.getTpParams();
/* 1393 */     ATpTerm[] arrayOfATpTerm1 = new ATpTerm[paramAMethod1.getVlParams().length + 1];
/* 1394 */     for (int i = 0; i < paramAMethod1.getVlParams().length; i++)
/* 1395 */       arrayOfATpTerm1[i] = paramAMethod1.getVlParams()[i].getType();
/* 1396 */     arrayOfATpTerm1[paramAMethod1.getVlParams().length] = paramAMethod1.getType();
/* 1397 */     P2 localP2 = Cloner.cloneMethod(arrayOfATpParam1, arrayOfACsTerm, paramAMethod2);
/* 1398 */     ATpParam[] arrayOfATpParam3 = (ATpParam[])localP2.vl0;
/* 1399 */     ATpTerm[] arrayOfATpTerm2 = (ATpTerm[])localP2.vl1;
/* 1400 */     Freezer.instance.rewrite(arrayOfATpParam3);
/* 1401 */     Freezer.instance.rewrite(arrayOfATpTerm2);
/* 1402 */     if (!EqualityTester.areEqual(this.compiler, arrayOfATpParam2, arrayOfATpTerm1, arrayOfATpParam3, arrayOfATpTerm2, true))
/*      */     {
/* 1405 */       ATpTerm[] arrayOfATpTerm3 = new ATpTerm[arrayOfATpTerm1.length - 1];
/* 1406 */       for (int j = 0; j < arrayOfATpTerm3.length; j++)
/* 1407 */         arrayOfATpTerm3[j] = arrayOfATpTerm1[j];
/* 1408 */       ATpTerm[] arrayOfATpTerm4 = new ATpTerm[arrayOfATpTerm2.length - 1];
/* 1409 */       for (int k = 0; k < arrayOfATpTerm4.length; k++)
/* 1410 */         arrayOfATpTerm4[k] = arrayOfATpTerm2[k];
/* 1411 */       ATpTerm localATpTerm1 = arrayOfATpTerm1[(arrayOfATpTerm1.length - 1)];
/* 1412 */       ATpTerm localATpTerm2 = arrayOfATpTerm2[(arrayOfATpTerm2.length - 1)];
/* 1413 */       error(paramInt, "the method '" + paramAMethod1.getName() + "' cannot override the method '" + paramAMethod2.getName() + "' of class '" + paramAMethod2.getOwner().getName() + "' with a different signature" + "\n  expected: " + "[" + this.compiler.toString(arrayOfATpParam3) + "]" + "(" + this.compiler.toString(arrayOfATpTerm4) + ")" + ": " + this.compiler.toString(localATpTerm2) + "\n  found   : " + "[" + this.compiler.toString(arrayOfATpParam2) + "]" + "(" + this.compiler.toString(arrayOfATpTerm3) + ")" + ": " + this.compiler.toString(localATpTerm1));
/*      */     }
/*      */   }
/*      */ 
/*      */   public ATpTerm computeLUB(int paramInt, ATpTerm paramATpTerm1, ATpTerm paramATpTerm2)
/*      */   {
/* 1433 */     if (this.compiler.isSubtypeOf(paramATpTerm1, paramATpTerm2, true)) return paramATpTerm2;
/* 1434 */     ATpTerm localATpTerm = paramATpTerm1;
/*      */     while (true) { if (this.compiler.isSubtypeOf(paramATpTerm2, localATpTerm, true)) return localATpTerm;
/* 1436 */       Option localOption = this.compiler.getUpperBound(localATpTerm);
/* 1437 */       if (localOption.isEmpty()) break;
/* 1438 */       localATpTerm = (ATpTerm)localOption.get();
/*      */     }
/* 1440 */     error(paramInt, "cannot compute least upper bound of\n  type 1: " + this.compiler.toString(paramATpTerm1) + "\n  type 2: " + this.compiler.toString(paramATpTerm2));
/*      */ 
/* 1443 */     return new ATpTerm.Error();
/*      */   }
/*      */ 
/*      */   public Promise<Option<AClassBase>> toClassBase(final ClassScope paramClassScope, final Option<SClassBase> paramOption)
/*      */   {
/* 1453 */     if (paramOption.isEmpty()) {
/* 1454 */       paramClassScope.setSuperclass(Option.Some(this.compiler.getObjectScope()));
/* 1455 */       return Promise.value(Option.Some(this.compiler.getObjectAsBase()));
/*      */     }
/*      */ 
/* 1458 */     return promise(new Task(paramClassScope) {
/*      */       public Option<AClassBase> evaluate() {
/* 1460 */         return Option.Some(Context.this.compileClassBase(paramClassScope, (SClassBase)paramOption.get()));
/*      */       }
/*      */ 
/*      */       public String getTask() {
/* 1464 */         return "compile class base of " + paramClassScope.getClasz().getKindName();
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public Promise<ACsTerm[]> toClassSuperArgs(final ClassScope paramClassScope, final SClassBase paramSClassBase)
/*      */   {
/* 1474 */     return promise(new Task(paramClassScope) {
/*      */       public ACsTerm[] evaluate() {
/* 1476 */         return Context.this.compileClassSuperArgs(paramClassScope, paramSClassBase);
/*      */       }
/*      */       public String getTask() {
/* 1479 */         return "compile class super arguments of " + paramClassScope.getClasz().getKindName();
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public Promise<AClassBody> toClassBody(final ClassScope paramClassScope, final Option<SClassBody> paramOption)
/*      */   {
/* 1489 */     if (paramOption.isEmpty()) return Promise.value(new AClassBody());
/* 1490 */     return promise(new Task(paramClassScope) {
/*      */       public AClassBody evaluate() {
/* 1492 */         return Context.this.compileClassBody(paramClassScope, (SClassBody)paramOption.get());
/*      */       }
/*      */       public String getTask() {
/* 1495 */         return "compile class body of " + paramClassScope.getClasz().getKindName();
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public ATpTerm toSlotBound(final ASymbol paramASymbol, final Scope paramScope, final SSlotBound paramSSlotBound)
/*      */   {
/* 1509 */     return new ATpTerm.Delayed(promise(new Task(paramScope) {
/*      */       public ATpTerm evaluate() {
/* 1511 */         return Context.this.compileSlotBound(paramScope, paramSSlotBound);
/*      */       }
/*      */       public String getTask() {
/* 1514 */         return "compile bound of " + paramASymbol.getKindName();
/*      */       }
/*      */     }));
/*      */   }
/*      */ 
/*      */   public ATpTerm toSlotType(final ASymbol paramASymbol, final Scope paramScope, final SSlotType paramSSlotType)
/*      */   {
/* 1524 */     return new ATpTerm.Delayed(promise(new Task(paramScope) {
/*      */       public ATpTerm evaluate() {
/* 1526 */         return Context.this.compileSlotType(paramScope, paramSSlotType);
/*      */       }
/*      */       public String getTask() {
/* 1529 */         return "compile type of " + paramASymbol.getKindName();
/*      */       }
/*      */     }));
/*      */   }
/*      */ 
/*      */   public AVlTerm toSlotBody(final ASymbol paramASymbol, final Scope paramScope, final SSlotBody paramSSlotBody, final ATpTerm paramATpTerm)
/*      */   {
/* 1540 */     return new AVlTerm.Delayed(promise(new Task(paramScope) {
/*      */       public AVlTerm evaluate() {
/* 1542 */         return Context.this.compileSlotBody(paramScope, paramSSlotBody, paramATpTerm);
/*      */       }
/*      */       public String getTask() {
/* 1545 */         return "compile body of " + paramASymbol.getKindName();
/*      */       }
/*      */     }));
/*      */   }
/*      */ 
/*      */   public Promise<Void> toMain(final Scope paramScope, final SVlTerm paramSVlTerm)
/*      */   {
/* 1557 */     return promise(new Task(paramScope) {
/*      */       public Void evaluate() {
/* 1559 */         VlTp localVlTp = Context.this.compileVlTerm(paramScope, paramSVlTerm);
/* 1560 */         Context.this.source.setMain(Option.Some(localVlTp.vl));
/* 1561 */         return null;
/*      */       }
/*      */       public String getTask() {
/* 1564 */         return "compile main of source '" + Context.this.source.getName() + "'";
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public void error(ASymbol paramASymbol, String paramString)
/*      */   {
/* 1573 */     error(paramASymbol.getSource(), paramASymbol.getPosition(), paramString);
/*      */   }
/*      */ 
/*      */   public void error(STree paramSTree, String paramString) {
/* 1577 */     error(this.source, paramSTree.position(), paramString);
/*      */   }
/*      */ 
/*      */   public void error(int paramInt, String paramString) {
/* 1581 */     error(this.source, paramInt, paramString);
/*      */   }
/*      */ 
/*      */   public void error(Source paramSource, int paramInt, String paramString) {
/* 1585 */     assert (paramSource != null) : paramString;
/* 1586 */     paramSource.error(paramInt, paramString);
/* 1587 */     this.erroneous = true;
/*      */   }
/*      */ 
/*      */   private <Type> Promise<Type> promise(Task<Type> paramTask)
/*      */   {
/* 1594 */     return paramTask.getPromise();
/*      */   }
/*      */ 
/*      */   public abstract class Task<Type> extends pkg.util.Task<Type>
/*      */     implements Thunk<Type>
/*      */   {
                public Task(Type t) {
                    super(Promise.value(t));
                }

    /*      */     //public Task(Type t)
/*      */     //{
                 //promise = Promise.value(t);
///* 1604 */       super(t);
/*      */     //}
/*      */ 
/*      */     public abstract String getTask();
/*      */ 
/*      */     public Thunk<Type> getThunk() {
/* 1610 */       return this;
/*      */     }
/*      */ 
/*      */     public String toString() {
/* 1614 */       return getTask();
/*      */     }
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.Context
 * JD-Core Version:    0.6.2
 */