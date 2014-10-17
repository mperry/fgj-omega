/*     */ package pkg.hofgj.compiler;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import pkg.util.Option;
/*     */ 
/*     */ public class Interpreter
/*     */ {
/*     */   private final Compiler compiler;
/*     */   private final Map<AField, Integer> fields;
/*     */   private Option<Value> self;
/*     */   private AVlParam[] params;
/*     */   private Value[] args;
/*     */ 
/*     */   public Interpreter(Compiler paramCompiler)
/*     */   {
/*  24 */     this.compiler = paramCompiler;
/*  25 */     this.fields = new HashMap();
/*     */   }
/*     */ 
/*     */   public Value getValueOf(AVlTerm paramAVlTerm)
/*     */   {
/*  32 */     this.self = Option.None();
/*  33 */     this.params = new AVlParam[0];
/*  34 */     this.args = new Value[0];
/*  35 */     Value localValue = evaluate(paramAVlTerm);
/*  36 */     this.args = null;
/*  37 */     this.params = null;
/*  38 */     this.self = null;
/*  39 */     return localValue;
/*     */   }
/*     */ 
/*     */   private Value[] evaluate(AVlTerm[] paramArrayOfAVlTerm)
/*     */   {
/*  46 */     Value[] arrayOfValue = new Value[paramArrayOfAVlTerm.length];
/*  47 */     for (int i = 0; i < arrayOfValue.length; i++)
/*  48 */       arrayOfValue[i] = evaluate(paramArrayOfAVlTerm[i]);
/*  49 */     return arrayOfValue;
/*     */   }
/*     */ 
/*     */   private Value evaluate(AVlTerm paramAVlTerm) {
/*  53 */     //switch (1.$SwitchMap$pkg$hofgj$compiler$AVlTerm$Kind[paramAVlTerm.getKind().ordinal()]) {
              switch (paramAVlTerm.getKind().ordinal()) {
              case 1:
/*  54 */       return evaluate((AVlTerm.Boolean)paramAVlTerm);
/*     */     case 2:
/*  55 */       return evaluate((AVlTerm.Integer)paramAVlTerm);
/*     */     case 3:
/*  56 */       return evaluate((AVlTerm.This)paramAVlTerm);
/*     */     case 4:
/*  57 */       return evaluate((AVlTerm.Local)paramAVlTerm);
/*     */     case 5:
/*  58 */       return evaluate((AVlTerm.Get)paramAVlTerm);
/*     */     case 6:
/*  59 */       return evaluate((AVlTerm.Call)paramAVlTerm);
/*     */     case 7:
/*  60 */       return evaluate((AVlTerm.New)paramAVlTerm);
/*     */     case 8:
/*  61 */       return evaluate((AVlTerm.If)paramAVlTerm);
/*     */     case 9:
/*  62 */       return evaluate((AVlTerm.Unary)paramAVlTerm);
/*     */     case 10:
/*  63 */       return evaluate((AVlTerm.Binary)paramAVlTerm);
/*     */     case 11:
/*  64 */       return evaluate(paramAVlTerm.evaluate()); }
/*  65 */     throw new Error("" + paramAVlTerm.getKind());
/*     */   }
/*     */ 
/*     */   private Value evaluate(AVlTerm.Boolean paramBoolean)
/*     */   {
/*  70 */     return mkBoolean(paramBoolean.value);
/*     */   }
/*     */ 
/*     */   private Value evaluate(AVlTerm.Integer paramInteger) {
/*  74 */     return mkInteger(paramInteger.value);
/*     */   }
/*     */ 
/*     */   private Value evaluate(AVlTerm.This paramThis) {
/*  78 */     assert (!this.self.isEmpty());
/*  79 */     return (Value)this.self.get();
/*     */   }
/*     */ 
/*     */   private Value evaluate(AVlTerm.Local paramLocal) {
/*  83 */     for (int i = 0; i < this.params.length; i++)
/*  84 */       if (this.params[i] == paramLocal.param) return this.args[i];
/*  85 */     throw new Error(this.compiler.toString(paramLocal));
/*     */   }
/*     */ 
/*     */   private Value evaluate(AVlTerm.Get paramGet) {
/*  89 */     Value.Object localObject = toObject(paramGet.object);
/*  90 */     int i = getFieldIndex(paramGet.field);
/*     */ 
/*  92 */     assert (i < localObject.vargs.length) : (this.compiler.toString(paramGet) + " - " + i + " - " + this.compiler.toString(localObject));
/*     */ 
/*  94 */     return localObject.vargs[i];
/*     */   }
/*     */ 
/*     */   private Value evaluate(AVlTerm.Call paramCall) {
/*  98 */     Value.Object localObject = toObject(paramCall.object);
/*  99 */     Option localOption1 = localObject.clasz.getMethod(paramCall.method.getName());
/*     */ 
/* 101 */     assert (!localOption1.isEmpty()) : this.compiler.toString(paramCall);
/* 102 */     AMethod localAMethod = (AMethod)localOption1.get();
/* 103 */     assert (!localAMethod.getBody().isEmpty()) : this.compiler.toString(paramCall);
/* 104 */     AVlTerm localAVlTerm = (AVlTerm)localAMethod.getBody().get();
/*     */ 
/* 106 */     localAVlTerm = Cloner.cloneVl(localAMethod.getTpParams(), paramCall.targs, localAVlTerm);
/* 107 */     Freezer.instance.rewrite(localAVlTerm);
/* 108 */     localAVlTerm = Cloner.cloneVl(localAMethod.getOwner().getParams(), this.compiler.getClassArgsFrom(localAMethod.getOwner(), new ATpTerm.Concrete(localObject.clasz, localObject.targs)), localAVlTerm);
/*     */ 
/* 114 */     Freezer.instance.rewrite(localAVlTerm);
/* 115 */     Value[] arrayOfValue1 = evaluate(paramCall.vargs);
/* 116 */     Option localOption2 = this.self;
/* 117 */     AVlParam[] arrayOfAVlParam = this.params;
/* 118 */     Value[] arrayOfValue2 = this.args;
/* 119 */     this.self = Option.Some(localObject);
/* 120 */     this.params = localAMethod.getVlParams();
/* 121 */     this.args = arrayOfValue1;
/* 122 */     Value localValue = evaluate(localAVlTerm);
/* 123 */     this.args = arrayOfValue2;
/* 124 */     this.params = arrayOfAVlParam;
/* 125 */     this.self = localOption2;
/* 126 */     return localValue;
/*     */   }
/*     */ 
/*     */   private Value evaluate(AVlTerm.New paramNew) {
/* 130 */     return mkObject(paramNew.clasz, paramNew.targs, evaluate(paramNew.vargs));
/*     */   }
/*     */ 
/*     */   private Value evaluate(AVlTerm.If paramIf) {
/* 134 */     return toBoolean(paramIf.test) ? evaluate(paramIf.success) : evaluate(paramIf.failure);
/*     */   }
/*     */ 
/*     */   private Value evaluate(AVlTerm.Unary paramUnary)
/*     */   {
/* 140 */     //switch (1.$SwitchMap$pkg$hofgj$compiler$AVlTerm$UnaryOperator[paramUnary.operator.ordinal()]) {
            switch (paramUnary.operator.ordinal()) {
              case 1:
/* 141 */       return mkInteger(toInteger(paramUnary.arg));
/*     */     case 2:
/* 142 */       return mkInteger(-toInteger(paramUnary.arg));
/*     */     case 3:
/* 143 */       return mkInteger(toInteger(paramUnary.arg) ^ 0xFFFFFFFF);
/*     */     case 4:
/* 144 */       return mkBoolean(!toBoolean(paramUnary.arg)); }
/* 145 */     throw new Error("" + paramUnary.operator);
/*     */   }
/*     */ 
/*     */   private Value evaluate(AVlTerm.Binary paramBinary)
/*     */   {
/* 150 */     AVlTerm localAVlTerm1 = paramBinary.argl;
/* 151 */     AVlTerm localAVlTerm2 = paramBinary.argr;
/* 152 */     //switch (1.$SwitchMap$pkg$hofgj$compiler$AVlTerm$BinaryOperator[paramBinary.operator.ordinal()]) {
            switch (paramBinary.operator.ordinal()) {
              case 1:
/* 153 */       return mkInteger(toInteger(localAVlTerm1) + toInteger(localAVlTerm2));
/*     */     case 2:
/* 154 */       return mkInteger(toInteger(localAVlTerm1) - toInteger(localAVlTerm2));
/*     */     case 3:
/* 155 */       return mkInteger(toInteger(localAVlTerm1) * toInteger(localAVlTerm2));
/*     */     case 4:
/* 156 */       return mkInteger(toInteger(localAVlTerm1) / toInteger(localAVlTerm2));
/*     */     case 5:
/* 157 */       return mkInteger(toInteger(localAVlTerm1) % toInteger(localAVlTerm2));
/*     */     case 6:
/* 158 */       return mkInteger(toInteger(localAVlTerm1) << toInteger(localAVlTerm2));
/*     */     case 7:
/* 159 */       return mkInteger(toInteger(localAVlTerm1) >>> toInteger(localAVlTerm2));
/*     */     case 8:
/* 160 */       return mkInteger(toInteger(localAVlTerm1) >> toInteger(localAVlTerm2));
/*     */     case 9:
/* 161 */       return mkBoolean(toInteger(localAVlTerm1) < toInteger(localAVlTerm2));
/*     */     case 10:
/* 162 */       return mkBoolean(toInteger(localAVlTerm1) >= toInteger(localAVlTerm2));
/*     */     case 11:
/* 163 */       return mkBoolean(toInteger(localAVlTerm1) <= toInteger(localAVlTerm2));
/*     */     case 12:
/* 164 */       return mkBoolean(toInteger(localAVlTerm1) > toInteger(localAVlTerm2));
/*     */     case 13:
/* 165 */       return mkBoolean(toInteger(localAVlTerm1) == toInteger(localAVlTerm2));
/*     */     case 14:
/* 166 */       return mkBoolean(toInteger(localAVlTerm1) != toInteger(localAVlTerm2));
/*     */     case 15:
/* 167 */       return mkInteger(toInteger(localAVlTerm1) | toInteger(localAVlTerm2));
/*     */     case 16:
/* 168 */       return mkInteger(toInteger(localAVlTerm1) ^ toInteger(localAVlTerm2));
/*     */     case 17:
/* 169 */       return mkInteger(toInteger(localAVlTerm1) & toInteger(localAVlTerm2));
/*     */     case 18:
/* 170 */       return mkBoolean((toBoolean(localAVlTerm1)) || (toBoolean(localAVlTerm2)));
/*     */     case 19:
/* 171 */       return mkBoolean(toBoolean(localAVlTerm1) ^ toBoolean(localAVlTerm2));
/*     */     case 20:
/* 172 */       return mkBoolean((toBoolean(localAVlTerm1)) && (toBoolean(localAVlTerm2))); }
/* 173 */     throw new Error("" + paramBinary.operator);
/*     */   }
/*     */ 
/*     */   private Value.Object toObject(AVlTerm paramAVlTerm)
/*     */   {
/* 181 */     return toObject(evaluate(paramAVlTerm));
/*     */   }
/*     */ 
/*     */   private boolean toBoolean(AVlTerm paramAVlTerm) {
/* 185 */     return toBoolean(evaluate(paramAVlTerm));
/*     */   }
/*     */ 
/*     */   private int toInteger(AVlTerm paramAVlTerm) {
/* 189 */     return toInteger(evaluate(paramAVlTerm));
/*     */   }
/*     */ 
/*     */   private Value.Object toObject(Value paramValue) {
/* 193 */     assert ((paramValue instanceof Value.Object)) : this.compiler.toString(paramValue);
/* 194 */     return (Value.Object)paramValue;
/*     */   }
/*     */ 
/*     */   private boolean toBoolean(Value paramValue) {
/* 198 */     assert ((paramValue instanceof Value.Boolean)) : this.compiler.toString(paramValue);
/* 199 */     return ((Value.Boolean)paramValue).value;
/*     */   }
/*     */ 
/*     */   private int toInteger(Value paramValue) {
/* 203 */     assert ((paramValue instanceof Value.Integer)) : this.compiler.toString(paramValue);
/* 204 */     return ((Value.Integer)paramValue).value;
/*     */   }
/*     */ 
/*     */   private Value mkObject(AClass paramAClass, ACsTerm[] paramArrayOfACsTerm, Value[] paramArrayOfValue) {
/* 208 */     return new Value.Object(paramAClass, paramArrayOfACsTerm, paramArrayOfValue);
/*     */   }
/*     */ 
/*     */   private Value mkBoolean(boolean paramBoolean) {
/* 212 */     return new Value.Boolean(paramBoolean);
/*     */   }
/*     */ 
/*     */   private Value mkInteger(int paramInt) {
/* 216 */     return new Value.Integer(paramInt);
/*     */   }
/*     */ 
/*     */   private int getFieldIndex(AField paramAField)
/*     */   {
/* 223 */     Integer localInteger = (Integer)this.fields.get(paramAField);
/* 224 */     if (localInteger != null) return localInteger.intValue();
/* 225 */     AField[] arrayOfAField = paramAField.getOwner().getFields(0);
/* 226 */     for (int i = 0; i < arrayOfAField.length; i++) {
/* 227 */       if (arrayOfAField[i] == paramAField) {
/* 228 */         this.fields.put(paramAField, Integer.valueOf(i));
/* 229 */         return i;
/*     */       }
/*     */     }
/* 232 */     throw new Error(paramAField.getOwner() + " - " + paramAField);
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.Interpreter
 * JD-Core Version:    0.6.2
 */