/*     */ package pkg.parse;
/*     */ 
/*     */ public class ParserFactory
/*     */ {
/*     */   public Parser parseFailure()
/*     */   {
/*   9 */     return new ParseFailure();
/*     */   }
/*     */ 
/*     */   public Parser parseNothing() {
/*  13 */     return new ParseNothing();
/*     */   }
/*     */ 
/*     */   public Parser parseEOF() {
/*  17 */     return new ParseEOF();
/*     */   }
/*     */ 
/*     */   public Parser parseCharacter(int paramInt) {
/*  21 */     return parseCharacter(paramInt, paramInt);
/*     */   }
/*     */ 
/*     */   public Parser parseCharacter(int paramInt1, int paramInt2) {
/*  25 */     return new ParseCharacterInRange(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public Parser parseCharacter(ByteSet paramByteSet) {
/*  29 */     return new ParseCharacterInU1Set(paramByteSet);
/*     */   }
/*     */ 
/*     */   public Parser parseString(String paramString) {
/*  33 */     return new ParseString(paramString);
/*     */   }
/*     */ 
/*     */   public Parser parseSequence(Parser[] paramArrayOfParser) {
/*  37 */     return new ParseSequence(paramArrayOfParser);
/*     */   }
/*     */ 
/*     */   public Parser parseAlternatives(Parser[] paramArrayOfParser) {
/*  41 */     return new ParseAlternatives(paramArrayOfParser);
/*     */   }
/*     */ 
/*     */   public Parser parseZeroOrOne(Parser paramParser) {
/*  45 */     return parseNToM(0, 1, paramParser);
/*     */   }
/*     */ 
/*     */   public Parser parseZeroOrMore(Parser paramParser) {
/*  49 */     return parseNToM(0, 2147483647, paramParser);
/*     */   }
/*     */ 
/*     */   public Parser parseZeroOrMore(Parser paramParser1, Parser paramParser2) {
/*  53 */     return parseNToM(0, 2147483647, paramParser1, paramParser2);
/*     */   }
/*     */ 
/*     */   public Parser parseOneOrMore(Parser paramParser) {
/*  57 */     return parseNToM(1, 2147483647, paramParser);
/*     */   }
/*     */ 
/*     */   public Parser parseOneOrMore(Parser paramParser1, Parser paramParser2) {
/*  61 */     return parseNToM(1, 2147483647, paramParser1, paramParser2);
/*     */   }
/*     */ 
/*     */   public Parser parseN(int paramInt, Parser paramParser) {
/*  65 */     return parseNToM(paramInt, paramInt, paramParser);
/*     */   }
/*     */ 
/*     */   public Parser parseN(int paramInt, Parser paramParser1, Parser paramParser2) {
/*  69 */     return parseNToM(paramInt, paramInt, paramParser1, paramParser2);
/*     */   }
/*     */ 
/*     */   public Parser parseNToM(int paramInt1, int paramInt2, Parser paramParser) {
/*  73 */     assert ((0 <= paramInt1) && (paramInt1 <= paramInt2)) : (paramInt1 + " - " + paramInt2);
/*  74 */     if (paramInt2 == 0) return parseNothing();
/*  75 */     if ((paramInt1 == 1) && (paramInt2 == 1)) return paramParser;
/*  76 */     return new ParseRepetition(paramParser, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public Parser parseNToM(int paramInt1, int paramInt2, Parser paramParser1, Parser paramParser2) {
/*  80 */     assert ((0 <= paramInt1) && (paramInt1 <= paramInt2)) : (paramInt1 + " - " + paramInt2);
/*  81 */     if (paramInt2 == 0) return parseNothing();
/*  82 */     if (paramInt2 == 1) return parseNToM(paramInt1, paramInt2, paramParser1);
/*  83 */     Parser localParser = parseSequence(new Parser[] { paramParser1, parseNToM(Math.max(0, paramInt1 - 1), paramInt2 - 1, parseSequence(new Parser[] { paramParser2, paramParser1 })) });
/*     */ 
/*  86 */     return paramInt1 == 0 ? parseZeroOrOne(localParser) : localParser;
/*     */   }
/*     */ 
/*     */   public Parser check(Parser paramParser)
/*     */   {
/*  93 */     return new Check(paramParser);
/*     */   }
/*     */ 
/*     */   public Parser buildString(Parser paramParser)
/*     */   {
/* 100 */     return new BuildString(paramParser);
/*     */   }
/*     */ 
/*     */   public Parser buildArray(Parser paramParser, Class<?> paramClass) {
/* 104 */     return new BuildArray(paramParser, paramClass);
/*     */   }
/*     */ 
/*     */   public Parser putCurrentIndex()
/*     */   {
/* 111 */     return new PutCurrentIndex();
/*     */   }
/*     */ 
/*     */   public Parser putValueI(int paramInt) {
/* 115 */     return new PutValueI(paramInt);
/*     */   }
/*     */ 
/*     */   public <Type> Parser putValueO(Type paramType) {
/* 119 */     return new PutValueO(paramType);
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.ParserFactory
 * JD-Core Version:    0.6.2
 */