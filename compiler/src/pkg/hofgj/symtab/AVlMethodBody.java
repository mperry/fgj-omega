/*     */ package pkg.hofgj.symtab;
/*     */ 
/*     */ class AVlMethodBody
/*     */ {
/*     */   private final AVlMethod method;
/*     */   private final AVlCode body;
/*     */ 
/*     */   public AVlMethodBody(AVlMethod paramAVlMethod, AVlCode paramAVlCode)
/*     */   {
/*  92 */     this.method = paramAVlMethod;
/*  93 */     this.body = paramAVlCode;
/*     */   }
/*     */ 
/*     */   public AVlMethod getMethod() {
/*  97 */     return this.method;
/*     */   }
/*     */ 
/*     */   public AVlCode getCode() {
/* 101 */     return this.body;
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.AVlMethodBody
 * JD-Core Version:    0.6.2
 */