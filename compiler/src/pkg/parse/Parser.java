/*    */ package pkg.parse;
/*    */ 
/*    */ public abstract class Parser
/*    */ {
/*    */   public static Parser forNothing()
/*    */   {
/*  9 */     return new ParseNothing();
/*    */   }
/*    */ 
/*    */   public static Parser forEOF() {
/* 13 */     return new ParseEOF();
/*    */   }
/*    */ 
/*    */   public static Parser forCharacter(int paramInt) {
/* 17 */     return forCharacter(paramInt, paramInt);
/*    */   }
/*    */ 
/*    */   public static Parser forCharacter(int paramInt1, int paramInt2) {
/* 21 */     return new ParseCharacterInRange(paramInt1, paramInt2);
/*    */   }
/*    */ 
/*    */   public static Parser forCharacter(ByteSet paramByteSet) {
/* 25 */     return new ParseCharacterInU1Set(paramByteSet);
/*    */   }
/*    */ 
/*    */   public static Parser forSequence(Parser[] paramArrayOfParser) {
/* 29 */     return new ParseSequence(paramArrayOfParser);
/*    */   }
/*    */ 
/*    */   public abstract boolean parse(Scanner paramScanner, boolean paramBoolean);
/*    */ 
/*    */   public abstract boolean isNullable();
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.Parser
 * JD-Core Version:    0.6.2
 */