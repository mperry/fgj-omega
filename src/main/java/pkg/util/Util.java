/*    */ package pkg.util;
/*    */ 
/*    */ public abstract class Util
/*    */ {
/*    */   public static <Type> boolean areEqual(Type paramType1, Type paramType2)
/*    */   {
/*  9 */     return (paramType1 == paramType2) || ((paramType1 != null) && (paramType2 != null) && (paramType1.equals(paramType2)));
/*    */   }
/*    */ 
/*    */   public static int getHashCode(boolean paramBoolean)
/*    */   {
/* 16 */     return paramBoolean ? 1231 : 1237;
/*    */   }
/*    */ 
/*    */   public static int getHashCode(byte paramByte) {
/* 20 */     return paramByte;
/*    */   }
/*    */ 
/*    */   public static int getHashCode(short paramShort) {
/* 24 */     return paramShort;
/*    */   }
/*    */ 
/*    */   public static int getHashCode(char paramChar) {
/* 28 */     return paramChar;
/*    */   }
/*    */ 
/*    */   public static int getHashCode(int paramInt) {
/* 32 */     return paramInt;
/*    */   }
/*    */ 
/*    */   public static int getHashCode(long paramLong) {
/* 36 */     return (int)(paramLong ^ paramLong >>> 32);
/*    */   }
/*    */ 
/*    */   public static int getHashCode(float paramFloat) {
/* 40 */     return Float.floatToRawIntBits(paramFloat);
/*    */   }
/*    */ 
/*    */   public static int getHashCode(double paramDouble) {
/* 44 */     return getHashCode(Double.doubleToRawLongBits(paramDouble));
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.util.Util
 * JD-Core Version:    0.6.2
 */