/*    */ package pkg.hofgj.stree;
/*    */ 
/*    */ import pkg.util.Option;
/*    */ 
/*    */ public class SProgram extends STree
/*    */ {
/*    */   public final SMember[] members;
/*    */   public final Option<SVlTerm> main;
/*    */ 
/*    */   public SProgram(SMember[] paramArrayOfSMember, Option<SVlTerm> paramOption)
/*    */   {
/* 17 */     this.members = paramArrayOfSMember;
/* 18 */     this.main = paramOption;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.stree.SProgram
 * JD-Core Version:    0.6.2
 */