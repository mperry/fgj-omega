/*    */ package pkg.hofgj.stree;
/*    */ 
/*    */ import pkg.util.Option;
/*    */ 
/*    */ public class SField extends STree
/*    */ {
/*    */   public final SModifier[] modifiers;
/*    */   public final SLabel name;
/*    */   public final Option<SSlotType> type;
/*    */   public final Option<SSlotBody> body;
/*    */ 
/*    */   public SField(SModifier[] paramArrayOfSModifier, SLabel paramSLabel, Option<SSlotType> paramOption, Option<SSlotBody> paramOption1)
/*    */   {
/* 21 */     this.modifiers = paramArrayOfSModifier;
/* 22 */     this.name = paramSLabel;
/* 23 */     this.type = paramOption;
/* 24 */     this.body = paramOption1;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.stree.SField
 * JD-Core Version:    0.6.2
 */