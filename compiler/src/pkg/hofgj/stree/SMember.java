/*    */ package pkg.hofgj.stree;
/*    */ 
/*    */ public abstract class SMember extends STree
/*    */ {
/*    */   public abstract Kind getKind();
/*    */ 
/*    */   public static class Constructor extends SMember
/*    */   {
/*    */     public final SConstructor constructor;
/*    */ 
/*    */     public Constructor(SConstructor paramSConstructor)
/*    */     {
/* 39 */       this.constructor = paramSConstructor;
/*    */     }
/* 41 */     public SMember.Kind getKind() { return SMember.Kind.CONSTRUCTOR; }
/*    */ 
/*    */   }
/*    */ 
/*    */   public static class Method extends SMember
/*    */   {
/*    */     public final SMethod method;
/*    */ 
/*    */     public Method(SMethod paramSMethod)
/*    */     {
/* 32 */       this.method = paramSMethod; } 
/* 33 */     public SMember.Kind getKind() { return SMember.Kind.METHOD; }
/*    */ 
/*    */   }
/*    */ 
/*    */   public static class Field extends SMember
/*    */   {
/*    */     public final SField field;
/*    */ 
/*    */     public Field(SField paramSField)
/*    */     {
/* 26 */       this.field = paramSField; } 
/* 27 */     public SMember.Kind getKind() { return SMember.Kind.FIELD; }
/*    */ 
/*    */   }
/*    */ 
/*    */   public static class Class extends SMember
/*    */   {
/*    */     public final SClass clasz;
/*    */ 
/*    */     public Class(SClass paramSClass)
/*    */     {
/* 20 */       this.clasz = paramSClass; } 
/* 21 */     public SMember.Kind getKind() { return SMember.Kind.CLASS; }
/*    */ 
/*    */   }
/*    */ 
/*    */   public static enum Kind
/*    */   {
/*  8 */     CLASS, FIELD, METHOD, CONSTRUCTOR;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.stree.SMember
 * JD-Core Version:    0.6.2
 */