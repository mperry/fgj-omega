/*    */ package pkg.util;
/*    */ 
/*    */ public abstract class Task<Type>
/*    */ {
/*    */   protected Promise<Type> promise;
/*    */
           public Task(Promise<Type> p) {
               promise = p;
           }

/*    */   public Task(TaskManager paramTaskManager)
/*    */   {
/* 14 */     this.promise = paramTaskManager.delay(this);
/*    */   }
/*    */ 
/*    */   public abstract Thunk<Type> getThunk();
/*    */ 
/*    */   public Promise<Type> getPromise()
/*    */   {
/* 23 */     return this.promise;
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.util.Task
 * JD-Core Version:    0.6.2
 */