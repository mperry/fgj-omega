/*    */ package pkg.hofgj.stree;
/*    */ 
/*    */ import pkg.util.Option;
/*    */ 
/*    */ public class SMethod extends STree
/*    */ {
/*    */   public final SModifier[] modifiers;
/*    */   public final SLabel name;
/*    */   public final Option<STpParamList> tparams;
/*    */   public final Option<SVlParamList> vparams;
/*    */   public final Option<SSlotType> type;
/*    */   public final Option<SSlotBody> body;
/*    */ 
/*    */   public SMethod(SModifier[] paramArrayOfSModifier, SLabel paramSLabel, Option<STpParamList> paramOption, Option<SVlParamList> paramOption1, Option<SSlotType> paramOption2, Option<SSlotBody> paramOption3)
/*    */   {
/* 24 */     this.modifiers = paramArrayOfSModifier;
/* 25 */     this.name = paramSLabel;
/* 26 */     this.tparams = paramOption;
/* 27 */     this.vparams = paramOption1;
/* 28 */     this.type = paramOption2;
/* 29 */     this.body = paramOption3;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.stree.SMethod
 * JD-Core Version:    0.6.2
 */