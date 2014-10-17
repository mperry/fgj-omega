/*    */ package pkg.hofgj.compiler;
/*    */ 
/*    */ import pkg.hofgj.Source;
/*    */ 
/*    */ public class AField extends ASymbol
/*    */ {
/*    */   private final AClass owner;
/*    */   private ATpTerm type;
/*    */ 
/*    */   public AField(Source paramSource, int paramInt, String paramString, AClass paramAClass)
/*    */   {
/* 17 */     super(paramSource, paramInt, paramString);
/* 18 */     this.owner = paramAClass;
/*    */   }
/*    */ 
/*    */   public void setType(ATpTerm paramATpTerm)
/*    */   {
/* 25 */     assert ((this.type == null) && (paramATpTerm != null)) : this;
/* 26 */     this.type = paramATpTerm;
/*    */   }
/*    */ 
/*    */   public AClass getOwner() {
/* 30 */     return this.owner;
/*    */   }
/*    */ 
/*    */   public ATpTerm getType() {
/* 34 */     assert (this.type != null);
/* 35 */     return this.type;
/*    */   }
/*    */ 
/*    */   public String getKind() {
/* 39 */     return "field";
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.AField
 * JD-Core Version:    0.6.2
 */