/*     */ package pkg.hofgj.symtab;
/*     */ 
/*     */ class AVlCode
/*     */ {
/*     */   public static class Create extends AVlCode
/*     */   {
/*     */     public final AClass clasz;
/*     */     public final ACsCode[] cargs;
/*     */     public final AVlCode[] vargs;
/*     */ 
/*     */     public Create(AClass paramAClass, ACsCode[] paramArrayOfACsCode, AVlCode[] paramArrayOfAVlCode)
/*     */     {
/* 158 */       this.clasz = paramAClass;
/* 159 */       this.cargs = paramArrayOfACsCode;
/* 160 */       this.vargs = paramArrayOfAVlCode;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Apply extends AVlCode
/*     */   {
/*     */     public final AVlCode prefix;
/*     */     public final AVlMethod method;
/*     */     public final ACsCode[] cargs;
/*     */     public final AVlCode[] vargs;
/*     */ 
/*     */     public Apply(AVlCode paramAVlCode, AVlMethod paramAVlMethod, ACsCode[] paramArrayOfACsCode, AVlCode[] paramArrayOfAVlCode)
/*     */     {
/* 146 */       this.prefix = paramAVlCode;
/* 147 */       this.method = paramAVlMethod;
/* 148 */       this.cargs = paramArrayOfACsCode;
/* 149 */       this.vargs = paramArrayOfAVlCode;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Select extends AVlCode
/*     */   {
/*     */     public final AVlCode prefix;
/*     */     public final AVlField field;
/*     */ 
/*     */     public Select(AVlCode paramAVlCode, AVlField paramAVlField)
/*     */     {
/* 133 */       this.prefix = paramAVlCode;
/* 134 */       this.field = paramAVlField;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Load extends AVlCode
/*     */   {
/*     */     public final AVlVariable variable;
/*     */ 
/*     */     public Load(AVlVariable paramAVlVariable)
/*     */     {
/* 125 */       this.variable = paramAVlVariable;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.AVlCode
 * JD-Core Version:    0.6.2
 */