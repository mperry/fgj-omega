/*     */ package pkg.hofgj.stree;
/*     */ 
/*     */ import pkg.util.Option;
/*     */ 
/*     */ public abstract class SVlTerm extends STree
/*     */ {
/*     */   public abstract Kind getKind();
/*     */ 
/*     */   public static class Block extends SVlTerm
/*     */   {
/*     */     public final SMember[] members;
/*     */     public final SVlTerm body;
/*     */ 
/*     */     public Block(SMember[] paramArrayOfSMember, SVlTerm paramSVlTerm)
/*     */     {
/* 117 */       this.members = paramArrayOfSMember;
/* 118 */       this.body = paramSVlTerm;
/*     */     }
/* 120 */     public SVlTerm.Kind getKind() { return SVlTerm.Kind.BLOCK; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Binary extends SVlTerm
/*     */   {
/*     */     public final SVlTerm argl;
/*     */     public final SVlTerm.BinaryOperator operator;
/*     */     public final SVlTerm argr;
/*     */ 
/*     */     public Binary(SVlTerm paramSVlTerm1, SVlTerm.BinaryOperator paramBinaryOperator, SVlTerm paramSVlTerm2)
/*     */     {
/* 106 */       this.argl = paramSVlTerm1;
/* 107 */       this.operator = paramBinaryOperator;
/* 108 */       this.argr = paramSVlTerm2;
/*     */     }
/* 110 */     public SVlTerm.Kind getKind() { return SVlTerm.Kind.BINARY; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Unary extends SVlTerm
/*     */   {
/*     */     public final SVlTerm.UnaryOperator operator;
/*     */     public final SVlTerm arg;
/*     */ 
/*     */     public Unary(SVlTerm.UnaryOperator paramUnaryOperator, SVlTerm paramSVlTerm)
/*     */     {
/*  95 */       this.operator = paramUnaryOperator;
/*  96 */       this.arg = paramSVlTerm;
/*     */     }
/*  98 */     public SVlTerm.Kind getKind() { return SVlTerm.Kind.UNARY; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class If extends SVlTerm
/*     */   {
/*     */     public final SVlTerm test;
/*     */     public final SVlTerm success;
/*     */     public final SVlTerm failure;
/*     */ 
/*     */     public If(SVlTerm paramSVlTerm1, SVlTerm paramSVlTerm2, SVlTerm paramSVlTerm3)
/*     */     {
/*  84 */       this.test = paramSVlTerm1;
/*  85 */       this.success = paramSVlTerm2;
/*  86 */       this.failure = paramSVlTerm3;
/*     */     }
/*  88 */     public SVlTerm.Kind getKind() { return SVlTerm.Kind.IF; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class New extends SVlTerm
/*     */   {
/*     */     public final SLabel clasz;
/*     */     public final Option<STpArgList> targs;
/*     */     public final Option<SVlArgList> vargs;
/*     */     public final Option<SClassBody> body;
/*     */ 
/*     */     public New(SLabel paramSLabel, Option<STpArgList> paramOption, Option<SVlArgList> paramOption1, Option<SClassBody> paramOption2)
/*     */     {
/*  71 */       this.clasz = paramSLabel;
/*  72 */       this.targs = paramOption;
/*  73 */       this.vargs = paramOption1;
/*  74 */       this.body = paramOption2;
/*     */     }
/*  76 */     public SVlTerm.Kind getKind() { return SVlTerm.Kind.NEW; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Sel extends SVlTerm
/*     */   {
/*     */     public final SVlTerm object;
/*     */     public final SVlSelector selector;
/*     */ 
/*     */     public Sel(SVlTerm paramSVlTerm, SVlSelector paramSVlSelector)
/*     */     {
/*  57 */       this.object = paramSVlTerm;
/*  58 */       this.selector = paramSVlSelector;
/*     */     }
/*  60 */     public SVlTerm.Kind getKind() { return SVlTerm.Kind.SEL; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Ref extends SVlTerm
/*     */   {
/*     */     public final SVlRef ref;
/*     */ 
/*     */     public Ref(SVlRef paramSVlRef)
/*     */     {
/*  49 */       this.ref = paramSVlRef; } 
/*  50 */     public SVlTerm.Kind getKind() { return SVlTerm.Kind.REF; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class This extends SVlTerm
/*     */   {
/*     */     public SVlTerm.Kind getKind()
/*     */     {
/*  44 */       return SVlTerm.Kind.THIS;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Integer extends SVlTerm
/*     */   {
/*     */     public final int value;
/*     */ 
/*     */     public Integer(int paramInt)
/*     */     {
/*  39 */       this.value = paramInt; } 
/*  40 */     public SVlTerm.Kind getKind() { return SVlTerm.Kind.INTEGER; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Boolean extends SVlTerm
/*     */   {
/*     */     public final boolean value;
/*     */ 
/*     */     public Boolean(boolean paramBoolean)
/*     */     {
/*  33 */       this.value = paramBoolean; } 
/*  34 */     public SVlTerm.Kind getKind() { return SVlTerm.Kind.BOOLEAN; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static enum BinaryOperator
/*     */   {
/*  17 */     IADD, ISUB, IMUL, IDIV, IREM, 
/*  18 */     ILSL, ILSR, IASR, 
/*  19 */     ILT, IGE, ILE, IGT, IEQ, INE, 
/*  20 */     IOR, IXOR, IAND, 
/*  21 */     ZOR, ZXOR, ZAND;
/*     */   }
/*     */ 
/*     */   public static enum UnaryOperator
/*     */   {
/*  14 */     IPOS, INEG, INOT, ZNOT;
/*     */   }
/*     */ 
/*     */   public static enum Kind
/*     */   {
/*  11 */     BOOLEAN, INTEGER, THIS, REF, SEL, NEW, IF, UNARY, BINARY, BLOCK;
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.stree.SVlTerm
 * JD-Core Version:    0.6.2
 */