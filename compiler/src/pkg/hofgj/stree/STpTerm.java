/*    */ package pkg.hofgj.stree;
/*    */ 
/*    */ public abstract class STpTerm extends STree
/*    */ {
/*    */   public abstract Kind getKind();
/*    */ 
/*    */   public static class App extends STpTerm
/*    */   {
/*    */     public final STpTerm fun;
/*    */     public final STpArgList args;
/*    */ 
/*    */     public App(STpTerm paramSTpTerm, STpArgList paramSTpArgList)
/*    */     {
/* 50 */       this.fun = paramSTpTerm;
/* 51 */       this.args = paramSTpArgList;
/*    */     }
/* 53 */     public STpTerm.Kind getKind() { return STpTerm.Kind.APP; }
/*    */ 
/*    */   }
/*    */ 
/*    */   public static class Abs extends STpTerm
/*    */   {
/*    */     public final STpParamList params;
/*    */     public final STpTerm body;
/*    */ 
/*    */     public Abs(STpParamList paramSTpParamList, STpTerm paramSTpTerm)
/*    */     {
/* 40 */       this.params = paramSTpParamList;
/* 41 */       this.body = paramSTpTerm;
/*    */     }
/* 43 */     public STpTerm.Kind getKind() { return STpTerm.Kind.ABS; }
/*    */ 
/*    */   }
/*    */ 
/*    */   public static class Ref extends STpTerm
/*    */   {
/*    */     public final SLabel label;
/*    */     public final STpSelector[] selectors;
/*    */ 
/*    */     public Ref(SLabel paramSLabel, STpSelector[] paramArrayOfSTpSelector)
/*    */     {
/* 30 */       this.label = paramSLabel;
/* 31 */       this.selectors = paramArrayOfSTpSelector;
/*    */     }
/* 33 */     public STpTerm.Kind getKind() { return STpTerm.Kind.REF; }
/*    */ 
/*    */   }
/*    */ 
/*    */   public static class Integer extends STpTerm
/*    */   {
/*    */     public STpTerm.Kind getKind()
/*    */     {
/* 23 */       return STpTerm.Kind.INTEGER;
/*    */     }
/*    */   }
/*    */ 
/*    */   public static class Boolean extends STpTerm
/*    */   {
/*    */     public STpTerm.Kind getKind()
/*    */     {
/* 19 */       return STpTerm.Kind.BOOLEAN;
/*    */     }
/*    */   }
/*    */ 
/*    */   public static enum Kind
/*    */   {
/*  8 */     BOOLEAN, INTEGER, REF, ABS, APP;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.stree.STpTerm
 * JD-Core Version:    0.6.2
 */