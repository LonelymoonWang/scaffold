package wang.lonelymoon.scaffold.common.config.scheuled;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wangpeng
 * @description
 * @date 2021/9/14
 **/
@Configuration
public class ThreadPoolConfig {
    /**
     * 配置线程池
     *
     * @return
     */
    @Bean(name = "scheduledPoolTaskExecutor")
    public ThreadPoolTaskExecutor getAsyncThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 线程池创建时候初始化的线程数
        taskExecutor.setCorePoolSize(20);
        // 线程池最大的线程数
        taskExecutor.setMaxPoolSize(200);
        // 用来缓冲执行任务的队列
        taskExecutor.setQueueCapacity(25);
        // 允许线程的空闲时间
        taskExecutor.setKeepAliveSeconds(200);
        // 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        taskExecutor.setThreadNamePrefix("oKong-Scheduled-");
        // 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 调度器shutdown被调用时等待当前被调度的任务完成
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 等待时长
        taskExecutor.setAwaitTerminationSeconds(60);
        taskExecutor.initialize();
        return taskExecutor;
    }
}
