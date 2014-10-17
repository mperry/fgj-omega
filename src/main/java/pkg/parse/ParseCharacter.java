/*    */ package pkg.parse;
/*    */ 
/*    */ public abstract class ParseCharacter extends Parser
/*    */ {
/*    */   public abstract boolean isAccepted(int paramInt);
/*    */ 
/*    */   public boolean parse(Scanner paramScanner, boolean paramBoolean)
/*    */   {
/* 11 */     if (paramScanner.isEmpty()) return false;
/* 12 */     if (!isAccepted(paramScanner.getCurrentValue())) return false;
/* 13 */     paramScanner.setIndexRelative(1);
/* 14 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean isNullable() {
/* 18 */     return false;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.ParseCharacter
 * JD-Core Version:    0.6.2
 */