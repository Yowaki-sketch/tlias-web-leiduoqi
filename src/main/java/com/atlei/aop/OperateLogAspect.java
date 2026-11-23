package com.atlei.aop;

import com.atlei.mapper.OperateLogMapper;
import com.atlei.pojo.OperateLog;
import com.atlei.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 操作履歴ログ記録用AOPアスペクト
 */
@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    /**
     * アラウンドアドバイス：@Log アノテーションが付与されたすべてのメソッドを対象に操作ログを記録する
     */
    @Around("@annotation(com.atlei.anno.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {

        // 1. ThreadLocalから現在ログイン中の操作担当者IDを取得
        Integer operateEmpId = getCurrentUserId();

        // 2. 操作日時を取得
        LocalDateTime operateTime = LocalDateTime.now();

        // 3. 対象クラスの完全修飾クラス名を取得
        String className = joinPoint.getTarget().getClass().getName();

        // 4. 対象メソッド名を取得
        String methodName = joinPoint.getSignature().getName();

        // 5. メソッドの実行時引数を取得
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);

        // 6. 対象メソッドを実行し、処理時間を計測
        long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed(); // メソッドを実行して戻り値を取得
        long end = System.currentTimeMillis();
        Long costTime = end - begin;

        // 7. 操作ログエンティティの生成
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateEmpId(operateEmpId);
        operateLog.setOperateTime(operateTime);
        operateLog.setClassName(className);
        operateLog.setMethodName(methodName);
        operateLog.setMethodParams(methodParams);
        operateLog.setReturnValue(result != null ? result.toString() : "void");
        operateLog.setCostTime(costTime);

        // 8. 操作ログのコンソール出力およびデータベースへの永続化
        log.info("AOP操作ログを記録しました: {}", operateLog);
        operateLogMapper.insert(operateLog);

        // 9. 対象メソッドの元の戻り値を返却
        return result;
    }

    // ThreadLocalから操作担当者IDを取得するヘルパーメソッド
    private static Integer getCurrentUserId() {
        return CurrentHolder.getCurrentId();
    }
}