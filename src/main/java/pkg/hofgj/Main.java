/*    */ package pkg.hofgj;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import pkg.hofgj.compiler.AVlTerm;
/*    */ import pkg.hofgj.compiler.Compiler;
/*    */ import pkg.hofgj.compiler.Interpreter;
/*    */ import pkg.util.Option;
/*    */ 
/*    */ public class Main
/*    */ {
/*    */   public static void main(String[] paramArrayOfString)
/*    */   {
/* 21 */     int i = 0;
/* 22 */     int j = 0;
/* 23 */     int k = 0;
/* 24 */     ArrayList localArrayList = new ArrayList();
/* 25 */     for (int m = 0; m < paramArrayOfString.length; m++) {
/* 26 */       if (paramArrayOfString[m].equals("-print")) {
/* 27 */         j = 1;
/*    */       }
/* 30 */       else if (paramArrayOfString[m].equals("-nodebug")) {
/* 31 */         k = 1;
/*    */       }
/* 34 */       else if (paramArrayOfString[m].startsWith("-")) {
/* 35 */         System.err.println("error: unkonwn option '" + paramArrayOfString[m] + "'");
/* 36 */         i++;
/*    */       }
/* 39 */       else if (paramArrayOfString[m].endsWith(".java")) {
/* 40 */         localArrayList.add(new Source(paramArrayOfString[m], false));
/*    */       }
/* 43 */       else if (paramArrayOfString[m].endsWith(".scala")) {
/* 44 */         localArrayList.add(new Source(paramArrayOfString[m], true));
/*    */       }
/*    */       else {
/* 47 */         System.err.println("error: unkonwn file type '" + paramArrayOfString[m] + "'");
/* 48 */         i++;
/*    */       }
/*    */     }
/* 51 */     if (k != 0) pkg.hofgj.compiler.ATpParam.DEBUG = false;
/*    */ 
/* 53 */     Compiler localCompiler = new Compiler();
/* 54 */     localCompiler.compile((Source[])localArrayList.toArray(new Source[localArrayList.size()]));
/*    */     Object localObject2;
/* 55 */     for (Object localObject1 = localArrayList.iterator(); ((Iterator)localObject1).hasNext(); i += ((Source)localObject2).getErrorCount()) localObject2 = (Source)((Iterator)localObject1).next();
/*    */ 
/* 57 */     if (j != 0) {
/* 58 */       System.out.println("//############################################################################");
/*    */ 
/* 60 */       System.out.println(localCompiler.toString(localCompiler.getAllClasses()));
/* 61 */       for (Iterator localObject1 = localArrayList.iterator(); ((Iterator)localObject1).hasNext(); ) {
                 localObject2 = (Source)((Iterator)localObject1).next();
/* 62 */         if (!((Source)localObject2).getMain().isEmpty()) {
/* 63 */           System.out.println("// " + ((Source)localObject2).getName());
/* 64 */           System.out.println(localCompiler.toString((AVlTerm)((Source)localObject2).getMain().get()));
/* 65 */           System.out.println();
/*    */         }
                }
/* 67 */       System.out.println("//############################################################################");
/*    */ 
/* 69 */       System.out.println();
/*    */     }
/*    */ 
/* 72 */     if (i == 0) {
/* 73 */       Interpreter localObject1 = new Interpreter(localCompiler);
/* 74 */       for (localObject2 = localArrayList.iterator(); ((Iterator)localObject2).hasNext(); ) { Source localSource = (Source)((Iterator)localObject2).next();
/* 75 */         if (!localSource.getMain().isEmpty()) {
/* 76 */           System.out.println("// " + localSource.getName());
/* 77 */           System.out.println(localCompiler.toString(((Interpreter)localObject1).getValueOf((AVlTerm)localSource.getMain().get())));
/*    */ 
/* 80 */           System.out.println();
/*    */         }
/*    */       }
/*    */     }
/* 84 */     if (i > 0) System.err.println(Util.toString(i, "error"));
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.Main
 * JD-Core Version:    0.6.2
 */