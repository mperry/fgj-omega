/*    */ package pkg.hofgj.stree;
/*    */ 
/*    */ import pkg.util.Option;
/*    */ 
/*    */ public class SVlRef extends STree
/*    */ {
/*    */   public final SLabel label;
/*    */   public final Option<STpArgList> targs;
/*    */   public final Option<SVlArgList> vargs;
/*    */ 
/*    */   public SVlRef(SLabel paramSLabel, Option<STpArgList> paramOption, Option<SVlArgList> paramOption1)
/*    */   {
/* 20 */     this.label = paramSLabel;
/* 21 */     this.targs = paramOption;
/* 22 */     this.vargs = paramOption1;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.stree.SVlRef
 * JD-Core Version:    0.6.2
 */