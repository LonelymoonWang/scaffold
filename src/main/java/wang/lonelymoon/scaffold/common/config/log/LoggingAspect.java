package wang.lonelymoon.scaffold.common.config.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author wangpeng
 * @description
 * @date 2021/9/15
 **/
@Aspect
@Component
@Slf4j
public class LoggingAspect {
    //AOP expression for which methods shall be intercepted
    @Around("execution(* wang.lonelymoon.scaffold..*(..)))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        final StopWatch stopWatch = new StopWatch();
        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        //Log method execution time
        LoggingAspect.log.info("Execution time of " + className + "." + methodName + " :" + result + ": " + stopWatch.getTotalTimeMillis() + " ms");
        return result;
    }

    @Around("execution(* wang.lonelymoon.scaffold..*(..)))")
    public Object profileAllLogMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        //Get intercepted method details
        boolean present = methodSignature.getMethod().isAnnotationPresent(Log.class);
        if (present) {
            Log annotation = methodSignature.getMethod().getAnnotation(Log.class);
            log.info(annotation.desc());
            log.info(annotation.methodName());
        }
        final StopWatch stopWatch = new StopWatch();
        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        //Log method execution time
        return result;
    }
}
