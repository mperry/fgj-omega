/*    */ package pkg.hofgj.compiler;
/*    */ 
/*    */ import pkg.hofgj.Source;
/*    */ import pkg.util.Option;
/*    */ 
/*    */ public class AMethod extends ASymbol
/*    */ {
/*    */   private final AClass owner;
/*    */   private ATpParam[] tparams;
/*    */   private AVlParam[] vparams;
/*    */   private ATpTerm type;
/*    */   private Option<AVlTerm> body;
/*    */ 
/*    */   public AMethod(Source paramSource, int paramInt, String paramString, AClass paramAClass)
/*    */   {
/* 22 */     super(paramSource, paramInt, paramString);
/* 23 */     this.owner = paramAClass;
/*    */   }
/*    */ 
/*    */   public void setTpParams(ATpParam[] paramArrayOfATpParam)
/*    */   {
/* 30 */     assert ((this.tparams == null) && (paramArrayOfATpParam != null)) : this;
/* 31 */     this.tparams = paramArrayOfATpParam;
/*    */   }
/*    */ 
/*    */   public void setVlParams(AVlParam[] paramArrayOfAVlParam) {
/* 35 */     assert ((this.vparams == null) && (paramArrayOfAVlParam != null)) : this;
/* 36 */     this.vparams = paramArrayOfAVlParam;
/*    */   }
/*    */ 
/*    */   public void setType(ATpTerm paramATpTerm) {
/* 40 */     assert ((this.type == null) && (paramATpTerm != null)) : this;
/* 41 */     this.type = paramATpTerm;
/*    */   }
/*    */ 
/*    */   public void setBody(Option<AVlTerm> paramOption) {
/* 45 */     assert ((this.body == null) && (paramOption != null)) : this;
/* 46 */     this.body = paramOption;
/*    */   }
/*    */ 
/*    */   public AClass getOwner() {
/* 50 */     return this.owner;
/*    */   }
/*    */ 
/*    */   public ATpParam[] getTpParams() {
/* 54 */     assert (this.tparams != null);
/* 55 */     return this.tparams;
/*    */   }
/*    */ 
/*    */   public AVlParam[] getVlParams() {
/* 59 */     assert (this.vparams != null);
/* 60 */     return this.vparams;
/*    */   }
/*    */ 
/*    */   public ATpTerm getType() {
/* 64 */     assert (this.type != null);
/* 65 */     return this.type;
/*    */   }
/*    */ 
/*    */   public Option<AVlTerm> getBody() {
/* 69 */     assert (this.body != null);
/* 70 */     return this.body;
/*    */   }
/*    */ 
/*    */   public String getKind() {
/* 74 */     return "method";
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.AMethod
 * JD-Core Version:    0.6.2
 */