/*    */ package pkg.parse;
/*    */ 
/*    */ import pkg.util.Util;
/*    */ 
/*    */ public final class ByteSet
/*    */   implements Comparable<ByteSet>
/*    */ {
/*    */   public static final int FIELD_COUNT = 4;
/*    */   public static final int FIELD_BITS = 64;
/*    */   public static final int TOTAL_BITS = 256;
/*    */   public static final int MIN_VALUE = 0;
/*    */   public static final int MAX_VALUE = 255;
/*    */   protected final long bits0;
/*    */   protected final long bits1;
/*    */   protected final long bits2;
/*    */   protected final long bits3;
/*    */ 
/*    */   public static ByteSet fromValue(int paramInt)
/*    */   {
/* 21 */     return new ByteSetBuilder().add(paramInt).toSet();
/*    */   }
/*    */ 
/*    */   public static ByteSet fromRange(int paramInt1, int paramInt2) {
/* 25 */     return new ByteSetBuilder().add(paramInt1, paramInt2).toSet();
/*    */   }
/*    */ 
/*    */   protected ByteSet(long paramLong1, long paramLong2, long paramLong3, long paramLong4)
/*    */   {
/* 40 */     this.bits0 = paramLong1;
/* 41 */     this.bits1 = paramLong2;
/* 42 */     this.bits2 = paramLong3;
/* 43 */     this.bits3 = paramLong4;
/*    */   }
/*    */ 
/*    */   public boolean isEmpty()
/*    */   {
/* 50 */     return (this.bits0 == 0L) && (this.bits1 == 0L) && (this.bits2 == 0L) && (this.bits3 == 0L);
/*    */   }
/*    */ 
/*    */   public boolean contains(int paramInt) {
/* 54 */     assert ((0 <= paramInt) && (paramInt <= 255)) : paramInt;
/* 55 */     long l1 = 1L << paramInt;
/* 56 */     long l2 = paramInt < 192 ? this.bits2 : paramInt < 128 ? this.bits1 : paramInt < 64 ? this.bits0 : this.bits3;
/*    */ 
/* 59 */     return (l2 & l1) != 0L;
/*    */   }
/*    */ 
/*    */   public ByteSetBuilder toBuilder() {
/* 63 */     return new ByteSetBuilder(this);
/*    */   }
/*    */ 
/*    */   public int compareTo(ByteSet paramByteSet) {
/* 67 */     long l = 0L;
/* 68 */     if (l == 0L) l = this.bits0 - paramByteSet.bits0;
/* 69 */     if (l == 0L) l = this.bits1 - paramByteSet.bits1;
/* 70 */     if (l == 0L) l = this.bits2 - paramByteSet.bits2;
/* 71 */     if (l == 0L) l = this.bits3 - paramByteSet.bits3;
/* 72 */     return l > 0L ? 1 : l < 0L ? -1 : 0;
/*    */   }
/*    */ 
/*    */   public boolean equals(ByteSet paramByteSet) {
/* 76 */     if (this == paramByteSet) return true;
/* 77 */     if (this.bits0 != paramByteSet.bits0) return false;
/* 78 */     if (this.bits1 != paramByteSet.bits1) return false;
/* 79 */     if (this.bits2 != paramByteSet.bits2) return false;
/* 80 */     if (this.bits3 != paramByteSet.bits3) return false;
/* 81 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object paramObject) {
/* 85 */     return ((paramObject instanceof ByteSet)) && (equals((ByteSet)paramObject));
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 89 */     return Util.getHashCode(this.bits0 ^ this.bits1 ^ this.bits2 ^ this.bits3);
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.ByteSet
 * JD-Core Version:    0.6.2
 */