/*    */ package pkg.hofgj.stree;
/*    */ 
/*    */ import pkg.util.Option;
/*    */ 
/*    */ public class STpParam extends STree
/*    */ {
/*    */   public final SLabel name;
/*    */   public final Option<STpParamList> params;
/*    */   public final Option<SSlotBound> bound;
/*    */ 
/*    */   public STpParam(SLabel paramSLabel, Option<STpParamList> paramOption, Option<SSlotBound> paramOption1)
/*    */   {
/* 20 */     this.name = paramSLabel;
/* 21 */     this.params = paramOption;
/* 22 */     this.bound = paramOption1;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.stree.STpParam
 * JD-Core Version:    0.6.2
 */