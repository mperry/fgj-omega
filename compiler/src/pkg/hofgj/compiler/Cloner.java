/*     */ package pkg.hofgj.compiler;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import pkg.util.P2;
/*     */ 
/*     */ public class Cloner extends Rewriter
/*     */ {
/*     */   private final Map<ATpParam, TpLookup> tparams;
/*     */ 
/*     */   public static P2<ATpParam[], ATpTerm[]> cloneMethod(ATpParam[] paramArrayOfATpParam, ACsTerm[] paramArrayOfACsTerm, AMethod paramAMethod)
/*     */   {
/*  17 */     Cloner localCloner = new Cloner();
/*  18 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/*  19 */     localCloner.insert(paramArrayOfATpParam, paramArrayOfACsTerm);
/*  20 */     ATpParam[] arrayOfATpParam = localCloner.clone(paramAMethod.getTpParams());
/*  21 */     ATpTerm[] arrayOfATpTerm = new ATpTerm[paramAMethod.getVlParams().length + 1];
/*  22 */     for (int i = 0; i < paramAMethod.getVlParams().length; i++)
/*  23 */       arrayOfATpTerm[i] = localCloner.rewrite(paramAMethod.getVlParams()[i].getType());
/*  24 */     arrayOfATpTerm[paramAMethod.getVlParams().length] = localCloner.rewrite(paramAMethod.getType());
/*     */ 
/*  26 */     localCloner.remove(paramAMethod.getTpParams());
/*  27 */     localCloner.remove(paramArrayOfATpParam);
/*  28 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/*  29 */     return P2.mk(arrayOfATpParam, arrayOfATpTerm);
/*     */   }
/*     */ 
/*     */   public static P2<ATpParam[][], ATpTerm[]> cloneTpParams(ATpParam[] paramArrayOfATpParam, ACsTerm[] paramArrayOfACsTerm)
/*     */   {
/*  35 */     Cloner localCloner = new Cloner();
/*  36 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/*  37 */     localCloner.insert(paramArrayOfATpParam, paramArrayOfACsTerm);
/*  38 */     ATpParam[][] arrayOfATpParam = new ATpParam[paramArrayOfATpParam.length][];
/*  39 */     ATpTerm[] arrayOfATpTerm = new ATpTerm[paramArrayOfATpParam.length];
/*  40 */     for (int i = 0; i < paramArrayOfATpParam.length; i++) {
/*  41 */       arrayOfATpParam[i] = localCloner.clone(paramArrayOfATpParam[i].getParams());
/*  42 */       arrayOfATpTerm[i] = localCloner.rewrite(paramArrayOfATpParam[i].getBound());
/*  43 */       localCloner.remove(paramArrayOfATpParam[i].getParams());
/*     */     }
/*  45 */     localCloner.remove(paramArrayOfATpParam);
/*  46 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/*  47 */     return P2.mk(arrayOfATpParam, arrayOfATpTerm);
/*     */   }
/*     */ 
/*     */   public static ATpParam[][] cloneTpParams0(ATpParam[] paramArrayOfATpParam, ACsTerm[] paramArrayOfACsTerm)
/*     */   {
/*  54 */     Cloner localCloner = new Cloner();
/*  55 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/*  56 */     localCloner.insert(paramArrayOfATpParam, paramArrayOfACsTerm);
/*  57 */     ATpParam[][] arrayOfATpParam = new ATpParam[paramArrayOfATpParam.length][];
/*  58 */     for (int i = 0; i < paramArrayOfATpParam.length; i++) {
/*  59 */       arrayOfATpParam[i] = localCloner.clone(paramArrayOfATpParam[i].getParams());
/*  60 */       localCloner.remove(paramArrayOfATpParam[i].getParams());
/*     */     }
/*  62 */     localCloner.remove(paramArrayOfATpParam);
/*  63 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/*  64 */     return arrayOfATpParam;
/*     */   }
/*     */ 
/*     */   public static ATpTerm[] cloneTps(ATpParam[] paramArrayOfATpParam, ACsTerm[] paramArrayOfACsTerm, ATpTerm[] paramArrayOfATpTerm)
/*     */   {
/*  70 */     Cloner localCloner = new Cloner();
/*  71 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/*  72 */     localCloner.insert(paramArrayOfATpParam, paramArrayOfACsTerm);
/*  73 */     ATpTerm[] arrayOfATpTerm = localCloner.rewrite(paramArrayOfATpTerm);
/*  74 */     localCloner.remove(paramArrayOfATpParam);
/*  75 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/*  76 */     return arrayOfATpTerm;
/*     */   }
/*     */ 
/*     */   public static ATpTerm cloneTp(ATpParam[] paramArrayOfATpParam, ACsTerm[] paramArrayOfACsTerm, ATpTerm paramATpTerm)
/*     */   {
/*  82 */     Cloner localCloner = new Cloner();
/*  83 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/*  84 */     localCloner.insert(paramArrayOfATpParam, paramArrayOfACsTerm);
/*  85 */     ATpTerm localATpTerm = localCloner.rewrite(paramATpTerm);
/*  86 */     localCloner.remove(paramArrayOfATpParam);
/*  87 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/*  88 */     return localATpTerm;
/*     */   }
/*     */ 
/*     */   public static AVlTerm cloneVl(ATpParam[] paramArrayOfATpParam, ACsTerm[] paramArrayOfACsTerm, AVlTerm paramAVlTerm)
/*     */   {
/*  94 */     Cloner localCloner = new Cloner();
/*  95 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/*  96 */     localCloner.insert(paramArrayOfATpParam, paramArrayOfACsTerm);
/*  97 */     AVlTerm localAVlTerm = localCloner.rewrite(paramAVlTerm);
/*  98 */     localCloner.remove(paramArrayOfATpParam);
/*  99 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/* 100 */     return localAVlTerm;
/*     */   }
/*     */ 
/*     */   public static ATpTerm cloneToTp(AClass paramAClass) {
/* 104 */     Cloner localCloner = new Cloner();
/* 105 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/* 106 */     ACsTerm[] arrayOfACsTerm = localCloner.cloneToCs(paramAClass.getParams(), paramAClass.getParams());
/*     */ 
/* 108 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/* 109 */     return new ATpTerm.Concrete(paramAClass, arrayOfACsTerm);
/*     */   }
/*     */ 
/*     */   public static ACsTerm cloneToCs(AClass paramAClass) {
/* 113 */     Cloner localCloner = new Cloner();
/* 114 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/* 115 */     ATpParam[] arrayOfATpParam1 = paramAClass.getParams();
/* 116 */     ATpParam[] arrayOfATpParam2 = localCloner.clone(arrayOfATpParam1);
/* 117 */     ACsTerm[] arrayOfACsTerm = localCloner.cloneToCs(arrayOfATpParam1, arrayOfATpParam2);
/* 118 */     localCloner.remove(arrayOfATpParam1);
/* 119 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/* 120 */     return new ACsTerm.Function(arrayOfATpParam2, new ATpTerm.Concrete(paramAClass, arrayOfACsTerm));
/*     */   }
/*     */ 
/*     */   public static ACsTerm cloneToCs(ATpParam paramATpParam) {
/* 124 */     Cloner localCloner = new Cloner();
/* 125 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/* 126 */     ATpParam[] arrayOfATpParam1 = paramATpParam.getParams();
/* 127 */     ATpParam[] arrayOfATpParam2 = localCloner.clone(arrayOfATpParam1);
/* 128 */     ACsTerm[] arrayOfACsTerm = localCloner.cloneToCs(arrayOfATpParam1, arrayOfATpParam2);
/* 129 */     localCloner.remove(arrayOfATpParam1);
/* 130 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/* 131 */     return new ACsTerm.Function(arrayOfATpParam2, new ATpTerm.Abstract(paramATpParam, arrayOfACsTerm));
/*     */   }
/*     */ 
/*     */   public static ACsTerm cloneCs(ACsTerm paramACsTerm) {
/* 135 */     Cloner localCloner = new Cloner();
/* 136 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/* 137 */     ACsTerm localACsTerm = localCloner.rewrite(paramACsTerm);
/* 138 */     assert (localCloner.tparams.isEmpty()) : localCloner.tparams;
/* 139 */     return localACsTerm;
/*     */   }
/*     */ 
/*     */   private Cloner()
/*     */   {
/* 151 */     this.tparams = new LinkedHashMap();
/* 152 */     freeze();
/*     */   }
/*     */ 
/*     */   public void rewrite(ATpParam[] paramArrayOfATpParam)
/*     */   {
/* 159 */     assert (frozen());
/* 160 */     throw new Error("---" + paramArrayOfATpParam);
/*     */   }
/*     */ 
/*     */   public void rewrite(ATpParam paramATpParam) {
/* 164 */     assert (frozen());
/* 165 */     throw new Error(paramATpParam.getKindName());
/*     */   }
/*     */ 
/*     */   public ACsTerm rewrite(ACsTerm.Function paramFunction)
/*     */   {
/* 172 */     assert (frozen());
/* 173 */     ATpParam[] arrayOfATpParam = clone(paramFunction.params);
/* 174 */     ATpTerm localATpTerm = rewrite(paramFunction.body);
/* 175 */     remove(paramFunction.params);
/* 176 */     return paramFunction.update(arrayOfATpParam, localATpTerm);
/*     */   }
/*     */ 
/*     */   public ATpTerm rewrite(ATpTerm.Abstract paramAbstract)
/*     */   {
/* 185 */     assert (frozen());
/* 186 */     TpLookup localTpLookup = lookup(paramAbstract.param);
/* 187 */     if (localTpLookup == null) return super.rewrite(paramAbstract);
/*     */     Object localObject1;
/* 188 */     switch (localTpLookup.getKind()) {
/*     */     case PARAMETER:
/* 190 */       localObject1 = (Cloner.TpLookup.Parameter)localTpLookup;
/* 191 */       return paramAbstract.update(((Cloner.TpLookup.Parameter)localObject1).param, rewrite(paramAbstract.args));
/*     */     case CONSTRUCTOR:
/* 194 */       localObject1 = (Cloner.TpLookup.Constructor)localTpLookup;
/* 195 */       ACsTerm localACsTerm = ((Cloner.TpLookup.Constructor)localObject1).term.evaluate();
/* 196 */       localACsTerm = Freezer.instance.rewrite(cloneCs(localACsTerm));
/*     */       Object localObject2;
/* 197 */       switch (localACsTerm.getKind()) {
/*     */       case FUNCTION:
/* 199 */         localObject2 = (ACsTerm.Function)localACsTerm;
/* 200 */         return Freezer.instance.rewrite(cloneTp(((ACsTerm.Function)localObject2).params, Freezer.instance.rewrite(rewrite(paramAbstract.args)), ((ACsTerm.Function)localObject2).body));
/*     */       case ERROR:
/* 213 */         localObject2 = (ACsTerm.Error)localACsTerm;
/* 214 */         return new ATpTerm.Error();
/*     */       }
/*     */ 
/* 217 */       throw new Error("" + localACsTerm.getKind());
/*     */     }
/*     */ 
/* 221 */     throw new Error("" + localTpLookup.getKind());
/*     */   }
/*     */ 
/*     */   private ATpParam[] clone(ATpParam[] paramArrayOfATpParam)
/*     */   {
/* 229 */     assert (frozen());
/* 230 */     if (paramArrayOfATpParam.length == 0) return paramArrayOfATpParam;
/* 231 */     ATpParam[] arrayOfATpParam1 = new ATpParam[paramArrayOfATpParam.length];
/* 232 */     for (int i = 0; i < arrayOfATpParam1.length; i++) {
/* 233 */       arrayOfATpParam1[i] = new ATpParam(paramArrayOfATpParam[i].getSource(), paramArrayOfATpParam[i].getPosition(), paramArrayOfATpParam[i].getName());
/*     */     }
/* 235 */     insert(paramArrayOfATpParam, arrayOfATpParam1);
/* 236 */     for (int i = 0; i < arrayOfATpParam1.length; i++) {
/* 237 */       ATpParam[] arrayOfATpParam2 = paramArrayOfATpParam[i].getParams();
/* 238 */       arrayOfATpParam1[i].setParams(clone(arrayOfATpParam2));
/* 239 */       arrayOfATpParam1[i].setBound(rewrite(paramArrayOfATpParam[i].getBound()));
/* 240 */       remove(arrayOfATpParam2);
/*     */     }
/* 242 */     return arrayOfATpParam1;
/*     */   }
/*     */ 
/*     */   private ACsTerm cloneToCs(ATpParam paramATpParam1, ATpParam paramATpParam2) {
/* 246 */     ATpParam[] arrayOfATpParam1 = paramATpParam1.getParams();
/* 247 */     ATpParam[] arrayOfATpParam2 = clone(arrayOfATpParam1);
/* 248 */     ACsTerm[] arrayOfACsTerm = cloneToCs(arrayOfATpParam1, arrayOfATpParam2);
/* 249 */     remove(arrayOfATpParam1);
/* 250 */     return new ACsTerm.Function(arrayOfATpParam2, new ATpTerm.Abstract(paramATpParam2, arrayOfACsTerm));
/*     */   }
/*     */ 
/*     */   private ACsTerm[] cloneToCs(ATpParam[] paramArrayOfATpParam1, ATpParam[] paramArrayOfATpParam2) {
/* 254 */     ACsTerm[] arrayOfACsTerm = new ACsTerm[paramArrayOfATpParam1.length];
/* 255 */     for (int i = 0; i < arrayOfACsTerm.length; i++) {
/* 256 */       ATpParam[] arrayOfATpParam = paramArrayOfATpParam1[i].getParams();
/* 257 */       arrayOfACsTerm[i] = cloneToCs(paramArrayOfATpParam1[i], paramArrayOfATpParam2[i]);
/*     */     }
/* 259 */     return arrayOfACsTerm;
/*     */   }
/*     */ 
/*     */   private TpLookup lookup(ATpParam paramATpParam)
/*     */   {
/* 266 */     return (TpLookup)this.tparams.get(paramATpParam);
/*     */   }
/*     */ 
/*     */   private void insert(ATpParam[] paramArrayOfATpParam1, ATpParam[] paramArrayOfATpParam2)
/*     */   {
/* 271 */     assert (paramArrayOfATpParam1.length == paramArrayOfATpParam2.length) : (paramArrayOfATpParam1.length + " - " + paramArrayOfATpParam2.length);
/* 272 */     for (int i = 0; i < paramArrayOfATpParam1.length; i++) {
/* 273 */       assert (!this.tparams.containsKey(paramArrayOfATpParam1[i])) : (this.tparams + " - " + paramArrayOfATpParam1[i]);
/* 274 */       this.tparams.put(paramArrayOfATpParam1[i], new Cloner.TpLookup.Parameter(paramArrayOfATpParam2[i]));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void insert(ATpParam[] paramArrayOfATpParam, ACsTerm[] paramArrayOfACsTerm)
/*     */   {
/* 280 */     assert (paramArrayOfATpParam.length == paramArrayOfACsTerm.length) : (paramArrayOfATpParam.length + " - " + paramArrayOfACsTerm.length);
/* 281 */     for (int i = 0; i < paramArrayOfATpParam.length; i++) {
/* 282 */       assert (!this.tparams.containsKey(paramArrayOfATpParam[i])) : (this.tparams + " - " + paramArrayOfATpParam[i]);
/* 283 */       this.tparams.put(paramArrayOfATpParam[i], new Cloner.TpLookup.Constructor(paramArrayOfACsTerm[i]));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void remove(ATpParam[] paramArrayOfATpParam) {
/* 288 */     for (ATpParam localATpParam : paramArrayOfATpParam) {
/* 289 */       assert (this.tparams.containsKey(localATpParam)) : (this.tparams + " - " + localATpParam);
/* 290 */       this.tparams.remove(localATpParam);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static abstract class TpLookup
/*     */   {
/*     */     public abstract Kind getKind();
/*     */ 
/*     */     public static class Constructor extends Cloner.TpLookup
/*     */     {
/*     */       public final ACsTerm term;
/*     */ 
/*     */       public Constructor(ACsTerm paramACsTerm)
/*     */       {
/* 311 */         super(); this.term = paramACsTerm; } 
/* 312 */       public Cloner.TpLookup.Kind getKind() { return Cloner.TpLookup.Kind.CONSTRUCTOR; }
/*     */ 
/*     */     }
/*     */ 
/*     */     public static class Parameter extends Cloner.TpLookup
/*     */     {
/*     */       public final ATpParam param;
/*     */ 
/*     */       public Parameter(ATpParam paramATpParam)
/*     */       {
/* 305 */         super(); this.param = paramATpParam; } 
/* 306 */       public Cloner.TpLookup.Kind getKind() { return Cloner.TpLookup.Kind.PARAMETER; }
/*     */ 
/*     */     }
/*     */ 
/*     */     public static enum Kind
/*     */     {
/* 299 */       PARAMETER, CONSTRUCTOR;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.Cloner
 * JD-Core Version:    0.6.2
 */