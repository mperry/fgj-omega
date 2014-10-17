/*     */ package pkg.hofgj;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import pkg.hofgj.compiler.AVlTerm;
/*     */ import pkg.hofgj.parser.JavaParser;
/*     */ import pkg.hofgj.parser.Parser;
import pkg.hofgj.parser.Parsers;
/*     */ import pkg.hofgj.parser.Tokens;
/*     */ import pkg.hofgj.stree.SProgram;
/*     */ import pkg.parse.Scanner;
/*     */ import pkg.util.Option;
/*     */ 
/*     */ public class Source
/*     */ {
/*     */   private final String filename;
/*     */   private final boolean scala;
/*     */   private byte[] bytes;
/*     */   private int[] tokens;
/*     */   private int[] positions;
/*     */   private String[] strings;
/*     */   private Option<AVlTerm> main;
/*     */   private int errors;
/*     */ 
/*     */   public Source(String paramString, boolean paramBoolean)
/*     */   {
/*  39 */     this.filename = paramString;
/*  40 */     this.scala = paramBoolean;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  47 */     return this.filename;
/*     */   }
/*     */ 
/*     */   public boolean isScala() {
/*  51 */     return this.scala;
/*     */   }
/*     */ 
/*     */   public byte[] getBytes() {
/*  55 */     if (this.bytes == null) {
/*     */       try {
/*  57 */         FileInputStream localFileInputStream = new FileInputStream(this.filename);
/*  58 */         int i = localFileInputStream.available();
/*  59 */         byte[] arrayOfByte = new byte[i];
/*  60 */         int j = localFileInputStream.read(arrayOfByte);
/*  61 */         assert (j == i) : (j + " < " + i);
/*     */ 
/*  63 */         assert (localFileInputStream.available() == 0) : (this.filename + " - " + localFileInputStream.available());
/*  64 */         localFileInputStream.close();
/*  65 */         this.bytes = arrayOfByte;
/*     */       } catch (IOException localIOException) {
/*  67 */         error("IO error");
/*  68 */         this.bytes = new byte[0];
/*     */       }
/*     */     }
/*  71 */     return this.bytes;
/*     */   }
/*     */ 
/*     */   public int[] getTokens() {
/*  75 */     if (this.tokens == null) readTokens();
/*  76 */     return this.tokens;
/*     */   }
/*     */ 
/*     */   public int[] getTokenPositions() {
/*  80 */     if (this.tokens == null) readTokens();
/*  81 */     return this.positions;
/*     */   }
/*     */ 
/*     */   public String[] getTokenStrings() {
/*  85 */     if (this.tokens == null) readTokens();
/*  86 */     return this.strings;
/*     */   }
/*     */ 
/*     */   public void printTokens() {
/*  90 */     byte[] arrayOfByte = getBytes();
/*  91 */     Scanner localScanner = new Scanner(arrayOfByte);
/*  92 */     Parsers localParsers = new Parsers(this.scala);
/*     */     while (true) {
/*  94 */       int i = localScanner.lockQueue();
/*  95 */       localParsers.program.parse(localScanner, true);
/*  96 */       int j = localScanner.getValueI();
/*  97 */       int k = localScanner.getValueI();
/*  98 */       String str = Tokens.isUnique(j) ? null : (String)localScanner.getValueO();
/*     */ 
/* 100 */       System.out.println("- " + Tokens.toString(j, str));
/* 101 */       localScanner.freeQueue(i);
/* 102 */       if (j == 1) break;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Option<SProgram> parseProgram() {
/* 107 */     if (this.scala) {
/* 108 */       Parser localObject = new pkg.hofgj.parser.Parser(this, getTokens(), getTokenPositions(), getTokenStrings());
/*     */ 
/* 110 */       return ((pkg.hofgj.parser.Parser)localObject).readProgram();
/*     */     }
/* 112 */     Object localObject = new JavaParser(this, getTokens(), getTokenPositions(), getTokenStrings());
/*     */ 
/* 114 */     return ((JavaParser)localObject).readProgram();
/*     */   }
/*     */ 
/*     */   public void setMain(Option<AVlTerm> paramOption)
/*     */   {
/* 119 */     assert ((this.main == null) && (paramOption != null));
/* 120 */     this.main = paramOption;
/*     */   }
/*     */ 
/*     */   public Option<AVlTerm> getMain() {
/* 124 */     assert (this.main != null);
/* 125 */     return this.main;
/*     */   }
/*     */ 
/*     */   public int getErrorCount() {
/* 129 */     return this.errors;
/*     */   }
/*     */ 
/*     */   public void error(String paramString) {
/* 133 */     System.err.println(this.filename + ": " + paramString);
/* 134 */     this.errors += 1;
/*     */   }
/*     */ 
/*     */   public void error(int paramInt, String paramString) {
/* 138 */     byte[] arrayOfByte = getBytes();
/* 139 */     int i = 0;
/* 140 */     int j = 0;
/* 141 */     int k = 1;
/* 142 */     int m = 0;
/* 143 */     while ((i < paramInt) && (i < arrayOfByte.length)) {
/* 144 */       if ((arrayOfByte[i] == 13) || (arrayOfByte[i] == 10)) {
/* 145 */         k++;
/* 146 */         m = 0;
/* 147 */         i++;
/* 148 */         if ((arrayOfByte[(i - 1)] == 13) && (i < arrayOfByte.length) && (arrayOfByte[i] == 10))
/* 149 */           i++;
/* 150 */         j = i;
/*     */       } else {
/* 152 */         m++;
/* 153 */         i++;
/*     */       }
/*     */     }
/* 156 */     System.err.println(this.filename + ":" + k + ": " + paramString);
/* 157 */     if (j < arrayOfByte.length) {
/* 158 */       while ((j < arrayOfByte.length) && (arrayOfByte[j] != 13) && (arrayOfByte[j] != 10)) {
/* 159 */         System.err.print((char)arrayOfByte[j]);
/* 160 */         j++;
/*     */       }
/* 162 */       System.err.println();
/* 163 */       for (; m > 0; m--) System.err.print(' ');
/* 164 */       System.err.println('^');
/*     */     }
/* 166 */     this.errors += 1;
/*     */   }
/*     */ 
/*     */   private void readTokens()
/*     */   {
/* 173 */     Scanner localScanner = new Scanner(getBytes());
/* 174 */     Parsers localParsers = new Parsers(this.scala);
/* 175 */     int i = 0;
/* 176 */     int[] localObject1 = new int[8];
/* 177 */     int[] localObject2 = new int[8];
/* 178 */     String[] localObject3 = new String[8];
/*     */     while (true) {
/* 180 */       int j = localScanner.lockQueue();
/* 181 */       localParsers.program.parse(localScanner, true);
/* 182 */       int k = localScanner.getValueI();
/* 183 */       int m = localScanner.getValueI();
/* 184 */       String str = Tokens.isUnique(k) ? null : (String)localScanner.getValueO();
/*     */ 
/* 186 */       localScanner.freeQueue(j);
/* 187 */       if (i == localObject1.length) {
/* 188 */         int[] arrayOfInt1 = new int[2 * i];
/* 189 */         int[] arrayOfInt2 = new int[2 * i];
/* 190 */         String[] arrayOfString = new String[2 * i];
/* 191 */         for (int n = 0; n < i; n++) arrayOfInt1[n] = localObject1[n];
/* 192 */         for (int n = 0; n < i; n++) arrayOfInt2[n] = localObject2[n];
/* 193 */         for (int n = 0; n < i; n++) arrayOfString[n] = localObject3[n];
/* 194 */         localObject1 = arrayOfInt1;
/* 195 */         localObject2 = arrayOfInt2;
/* 196 */         localObject3 = arrayOfString;
/*     */       }
/* 198 */       localObject1[i] = k;
/* 199 */       localObject2[i] = m;
/* 200 */       localObject3[i] = str;
/* 201 */       i++;
/* 202 */       if (k == 1) break;
/*     */     }
/* 204 */     this.tokens = new int[i];
/* 205 */     this.positions = new int[i];
/* 206 */     this.strings = new String[i];
/* 207 */     for (int j = 0; j < i; j++) this.tokens[j] = localObject1[j];
/* 208 */     for (int j = 0; j < i; j++) this.positions[j] = localObject2[j];
/* 209 */     for (int j = 0; j < i; j++) this.strings[j] = localObject3[j];
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.Source
 * JD-Core Version:    0.6.2
 */