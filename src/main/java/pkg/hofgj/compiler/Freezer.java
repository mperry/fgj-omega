/*    */ package pkg.hofgj.compiler;
/*    */ 
/*    */ public class Freezer extends Rewriter
/*    */ {
/*  8 */   public static final Freezer instance = new Freezer();
/*    */ 
/*    */   private Freezer()
/*    */   {
/* 14 */     freeze();
/*    */   }
/*    */ 
/*    */   public void rewrite(ATpParam paramATpParam)
/*    */   {
/* 21 */     if (!paramATpParam.isFrozen()) {
/* 22 */       super.rewrite(paramATpParam);
/* 23 */       paramATpParam.freeze();
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.Freezer
 * JD-Core Version:    0.6.2
 */