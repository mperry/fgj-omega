/*      */ package pkg.hofgj.compiler;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Map;
/*      */ import pkg.util.Option;
/*      */ import pkg.util.P2;
/*      */ import pkg.util.Promise;
/*      */ 
/*      */ class EqualityTester
/*      */ {
/*      */   private final Compiler compiler;
/*      */   private final Map<ATpParam, TpLookup> tparams;
/*      */ 
/*      */   public static boolean areConform(Compiler paramCompiler, ATpParam[] paramArrayOfATpParam, ACsTerm[] paramArrayOfACsTerm)
/*      */   {
/* 1677 */     boolean bool1 = true;
/* 1678 */     EqualityTester localEqualityTester = new EqualityTester(paramCompiler);
/* 1679 */     assert (localEqualityTester.tparams.isEmpty());
/*      */ 
/* 1681 */     boolean bool2 = localEqualityTester.arity(paramArrayOfATpParam, paramArrayOfACsTerm, bool1);
/* 1682 */     if (bool2) {
/* 1683 */       P2 localP2 = Cloner.cloneTpParams(paramArrayOfATpParam, paramArrayOfACsTerm);
/*      */       Object localObject;
/* 1684 */       for (localObject : (ATpParam[][])localP2.vl0) Freezer.instance.rewrite((ATpParam[])localObject);
/* 1685 */       Freezer.instance.rewrite((ATpTerm[])localP2.vl1);
/*      */       ATpParam[] arrayOfATpParam1;
/*      */       ATpTerm localATpTerm;
/*      */       ACsTerm.Function localFunction;
/* 1686 */       for (int i = 0; (i < paramArrayOfATpParam.length) && (bool2); i++) {
/* 1687 */         arrayOfATpParam1 = ((ATpParam[][])localP2.vl0)[i];
/* 1688 */         localATpTerm = ((ATpTerm[])localP2.vl1)[i];
/* 1689 */         localObject = paramArrayOfACsTerm[i].evaluate();
/* 1690 */         switch (localObject.getKind()) {
/*      */         case FUNCTION:
/* 1692 */           localFunction = (ACsTerm.Function)localObject;
/* 1693 */           bool2 = localEqualityTester.insert(arrayOfATpParam1, localFunction.params, bool1);
/* 1694 */           if (bool2) localEqualityTester.remove(arrayOfATpParam1, localFunction.params); break;
/*      */         case ERROR:
/* 1698 */           break;
/*      */         default:
/* 1700 */           throw new Error("" + ((ACsTerm)localObject).getKind());
/*      */         }
/*      */       }
/* 1703 */       for (i = 0; (i < paramArrayOfATpParam.length) && (bool2); i++) {
/* 1704 */         arrayOfATpParam1 = ((ATpParam[][])localP2.vl0)[i];
/* 1705 */         localATpTerm = ((ATpTerm[])localP2.vl1)[i];
/* 1706 */         localObject = paramArrayOfACsTerm[i].evaluate();
/* 1707 */         switch (localObject.getKind()) {
/*      */         case FUNCTION:
/* 1709 */           localFunction = (ACsTerm.Function)localObject;
/* 1710 */           boolean bool3 = localEqualityTester.insert(localFunction.params, arrayOfATpParam1, bool1);
/* 1711 */           assert (bool3);
/* 1712 */           bool2 = localEqualityTester.isSubtypeOf(localFunction.body, localATpTerm, bool1);
/* 1713 */           localEqualityTester.remove(localFunction.params, arrayOfATpParam1);
/* 1714 */           break;
/*      */         case ERROR:
/* 1717 */           break;
/*      */         default:
/* 1719 */           throw new Error("" + ((ACsTerm)localObject).getKind());
/*      */         }
/*      */       }
/*      */     }
/* 1723 */     assert (localEqualityTester.tparams.isEmpty());
/* 1724 */     return bool2;
/*      */   }
/*      */ 
/*      */   public static boolean areConform0(Compiler paramCompiler, ATpParam[] paramArrayOfATpParam, ACsTerm[] paramArrayOfACsTerm)
/*      */   {
/* 1731 */     boolean bool1 = true;
/* 1732 */     EqualityTester localEqualityTester = new EqualityTester(paramCompiler);
/* 1733 */     assert (localEqualityTester.tparams.isEmpty());
/*      */ 
/* 1735 */     boolean bool2 = localEqualityTester.arity(paramArrayOfATpParam, paramArrayOfACsTerm, bool1);
/* 1736 */     if (bool2) {
/* 1737 */       ATpParam[][] arrayOfATpParam1 = Cloner.cloneTpParams0(paramArrayOfATpParam, paramArrayOfACsTerm);
/*      */       Object localObject;
/* 1738 */       for (localObject : arrayOfATpParam1) Freezer.instance.rewrite((ATpParam[])localObject);
/* 1739 */       for (int i = 0; (i < paramArrayOfATpParam.length) && (bool2); i++) {
/* 1740 */         ATpParam[] arrayOfATpParam = arrayOfATpParam1[i];
/* 1741 */         ACsTerm localACsTerm = paramArrayOfACsTerm[i].evaluate();
/* 1742 */         switch (localACsTerm.getKind()) {
/*      */         case FUNCTION:
/* 1744 */           localObject = (ACsTerm.Function)localACsTerm;
/* 1745 */           bool2 = localEqualityTester.insert(arrayOfATpParam, ((ACsTerm.Function)localObject).params, bool1);
/* 1746 */           if (bool2) localEqualityTester.remove(arrayOfATpParam, ((ACsTerm.Function)localObject).params); break;
/*      */         case ERROR:
/* 1750 */           break;
/*      */         default:
/* 1752 */           throw new Error("" + localACsTerm.getKind());
/*      */         }
/*      */       }
/*      */     }
/* 1756 */     assert (localEqualityTester.tparams.isEmpty());
/* 1757 */     return bool2;
/*      */   }
/*      */ 
/*      */   public static boolean areEqual(Compiler paramCompiler, ACsTerm[] paramArrayOfACsTerm1, ACsTerm[] paramArrayOfACsTerm2, boolean paramBoolean)
/*      */   {
/* 1802 */     EqualityTester localEqualityTester = new EqualityTester(paramCompiler);
/* 1803 */     assert (localEqualityTester.tparams.isEmpty());
/* 1804 */     boolean bool = localEqualityTester.test(paramArrayOfACsTerm1, paramArrayOfACsTerm2, paramBoolean);
/* 1805 */     assert (localEqualityTester.tparams.isEmpty());
/* 1806 */     return bool;
/*      */   }
/*      */ 
/*      */   public static boolean areEqual(Compiler paramCompiler, ATpParam[] paramArrayOfATpParam1, ATpTerm[] paramArrayOfATpTerm1, ATpParam[] paramArrayOfATpParam2, ATpTerm[] paramArrayOfATpTerm2, boolean paramBoolean)
/*      */   {
/* 1813 */     EqualityTester localEqualityTester = new EqualityTester(paramCompiler);
/* 1814 */     assert (localEqualityTester.tparams.isEmpty());
/* 1815 */     boolean bool = (localEqualityTester.arity(paramArrayOfATpParam1, paramArrayOfATpParam2)) && (paramArrayOfATpTerm1.length == paramArrayOfATpTerm2.length);
/*      */ 
/* 1817 */     if (bool) {
/* 1818 */       bool = localEqualityTester.insert(paramArrayOfATpParam1, paramArrayOfATpParam2, paramBoolean);
/* 1819 */       if (bool) {
/* 1820 */         bool = localEqualityTester.test(paramArrayOfATpTerm1, paramArrayOfATpTerm2, paramBoolean);
/* 1821 */         localEqualityTester.remove(paramArrayOfATpParam1, paramArrayOfATpParam2);
/*      */       }
/*      */     }
/* 1824 */     assert (localEqualityTester.tparams.isEmpty());
/* 1825 */     return bool;
/*      */   }
/*      */ 
/*      */   private EqualityTester(Compiler paramCompiler)
/*      */   {
/* 1838 */     this.compiler = paramCompiler;
/* 1839 */     this.tparams = new LinkedHashMap();
/*      */   }
/*      */ 
/*      */   public ATpTerm apply(ACsTerm paramACsTerm, ACsTerm[] paramArrayOfACsTerm)
/*      */   {
/* 1847 */     switch (1.$SwitchMap$pkg$hofgj$compiler$ACsTerm$Kind[paramACsTerm.getKind().ordinal()]) {
/*      */     case 1:
/* 1849 */       ACsTerm.Function localFunction = (ACsTerm.Function)paramACsTerm;
/* 1850 */       return apply(localFunction.params, paramArrayOfACsTerm, localFunction.body);
/*      */     case 2:
/* 1853 */       return new ATpTerm.Error();
/*      */     case 3:
/* 1855 */       return apply(paramACsTerm.evaluate(), paramArrayOfACsTerm);
/*      */     }
/* 1857 */     throw new Error("" + paramACsTerm.getKind());
/*      */   }
/*      */ 
/*      */   public ATpTerm apply(ATpParam[] paramArrayOfATpParam, ACsTerm[] paramArrayOfACsTerm, ATpTerm paramATpTerm)
/*      */   {
/* 1863 */     return Freezer.instance.rewrite(Cloner.cloneTp(paramArrayOfATpParam, paramArrayOfACsTerm, paramATpTerm));
/*      */   }
/*      */ 
/*      */   public Option<ATpTerm> getUpperBound(ATpTerm paramATpTerm)
/*      */   {
/*      */     Object localObject1;
/*      */     Object localObject2;
/* 1868 */     switch (1.$SwitchMap$pkg$hofgj$compiler$ATpTerm$Kind[((ATpTerm.Kind)paramATpTerm.getKind()).ordinal()]) {
/*      */     case 1:
/* 1870 */       localObject1 = (ATpTerm.Boolean)paramATpTerm;
/* 1871 */       return Option.Some(this.compiler.getObjectAsTp());
/*      */     case 2:
/* 1874 */       localObject1 = (ATpTerm.Integer)paramATpTerm;
/* 1875 */       return Option.Some(this.compiler.getObjectAsTp());
/*      */     case 3:
/* 1878 */       localObject1 = (ATpTerm.Concrete)paramATpTerm;
/* 1879 */       localObject2 = ((ATpTerm.Concrete)localObject1).clasz.getBase();
/* 1880 */       if (((Option)localObject2).isEmpty()) return Option.None();
/* 1881 */       ATpTerm localATpTerm = apply(((ATpTerm.Concrete)localObject1).clasz.getParams(), ((ATpTerm.Concrete)localObject1).args, ((AClassBase)((Option)localObject2).get()).getBaseType());
/*      */ 
/* 1883 */       return Option.Some(localATpTerm);
/*      */     case 4:
/* 1886 */       localObject1 = (ATpTerm.Abstract)paramATpTerm;
/* 1887 */       localObject2 = apply(((ATpTerm.Abstract)localObject1).param.getParams(), ((ATpTerm.Abstract)localObject1).args, ((ATpTerm.Abstract)localObject1).param.getBound());
/*      */ 
/* 1889 */       return Option.Some(localObject2);
/*      */     case 5:
/* 1892 */       localObject1 = (ATpTerm.Error)paramATpTerm;
/* 1893 */       return Option.None();
/*      */     case 6:
/* 1896 */       localObject1 = (ATpTerm.Delayed)paramATpTerm;
/* 1897 */       return getUpperBound((ATpTerm)((ATpTerm.Delayed)localObject1).promise.force());
/*      */     }
/*      */ 
/* 1900 */     throw new Error("" + paramATpTerm.getKind());
/*      */   }
/*      */ 
/*      */   public boolean isSubtypeOf(ATpTerm paramATpTerm1, ATpTerm paramATpTerm2, boolean paramBoolean)
/*      */   {
/* 1909 */     if ((paramATpTerm1 = paramATpTerm1.evaluate()).isError()) return paramBoolean;
/* 1910 */     if ((paramATpTerm2 = paramATpTerm2.evaluate()).isError()) return paramBoolean;
/*      */     Object localObject2;
/*      */     Object localObject3;
/* 1912 */     switch ((ATpTerm.Kind)paramATpTerm1.getKind()) {
/*      */     case ABSTRACT:
/* 1914 */       localObject1 = (ATpTerm.Abstract)paramATpTerm1;
/* 1915 */       localObject2 = (TpLookup)this.tparams.get(((ATpTerm.Abstract)localObject1).param);
/* 1916 */       if (localObject2 != null) {
/* 1917 */         switch (localObject2.getKind()) {
/*      */         case PARAMETER:
/* 1919 */           localObject3 = (EqualityTester.TpLookup.Parameter)localObject2;
/*      */ 
/* 1921 */           return isSubtypeOf(new ATpTerm.Abstract(((EqualityTester.TpLookup.Parameter)localObject3).param, ((ATpTerm.Abstract)localObject1).args), paramATpTerm2, paramBoolean);
/*      */         case CONSTRUCTOR:
/* 1926 */           localObject3 = (EqualityTester.TpLookup.Constructor)localObject2;
/*      */ 
/* 1928 */           System.out.println(this.compiler.toString(paramATpTerm1) + " -> " + this.compiler.toString(apply(((EqualityTester.TpLookup.Constructor)localObject3).term, ((ATpTerm.Abstract)localObject1).args)));
/* 1929 */           return isSubtypeOf(apply(((EqualityTester.TpLookup.Constructor)localObject3).term, ((ATpTerm.Abstract)localObject1).args), paramATpTerm2, paramBoolean);
/*      */         }
/*      */ 
/* 1933 */         throw new Error("" + ((TpLookup)localObject2).getKind());
/*      */       }
/*      */       break;
/*      */     }
/*      */ 
/* 1938 */     switch ((ATpTerm.Kind)paramATpTerm2.getKind()) {
/*      */     case ABSTRACT:
/* 1940 */       localObject1 = (ATpTerm.Abstract)paramATpTerm2;
/* 1941 */       localObject2 = (TpLookup)this.tparams.get(((ATpTerm.Abstract)localObject1).param);
/* 1942 */       if (localObject2 != null) {
/* 1943 */         switch (localObject2.getKind()) {
/*      */         case PARAMETER:
/* 1945 */           localObject3 = (EqualityTester.TpLookup.Parameter)localObject2;
/*      */ 
/* 1947 */           return isSubtypeOf(paramATpTerm1, new ATpTerm.Abstract(((EqualityTester.TpLookup.Parameter)localObject3).param, ((ATpTerm.Abstract)localObject1).args), paramBoolean);
/*      */         case CONSTRUCTOR:
/* 1952 */           localObject3 = (EqualityTester.TpLookup.Constructor)localObject2;
/*      */ 
/* 1954 */           return isSubtypeOf(paramATpTerm1, apply(((EqualityTester.TpLookup.Constructor)localObject3).term, ((ATpTerm.Abstract)localObject1).args), paramBoolean);
/*      */         }
/*      */ 
/* 1958 */         throw new Error("" + ((TpLookup)localObject2).getKind());
/*      */       }
/*      */ 
/*      */       break;
/*      */     }
/*      */ 
/* 1964 */     switch (1.$SwitchMap$pkg$hofgj$compiler$ATpTerm$Kind[((ATpTerm.Kind)paramATpTerm2.getKind()).ordinal()]) {
/*      */     case 1:
/* 1966 */       localObject1 = (ATpTerm.Boolean)paramATpTerm2;
/* 1967 */       switch ((ATpTerm.Kind)paramATpTerm1.getKind()) {
/*      */       case BOOLEAN:
/* 1969 */         localObject2 = (ATpTerm.Boolean)paramATpTerm1;
/* 1970 */         return true;
/*      */       }
/*      */ 
/* 1973 */       break;
/*      */     case 2:
/* 1977 */       localObject1 = (ATpTerm.Integer)paramATpTerm2;
/* 1978 */       switch ((ATpTerm.Kind)paramATpTerm1.getKind()) {
/*      */       case INTEGER:
/* 1980 */         localObject2 = (ATpTerm.Integer)paramATpTerm1;
/* 1981 */         return true;
/*      */       }
/*      */ 
/* 1984 */       break;
/*      */     case 3:
/* 1988 */       localObject1 = (ATpTerm.Concrete)paramATpTerm2;
/* 1989 */       switch ((ATpTerm.Kind)paramATpTerm1.getKind()) {
/*      */       case CONCRETE:
/* 1991 */         localObject2 = (ATpTerm.Concrete)paramATpTerm1;
/* 1992 */         if (((ATpTerm.Concrete)localObject2).clasz == ((ATpTerm.Concrete)localObject1).clasz) {
/* 1993 */           return test(((ATpTerm.Concrete)localObject2).args, ((ATpTerm.Concrete)localObject1).args, paramBoolean);
/*      */         }
/*      */         break;
/*      */       }
/* 1997 */       break;
/*      */     case 4:
/* 2001 */       localObject1 = (ATpTerm.Abstract)paramATpTerm2;
/* 2002 */       switch ((ATpTerm.Kind)paramATpTerm1.getKind()) {
/*      */       case ABSTRACT:
/* 2004 */         localObject2 = (ATpTerm.Abstract)paramATpTerm1;
/*      */ 
/* 2047 */         if (((ATpTerm.Abstract)localObject2).param == ((ATpTerm.Abstract)localObject1).param) {
/* 2048 */           return test(((ATpTerm.Abstract)localObject2).args, ((ATpTerm.Abstract)localObject1).args, paramBoolean);
/*      */         }
/*      */         break;
/*      */       }
/* 2052 */       break;
/*      */     default:
/* 2056 */       throw new Error("" + paramATpTerm2.getKind());
/*      */     }
/*      */ 
/* 2061 */     Object localObject1 = getUpperBound(paramATpTerm1);
/* 2062 */     if (((Option)localObject1).isEmpty()) return false;
/* 2063 */     return isSubtypeOf((ATpTerm)((Option)localObject1).get(), paramATpTerm2, paramBoolean);
/*      */   }
/*      */ 
/*      */   private boolean test(ACsTerm[] paramArrayOfACsTerm1, ACsTerm[] paramArrayOfACsTerm2, boolean paramBoolean)
/*      */   {
/* 2070 */     assert (paramArrayOfACsTerm1.length == paramArrayOfACsTerm2.length) : (paramArrayOfACsTerm1.length + " - " + paramArrayOfACsTerm2.length);
/* 2071 */     for (int i = 0; i < paramArrayOfACsTerm1.length; i++)
/* 2072 */       if (!test(paramArrayOfACsTerm1[i], paramArrayOfACsTerm2[i], paramBoolean)) return false;
/* 2073 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean test(ACsTerm paramACsTerm1, ACsTerm paramACsTerm2, boolean paramBoolean) {
/* 2077 */     if ((paramACsTerm1 = paramACsTerm1.evaluate()).isError()) return paramBoolean;
/* 2078 */     if ((paramACsTerm2 = paramACsTerm2.evaluate()).isError()) return paramBoolean;
/* 2079 */     switch (paramACsTerm1.getKind()) {
/*      */     case FUNCTION:
/* 2081 */       ACsTerm.Function localFunction1 = (ACsTerm.Function)paramACsTerm1;
/* 2082 */       switch (paramACsTerm2.getKind()) {
/*      */       case FUNCTION:
/* 2084 */         ACsTerm.Function localFunction2 = (ACsTerm.Function)paramACsTerm2;
/* 2085 */         if (!arity(localFunction1.params, localFunction2.params)) return false;
/* 2086 */         if (!insert(localFunction1.params, localFunction2.params, paramBoolean)) return false;
/* 2087 */         boolean bool = test(localFunction1.body, localFunction2.body, paramBoolean);
/* 2088 */         remove(localFunction1.params, localFunction2.params);
/* 2089 */         return bool;
/*      */       }
/*      */ 
/* 2092 */       throw new Error("" + paramACsTerm2.getKind());
/*      */     }
/*      */ 
/* 2096 */     throw new Error("" + paramACsTerm1.getKind());
/*      */   }
/*      */ 
/*      */   private boolean test(ATpTerm[] paramArrayOfATpTerm1, ATpTerm[] paramArrayOfATpTerm2, boolean paramBoolean)
/*      */   {
/* 2101 */     assert (paramArrayOfATpTerm1.length == paramArrayOfATpTerm2.length) : (paramArrayOfATpTerm1.length + " - " + paramArrayOfATpTerm2.length);
/* 2102 */     for (int i = 0; i < paramArrayOfATpTerm1.length; i++)
/* 2103 */       if (!test(paramArrayOfATpTerm1[i], paramArrayOfATpTerm2[i], paramBoolean)) return false;
/* 2104 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean test(ATpTerm paramATpTerm1, ATpTerm paramATpTerm2, boolean paramBoolean) {
/* 2108 */     if ((paramATpTerm1 = paramATpTerm1.evaluate()).isError()) return paramBoolean;
/* 2109 */     if ((paramATpTerm2 = paramATpTerm2.evaluate()).isError()) return paramBoolean;
/*      */     Object localObject1;
/*      */     Object localObject2;
/* 2110 */     switch (1.$SwitchMap$pkg$hofgj$compiler$ATpTerm$Kind[((ATpTerm.Kind)paramATpTerm1.getKind()).ordinal()]) {
/*      */     case 1:
/* 2112 */       localObject1 = (ATpTerm.Boolean)paramATpTerm1;
/* 2113 */       switch (1.$SwitchMap$pkg$hofgj$compiler$ATpTerm$Kind[((ATpTerm.Kind)paramATpTerm2.getKind()).ordinal()]) {
/*      */       case 1:
/* 2115 */         localObject2 = (ATpTerm.Boolean)paramATpTerm2;
/* 2116 */         return true;
/*      */       case 2:
/* 2119 */         localObject2 = (ATpTerm.Integer)paramATpTerm2;
/* 2120 */         return false;
/*      */       case 3:
/* 2123 */         localObject2 = (ATpTerm.Concrete)paramATpTerm2;
/* 2124 */         return false;
/*      */       case 4:
/* 2127 */         localObject2 = (ATpTerm.Abstract)paramATpTerm2;
/* 2128 */         return false;
/*      */       }
/*      */ 
/* 2131 */       throw new Error("" + paramATpTerm2.getKind());
/*      */     case 2:
/* 2135 */       localObject1 = (ATpTerm.Integer)paramATpTerm1;
/* 2136 */       switch (1.$SwitchMap$pkg$hofgj$compiler$ATpTerm$Kind[((ATpTerm.Kind)paramATpTerm2.getKind()).ordinal()]) {
/*      */       case 1:
/* 2138 */         localObject2 = (ATpTerm.Boolean)paramATpTerm2;
/* 2139 */         return false;
/*      */       case 2:
/* 2142 */         localObject2 = (ATpTerm.Integer)paramATpTerm2;
/* 2143 */         return true;
/*      */       case 3:
/* 2146 */         localObject2 = (ATpTerm.Concrete)paramATpTerm2;
/* 2147 */         return false;
/*      */       case 4:
/* 2150 */         localObject2 = (ATpTerm.Abstract)paramATpTerm2;
/* 2151 */         return false;
/*      */       }
/*      */ 
/* 2154 */       throw new Error("" + paramATpTerm2.getKind());
/*      */     case 3:
/* 2158 */       localObject1 = (ATpTerm.Concrete)paramATpTerm1;
/* 2159 */       switch (1.$SwitchMap$pkg$hofgj$compiler$ATpTerm$Kind[((ATpTerm.Kind)paramATpTerm2.getKind()).ordinal()]) {
/*      */       case 1:
/* 2161 */         localObject2 = (ATpTerm.Boolean)paramATpTerm2;
/* 2162 */         return false;
/*      */       case 2:
/* 2165 */         localObject2 = (ATpTerm.Integer)paramATpTerm2;
/* 2166 */         return false;
/*      */       case 3:
/* 2169 */         localObject2 = (ATpTerm.Concrete)paramATpTerm2;
/* 2170 */         if (((ATpTerm.Concrete)localObject1).clasz != ((ATpTerm.Concrete)localObject2).clasz) return false;
/* 2171 */         return test(((ATpTerm.Concrete)localObject1).args, ((ATpTerm.Concrete)localObject2).args, paramBoolean);
/*      */       case 4:
/* 2174 */         localObject2 = (ATpTerm.Abstract)paramATpTerm2;
/* 2175 */         return false;
/*      */       }
/*      */ 
/* 2178 */       throw new Error("" + paramATpTerm2.getKind());
/*      */     case 4:
/* 2182 */       localObject1 = (ATpTerm.Abstract)paramATpTerm1;
/* 2183 */       switch (1.$SwitchMap$pkg$hofgj$compiler$ATpTerm$Kind[((ATpTerm.Kind)paramATpTerm2.getKind()).ordinal()]) {
/*      */       case 1:
/* 2185 */         localObject2 = (ATpTerm.Boolean)paramATpTerm2;
/* 2186 */         return true;
/*      */       case 2:
/* 2189 */         localObject2 = (ATpTerm.Integer)paramATpTerm2;
/* 2190 */         return false;
/*      */       case 3:
/* 2193 */         localObject2 = (ATpTerm.Concrete)paramATpTerm2;
/* 2194 */         return false;
/*      */       case 4:
/* 2197 */         localObject2 = (ATpTerm.Abstract)paramATpTerm2;
/* 2198 */         return test((ATpTerm.Abstract)localObject1, (ATpTerm.Abstract)localObject2, paramBoolean);
/*      */       }
/*      */ 
/* 2201 */       throw new Error("" + paramATpTerm2.getKind());
/*      */     }
/*      */ 
/* 2205 */     throw new Error("" + paramATpTerm1.getKind());
/*      */   }
/*      */ 
/*      */   private boolean test(ATpTerm.Abstract paramAbstract1, ATpTerm.Abstract paramAbstract2, boolean paramBoolean)
/*      */   {
/* 2212 */     TpLookup localTpLookup = (TpLookup)this.tparams.get(paramAbstract1.param);
/* 2213 */     if (localTpLookup != null) {
/* 2214 */       switch (localTpLookup.getKind()) {
/*      */       case PARAMETER:
/* 2216 */         localObject1 = (EqualityTester.TpLookup.Parameter)localTpLookup;
/*      */ 
/* 2218 */         paramAbstract1 = new ATpTerm.Abstract(((EqualityTester.TpLookup.Parameter)localObject1).param, paramAbstract1.args);
/* 2219 */         return test(paramAbstract1, paramAbstract2, paramBoolean);
/*      */       case CONSTRUCTOR:
/* 2222 */         localObject1 = (EqualityTester.TpLookup.Constructor)localTpLookup;
/*      */ 
/* 2224 */         return test(apply(((EqualityTester.TpLookup.Constructor)localObject1).term, paramAbstract1.args), paramAbstract2, paramBoolean);
/*      */       }
/*      */ 
/* 2227 */       throw new Error("" + localTpLookup.getKind());
/*      */     }
/*      */ 
/* 2230 */     Object localObject1 = (TpLookup)this.tparams.get(paramAbstract2.param);
/* 2231 */     if (localObject1 != null)
/*      */     {
/*      */       Object localObject2;
/* 2232 */       switch (localObject1.getKind()) {
/*      */       case PARAMETER:
/* 2234 */         localObject2 = (EqualityTester.TpLookup.Parameter)localObject1;
/*      */ 
/* 2236 */         paramAbstract2 = new ATpTerm.Abstract(((EqualityTester.TpLookup.Parameter)localObject2).param, paramAbstract2.args);
/* 2237 */         return test(paramAbstract1, paramAbstract2, paramBoolean);
/*      */       case CONSTRUCTOR:
/* 2240 */         localObject2 = (EqualityTester.TpLookup.Constructor)localObject1;
/*      */ 
/* 2242 */         return test(paramAbstract1, apply(((EqualityTester.TpLookup.Constructor)localObject2).term, paramAbstract2.args), paramBoolean);
/*      */       }
/*      */ 
/* 2245 */       throw new Error("" + ((TpLookup)localObject1).getKind());
/*      */     }
/*      */ 
/* 2248 */     return (paramAbstract1.param == paramAbstract2.param) && (test(paramAbstract1.args, paramAbstract2.args, paramBoolean));
/*      */   }
/*      */ 
/*      */   private boolean arity(ATpParam[] paramArrayOfATpParam1, ATpParam[] paramArrayOfATpParam2)
/*      */   {
/* 2274 */     if (paramArrayOfATpParam1.length != paramArrayOfATpParam2.length) return false;
/* 2275 */     for (int i = 0; i < paramArrayOfATpParam1.length; i++)
/* 2276 */       if (!arity(paramArrayOfATpParam1[i], paramArrayOfATpParam2[i])) return false;
/* 2277 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean arity(ATpParam paramATpParam1, ATpParam paramATpParam2) {
/* 2281 */     return arity(paramATpParam1.getParams(), paramATpParam2.getParams());
/*      */   }
/*      */ 
/*      */   private boolean arity(ATpParam[] paramArrayOfATpParam, ACsTerm[] paramArrayOfACsTerm, boolean paramBoolean) {
/* 2285 */     if (paramArrayOfATpParam.length != paramArrayOfACsTerm.length) return false;
/* 2286 */     for (int i = 0; i < paramArrayOfATpParam.length; i++)
/* 2287 */       if (!arity(paramArrayOfATpParam[i], paramArrayOfACsTerm[i], paramBoolean)) return false;
/* 2288 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean arity(ATpParam paramATpParam, ACsTerm paramACsTerm, boolean paramBoolean) {
/* 2292 */     switch (1.$SwitchMap$pkg$hofgj$compiler$ACsTerm$Kind[paramACsTerm.getKind().ordinal()]) { case 1:
/* 2293 */       return arity(paramATpParam, (ACsTerm.Function)paramACsTerm);
/*      */     case 2:
/* 2294 */       return paramBoolean;
/*      */     case 3:
/* 2295 */       return arity(paramATpParam, paramACsTerm.evaluate(), paramBoolean); }
/* 2296 */     throw new Error("" + paramACsTerm.getKind());
/*      */   }
/*      */ 
/*      */   private boolean arity(ATpParam paramATpParam, ACsTerm.Function paramFunction)
/*      */   {
/* 2301 */     return arity(paramATpParam.getParams(), paramFunction.params);
/*      */   }
/*      */ 
/*      */   private boolean insert(ATpParam[] paramArrayOfATpParam1, ATpParam[] paramArrayOfATpParam2, boolean paramBoolean)
/*      */   {
/* 2308 */     assert (paramArrayOfATpParam1.length == paramArrayOfATpParam2.length) : (paramArrayOfATpParam1.length + " - " + paramArrayOfATpParam2.length);
/* 2309 */     for (int i = 0; i < paramArrayOfATpParam1.length; i++) {
/* 2310 */       assert (!this.tparams.containsKey(paramArrayOfATpParam1[i]));
/* 2311 */       assert (!this.tparams.containsKey(paramArrayOfATpParam2[i]));
/* 2312 */       this.tparams.put(paramArrayOfATpParam1[i], new EqualityTester.TpLookup.Parameter(paramArrayOfATpParam2[i]));
/*      */     }
/* 2314 */     i = 1;
/*      */     boolean bool;
/* 2315 */     for (int j = 0; (j < paramArrayOfATpParam1.length) && (i != 0); j++) {
/* 2316 */       if (insert(paramArrayOfATpParam1[j].getParams(), paramArrayOfATpParam2[j].getParams(), paramBoolean)) {
/* 2317 */         i &= test(paramArrayOfATpParam1[j].getBound(), paramArrayOfATpParam2[j].getBound(), paramBoolean);
/* 2318 */         remove(paramArrayOfATpParam1[j].getParams(), paramArrayOfATpParam2[j].getParams());
/*      */       } else {
/* 2320 */         bool = false;
/*      */       }
/*      */     }
/* 2323 */     if (!bool) remove(paramArrayOfATpParam1, paramArrayOfATpParam2);
/* 2324 */     return bool;
/*      */   }
/*      */ 
/*      */   private void remove(ATpParam[] paramArrayOfATpParam1, ATpParam[] paramArrayOfATpParam2) {
/* 2328 */     assert (paramArrayOfATpParam1.length == paramArrayOfATpParam2.length) : (paramArrayOfATpParam1.length + " - " + paramArrayOfATpParam2.length);
/* 2329 */     for (int i = 0; i < paramArrayOfATpParam1.length; i++) {
/* 2330 */       ATpParam localATpParam = paramArrayOfATpParam1[i];
/* 2331 */       TpLookup localTpLookup = (TpLookup)this.tparams.remove(paramArrayOfATpParam1[i]);
/* 2332 */       assert ((localTpLookup instanceof EqualityTester.TpLookup.Parameter));
/* 2333 */       assert (((EqualityTester.TpLookup.Parameter)localTpLookup).param == paramArrayOfATpParam2[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static abstract class TpLookup
/*      */   {
/*      */     public abstract Kind getKind();
/*      */ 
/*      */     public static class Constructor extends EqualityTester.TpLookup
/*      */     {
/*      */       public final ACsTerm term;
/*      */ 
/*      */       public Constructor(ACsTerm paramACsTerm)
/*      */       {
/* 2393 */         super(); this.term = paramACsTerm; } 
/* 2394 */       public EqualityTester.TpLookup.Kind getKind() { return EqualityTester.TpLookup.Kind.CONSTRUCTOR; }
/*      */ 
/*      */     }
/*      */ 
/*      */     public static class Parameter extends EqualityTester.TpLookup
/*      */     {
/*      */       public final ATpParam param;
/*      */ 
/*      */       public Parameter(ATpParam paramATpParam)
/*      */       {
/* 2387 */         super(); this.param = paramATpParam; } 
/* 2388 */       public EqualityTester.TpLookup.Kind getKind() { return EqualityTester.TpLookup.Kind.PARAMETER; }
/*      */ 
/*      */     }
/*      */ 
/*      */     public static enum Kind
/*      */     {
/* 2381 */       PARAMETER, CONSTRUCTOR;
/*      */     }
/*      */   }
/*      */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.hofgj.compiler.EqualityTester
 * JD-Core Version:    0.6.2
 */