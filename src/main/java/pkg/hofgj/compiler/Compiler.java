/*     */ package pkg.hofgj.compiler;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import pkg.hofgj.Source;
/*     */ import pkg.util.Option;
/*     */ import pkg.util.Promise;
/*     */ import pkg.util.TaskManager;
/*     */ 
/*     */ public class Compiler
/*     */ {
/*     */   public static final String OBJECT = "Object";
/*     */   public static final String BOOLEAN = "Boolean";
/*     */   public static final String INTEGER = "Int";
/*     */   public static final String FALSE = "false";
/*     */   public static final String TRUE = "true";
/*     */   private final TaskManager manager;
/*     */   private final Map<String, ClassScope> classes1;
/*     */   private final Map<AClass, ClassScope> classes2;
/*     */   private final ClassScope object;
/*     */ 
/*     */   public Compiler()
/*     */   {
/*  65 */     this.manager = new TaskManager();
/*  66 */     this.classes1 = new LinkedHashMap();
/*  67 */     this.classes2 = new LinkedHashMap();
/*  68 */     Source localSource = new Source("<object>", false);
/*  69 */     Context localContext = new Context(this, this.manager, localSource);
/*  70 */     RootScope localRootScope = getRootScope(localContext);
/*  71 */     AClass localAClass = new AClass(localSource, 0, "Object");
/*  72 */     localAClass.setParams(new ATpParam[0]);
/*  73 */     localAClass.setBase(Promise.value(Option.None()));
/*     */ 
/*  75 */     localAClass.setBody(Promise.value(new AClassBody()));
/*  76 */     ClassScope localClassScope = new ClassScope(localRootScope, localAClass);
/*  77 */     this.classes1.put("Object", localClassScope);
/*  78 */     this.classes2.put(localAClass, localClassScope);
/*  79 */     this.object = ((ClassScope)lookupClass("Object").get());
/*  80 */     this.object.setSuperclass(Option.None());
/*  81 */     this.object.freeze();
/*     */   }
/*     */ 
/*     */   public void compile(Source[] paramArrayOfSource)
/*     */   {
/*  88 */     Context[] arrayOfContext1 = new Context[paramArrayOfSource.length];
/*  89 */     for (int i = 0; i < paramArrayOfSource.length; i++)
/*  90 */       arrayOfContext1[i] = new Context(this, this.manager, paramArrayOfSource[i]);
/*  91 */     for (Context localContext : arrayOfContext1) localContext.enterTasks();
/*  92 */     this.manager.loop();
/*     */   }
/*     */ 
/*     */   public RootScope getRootScope(Context paramContext)
/*     */   {
/*  99 */     return new RootScope(paramContext, this.classes1, this.classes2);
/*     */   }
/*     */ 
/*     */   public ClassScope getObjectScope() {
/* 103 */     return this.object;
/*     */   }
/*     */ 
/*     */   public AClass getObjectClass() {
/* 107 */     return getObjectScope().getClasz();
/*     */   }
/*     */ 
/*     */   public AClassBase getObjectAsBase() {
/* 111 */     return new AClassBase(getObjectClass(), Promise.value(new ACsTerm[0]));
/*     */   }
/*     */ 
/*     */   public ATpTerm getObjectAsTp() {
/* 115 */     return new ATpTerm.Concrete(getObjectClass(), new ACsTerm[0]);
/*     */   }
/*     */ 
/*     */   public ATpTerm getTp(AClass paramAClass) {
/* 119 */     return Freezer.instance.rewrite(Cloner.cloneToTp(paramAClass));
/*     */   }
/*     */ 
/*     */   public AVlTerm[] getVlTerms(VlTp[] paramArrayOfVlTp) {
/* 123 */     AVlTerm[] arrayOfAVlTerm = new AVlTerm[paramArrayOfVlTp.length];
/* 124 */     for (int i = 0; i < arrayOfAVlTerm.length; i++) arrayOfAVlTerm[i] = paramArrayOfVlTp[i].vl;
/* 125 */     return arrayOfAVlTerm;
/*     */   }
/*     */ 
/*     */   public Option<ClassScope> lookupClass(String paramString)
/*     */   {
/* 132 */     ClassScope localClassScope = (ClassScope)this.classes1.get(paramString);
/* 133 */     return localClassScope != null ? Option.Some(localClassScope) : Option.None();
/*     */   }
/*     */ 
/*     */   public ClassScope getClassScope(AClass paramAClass) {
/* 137 */     ClassScope localClassScope = (ClassScope)this.classes2.get(paramAClass);
/* 138 */     assert (localClassScope != null) : paramAClass;
/* 139 */     return localClassScope;
/*     */   }
/*     */ 
/*     */   public AClass[] getAllClasses() {
/* 143 */     return (AClass[])this.classes2.keySet().toArray(new AClass[this.classes2.size()]);
/*     */   }
/*     */ 
/*     */   public ATpTerm apply(ACsTerm paramACsTerm, ACsTerm[] paramArrayOfACsTerm)
/*     */   {
/* 150 */     //switch (1.$SwitchMap$pkg$hofgj$compiler$ACsTerm$Kind[paramACsTerm.getKind().ordinal()]) {
              switch (paramACsTerm.getKind().ordinal()) {
/*     */     case 1:
/* 152 */       ACsTerm.Function localFunction = (ACsTerm.Function)paramACsTerm;
/* 153 */       return apply(localFunction.params, paramArrayOfACsTerm, localFunction.body);
/*     */     case 2:
/* 156 */       return new ATpTerm.Error();
/*     */     case 3:
/* 158 */       return apply(paramACsTerm.evaluate(), paramArrayOfACsTerm);
/*     */     }
/* 160 */     throw new Error("" + paramACsTerm.getKind());
/*     */   }
/*     */ 
/*     */   public ATpTerm apply(ATpParam[] paramArrayOfATpParam, ACsTerm[] paramArrayOfACsTerm, ATpTerm paramATpTerm)
/*     */   {
/* 165 */     return Freezer.instance.rewrite(Cloner.cloneTp(paramArrayOfATpParam, paramArrayOfACsTerm, paramATpTerm));
/*     */   }
/*     */ 
/*     */   public Option<ATpTerm> getUpperBound(ATpTerm paramATpTerm)
/*     */   {
/*     */     Object localObject1;
/*     */     Object localObject2;
/* 169 */     //switch (1.$SwitchMap$pkg$hofgj$compiler$ATpTerm$Kind[((ATpTerm.Kind)paramATpTerm.getKind()).ordinal()]) {
              switch (((ATpTerm.Kind)paramATpTerm.getKind()).ordinal()) {
/*     */     case 1:
/* 171 */       localObject1 = (ATpTerm.Boolean)paramATpTerm;
/* 172 */       return Option.Some(getObjectAsTp());
/*     */     case 2:
/* 175 */       localObject1 = (ATpTerm.Integer)paramATpTerm;
/* 176 */       return Option.Some(getObjectAsTp());
/*     */     case 3:
/* 179 */       localObject1 = (ATpTerm.Concrete)paramATpTerm;
/* 180 */       localObject2 = ((ATpTerm.Concrete)localObject1).clasz.getBase();
/* 181 */       if (((Option)localObject2).isEmpty()) return Option.None();
/* 182 */       ATpTerm localATpTerm = apply(((ATpTerm.Concrete)localObject1).clasz.getParams(), ((ATpTerm.Concrete)localObject1).args, ((AClassBase)((Option)localObject2).get()).getBaseType());
/*     */ 
/* 184 */       return Option.Some(localATpTerm);
/*     */     case 4:
/* 187 */       localObject1 = (ATpTerm.Abstract)paramATpTerm;
/* 188 */       localObject2 = apply(((ATpTerm.Abstract)localObject1).param.getParams(), ((ATpTerm.Abstract)localObject1).args, ((ATpTerm.Abstract)localObject1).param.getBound());
/*     */ 
/* 190 */       return Option.Some((ATpTerm) localObject2);
/*     */     case 5:
/* 193 */       localObject1 = (ATpTerm.Error)paramATpTerm;
/* 194 */       return Option.None();
/*     */     case 6:
/* 197 */       localObject1 = (ATpTerm.Delayed)paramATpTerm;
/* 198 */       return getUpperBound((ATpTerm)((ATpTerm.Delayed)localObject1).promise.force());
/*     */     }
/*     */ 
/* 201 */     throw new Error("" + paramATpTerm.getKind());
/*     */   }
/*     */ 
/*     */   public Option<ATpTerm> getUpperClass(ATpTerm paramATpTerm)
/*     */   {
/* 206 */     //switch (1.$SwitchMap$pkg$hofgj$compiler$ATpTerm$Kind[((ATpTerm.Kind)paramATpTerm.getKind()).ordinal()]) {
              switch (((ATpTerm.Kind)paramATpTerm.getKind()).ordinal()) {
              case 3:
/* 207 */       return Option.Some(paramATpTerm);
/*     */     case 5:
/* 208 */       return Option.Some(paramATpTerm);
/*     */     case 6:
/* 209 */       return getUpperClass(paramATpTerm.evaluate());
/*     */     case 4: }
/* 211 */     Option localOption = getUpperBound(paramATpTerm);
/* 212 */     if (localOption.isEmpty()) return Option.None();
/* 213 */     return getUpperClass((ATpTerm)localOption.get());
/*     */   }
/*     */ 
/*     */   public ACsTerm[] getClassArgsFrom(AClass paramAClass, ATpTerm paramATpTerm) {
/* 217 */     switch ((ATpTerm.Kind)paramATpTerm.getKind()) {
/*     */     case DELAYED:
/* 219 */       return getClassArgsFrom(paramAClass, paramATpTerm.evaluate());
/*     */     case CONCRETE:
/* 221 */       ATpTerm localObject = (ATpTerm.Concrete)paramATpTerm;
/* 222 */       if (((ATpTerm.Concrete)localObject).clasz == paramAClass) return ((ATpTerm.Concrete)localObject).args; break;
/*     */     }
/* 224 */     Object localObject = getUpperBound(paramATpTerm);
/* 225 */     if (((Option)localObject).isEmpty()) throw new Error("" + paramATpTerm);
/* 226 */     return getClassArgsFrom(paramAClass, (ATpTerm)((Option)localObject).get());
/*     */   }
/*     */ 
/*     */   public ATpTerm asSeenFrom(AClass paramAClass, ATpTerm paramATpTerm1, ATpTerm paramATpTerm2) {
/* 230 */     return apply(paramAClass.getParams(), getClassArgsFrom(paramAClass, paramATpTerm2), paramATpTerm1);
/*     */   }
/*     */ 
/*     */   public boolean isSubtypeOf(ATpTerm paramATpTerm1, ATpTerm paramATpTerm2, boolean paramBoolean) {
/* 234 */     if ((paramATpTerm1 = paramATpTerm1.evaluate()).isError()) return paramBoolean;
/* 235 */     if ((paramATpTerm2 = paramATpTerm2.evaluate()).isError()) return paramBoolean;
/*     */     Object localObject2;
/* 237 */     //switch (1.$SwitchMap$pkg$hofgj$compiler$ATpTerm$Kind[((ATpTerm.Kind)paramATpTerm2.getKind()).ordinal()]) {
              switch (((ATpTerm.Kind)paramATpTerm2.getKind()).ordinal()) {
/*     */     case 1:
/* 239 */       ATpTerm localObject1 = (ATpTerm.Boolean)paramATpTerm2;
/* 240 */       switch ((ATpTerm.Kind)paramATpTerm1.getKind()) {
/*     */       case BOOLEAN:
/* 242 */         localObject2 = (ATpTerm.Boolean)paramATpTerm1;
/* 243 */         return true;
/*     */       }
/*     */ 
/* 246 */       break;
/*     */     case 2:
/* 250 */       localObject1 = (ATpTerm.Integer)paramATpTerm2;
/* 251 */       switch ((ATpTerm.Kind)paramATpTerm1.getKind()) {
/*     */       case INTEGER:
/* 253 */         localObject2 = (ATpTerm.Integer)paramATpTerm1;
/* 254 */         return true;
/*     */       }
/*     */ 
/* 257 */       break;
/*     */     case 3:
/* 261 */       localObject1 = (ATpTerm.Concrete)paramATpTerm2;
/* 262 */       switch ((ATpTerm.Kind)paramATpTerm1.getKind()) {
/*     */       case CONCRETE:
/* 264 */         localObject2 = (ATpTerm.Concrete)paramATpTerm1;
/* 265 */         if (((ATpTerm.Concrete)localObject2).clasz == ((ATpTerm.Concrete)localObject1).clasz) {
/* 266 */           return areEqual(((ATpTerm.Concrete)localObject2).args, ((ATpTerm.Concrete)localObject1).args, paramBoolean);
/*     */         }
/*     */         break;
/*     */       }
/* 270 */       break;
/*     */     case 4:
/* 274 */       localObject1 = (ATpTerm.Abstract)paramATpTerm2;
/* 275 */       switch ((ATpTerm.Kind)paramATpTerm1.getKind()) {
/*     */       case ABSTRACT:
/* 277 */         localObject2 = (ATpTerm.Abstract)paramATpTerm1;
/* 278 */         if (((ATpTerm.Abstract)localObject2).param == ((ATpTerm.Abstract)localObject1).param) {
/* 279 */           return areEqual(((ATpTerm.Abstract)localObject2).args, ((ATpTerm.Abstract)localObject1).args, paramBoolean);
/*     */         }
/*     */         break;
/*     */       }
/* 283 */       break;
/*     */     default:
/* 287 */       throw new Error("" + paramATpTerm2.getKind());
/*     */     }
/* 289 */     Object localObject1 = getUpperBound(paramATpTerm1);
/* 290 */     if (((Option)localObject1).isEmpty()) return false;
/* 291 */     return isSubtypeOf((ATpTerm)((Option)localObject1).get(), paramATpTerm2, paramBoolean);
/*     */   }
/*     */ 
/*     */   public boolean areEqual(ACsTerm[] paramArrayOfACsTerm1, ACsTerm[] paramArrayOfACsTerm2, boolean paramBoolean) {
/* 295 */     return EqualityTester.areEqual(this, paramArrayOfACsTerm1, paramArrayOfACsTerm2, paramBoolean);
/*     */   }
/*     */ 
/*     */   public String toString(AClass[] paramArrayOfAClass)
/*     */   {
/* 302 */     StringBuilder localStringBuilder = new StringBuilder();
/* 303 */     for (int i = 0; i < paramArrayOfAClass.length; i++) {
/* 304 */       localStringBuilder.append(toString(paramArrayOfAClass[i]));
/*     */     }
/* 306 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public String toString(AClass paramAClass) {
/* 310 */     return "\nclass " + paramAClass.getName() + "[" + toString(paramAClass.getParams()) + "]" + (paramAClass.getBase().isEmpty() ? "" : new StringBuilder().append(" extends ").append(toString(((AClassBase)paramAClass.getBase().get()).getBaseType())).toString()) + " {" + indent(toString(paramAClass.getBody().fields)) + indent(toString(paramAClass.getBody().methods)) + "\n}" + "\n";
/*     */   }
/*     */ 
/*     */   public String toString(AField[] paramArrayOfAField)
/*     */   {
/* 323 */     StringBuilder localStringBuilder = new StringBuilder();
/* 324 */     for (int i = 0; i < paramArrayOfAField.length; i++) {
/* 325 */       localStringBuilder.append(toString(paramArrayOfAField[i]));
/*     */     }
/* 327 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public String toString(AField paramAField) {
/* 331 */     return "\nval " + paramAField.getName() + ": " + toString(paramAField.getType());
/*     */   }
/*     */ 
/*     */   public String toString(AMethod[] paramArrayOfAMethod) {
/* 335 */     StringBuilder localStringBuilder = new StringBuilder();
/* 336 */     for (int i = 0; i < paramArrayOfAMethod.length; i++) {
/* 337 */       localStringBuilder.append(toString(paramArrayOfAMethod[i]));
/*     */     }
/* 339 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public String toString(AMethod paramAMethod) {
/* 343 */     return "\ndef " + paramAMethod.getName() + "[" + toString(paramAMethod.getTpParams()) + "]" + "(" + toString(paramAMethod.getVlParams()) + ")" + ": " + toString(paramAMethod.getType()) + (paramAMethod.getBody().isEmpty() ? "" : new StringBuilder().append(" = ").append(toString((AVlTerm)paramAMethod.getBody().get())).toString());
/*     */   }
/*     */ 
/*     */   public String toString(ATpParam[] paramArrayOfATpParam)
/*     */   {
/* 353 */     StringBuilder localStringBuilder = new StringBuilder();
/* 354 */     for (int i = 0; i < paramArrayOfATpParam.length; i++) {
/* 355 */       if (i > 0) localStringBuilder.append(", ");
/* 356 */       localStringBuilder.append(toString(paramArrayOfATpParam[i]));
/*     */     }
/* 358 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public String toString(ATpParam paramATpParam) {
/* 362 */     return paramATpParam.getName() + "[" + toString(paramATpParam.getParams()) + "] <: " + toString(paramATpParam.getBound());
/*     */   }
/*     */ 
/*     */   public String toString(AVlParam[] paramArrayOfAVlParam)
/*     */   {
/* 367 */     StringBuilder localStringBuilder = new StringBuilder();
/* 368 */     for (int i = 0; i < paramArrayOfAVlParam.length; i++) {
/* 369 */       if (i > 0) localStringBuilder.append(", ");
/* 370 */       localStringBuilder.append(toString(paramArrayOfAVlParam[i]));
/*     */     }
/* 372 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public String toString(AVlParam paramAVlParam) {
/* 376 */     return paramAVlParam.getName() + ": " + toString(paramAVlParam.getType());
/*     */   }
/*     */ 
/*     */   public String toString(ACsTerm[] paramArrayOfACsTerm) {
/* 380 */     StringBuilder localStringBuilder = new StringBuilder();
/* 381 */     for (int i = 0; i < paramArrayOfACsTerm.length; i++) {
/* 382 */       if (i > 0) localStringBuilder.append(", ");
/* 383 */       localStringBuilder.append(toString(paramArrayOfACsTerm[i]));
/*     */     }
/* 385 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public String toString(ACsTerm paramACsTerm) {
/* 389 */     //switch (1.$SwitchMap$pkg$hofgj$compiler$ACsTerm$Kind[paramACsTerm.getKind().ordinal()]) {
              switch (paramACsTerm.getKind().ordinal()) {
              case 1:
/* 390 */       return toString((ACsTerm.Function)paramACsTerm);
/*     */     case 2:
/* 391 */       return "<error>";
/*     */     case 3:
/* 392 */       return toString(paramACsTerm.evaluate()); }
/* 393 */     throw new Error("" + paramACsTerm.getKind());
/*     */   }
/*     */ 
/*     */   public String toString(ACsTerm.Function paramFunction)
/*     */   {
/* 398 */     return "[" + toString(paramFunction.params) + "] => " + toString(paramFunction.body);
/*     */   }
/*     */ 
/*     */   public String toString(ATpTerm[] paramArrayOfATpTerm) {
/* 402 */     StringBuilder localStringBuilder = new StringBuilder();
/* 403 */     for (int i = 0; i < paramArrayOfATpTerm.length; i++) {
/* 404 */       if (i > 0) localStringBuilder.append(", ");
/* 405 */       localStringBuilder.append(toString(paramArrayOfATpTerm[i]));
/*     */     }
/* 407 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public String toString(ATpTerm paramATpTerm) {
/* 411 */     //switch (1.$SwitchMap$pkg$hofgj$compiler$ATpTerm$Kind[((ATpTerm.Kind)paramATpTerm.getKind()).ordinal()]) {
              switch (((ATpTerm.Kind)paramATpTerm.getKind()).ordinal()) {
              case 1:
/* 412 */       return "Boolean";
/*     */     case 2:
/* 413 */       return "Int";
/*     */     case 3:
/* 414 */       return toString((ATpTerm.Concrete)paramATpTerm);
/*     */     case 4:
/* 415 */       return toString((ATpTerm.Abstract)paramATpTerm);
/*     */     case 5:
/* 416 */       return "<error>";
/*     */     case 6:
/* 417 */       return toString(paramATpTerm.evaluate()); }
/* 418 */     throw new Error("" + paramATpTerm.getKind());
/*     */   }
/*     */ 
/*     */   public String toString(ATpTerm.Concrete paramConcrete)
/*     */   {
/* 423 */     return paramConcrete.clasz.getName() + "[" + toString(paramConcrete.args) + "]";
/*     */   }
/*     */ 
/*     */   public String toString(ATpTerm.Abstract paramAbstract) {
/* 427 */     return paramAbstract.param.getName() + "[" + toString(paramAbstract.args) + "]";
/*     */   }
/*     */ 
/*     */   public String toString(AVlTerm[] paramArrayOfAVlTerm) {
/* 431 */     StringBuilder localStringBuilder = new StringBuilder();
/* 432 */     for (int i = 0; i < paramArrayOfAVlTerm.length; i++) {
/* 433 */       if (i > 0) localStringBuilder.append(", ");
/* 434 */       localStringBuilder.append(toString(paramArrayOfAVlTerm[i]));
/*     */     }
/* 436 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public String toString(AVlTerm paramAVlTerm) {
/* 440 */     //switch (1.$SwitchMap$pkg$hofgj$compiler$AVlTerm$Kind[paramAVlTerm.getKind().ordinal()]) {
              switch (paramAVlTerm.getKind().ordinal()) {
              case 1:
/* 441 */       return toString((AVlTerm.Boolean)paramAVlTerm);
/*     */     case 2:
/* 442 */       return toString((AVlTerm.Integer)paramAVlTerm);
/*     */     case 3:
/* 443 */       return "this";
/*     */     case 4:
/* 444 */       return toString((AVlTerm.Local)paramAVlTerm);
/*     */     case 5:
/* 445 */       return toString((AVlTerm.Get)paramAVlTerm);
/*     */     case 6:
/* 446 */       return toString((AVlTerm.Call)paramAVlTerm);
/*     */     case 7:
/* 447 */       return toString((AVlTerm.New)paramAVlTerm);
/*     */     case 8:
/* 448 */       return toString((AVlTerm.If)paramAVlTerm);
/*     */     case 9:
/* 449 */       return toString((AVlTerm.Unary)paramAVlTerm);
/*     */     case 10:
/* 450 */       return toString((AVlTerm.Binary)paramAVlTerm);
/*     */     case 11:
/* 451 */       return "<error>";
/*     */     case 12:
/* 452 */       return toString(paramAVlTerm.evaluate()); }
/* 453 */     throw new Error("" + paramAVlTerm.getKind());
/*     */   }
/*     */ 
/*     */   public String toString(AVlTerm.Boolean paramBoolean)
/*     */   {
/* 458 */     return String.valueOf(paramBoolean.value);
/*     */   }
/*     */ 
/*     */   public String toString(AVlTerm.Integer paramInteger) {
/* 462 */     return String.valueOf(paramInteger.value);
/*     */   }
/*     */ 
/*     */   public String toString(AVlTerm.Local paramLocal) {
/* 466 */     return paramLocal.param.getName();
/*     */   }
/*     */ 
/*     */   public String toString(AVlTerm.Get paramGet) {
/* 470 */     return toString(paramGet.object) + "." + paramGet.field.getName();
/*     */   }
/*     */ 
/*     */   public String toString(AVlTerm.Call paramCall) {
/* 474 */     return toString(paramCall.object) + "." + paramCall.method.getName() + "[" + toString(paramCall.targs) + "]" + "(" + toString(paramCall.vargs) + ")";
/*     */   }
/*     */ 
/*     */   public String toString(AVlTerm.New paramNew)
/*     */   {
/* 480 */     return "new " + paramNew.clasz.getName() + "[" + toString(paramNew.targs) + "]" + "(" + toString(paramNew.vargs) + ")";
/*     */   }
/*     */ 
/*     */   public String toString(AVlTerm.If paramIf)
/*     */   {
/* 486 */     return "if (" + toString(paramIf.test) + ")" + indent(new StringBuilder().append("\n").append(toString(paramIf.success)).toString()) + "\nelse" + indent(new StringBuilder().append("\n").append(toString(paramIf.failure)).toString());
/*     */   }
/*     */ 
/*     */   public String toString(AVlTerm.Unary paramUnary)
/*     */   {
/* 493 */     return "(" + toString(paramUnary.operator) + toString(paramUnary.arg) + ")";
/*     */   }
/*     */ 
/*     */   public String toString(AVlTerm.Binary paramBinary) {
/* 497 */     return "(" + toString(paramBinary.argl) + " " + toString(paramBinary.operator) + " " + toString(paramBinary.argr) + ")";
/*     */   }
/*     */ 
/*     */   public String toString(AVlTerm.UnaryOperator paramUnaryOperator)
/*     */   {
/* 504 */     //switch (1.$SwitchMap$pkg$hofgj$compiler$AVlTerm$UnaryOperator[paramUnaryOperator.ordinal()]) {
              switch (paramUnaryOperator.ordinal()) {
              case 1:
/* 505 */       return "+";
/*     */     case 2:
/* 506 */       return "-";
/*     */     case 3:
/* 507 */       return "~";
/*     */     case 4:
/* 508 */       return "!"; }
/* 509 */     throw new Error("" + paramUnaryOperator);
/*     */   }
/*     */ 
/*     */   public String toString(AVlTerm.BinaryOperator paramBinaryOperator)
/*     */   {
/* 514 */     //switch (1.$SwitchMap$pkg$hofgj$compiler$AVlTerm$BinaryOperator[paramBinaryOperator.ordinal()]) {
              switch (paramBinaryOperator.ordinal()) {
              case 1:
/* 515 */       return "+";
/*     */     case 2:
/* 516 */       return "-";
/*     */     case 3:
/* 517 */       return "*";
/*     */     case 4:
/* 518 */       return "/";
/*     */     case 5:
/* 519 */       return "%";
/*     */     case 6:
/* 520 */       return "<<";
/*     */     case 7:
/* 521 */       return ">>>";
/*     */     case 8:
/* 522 */       return ">>";
/*     */     case 9:
/* 523 */       return "<";
/*     */     case 10:
/* 524 */       return ">=";
/*     */     case 11:
/* 525 */       return "<=";
/*     */     case 12:
/* 526 */       return ">";
/*     */     case 13:
/* 527 */       return "==";
/*     */     case 14:
/* 528 */       return "!=";
/*     */     case 15:
/* 529 */       return "|";
/*     */     case 16:
/* 530 */       return "^";
/*     */     case 17:
/* 531 */       return "&";
/*     */     case 18:
/* 532 */       return "||";
/*     */     case 19:
/* 533 */       return "^^";
/*     */     case 20:
/* 534 */       return "&&"; }
/* 535 */     throw new Error("" + paramBinaryOperator);
/*     */   }
/*     */ 
/*     */   public String toString(Value[] paramArrayOfValue)
/*     */   {
/* 540 */     StringBuilder localStringBuilder = new StringBuilder();
/* 541 */     for (int i = 0; i < paramArrayOfValue.length; i++) {
/* 542 */       if (i > 0) localStringBuilder.append(", ");
/* 543 */       localStringBuilder.append(toString(paramArrayOfValue[i]));
/*     */     }
/* 545 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public String toString(Value paramValue) {
/* 549 */     //switch (1.$SwitchMap$pkg$hofgj$compiler$Value$Kind[paramValue.getKind().ordinal()]) {
              switch (paramValue.getKind().ordinal()) {
              case 1:
/* 550 */       return toString((Value.Object)paramValue);
/*     */     case 2:
/* 551 */       return toString((Value.Boolean)paramValue);
/*     */     case 3:
/* 552 */       return toString((Value.Integer)paramValue); }
/* 553 */     throw new Error("" + paramValue.getKind());
/*     */   }
/*     */ 
/*     */   public String toString(Value.Object paramObject)
/*     */   {
/* 558 */     return paramObject.clasz.getName() + "[" + toString(paramObject.targs) + "]" + "(" + toString(paramObject.vargs) + ")";
/*     */   }
/*     */ 
/*     */   public String toString(Value.Boolean paramBoolean)
/*     */   {
/* 564 */     return String.valueOf(paramBoolean.value);
/*     */   }
/*     */ 
/*     */   public String toString(Value.Integer paramInteger) {
/* 568 */     return String.valueOf(paramInteger.value);
/*     */   }
/*     */ 
/*     */   public String indent(String paramString) {
/* 572 */     return paramString.replaceAll("\n", "\n  ");
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.Compiler
 * JD-Core Version:    0.6.2
 */