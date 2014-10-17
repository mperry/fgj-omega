/*    */ package pkg.parse;
/*    */ 
/*    */ public class ParseSequence extends Parser
/*    */ {
/*    */   private final Parser[] parsers;
/*    */ 
/*    */   public ParseSequence(Parser[] paramArrayOfParser)
/*    */   {
/* 14 */     assert (paramArrayOfParser != null);
/* 15 */     for (Parser localParser : paramArrayOfParser) assert (localParser != null);
/* 16 */     this.parsers = paramArrayOfParser;
/*    */   }
/*    */ 
/*    */   public boolean parse(Scanner paramScanner, boolean paramBoolean)
/*    */   {
/* 23 */     for (Parser localParser : this.parsers)
/* 24 */       if (!localParser.parse(paramScanner, paramBoolean)) return false;
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean isNullable() {
/* 29 */     for (Parser localParser : this.parsers) if (!localParser.isNullable()) return false;
/* 30 */     return true;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.ParseSequence
 * JD-Core Version:    0.6.2
 */