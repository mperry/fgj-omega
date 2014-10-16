/*    */ package pkg.hofgj.stree;
/*    */ 
/*    */ import pkg.util.Option;
/*    */ 
/*    */ public class SConstructor extends STree
/*    */ {
/*    */   public final SModifier[] modifiers;
/*    */   public final Option<SLabel> name;
/*    */   public final Option<STpParamList> tparams;
/*    */   public final Option<SVlParamList> vparams;
/*    */   public final SLabel[] args;
/*    */   public final SLabel[] fields;
/*    */   public final SLabel[] values;
/*    */ 
/*    */   public SConstructor(SModifier[] paramArrayOfSModifier, Option<SLabel> paramOption, Option<STpParamList> paramOption1, Option<SVlParamList> paramOption2, SLabel[] paramArrayOfSLabel1, SLabel[] paramArrayOfSLabel2, SLabel[] paramArrayOfSLabel3)
/*    */   {
/* 25 */     this.modifiers = paramArrayOfSModifier;
/* 26 */     this.name = paramOption;
/* 27 */     this.tparams = paramOption1;
/* 28 */     this.vparams = paramOption2;
/* 29 */     this.args = paramArrayOfSLabel1;
/* 30 */     this.fields = paramArrayOfSLabel2;
/* 31 */     this.values = paramArrayOfSLabel3;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.stree.SConstructor
 * JD-Core Version:    0.6.2
 */