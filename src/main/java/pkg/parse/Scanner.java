/*     */ package pkg.parse;
/*     */ 
/*     */ public final class Scanner
/*     */ {
/*     */   private static final int EMPTY = 0;
/*     */   private static final int VALUE_O = 1;
/*     */   private static final int VALUE_I = 2;
/*     */   private static final int VALUE_MASK = 255;
/*     */   private static final int STATE_DATUM_LENGTH = 4;
/*     */   private static final int QUEUE_DATUM_LENGTH = 2;
/*     */   private static final int VALUE_DATUM_LENGTH = 1;
/*     */   private final byte[] input_data;
/*     */   private int input_index;
/*     */   private int[] state_data;
/*     */   private int state_count;
/*     */   private int[] queue_data;
/*     */   private int queue_count;
/*     */   private byte[] queue_kinds;
/*     */   private Object[] queue_values_o;
/*     */   private int[] queue_values_i;
/*     */   private int queue_start;
/*     */   private int queue_end;
/*     */ 
/*     */   public Scanner(byte[] paramArrayOfByte)
/*     */   {
/*  40 */     this.input_data = paramArrayOfByte;
/*  41 */     this.state_data = new int[16];
/*  42 */     this.queue_data = new int[8];
/*  43 */     this.queue_kinds = new byte[16];
/*  44 */     this.queue_values_o = new Object[this.queue_kinds.length];
/*  45 */     this.queue_values_i = new int[this.queue_kinds.length];
/*     */   }
/*     */ 
/*     */   public int saveState()
/*     */   {
/*  52 */     ensureCapacityOfStateData(this.state_count + 1);
/*  53 */     int i = this.state_count * 4;
/*  54 */     this.state_data[(i + 0)] = this.input_index;
/*  55 */     this.state_data[(i + 1)] = this.queue_count;
/*  56 */     this.state_data[(i + 2)] = this.queue_start;
/*  57 */     this.state_data[(i + 3)] = this.queue_end;
/*  58 */     this.state_count += 1;
/*  59 */     return this.state_count - 1;
/*     */   }
/*     */ 
/*     */   public void loadState(int paramInt) {
/*  63 */     assert ((0 <= paramInt) && (paramInt == this.state_count - 1));
/*  64 */     int[] arrayOfInt = this.state_data;
/*  65 */     int i = (this.state_count - 1) * 4;
/*  66 */     this.input_index = arrayOfInt[(i + 0)];
/*  67 */     resetQueues(arrayOfInt[(i + 1)], arrayOfInt[(i + 2)], arrayOfInt[(i + 3)]);
/*     */   }
/*     */ 
/*     */   public void dropState(int paramInt) {
/*  71 */     assert ((0 <= paramInt) && (paramInt == this.state_count - 1));
/*  72 */     this.state_count -= 1;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  79 */     return getLengthRelative() == 0;
/*     */   }
/*     */ 
/*     */   public int getCurrentValue() {
/*  83 */     return getValueAtRelative(0);
/*     */   }
/*     */ 
/*     */   public int getCurrentIndex() {
/*  87 */     return this.input_index;
/*     */   }
/*     */ 
/*     */   public Scanner setIndexAbsolute(int paramInt)
/*     */   {
/*  92 */     assert ((0 <= paramInt) && (paramInt <= this.input_data.length));
/*  93 */     this.input_index = paramInt;
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   public Scanner setIndexRelative(int paramInt) {
/*  98 */     return setIndexAbsolute(getCurrentIndex() + paramInt);
/*     */   }
/*     */ 
/*     */   public int getLengthAbsolute()
/*     */   {
/* 103 */     return this.input_data.length;
/*     */   }
/*     */ 
/*     */   public int getLengthRelative() {
/* 107 */     return getLengthAbsolute() - getCurrentIndex();
/*     */   }
/*     */ 
/*     */   public int getValueAtAbsolute(int paramInt)
/*     */   {
/* 112 */     assert ((0 <= paramInt) && (paramInt < this.input_data.length));
/* 113 */     return this.input_data[paramInt];
/*     */   }
/*     */ 
/*     */   public int getValueAtRelative(int paramInt) {
/* 117 */     return getValueAtAbsolute(getCurrentIndex() + paramInt);
/*     */   }
/*     */ 
/*     */   public byte[] getBytesAbsolute(int paramInt)
/*     */   {
/* 122 */     return getBytesAbsolute(paramInt, getCurrentIndex());
/*     */   }
/*     */ 
/*     */   public byte[] getBytesAbsolute(int paramInt1, int paramInt2) {
/* 126 */     assert ((0 <= paramInt1) && (paramInt2 < this.input_data.length));
/* 127 */     assert (paramInt1 <= paramInt2);
/* 128 */     int i = paramInt2 - paramInt1;
/* 129 */     byte[] arrayOfByte1 = this.input_data;
/* 130 */     byte[] arrayOfByte2 = new byte[i];
/* 131 */     for (int j = 0; j < i; j++)
/* 132 */       arrayOfByte2[j] = arrayOfByte1[(paramInt1 + j)];
/* 133 */     return arrayOfByte2;
/*     */   }
/*     */ 
/*     */   public byte[] getBytesRelative(int paramInt) {
/* 137 */     return getBytesRelative(paramInt, 0);
/*     */   }
/*     */ 
/*     */   public byte[] getBytesRelative(int paramInt1, int paramInt2) {
/* 141 */     return getBytesAbsolute(getCurrentIndex() + paramInt1, getCurrentIndex() + paramInt2);
/*     */   }
/*     */ 
/*     */   public char[] getCharsAbsolute(int paramInt)
/*     */   {
/* 148 */     return getCharsAbsolute(paramInt, getCurrentIndex());
/*     */   }
/*     */ 
/*     */   public char[] getCharsAbsolute(int paramInt1, int paramInt2) {
/* 152 */     assert ((0 <= paramInt1) && (paramInt2 < this.input_data.length));
/* 153 */     assert (paramInt1 <= paramInt2);
/* 154 */     int i = paramInt2 - paramInt1;
/* 155 */     byte[] arrayOfByte = this.input_data;
/* 156 */     char[] arrayOfChar = new char[i];
/* 157 */     for (int j = 0; j < i; j++)
/* 158 */       arrayOfChar[j] = ((char)(arrayOfByte[(paramInt1 + j)] & 0xFF));
/* 159 */     return arrayOfChar;
/*     */   }
/*     */ 
/*     */   public char[] getCharsRelative(int paramInt) {
/* 163 */     return getCharsRelative(paramInt, 0);
/*     */   }
/*     */ 
/*     */   public char[] getCharsRelative(int paramInt1, int paramInt2) {
/* 167 */     return getCharsAbsolute(getCurrentIndex() + paramInt1, getCurrentIndex() + paramInt2);
/*     */   }
/*     */ 
/*     */   public String getStringAbsolute(int paramInt)
/*     */   {
/* 174 */     return getStringAbsolute(paramInt, getCurrentIndex());
/*     */   }
/*     */ 
/*     */   public String getStringAbsolute(int paramInt1, int paramInt2) {
/* 178 */     assert ((0 <= paramInt1) && (paramInt2 < this.input_data.length));
/* 179 */     assert (paramInt1 <= paramInt2);
/* 180 */     int i = paramInt2 - paramInt1;
/* 181 */     byte[] arrayOfByte = this.input_data;
/* 182 */     StringBuilder localStringBuilder = new StringBuilder(i);
/* 183 */     for (int j = 0; j < i; j++)
/* 184 */       localStringBuilder.append((char)(arrayOfByte[(paramInt1 + j)] & 0xFF));
/* 185 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public String getStringRelative(int paramInt) {
/* 189 */     return getStringRelative(paramInt, 0);
/*     */   }
/*     */ 
/*     */   public String getStringRelative(int paramInt1, int paramInt2) {
/* 193 */     return getStringAbsolute(getCurrentIndex() + paramInt1, getCurrentIndex() + paramInt2);
/*     */   }
/*     */ 
/*     */   public int lockQueue()
/*     */   {
/* 202 */     ensureCapacityOfQueueData(this.queue_count + 1);
/* 203 */     int i = this.queue_count * 2;
/* 204 */     this.queue_data[(i + 0)] = this.queue_start;
/* 205 */     this.queue_data[(i + 1)] = this.queue_end;
/* 206 */     this.queue_count += 1;
/* 207 */     this.queue_start = this.queue_end;
/* 208 */     return this.queue_count - 1;
/*     */   }
/*     */ 
/*     */   public void freeQueue(int paramInt) {
/* 212 */     assert ((0 <= paramInt) && (paramInt == this.queue_count - 1));
/* 213 */     assert (this.queue_start == this.queue_end);
/* 214 */     int i = (this.queue_count - 1) * 2;
/* 215 */     this.queue_start = this.queue_data[(i + 0)];
/* 216 */     this.queue_end = this.queue_data[(i + 1)];
/* 217 */     this.queue_count -= 1;
/*     */   }
/*     */ 
/*     */   public <Type> Scanner putValueO(Type paramType)
/*     */   {
/* 224 */     assert (this.queue_count > 0);
/* 225 */     ensureCapacityOfQueueValues(this.queue_end + 1);
/* 226 */     this.queue_kinds[this.queue_end] = 1;
/* 227 */     this.queue_values_o[this.queue_end] = paramType;
/* 228 */     this.queue_end += 1;
/* 229 */     return this;
/*     */   }
/*     */ 
/*     */   public Scanner putValueI(int paramInt) {
/* 233 */     assert (this.queue_count > 0);
/* 234 */     ensureCapacityOfQueueValues(this.queue_end + 1);
/* 235 */     this.queue_kinds[this.queue_end] = 2;
/* 236 */     this.queue_values_i[this.queue_end] = paramInt;
/* 237 */     this.queue_end += 1;
/* 238 */     return this;
/*     */   }
/*     */ 
/*     */   public int getValueCount() {
/* 242 */     return this.queue_end - this.queue_start;
/*     */   }
/*     */ 
/*     */   public <Type> Type getValueO() {
/* 246 */     assert ((this.queue_count > 0) && (this.queue_start < this.queue_end));
/* 247 */     assert (this.queue_kinds[this.queue_start] == 1);
/*     */ 
/* 249 */     Type localObject = (Type) this.queue_values_o[this.queue_start];
/* 250 */     dropValue();
/* 251 */     return localObject;
/*     */   }
/*     */ 
/*     */   public int getValueI() {
/* 255 */     assert ((this.queue_count > 0) && (this.queue_start < this.queue_end));
/* 256 */     assert (this.queue_kinds[this.queue_start] == 2);
/* 257 */     int i = this.queue_values_i[this.queue_start];
/* 258 */     dropValue();
/* 259 */     return i;
/*     */   }
/*     */ 
/*     */   public Scanner dropValue() {
/* 263 */     assert ((this.queue_count > 0) && (this.queue_start < this.queue_end));
/* 264 */     this.queue_kinds[this.queue_start] = 0;
/* 265 */     this.queue_values_o[this.queue_start] = null;
/* 266 */     this.queue_values_i[this.queue_start] = 0;
/* 267 */     this.queue_start += 1;
/* 268 */     return this;
/*     */   }
/*     */ 
/*     */   private void ensureCapacityOfStateData(int paramInt)
/*     */   {
/* 275 */     int i = this.state_data.length;
/* 276 */     int j = paramInt * 4;
/* 277 */     if (j <= i) return;
/* 278 */     j = Math.max(j, 2 * i);
/* 279 */     int[] arrayOfInt1 = this.state_data;
/* 280 */     int[] arrayOfInt2 = new int[j];
/* 281 */     int k = 0; for (int m = this.state_count * 4; k < m; k++)
/* 282 */       arrayOfInt2[k] = arrayOfInt1[k];
/* 283 */     this.state_data = arrayOfInt2;
/*     */   }
/*     */ 
/*     */   private void ensureCapacityOfQueueData(int paramInt) {
/* 287 */     int i = this.queue_data.length;
/* 288 */     int j = paramInt * 2;
/* 289 */     if (j <= i) return;
/* 290 */     j = Math.max(j, 2 * i);
/* 291 */     int[] arrayOfInt1 = this.queue_data;
/* 292 */     int[] arrayOfInt2 = new int[j];
/* 293 */     int k = 0; for (int m = this.queue_count * 2; k < m; k++)
/* 294 */       arrayOfInt2[k] = arrayOfInt1[k];
/* 295 */     this.queue_data = arrayOfInt2;
/*     */   }
/*     */ 
/*     */   private void ensureCapacityOfQueueValues(int paramInt) {
/* 299 */     int i = this.queue_kinds.length;
/* 300 */     int j = paramInt * 1;
/* 301 */     if (j <= i) return;
/* 302 */     j = Math.max(j, 2 * i);
/* 303 */     byte[] arrayOfByte1 = this.queue_kinds;
/* 304 */     byte[] arrayOfByte2 = new byte[j];
/* 305 */     int k = 0; for (int m = this.queue_end * 1; k < m; k++)
/* 306 */       arrayOfByte2[k] = arrayOfByte1[k];
/* 307 */     this.queue_kinds = arrayOfByte2;
/* 308 */     Object[] arrayOfObject1 = this.queue_values_o;
/* 309 */     Object[] arrayOfObject2 = new Object[j];
/* 310 */     int n = 0; for (int i1 = this.queue_end * 1; n < i1; n++)
/* 311 */       arrayOfObject2[n] = arrayOfObject1[n];
/* 312 */     this.queue_values_o = arrayOfObject2;
/* 313 */     int[] arrayOfInt1 = this.queue_values_i;
/* 314 */     int[] arrayOfInt2 = new int[j];
/* 315 */     int i2 = 0; for (int i3 = this.queue_end * 1; i2 < i3; i2++)
/* 316 */       arrayOfInt2[i2] = arrayOfInt1[i2];
/* 317 */     this.queue_values_i = arrayOfInt2;
/*     */   }
/*     */ 
/*     */   private void resetQueues(int paramInt1, int paramInt2, int paramInt3) {
/* 321 */     assert ((paramInt1 <= this.queue_count) && (paramInt3 <= this.queue_end));
/* 322 */     int i = paramInt3; for (int j = this.queue_end; i < j; i++) {
/* 323 */       this.queue_kinds[i] = 0;
/* 324 */       this.queue_values_o[i] = null;
/* 325 */       this.queue_values_i[i] = 0;
/*     */     }
/* 327 */     this.queue_count = paramInt1;
/* 328 */     this.queue_start = paramInt2;
/* 329 */     this.queue_end = paramInt3;
/*     */   }
/*     */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.parse.Scanner
 * JD-Core Version:    0.6.2
 */