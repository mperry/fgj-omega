/*    */ package pkg.util;
/*    */ 
/*    */ public class P2<Tp0, Tp1>
/*    */ {
/*    */   public final Tp0 vl0;
/*    */   public final Tp1 vl1;
/*    */ 
/*    */   public static <Tp0, Tp1> P2<Tp0, Tp1> mk(Tp0 paramTp0, Tp1 paramTp1)
/*    */   {
/*  9 */     return new P2(paramTp0, paramTp1);
/*    */   }
/*    */ 
/*    */   public P2(Tp0 paramTp0, Tp1 paramTp1)
/*    */   {
/* 22 */     this.vl0 = paramTp0;
/* 23 */     this.vl1 = paramTp1;
/*    */   }
/*    */ 
/*    */   public boolean equals(P2<?, ?> paramP2)
/*    */   {
/* 30 */     return (this == paramP2) || ((paramP2 != null) && (this.vl0.equals(paramP2.vl0)) && (this.vl1.equals(paramP2.vl1)));
/*    */   }
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 36 */     return (this == paramObject) || (((paramObject instanceof P2)) && (equals((P2)paramObject)));
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 41 */     return this.vl0.hashCode() ^ this.vl1.hashCode();
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 45 */     return "(" + this.vl0 + "," + this.vl1 + ")";
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.util.P2
 * JD-Core Version:    0.6.2
 */