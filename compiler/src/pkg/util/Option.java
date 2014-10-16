/*     */ package pkg.util;
/*     */ 
/*     */ import pkg.debug.Debug;
/*     */ 
/*     */ public abstract class Option<Type>
/*     */   implements Union<Option<Type>, Kind>
/*     */ {
/*     */   public static <X> Function1<X, Option<X>> Some()
/*     */   {
/*  11 */     return new Function1() {
/*  12 */       public Option<X> apply(X paramAnonymousX) { return Option.Some(paramAnonymousX); }
/*     */ 
/*     */     };
/*     */   }
/*     */ 
/*     */   public static <Type> Option<Type> from(Type[] paramArrayOfType)
/*     */   {
/*  20 */     return from(paramArrayOfType, 0);
/*     */   }
/*     */ 
/*     */   public static <Type> Option<Type> from(Type[] paramArrayOfType, int paramInt) {
/*  24 */     return paramInt < paramArrayOfType.length ? Some(paramArrayOfType[paramInt]) : None();
/*     */   }
/*     */ 
/*     */   public static <Type> Option<Type> from(boolean paramBoolean, Type paramType)
/*     */   {
/*  30 */     return paramBoolean ? Some(paramType) : None();
/*     */   }
/*     */ 
/*     */   public static <Base, Type extends Base> Option<Type> from(Function1<Base, Boolean> paramFunction1, Type paramType)
/*     */   {
/*  36 */     return from(((Boolean)paramFunction1.apply(paramType)).booleanValue(), paramType);
/*     */   }
/*     */ 
/*     */   public static <Type> None<Type> None() {
/*  40 */     return new None(null);
/*     */   }
/*     */ 
/*     */   public static <Type> Some<Type> Some(Type paramType) {
/*  44 */     return new Some(paramType, null);
/*     */   }
/*     */ 
/*     */   public static <Type> boolean areEqual(Option<Type> paramOption1, Option<Type> paramOption2)
/*     */   {
/*  51 */     if (paramOption1 == paramOption2) return true;
/*  52 */     if (paramOption1 == null) return false;
/*  53 */     if (paramOption2 == null) return false;
/*  54 */     Kind localKind1 = (Kind)paramOption1.getKind();
/*  55 */     Kind localKind2 = (Kind)paramOption2.getKind();
/*  56 */     if (localKind1 != localKind2) return false;
/*  57 */     switch (2.$SwitchMap$pkg$util$Option$Kind[localKind1.ordinal()]) {
/*     */     case 1:
/*  59 */       return true;
/*     */     case 2:
/*  61 */       return Util.areEqual(paramOption1.value(), paramOption2.value());
/*     */     }
/*  63 */     throw Debug.unexpected(paramOption1);
/*     */   }
/*     */ 
/*     */   public final boolean isEmpty()
/*     */   {
/*  76 */     switch (2.$SwitchMap$pkg$util$Option$Kind[((Kind)getKind()).ordinal()]) { case 1:
/*  77 */       return true;
/*     */     case 2:
/*  78 */       return false; }
/*  79 */     throw Debug.unexpected(this);
/*     */   }
/*     */ 
/*     */   public final Type value()
/*     */   {
/*  84 */     switch (2.$SwitchMap$pkg$util$Option$Kind[((Kind)getKind()).ordinal()]) { case 1:
/*  85 */       throw Debug.illegal(this, "value", new Object[0]);
/*     */     case 2:
/*  86 */       return ((Some)this).value; }
/*  87 */     throw Debug.unexpected(this);
/*     */   }
/*     */ 
/*     */   public final Option<Type> add(Type paramType)
/*     */   {
/*  92 */     return Some(paramType);
/*     */   }
/*     */ 
/*     */   public final int size() {
/*  96 */     switch (2.$SwitchMap$pkg$util$Option$Kind[((Kind)getKind()).ordinal()]) { case 1:
/*  97 */       return 0;
/*     */     case 2:
/*  98 */       return 1; }
/*  99 */     throw Debug.unexpected(this);
/*     */   }
/*     */ 
/*     */   public final Type get()
/*     */   {
/* 104 */     switch (2.$SwitchMap$pkg$util$Option$Kind[((Kind)getKind()).ordinal()]) { case 1:
/* 105 */       throw Debug.illegal(this, "get", new Object[0]);
/*     */     case 2:
/* 106 */       return value(); }
/* 107 */     throw Debug.unexpected(this);
/*     */   }
/*     */ 
/*     */   public final Type get(Type paramType)
/*     */   {
/* 112 */     switch (2.$SwitchMap$pkg$util$Option$Kind[((Kind)getKind()).ordinal()]) { case 1:
/* 113 */       return paramType;
/*     */     case 2:
/* 114 */       return value(); }
/* 115 */     throw Debug.unexpected(this);
/*     */   }
/*     */ 
/*     */   public final Type[] toArray(Type[] paramArrayOfType)
/*     */   {
/* 120 */     switch (2.$SwitchMap$pkg$util$Option$Kind[((Kind)getKind()).ordinal()]) { case 1:
/* 121 */       return paramArrayOfType;
/*     */     case 2:
/* 122 */       paramArrayOfType[0] = value(); return paramArrayOfType; }
/* 123 */     throw Debug.unexpected(this);
/*     */   }
/*     */ 
/*     */   public boolean equals(Option<Type> paramOption)
/*     */   {
/* 128 */     return areEqual(this, paramOption);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 133 */     return ((paramObject instanceof Option)) && (areEqual(this, (Option)paramObject));
/*     */   }
/*     */ 
/*     */   public final String toString() {
/* 137 */     switch (2.$SwitchMap$pkg$util$Option$Kind[((Kind)getKind()).ordinal()]) { case 1:
/* 138 */       return "Option()";
/*     */     case 2:
/* 139 */       return "Option(" + value() + ")"; }
/* 140 */     throw Debug.unexpected(this);
/*     */   }
/*     */ 
/*     */   public static class Some<Type> extends Option<Type>
/*     */   {
/*     */     public final Type value;
/*     */ 
/*     */     private Some(Type paramType)
/*     */     {
/* 163 */       this.value = paramType;
/*     */     }
/*     */ 
/*     */     public Option.Kind getKind() {
/* 167 */       return Option.Kind.SOME;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class None<Type> extends Option<Type>
/*     */   {
/*     */     public Option.Kind getKind()
/*     */     {
/* 153 */       return Option.Kind.NONE;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum Kind
/*     */   {
/*  70 */     NONE, SOME;
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.util.Option
 * JD-Core Version:    0.6.2
 */