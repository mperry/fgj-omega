/*    */ package pkg.hofgj.stree;
/*    */ 
/*    */ import pkg.util.Option;
/*    */ 
/*    */ public class SClass extends STree
/*    */ {
/*    */   public final SModifier[] modifiers;
/*    */   public final boolean isInterface;
/*    */   public final SLabel name;
/*    */   public final Option<STpParamList> params;
/*    */   public final SClassBase[] extensions;
/*    */   public final SClassBase[] implementations;
/*    */   public final Option<SClassBody> body;
/*    */ 
/*    */   public SClass(SModifier[] paramArrayOfSModifier, boolean paramBoolean, SLabel paramSLabel, Option<STpParamList> paramOption, SClassBase[] paramArrayOfSClassBase1, SClassBase[] paramArrayOfSClassBase2, Option<SClassBody> paramOption1)
/*    */   {
/* 25 */     this.modifiers = paramArrayOfSModifier;
/* 26 */     this.isInterface = paramBoolean;
/* 27 */     this.name = paramSLabel;
/* 28 */     this.params = paramOption;
/* 29 */     this.extensions = paramArrayOfSClassBase1;
/* 30 */     this.implementations = paramArrayOfSClassBase2;
/* 31 */     this.body = paramOption1;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.stree.SClass
 * JD-Core Version:    0.6.2
 */