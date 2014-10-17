/*     */ package pkg.hofgj.symtab;
/*     */ 
/*     */ class AProgram
/*     */ {
/*     */   private final AClass[] classes;
/*     */   private final AVlCode main;
/*     */ 
/*     */   public AProgram(AClass[] paramArrayOfAClass, AVlCode paramAVlCode)
/*     */   {
/* 268 */     this.classes = paramArrayOfAClass;
/* 269 */     this.main = paramAVlCode;
/*     */   }
/*     */ 
/*     */   public AClass[] getClasses() {
/* 273 */     return this.classes;
/*     */   }
/*     */ 
/*     */   public AVlCode getMain() {
/* 277 */     return this.main;
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.AProgram
 * JD-Core Version:    0.6.2
 */