package eu.venthe.pipeline.orchestrator.utilities;

import eu.venthe.pipeline.orchestrator.shared_kernel.Either;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

@Slf4j
public final class ExponentialBackOff {
    private static long TIMEOUT = 20_000;
    private static long BACKOFF = 200;
    private static int MAX_RETRIES = 5;
    private static double EXPONENT = 1.1;

    private final ExecutorService executorService;

    public ExponentialBackOff(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public <T> Either<Void, T> invoke(Supplier<T> supplier) {
        try {
            return executorService.submit(() ->
                            invokeSupply(supplier, 1))
                    .get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> Either<Void, T> invokeSupply(Supplier<T> supplier, int times) {
        if (times > MAX_RETRIES) {
            log.error("Condition MAX_RETRIES met. {} > {}", times, MAX_RETRIES);
            return Either.failure(null);
        }

        if (times < 1) throw new RuntimeException();

        if (times > 1) {
            long _milis = (long) (BACKOFF * Math.pow(EXPONENT + 1, times));
            log.warn("Back off failed. Retry={}, Time={}ms", times, _milis);
            try {
                Thread.sleep(Duration.ofMillis(_milis));
            } catch (InterruptedException e) {
                return Either.failure(null);
            }
        }

        try {
            log.debug("Invoking an exponential backoff. Retry={}", times);
            return Either.success(supplier.get());
        } catch (Exception exception) {
            return invokeSupply(supplier, times + 1);
        }
    }
}
