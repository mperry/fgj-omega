/*     */ package pkg.hofgj.parser;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import pkg.hofgj.Source;
/*     */ import pkg.hofgj.stree.SClass;
/*     */ import pkg.hofgj.stree.SClassBase;
/*     */ import pkg.hofgj.stree.SClassBody;
/*     */ import pkg.hofgj.stree.SField;
/*     */ import pkg.hofgj.stree.SLabel;
/*     */ import pkg.hofgj.stree.SMember;
/*     */ import pkg.hofgj.stree.SMember.Class;
/*     */ import pkg.hofgj.stree.SMember.Field;
/*     */ import pkg.hofgj.stree.SMember.Method;
/*     */ import pkg.hofgj.stree.SMethod;
/*     */ import pkg.hofgj.stree.SModifier;
/*     */ import pkg.hofgj.stree.SProgram;
/*     */ import pkg.hofgj.stree.SSlotBody;
/*     */ import pkg.hofgj.stree.SSlotBound;
/*     */ import pkg.hofgj.stree.SSlotType;
/*     */ import pkg.hofgj.stree.STpArgList;
/*     */ import pkg.hofgj.stree.STpParam;
/*     */ import pkg.hofgj.stree.STpParamList;
/*     */ import pkg.hofgj.stree.STpSelector;
/*     */ import pkg.hofgj.stree.STpTerm;
/*     */ import pkg.hofgj.stree.STpTerm.Abs;
/*     */ import pkg.hofgj.stree.STpTerm.App;
/*     */ import pkg.hofgj.stree.STpTerm.Ref;
/*     */ import pkg.hofgj.stree.STree;
/*     */ import pkg.hofgj.stree.SVlArgList;
/*     */ import pkg.hofgj.stree.SVlParam;
/*     */ import pkg.hofgj.stree.SVlParamList;
/*     */ import pkg.hofgj.stree.SVlRef;
/*     */ import pkg.hofgj.stree.SVlSelector;
/*     */ import pkg.hofgj.stree.SVlTerm;
/*     */ import pkg.hofgj.stree.SVlTerm.Binary;
/*     */ import pkg.hofgj.stree.SVlTerm.BinaryOperator;
/*     */ import pkg.hofgj.stree.SVlTerm.Block;
/*     */ import pkg.hofgj.stree.SVlTerm.If;
/*     */ import pkg.hofgj.stree.SVlTerm.Integer;
/*     */ import pkg.hofgj.stree.SVlTerm.New;
/*     */ import pkg.hofgj.stree.SVlTerm.Ref;
/*     */ import pkg.hofgj.stree.SVlTerm.Sel;
/*     */ import pkg.hofgj.stree.SVlTerm.This;
/*     */ import pkg.hofgj.stree.SVlTerm.Unary;
/*     */ import pkg.hofgj.stree.SVlTerm.UnaryOperator;
/*     */ import pkg.util.Option;
/*     */ 
/*     */ public class Parser
/*     */ {
/*     */   private final Source source;
/*     */   private final int[] tokens;
/*     */   private final int[] positions;
/*     */   private final String[] strings;
/*     */   private int index;
/*     */ 
/*     */   public Parser(Source paramSource, int[] paramArrayOfInt1, int[] paramArrayOfInt2, String[] paramArrayOfString)
/*     */   {
/*  55 */     this.source = paramSource;
/*  56 */     this.tokens = paramArrayOfInt1;
/*  57 */     this.positions = paramArrayOfInt2;
/*  58 */     this.strings = paramArrayOfString;
/*     */   }
/*     */ 
/*     */   public Option<SProgram> readProgram()
/*     */   {
/*     */     try
/*     */     {
/*  66 */       SProgram localSProgram = parseProgram();
/*  67 */       skip(1);
/*  68 */       return Option.Some(localSProgram);
/*     */     } catch (ParseError localParseError) {
/*  70 */       this.source.error(localParseError.position, "parse error\n  expected: " + localParseError.expected + "\n  found   : " + localParseError.actual);
/*     */     }
/*     */ 
/*  73 */     return Option.None();
/*     */   }
/*     */ 
/*     */   public SProgram parseProgram()
/*     */   {
/*  84 */     return (SProgram)set(position(), new SProgram(parseMembers(), token() == 1 ? Option.None() : Option.Some(parseVlTerm())));
/*     */   }
/*     */ 
/*     */   public SMember[] parseMembers()
/*     */   {
/* 101 */     ArrayList localArrayList = new ArrayList();
/*     */     while (true) {
/* 103 */       switch (token()) {
/*     */       case 20:
/* 105 */         localArrayList.add(set(position(), new SMember.Class(parseClass())));
/* 106 */         break;
/*     */       case 25:
/* 108 */         localArrayList.add(set(position(), new SMember.Field(parseField())));
/* 109 */         break;
/*     */       case 26:
/* 111 */         localArrayList.add(set(position(), new SMember.Method(parseMethod())));
/* 112 */         break;
/*     */       case 61:
/* 114 */         skip();
/*     */       }
/*     */     }
/* 117 */     return (SMember[])localArrayList.toArray(new SMember[localArrayList.size()]);
/*     */   }
/*     */ 
/*     */   public SClass parseClass()
/*     */   {
/* 128 */     return (SClass)set(skip(20), new SClass(new SModifier[0], false, parseLabel(), parseTpParamListSct(), parseClassBaseSct(), new SClassBase[0], parseClassBodySct()));
/*     */   }
/*     */ 
/*     */   public SField parseField()
/*     */   {
/* 143 */     return (SField)set(skip(25), new SField(new SModifier[0], parseLabel(), parseSlotTypeSct(), parseSlotBodySct()));
/*     */   }
/*     */ 
/*     */   public SMethod parseMethod()
/*     */   {
/* 156 */     return (SMethod)set(skip(26), new SMethod(new SModifier[0], parseLabel(), parseTpParamListSct(), parseVlParamListSct(), parseSlotTypeSct(), parseSlotBodySct()));
/*     */   }
/*     */ 
/*     */   public SClassBase[] parseClassBaseSct()
/*     */   {
/* 170 */     return token() == 23 ? new SClassBase[] { parseClassBase() } : new SClassBase[0];
/*     */   }
/*     */ 
/*     */   public Option<SClassBody> parseClassBodySct()
/*     */   {
/* 179 */     return token() == 54 ? Option.Some(parseClassBody()) : Option.None();
/*     */   }
/*     */ 
/*     */   public SClassBase parseClassBase()
/*     */   {
/* 188 */     return (SClassBase)set(skip(23), new SClassBase(parseLabel(), parseTpArgListSct()));
/*     */   }
/*     */ 
/*     */   public SClassBody parseClassBody()
/*     */   {
/* 196 */     return (SClassBody)skip(set(skip(54), new SClassBody(parseMembers())), 55);
/*     */   }
/*     */ 
/*     */   public Option<SSlotBound> parseSlotBoundSct()
/*     */   {
/* 206 */     return token() == 65 ? Option.Some(parseSlotBound()) : Option.None();
/*     */   }
/*     */ 
/*     */   public Option<SSlotType> parseSlotTypeSct()
/*     */   {
/* 215 */     return token() == 62 ? Option.Some(parseSlotType()) : Option.None();
/*     */   }
/*     */ 
/*     */   public Option<SSlotBody> parseSlotBodySct()
/*     */   {
/* 224 */     return token() == 67 ? Option.Some(parseSlotBody()) : Option.None();
/*     */   }
/*     */ 
/*     */   public SSlotBound parseSlotBound()
/*     */   {
/* 233 */     return (SSlotBound)set(skip(65), new SSlotBound(parseTpTerm()));
/*     */   }
/*     */ 
/*     */   public SSlotType parseSlotType()
/*     */   {
/* 240 */     return (SSlotType)set(skip(62), new SSlotType(parseTpTerm()));
/*     */   }
/*     */ 
/*     */   public SSlotBody parseSlotBody()
/*     */   {
/* 247 */     return (SSlotBody)set(skip(67), new SSlotBody(parseVlTerm()));
/*     */   }
/*     */ 
/*     */   public Option<STpParamList> parseTpParamListSct()
/*     */   {
/* 254 */     return token() == 52 ? Option.Some(parseTpParamList()) : Option.None();
/*     */   }
/*     */ 
/*     */   public Option<SVlParamList> parseVlParamListSct()
/*     */   {
/* 263 */     return token() == 50 ? Option.Some(parseVlParamList()) : Option.None();
/*     */   }
/*     */ 
/*     */   public STpParamList parseTpParamList()
/*     */   {
/* 272 */     int i = skip(52);
/* 273 */     ArrayList localArrayList = new ArrayList();
/* 274 */     if (token() != 53) {
/* 275 */       localArrayList.add(parseTpParam());
/* 276 */       while (skipIf(60)) localArrayList.add(parseTpParam());
/*     */     }
/* 278 */     skip(53);
/* 279 */     return (STpParamList)set(i, new STpParamList((STpParam[])localArrayList.toArray(new STpParam[localArrayList.size()])));
/*     */   }
/*     */ 
/*     */   public SVlParamList parseVlParamList()
/*     */   {
/* 287 */     int i = skip(50);
/* 288 */     ArrayList localArrayList = new ArrayList();
/* 289 */     if (token() != 51) {
/* 290 */       localArrayList.add(parseVlParam());
/* 291 */       while (skipIf(60)) localArrayList.add(parseVlParam());
/*     */     }
/* 293 */     skip(51);
/* 294 */     return (SVlParamList)set(i, new SVlParamList((SVlParam[])localArrayList.toArray(new SVlParam[localArrayList.size()])));
/*     */   }
/*     */ 
/*     */   public STpParam parseTpParam()
/*     */   {
/* 302 */     return (STpParam)set(position(), new STpParam(parseLabel(), parseTpParamListSct(), parseSlotBoundSct()));
/*     */   }
/*     */ 
/*     */   public SVlParam parseVlParam()
/*     */   {
/* 313 */     return (SVlParam)set(position(), new SVlParam(parseLabel(), parseSlotTypeSct()));
/*     */   }
/*     */ 
/*     */   public Option<STpArgList> parseTpArgListSct()
/*     */   {
/* 323 */     return token() == 52 ? Option.Some(parseTpArgList()) : Option.None();
/*     */   }
/*     */ 
/*     */   public Option<SVlArgList> parseVlArgListSct()
/*     */   {
/* 332 */     return token() == 50 ? Option.Some(parseVlArgList()) : Option.None();
/*     */   }
/*     */ 
/*     */   public STpArgList parseTpArgList()
/*     */   {
/* 341 */     int i = skip(52);
/* 342 */     ArrayList localArrayList = new ArrayList();
/* 343 */     if (token() != 53) {
/* 344 */       localArrayList.add(parseTpTerm());
/* 345 */       while (skipIf(60)) localArrayList.add(parseTpTerm());
/*     */     }
/* 347 */     skip(53);
/* 348 */     return (STpArgList)set(i, new STpArgList((STpTerm[])localArrayList.toArray(new STpTerm[localArrayList.size()])));
/*     */   }
/*     */ 
/*     */   public SVlArgList parseVlArgList()
/*     */   {
/* 356 */     int i = skip(50);
/* 357 */     ArrayList localArrayList = new ArrayList();
/* 358 */     if (token() != 51) {
/* 359 */       localArrayList.add(parseVlTerm());
/* 360 */       while (skipIf(60)) localArrayList.add(parseVlTerm());
/*     */     }
/* 362 */     skip(51);
/* 363 */     return (SVlArgList)set(i, new SVlArgList((SVlTerm[])localArrayList.toArray(new SVlTerm[localArrayList.size()])));
/*     */   }
/*     */ 
/*     */   public STpTerm parseTpTerm()
/*     */   {
/* 373 */     int i = position();
/* 374 */     switch (token()) {
/*     */     case 52:
/* 376 */       STpParamList localSTpParamList = parseTpParamList();
/* 377 */       skip(66);
/* 378 */       return (STpTerm)set(i, new STpTerm.Abs(localSTpParamList, parseTpTerm()));
/*     */     case 10:
/* 380 */       SLabel localSLabel = parseLabel();
/* 381 */       ArrayList localArrayList = new ArrayList();
/* 382 */       while (token() == 64) localArrayList.add(parseTpSelector());
/* 383 */       STpTerm localSTpTerm = (STpTerm)set(i, new STpTerm.Ref(localSLabel, (STpSelector[])localArrayList.toArray(new STpSelector[localArrayList.size()])));
/*     */ 
/* 387 */       while (token() == 52)
/* 388 */         localSTpTerm = (STpTerm)set(position(), new STpTerm.App(localSTpTerm, parseTpArgList()));
/* 389 */       return localSTpTerm;
/*     */     case 50:
/* 391 */       skip();
/* 392 */       return (STpTerm)skip(parseTpTerm(), 51);
/*     */     }
/* 394 */     throw new ParseError(i, "a type term", token());
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlTerm()
/*     */   {
/* 403 */     switch (token()) {
/*     */     case 30:
/* 405 */       int i = skip();
/* 406 */       skip(50);
/* 407 */       SVlTerm localSVlTerm1 = parseVlTerm();
/* 408 */       skip(51);
/* 409 */       SVlTerm localSVlTerm2 = parseVlTerm();
/* 410 */       skip(31);
/* 411 */       return (SVlTerm)set(i, new SVlTerm.If(localSVlTerm1, localSVlTerm2, parseVlTerm()));
/*     */     }
/* 413 */     return parseVlConditional();
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlConditional()
/*     */   {
/* 422 */     SVlTerm localSVlTerm1 = parseVlZOr();
/* 423 */     switch (token()) {
/*     */     case 63:
/* 425 */       int i = skip();
/* 426 */       SVlTerm localSVlTerm2 = parseVlConditional();
/* 427 */       skip(62);
/* 428 */       SVlTerm localSVlTerm3 = parseVlConditional();
/* 429 */       return (SVlTerm)set(i, new SVlTerm.If(localSVlTerm1, localSVlTerm2, localSVlTerm3));
/*     */     }
/* 431 */     return localSVlTerm1;
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlZOr()
/*     */   {
/* 440 */     SVlTerm localSVlTerm = parseVlZXor();
/*     */     while (true) {
/* 442 */       switch (token()) {
/*     */       case 89:
/* 444 */         localSVlTerm = mkBinary(token(), skip(), localSVlTerm, parseVlZXor());
/*     */       }
/*     */     }
/* 447 */     return localSVlTerm;
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlZXor()
/*     */   {
/* 457 */     SVlTerm localSVlTerm = parseVlZAnd();
/*     */     while (true) {
/* 459 */       switch (token()) {
/*     */       case 90:
/* 461 */         localSVlTerm = mkBinary(token(), skip(), localSVlTerm, parseVlZAnd());
/*     */       }
/*     */     }
/* 464 */     return localSVlTerm;
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlZAnd()
/*     */   {
/* 474 */     SVlTerm localSVlTerm = parseVlIOr();
/*     */     while (true) {
/* 476 */       switch (token()) {
/*     */       case 91:
/* 478 */         localSVlTerm = mkBinary(token(), skip(), localSVlTerm, parseVlIOr());
/*     */       }
/*     */     }
/* 481 */     return localSVlTerm;
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlIOr()
/*     */   {
/* 491 */     SVlTerm localSVlTerm = parseVlIXor();
/*     */     while (true) {
/* 493 */       switch (token()) {
/*     */       case 86:
/* 495 */         localSVlTerm = mkBinary(token(), skip(), localSVlTerm, parseVlIXor());
/*     */       }
/*     */     }
/* 498 */     return localSVlTerm;
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlIXor()
/*     */   {
/* 508 */     SVlTerm localSVlTerm = parseVlIAnd();
/*     */     while (true) {
/* 510 */       switch (token()) {
/*     */       case 87:
/* 512 */         localSVlTerm = mkBinary(token(), skip(), localSVlTerm, parseVlIAnd());
/*     */       }
/*     */     }
/* 515 */     return localSVlTerm;
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlIAnd()
/*     */   {
/* 525 */     SVlTerm localSVlTerm = parseVlEquality();
/*     */     while (true) {
/* 527 */       switch (token()) {
/*     */       case 88:
/* 529 */         localSVlTerm = mkBinary(token(), skip(), localSVlTerm, parseVlEquality());
/*     */       }
/*     */     }
/* 532 */     return localSVlTerm;
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlEquality()
/*     */   {
/* 543 */     SVlTerm localSVlTerm = parseVlRelational();
/*     */     while (true) {
/* 545 */       switch (token()) {
/*     */       case 84:
/*     */       case 85:
/* 548 */         localSVlTerm = mkBinary(token(), skip(), localSVlTerm, parseVlRelational());
/*     */       }
/*     */     }
/* 551 */     return localSVlTerm;
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlRelational()
/*     */   {
/* 564 */     SVlTerm localSVlTerm = parseVlShift();
/*     */     while (true) {
/* 566 */       switch (token()) {
/*     */       case 80:
/*     */       case 81:
/*     */       case 82:
/*     */       case 83:
/* 571 */         localSVlTerm = mkBinary(token(), skip(), localSVlTerm, parseVlShift());
/*     */       }
/*     */     }
/* 574 */     return localSVlTerm;
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlShift()
/*     */   {
/* 586 */     SVlTerm localSVlTerm = parseVlAdditive();
/*     */     while (true) {
/* 588 */       switch (token()) {
/*     */       case 77:
/*     */       case 78:
/*     */       case 79:
/* 592 */         localSVlTerm = mkBinary(token(), skip(), localSVlTerm, parseVlAdditive());
/*     */       }
/*     */     }
/* 595 */     return localSVlTerm;
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlAdditive()
/*     */   {
/* 606 */     SVlTerm localSVlTerm = parseVlMultiplicative();
/*     */     while (true) {
/* 608 */       switch (token()) {
/*     */       case 72:
/*     */       case 73:
/* 611 */         localSVlTerm = mkBinary(token(), skip(), localSVlTerm, parseVlMultiplicative());
/*     */       }
/*     */     }
/* 614 */     return localSVlTerm;
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlMultiplicative()
/*     */   {
/* 626 */     SVlTerm localSVlTerm = parseVlUnary();
/*     */     while (true) {
/* 628 */       switch (token()) {
/*     */       case 74:
/*     */       case 75:
/*     */       case 76:
/* 632 */         localSVlTerm = mkBinary(token(), skip(), localSVlTerm, parseVlUnary());
/*     */       }
/*     */     }
/* 635 */     return localSVlTerm;
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlUnary()
/*     */   {
/* 648 */     switch (token()) {
/*     */     case 70:
/*     */     case 71:
/*     */     case 72:
/*     */     case 73:
/* 653 */       return mkUnary(token(), skip(), parseVlUnary());
/*     */     }
/* 655 */     return parseVlPrimary();
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlPrimary()
/*     */   {
/* 663 */     SVlTerm localSVlTerm = parseVlSimple();
/* 664 */     while (token() == 64) {
/* 665 */       localSVlTerm = (SVlTerm)set(position(), new SVlTerm.Sel(localSVlTerm, parseVlSelector()));
/*     */     }
/* 667 */     return localSVlTerm;
/*     */   }
/*     */ 
/*     */   public SVlTerm parseVlSimple()
/*     */   {
/* 679 */     switch (token()) {
/*     */     case 11:
/* 681 */       return (SVlTerm)set(position(), new SVlTerm.Integer(readInteger()));
/*     */     case 27:
/* 683 */       return (SVlTerm)set(skip(), new SVlTerm.This());
/*     */     case 29:
/* 685 */       return (SVlTerm)set(skip(), new SVlTerm.New(parseLabel(), parseTpArgListSct(), parseVlArgListSct(), parseClassBodySct()));
/*     */     case 10:
/* 692 */       return (SVlTerm)set(position(), new SVlTerm.Ref(parseVlRef()));
/*     */     case 54:
/* 694 */       return (SVlTerm)skip(set(skip(), new SVlTerm.Block(parseMembers(), parseVlTerm())), 55);
/*     */     case 50:
/* 699 */       skip();
/* 700 */       return (SVlTerm)skip(parseVlTerm(), 51);
/*     */     }
/* 702 */     throw new ParseError(position(), "a value term", token());
/*     */   }
/*     */ 
/*     */   public STpSelector parseTpSelector()
/*     */   {
/* 710 */     return (STpSelector)set(skip(64), new STpSelector(parseLabel()));
/*     */   }
/*     */ 
/*     */   public SVlSelector parseVlSelector()
/*     */   {
/* 717 */     return (SVlSelector)set(skip(64), new SVlSelector(parseVlRef()));
/*     */   }
/*     */ 
/*     */   public SVlRef parseVlRef()
/*     */   {
/* 724 */     return (SVlRef)set(position(), new SVlRef(parseLabel(), parseTpArgListSct(), parseVlArgListSct()));
/*     */   }
/*     */ 
/*     */   public SLabel parseLabel()
/*     */   {
/* 735 */     String str = string();
/* 736 */     return (SLabel)set(skip(10), new SLabel(str));
/*     */   }
/*     */ 
/*     */   private int readInteger()
/*     */   {
/* 743 */     int i = position();
/* 744 */     String str = string();
/* 745 */     skip(11);
/*     */     try {
/* 747 */       return Integer.parseInt(str);
/*     */     } catch (NumberFormatException localNumberFormatException) {
/* 749 */       this.source.error(i, "integer constant '" + str + "' is too big");
/*     */     }
/* 751 */     return 0;
/*     */   }
/*     */ 
/*     */   private boolean skipIf(int paramInt)
/*     */   {
/* 756 */     if (token() != paramInt) return false;
/* 757 */     skip();
/* 758 */     return true;
/*     */   }
/*     */ 
/*     */   private <Tree> Tree skip(Tree paramTree, int paramInt) {
/* 762 */     skip(paramInt);
/* 763 */     return paramTree;
/*     */   }
/*     */ 
/*     */   private int skip(int paramInt) {
/* 767 */     if (token() != paramInt)
/* 768 */       throw new ParseError(position(), paramInt, token());
/* 769 */     return skip();
/*     */   }
/*     */ 
/*     */   private int skip() {
/* 773 */     int i = position();
/* 774 */     this.index += 1;
/* 775 */     return i;
/*     */   }
/*     */ 
/*     */   private int position() {
/* 779 */     return this.positions[this.index];
/*     */   }
/*     */ 
/*     */   private int token() {
/* 783 */     return this.tokens[this.index];
/*     */   }
/*     */ 
/*     */   private String string() {
/* 787 */     return this.strings[this.index];
/*     */   }
/*     */ 
/*     */   private SVlTerm.UnaryOperator getUnaryOperator(int paramInt)
/*     */   {
/* 794 */     switch (paramInt) { case 72:
/* 795 */       return SVlTerm.UnaryOperator.IPOS;
/*     */     case 73:
/* 796 */       return SVlTerm.UnaryOperator.INEG;
/*     */     case 70:
/* 797 */       return SVlTerm.UnaryOperator.INOT;
/*     */     case 71:
/* 798 */       return SVlTerm.UnaryOperator.ZNOT; }
/* 799 */     throw new Error(Tokens.toString(paramInt));
/*     */   }
/*     */ 
/*     */   private SVlTerm.BinaryOperator getBinaryOperator(int paramInt)
/*     */   {
/* 804 */     switch (paramInt) { case 72:
/* 805 */       return SVlTerm.BinaryOperator.IADD;
/*     */     case 73:
/* 806 */       return SVlTerm.BinaryOperator.ISUB;
/*     */     case 74:
/* 807 */       return SVlTerm.BinaryOperator.IMUL;
/*     */     case 75:
/* 808 */       return SVlTerm.BinaryOperator.IDIV;
/*     */     case 76:
/* 809 */       return SVlTerm.BinaryOperator.IREM;
/*     */     case 77:
/* 810 */       return SVlTerm.BinaryOperator.ILSL;
/*     */     case 78:
/* 811 */       return SVlTerm.BinaryOperator.ILSR;
/*     */     case 79:
/* 812 */       return SVlTerm.BinaryOperator.IASR;
/*     */     case 80:
/* 813 */       return SVlTerm.BinaryOperator.ILT;
/*     */     case 81:
/* 814 */       return SVlTerm.BinaryOperator.IGE;
/*     */     case 82:
/* 815 */       return SVlTerm.BinaryOperator.ILE;
/*     */     case 83:
/* 816 */       return SVlTerm.BinaryOperator.IGT;
/*     */     case 84:
/* 817 */       return SVlTerm.BinaryOperator.IEQ;
/*     */     case 85:
/* 818 */       return SVlTerm.BinaryOperator.INE;
/*     */     case 86:
/* 819 */       return SVlTerm.BinaryOperator.IOR;
/*     */     case 87:
/* 820 */       return SVlTerm.BinaryOperator.IXOR;
/*     */     case 88:
/* 821 */       return SVlTerm.BinaryOperator.IAND;
/*     */     case 89:
/* 822 */       return SVlTerm.BinaryOperator.ZOR;
/*     */     case 90:
/* 823 */       return SVlTerm.BinaryOperator.ZXOR;
/*     */     case 91:
/* 824 */       return SVlTerm.BinaryOperator.ZAND; }
/* 825 */     throw new Error(Tokens.toString(paramInt));
/*     */   }
/*     */ 
/*     */   private SVlTerm mkUnary(int paramInt1, int paramInt2, SVlTerm paramSVlTerm)
/*     */   {
/* 833 */     return (SVlTerm)set(paramInt2, new SVlTerm.Unary(getUnaryOperator(paramInt1), paramSVlTerm));
/*     */   }
/*     */ 
/*     */   private SVlTerm mkBinary(int paramInt1, int paramInt2, SVlTerm paramSVlTerm1, SVlTerm paramSVlTerm2)
/*     */   {
/* 839 */     return (SVlTerm)set(paramInt2, new SVlTerm.Binary(paramSVlTerm1, getBinaryOperator(paramInt1), paramSVlTerm2));
/*     */   }
/*     */ 
/*     */   private static <Tree extends STree> Tree set(int paramInt, Tree paramTree)
/*     */   {
/* 844 */     paramTree.position(paramInt);
/* 845 */     return paramTree;
/*     */   }
/*     */ 
/*     */   private static class ParseError extends Error
/*     */   {
/*     */     private static final long serialVersionUID = 0L;
/*     */     public final int position;
/*     */     public final String expected;
/*     */     public final String actual;
/*     */ 
/*     */     public ParseError(int paramInt, String paramString1, String paramString2)
/*     */     {
/* 860 */       this.position = paramInt;
/* 861 */       this.expected = paramString1;
/* 862 */       this.actual = paramString2;
/*     */     }
/*     */ 
/*     */     public ParseError(int paramInt1, String paramString, int paramInt2) {
/* 866 */       this(paramInt1, paramString, Tokens.toString(paramInt2));
/*     */     }
/*     */ 
/*     */     public ParseError(int paramInt1, int paramInt2, int paramInt3) {
/* 870 */       this(paramInt1, Tokens.toString(paramInt2), paramInt3);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.parser.Parser
 * JD-Core Version:    0.6.2
 */