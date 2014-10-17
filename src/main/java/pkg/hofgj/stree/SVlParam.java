/*    */ package pkg.hofgj.stree;
/*    */ 
/*    */ import pkg.util.Option;
/*    */ 
/*    */ public class SVlParam extends STree
/*    */ {
/*    */   public final SLabel name;
/*    */   public final Option<SSlotType> type;
/*    */ 
/*    */   public SVlParam(SLabel paramSLabel, Option<SSlotType> paramOption)
/*    */   {
/* 17 */     this.name = paramSLabel;
/* 18 */     this.type = paramOption;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.stree.SVlParam
 * JD-Core Version:    0.6.2
 */