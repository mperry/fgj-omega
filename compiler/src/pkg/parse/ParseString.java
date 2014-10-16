/*    */ package pkg.parse;
/*    */ 
/*    */ public class ParseString extends Parser
/*    */ {
/*    */   private final String string;
/*    */ 
/*    */   public ParseString(String paramString)
/*    */   {
/* 14 */     assert (paramString != null);
/* 15 */     this.string = paramString;
/*    */   }
/*    */ 
/*    */   public boolean parse(Scanner paramScanner, boolean paramBoolean)
/*    */   {
/* 22 */     int i = this.string.length();
/* 23 */     if (paramScanner.getLengthRelative() < i) return false;
/* 24 */     for (int j = 0; j < i; j++)
/* 25 */       if (paramScanner.getValueAtRelative(j) != this.string.charAt(j))
/* 26 */         return false;
/* 27 */     paramScanner.setIndexRelative(i);
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean isNullable() {
/* 32 */     return this.string.length() == 0;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.ParseString
 * JD-Core Version:    0.6.2
 */