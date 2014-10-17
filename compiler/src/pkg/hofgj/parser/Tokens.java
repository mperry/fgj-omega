/*     */ package pkg.hofgj.parser;

import pkg.hofgj.Util;

import static pkg.hofgj.Util.$assertionsDisabled;

/*     */
/*     */ public abstract class Tokens
/*     */ {
/*     */   public static final int EOF = 1;
/*     */   public static final int IDENTIFIER = 10;
/*     */   public static final int INTEGER = 11;
/*     */   public static final int STRING = 12;
/*     */   public static final int CLASS = 20;
/*     */   public static final int INTERFACE = 21;
/*     */   public static final int EXTENDS = 23;
/*     */   public static final int IMPLEMENTS = 24;
/*     */   public static final int VAL = 25;
/*     */   public static final int DEF = 26;
/*     */   public static final int THIS = 27;
/*     */   public static final int SUPER = 28;
/*     */   public static final int NEW = 29;
/*     */   public static final int IF = 30;
/*     */   public static final int ELSE = 31;
/*     */   public static final int RETURN = 32;
/*     */   public static final int PUBLIC = 33;
/*     */   public static final int PROTECTED = 34;
/*     */   public static final int PRIVATE = 35;
/*     */   public static final int STATIC = 36;
/*     */   public static final int ABSTRACT = 37;
/*     */   public static final int FINAL = 38;
/*     */   public static final int BOOLEAN = 39;
/*     */   public static final int INT = 40;
/*     */   public static final int LPAREN = 50;
/*     */   public static final int RPAREN = 51;
/*     */   public static final int LBRACK = 52;
/*     */   public static final int RBRACK = 53;
/*     */   public static final int LBRACE = 54;
/*     */   public static final int RBRACE = 55;
/*     */   public static final int COMMA = 60;
/*     */   public static final int SEMICOLON = 61;
/*     */   public static final int COLON = 62;
/*     */   public static final int HOOK = 63;
/*     */   public static final int DOT = 64;
/*     */   public static final int LTCOLON = 65;
/*     */   public static final int EQGT = 66;
/*     */   public static final int EQ = 67;
/*     */   public static final int INOT = 70;
/*     */   public static final int ZNOT = 71;
/*     */   public static final int IADD = 72;
/*     */   public static final int ISUB = 73;
/*     */   public static final int IMUL = 74;
/*     */   public static final int IDIV = 75;
/*     */   public static final int IREM = 76;
/*     */   public static final int ILSL = 77;
/*     */   public static final int ILSR = 78;
/*     */   public static final int IASR = 79;
/*     */   public static final int ILT = 80;
/*     */   public static final int IGE = 81;
/*     */   public static final int ILE = 82;
/*     */   public static final int IGT = 83;
/*     */   public static final int IEQ = 84;
/*     */   public static final int INE = 85;
/*     */   public static final int IOR = 86;
/*     */   public static final int IXOR = 87;
/*     */   public static final int IAND = 88;
/*     */   public static final int ZOR = 89;
/*     */   public static final int ZXOR = 90;
/*     */   public static final int ZAND = 91;
/*     */   public static final int ERROR = 99;
/*     */ 
/*     */   public static String toString(int paramInt)
/*     */   {
/*  80 */     switch (paramInt) { case 1:
/*  81 */       return "end of file";
/*     */     case 10:
/*  82 */       return "identifier";
/*     */     case 11:
/*  83 */       return "integer literal";
/*     */     case 12:
/*  84 */       return "string literal";
/*     */     case 20:
/*  85 */       return "keyword 'class'";
/*     */     case 21:
/*  86 */       return "keyword 'interface'";
/*     */     case 23:
/*  87 */       return "keyword 'extends'";
/*     */     case 24:
/*  88 */       return "keyword 'implements'";
/*     */     case 25:
/*  89 */       return "keyword 'val'";
/*     */     case 26:
/*  90 */       return "keyword 'def'";
/*     */     case 27:
/*  91 */       return "keyword 'this'";
/*     */     case 28:
/*  92 */       return "keyword 'super'";
/*     */     case 29:
/*  93 */       return "keyword 'new'";
/*     */     case 30:
/*  94 */       return "keyword 'if'";
/*     */     case 31:
/*  95 */       return "keyword 'else'";
/*     */     case 32:
/*  96 */       return "keyword 'return'";
/*     */     case 33:
/*  97 */       return "keyword 'public'";
/*     */     case 34:
/*  98 */       return "keyword 'protected'";
/*     */     case 35:
/*  99 */       return "keyword 'private'";
/*     */     case 36:
/* 100 */       return "keyword 'static'";
/*     */     case 37:
/* 101 */       return "keyword 'abstract'";
/*     */     case 38:
/* 102 */       return "keyword 'final'";
/*     */     case 39:
/* 103 */       return "keyword 'boolean'";
/*     */     case 40:
/* 104 */       return "keyword 'int'";
/*     */     case 50:
/* 105 */       return "token '('";
/*     */     case 51:
/* 106 */       return "token ')'";
/*     */     case 52:
/* 107 */       return "token '['";
/*     */     case 53:
/* 108 */       return "token ']'";
/*     */     case 54:
/* 109 */       return "token '{'";
/*     */     case 55:
/* 110 */       return "token '}'";
/*     */     case 60:
/* 111 */       return "token ','";
/*     */     case 61:
/* 112 */       return "token ';'";
/*     */     case 62:
/* 113 */       return "token ':'";
/*     */     case 63:
/* 114 */       return "token '?'";
/*     */     case 64:
/* 115 */       return "token '.'";
/*     */     case 65:
/* 116 */       return "token '<:'";
/*     */     case 66:
/* 117 */       return "token '=>'";
/*     */     case 67:
/* 118 */       return "token '='";
/*     */     case 70:
/* 119 */       return "token '~'";
/*     */     case 71:
/* 120 */       return "token '!'";
/*     */     case 72:
/* 121 */       return "token '+'";
/*     */     case 73:
/* 122 */       return "token '-'";
/*     */     case 74:
/* 123 */       return "token '*'";
/*     */     case 75:
/* 124 */       return "token '/'";
/*     */     case 76:
/* 125 */       return "token '%'";
/*     */     case 77:
/* 126 */       return "token '<<'";
/*     */     case 78:
/* 127 */       return "token '>>>'";
/*     */     case 79:
/* 128 */       return "token '>>'";
/*     */     case 80:
/* 129 */       return "token '<'";
/*     */     case 81:
/* 130 */       return "token '>='";
/*     */     case 82:
/* 131 */       return "token '<='";
/*     */     case 83:
/* 132 */       return "token '>'";
/*     */     case 84:
/* 133 */       return "token '=='";
/*     */     case 85:
/* 134 */       return "token '!='";
/*     */     case 86:
/* 135 */       return "token '|'";
/*     */     case 87:
/* 136 */       return "token '^'";
/*     */     case 88:
/* 137 */       return "token '&'";
/*     */     case 89:
/* 138 */       return "token '||'";
/*     */     case 90:
/* 139 */       return "token '^^'";
/*     */     case 91:
/* 140 */       return "token '&&'";
/*     */     case 99:
/* 141 */       return "illegal token";
/*     */     case 2:
/*     */     case 3:
/*     */     case 4:
/*     */     case 5:
/*     */     case 6:
/*     */     case 7:
/*     */     case 8:
/*     */     case 9:
/*     */     case 13:
/*     */     case 14:
/*     */     case 15:
/*     */     case 16:
/*     */     case 17:
/*     */     case 18:
/*     */     case 19:
/*     */     case 22:
/*     */     case 41:
/*     */     case 42:
/*     */     case 43:
/*     */     case 44:
/*     */     case 45:
/*     */     case 46:
/*     */     case 47:
/*     */     case 48:
/*     */     case 49:
/*     */     case 56:
/*     */     case 57:
/*     */     case 58:
/*     */     case 59:
/*     */     case 68:
/*     */     case 69:
/*     */     case 92:
/*     */     case 93:
/*     */     case 94:
/*     */     case 95:
/*     */     case 96:
/*     */     case 97:
/* 143 */     case 98: } if (!$assertionsDisabled) throw new AssertionError("unknown token (" + paramInt + ")");
/* 144 */     throw new Error("!!!");
/*     */   }
/*     */ 
/*     */   public static String toString(int paramInt, String paramString)
/*     */   {
/* 149 */     String str = toString(paramInt);
/*     */ 
/* 151 */     if (!isUnique(paramInt)) str = str + " '" + paramString.toString() + "'";
/* 152 */     return str;
/*     */   }
/*     */ 
/*     */   public static boolean isUnique(int paramInt) {
/* 156 */     switch (paramInt) { case 1:
/* 157 */       return true;
/*     */     case 10:
/* 158 */       return false;
/*     */     case 11:
/* 159 */       return false;
/*     */     case 12:
/* 160 */       return false;
/*     */     case 20:
/* 161 */       return true;
/*     */     case 21:
/* 162 */       return true;
/*     */     case 23:
/* 163 */       return true;
/*     */     case 24:
/* 164 */       return true;
/*     */     case 25:
/* 165 */       return true;
/*     */     case 26:
/* 166 */       return true;
/*     */     case 27:
/* 167 */       return true;
/*     */     case 28:
/* 168 */       return true;
/*     */     case 29:
/* 169 */       return true;
/*     */     case 30:
/* 170 */       return true;
/*     */     case 31:
/* 171 */       return true;
/*     */     case 32:
/* 172 */       return true;
/*     */     case 33:
/* 173 */       return true;
/*     */     case 34:
/* 174 */       return true;
/*     */     case 35:
/* 175 */       return true;
/*     */     case 36:
/* 176 */       return true;
/*     */     case 37:
/* 177 */       return true;
/*     */     case 38:
/* 178 */       return true;
/*     */     case 39:
/* 179 */       return true;
/*     */     case 40:
/* 180 */       return true;
/*     */     case 50:
/* 181 */       return true;
/*     */     case 51:
/* 182 */       return true;
/*     */     case 52:
/* 183 */       return true;
/*     */     case 53:
/* 184 */       return true;
/*     */     case 54:
/* 185 */       return true;
/*     */     case 55:
/* 186 */       return true;
/*     */     case 60:
/* 187 */       return true;
/*     */     case 61:
/* 188 */       return true;
/*     */     case 62:
/* 189 */       return true;
/*     */     case 63:
/* 190 */       return true;
/*     */     case 64:
/* 191 */       return true;
/*     */     case 65:
/* 192 */       return true;
/*     */     case 66:
/* 193 */       return true;
/*     */     case 67:
/* 194 */       return true;
/*     */     case 70:
/* 195 */       return true;
/*     */     case 71:
/* 196 */       return true;
/*     */     case 72:
/* 197 */       return true;
/*     */     case 73:
/* 198 */       return true;
/*     */     case 74:
/* 199 */       return true;
/*     */     case 75:
/* 200 */       return true;
/*     */     case 76:
/* 201 */       return true;
/*     */     case 77:
/* 202 */       return true;
/*     */     case 78:
/* 203 */       return true;
/*     */     case 79:
/* 204 */       return true;
/*     */     case 80:
/* 205 */       return true;
/*     */     case 81:
/* 206 */       return true;
/*     */     case 82:
/* 207 */       return true;
/*     */     case 83:
/* 208 */       return true;
/*     */     case 84:
/* 209 */       return true;
/*     */     case 85:
/* 210 */       return true;
/*     */     case 86:
/* 211 */       return true;
/*     */     case 87:
/* 212 */       return true;
/*     */     case 88:
/* 213 */       return true;
/*     */     case 89:
/* 214 */       return true;
/*     */     case 90:
/* 215 */       return true;
/*     */     case 91:
/* 216 */       return true;
/*     */     case 99:
/* 217 */       return false;
/*     */     case 2:
/*     */     case 3:
/*     */     case 4:
/*     */     case 5:
/*     */     case 6:
/*     */     case 7:
/*     */     case 8:
/*     */     case 9:
/*     */     case 13:
/*     */     case 14:
/*     */     case 15:
/*     */     case 16:
/*     */     case 17:
/*     */     case 18:
/*     */     case 19:
/*     */     case 22:
/*     */     case 41:
/*     */     case 42:
/*     */     case 43:
/*     */     case 44:
/*     */     case 45:
/*     */     case 46:
/*     */     case 47:
/*     */     case 48:
/*     */     case 49:
/*     */     case 56:
/*     */     case 57:
/*     */     case 58:
/*     */     case 59:
/*     */     case 68:
/*     */     case 69:
/*     */     case 92:
/*     */     case 93:
/*     */     case 94:
/*     */     case 95:
/*     */     case 96:
/*     */     case 97:
/* 219 */     case 98: } if (!$assertionsDisabled) throw new AssertionError("unknown token (" + paramInt + ")");
/* 220 */     throw new Error("!!!");
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.parser.Tokens
 * JD-Core Version:    0.6.2
 */