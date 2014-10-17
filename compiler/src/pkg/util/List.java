/*     */ package pkg.util;
/*     */ 
/*     */ import pkg.debug.Debug;
/*     */ 
/*     */ public abstract class List<Type>
/*     */   implements Union<List<Type>, List.Kind>
/*     */ {
/*     */   public static <Type> Nil<Type> Nil()
/*     */   {
/*  11 */     return new Nil();
/*     */   }
/*     */ 
/*     */   public static <Type> Cons<Type> Cons(Type paramType, List<Type> paramList) {
/*  15 */     return new Cons(paramType, paramList);
/*     */   }
/*     */ 
/*     */   public static <Type> List<Type> mk(Type[] paramArrayOfType) {
/*  19 */     Nil localNil = Nil();
/*  20 */     for (int i = paramArrayOfType.length - 1; i >= 0; i--) localNil.add(paramArrayOfType[i]);
/*  21 */     return localNil;
/*     */   }
/*     */ 
/*     */   public static <Type> boolean areEqual(List<Type> paramList1, List<Type> paramList2)
/*     */   {
/*  28 */     if (paramList1 == paramList2) return true;
/*  29 */     if (paramList1 == null) return false;
/*  30 */     if (paramList2 == null) return false; do
/*     */     {
/*  32 */       Kind localKind1 = (Kind)paramList1.getKind();
/*  33 */       Kind localKind2 = (Kind)paramList2.getKind();
/*  34 */       if (localKind1 != localKind2) return false;
/*  35 */       //switch (1.$SwitchMap$pkg$util$List$Kind[localKind1.ordinal()]) {
                switch (localKind1.ordinal()) {
/*     */       case 1:
/*  37 */         return true;
/*     */       case 2:
/*  39 */         if (!Util.areEqual(paramList1.head(), paramList2.head())) return false;
/*  40 */         paramList1 = paramList1.tail();
/*  41 */         paramList2 = paramList2.tail(); } 
/*  42 */     } while (paramList1 != paramList2);
            return true;
/*     */ 
///*  44 */     throw Debug.unexpected(paramList1);
/*     */   }
/*     */ 
/*     */   public final boolean isEmpty()
/*     */   {
/*  58 */     //switch (1.$SwitchMap$pkg$util$List$Kind[((Kind)getKind()).ordinal()]) {
              switch (((Kind)getKind()).ordinal()) {
              case 1:
/*  59 */       return true;
/*     */     case 2:
/*  60 */       return false; }
/*  61 */     throw Debug.unexpected(this);
/*     */   }
/*     */ 
/*     */   public final Type head()
/*     */   {
/*  66 */     //switch (1.$SwitchMap$pkg$util$List$Kind[((Kind)getKind()).ordinal()]) {
              switch (((Kind)getKind()).ordinal()) {
              case 1:
/*  67 */       throw Debug.illegal(this, "head", new Object[0]);
/*     */     case 2:
/*  68 */       return ((Cons<Type>)this).head; }
/*  69 */     throw Debug.unexpected(this);
/*     */   }
/*     */ 
/*     */   public final List<Type> tail()
/*     */   {
/*  74 */     //switch (1.$SwitchMap$pkg$util$List$Kind[((Kind)getKind()).ordinal()]) {
              switch (((Kind)getKind()).ordinal()) {
              case 1:
/*  75 */       throw Debug.illegal(this, "tail", new Object[0]);
/*     */     case 2:
/*  76 */       return ((Cons)this).tail; }
/*  77 */     throw Debug.unexpected(this);
/*     */   }
/*     */ 
/*     */   public final List<Type> add(Type paramType)
/*     */   {
/*  82 */     return Cons(paramType, this);
/*     */   }
/*     */ 
/*     */   public final int size() {
/*  86 */     int i = 0;
/*  87 */     for (List localList = this; ; localList = localList.tail())
/*  88 */       //switch (1.$SwitchMap$pkg$util$List$Kind[((Kind)localList.getKind()).ordinal()]) {
                switch (((Kind)localList.getKind()).ordinal()) {
                case 1:
/*  89 */         return i;
/*     */       case 2:
/*  90 */         i++; break;
/*     */       default:
/*  91 */         throw Debug.unexpected(localList);
/*     */       }
/*     */   }
/*     */ 
/*     */   public final List<Type> reverse()
/*     */   {
/*  97 */     List<Type> localObject = Nil();
/*  98 */     for (List localList = this; ; localList = localList.tail())
/*  99 */       //switch (1.$SwitchMap$pkg$util$List$Kind[((Kind)localList.getKind()).ordinal()]) {
                switch (((Kind)localList.getKind()).ordinal()) {
                case 1:
/* 100 */         return localObject;
/*     */       case 2:
/* 101 */         localObject = ((List)localObject).add(((List)localObject).head()); break;
/*     */       default:
/* 102 */         throw Debug.unexpected(localList);
/*     */       }
/*     */   }
/*     */ 
/*     */   public final List<Type> drop(int paramInt)
/*     */   {
/* 108 */     int i = paramInt;
/* 109 */     for (List localList = this; ; i--) {
/* 110 */       if (i == 0) return localList;
/* 111 */       //switch (1.$SwitchMap$pkg$util$List$Kind[((Kind)localList.getKind()).ordinal()]) {
                switch (((Kind)localList.getKind()).ordinal()) {
                case 1:
/* 112 */         throw Debug.illegal(this, "drop", new Object[] { Integer.valueOf(paramInt) });
/*     */       case 2:
/* 113 */         break;
/*     */       default:
/* 114 */         throw Debug.unexpected(localList);
/*     */       }
/* 109 */       localList = localList.tail();
/*     */     }
/*     */   }
/*     */ 
/*     */   public final Type at(int paramInt)
/*     */   {
/* 120 */     int i = paramInt;
/* 121 */     for (List<Type> localList = this; ; i--) {
/* 122 */       //switch (1.$SwitchMap$pkg$util$List$Kind[((Kind)localList.getKind()).ordinal()]) {
                switch (((Kind)localList.getKind()).ordinal()) {
                case 1:
/* 123 */         throw Debug.illegal(this, "at", new Object[] { Integer.valueOf(paramInt) });
/*     */       case 2:
/* 124 */         if (i == 0) return localList.head(); break;
/*     */       default:
/* 125 */         throw Debug.unexpected(localList);
/*     */       }
/* 121 */       localList = localList.tail();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean equals(List<Type> paramList)
/*     */   {
/* 131 */     return areEqual(this, paramList);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 136 */     return ((paramObject instanceof List)) && (areEqual(this, (List)paramObject));
/*     */   }
/*     */ 
/*     */   public final String toString() {
/* 140 */     StringBuilder localStringBuilder = new StringBuilder("List(");
/* 141 */     int i = 0;
/* 142 */     for (List localList = this; ; localList = localList.tail()) {
/* 143 */       if (i != 0) localStringBuilder.append(", "); else i = 1;
/* 144 */       //switch (1.$SwitchMap$pkg$util$List$Kind[((Kind)localList.getKind()).ordinal()]) {
                switch (((Kind)localList.getKind()).ordinal()) {
                case 1:
/* 145 */         return ")";
/*     */       case 2:
/* 146 */         localStringBuilder.append(localList.head()); break;
/*     */       default:
/* 147 */         throw Debug.unexpected(localList);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Cons<Type> extends List<Type>
/*     */   {
/*     */     public final Type head;
/*     */     public final List<Type> tail;
/*     */ 
/*     */     private Cons(Type paramType, List<Type> paramList)
/*     */     {
/* 172 */       assert (paramList != null);
/* 173 */       this.head = paramType;
/* 174 */       this.tail = paramList;
/*     */     }
/*     */ 
/*     */     public List.Kind getKind() {
/* 178 */       return List.Kind.CONS;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Nil<Type> extends List<Type>
/*     */   {
/*     */     public List.Kind getKind()
/*     */     {
/* 161 */       return List.Kind.NIL;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum Kind
/*     */   {
/*  52 */     NIL, CONS;
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.util.List
 * JD-Core Version:    0.6.2
 */