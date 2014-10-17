/*    */ package pkg.parse;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ 
/*    */ public class BuildArray extends Parser
/*    */ {
/*    */   private final Parser parser;
/*    */   private final Class<?> clasz;
/*    */ 
/*    */   public BuildArray(Parser paramParser, Class<?> paramClass)
/*    */   {
/* 17 */     this.parser = paramParser;
/* 18 */     this.clasz = paramClass;
/*    */   }
/*    */ 
/*    */   public boolean parse(Scanner paramScanner, boolean paramBoolean)
/*    */   {
/* 25 */     if (!paramBoolean) return this.parser.parse(paramScanner, false);
/* 26 */     int i = paramScanner.lockQueue();
/* 27 */     if (!this.parser.parse(paramScanner, true)) return false;
/* 28 */     Object[] arrayOfObject = (Object[])Array.newInstance(this.clasz, paramScanner.getValueCount());
/*    */ 
/* 30 */     int j = 0; for (int k = arrayOfObject.length; j < k; j++)
/* 31 */       arrayOfObject[j] = paramScanner.getValueO();
/* 32 */     paramScanner.freeQueue(i);
/* 33 */     paramScanner.putValueO(arrayOfObject);
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean isNullable() {
/* 38 */     return this.parser.isNullable();
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.BuildArray
 * JD-Core Version:    0.6.2
 */