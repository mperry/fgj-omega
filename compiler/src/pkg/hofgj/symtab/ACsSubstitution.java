/*     */ package pkg.hofgj.symtab;
/*     */ 
/*     */ class ACsSubstitution
/*     */ {
/* 424 */   private static final Option.None<ACsCode> NONE = new Option.None();
/*     */   private final TS ts;
/*     */   private final ACsVariable[] src;
/*     */   private final Option<ACsCode>[] dst;
/*     */ 
/*     */   public ACsSubstitution(TS paramTS, ACsVariable[] paramArrayOfACsVariable, ACsCode[] paramArrayOfACsCode)
/*     */   {
/* 432 */     assert ((paramArrayOfACsVariable.length == paramArrayOfACsCode.length) && (paramArrayOfACsVariable.length > 0));
/* 433 */     this.ts = paramTS;
/* 434 */     this.src = paramArrayOfACsVariable;
/*     */ 
/* 436 */     Option[] arrayOfOption = new Option[paramArrayOfACsCode.length];
/* 437 */     this.dst = arrayOfOption;
/* 438 */     for (int i = 0; i < paramArrayOfACsCode.length; i++)
/* 439 */       this.dst[i] = new Option.Some(paramArrayOfACsCode[i]);
/*     */   }
/*     */ 
/*     */   public Option<ACsCode> lookup(ACsReference paramACsReference)
/*     */   {
/*     */     Object localObject;
/* 443 */     switch (paramACsReference.getKind()) {
/*     */     case CONCRETE:
/* 445 */       localObject = (ACsReference.Concrete)paramACsReference;
/*     */ 
/* 447 */       return NONE;
/*     */     case ABSTRACT:
/* 450 */       localObject = (ACsReference.Abstract)paramACsReference;
/*     */ 
/* 452 */       int i = 0; for (int j = this.src.length; i < j; i++)
/* 453 */         if (((ACsReference.Abstract)localObject).variable == this.src[i]) return this.dst[i];
/* 454 */       return NONE;
/*     */     }
/*     */ 
/* 457 */     throw new Error("default");
/*     */   }
/*     */ 
/*     */   public Option<AType> getTypes(Option<AType> paramOption)
/*     */   {
/* 462 */     if (paramOption.isEmpty()) return paramOption;
/* 463 */     return new Option.Some(getType((AType)paramOption.get()));
/*     */   }
/*     */ 
/*     */   public AType[] getTypes(AType[] paramArrayOfAType) {
/* 467 */     AType[] arrayOfAType = new AType[paramArrayOfAType.length];
/* 468 */     for (int i = 0; i < arrayOfAType.length; i++)
/* 469 */       arrayOfAType[i] = getType(paramArrayOfAType[i]);
/* 470 */     return arrayOfAType;
/*     */   }
/*     */ 
/*     */   public AType getType(AType paramAType) {
/* 474 */     ACsCode[] arrayOfACsCode = getCsCodes(paramAType.cargs);
/* 475 */     Option localOption = lookup(paramAType.constructor);
/* 476 */     return localOption.isEmpty() ? new AType(paramAType.constructor, arrayOfACsCode) : this.ts.getCsApply((ACsCode)localOption.get(), arrayOfACsCode);
/*     */   }
/*     */ 
/*     */   public ACsCode[] getCsCodes(ACsCode[] paramArrayOfACsCode)
/*     */   {
/* 482 */     ACsCode[] arrayOfACsCode = new ACsCode[paramArrayOfACsCode.length];
/* 483 */     for (int i = 0; i < arrayOfACsCode.length; i++)
/* 484 */       arrayOfACsCode[i] = getCsCode(paramArrayOfACsCode[i]);
/* 485 */     return arrayOfACsCode;
/*     */   }
/*     */ 
/*     */   public ACsCode getCsCode(ACsCode paramACsCode)
/*     */   {
/*     */     Object localObject;
/* 489 */     switch (paramACsCode.getKind()) {
/*     */     case REFERENCE:
/* 491 */       localObject = (ACsCode.Reference)paramACsCode;
/* 492 */       return (ACsCode)lookup(((ACsCode.Reference)localObject).constructor).get(paramACsCode);
/*     */     case ANONYMOUS:
/* 495 */       localObject = (ACsCode.Anonymous)paramACsCode;
/* 496 */       ACsVariable[] arrayOfACsVariable = getCsVariables(((ACsCode.Anonymous)localObject).cparams);
/* 497 */       ACsCode[] arrayOfACsCode = this.ts.toCsCodes(arrayOfACsVariable);
/* 498 */       AType localAType = getType(((ACsCode.Anonymous)localObject).body);
/* 499 */       localAType = this.ts.getCsSubstitution(localAType, ((ACsCode.Anonymous)localObject).cparams, arrayOfACsCode);
/* 500 */       return new ACsCode.Anonymous(arrayOfACsVariable, localAType);
/*     */     }
/*     */ 
/* 503 */     throw new Error("default");
/*     */   }
/*     */ 
/*     */   public ACsVariable[] getCsVariables(ACsVariable[] paramArrayOfACsVariable)
/*     */   {
/* 508 */     if (paramArrayOfACsVariable.length == 0) return paramArrayOfACsVariable;
/* 509 */     ACsVariable[] arrayOfACsVariable1 = new ACsVariable[paramArrayOfACsVariable.length];
/* 510 */     for (int i = 0; i < arrayOfACsVariable1.length; i++) {
/* 511 */       ACsVariable[] arrayOfACsVariable2 = paramArrayOfACsVariable[i].getCsParameters();
/* 512 */       arrayOfACsVariable2 = getCsVariables(arrayOfACsVariable2);
/* 513 */       arrayOfACsVariable1[i] = new ACsVariable(arrayOfACsVariable2);
/*     */     }
/* 515 */     ACsSubstitution localACsSubstitution = new ACsSubstitution(this.ts, paramArrayOfACsVariable, this.ts.toCsCodes(arrayOfACsVariable1));
/*     */ 
/* 517 */     for (int j = 0; j < arrayOfACsVariable1.length; j++) {
/* 518 */       Option localOption = paramArrayOfACsVariable[j].getUpperBound();
/* 519 */       localOption = getTypes(localOption);
/* 520 */       localOption = localACsSubstitution.getTypes(localOption);
/* 521 */       arrayOfACsVariable1[j].setUpperBound(localOption);
/*     */     }
/* 523 */     return arrayOfACsVariable1;
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.ACsSubstitution
 * JD-Core Version:    0.6.2
 */