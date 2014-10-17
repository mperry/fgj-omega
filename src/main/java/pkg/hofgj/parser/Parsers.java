/*     */ package pkg.hofgj.parser;
/*     */ 
/*     */ import pkg.parse.ByteSet;
/*     */ import pkg.parse.ByteSetBuilder;
/*     */ import pkg.parse.Parser;
/*     */ 
/*     */ public class Parsers extends ParsersBase
/*     */ {
/*  38 */   public final ByteSet bsAny = bs().not().toSet(); public final ByteSet bsNoEOL = bs().add(10).add(13).not().toSet(); public final ByteSet bsWhiteSpace = bs().add(32).add(9).add(12).toSet(); public final ByteSet bsDigit09 = ByteSet.fromRange(48, 57); public final ByteSet bsDigit19 = ByteSet.fromRange(49, 57); public final ByteSet bsUpper = ByteSet.fromRange(65, 90); public final ByteSet bsLower = ByteSet.fromRange(97, 122); public final ByteSet bsAlpha = this.bsUpper.toBuilder().add(this.bsLower).toSet(); public final ByteSet bsAlphaNumeric = this.bsAlpha.toBuilder().add(this.bsDigit09).toSet(); public final ByteSet bsIdStart = this.bsAlpha.toBuilder().add(95).toSet(); public final ByteSet bsIdNext = this.bsAlphaNumeric.toBuilder().add(95).add(36).toSet();
/*     */ 
/*  65 */   public final Parser skipWhiteSpace = parseCharacter(this.bsWhiteSpace);
/*  66 */   public final Parser skipLineTerminator = parseAlternatives(new Parser[] { parseCharacter(10), parseSequence(new Parser[] { parseCharacter(13), parseZeroOrOne(parseCharacter(10)) }) });
/*     */ 
/*  71 */   public final Parser skipLineComment = parseSequence(new Parser[] { parseCharacter(47), parseCharacter(47), parseZeroOrMore(parseCharacter(this.bsNoEOL)) });
/*     */ 
/*  75 */   public final Parser skipBlockComment = parseString("/*!!!*/");
/*  76 */   public final Parser skipBlank = parseAlternatives(new Parser[] { this.skipWhiteSpace, this.skipLineTerminator, this.skipLineComment, this.skipBlockComment });
/*     */ 
/*  81 */   public final Parser skipBlanks = parseZeroOrMore(this.skipBlank);
/*     */ 
/*  83 */   public final Parser skipIdentifier = parseSequence(new Parser[] { parseCharacter(this.bsIdStart), parseZeroOrMore(parseCharacter(this.bsIdNext)) });
/*     */ 
/*  86 */   public final Parser skipInteger = parseAlternatives(new Parser[] { parseCharacter(48), parseSequence(new Parser[] { parseCharacter(this.bsDigit19), parseZeroOrMore(parseCharacter(this.bsDigit09)) }) });
/*     */ 
/*  92 */   public final Parser skipError = parseCharacter(this.bsAny);
/*     */ 
/*  95 */   public final Parser noIdNext = check(parseAlternatives(new Parser[] { parseEOF(), parseCharacter(this.bsIdNext.toBuilder().not().toSet()) }));
/*     */ 
/* 100 */   public final Parser tkFailure = parseFailure(); public final Parser tkEOF = parseToken(1, parseEOF(), false); public final Parser tkIdentifier = parseToken(10, this.skipIdentifier, true); public final Parser tkInteger = parseToken(11, this.skipInteger, true); public final Parser tkError = parseToken(99, this.skipError, true); public final Parser tkClass = parseKeyword(20, "class"); public final Parser tkInterface = parseKeyword(21, "interface"); public final Parser tkExtends = parseKeyword(23, "extends"); public final Parser tkImplements = parseKeyword(24, "implements"); public final Parser tkVal = parseKeyword(25, "val"); public final Parser tkDef = parseKeyword(26, "def"); public final Parser tkThis = parseKeyword(27, "this"); public final Parser tkSuper = parseKeyword(28, "super"); public final Parser tkNew = parseKeyword(29, "new"); public final Parser tkIf = parseKeyword(30, "if"); public final Parser tkElse = parseKeyword(31, "else"); public final Parser tkReturn = parseKeyword(32, "return"); public final Parser tkPublic = parseKeyword(33, "public"); public final Parser tkProtected = parseKeyword(34, "protected"); public final Parser tkPrivate = parseKeyword(35, "private"); public final Parser tkStatic = parseKeyword(36, "static"); public final Parser tkAbstract = parseKeyword(37, "abstract"); public final Parser tkFinal = parseKeyword(38, "final"); public final Parser tkBoolean = parseKeyword(39, "boolean"); public final Parser tkInt = parseKeyword(40, "int"); public final Parser tkLParen = parseString(50, "("); public final Parser tkRParen = parseString(51, ")"); public final Parser tkLBrack = parseString(52, "["); public final Parser tkRBrack = parseString(53, "]"); public final Parser tkLBrace = parseString(54, "{"); public final Parser tkRBrace = parseString(55, "}"); public final Parser tkComma = parseString(60, ","); public final Parser tkSemicolon = parseString(61, ";"); public final Parser tkColon = parseString(62, ":"); public final Parser tkHook = parseString(63, "?"); public final Parser tkDot = parseString(64, "."); public final Parser tkLtColon = parseString(65, "<:"); public final Parser tkEqGt = parseString(66, "=>"); public final Parser tkEq = parseString(67, "="); public final Parser tkINot = parseString(70, "~"); public final Parser tkZNot = parseString(71, "!"); public final Parser tkIAdd = parseString(72, "+"); public final Parser tkISub = parseString(73, "-"); public final Parser tkIMul = parseString(74, "*"); public final Parser tkIDiv = parseString(75, "/"); public final Parser tkIRem = parseString(76, "%"); public final Parser tkILsl = parseString(77, "<<"); public final Parser tkILsr = parseString(78, ">>>"); public final Parser tkIAsr = parseString(79, ">>"); public final Parser tkILt = parseString(80, "<"); public final Parser tkIGe = parseString(81, ">="); public final Parser tkILe = parseString(82, "<="); public final Parser tkIGt = parseString(83, ">"); public final Parser tkIEq = parseString(84, "=="); public final Parser tkINe = parseString(85, "!="); public final Parser tkIOr = parseString(86, "|"); public final Parser tkIXor = parseString(87, "^"); public final Parser tkIAnd = parseString(88, "&"); public final Parser tkZOr = parseString(89, "||"); public final Parser tkZXor = parseString(90, "^^"); public final Parser tkZAnd = parseString(91, "&&");
/*     */ 
/* 168 */   public final Parser readToken = parseAlternatives(new Parser[] { this.tkClass, this.scala ? this.tkFailure : this.tkInterface, this.tkExtends, this.scala ? this.tkFailure : this.tkImplements, this.scala ? this.tkVal : this.tkFailure, this.scala ? this.tkDef : this.tkFailure, this.tkThis, this.scala ? this.tkFailure : this.tkSuper, this.tkNew, this.tkIf, this.tkElse, this.scala ? this.tkFailure : this.tkReturn, this.scala ? this.tkFailure : this.tkPublic, this.scala ? this.tkFailure : this.tkProtected, this.scala ? this.tkFailure : this.tkPrivate, this.scala ? this.tkFailure : this.tkStatic, this.scala ? this.tkFailure : this.tkAbstract, this.scala ? this.tkFailure : this.tkFinal, this.scala ? this.tkFailure : this.tkBoolean, this.scala ? this.tkFailure : this.tkInt, this.tkLParen, this.tkRParen, this.tkLBrack, this.tkRBrack, this.tkLBrace, this.tkRBrace, this.tkComma, this.tkSemicolon, this.tkColon, this.tkHook, this.tkDot, this.tkLtColon, this.tkILsl, this.tkILe, this.tkILt, this.tkILsr, this.tkIAsr, this.tkIGe, this.tkIGt, this.tkEqGt, this.tkIEq, this.tkEq, this.tkINe, this.tkZNot, this.tkINot, this.tkIAdd, this.tkISub, this.tkIMul, this.tkIDiv, this.tkIRem, this.tkZOr, this.tkIOr, this.tkZXor, this.tkIXor, this.tkZAnd, this.tkIAnd, this.tkInteger, this.tkIdentifier, this.tkEOF, this.tkError });
/*     */ 
/* 230 */   public final Parser nextToken = parseSequence(new Parser[] { this.skipBlanks, this.readToken });
/*     */ 
/* 235 */   public final Parser program = this.nextToken;
/*     */ 
/*     */   public Parsers(boolean paramBoolean)
/*     */   {
/*  32 */     super(paramBoolean);
/*     */   }
/*     */ 
/*     */   private final Parser parseString(int paramInt, String paramString)
/*     */   {
/* 241 */     return parseToken(paramInt, parseString(paramString), false);
/*     */   }
/*     */ 
/*     */   private final Parser parseKeyword(int paramInt, String paramString) {
/* 245 */     return parseToken(paramInt, parseSequence(new Parser[] { parseString(paramString), this.noIdNext }), false);
/*     */   }
/*     */ 
/*     */   private final Parser parseToken(int paramInt, Parser paramParser, boolean paramBoolean) {
/* 249 */     if (paramBoolean) paramParser = buildString(paramParser);
/* 250 */     return parseSequence(new Parser[] { putValueI(paramInt), putCurrentIndex(), paramParser });
/*     */   }
/*     */ 
/*     */   private ByteSetBuilder bs()
/*     */   {
/* 257 */     return new ByteSetBuilder();
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.parser.Parsers
 * JD-Core Version:    0.6.2
 */