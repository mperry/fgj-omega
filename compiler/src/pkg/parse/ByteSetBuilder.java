/*     */ package pkg.parse;
/*     */ 
/*     */ public final class ByteSetBuilder
/*     */ {
/*     */   private long bits0;
/*     */   private long bits1;
/*     */   private long bits2;
/*     */   private long bits3;
/*     */ 
/*     */   public ByteSetBuilder(long paramLong1, long paramLong2, long paramLong3, long paramLong4)
/*     */   {
/*  17 */     this.bits0 = paramLong1;
/*  18 */     this.bits1 = paramLong2;
/*  19 */     this.bits2 = paramLong3;
/*  20 */     this.bits3 = paramLong4;
/*     */   }
/*     */ 
/*     */   public ByteSetBuilder(ByteSet paramByteSet) {
/*  24 */     this(paramByteSet.bits0, paramByteSet.bits1, paramByteSet.bits2, paramByteSet.bits3);
/*     */   }
/*     */ 
/*     */   public ByteSetBuilder() {
/*  28 */     this(0L, 0L, 0L, 0L);
/*     */   }
/*     */ 
/*     */   public ByteSetBuilder not()
/*     */   {
/*  35 */     this.bits0 ^= -1L;
/*  36 */     this.bits1 ^= -1L;
/*  37 */     this.bits2 ^= -1L;
/*  38 */     this.bits3 ^= -1L;
/*  39 */     return this;
/*     */   }
/*     */ 
/*     */   public ByteSetBuilder add(int paramInt) {
/*  43 */     assert ((0 <= paramInt) && (paramInt <= 255)) : paramInt;
/*  44 */     long l = 1L << paramInt;
/*  45 */     if (paramInt < 128) {
/*  46 */       if (paramInt < 64)
/*  47 */         this.bits0 |= l;
/*     */       else
/*  49 */         this.bits1 |= l;
/*     */     }
/*  51 */     else if (paramInt < 192)
/*  52 */       this.bits2 |= l;
/*     */     else
/*  54 */       this.bits3 |= l;
/*  55 */     return this;
/*     */   }
/*     */ 
/*     */   public ByteSetBuilder sub(int paramInt) {
/*  59 */     assert ((0 <= paramInt) && (paramInt <= 255)) : paramInt;
/*  60 */     long l = 1L << paramInt;
/*  61 */     if (paramInt < 128) {
/*  62 */       if (paramInt < 64)
/*  63 */         this.bits0 &= (l ^ 0xFFFFFFFF);
/*     */       else
/*  65 */         this.bits1 &= (l ^ 0xFFFFFFFF);
/*     */     }
/*  67 */     else if (paramInt < 192)
/*  68 */       this.bits2 &= (l ^ 0xFFFFFFFF);
/*     */     else
/*  70 */       this.bits3 &= (l ^ 0xFFFFFFFF);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public ByteSetBuilder add(int paramInt1, int paramInt2) {
/*  75 */     assert (0 <= paramInt1) : paramInt1;
/*  76 */     assert (paramInt1 <= paramInt2) : (paramInt1 + " - " + paramInt2);
/*  77 */     assert (paramInt2 <= 255) : paramInt2;
/*  78 */     while (paramInt1 <= paramInt2) add(paramInt1++);
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */   public ByteSetBuilder sub(int paramInt1, int paramInt2) {
/*  83 */     assert (0 <= paramInt1) : paramInt1;
/*  84 */     assert (paramInt1 <= paramInt2) : (paramInt1 + " - " + paramInt2);
/*  85 */     assert (paramInt2 <= 255) : paramInt2;
/*  86 */     while (paramInt1 <= paramInt2) sub(paramInt1++);
/*  87 */     return this;
/*     */   }
/*     */ 
/*     */   public ByteSetBuilder add(ByteSet paramByteSet) {
/*  91 */     this.bits0 |= paramByteSet.bits0;
/*  92 */     this.bits1 |= paramByteSet.bits1;
/*  93 */     this.bits2 |= paramByteSet.bits2;
/*  94 */     this.bits3 |= paramByteSet.bits3;
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   public ByteSetBuilder sub(ByteSet paramByteSet) {
/*  99 */     this.bits0 &= (paramByteSet.bits0 ^ 0xFFFFFFFF);
/* 100 */     this.bits1 &= (paramByteSet.bits1 ^ 0xFFFFFFFF);
/* 101 */     this.bits2 &= (paramByteSet.bits2 ^ 0xFFFFFFFF);
/* 102 */     this.bits3 &= (paramByteSet.bits3 ^ 0xFFFFFFFF);
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */   public ByteSetBuilder mul(ByteSet paramByteSet) {
/* 107 */     this.bits0 &= paramByteSet.bits0;
/* 108 */     this.bits1 &= paramByteSet.bits1;
/* 109 */     this.bits2 &= paramByteSet.bits2;
/* 110 */     this.bits3 &= paramByteSet.bits3;
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   public ByteSet toSet() {
/* 115 */     return new ByteSet(this.bits0, this.bits1, this.bits2, this.bits3);
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.ByteSetBuilder
 * JD-Core Version:    0.6.2
 */