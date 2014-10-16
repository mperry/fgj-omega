/*    */ package pkg.hofgj.compiler;
/*    */ 
/*    */ import pkg.hofgj.Source;
/*    */ 
/*    */ public class AVlParam extends ASymbol
/*    */ {
/*    */   private ATpTerm type;
/*    */ 
/*    */   public AVlParam(Source paramSource, int paramInt, String paramString)
/*    */   {
/* 16 */     super(paramSource, paramInt, paramString);
/*    */   }
/*    */ 
/*    */   public void setType(ATpTerm paramATpTerm)
/*    */   {
/* 23 */     assert ((this.type == null) && (paramATpTerm != null)) : this;
/* 24 */     this.type = paramATpTerm;
/*    */   }
/*    */ 
/*    */   public ATpTerm getType() {
/* 28 */     assert (this.type != null);
/* 29 */     return this.type;
/*    */   }
/*    */ 
/*    */   public String getKind() {
/* 33 */     return "value parameter";
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.AVlParam
 * JD-Core Version:    0.6.2
 */