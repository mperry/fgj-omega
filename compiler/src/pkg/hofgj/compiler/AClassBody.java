/*    */ package pkg.hofgj.compiler;
/*    */ 
/*    */ public class AClassBody
/*    */ {
/*    */   public final AField[] fields;
/*    */   public final AMethod[] methods;
/*    */ 
/*    */   public AClassBody(AField[] paramArrayOfAField, AMethod[] paramArrayOfAMethod)
/*    */   {
/* 15 */     this.fields = paramArrayOfAField;
/* 16 */     this.methods = paramArrayOfAMethod;
/*    */   }
/*    */ 
/*    */   public AClassBody() {
/* 20 */     this(new AField[0], new AMethod[0]);
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.AClassBody
 * JD-Core Version:    0.6.2
 */