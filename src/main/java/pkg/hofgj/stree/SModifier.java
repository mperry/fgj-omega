/*    */ package pkg.hofgj.stree;
/*    */ 
/*    */ public abstract class SModifier extends STree
/*    */ {
/*    */   public abstract Kind getKind();
/*    */ 
/*    */   public static class IsFinal extends SModifier
/*    */   {
/*    */     public SModifier.Kind getKind()
/*    */     {
/* 46 */       return SModifier.Kind.FINAL;
/*    */     }
/*    */   }
/*    */ 
/*    */   public static class IsAbstract extends SModifier
/*    */   {
/*    */     public SModifier.Kind getKind()
/*    */     {
/* 41 */       return SModifier.Kind.ABSTRACT;
/*    */     }
/*    */   }
/*    */ 
/*    */   public static class IsStatic extends SModifier
/*    */   {
/*    */     public SModifier.Kind getKind()
/*    */     {
/* 36 */       return SModifier.Kind.STATIC;
/*    */     }
/*    */   }
/*    */ 
/*    */   public static class IsPrivate extends SModifier
/*    */   {
/*    */     public SModifier.Kind getKind()
/*    */     {
/* 31 */       return SModifier.Kind.PRIVATE;
/*    */     }
/*    */   }
/*    */ 
/*    */   public static class IsProtected extends SModifier
/*    */   {
/*    */     public SModifier.Kind getKind()
/*    */     {
/* 26 */       return SModifier.Kind.PROTECTED;
/*    */     }
/*    */   }
/*    */ 
/*    */   public static class IsPublic extends SModifier
/*    */   {
/*    */     public SModifier.Kind getKind()
/*    */     {
/* 21 */       return SModifier.Kind.PUBLIC;
/*    */     }
/*    */   }
/*    */ 
/*    */   public static enum Kind
/*    */   {
/*  9 */     PUBLIC, PROTECTED, PRIVATE, STATIC, ABSTRACT, FINAL;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.stree.SModifier
 * JD-Core Version:    0.6.2
 */