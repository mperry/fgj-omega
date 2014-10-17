/*    */ package pkg.hofgj.compiler;
/*    */ 
/*    */ import pkg.hofgj.Source;
import pkg.hofgj.Util;

import static pkg.hofgj.Util.$assertionsDisabled;

/*    */
/*    */ public class ATpParam extends ASymbol
/*    */ {
//           public static boolean $assertionsDisabled = false;
/*  7 */   public static boolean DEBUG = true;
/*    */   private ATpParam[] params;
/*    */   private ATpTerm bound;
/*    */   private boolean frozen;
/*    */ 
/*    */   public ATpParam(Source paramSource, int paramInt, String paramString)
/*    */   {
/* 21 */     super(paramSource, paramInt, paramString);
/*    */   }
/*    */ 
/*    */   public void freeze()
/*    */   {
/* 28 */     if ((DEBUG) && (!$assertionsDisabled) && (this.frozen)) throw new AssertionError(this);
/* 29 */     this.frozen = true;
/*    */   }
/*    */ 
/*    */   public boolean isFrozen() {
/* 33 */     return this.frozen;
/*    */   }
/*    */ 
/*    */   public void setParams(ATpParam[] paramArrayOfATpParam) {
/* 37 */     if ((DEBUG) && (!$assertionsDisabled) && ((this.params != null) || (paramArrayOfATpParam == null))) throw new AssertionError(this);
/* 38 */     this.params = paramArrayOfATpParam;
/*    */   }
/*    */ 
/*    */   public void setBound(ATpTerm paramATpTerm) {
/* 42 */     setBound(paramATpTerm, false);
/*    */   }
/*    */ 
/*    */   public void setBound(ATpTerm paramATpTerm, boolean paramBoolean) {
/* 46 */     if ((DEBUG) && (!$assertionsDisabled) && (this.frozen)) throw new AssertionError(this);
/* 47 */     if ((DEBUG) && (!$assertionsDisabled)) if (((this.bound != null) != paramBoolean) || (paramATpTerm == null)) throw new AssertionError(this);
/* 48 */     this.bound = paramATpTerm;
/*    */   }
/*    */ 
/*    */   public ATpParam[] getParams() {
/* 52 */     if ((DEBUG) && (!$assertionsDisabled) && (this.params == null)) throw new AssertionError(this);
/* 53 */     return this.params;
/*    */   }
/*    */ 
/*    */   public ATpTerm getBound() {
/* 57 */     return getBound(false);
/*    */   }
/*    */ 
/*    */   public ATpTerm getBound(boolean paramBoolean) {
/* 61 */     if ((DEBUG) && (!$assertionsDisabled)) if (this.frozen != (!paramBoolean)) throw new AssertionError(this);
/* 62 */     return this.bound;
/*    */   }
/*    */ 
/*    */   public String getKind() {
/* 66 */     return "type parameter";
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.ATpParam
 * JD-Core Version:    0.6.2
 */