/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ abstract class FTpTerm
/*      */ {
/*      */   public abstract Kind getKind();
/*      */ 
/*      */   public static class Tp extends FTpTerm
/*      */   {
/*      */     public final ATpTerm term;
/*      */ 
/*      */     public Tp(ATpTerm paramATpTerm)
/*      */     {
/* 3155 */       this.term = paramATpTerm; } 
/* 3156 */     public FTpTerm.Kind getKind() { return FTpTerm.Kind.TP; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public static class Cs extends FTpTerm
/*      */   {
/*      */     public final ACsTerm term;
/*      */ 
/*      */     public Cs(ACsTerm paramACsTerm)
/*      */     {
/* 3149 */       this.term = paramACsTerm; } 
/* 3150 */     public FTpTerm.Kind getKind() { return FTpTerm.Kind.CS; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public static class Abstract extends FTpTerm
/*      */   {
/*      */     public final ATpParam param;
/*      */ 
/*      */     public Abstract(ATpParam paramATpParam)
/*      */     {
/* 3143 */       this.param = paramATpParam; } 
/* 3144 */     public FTpTerm.Kind getKind() { return FTpTerm.Kind.ABSTRACT; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public static class Concrete extends FTpTerm
/*      */   {
/*      */     public final AClass clasz;
/*      */ 
/*      */     public Concrete(AClass paramAClass)
/*      */     {
/* 3137 */       this.clasz = paramAClass; } 
/* 3138 */     public FTpTerm.Kind getKind() { return FTpTerm.Kind.CONCRETE; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public static enum Kind
/*      */   {
/* 3125 */     CONCRETE, ABSTRACT, CS, TP;
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.FTpTerm
 * JD-Core Version:    0.6.2
 */