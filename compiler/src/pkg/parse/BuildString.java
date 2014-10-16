/*    */ package pkg.parse;
/*    */ 
/*    */ public class BuildString extends Parser
/*    */ {
/*    */   private final Parser parser;
/*    */ 
/*    */   public BuildString(Parser paramParser)
/*    */   {
/* 14 */     this.parser = paramParser;
/*    */   }
/*    */ 
/*    */   public boolean parse(Scanner paramScanner, boolean paramBoolean)
/*    */   {
/* 21 */     if (!paramBoolean) return this.parser.parse(paramScanner, false);
/* 22 */     int i = paramScanner.getCurrentIndex();
/* 23 */     if (!this.parser.parse(paramScanner, true)) return false;
/* 24 */     paramScanner.putValueO(paramScanner.getStringAbsolute(i));
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean isNullable() {
/* 29 */     return this.parser.isNullable();
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.BuildString
 * JD-Core Version:    0.6.2
 */