package org.trimou.benchmark;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openjdk.jmh.annotations.TearDown;
import org.trimou.engine.MustacheEngineBuilder;

/**
 *
 * @author Martin Kouba
 */
public class DemandingAsync extends Basic {

    private ExecutorService executorService;

    @Override
    protected void customizeMustacheEngineBuilder(MustacheEngineBuilder builder) {
        Method method;
        try {
            method = MustacheEngineBuilder.class.getMethod("setExecutorService", ExecutorService.class);
        } catch (Exception e) {
            throw new IllegalStateException("Async not supported!", e);
        }
        if (method != null) {
            try {
                executorService = Executors.newCachedThreadPool();
                method.invoke(builder, executorService);
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
        } else {
            throw new IllegalStateException("Async not supported!");
        }
    }

    @TearDown
    public void tearDown() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    protected String getMustacheName() {
        return "templates/demanding_async";
    }

}
