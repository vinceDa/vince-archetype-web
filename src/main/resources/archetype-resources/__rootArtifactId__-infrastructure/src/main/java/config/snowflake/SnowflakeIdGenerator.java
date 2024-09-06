#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.snowflake;

/**
 * SnowflakeIdGenerator - 基于 twitter 的雪花算法生成唯一 id 的公共类
 * 此实现旨在作为单个项目中的独立实用程序使用。
 *
 * todo 后续计划(服务大量部署后):
 * 1. 使用一个集中式的发号器服务来管理工作机器ID，避免冲突。
 * 2. 实现时钟回拨检测机制，在检测到回拨时可以等待一段时间或抛出异常。
 * 3. 根据业务需求调整时间戳、机器ID和序列号的位数分配。
 * 4. 保证 workId 在分布式系统中的唯一性
 */
public class SnowflakeIdGenerator {
    // 开始时间戳 (2021-01-01 00:00:00)
    private static final long EPOCH = 1609459200000L;
    
    // 工作机器 ID 所占的位数
    private static final long WORKER_ID_BITS = 5L;
    
    // 序列号占的位数
    private static final long SEQUENCE_BITS = 12L;

    // 支持的最大工作机器 ID, 结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    
    // 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    // 工作机器 ID 向左移12位
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    
    // 时间戳向左移17位(12+5)
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    // 工作机器 ID(0~31)
    private final long workerId;
    
    // 毫秒内序列(0~4095)
    private long sequence = 0L;
    
    // 上次生成 ID 的时间戳
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     * @param workerId 工作 ID (0~31)
     */
    public SnowflakeIdGenerator(long workerId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("Worker ID can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        this.workerId = workerId;
    }

    /**
     * 获得下一个 ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次 ID 生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        // 上次生成 ID 的时间戳
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的 ID
        return ((timestamp - EPOCH) << TIMESTAMP_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成 ID 的时间戳
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }
}