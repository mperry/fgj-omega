/*     */ package pkg.hofgj.compiler;
/*     */ 
/*     */ public class Rewriter
/*     */ {
/*     */   private boolean frozen;
/*     */ 
/*     */   public boolean frozen()
/*     */   {
/*  14 */     return this.frozen;
/*     */   }
/*     */ 
/*     */   public Rewriter freeze() {
/*  18 */     assert (!frozen());
/*  19 */     this.frozen = true;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public void rewrite(ATpParam[] paramArrayOfATpParam)
/*     */   {
/*  27 */     assert (frozen());
/*  28 */     for (ATpParam localATpParam : paramArrayOfATpParam) rewrite(localATpParam); 
/*     */   }
/*     */ 
/*     */   public void rewrite(ATpParam paramATpParam)
/*     */   {
/*  32 */     assert (frozen());
/*  33 */     rewrite(paramATpParam.getParams());
/*  34 */     paramATpParam.setBound(rewrite(paramATpParam.getBound(true)), true);
/*     */   }
/*     */ 
/*     */   public ACsTerm[] rewrite(ACsTerm[] paramArrayOfACsTerm)
/*     */   {
/*  41 */     assert (frozen());
/*  42 */     if (paramArrayOfACsTerm.length == 0) return paramArrayOfACsTerm;
/*  43 */     for (int i = 0; i < paramArrayOfACsTerm.length; i++) {
/*  44 */       ACsTerm localACsTerm = rewrite(paramArrayOfACsTerm[i]);
/*  45 */       if (localACsTerm != paramArrayOfACsTerm[i]) {
/*  46 */         ACsTerm[] arrayOfACsTerm = new ACsTerm[paramArrayOfACsTerm.length];
/*  47 */         for (int j = 0; j < i; j++) arrayOfACsTerm[j] = paramArrayOfACsTerm[j];
/*  48 */         arrayOfACsTerm[i] = localACsTerm;
/*  49 */         for (; i < arrayOfACsTerm.length; i++) arrayOfACsTerm[i] = rewrite(paramArrayOfACsTerm[i]);
/*  50 */         return arrayOfACsTerm;
/*     */       }
/*     */     }
/*  52 */     return paramArrayOfACsTerm;
/*     */   }
/*     */ 
/*     */   public ACsTerm rewrite(ACsTerm paramACsTerm) {
/*  56 */     assert (frozen());
/*  57 */     switch (1.$SwitchMap$pkg$hofgj$compiler$ACsTerm$Kind[paramACsTerm.getKind().ordinal()]) { case 1:
/*  58 */       return rewrite((ACsTerm.Function)paramACsTerm);
/*     */     case 2:
/*  59 */       return rewrite((ACsTerm.Error)paramACsTerm);
/*     */     case 3:
/*  60 */       return rewrite((ACsTerm.Delayed)paramACsTerm); }
/*  61 */     throw new Error("" + paramACsTerm.getKind());
/*     */   }
/*     */ 
/*     */   public ACsTerm rewrite(ACsTerm.Function paramFunction)
/*     */   {
/*  66 */     assert (frozen());
/*  67 */     rewrite(paramFunction.params);
/*  68 */     return paramFunction.update(paramFunction.params, rewrite(paramFunction.body));
/*     */   }
/*     */ 
/*     */   public ACsTerm rewrite(ACsTerm.Error paramError)
/*     */   {
/*  74 */     assert (frozen());
/*  75 */     return paramError;
/*     */   }
/*     */ 
/*     */   public ACsTerm rewrite(ACsTerm.Delayed paramDelayed) {
/*  79 */     assert (frozen());
/*  80 */     return rewrite(paramDelayed.evaluate());
/*     */   }
/*     */ 
/*     */   public ATpTerm[] rewrite(ATpTerm[] paramArrayOfATpTerm)
/*     */   {
/*  87 */     assert (frozen());
/*  88 */     if (paramArrayOfATpTerm.length == 0) return paramArrayOfATpTerm;
/*  89 */     for (int i = 0; i < paramArrayOfATpTerm.length; i++) {
/*  90 */       ATpTerm localATpTerm = rewrite(paramArrayOfATpTerm[i]);
/*  91 */       if (localATpTerm != paramArrayOfATpTerm[i]) {
/*  92 */         ATpTerm[] arrayOfATpTerm = new ATpTerm[paramArrayOfATpTerm.length];
/*  93 */         for (int j = 0; j < i; j++) arrayOfATpTerm[j] = paramArrayOfATpTerm[j];
/*  94 */         arrayOfATpTerm[i] = localATpTerm;
/*  95 */         for (; i < arrayOfATpTerm.length; i++) arrayOfATpTerm[i] = rewrite(paramArrayOfATpTerm[i]);
/*  96 */         return arrayOfATpTerm;
/*     */       }
/*     */     }
/*  98 */     return paramArrayOfATpTerm;
/*     */   }
/*     */ 
/*     */   public ATpTerm rewrite(ATpTerm paramATpTerm) {
/* 102 */     assert (frozen());
/* 103 */     switch (1.$SwitchMap$pkg$hofgj$compiler$ATpTerm$Kind[((ATpTerm.Kind)paramATpTerm.getKind()).ordinal()]) { case 1:
/* 104 */       return rewrite((ATpTerm.Boolean)paramATpTerm);
/*     */     case 2:
/* 105 */       return rewrite((ATpTerm.Integer)paramATpTerm);
/*     */     case 3:
/* 106 */       return rewrite((ATpTerm.Concrete)paramATpTerm);
/*     */     case 4:
/* 107 */       return rewrite((ATpTerm.Abstract)paramATpTerm);
/*     */     case 5:
/* 108 */       return rewrite((ATpTerm.Error)paramATpTerm);
/*     */     case 6:
/* 109 */       return rewrite((ATpTerm.Delayed)paramATpTerm); }
/* 110 */     throw new Error("" + paramATpTerm.getKind());
/*     */   }
/*     */ 
/*     */   public ATpTerm rewrite(ATpTerm.Boolean paramBoolean)
/*     */   {
/* 115 */     assert (frozen());
/* 116 */     return paramBoolean;
/*     */   }
/*     */ 
/*     */   public ATpTerm rewrite(ATpTerm.Integer paramInteger) {
/* 120 */     assert (frozen());
/* 121 */     return paramInteger;
/*     */   }
/*     */ 
/*     */   public ATpTerm rewrite(ATpTerm.Concrete paramConcrete) {
/* 125 */     assert (frozen());
/* 126 */     return paramConcrete.update(paramConcrete.clasz, rewrite(paramConcrete.args));
/*     */   }
/*     */ 
/*     */   public ATpTerm rewrite(ATpTerm.Abstract paramAbstract)
/*     */   {
/* 132 */     assert (frozen());
/* 133 */     return paramAbstract.update(paramAbstract.param, rewrite(paramAbstract.args));
/*     */   }
/*     */ 
/*     */   public ATpTerm rewrite(ATpTerm.Error paramError)
/*     */   {
/* 139 */     assert (frozen());
/* 140 */     return paramError;
/*     */   }
/*     */ 
/*     */   public ATpTerm rewrite(ATpTerm.Delayed paramDelayed) {
/* 144 */     assert (frozen());
/* 145 */     return rewrite(paramDelayed.evaluate());
/*     */   }
/*     */ 
/*     */   public AVlTerm[] rewrite(AVlTerm[] paramArrayOfAVlTerm)
/*     */   {
/* 152 */     assert (frozen());
/* 153 */     if (paramArrayOfAVlTerm.length == 0) return paramArrayOfAVlTerm;
/* 154 */     for (int i = 0; i < paramArrayOfAVlTerm.length; i++) {
/* 155 */       AVlTerm localAVlTerm = rewrite(paramArrayOfAVlTerm[i]);
/* 156 */       if (localAVlTerm != paramArrayOfAVlTerm[i]) {
/* 157 */         AVlTerm[] arrayOfAVlTerm = new AVlTerm[paramArrayOfAVlTerm.length];
/* 158 */         for (int j = 0; j < i; j++) arrayOfAVlTerm[j] = paramArrayOfAVlTerm[j];
/* 159 */         arrayOfAVlTerm[i] = localAVlTerm;
/* 160 */         for (; i < arrayOfAVlTerm.length; i++) arrayOfAVlTerm[i] = rewrite(paramArrayOfAVlTerm[i]);
/* 161 */         return arrayOfAVlTerm;
/*     */       }
/*     */     }
/* 163 */     return paramArrayOfAVlTerm;
/*     */   }
/*     */ 
/*     */   public AVlTerm rewrite(AVlTerm paramAVlTerm) {
/* 167 */     assert (frozen());
/* 168 */     switch (1.$SwitchMap$pkg$hofgj$compiler$AVlTerm$Kind[paramAVlTerm.getKind().ordinal()]) { case 1:
/* 169 */       return rewrite((AVlTerm.Boolean)paramAVlTerm);
/*     */     case 2:
/* 170 */       return rewrite((AVlTerm.Integer)paramAVlTerm);
/*     */     case 3:
/* 171 */       return rewrite((AVlTerm.This)paramAVlTerm);
/*     */     case 4:
/* 172 */       return rewrite((AVlTerm.Local)paramAVlTerm);
/*     */     case 5:
/* 173 */       return rewrite((AVlTerm.Get)paramAVlTerm);
/*     */     case 6:
/* 174 */       return rewrite((AVlTerm.Call)paramAVlTerm);
/*     */     case 7:
/* 175 */       return rewrite((AVlTerm.New)paramAVlTerm);
/*     */     case 8:
/* 176 */       return rewrite((AVlTerm.If)paramAVlTerm);
/*     */     case 9:
/* 177 */       return rewrite((AVlTerm.Unary)paramAVlTerm);
/*     */     case 10:
/* 178 */       return rewrite((AVlTerm.Binary)paramAVlTerm);
/*     */     case 11:
/* 179 */       return rewrite((AVlTerm.Error)paramAVlTerm);
/*     */     case 12:
/* 180 */       return rewrite((AVlTerm.Delayed)paramAVlTerm); }
/* 181 */     throw new Error("" + paramAVlTerm.getKind());
/*     */   }
/*     */ 
/*     */   public AVlTerm rewrite(AVlTerm.Boolean paramBoolean)
/*     */   {
/* 186 */     assert (frozen());
/* 187 */     return paramBoolean.update(paramBoolean.value);
/*     */   }
/*     */ 
/*     */   public AVlTerm rewrite(AVlTerm.Integer paramInteger)
/*     */   {
/* 192 */     assert (frozen());
/* 193 */     return paramInteger.update(paramInteger.value);
/*     */   }
/*     */ 
/*     */   public AVlTerm rewrite(AVlTerm.This paramThis)
/*     */   {
/* 198 */     assert (frozen());
/* 199 */     return paramThis.update();
/*     */   }
/*     */ 
/*     */   public AVlTerm rewrite(AVlTerm.Local paramLocal) {
/* 203 */     assert (frozen());
/* 204 */     return paramLocal.update(paramLocal.param);
/*     */   }
/*     */ 
/*     */   public AVlTerm rewrite(AVlTerm.Get paramGet)
/*     */   {
/* 209 */     assert (frozen());
/* 210 */     return paramGet.update(rewrite(paramGet.object), paramGet.field);
/*     */   }
/*     */ 
/*     */   public AVlTerm rewrite(AVlTerm.Call paramCall)
/*     */   {
/* 216 */     assert (frozen());
/* 217 */     return paramCall.update(rewrite(paramCall.object), paramCall.method, rewrite(paramCall.targs), rewrite(paramCall.vargs));
/*     */   }
/*     */ 
/*     */   public AVlTerm rewrite(AVlTerm.New paramNew)
/*     */   {
/* 225 */     assert (frozen());
/* 226 */     return paramNew.update(paramNew.clasz, rewrite(paramNew.targs), rewrite(paramNew.vargs));
/*     */   }
/*     */ 
/*     */   public AVlTerm rewrite(AVlTerm.If paramIf)
/*     */   {
/* 233 */     assert (frozen());
/* 234 */     return paramIf.update(rewrite(paramIf.test), rewrite(paramIf.success), rewrite(paramIf.failure));
/*     */   }
/*     */ 
/*     */   public AVlTerm rewrite(AVlTerm.Unary paramUnary)
/*     */   {
/* 241 */     assert (frozen());
/* 242 */     return paramUnary.update(paramUnary.operator, rewrite(paramUnary.arg));
/*     */   }
/*     */ 
/*     */   public AVlTerm rewrite(AVlTerm.Binary paramBinary)
/*     */   {
/* 248 */     assert (frozen());
/* 249 */     return paramBinary.update(rewrite(paramBinary.argl), paramBinary.operator, rewrite(paramBinary.argr));
/*     */   }
/*     */ 
/*     */   public AVlTerm rewrite(AVlTerm.Error paramError)
/*     */   {
/* 256 */     assert (frozen());
/* 257 */     return paramError;
/*     */   }
/*     */ 
/*     */   public AVlTerm rewrite(AVlTerm.Delayed paramDelayed) {
/* 261 */     assert (frozen());
/* 262 */     return rewrite(paramDelayed.evaluate());
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.Rewriter
 * JD-Core Version:    0.6.2
 */