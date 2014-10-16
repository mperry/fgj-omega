/*     */ package pkg.hofgj.compiler;
/*     */ 
/*     */ import pkg.util.Promise;
/*     */ 
/*     */ public abstract class AVlTerm extends ATree
/*     */ {
/*     */   public abstract Kind getKind();
/*     */ 
/*     */   public abstract boolean isError();
/*     */ 
/*     */   public abstract AVlTerm evaluate();
/*     */ 
/*     */   public static class Delayed extends AVlTerm
/*     */   {
/*     */     public final Promise<AVlTerm> promise;
/*     */ 
/*     */     public Delayed(Promise<AVlTerm> paramPromise)
/*     */     {
/* 268 */       this.promise = paramPromise;
/*     */     }
/*     */     public Delayed update(Promise<AVlTerm> paramPromise) {
/* 271 */       int i = 0;
/* 272 */       i |= (this.promise != paramPromise ? 1 : 0);
/* 273 */       if (i == 0) return this;
/* 274 */       Delayed localDelayed = new Delayed(paramPromise);
/* 275 */       localDelayed.position(position());
/* 276 */       return localDelayed;
/*     */     }
/* 278 */     public AVlTerm.Kind getKind() { return AVlTerm.Kind.DELAYED; } 
/* 279 */     public boolean isError() { return evaluate().isError(); } 
/* 280 */     public AVlTerm evaluate() { return (AVlTerm)this.promise.force(); }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Error extends AVlTerm
/*     */   {
/*     */     public Error update()
/*     */     {
/* 254 */       int i = 0;
/* 255 */       if (i == 0) return this;
/* 256 */       Error localError = new Error();
/* 257 */       localError.position(position());
/* 258 */       return localError;
/*     */     }
/* 260 */     public AVlTerm.Kind getKind() { return AVlTerm.Kind.ERROR; } 
/* 261 */     public boolean isError() { return true; } 
/* 262 */     public AVlTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Binary extends AVlTerm
/*     */   {
/*     */     public final AVlTerm argl;
/*     */     public final AVlTerm.BinaryOperator operator;
/*     */     public final AVlTerm argr;
/*     */ 
/*     */     public Binary(AVlTerm paramAVlTerm1, AVlTerm.BinaryOperator paramBinaryOperator, AVlTerm paramAVlTerm2)
/*     */     {
/* 229 */       this.argl = paramAVlTerm1;
/* 230 */       this.operator = paramBinaryOperator;
/* 231 */       this.argr = paramAVlTerm2;
/*     */     }
/*     */ 
/*     */     public Binary update(AVlTerm paramAVlTerm1, AVlTerm.BinaryOperator paramBinaryOperator, AVlTerm paramAVlTerm2)
/*     */     {
/* 236 */       int i = 0;
/* 237 */       i |= (this.argl != paramAVlTerm1 ? 1 : 0);
/* 238 */       i |= (this.operator != paramBinaryOperator ? 1 : 0);
/* 239 */       i |= (this.argr != paramAVlTerm2 ? 1 : 0);
/* 240 */       if (i == 0) return this;
/* 241 */       Binary localBinary = new Binary(paramAVlTerm1, paramBinaryOperator, paramAVlTerm2);
/* 242 */       localBinary.position(position());
/* 243 */       return localBinary;
/*     */     }
/* 245 */     public AVlTerm.Kind getKind() { return AVlTerm.Kind.BINARY; } 
/* 246 */     public boolean isError() { return false; } 
/* 247 */     public AVlTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Unary extends AVlTerm
/*     */   {
/*     */     public final AVlTerm.UnaryOperator operator;
/*     */     public final AVlTerm arg;
/*     */ 
/*     */     public Unary(AVlTerm.UnaryOperator paramUnaryOperator, AVlTerm paramAVlTerm)
/*     */     {
/* 207 */       this.operator = paramUnaryOperator;
/* 208 */       this.arg = paramAVlTerm;
/*     */     }
/*     */     public Unary update(AVlTerm.UnaryOperator paramUnaryOperator, AVlTerm paramAVlTerm) {
/* 211 */       int i = 0;
/* 212 */       i |= (this.operator != paramUnaryOperator ? 1 : 0);
/* 213 */       i |= (this.arg != paramAVlTerm ? 1 : 0);
/* 214 */       if (i == 0) return this;
/* 215 */       Unary localUnary = new Unary(paramUnaryOperator, paramAVlTerm);
/* 216 */       localUnary.position(position());
/* 217 */       return localUnary;
/*     */     }
/* 219 */     public AVlTerm.Kind getKind() { return AVlTerm.Kind.UNARY; } 
/* 220 */     public boolean isError() { return false; } 
/* 221 */     public AVlTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class If extends AVlTerm
/*     */   {
/*     */     public final AVlTerm test;
/*     */     public final AVlTerm success;
/*     */     public final AVlTerm failure;
/*     */ 
/*     */     public If(AVlTerm paramAVlTerm1, AVlTerm paramAVlTerm2, AVlTerm paramAVlTerm3)
/*     */     {
/* 184 */       this.test = paramAVlTerm1;
/* 185 */       this.success = paramAVlTerm2;
/* 186 */       this.failure = paramAVlTerm3;
/*     */     }
/*     */     public If update(AVlTerm paramAVlTerm1, AVlTerm paramAVlTerm2, AVlTerm paramAVlTerm3) {
/* 189 */       int i = 0;
/* 190 */       i |= (this.test != paramAVlTerm1 ? 1 : 0);
/* 191 */       i |= (this.success != paramAVlTerm2 ? 1 : 0);
/* 192 */       i |= (this.failure != paramAVlTerm3 ? 1 : 0);
/* 193 */       if (i == 0) return this;
/* 194 */       If localIf = new If(paramAVlTerm1, paramAVlTerm2, paramAVlTerm3);
/* 195 */       localIf.position(position());
/* 196 */       return localIf;
/*     */     }
/* 198 */     public AVlTerm.Kind getKind() { return AVlTerm.Kind.IF; } 
/* 199 */     public boolean isError() { return false; } 
/* 200 */     public AVlTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class New extends AVlTerm
/*     */   {
/*     */     public final AClass clasz;
/*     */     public final ACsTerm[] targs;
/*     */     public final AVlTerm[] vargs;
/*     */ 
/*     */     public New(AClass paramAClass, ACsTerm[] paramArrayOfACsTerm, AVlTerm[] paramArrayOfAVlTerm)
/*     */     {
/* 160 */       this.clasz = paramAClass;
/* 161 */       this.targs = paramArrayOfACsTerm;
/* 162 */       this.vargs = paramArrayOfAVlTerm;
/*     */     }
/*     */     public New update(AClass paramAClass, ACsTerm[] paramArrayOfACsTerm, AVlTerm[] paramArrayOfAVlTerm) {
/* 165 */       int i = 0;
/* 166 */       i |= (this.clasz != paramAClass ? 1 : 0);
/* 167 */       i |= (this.targs != paramArrayOfACsTerm ? 1 : 0);
/* 168 */       i |= (this.vargs != paramArrayOfAVlTerm ? 1 : 0);
/* 169 */       if (i == 0) return this;
/* 170 */       New localNew = new New(paramAClass, paramArrayOfACsTerm, paramArrayOfAVlTerm);
/* 171 */       localNew.position(position());
/* 172 */       return localNew;
/*     */     }
/* 174 */     public AVlTerm.Kind getKind() { return AVlTerm.Kind.NEW; } 
/* 175 */     public boolean isError() { return false; } 
/* 176 */     public AVlTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Call extends AVlTerm
/*     */   {
/*     */     public final AVlTerm object;
/*     */     public final AMethod method;
/*     */     public final ACsTerm[] targs;
/*     */     public final AVlTerm[] vargs;
/*     */ 
/*     */     public Call(AVlTerm paramAVlTerm, AMethod paramAMethod, ACsTerm[] paramArrayOfACsTerm, AVlTerm[] paramArrayOfAVlTerm)
/*     */     {
/* 132 */       this.object = paramAVlTerm;
/* 133 */       this.method = paramAMethod;
/* 134 */       this.targs = paramArrayOfACsTerm;
/* 135 */       this.vargs = paramArrayOfAVlTerm;
/*     */     }
/*     */ 
/*     */     public Call update(AVlTerm paramAVlTerm, AMethod paramAMethod, ACsTerm[] paramArrayOfACsTerm, AVlTerm[] paramArrayOfAVlTerm)
/*     */     {
/* 140 */       int i = 0;
/* 141 */       i |= (this.object != paramAVlTerm ? 1 : 0);
/* 142 */       i |= (this.method != paramAMethod ? 1 : 0);
/* 143 */       i |= (this.targs != paramArrayOfACsTerm ? 1 : 0);
/* 144 */       i |= (this.vargs != paramArrayOfAVlTerm ? 1 : 0);
/* 145 */       if (i == 0) return this;
/* 146 */       Call localCall = new Call(paramAVlTerm, paramAMethod, paramArrayOfACsTerm, paramArrayOfAVlTerm);
/* 147 */       localCall.position(position());
/* 148 */       return localCall;
/*     */     }
/* 150 */     public AVlTerm.Kind getKind() { return AVlTerm.Kind.CALL; } 
/* 151 */     public boolean isError() { return false; } 
/* 152 */     public AVlTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Get extends AVlTerm
/*     */   {
/*     */     public final AVlTerm object;
/*     */     public final AField field;
/*     */ 
/*     */     public Get(AVlTerm paramAVlTerm, AField paramAField)
/*     */     {
/* 107 */       this.object = paramAVlTerm;
/* 108 */       this.field = paramAField;
/*     */     }
/*     */     public Get update(AVlTerm paramAVlTerm, AField paramAField) {
/* 111 */       int i = 0;
/* 112 */       i |= (this.object != paramAVlTerm ? 1 : 0);
/* 113 */       i |= (this.field != paramAField ? 1 : 0);
/* 114 */       if (i == 0) return this;
/* 115 */       Get localGet = new Get(paramAVlTerm, paramAField);
/* 116 */       localGet.position(position());
/* 117 */       return localGet;
/*     */     }
/* 119 */     public AVlTerm.Kind getKind() { return AVlTerm.Kind.GET; } 
/* 120 */     public boolean isError() { return false; } 
/* 121 */     public AVlTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Local extends AVlTerm
/*     */   {
/*     */     public final AVlParam param;
/*     */ 
/*     */     public Local(AVlParam paramAVlParam)
/*     */     {
/*  88 */       this.param = paramAVlParam;
/*     */     }
/*     */     public Local update(AVlParam paramAVlParam) {
/*  91 */       int i = 0;
/*  92 */       i |= (this.param != paramAVlParam ? 1 : 0);
/*  93 */       if (i == 0) return this;
/*  94 */       Local localLocal = new Local(paramAVlParam);
/*  95 */       localLocal.position(position());
/*  96 */       return localLocal;
/*     */     }
/*  98 */     public AVlTerm.Kind getKind() { return AVlTerm.Kind.LOCAL; } 
/*  99 */     public boolean isError() { return false; } 
/* 100 */     public AVlTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class This extends AVlTerm
/*     */   {
/*     */     public This update()
/*     */     {
/*  74 */       int i = 0;
/*  75 */       if (i == 0) return this;
/*  76 */       This localThis = new This();
/*  77 */       localThis.position(position());
/*  78 */       return localThis;
/*     */     }
/*  80 */     public AVlTerm.Kind getKind() { return AVlTerm.Kind.THIS; } 
/*  81 */     public boolean isError() { return false; } 
/*  82 */     public AVlTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Integer extends AVlTerm
/*     */   {
/*     */     public final int value;
/*     */ 
/*     */     public Integer(int paramInt)
/*     */     {
/*  55 */       this.value = paramInt;
/*     */     }
/*     */     public Integer update(int paramInt) {
/*  58 */       int i = 0;
/*  59 */       i |= (this.value != paramInt ? 1 : 0);
/*  60 */       if (i == 0) return this;
/*  61 */       Integer localInteger = new Integer(paramInt);
/*  62 */       localInteger.position(position());
/*  63 */       return localInteger;
/*     */     }
/*  65 */     public AVlTerm.Kind getKind() { return AVlTerm.Kind.INTEGER; } 
/*  66 */     public boolean isError() { return false; } 
/*  67 */     public AVlTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class Boolean extends AVlTerm
/*     */   {
/*     */     public final boolean value;
/*     */ 
/*     */     public Boolean(boolean paramBoolean)
/*     */     {
/*  37 */       this.value = paramBoolean;
/*     */     }
/*     */     public Boolean update(boolean paramBoolean) {
/*  40 */       int i = 0;
/*  41 */       i |= (this.value != paramBoolean ? 1 : 0);
/*  42 */       if (i == 0) return this;
/*  43 */       Boolean localBoolean = new Boolean(paramBoolean);
/*  44 */       localBoolean.position(position());
/*  45 */       return localBoolean;
/*     */     }
/*  47 */     public AVlTerm.Kind getKind() { return AVlTerm.Kind.BOOLEAN; } 
/*  48 */     public boolean isError() { return false; } 
/*  49 */     public AVlTerm evaluate() { return this; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static enum BinaryOperator
/*     */   {
/*  18 */     IADD, ISUB, IMUL, IDIV, IREM, 
/*  19 */     ILSL, ILSR, IASR, 
/*  20 */     ILT, IGE, ILE, IGT, IEQ, INE, 
/*  21 */     IOR, IXOR, IAND, 
/*  22 */     ZOR, ZXOR, ZAND;
/*     */   }
/*     */ 
/*     */   public static enum UnaryOperator
/*     */   {
/*  15 */     IPOS, INEG, INOT, ZNOT;
/*     */   }
/*     */ 
/*     */   public static enum Kind
/*     */   {
/*  11 */     BOOLEAN, INTEGER, THIS, LOCAL, GET, CALL, NEW, IF, UNARY, BINARY, 
/*  12 */     ERROR, DELAYED;
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.AVlTerm
 * JD-Core Version:    0.6.2
 */