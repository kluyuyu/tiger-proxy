package org.tiger.proxy.constant;

/**
 * Created by liufish on 16/7/30.
 */
public interface IsolationType {


    /**
     * READ UNCOMMITTED是限制性最弱的隔离级别，因为该级别忽略其他事务放置的锁。
     * 使用READ UNCOMMITTED级别执行的事务，可以读取尚未由其他事务提交的修改后的数据值，这些行为称为“脏”读。
     * 这是因为在Read Uncommitted级别下，读取数据不需要加S锁，这样就不会跟被修改的数据上的X锁冲突。
     * 比如，事务1修改一行，事务2在事务1提交之前读取了这一行。
     * 如果事务1回滚，事务2就读取了一行没有提交的数据，这样的数据我们认为是不存在的。
     */
    int READ_UNCOMMITTED = 1;

    /**
     * READ COMMITTED是默认的隔离级别。
     * 该级别通过指定语句不能读取其他事务已修改但是尚未提交的数据值，禁止执行脏读。
     * 在当前事务中的各个语句执行之间，其他事务仍可以修改、插入或删除数据，从而产生无法重复的读操作，或“影子”数据。
     * 比如，事务1读取了一行，事务2修改或者删除这一行并且提交。
     * 如果事务1想再一次读取这一行，它将获得修改后的数据或者发现这一样已经被删除，因此事务的第二次读取结果与第一次读取结果不同，因此也叫不可重复读。
     */
    int READ_COMMITTED = 2;

    /**
     * REPEATABLE READ是比READ COMMITTED限制性更强的隔离级别。
     * 该级别包括READ COMMITTED，并且另外指定了在当前事务提交之前，其他任何事务均不可以修改或删除当前事务已读取的数据。
     * 并发性低于 READ COMMITTED，因为已读数据的共享锁在整个事务期间持有，而不是在每个语句结束时释放。
     * 比如，事务1读取了一行，事务2想修改或者删除这一行并且提交，但是因为事务1尚未提交，数据行中有事务1的锁，事务2无法进行更新操作，因此事务2阻塞。
     * 如果这时候事务1想再一次读取这一行，它读取结果与第一次读取结果相同，因此叫可重复读。
     */
    int REPEATED_READ = 3;

    /**
     * SERIALIZABLE 是限制性最强的隔离级别，因为该级别锁定整个范围的键，并一直持有锁，直到事务完成。
     * 该级别包括REPEATABLE READ，并增加了在事务完成之前，其他事务不能向事务已读取的范围插入新行的限制。
     * 比如，事务1读取了一系列满足搜索条件的行。
     * 事务2在执行SQL statement产生一行或者多行满足事务1搜索条件的行时会冲突，则事务2回滚。
     * 这时事务1再次读取了一系列满足相同搜索条件的行，第二次读取的结果和第一次读取的结果相同。
     */
    int SERIALIZABLE = 4;
}
