/*    */ package pkg.parse;
/*    */ 
/*    */ public class ParseEOF extends Parser
/*    */ {
/*    */   public boolean parse(Scanner paramScanner, boolean paramBoolean)
/*    */   {
/*  9 */     return paramScanner.isEmpty();
/*    */   }
/*    */ 
/*    */   public boolean isNullable() {
/* 13 */     return false;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.ParseEOF
 * JD-Core Version:    0.6.2
 */