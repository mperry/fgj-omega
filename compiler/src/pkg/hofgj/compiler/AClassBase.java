/*    */ package pkg.hofgj.compiler;
/*    */ 
/*    */ import pkg.util.Promise;
/*    */ 
/*    */ public class AClassBase
/*    */ {
/*    */   public final AClass clasz;
/*    */   public final Promise<ACsTerm[]> args;
/*    */ 
/*    */   public AClassBase(AClass paramAClass, Promise<ACsTerm[]> paramPromise)
/*    */   {
/* 17 */     this.clasz = paramAClass;
/* 18 */     this.args = paramPromise;
/*    */   }
/*    */ 
/*    */   public ATpTerm getBaseType()
/*    */   {
/* 25 */     return new ATpTerm.Concrete(this.clasz, (ACsTerm[])this.args.force());
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.AClassBase
 * JD-Core Version:    0.6.2
 */