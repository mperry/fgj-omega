/*    */ package pkg.parse;
/*    */ 
/*    */ public class PutValueO<Type> extends Parser
/*    */ {
/*    */   private final Type value;
/*    */ 
/*    */   public PutValueO(Type paramType)
/*    */   {
/* 14 */     this.value = paramType;
/*    */   }
/*    */ 
/*    */   public boolean parse(Scanner paramScanner, boolean paramBoolean)
/*    */   {
/* 21 */     if (paramBoolean) paramScanner.putValueO(this.value);
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean isNullable() {
/* 26 */     return true;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.PutValueO
 * JD-Core Version:    0.6.2
 */