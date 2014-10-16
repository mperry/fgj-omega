/*    */ package pkg.hofgj.compiler;
/*    */ 
/*    */ import pkg.hofgj.Source;
/*    */ 
/*    */ public abstract class ASymbol
/*    */ {
/*    */   private final Source source;
/*    */   private final int position;
/*    */   private final String name;
/*    */ 
/*    */   public ASymbol(Source paramSource, int paramInt, String paramString)
/*    */   {
/* 18 */     this.source = paramSource;
/* 19 */     this.position = paramInt;
/* 20 */     this.name = paramString;
/*    */   }
/*    */ 
/*    */   public abstract String getKind();
/*    */ 
/*    */   public Source getSource()
/*    */   {
/* 29 */     return this.source;
/*    */   }
/*    */ 
/*    */   public int getPosition() {
/* 33 */     return this.position;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 37 */     return this.name;
/*    */   }
/*    */ 
/*    */   public String getKindName() {
/* 41 */     return getKind() + " '" + getName() + "'";
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.ASymbol
 * JD-Core Version:    0.6.2
 */