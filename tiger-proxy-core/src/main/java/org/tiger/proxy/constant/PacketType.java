package org.tiger.proxy.constant;

/**
 * Created by liufish on 16/7/24.
 */
public interface PacketType {


    /**
     *（内部线程状态）
     */
    byte COM_SLEEP = 0;

    /**
     * 关闭连接
     */
    byte COM_QUIT = 1;

    /**
     *切换数据库
     */
    byte COM_INIT_DB = 2;

    /**
     * SQL查询请求
     */
    byte COM_QUERY = 3;

    /**
     * 获取数据表字段信息
     */
    byte COM_FIELD_LIST = 4;

    /**
     * 创建数据库
     */
    byte COM_CREATE_DB = 5;

    /**
     * 删除数据库
     */
    byte COM_DROP_DB = 6;

    /**
     * 清除缓存
     */
    byte COM_REFRESH = 7;

    /**
     * 停止服务器
     */
    byte COM_SHUTDOWN = 8;

    /**
     * 获取服务器统计信息
     */
    byte COM_STATISTICS = 9;

    /**
     * 获取当前连接的列表
     */
    byte COM_PROCESS_INFO = 10;

    /**
     * （内部线程状态）
     */
    byte COM_CONNECT = 11;

    /**
     * 中断某个连接
     */
    byte COM_PROCESS_KILL = 12;

    /**
     * 保存服务器调试信息
     */
    byte COM_DEBUG = 13;

    /**
     * 测试连通性
     */
    byte COM_PING = 14;

    /**
     *（内部线程状态）
     */
    byte COM_TIME = 15;

    /**
     * （内部线程状态）
     */
    byte COM_DELAYED_INSERT = 16;

    /**
     * 重新登陆（不断连接）
     */
    byte COM_CHANGE_USER = 17;

    /**
     * 获取二进制日志信息
     */
    byte COM_BINLOG_DUMP = 18;

    /**
     * 获取数据表结构信息
     */
    byte COM_TABLE_DUMP = 19;

    /**
     * （内部线程状态）
     */
    byte COM_CONNECT_OUT = 20;

    /**
     * 从服务器向主服务器进行注册
     */
    byte COM_REGISTER_SLAVE = 21;

    /**
     * 预处理SQL语句
     */
    byte COM_STMT_PREPARE = 22;

    /**
     * 执行预处理语句
     */
    byte COM_STMT_EXECUTE = 23;

    /**
     * 发送BLOB类型的数据
     */
    byte COM_STMT_SEND_LONG_DATA = 24;

    /**
     * 销毁预处理语句
     */
    byte COM_STMT_CLOSE = 25;

    /**
     * 清除预处理语句参数缓存
     */
    byte COM_STMT_RESET = 26;

    /**
     * 设置语句选项
     */
    byte COM_SET_OPTION = 27;

    /**
     * 获取预处理语句的执行结果
     */
    byte COM_STMT_FETCH = 28;

    /**
     * 本中间件自定义心跳
     */
    byte COM_HEARTBEAT = 64;
}
