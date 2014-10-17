/*    */ package pkg.parse;
/*    */ 
/*    */ public class ParseRepetition extends Parser
/*    */ {
/*    */   private final Parser parser;
/*    */   private final int min;
/*    */   private final int max;
/*    */ 
/*    */   public ParseRepetition(Parser paramParser, int paramInt1, int paramInt2)
/*    */   {
/* 16 */     assert (paramParser != null);
/* 17 */     assert ((0 <= paramInt1) && (paramInt1 <= paramInt2)) : (paramInt1 + " - " + paramInt2);
/* 18 */     this.parser = paramParser;
/* 19 */     this.min = paramInt1;
/* 20 */     this.max = paramInt2;
/*    */   }
/*    */ 
/*    */   public boolean parse(Scanner paramScanner, boolean paramBoolean)
/*    */   {
/* 27 */     int i = 0; for (int j = this.min; i < j; i++)
/* 28 */       if (!this.parser.parse(paramScanner, paramBoolean)) return false;
/* 29 */     i = this.min; for (int j = this.max; i < j; i++) {
/* 30 */       int k = paramScanner.saveState();
/* 31 */       if (this.parser.parse(paramScanner, paramBoolean)) {
/* 32 */         paramScanner.dropState(k);
/*    */       }
/*    */       else {
/* 35 */         paramScanner.loadState(k);
/* 36 */         paramScanner.dropState(k);
/* 37 */         return true;
/*    */       }
/*    */     }
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean isNullable() {
/* 44 */     return (this.min == 0) || (this.parser.isNullable());
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.ParseRepetition
 * JD-Core Version:    0.6.2
 */