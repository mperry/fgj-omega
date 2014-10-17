/*    */ package pkg.parse;
/*    */ 
/*    */ public class ParseAlternatives extends Parser
/*    */ {
/*    */   private final Parser[] parsers;
/*    */ 
/*    */   public ParseAlternatives(Parser[] paramArrayOfParser)
/*    */   {
/* 14 */     assert (paramArrayOfParser != null);
/* 15 */     for (Parser localParser : paramArrayOfParser) assert (localParser != null);
/* 16 */     this.parsers = paramArrayOfParser;
/*    */   }
/*    */ 
/*    */   public boolean parse(Scanner paramScanner, boolean paramBoolean)
/*    */   {
/* 23 */     int i = paramScanner.saveState();
/* 24 */     for (Parser localParser : this.parsers) {
/* 25 */       if (localParser.parse(paramScanner, paramBoolean)) {
/* 26 */         paramScanner.dropState(i);
/* 27 */         return true;
/*    */       }
/* 29 */       paramScanner.loadState(i);
/*    */     }
/*    */ 
/* 33 */     paramScanner.dropState(i);
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean isNullable() {
/* 38 */     for (Parser localParser : this.parsers) if (localParser.isNullable()) return true;
/* 39 */     return false;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.ParseAlternatives
 * JD-Core Version:    0.6.2
 */