/*      */ package pkg.hofgj.parser;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import pkg.hofgj.Source;
/*      */ import pkg.hofgj.stree.SClass;
/*      */ import pkg.hofgj.stree.SClassBase;
/*      */ import pkg.hofgj.stree.SClassBody;
/*      */ import pkg.hofgj.stree.SConstructor;
/*      */ import pkg.hofgj.stree.SField;
/*      */ import pkg.hofgj.stree.SLabel;
/*      */ import pkg.hofgj.stree.SMember;
/*      */ import pkg.hofgj.stree.SMember.Class;
/*      */ import pkg.hofgj.stree.SMember.Constructor;
/*      */ import pkg.hofgj.stree.SMember.Field;
/*      */ import pkg.hofgj.stree.SMember.Method;
/*      */ import pkg.hofgj.stree.SMethod;
/*      */ import pkg.hofgj.stree.SModifier;
/*      */ import pkg.hofgj.stree.SModifier.IsAbstract;
/*      */ import pkg.hofgj.stree.SModifier.IsFinal;
/*      */ import pkg.hofgj.stree.SModifier.IsPrivate;
/*      */ import pkg.hofgj.stree.SModifier.IsProtected;
/*      */ import pkg.hofgj.stree.SModifier.IsPublic;
/*      */ import pkg.hofgj.stree.SModifier.IsStatic;
/*      */ import pkg.hofgj.stree.SProgram;
/*      */ import pkg.hofgj.stree.SSlotBody;
/*      */ import pkg.hofgj.stree.SSlotBound;
/*      */ import pkg.hofgj.stree.SSlotType;
/*      */ import pkg.hofgj.stree.STpArgList;
/*      */ import pkg.hofgj.stree.STpParam;
/*      */ import pkg.hofgj.stree.STpParamList;
/*      */ import pkg.hofgj.stree.STpSelector;
/*      */ import pkg.hofgj.stree.STpTerm;
/*      */ import pkg.hofgj.stree.STpTerm.Abs;
/*      */ import pkg.hofgj.stree.STpTerm.App;
/*      */ import pkg.hofgj.stree.STpTerm.Boolean;
/*      */ import pkg.hofgj.stree.STpTerm.Integer;
/*      */ import pkg.hofgj.stree.STpTerm.Ref;
/*      */ import pkg.hofgj.stree.STree;
/*      */ import pkg.hofgj.stree.SVlArgList;
/*      */ import pkg.hofgj.stree.SVlParam;
/*      */ import pkg.hofgj.stree.SVlParamList;
/*      */ import pkg.hofgj.stree.SVlRef;
/*      */ import pkg.hofgj.stree.SVlSelector;
/*      */ import pkg.hofgj.stree.SVlTerm;
/*      */ import pkg.hofgj.stree.SVlTerm.Binary;
/*      */ import pkg.hofgj.stree.SVlTerm.BinaryOperator;
/*      */ import pkg.hofgj.stree.SVlTerm.Block;
/*      */ //import pkg.hofgj.stree.SVlTerm.Boolean;
/*      */ import pkg.hofgj.stree.SVlTerm.If;
/*      */ //import pkg.hofgj.stree.SVlTerm.Integer;
/*      */ import pkg.hofgj.stree.SVlTerm.New;
/*      */ //import pkg.hofgj.stree.SVlTerm.Ref;
/*      */ import pkg.hofgj.stree.SVlTerm.Sel;
/*      */ import pkg.hofgj.stree.SVlTerm.This;
/*      */ import pkg.hofgj.stree.SVlTerm.Unary;
/*      */ import pkg.hofgj.stree.SVlTerm.UnaryOperator;
/*      */ import pkg.util.Option;
/*      */ 
/*      */ public class JavaParser
/*      */ {
/*      */   private final Source source;
/*      */   private final int[] tokens;
/*      */   private final int[] positions;
/*      */   private final String[] strings;
/*      */   private int index;
/*      */   private int tp_level;
/*      */   private int tp_countl;
/*      */   private int tp_countr;
/*      */   private int tp_position;
/*      */ 
/*      */   public JavaParser(Source paramSource, int[] paramArrayOfInt1, int[] paramArrayOfInt2, String[] paramArrayOfString)
/*      */   {
/*   61 */     this.source = paramSource;
/*   62 */     this.tokens = paramArrayOfInt1;
/*   63 */     this.positions = paramArrayOfInt2;
/*   64 */     this.strings = paramArrayOfString;
/*      */   }
/*      */ 
/*      */   public Option<SProgram> readProgram()
/*      */   {
/*      */     try
/*      */     {
/*   72 */       SProgram localSProgram = parseProgram();
/*   73 */       skip(1);
/*   74 */       return Option.Some(localSProgram);
/*      */     } catch (ParseError localParseError) {
/*   76 */       this.source.error(localParseError.position, "parse error\n  expected: " + localParseError.expected + "\n  found   : " + localParseError.actual);
/*      */     }
/*      */ 
/*   79 */     return Option.None();
/*      */   }
/*      */ 
/*      */   public SProgram parseProgram()
/*      */   {
                int p = position();
                SMember[] pm = parseMembers();
                int t = token();
                Option<SVlTerm> o = t == 1 ? Option.None() : Option.Some(parseStatement());
                return mkProgram(p, pm, o);

///*   90 */     return mkProgram(position(), parseMembers(), token() == 1 ? Option.None() : Option.Some(parseStatement()));
/*      */   }
/*      */ 
/*      */   public SModifier[] parseModifiers()
/*      */   {
/*  106 */     ArrayList localArrayList = new ArrayList();
///*      */     while (true) {
/*  108 */       switch (token()) {
/*      */       case 33:
/*  110 */         localArrayList.add(mkIsPublic(skip()));
/*  111 */         break;
/*      */       case 34:
/*  113 */         localArrayList.add(mkIsProtected(skip()));
/*  114 */         break;
/*      */       case 35:
/*  116 */         localArrayList.add(mkIsPrivate(skip()));
/*  117 */         break;
/*      */       case 36:
/*  119 */         localArrayList.add(mkIsStatic(skip()));
/*  120 */         break;
/*      */       case 37:
/*  122 */         localArrayList.add(mkIsAbstract(skip()));
/*  123 */         break;
/*      */       case 38:
/*  125 */         localArrayList.add(mkIsFinal(skip()));
/*      */       }
///*      */     }
/*  128 */     return mkModifierArray(localArrayList);
/*      */   }
/*      */ 
/*      */   public SMember[] parseMembers()
/*      */   {
/*  137 */     ArrayList localArrayList = new ArrayList();
///*      */     while (true) {
/*  139 */       switch (token()) {
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*  146 */         localArrayList.add(parseMember(parseModifiers()));
/*  147 */         break;
/*      */       case 61:
/*  149 */         skip();
/*  150 */         break;
/*      */       case 10:
/*      */       case 20:
/*      */       case 21:
/*      */       case 39:
/*      */       case 40:
/*      */       case 50:
/*      */       case 77:
/*      */       case 80:
/*  158 */         localArrayList.add(parseMember(mkModifierArray(0)));
/*      */       }
///*      */     }
/*  161 */     return mkMemberArray(localArrayList);
/*      */   }
/*      */ 
/*      */   public SMember parseMember(SModifier[] paramArrayOfSModifier)
/*      */   {
/*  172 */     switch (token()) {
/*      */     case 20:
/*  174 */       return mkClass(position(), parseClass(paramArrayOfSModifier));
/*      */     case 21:
/*  176 */       return mkClass(position(), parseInterface(paramArrayOfSModifier));
/*      */     case 10:
/*      */     case 39:
/*      */     case 40:
/*      */     case 50:
/*      */     case 77:
/*      */     case 80:
/*  182 */       return parseFieldOrMethod(paramArrayOfSModifier);
/*      */     }
/*  184 */     throw new ParseError(position(), "a member declaration", token());
/*      */   }
/*      */ 
/*      */   public SClass parseClass(SModifier[] paramArrayOfSModifier)
/*      */   {
/*  197 */     return mkClass(skip(20), paramArrayOfSModifier, parseLabel(), parseTpParamListSct(), skipIf(23) ? parseClassBases() : mkClassBaseArray(0), skipIf(24) ? parseClassBases() : mkClassBaseArray(0), parseClassBody());
/*      */   }
/*      */ 
/*      */   public SClass parseInterface(SModifier[] paramArrayOfSModifier)
/*      */   {
/*  212 */     return mkInterface(skip(21), paramArrayOfSModifier, parseLabel(), parseTpParamListSct(), skipIf(23) ? parseClassBases() : mkClassBaseArray(0), parseClassBody());
/*      */   }
/*      */ 
/*      */   public SClassBase[] parseClassBases()
/*      */   {
/*  225 */     ArrayList localArrayList = new ArrayList();
/*  226 */     localArrayList.add(parseClassBase());
/*  227 */     while (skipIf(60)) localArrayList.add(parseClassBase());
/*  228 */     return mkClassBaseArray(localArrayList);
/*      */   }
/*      */ 
/*      */   public SClassBase parseClassBase()
/*      */   {
/*  235 */     return mkClassBase(position(), parseLabel(), parseTpArgListSct());
/*      */   }
/*      */ 
/*      */   public SClassBody parseClassBody()
/*      */   {
/*  242 */     return (SClassBody)skip(mkClassBody(skip(54), parseMembers()), 55);
/*      */   }
/*      */ 
/*      */   public SMember parseFieldOrMethod(SModifier[] paramArrayOfSModifier)
/*      */   {
/*  255 */     switch (token()) {
/*      */     case 10:
/*  257 */       return parseFieldOrMethod(paramArrayOfSModifier, position(), parseLabel());
/*      */     case 39:
/*      */     case 40:
/*      */     case 50:
/*  262 */       return parseFieldOrMethod(paramArrayOfSModifier, position(), parseTpTerm(), parseLabel());
/*      */     case 77:
/*      */     case 80:
/*  265 */       return mkMethod(position(), parseMethod(paramArrayOfSModifier));
/*      */     }
/*  267 */     throw new ParseError(position(), "a field or method declaration", token());
/*      */   }
/*      */ 
/*      */   public SMember parseFieldOrMethod(SModifier[] paramArrayOfSModifier, int paramInt, SLabel paramSLabel)
/*      */   {
/*  274 */     switch (token()) {
/*      */     case 50:
/*  276 */       return mkConstructor(position(), parseConstructor(paramArrayOfSModifier, paramInt, Option.None(), paramSLabel));
/*      */     }
/*      */ 
/*  280 */     return parseFieldOrMethod(paramArrayOfSModifier, paramInt, parseTpTerm(paramInt, paramSLabel), parseLabel());
/*      */   }
/*      */ 
/*      */   public SMember parseFieldOrMethod(SModifier[] paramArrayOfSModifier, int paramInt, STpTerm paramSTpTerm, SLabel paramSLabel)
/*      */   {
/*  288 */     switch (token()) {
/*      */     case 61:
/*      */     case 67:
/*  291 */       return mkField(paramInt, parseField(paramArrayOfSModifier, paramInt, paramSTpTerm, paramSLabel));
/*      */     case 50:
/*  294 */       return mkMethod(paramInt, parseMethod(paramArrayOfSModifier, paramInt, Option.None(), paramSTpTerm, paramSLabel));
/*      */     }
/*      */ 
/*  298 */     throw new ParseError(position(), "a field or method signature", token());
/*      */   }
/*      */ 
/*      */   public SField parseField(SModifier[] paramArrayOfSModifier)
/*      */   {
/*  307 */     return parseField(paramArrayOfSModifier, position(), parseTpTerm(), parseLabel());
/*      */   }
/*      */ 
/*      */   public SField parseField(SModifier[] paramArrayOfSModifier, int paramInt, STpTerm paramSTpTerm, SLabel paramSLabel)
/*      */   {
/*  313 */     return mkField(paramInt, paramArrayOfSModifier, paramSTpTerm, paramSLabel, token() == 67 ? Option.Some(mkFieldValue(skip(67), parseVlTerm())) : Option.None());
/*      */   }
/*      */ 
/*      */   public SMethod parseMethod(SModifier[] paramArrayOfSModifier)
/*      */   {
/*  323 */     return parseMethod(paramArrayOfSModifier, position(), parseTpParamListSct(), parseTpTerm(), parseLabel());
/*      */   }
/*      */ 
/*      */   public SMethod parseMethod(SModifier[] paramArrayOfSModifier, int paramInt, Option<STpParamList> paramOption, STpTerm paramSTpTerm, SLabel paramSLabel)
/*      */   {
/*  329 */     return mkMethod(paramInt, paramArrayOfSModifier, paramOption, paramSTpTerm, paramSLabel, parseVlParamList(), parseMethodBody());
/*      */   }
/*      */ 
/*      */   public Option<SSlotBody> parseMethodBody()
/*      */   {
/*  338 */     switch (token()) {
/*      */     case 54:
/*  340 */       return Option.Some(mkMethodBody(position(), parseBlock()));
/*      */     case 61:
/*  342 */       return Option.None();
/*      */     }
/*  344 */     throw new ParseError(position(), "a method body", token());
/*      */   }
/*      */ 
/*      */   public SConstructor parseConstructor(SModifier[] paramArrayOfSModifier)
/*      */   {
/*  354 */     return parseConstructor(paramArrayOfSModifier, position(), parseTpParamListSct(), parseLabel());
/*      */   }
/*      */ 
/*      */   public SConstructor parseConstructor(SModifier[] paramArrayOfSModifier, int paramInt, Option<STpParamList> paramOption, SLabel paramSLabel)
/*      */   {
/*  360 */     SVlParamList localSVlParamList = parseVlParamList();
/*  361 */     skip(54);
/*  362 */     skip(28);
/*  363 */     skip(50);
/*  364 */     ArrayList localArrayList1 = new ArrayList();
/*  365 */     if (token() != 51) {
/*  366 */       localArrayList1.add(parseLabel());
/*  367 */       while (skipIf(60)) localArrayList1.add(parseLabel());
/*      */     }
/*  369 */     skip(51);
/*  370 */     skip(61);
/*  371 */     ArrayList localArrayList2 = new ArrayList();
/*  372 */     ArrayList localArrayList3 = new ArrayList();
/*  373 */     while (skipIf(27)) {
/*  374 */       skip(64);
/*  375 */       localArrayList2.add(parseLabel());
/*  376 */       skip(67);
/*  377 */       localArrayList3.add(parseLabel());
/*  378 */       skip(61);
/*      */     }
/*  380 */     skip(55);
/*  381 */     return mkConstructor(paramInt, paramArrayOfSModifier, paramOption, paramSLabel, localSVlParamList, mkLabelArray(localArrayList1), mkLabelArray(localArrayList2), mkLabelArray(localArrayList3));
/*      */   }
/*      */ 
/*      */   public SVlTerm parseBlock()
/*      */   {
/*  392 */     return mkVlBlock(skip(54), parseMembers(), parseStatement(), skip(55));
/*      */   }
/*      */ 
/*      */   public SVlTerm parseStatement()
/*      */   {
                int z = 0;
/*  405 */     switch (token()) {
/*      */     case 30:
/*  407 */       return mkVlIf(skip(), skip(50), parseVlTerm(), skip(51), parseStatement(), skip(31), parseStatement());
/*      */     case 32:
/*  411 */       skip();
/*  412 */       SVlTerm localSVlTerm = parseVlTerm();
/*  413 */       parseTerminator();
/*  414 */       return localSVlTerm;
/*      */     case 54:
/*  416 */       return parseBlock();
/*      */     }
/*  418 */     throw new ParseError(position(), "a statement", token());
/*      */   }
/*      */ 
/*      */   public int parseTerminator()
/*      */   {
/*  426 */     int i = skip(61);
/*  427 */     while (token() == 61) skip();
/*  428 */     return i;
/*      */   }
/*      */ 
/*      */   public Option<STpParamList> parseTpParamListSct()
/*      */   {
/*  438 */     return isTpL() ? Option.Some(parseTpParamList()) : Option.None();
/*      */   }
/*      */ 
/*      */   public STpParamList parseTpParamList()
/*      */   {
/*  447 */     int i = skipTpL();
/*  448 */     ArrayList localArrayList = new ArrayList();
/*  449 */     if (!isTpR()) {
/*  450 */       localArrayList.add(parseTpParam());
/*  451 */       while (skipIf(60)) localArrayList.add(parseTpParam());
/*      */     }
/*  453 */     skipTpR();
/*  454 */     return mkTpParamList(i, mkTpParamArray(localArrayList));
/*      */   }
/*      */ 
/*      */   public STpParam parseTpParam()
/*      */   {
/*  461 */     return mkTpParam(position(), parseLabel(), parseTpParamListSct(), token() == 23 ? Option.Some(mkTpParamBound(skip(), parseTpTerm())) : Option.None());
/*      */   }
/*      */ 
/*      */   public SVlParamList parseVlParamList()
/*      */   {
/*  472 */     int i = skip(50);
/*  473 */     ArrayList localArrayList = new ArrayList();
/*  474 */     if (token() != 51) {
/*  475 */       localArrayList.add(parseVlParam());
/*  476 */       while (skipIf(60)) localArrayList.add(parseVlParam());
/*      */     }
/*  478 */     skip(51);
/*  479 */     return mkVlParamList(i, mkVlParamArray(localArrayList));
/*      */   }
/*      */ 
/*      */   public SVlParam parseVlParam()
/*      */   {
/*  486 */     return mkVlParam(position(), parseTpTerm(), parseLabel());
/*      */   }
/*      */ 
/*      */   public STpTerm parseTpTerm()
/*      */   {
/*  500 */     switch (token()) {
/*      */     case 39:
/*  502 */       return mkTpBoolean(skip());
/*      */     case 40:
/*  504 */       return mkTpInteger(skip());
/*      */     case 10:
/*  506 */       return parseTpTerm(position(), parseLabel());
/*      */     case 50:
/*  508 */       return mkTpApp(skip(), parseTpTerm(), skip(51), parseTpArgListSct());
/*      */     case 77:
/*      */     case 80:
/*  511 */       return parseTpTerm(position(), parseTpParamList());
/*      */     }
/*  513 */     throw new ParseError(position(), "a type term", token());
/*      */   }
/*      */ 
/*      */   public STpTerm parseTpTerm(int paramInt, SLabel paramSLabel) {
/*  517 */     return mkTpApp(mkTpRef(paramInt, paramSLabel), parseTpArgListSct());
/*      */   }
/*      */   public STpTerm parseTpTerm(int paramInt, STpParamList paramSTpParamList) {
/*  520 */     return mkTpAbs(paramInt, paramSTpParamList, skip(66), parseTpTerm());
/*      */   }
/*      */ 
/*      */   public Option<STpArgList> parseTpArgListSct()
/*      */   {
/*  527 */     return isTpL() ? Option.Some(parseTpArgList()) : Option.None();
/*      */   }
/*      */ 
/*      */   public STpArgList parseTpArgList()
/*      */   {
/*  536 */     int i = skipTpL();
/*  537 */     ArrayList localArrayList = new ArrayList();
/*  538 */     if (!isTpR()) {
/*  539 */       localArrayList.add(parseTpTerm());
/*  540 */       while (skipIf(60)) localArrayList.add(parseTpTerm());
/*      */     }
/*  542 */     skipTpR();
/*  543 */     return mkTpArgList(i, mkTpTermArray(localArrayList));
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlTerm()
/*      */   {
/*  553 */     switch (token()) {
/*      */     }
/*  555 */     return parseVlConditional();
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlConditional()
/*      */   {
/*  564 */     SVlTerm localSVlTerm = parseVlZOr();
/*  565 */     switch (token()) {
/*      */     case 63:
/*  567 */       return mkVlIf(localSVlTerm, skip(), parseVlConditional(), skip(62), parseVlConditional());
/*      */     }
/*      */ 
/*  570 */     return localSVlTerm;
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlZOr()
/*      */   {
/*  579 */     SVlTerm localSVlTerm = parseVlZXor();
///*      */     while (true) {
/*  581 */       switch (token()) {
/*      */       case 89:
/*  583 */         localSVlTerm = mkVlBinary(token(), skip(), localSVlTerm, parseVlZXor());
/*      */       }
///*      */     }
/*  586 */     return localSVlTerm;
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlZXor()
/*      */   {
/*  596 */     SVlTerm localSVlTerm = parseVlZAnd();
///*      */     while (true) {
/*  598 */       switch (token()) {
/*      */       case 90:
/*  600 */         localSVlTerm = mkVlBinary(token(), skip(), localSVlTerm, parseVlZAnd());
/*      */       }
///*      */     }
/*  603 */     return localSVlTerm;
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlZAnd()
/*      */   {
/*  613 */     SVlTerm localSVlTerm = parseVlIOr();
///*      */     while (true) {
/*  615 */       switch (token()) {
/*      */       case 91:
/*  617 */         localSVlTerm = mkVlBinary(token(), skip(), localSVlTerm, parseVlIOr());
/*      */       }
///*      */     }
/*  620 */     return localSVlTerm;
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlIOr()
/*      */   {
/*  630 */     SVlTerm localSVlTerm = parseVlIXor();
///*      */     while (true) {
/*  632 */       switch (token()) {
/*      */       case 86:
/*  634 */         localSVlTerm = mkVlBinary(token(), skip(), localSVlTerm, parseVlIXor());
/*      */       }
///*      */     }
/*  637 */     return localSVlTerm;
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlIXor()
/*      */   {
/*  647 */     SVlTerm localSVlTerm = parseVlIAnd();
///*      */     while (true) {
/*  649 */       switch (token()) {
/*      */       case 87:
/*  651 */         localSVlTerm = mkVlBinary(token(), skip(), localSVlTerm, parseVlIAnd());
/*      */       }
///*      */     }
/*  654 */     return localSVlTerm;
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlIAnd()
/*      */   {
/*  664 */     SVlTerm localSVlTerm = parseVlEquality();
///*      */     while (true) {
/*  666 */       switch (token()) {
/*      */       case 88:
/*  668 */         localSVlTerm = mkVlBinary(token(), skip(), localSVlTerm, parseVlEquality());
/*      */       }
///*      */     }
/*  671 */     return localSVlTerm;
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlEquality()
/*      */   {
/*  682 */     SVlTerm localSVlTerm = parseVlRelational();
///*      */     while (true) {
/*  684 */       switch (token()) {
/*      */       case 84:
/*      */       case 85:
/*  687 */         localSVlTerm = mkVlBinary(token(), skip(), localSVlTerm, parseVlRelational());
/*      */       }
///*      */     }
/*  690 */     return localSVlTerm;
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlRelational()
/*      */   {
/*  703 */     SVlTerm localSVlTerm = parseVlShift();
///*      */     while (true) {
/*  705 */       switch (token()) {
/*      */       case 80:
/*      */       case 81:
/*      */       case 82:
/*      */       case 83:
/*  710 */         localSVlTerm = mkVlBinary(token(), skip(), localSVlTerm, parseVlShift());
/*      */       }
///*      */     }
/*  713 */     return localSVlTerm;
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlShift()
/*      */   {
/*  725 */     SVlTerm localSVlTerm = parseVlAdditive();
///*      */     while (true) {
/*  727 */       switch (token()) {
/*      */       case 77:
/*      */       case 78:
/*      */       case 79:
/*  731 */         localSVlTerm = mkVlBinary(token(), skip(), localSVlTerm, parseVlAdditive());
/*      */       }
///*      */     }
/*  734 */     return localSVlTerm;
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlAdditive()
/*      */   {
/*  745 */     SVlTerm localSVlTerm = parseVlMultiplicative();
///*      */     while (true) {
/*  747 */       switch (token()) {
/*      */       case 72:
/*      */       case 73:
/*  750 */         localSVlTerm = mkVlBinary(token(), skip(), localSVlTerm, parseVlMultiplicative());
/*      */       }
///*      */     }
/*  753 */     return localSVlTerm;
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlMultiplicative()
/*      */   {
/*  765 */     SVlTerm localSVlTerm = parseVlUnary();
///*      */     while (true) {
/*  767 */       switch (token()) {
/*      */       case 74:
/*      */       case 75:
/*      */       case 76:
/*  771 */         localSVlTerm = mkVlBinary(token(), skip(), localSVlTerm, parseVlUnary());
/*      */       }
///*      */     }
/*  774 */     return localSVlTerm;
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlUnary()
/*      */   {
/*  787 */     switch (token()) {
/*      */     case 70:
/*      */     case 71:
/*      */     case 72:
/*      */     case 73:
/*  792 */       return mkVlUnary(token(), skip(), parseVlUnary());
/*      */     }
/*  794 */     return parseVlPrimary();
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlPrimary()
/*      */   {
/*  802 */     SVlTerm localSVlTerm = parseVlSimple();
/*  803 */     while (token() == 64) {
/*  804 */       localSVlTerm = mkVlSel(position(), localSVlTerm, mkVlSelector(skip(64), parseVlRef()));
/*      */     }
/*      */ 
/*  807 */     return localSVlTerm;
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlSimple()
/*      */   {
/*  818 */     switch (token()) {
/*      */     case 11:
/*  820 */       return mkVlInteger(position(), readInteger());
/*      */     case 27:
/*  822 */       return mkVlThis(skip());
/*      */     case 10:
/*      */     case 77:
/*      */     case 80:
/*  825 */       return mkVlRef(position(), parseVlRef());
/*      */     case 29:
/*  827 */       return parseVlNew();
/*      */     case 50:
/*  829 */       skip();
/*  830 */       return (SVlTerm)skip(parseVlTerm(), 51);
/*      */     }
/*  832 */     throw new ParseError(position(), "a value term", token());
/*      */   }
/*      */ 
/*      */   public SVlRef parseVlRef()
/*      */   {
/*  840 */     return mkVlRef(position(), parseTpArgListSct(), parseLabel(), parseVlArgListSct());
/*      */   }
/*      */ 
/*      */   public SVlTerm parseVlNew()
/*      */   {
/*  851 */     return mkVlNew(skip(29), parseLabel(), parseTpArgListSct(), parseVlArgList(), token() == 54 ? Option.Some(parseClassBody()) : Option.None());
/*      */   }
/*      */ 
/*      */   public Option<SVlArgList> parseVlArgListSct()
/*      */   {
/*  865 */     return token() == 50 ? Option.Some(parseVlArgList()) : Option.None();
/*      */   }
/*      */ 
/*      */   public SVlArgList parseVlArgList()
/*      */   {
/*  874 */     int i = skip(50);
/*  875 */     ArrayList localArrayList = new ArrayList();
/*  876 */     if (token() != 51) {
/*  877 */       localArrayList.add(parseVlTerm());
/*  878 */       while (skipIf(60)) localArrayList.add(parseVlTerm());
/*      */     }
/*  880 */     skip(51);
/*  881 */     return mkVlArgList(i, mkVlTermArray(localArrayList));
/*      */   }
/*      */ 
/*      */   public SLabel parseLabel()
/*      */   {
/*  891 */     return mkLabel(string(), skip(10));
/*      */   }
/*      */ 
/*      */   private boolean isTpL()
/*      */   {
/*  898 */     return (this.tp_countl > 0) || (token() == 80) || (token() == 77);
/*      */   }
/*      */ 
/*      */   private boolean isTpR()
/*      */   {
/*  904 */     assert (this.tp_level > 0) : (this.tp_level + " - " + this.tp_countr + " -  " + this.tp_position);
/*  905 */     if (this.tp_countr > 0) return true;
/*  906 */     if ((this.tp_level >= 3) && (token() == 78)) return true;
/*  907 */     if ((this.tp_level >= 2) && (token() == 79)) return true;
/*  908 */     return token() == 83;
/*      */   }
/*      */ 
/*      */   private int readInteger() {
/*  912 */     int i = position();
/*  913 */     String str = string();
/*  914 */     skip(11);
/*      */     try {
/*  916 */       return java.lang.Integer.parseInt(str);
/*      */     } catch (NumberFormatException localNumberFormatException) {
/*  918 */       this.source.error(i, "integer constant '" + str + "' is too big");
/*      */     }
/*  920 */     return 0;
/*      */   }
/*      */ 
/*      */   private boolean skipIf(int paramInt)
/*      */   {
/*  925 */     if (token() != paramInt) return false;
/*  926 */     skip();
/*  927 */     return true;
/*      */   }
/*      */ 
/*      */   private <Tree> Tree skip(Tree paramTree, int paramInt) {
/*  931 */     skip(paramInt);
/*  932 */     return paramTree;
/*      */   }
/*      */ 
/*      */   private int skipTpL() {
/*  936 */     assert (this.tp_countr == 0) : this.tp_countr;
/*  937 */     this.tp_level += 1;
/*      */     int i;
/*  938 */     if (this.tp_countl > 0) {
/*  939 */       i = this.tp_position;
/*  940 */       this.tp_position = (i + 1);
/*  941 */       this.tp_countl -= 1;
/*  942 */       return i;
/*      */     }
/*  944 */     if (token() == 77) {
/*  945 */       i = skip(77);
/*  946 */       this.tp_position = (i + 1);
/*  947 */       this.tp_countl = 1;
/*  948 */       return i;
/*      */     }
/*  950 */     return skip(80);
/*      */   }
/*      */ 
/*      */   private int skipTpR() {
/*  954 */     assert (this.tp_countl == 0) : this.tp_countl;
/*  955 */     assert (this.tp_level > 0) : (this.tp_level + " - " + this.tp_countr + " -  " + this.tp_position);
/*  956 */     this.tp_level -= 1;
/*      */     int i;
/*  957 */     if (this.tp_countr > 0) {
/*  958 */       i = this.tp_position;
/*  959 */       this.tp_position = (i + 1);
/*  960 */       this.tp_countr -= 1;
/*  961 */       return i;
/*      */     }
/*  963 */     if ((this.tp_level >= 2) && (token() == 78)) {
/*  964 */       i = skip(78);
/*  965 */       this.tp_position = (i + 1);
/*  966 */       this.tp_countr = 2;
/*  967 */       return i;
/*      */     }
/*  969 */     if ((this.tp_level >= 1) && (token() == 79)) {
/*  970 */       i = skip(79);
/*  971 */       this.tp_position = (i + 1);
/*  972 */       this.tp_countr = 1;
/*  973 */       return i;
/*      */     }
/*  975 */     return skip(83);
/*      */   }
/*      */ 
/*      */   private int skip(int paramInt) {
/*  979 */     if (token() != paramInt)
/*  980 */       throw new ParseError(position(), paramInt, token());
/*  981 */     return skip();
/*      */   }
/*      */ 
/*      */   private int skip() {
/*  985 */     assert (this.tp_countl == 0) : (this.tp_level + " - " + this.tp_countl + " -  " + this.tp_position);
/*  986 */     assert (this.tp_countr == 0) : (this.tp_level + " - " + this.tp_countr + " -  " + this.tp_position);
/*  987 */     int i = position();
/*  988 */     this.index += 1;
/*  989 */     return i;
/*      */   }
/*      */ 
/*      */   private int position() {
/*  993 */     if (this.tp_countl != 0) return this.tp_position;
/*  994 */     if (this.tp_countr != 0) return this.tp_position;
/*  995 */     return this.positions[this.index];
/*      */   }
/*      */ 
/*      */   private int token() {
/*  999 */     if (this.tp_countl != 0) return 80;
/* 1000 */     if (this.tp_countr != 0) return 83;
/* 1001 */     return this.tokens[this.index];
/*      */   }
/*      */ 
/*      */   private String string() {
/* 1005 */     return this.strings[this.index];
/*      */   }
/*      */ 
/*      */   private static SVlTerm.UnaryOperator getUnaryOperator(int paramInt)
/*      */   {
/* 1012 */     switch (paramInt) { case 72:
/* 1013 */       return SVlTerm.UnaryOperator.IPOS;
/*      */     case 73:
/* 1014 */       return SVlTerm.UnaryOperator.INEG;
/*      */     case 70:
/* 1015 */       return SVlTerm.UnaryOperator.INOT;
/*      */     case 71:
/* 1016 */       return SVlTerm.UnaryOperator.ZNOT; }
/* 1017 */     throw new Error(Tokens.toString(paramInt));
/*      */   }
/*      */ 
/*      */   private static SVlTerm.BinaryOperator getBinaryOperator(int paramInt)
/*      */   {
/* 1022 */     switch (paramInt) { case 72:
/* 1023 */       return SVlTerm.BinaryOperator.IADD;
/*      */     case 73:
/* 1024 */       return SVlTerm.BinaryOperator.ISUB;
/*      */     case 74:
/* 1025 */       return SVlTerm.BinaryOperator.IMUL;
/*      */     case 75:
/* 1026 */       return SVlTerm.BinaryOperator.IDIV;
/*      */     case 76:
/* 1027 */       return SVlTerm.BinaryOperator.IREM;
/*      */     case 77:
/* 1028 */       return SVlTerm.BinaryOperator.ILSL;
/*      */     case 78:
/* 1029 */       return SVlTerm.BinaryOperator.ILSR;
/*      */     case 79:
/* 1030 */       return SVlTerm.BinaryOperator.IASR;
/*      */     case 80:
/* 1031 */       return SVlTerm.BinaryOperator.ILT;
/*      */     case 81:
/* 1032 */       return SVlTerm.BinaryOperator.IGE;
/*      */     case 82:
/* 1033 */       return SVlTerm.BinaryOperator.ILE;
/*      */     case 83:
/* 1034 */       return SVlTerm.BinaryOperator.IGT;
/*      */     case 84:
/* 1035 */       return SVlTerm.BinaryOperator.IEQ;
/*      */     case 85:
/* 1036 */       return SVlTerm.BinaryOperator.INE;
/*      */     case 86:
/* 1037 */       return SVlTerm.BinaryOperator.IOR;
/*      */     case 87:
/* 1038 */       return SVlTerm.BinaryOperator.IXOR;
/*      */     case 88:
/* 1039 */       return SVlTerm.BinaryOperator.IAND;
/*      */     case 89:
/* 1040 */       return SVlTerm.BinaryOperator.ZOR;
/*      */     case 90:
/* 1041 */       return SVlTerm.BinaryOperator.ZXOR;
/*      */     case 91:
/* 1042 */       return SVlTerm.BinaryOperator.ZAND; }
/* 1043 */     throw new Error(Tokens.toString(paramInt));
/*      */   }
/*      */ 
/*      */   private SProgram mkProgram(int paramInt, SMember[] paramArrayOfSMember, Option<SVlTerm> paramOption)
/*      */   {
/* 1053 */     return (SProgram)set(paramInt, new SProgram(paramArrayOfSMember, paramOption));
/*      */   }
/*      */ 
/*      */   private SModifier[] mkModifierArray(int paramInt)
/*      */   {
/* 1060 */     return new SModifier[paramInt];
/*      */   }
/*      */ 
/*      */   private SModifier[] mkModifierArray(List<SModifier> paramList) {
/* 1064 */     return (SModifier[])paramList.toArray(mkModifierArray(paramList.size()));
/*      */   }
/*      */ 
/*      */   private SModifier mkIsPublic(int paramInt) {
/* 1068 */     return (SModifier)set(paramInt, new SModifier.IsPublic());
/*      */   }
/*      */ 
/*      */   private SModifier mkIsProtected(int paramInt) {
/* 1072 */     return (SModifier)set(paramInt, new SModifier.IsProtected());
/*      */   }
/*      */ 
/*      */   private SModifier mkIsPrivate(int paramInt) {
/* 1076 */     return (SModifier)set(paramInt, new SModifier.IsPrivate());
/*      */   }
/*      */ 
/*      */   private SModifier mkIsStatic(int paramInt) {
/* 1080 */     return (SModifier)set(paramInt, new SModifier.IsStatic());
/*      */   }
/*      */ 
/*      */   private SModifier mkIsAbstract(int paramInt) {
/* 1084 */     return (SModifier)set(paramInt, new SModifier.IsAbstract());
/*      */   }
/*      */ 
/*      */   private SModifier mkIsFinal(int paramInt) {
/* 1088 */     return (SModifier)set(paramInt, new SModifier.IsFinal());
/*      */   }
/*      */ 
/*      */   private SMember[] mkMemberArray(int paramInt)
/*      */   {
/* 1095 */     return new SMember[paramInt];
/*      */   }
/*      */ 
/*      */   private SMember[] mkMemberArray(List<SMember> paramList) {
/* 1099 */     return (SMember[])paramList.toArray(mkMemberArray(paramList.size()));
/*      */   }
/*      */ 
/*      */   private SMember mkClass(int paramInt, SClass paramSClass) {
/* 1103 */     return (SMember)set(paramInt, new SMember.Class(paramSClass));
/*      */   }
/*      */ 
/*      */   private SMember mkField(int paramInt, SField paramSField) {
/* 1107 */     return (SMember)set(paramInt, new SMember.Field(paramSField));
/*      */   }
/*      */ 
/*      */   private SMember mkMethod(int paramInt, SMethod paramSMethod) {
/* 1111 */     return (SMember)set(paramInt, new SMember.Method(paramSMethod));
/*      */   }
/*      */ 
/*      */   private SMember mkConstructor(int paramInt, SConstructor paramSConstructor) {
/* 1115 */     return (SMember)set(paramInt, new SMember.Constructor(paramSConstructor));
/*      */   }
/*      */ 
/*      */   private SClass mkClass(int paramInt, SModifier[] paramArrayOfSModifier, SLabel paramSLabel, Option<STpParamList> paramOption, SClassBase[] paramArrayOfSClassBase1, SClassBase[] paramArrayOfSClassBase2, SClassBody paramSClassBody)
/*      */   {
/* 1126 */     return (SClass)set(paramInt, new SClass(paramArrayOfSModifier, false, paramSLabel, paramOption, paramArrayOfSClassBase1, paramArrayOfSClassBase2, Option.Some(paramSClassBody)));
/*      */   }
/*      */ 
/*      */   private SClass mkInterface(int paramInt, SModifier[] paramArrayOfSModifier, SLabel paramSLabel, Option<STpParamList> paramOption, SClassBase[] paramArrayOfSClassBase, SClassBody paramSClassBody)
/*      */   {
/* 1135 */     return (SClass)set(paramInt, new SClass(paramArrayOfSModifier, true, paramSLabel, paramOption, paramArrayOfSClassBase, new SClassBase[0], Option.Some(paramSClassBody)));
/*      */   }
/*      */ 
/*      */   private SClassBase[] mkClassBaseArray(int paramInt)
/*      */   {
/* 1141 */     return new SClassBase[paramInt];
/*      */   }
/*      */ 
/*      */   private SClassBase[] mkClassBaseArray(List<SClassBase> paramList) {
/* 1145 */     return (SClassBase[])paramList.toArray(mkClassBaseArray(paramList.size()));
/*      */   }
/*      */ 
/*      */   private SClassBase mkClassBase(int paramInt, SLabel paramSLabel, Option<STpArgList> paramOption)
/*      */   {
/* 1151 */     return (SClassBase)set(paramInt, new SClassBase(paramSLabel, paramOption));
/*      */   }
/*      */ 
/*      */   private SClassBody mkClassBody(int paramInt, SMember[] paramArrayOfSMember) {
/* 1155 */     return (SClassBody)set(paramInt, new SClassBody(paramArrayOfSMember));
/*      */   }
/*      */ 
/*      */   private SField mkField(int paramInt, SModifier[] paramArrayOfSModifier, STpTerm paramSTpTerm, SLabel paramSLabel, Option<SSlotBody> paramOption)
/*      */   {
/* 1164 */     return (SField)set(paramInt, new SField(paramArrayOfSModifier, paramSLabel, Option.Some(set(paramInt, new SSlotType(paramSTpTerm))), paramOption));
/*      */   }
/*      */ 
/*      */   private SMethod mkMethod(int paramInt, SModifier[] paramArrayOfSModifier, Option<STpParamList> paramOption, STpTerm paramSTpTerm, SLabel paramSLabel, SVlParamList paramSVlParamList, Option<SSlotBody> paramOption1)
/*      */   {
/* 1173 */     return (SMethod)set(paramInt, new SMethod(paramArrayOfSModifier, paramSLabel, paramOption, Option.Some(paramSVlParamList), Option.Some(set(paramInt, new SSlotType(paramSTpTerm))), paramOption1));
/*      */   }
/*      */ 
/*      */   private SConstructor mkConstructor(int paramInt, SModifier[] paramArrayOfSModifier, Option<STpParamList> paramOption, SLabel paramSLabel, SVlParamList paramSVlParamList, SLabel[] paramArrayOfSLabel1, SLabel[] paramArrayOfSLabel2, SLabel[] paramArrayOfSLabel3)
/*      */   {
/* 1182 */     return (SConstructor)set(paramInt, new SConstructor(paramArrayOfSModifier, Option.Some(paramSLabel), paramOption, Option.Some(paramSVlParamList), paramArrayOfSLabel1, paramArrayOfSLabel2, paramArrayOfSLabel3));
/*      */   }
/*      */ 
/*      */   private SSlotBody mkFieldValue(int paramInt, SVlTerm paramSVlTerm)
/*      */   {
/* 1188 */     return (SSlotBody)set(paramInt, new SSlotBody(paramSVlTerm));
/*      */   }
/*      */ 
/*      */   private SSlotBody mkMethodBody(int paramInt, SVlTerm paramSVlTerm) {
/* 1192 */     return (SSlotBody)set(paramInt, new SSlotBody(paramSVlTerm));
/*      */   }
/*      */ 
/*      */   private STpParamList mkTpParamList(int paramInt, STpParam[] paramArrayOfSTpParam)
/*      */   {
/* 1199 */     return (STpParamList)set(paramInt, new STpParamList(paramArrayOfSTpParam));
/*      */   }
/*      */ 
/*      */   private STpParam[] mkTpParamArray(int paramInt) {
/* 1203 */     return new STpParam[paramInt];
/*      */   }
/*      */ 
/*      */   private STpParam[] mkTpParamArray(List<STpParam> paramList) {
/* 1207 */     return (STpParam[])paramList.toArray(mkTpParamArray(paramList.size()));
/*      */   }
/*      */ 
/*      */   private STpParam mkTpParam(int paramInt, SLabel paramSLabel, Option<STpParamList> paramOption, Option<SSlotBound> paramOption1)
/*      */   {
/* 1213 */     return (STpParam)set(paramInt, new STpParam(paramSLabel, paramOption, paramOption1));
/*      */   }
/*      */ 
/*      */   private SSlotBound mkTpParamBound(int paramInt, STpTerm paramSTpTerm) {
/* 1217 */     return (SSlotBound)set(paramInt, new SSlotBound(paramSTpTerm));
/*      */   }
/*      */ 
/*      */   private SVlParamList mkVlParamList(int paramInt, SVlParam[] paramArrayOfSVlParam) {
/* 1221 */     return (SVlParamList)set(paramInt, new SVlParamList(paramArrayOfSVlParam));
/*      */   }
/*      */ 
/*      */   private SVlParam[] mkVlParamArray(int paramInt) {
/* 1225 */     return new SVlParam[paramInt];
/*      */   }
/*      */ 
/*      */   private SVlParam[] mkVlParamArray(List<SVlParam> paramList) {
/* 1229 */     return (SVlParam[])paramList.toArray(mkVlParamArray(paramList.size()));
/*      */   }
/*      */ 
/*      */   private SVlParam mkVlParam(int paramInt, STpTerm paramSTpTerm, SLabel paramSLabel) {
/* 1233 */     return (SVlParam)set(paramInt, new SVlParam(paramSLabel, Option.Some(set(paramInt, new SSlotType(paramSTpTerm)))));
/*      */   }
/*      */ 
/*      */   private STpTerm[] mkTpTermArray(int paramInt)
/*      */   {
/* 1242 */     return new STpTerm[paramInt];
/*      */   }
/*      */ 
/*      */   private STpTerm[] mkTpTermArray(List<STpTerm> paramList) {
/* 1246 */     return (STpTerm[])paramList.toArray(mkTpTermArray(paramList.size()));
/*      */   }
/*      */ 
/*      */   private STpTerm mkTpBoolean(int paramInt) {
/* 1250 */     return (STpTerm)set(paramInt, new STpTerm.Boolean());
/*      */   }
/*      */ 
/*      */   private STpTerm mkTpInteger(int paramInt) {
/* 1254 */     return (STpTerm)set(paramInt, new STpTerm.Integer());
/*      */   }
/*      */ 
/*      */   private STpTerm mkTpRef(int paramInt, SLabel paramSLabel) {
/* 1258 */     return (STpTerm)set(paramInt, new STpTerm.Ref(paramSLabel, mkTpSelectorArray(0)));
/*      */   }
/*      */ 
/*      */   private STpTerm mkTpAbs(int paramInt1, STpParamList paramSTpParamList, int paramInt2, STpTerm paramSTpTerm)
/*      */   {
/* 1264 */     return (STpTerm)set(paramInt1, new STpTerm.Abs(paramSTpParamList, paramSTpTerm));
/*      */   }
/*      */ 
/*      */   private STpTerm mkTpApp(STpTerm paramSTpTerm, Option<STpArgList> paramOption) {
/* 1268 */     if (paramOption.isEmpty()) return paramSTpTerm;
/* 1269 */     return (STpTerm)set(((STpArgList)paramOption.value()).position(), new STpTerm.App(paramSTpTerm, (STpArgList)paramOption.value()));
/*      */   }
/*      */ 
/*      */   private STpTerm mkTpApp(int paramInt1, STpTerm paramSTpTerm, int paramInt2, Option<STpArgList> paramOption)
/*      */   {
/* 1275 */     return mkTpApp(paramSTpTerm, paramOption);
/*      */   }
/*      */ 
/*      */   private STpSelector[] mkTpSelectorArray(int paramInt) {
/* 1279 */     return new STpSelector[paramInt];
/*      */   }
/*      */ 
/*      */   private STpSelector[] mkTpSelectorArray(List<STpSelector> paramList) {
/* 1283 */     return (STpSelector[])paramList.toArray(mkTpSelectorArray(paramList.size()));
/*      */   }
/*      */ 
/*      */   private STpSelector mkTpSelector(int paramInt, SLabel paramSLabel) {
/* 1287 */     return (STpSelector)set(paramInt, new STpSelector(paramSLabel));
/*      */   }
/*      */ 
/*      */   private STpArgList mkTpArgList(int paramInt, STpTerm[] paramArrayOfSTpTerm) {
/* 1291 */     return (STpArgList)set(paramInt, new STpArgList(paramArrayOfSTpTerm));
/*      */   }
/*      */ 
/*      */   private SVlTerm[] mkVlTermArray(int paramInt)
/*      */   {
/* 1298 */     return new SVlTerm[paramInt];
/*      */   }
/*      */ 
/*      */   private SVlTerm[] mkVlTermArray(List<SVlTerm> paramList) {
/* 1302 */     return (SVlTerm[])paramList.toArray(mkVlTermArray(paramList.size()));
/*      */   }
/*      */ 
/*      */   private SVlTerm mkVlBoolean(int paramInt, boolean paramBoolean) {
/* 1306 */     return (SVlTerm)set(paramInt, new SVlTerm.Boolean(paramBoolean));
/*      */   }
/*      */ 
/*      */   private SVlTerm mkVlInteger(int paramInt1, int paramInt2) {
/* 1310 */     return (SVlTerm)set(paramInt1, new SVlTerm.Integer(paramInt2));
/*      */   }
/*      */ 
/*      */   private SVlTerm mkVlThis(int paramInt) {
/* 1314 */     return (SVlTerm)set(paramInt, new SVlTerm.This());
/*      */   }
/*      */ 
/*      */   private SVlTerm mkVlRef(int paramInt, SVlRef paramSVlRef)
/*      */   {
/* 1319 */     return (SVlTerm)set(paramInt, new SVlTerm.Ref(paramSVlRef));
/*      */   }
/*      */ 
/*      */   private SVlTerm mkVlSel(int paramInt, SVlTerm paramSVlTerm, SVlSelector paramSVlSelector)
/*      */   {
/* 1325 */     return (SVlTerm)set(paramInt, new SVlTerm.Sel(paramSVlTerm, paramSVlSelector));
/*      */   }
/*      */ 
/*      */   private SVlTerm mkVlNew(int paramInt, SLabel paramSLabel, Option<STpArgList> paramOption, SVlArgList paramSVlArgList, Option<SClassBody> paramOption1)
/*      */   {
/* 1331 */     return (SVlTerm)set(paramInt, new SVlTerm.New(paramSLabel, paramOption, Option.Some(paramSVlArgList), paramOption1));
/*      */   }
/*      */ 
/*      */   private SVlTerm mkVlIf(int paramInt1, int paramInt2, SVlTerm paramSVlTerm1, int paramInt3, SVlTerm paramSVlTerm2, int paramInt4, SVlTerm paramSVlTerm3)
/*      */   {
/* 1338 */     return (SVlTerm)set(paramInt1, new SVlTerm.If(paramSVlTerm1, paramSVlTerm2, paramSVlTerm3));
/*      */   }
/*      */ 
/*      */   private SVlTerm mkVlIf(SVlTerm paramSVlTerm1, int paramInt1, SVlTerm paramSVlTerm2, int paramInt2, SVlTerm paramSVlTerm3)
/*      */   {
/* 1344 */     return (SVlTerm)set(paramInt1, new SVlTerm.If(paramSVlTerm1, paramSVlTerm2, paramSVlTerm3));
/*      */   }
/*      */ 
/*      */   private SVlTerm mkVlUnary(int paramInt1, int paramInt2, SVlTerm paramSVlTerm) {
/* 1348 */     return (SVlTerm)set(paramInt2, new SVlTerm.Unary(getUnaryOperator(paramInt1), paramSVlTerm));
/*      */   }
/*      */ 
/*      */   private SVlTerm mkVlBinary(int paramInt1, int paramInt2, SVlTerm paramSVlTerm1, SVlTerm paramSVlTerm2)
/*      */   {
/* 1354 */     return (SVlTerm)set(paramInt2, new SVlTerm.Binary(paramSVlTerm1, getBinaryOperator(paramInt1), paramSVlTerm2));
/*      */   }
/*      */ 
/*      */   private SVlTerm mkVlBlock(int paramInt1, SMember[] paramArrayOfSMember, SVlTerm paramSVlTerm, int paramInt2)
/*      */   {
/* 1361 */     return (SVlTerm)set(paramInt1, new SVlTerm.Block(paramArrayOfSMember, paramSVlTerm));
/*      */   }
/*      */ 
/*      */   private SVlSelector mkVlSelector(int paramInt, SVlRef paramSVlRef) {
/* 1365 */     return (SVlSelector)set(paramInt, new SVlSelector(paramSVlRef));
/*      */   }
/*      */ 
/*      */   private SVlRef mkVlRef(int paramInt, Option<STpArgList> paramOption, SLabel paramSLabel, Option<SVlArgList> paramOption1)
/*      */   {
/* 1371 */     return (SVlRef)set(paramInt, new SVlRef(paramSLabel, paramOption, paramOption1));
/*      */   }
/*      */ 
/*      */   private SVlArgList mkVlArgList(int paramInt, SVlTerm[] paramArrayOfSVlTerm) {
/* 1375 */     return (SVlArgList)set(paramInt, new SVlArgList(paramArrayOfSVlTerm));
/*      */   }
/*      */ 
/*      */   private SLabel[] mkLabelArray(int paramInt)
/*      */   {
/* 1382 */     return new SLabel[paramInt];
/*      */   }
/*      */ 
/*      */   private SLabel[] mkLabelArray(List<SLabel> paramList) {
/* 1386 */     return (SLabel[])paramList.toArray(mkLabelArray(paramList.size()));
/*      */   }
/*      */ 
/*      */   private SLabel mkLabel(String paramString, int paramInt) {
/* 1390 */     return (SLabel)set(paramInt, new SLabel(paramString));
/*      */   }
/*      */ 
/*      */   private static <Tree extends STree> Tree set(int paramInt, Tree paramTree)
/*      */   {
/* 1397 */     paramTree.position(paramInt);
/* 1398 */     return paramTree;
/*      */   }
/*      */ 
/*      */   private static class ParseError extends Error
/*      */   {
/*      */     private static final long serialVersionUID = 0L;
/*      */     public final int position;
/*      */     public final String expected;
/*      */     public final String actual;
/*      */ 
/*      */     public ParseError(int paramInt, String paramString1, String paramString2)
/*      */     {
/* 1413 */       this.position = paramInt;
/* 1414 */       this.expected = paramString1;
/* 1415 */       this.actual = paramString2;
/*      */     }
/*      */ 
/*      */     public ParseError(int paramInt1, String paramString, int paramInt2) {
/* 1419 */       this(paramInt1, paramString, Tokens.toString(paramInt2));
/*      */     }
/*      */ 
/*      */     public ParseError(int paramInt1, int paramInt2, int paramInt3) {
/* 1423 */       this(paramInt1, Tokens.toString(paramInt2), paramInt3);
/*      */     }
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.parser.JavaParser
 * JD-Core Version:    0.6.2
 */