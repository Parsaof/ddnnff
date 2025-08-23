//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.async;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class ThreadPool
{
    private static final ScheduledThreadPoolExecutor pool;
    
    public static void setSize(final int size) {
        ThreadPool.pool.setCorePoolSize(size);
    }
    
    public static void submit(final Runnable runnable) {
        ThreadPool.pool.submit(runnable);
    }
    
    public static void schedule(final Runnable runnable, final long delay) {
        ThreadPool.pool.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }
    
    public static void repeatUntilComplete(final Callable<Boolean> task, final long period) {
        schedule(() -> {
            try {
                if (!task.call()) {
                    repeatUntilComplete(task, period);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }, period);
    }
    
    static {
        pool = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(10, new Factory());
    }
    
    private static class Factory implements ThreadFactory
    {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber;
        private final String namePrefix;
        
        Factory() {
            this.threadNumber = new AtomicInteger(1);
            final SecurityManager s = System.getSecurityManager();
            this.group = ((s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup());
            this.namePrefix = "AquaSkills Scheduled Thread Pool";
        }
        
        @Override
        public Thread newThread(final Runnable r) {
            final Thread t = new Thread(this.group, r, this.namePrefix + " (worker " + this.threadNumber.getAndIncrement() + ")", 0L);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != 5) {
                t.setPriority(5);
            }
            return t;
        }
    }
}
