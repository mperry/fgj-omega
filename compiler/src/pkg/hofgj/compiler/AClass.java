/*    */ package pkg.hofgj.compiler;
/*    */ 
/*    */ import pkg.hofgj.Source;
/*    */ import pkg.util.Option;
/*    */ import pkg.util.Promise;
/*    */ 
/*    */ public class AClass extends ASymbol
/*    */ {
/*    */   private ATpParam[] params;
/*    */   private Promise<Option<AClassBase>> base;
/*    */   private Promise<AClassBody> body;
/*    */ 
/*    */   public AClass(Source paramSource, int paramInt, String paramString)
/*    */   {
/* 21 */     super(paramSource, paramInt, paramString);
/*    */   }
/*    */ 
/*    */   public void setParams(ATpParam[] paramArrayOfATpParam)
/*    */   {
/* 28 */     assert ((this.params == null) && (paramArrayOfATpParam != null)) : this;
/* 29 */     this.params = paramArrayOfATpParam;
/*    */   }
/*    */ 
/*    */   public void setBase(Promise<Option<AClassBase>> paramPromise) {
/* 33 */     assert ((this.base == null) && (paramPromise != null)) : this;
/* 34 */     this.base = paramPromise;
/*    */   }
/*    */ 
/*    */   public void setBody(Promise<AClassBody> paramPromise) {
/* 38 */     assert ((this.body == null) && (paramPromise != null)) : this;
/* 39 */     this.body = paramPromise;
/*    */   }
/*    */ 
/*    */   public ATpParam[] getParams() {
/* 43 */     assert (this.params != null) : this;
/* 44 */     return this.params;
/*    */   }
/*    */ 
/*    */   public Option<AClassBase> getBase() {
/* 48 */     assert (this.base != null) : this;
/* 49 */     return (Option)this.base.force();
/*    */   }
/*    */ 
/*    */   public AClassBody getBody() {
/* 53 */     assert (this.body != null) : this;
/* 54 */     return (AClassBody)this.body.force();
/*    */   }
/*    */ 
/*    */   public AField[] getFields(int paramInt) {
/* 58 */     AField[] arrayOfAField1 = getBody().fields;
/* 59 */     paramInt += arrayOfAField1.length;
/* 60 */     AField[] arrayOfAField2 = getBase().isEmpty() ? new AField[paramInt] : ((AClassBase)getBase().get()).clasz.getFields(paramInt);
/*    */ 
/* 63 */     for (int i = 0; i < arrayOfAField1.length; i++)
/* 64 */       arrayOfAField2[(arrayOfAField2.length - paramInt + i)] = arrayOfAField1[i];
/* 65 */     return arrayOfAField2;
/*    */   }
/*    */ 
/*    */   public Option<AMethod> getMethod(String paramString) {
/* 69 */     for (AMethod localAMethod : getBody().methods)
/* 70 */       if (localAMethod.getName().equals(paramString)) return Option.Some(localAMethod);
/* 71 */     if (getBase().isEmpty()) return Option.None();
/* 72 */     return ((AClassBase)getBase().get()).clasz.getMethod(paramString);
/*    */   }
/*    */ 
/*    */   public String getKind() {
/* 76 */     return "class";
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.AClass
 * JD-Core Version:    0.6.2
 */