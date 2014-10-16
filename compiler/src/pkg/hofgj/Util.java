/*    */ package pkg.hofgj;
/*    */ 
/*    */ public class Util
/*    */ {
/*    */   public static String toString(int paramInt, String paramString)
/*    */   {
/*  9 */     return toString(paramInt, paramString, paramString + "s");
/*    */   }
/*    */ 
/*    */   public static String toString(int paramInt, String paramString1, String paramString2) {
/* 13 */     return paramInt + " " + (paramInt == 1 ? paramString1 : paramString2);
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.Util
 * JD-Core Version:    0.6.2
 */