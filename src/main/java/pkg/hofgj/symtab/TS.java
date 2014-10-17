/*     */ package pkg.hofgj.symtab;
/*     */ 
/*     */ class TS
/*     */ {
/*     */   public Result<TpLeProof> isTpLe(AType paramAType1, AType paramAType2)
/*     */   {
/* 533 */     if (areEqual(paramAType1, paramAType2)) return Result.success(new TpLeRefl(paramAType1));
/*     */ 
/* 535 */     Option localOption = getUpperBound(paramAType1.constructor, paramAType1.cargs);
/* 536 */     if (localOption.isEmpty()) return Result.failure(new TpLeTheorem(paramAType1, paramAType2));
/* 537 */     Result localResult = isTpLe((AType)localOption.get(), paramAType2);
/* 538 */     if (localResult.isFailure()) return Result.failure(new TpLeTheorem(paramAType1, paramAType2), localResult);
/*     */ 
/* 540 */     return Result.success(new TpLeTrans(new TpLeUpper(this, paramAType1), (TpLeProof)localResult.getProof()));
/*     */   }
/*     */ 
/*     */   public Option<AType> getUpperBound(AType paramAType)
/*     */   {
/* 549 */     return getUpperBound(paramAType.constructor, paramAType.cargs);
/*     */   }
/*     */ 
/*     */   public Option<AType> getUpperBound(ACsReference paramACsReference, ACsCode[] paramArrayOfACsCode)
/*     */   {
/*     */     Object localObject;
/* 555 */     switch (paramACsReference.getKind()) {
/*     */     case CONCRETE:
/* 557 */       localObject = (ACsReference.Concrete)paramACsReference;
/*     */ 
/* 559 */       return getUpperBound(((ACsReference.Concrete)localObject).clasz.getSuperType(), ((ACsReference.Concrete)localObject).clasz.getCsParameters(), paramArrayOfACsCode);
/*     */     case ABSTRACT:
/* 564 */       localObject = (ACsReference.Abstract)paramACsReference;
/*     */ 
/* 566 */       return getUpperBound(((ACsReference.Abstract)localObject).variable.getUpperBound(), ((ACsReference.Abstract)localObject).variable.getCsParameters(), paramArrayOfACsCode);
/*     */     }
/*     */ 
/* 570 */     throw new Error("default");
/*     */   }
/*     */ 
/*     */   public Option<AType> getUpperBound(Option<AType> paramOption, ACsVariable[] paramArrayOfACsVariable, ACsCode[] paramArrayOfACsCode)
/*     */   {
/* 577 */     if (paramOption.isEmpty()) return paramOption;
/* 578 */     return new Option.Some(getCsSubstitution((AType)paramOption.get(), paramArrayOfACsVariable, paramArrayOfACsCode));
/*     */   }
/*     */ 
/*     */   public boolean areEqual(ACsReference paramACsReference1, ACsReference paramACsReference2)
/*     */   {
/* 585 */     if (paramACsReference1 == paramACsReference2) return true;
/*     */     Object localObject1;
/*     */     Object localObject2;
/* 586 */     switch (paramACsReference1.getKind()) {
/*     */     case CONCRETE:
/* 588 */       localObject1 = (ACsReference.Concrete)paramACsReference1;
/* 589 */       switch (paramACsReference2.getKind()) {
/*     */       case CONCRETE:
/* 591 */         localObject2 = (ACsReference.Concrete)paramACsReference2;
/* 592 */         return ((ACsReference.Concrete)localObject1).clasz == ((ACsReference.Concrete)localObject2).clasz;
/*     */       case ABSTRACT:
/* 595 */         localObject2 = (ACsReference.Abstract)paramACsReference2;
/* 596 */         return false;
/*     */       }
/*     */ 
/* 599 */       throw new Error("default");
/*     */     case ABSTRACT:
/* 603 */       localObject1 = (ACsReference.Abstract)paramACsReference1;
/* 604 */       switch (paramACsReference2.getKind()) {
/*     */       case CONCRETE:
/* 606 */         localObject2 = (ACsReference.Concrete)paramACsReference2;
/* 607 */         return false;
/*     */       case ABSTRACT:
/* 610 */         localObject2 = (ACsReference.Abstract)paramACsReference2;
/* 611 */         return ((ACsReference.Abstract)localObject1).variable == ((ACsReference.Abstract)localObject2).variable;
/*     */       }
/*     */ 
/* 614 */       throw new Error("default");
/*     */     }
/*     */ 
/* 618 */     throw new Error("default");
/*     */   }
/*     */ 
/*     */   public boolean areEqual(AType[] paramArrayOfAType1, AType[] paramArrayOfAType2)
/*     */   {
/* 623 */     assert (paramArrayOfAType1.length == paramArrayOfAType2.length);
/* 624 */     if (paramArrayOfAType1 == paramArrayOfAType2) return true;
/* 625 */     for (int i = 0; i < paramArrayOfAType1.length; i++)
/* 626 */       if (!areEqual(paramArrayOfAType1[i], paramArrayOfAType2[i])) return false;
/* 627 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean areEqual(AType paramAType1, AType paramAType2) {
/* 631 */     if (paramAType1 == paramAType2) return true;
/* 632 */     return (areEqual(paramAType1.constructor, paramAType2.constructor)) && (areEqual(paramAType1.cargs, paramAType2.cargs));
/*     */   }
/*     */ 
/*     */   public boolean areEqual(ACsCode[] paramArrayOfACsCode1, ACsCode[] paramArrayOfACsCode2)
/*     */   {
/* 637 */     assert (paramArrayOfACsCode1.length == paramArrayOfACsCode2.length);
/* 638 */     if (paramArrayOfACsCode1 == paramArrayOfACsCode2) return true;
/* 639 */     for (int i = 0; i < paramArrayOfACsCode1.length; i++)
/* 640 */       if (!areEqual(paramArrayOfACsCode1[i], paramArrayOfACsCode2[i])) return false;
/* 641 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean areEqual(ACsCode paramACsCode1, ACsCode paramACsCode2) {
/* 645 */     if (paramACsCode1 == paramACsCode2) return true;
/*     */     Object localObject1;
/*     */     Object localObject2;
/* 646 */     switch (paramACsCode1.getKind()) {
/*     */     case REFERENCE:
/* 648 */       localObject1 = (ACsCode.Reference)paramACsCode1;
/* 649 */       switch (paramACsCode2.getKind()) {
/*     */       case REFERENCE:
/* 651 */         localObject2 = (ACsCode.Reference)paramACsCode2;
/* 652 */         return areEqual(((ACsCode.Reference)localObject1).constructor, ((ACsCode.Reference)localObject2).constructor);
/*     */       case ANONYMOUS:
/* 655 */         localObject2 = (ACsCode.Anonymous)paramACsCode2;
/* 656 */         return false;
/*     */       }
/*     */ 
/* 659 */       throw new Error("default");
/*     */     case ANONYMOUS:
/* 663 */       localObject1 = (ACsCode.Anonymous)paramACsCode1;
/* 664 */       switch (paramACsCode2.getKind()) {
/*     */       case REFERENCE:
/* 666 */         localObject2 = (ACsCode.Reference)paramACsCode2;
/* 667 */         return false;
/*     */       case ANONYMOUS:
/* 670 */         localObject2 = (ACsCode.Anonymous)paramACsCode2;
/* 671 */         return false;
/*     */       }
/*     */ 
/* 674 */       throw new Error("default");
/*     */     }
/*     */ 
/* 678 */     throw new Error("default");
/*     */   }
/*     */ 
/*     */   public AType[] getCsSubstitution(AType[] paramArrayOfAType, ACsVariable[] paramArrayOfACsVariable, ACsCode[] paramArrayOfACsCode)
/*     */   {
/* 687 */     if ((paramArrayOfACsVariable.length == 0) && (paramArrayOfACsCode.length == 0)) return paramArrayOfAType;
/* 688 */     return new ACsSubstitution(this, paramArrayOfACsVariable, paramArrayOfACsCode).getTypes(paramArrayOfAType);
/*     */   }
/*     */ 
/*     */   public AType getCsSubstitution(AType paramAType, ACsVariable[] paramArrayOfACsVariable, ACsCode[] paramArrayOfACsCode)
/*     */   {
/* 694 */     if ((paramArrayOfACsVariable.length == 0) && (paramArrayOfACsCode.length == 0)) return paramAType;
/* 695 */     return new ACsSubstitution(this, paramArrayOfACsVariable, paramArrayOfACsCode).getType(paramAType);
/*     */   }
/*     */ 
/*     */   public AType getCsApply(ACsCode paramACsCode, ACsCode[] paramArrayOfACsCode)
/*     */   {
/*     */     Object localObject;
/* 701 */     switch (paramACsCode.getKind()) {
/*     */     case REFERENCE:
/* 703 */       localObject = (ACsCode.Reference)paramACsCode;
/* 704 */       return new AType(((ACsCode.Reference)localObject).constructor, paramArrayOfACsCode);
/*     */     case ANONYMOUS:
/* 707 */       localObject = (ACsCode.Anonymous)paramACsCode;
/* 708 */       return getCsSubstitution(((ACsCode.Anonymous)localObject).body, ((ACsCode.Anonymous)localObject).cparams, paramArrayOfACsCode);
/*     */     }
/*     */ 
/* 711 */     throw new Error("default");
/*     */   }
/*     */ 
/*     */   public ACsCode[] toCsCodes(ACsVariable[] paramArrayOfACsVariable)
/*     */   {
/* 718 */     ACsCode[] arrayOfACsCode = new ACsCode[paramArrayOfACsVariable.length];
/* 719 */     for (int i = 0; i < arrayOfACsCode.length; i++)
/* 720 */       arrayOfACsCode[i] = toCsCode(paramArrayOfACsVariable[i]);
/* 721 */     return arrayOfACsCode;
/*     */   }
/*     */ 
/*     */   public ACsCode toCsCode(ACsVariable paramACsVariable) {
/* 725 */     return new ACsCode.Reference(new ACsReference.Abstract(paramACsVariable));
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.symtab.TS
 * JD-Core Version:    0.6.2
 */