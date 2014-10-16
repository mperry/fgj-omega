/*    */ package pkg.parse;
/*    */ 
/*    */ public class ParseCharacterInU1Set extends ParseCharacter
/*    */ {
/*    */   private final ByteSet values;
/*    */ 
/*    */   public ParseCharacterInU1Set(ByteSet paramByteSet)
/*    */   {
/* 14 */     assert (paramByteSet != null);
/* 15 */     this.values = paramByteSet;
/*    */   }
/*    */ 
/*    */   public boolean isAccepted(int paramInt)
/*    */   {
/* 22 */     return this.values.contains(paramInt);
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.ParseCharacterInU1Set
 * JD-Core Version:    0.6.2
 */