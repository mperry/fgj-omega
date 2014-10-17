/*    */ package pkg.hofgj.stree;
/*    */ 
/*    */ import pkg.util.Option;
/*    */ 
/*    */ public class SClassBase extends STree
/*    */ {
/*    */   public final SLabel clasz;
/*    */   public final Option<STpArgList> args;
/*    */ 
/*    */   public SClassBase(SLabel paramSLabel, Option<STpArgList> paramOption)
/*    */   {
/* 17 */     this.clasz = paramSLabel;
/* 18 */     this.args = paramOption;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.stree.SClassBase
 * JD-Core Version:    0.6.2
 */