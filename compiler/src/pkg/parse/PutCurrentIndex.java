/*    */ package pkg.parse;
/*    */ 
/*    */ public class PutCurrentIndex extends Parser
/*    */ {
/*    */   public boolean parse(Scanner paramScanner, boolean paramBoolean)
/*    */   {
/*  9 */     if (paramBoolean) paramScanner.putValueI(paramScanner.getCurrentIndex());
/* 10 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean isNullable() {
/* 14 */     return true;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.PutCurrentIndex
 * JD-Core Version:    0.6.2
 */