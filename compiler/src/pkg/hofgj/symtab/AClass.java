/*    */ package pkg.hofgj.symtab;
/*    */ 
/*    */ public class AClass
/*    */ {
/*    */   private final ACsVariable[] cparams;
/*    */   private final Option<AType> supertype;
/*    */   private final AVlField[] vfields;
/*    */   private final AVlMethod[] vmethods;
/*    */   private final AVlMethodBody[] vbodies;
/*    */ 
/*    */   public AClass(ACsVariable[] paramArrayOfACsVariable, Option<AType> paramOption, AVlField[] paramArrayOfAVlField, AVlMethod[] paramArrayOfAVlMethod, AVlMethodBody[] paramArrayOfAVlMethodBody)
/*    */   {
/* 16 */     this.cparams = paramArrayOfACsVariable;
/* 17 */     this.supertype = paramOption;
/* 18 */     this.vfields = paramArrayOfAVlField;
/* 19 */     this.vmethods = paramArrayOfAVlMethod;
/* 20 */     this.vbodies = paramArrayOfAVlMethodBody;
/*    */   }
/*    */ 
/*    */   public ACsVariable[] getCsParameters() {
/* 24 */     return this.cparams;
/*    */   }
/*    */ 
/*    */   public Option<AType> getSuperType() {
/* 28 */     return this.supertype;
/*    */   }
/*    */ 
/*    */   public AVlField[] getVlFields() {
/* 32 */     return this.vfields;
/*    */   }
/*    */ 
/*    */   public AVlMethod[] getVlMethods() {
/* 36 */     return this.vmethods;
/*    */   }
/*    */ 
/*    */   public AVlMethodBody[] getVlMethodBodies() {
/* 40 */     return this.vbodies;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.AClass
 * JD-Core Version:    0.6.2
 */