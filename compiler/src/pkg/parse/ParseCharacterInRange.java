/*    */ package pkg.parse;
/*    */ 
/*    */ public class ParseCharacterInRange extends ParseCharacter
/*    */ {
/*    */   private final int first;
/*    */   private final int last;
/*    */ 
/*    */   public ParseCharacterInRange(int paramInt1, int paramInt2)
/*    */   {
/* 15 */     assert (paramInt1 <= paramInt2) : (paramInt1 + " - " + paramInt2);
/* 16 */     this.first = paramInt1;
/* 17 */     this.last = paramInt2;
/*    */   }
/*    */ 
/*    */   public boolean isAccepted(int paramInt)
/*    */   {
/* 24 */     return (this.first <= paramInt) && (paramInt <= this.last);
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.ParseCharacterInRange
 * JD-Core Version:    0.6.2
 */