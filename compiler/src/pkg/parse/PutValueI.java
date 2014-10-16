/*    */ package pkg.parse;
/*    */ 
/*    */ public class PutValueI extends Parser
/*    */ {
/*    */   private final int value;
/*    */ 
/*    */   public PutValueI(int paramInt)
/*    */   {
/* 14 */     this.value = paramInt;
/*    */   }
/*    */ 
/*    */   public boolean parse(Scanner paramScanner, boolean paramBoolean)
/*    */   {
/* 21 */     if (paramBoolean) paramScanner.putValueI(this.value);
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean isNullable() {
/* 26 */     return true;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.PutValueI
 * JD-Core Version:    0.6.2
 */