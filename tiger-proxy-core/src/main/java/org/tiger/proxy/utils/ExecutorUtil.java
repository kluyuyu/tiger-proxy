package org.tiger.proxy.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liufish on 16/7/25.
 */
public class ExecutorUtil {

    public static final ThreadPoolExecutor create(String name, int size) {
        return create(name, size, true);
    }

    public static final ThreadPoolExecutor create(String name, int size, boolean isDaemon) {

        ProxyThreadFactory factory = new ProxyThreadFactory(name, isDaemon);
        return new ThreadPoolExecutor(size, size, Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>(), factory);
    }

    private static class ProxyThreadFactory implements ThreadFactory {

        private final ThreadGroup group;
        private final String namePrefix;
        private final AtomicInteger threadId;
        private final boolean isDaemon;

        public ProxyThreadFactory(String name, boolean isDaemon) {
            SecurityManager manager = System.getSecurityManager();
            this.group = (manager != null) ? manager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = name;
            this.threadId = new AtomicInteger(0);
            this.isDaemon = isDaemon;
        }

        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(group, runnable, namePrefix + threadId.getAndIncrement());
            thread.setDaemon(isDaemon);
            if (thread.getPriority() != Thread.NORM_PRIORITY) {
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }
    }
}
