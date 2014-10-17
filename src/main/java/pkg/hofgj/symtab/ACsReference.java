/*     */ package pkg.hofgj.symtab;
/*     */ 
/*     */ abstract class ACsReference
/*     */ {
/*     */   public abstract Kind getKind();
/*     */ 
/*     */   public static class Abstract extends ACsReference
/*     */   {
/*     */     public final ACsVariable variable;
/*     */ 
/*     */     public Abstract(ACsVariable paramACsVariable)
/*     */     {
/* 211 */       this.variable = paramACsVariable;
/*     */     }
/*     */     public ACsReference.Kind getKind() {
/* 214 */       return ACsReference.Kind.ABSTRACT;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Concrete extends ACsReference
/*     */   {
/*     */     public final AClass clasz;
/*     */ 
/*     */     public Concrete(AClass paramAClass)
/*     */     {
/* 201 */       this.clasz = paramAClass;
/*     */     }
/*     */     public ACsReference.Kind getKind() {
/* 204 */       return ACsReference.Kind.CONCRETE;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum Kind
/*     */   {
/* 194 */     CONCRETE, ABSTRACT;
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.ACsReference
 * JD-Core Version:    0.6.2
 */