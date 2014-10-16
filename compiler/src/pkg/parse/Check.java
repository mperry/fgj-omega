/*    */ package pkg.parse;
/*    */ 
/*    */ public class Check extends Parser
/*    */ {
/*    */   private final Parser parser;
/*    */ 
/*    */   public Check(Parser paramParser)
/*    */   {
/* 14 */     assert (paramParser != null);
/* 15 */     this.parser = paramParser;
/*    */   }
/*    */ 
/*    */   public boolean parse(Scanner paramScanner, boolean paramBoolean)
/*    */   {
/* 22 */     int i = paramScanner.saveState();
/* 23 */     boolean bool = this.parser.parse(paramScanner, false);
/* 24 */     paramScanner.loadState(i);
/* 25 */     paramScanner.dropState(i);
/* 26 */     return bool;
/*    */   }
/*    */ 
/*    */   public boolean isNullable() {
/* 30 */     return true;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.Check
 * JD-Core Version:    0.6.2
 */