/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ abstract class Value
/*      */ {
/*      */   public abstract Kind getKind();
/*      */ 
/*      */   public static class Integer extends Value
/*      */   {
/*      */     public final int value;
/*      */ 
/*      */     public Integer(int paramInt)
/*      */     {
/* 1660 */       this.value = paramInt;
/*      */     }
/* 1662 */     public Value.Kind getKind() { return Value.Kind.INTEGER; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public static class Boolean extends Value
/*      */   {
/*      */     public final boolean value;
/*      */ 
/*      */     public Boolean(boolean paramBoolean)
/*      */     {
/* 1652 */       this.value = paramBoolean;
/*      */     }
/* 1654 */     public Value.Kind getKind() { return Value.Kind.BOOLEAN; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public static class Object extends Value
/*      */   {
/*      */     public final AClass clasz;
/*      */     public final ACsTerm[] targs;
/*      */     public final Value[] vargs;
/*      */ 
/*      */     public Object(AClass paramAClass, ACsTerm[] paramArrayOfACsTerm, Value[] paramArrayOfValue)
/*      */     {
/* 1642 */       this.clasz = paramAClass;
/* 1643 */       this.targs = paramArrayOfACsTerm;
/* 1644 */       this.vargs = paramArrayOfValue;
/*      */     }
/* 1646 */     public Value.Kind getKind() { return Value.Kind.OBJECT; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public static enum Kind
/*      */   {
/* 1627 */     OBJECT, BOOLEAN, INTEGER;
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.Value
 * JD-Core Version:    0.6.2
 */