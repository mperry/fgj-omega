/*    */ package pkg.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class TaskManager
/*    */ {
/*    */   private final Set<Task<?>> delayed;
/*    */   private final Set<Task<?>> current;
/*    */   private final Set<Task<?>> terminated;
/*    */ 
/*    */   public TaskManager()
/*    */   {
/* 19 */     this.delayed = new LinkedHashSet();
/* 20 */     this.current = new LinkedHashSet();
/* 21 */     this.terminated = new LinkedHashSet();
/*    */   }
/*    */ 
/*    */   public boolean isStarted(Task<?> paramTask)
/*    */   {
/* 28 */     return this.current.contains(paramTask);
/*    */   }
/*    */ 
/*    */   public <Type> Promise<Type> delay(Task<Type> paramTask)
/*    */   {
/* 33 */     assert ((!this.current.contains(paramTask)) && (!this.terminated.contains(paramTask))) : ("\ndelayed task:\n  " + paramTask + "\n" + this);
/*    */ 
/* 35 */     assert (!this.delayed.contains(paramTask)) : ("\ndelayed task:\n  " + paramTask + "\n" + this);
/* 36 */     this.delayed.add(paramTask);
/* 37 */     return Promise.thunk(new TaskThunk(paramTask));
/*    */   }
/*    */ 
/*    */   public void loop() {
/* 41 */     while (!this.delayed.isEmpty())
/* 42 */       ((Task)this.delayed.iterator().next()).getPromise().force();
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 46 */     StringBuilder localStringBuilder = new StringBuilder();
/* 47 */     localStringBuilder.append("current tasks:\n");
/* 48 */     for (Iterator localIterator = this.current.iterator(); localIterator.hasNext(); ) { localTask = (Task)localIterator.next();
/* 49 */       localStringBuilder.append("  ").append(localTask).append("\n");
/*    */     }
/* 51 */     Task localTask;
/* 50 */     localStringBuilder.append("delayed tasks:\n");
/* 51 */     for (localIterator = this.delayed.iterator(); localIterator.hasNext(); ) { localTask = (Task)localIterator.next();
/* 52 */       localStringBuilder.append("  ").append(localTask).append("\n"); }
/* 53 */     localStringBuilder.append("terminated tasks:\n");
/* 54 */     for (localIterator = this.terminated.iterator(); localIterator.hasNext(); ) { localTask = (Task)localIterator.next();
/* 55 */       localStringBuilder.append("  ").append(localTask).append("\n"); }
/* 56 */     return localStringBuilder.toString();
/*    */   }
/*    */ 
/*    */   private void start(Task<?> paramTask)
/*    */   {
/* 64 */     assert ((!this.current.contains(paramTask)) && (!this.terminated.contains(paramTask))) : ("\nstarted task:\n  " + paramTask + "\n" + this);
/*    */ 
/* 66 */     assert (this.delayed.contains(paramTask)) : ("\nstarted task:\n  " + paramTask + "\n" + this);
/* 67 */     this.delayed.remove(paramTask);
/* 68 */     this.current.add(paramTask);
/*    */   }
/*    */ 
/*    */   private void stop(Task<?> paramTask)
/*    */   {
/* 73 */     assert ((!this.delayed.contains(paramTask)) && (!this.terminated.contains(paramTask))) : ("\nstopped task:\n  " + paramTask + "\n" + this);
/*    */ 
/* 75 */     assert (this.current.contains(paramTask)) : ("\nstopped task:\n  " + paramTask + "\n" + this);
/* 76 */     this.current.remove(paramTask);
/* 77 */     this.terminated.add(paramTask);
/*    */   }
/*    */ 
/*    */   private class TaskThunk<Type>
/*    */     implements Thunk<Type>
/*    */   {
/*    */     private final Task<Type> task;
/*    */ 
/*    */     public TaskThunk()
/*    */     {
/*    */       Object localObject;
/* 86 */       this.task = localObject;
/*    */     }
/*    */     public Type evaluate() {
/* 89 */       TaskManager.this.start(this.task);
/* 90 */       Object localObject = this.task.getThunk().evaluate();
/* 91 */       TaskManager.this.stop(this.task);
/* 92 */       return localObject;
/*    */     }
/*    */     public String toString() {
/* 95 */       return this.task.toString();
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/mperry/Downloads/hofgj-compiler-20080524/lib/hofgj.jar
 * Qualified Name:     pkg.util.TaskManager
 * JD-Core Version:    0.6.2
 */