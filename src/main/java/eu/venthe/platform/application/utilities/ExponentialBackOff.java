package eu.venthe.platform.application.utilities;

import eu.venthe.platform.shared_kernel.Either;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.function.Supplier;

@Slf4j
public final class ExponentialBackOff {
    private static long TIMEOUT = 20_000;
    private static long BACKOFF = 200;
    private static int MAX_RETRIES = 5;
    private static double EXPONENT = 1.1;

    public <T> Either<Void, T> invoke(Supplier<T> supplier) {
        return invokeSupply(supplier, 1);
    }

    private <T> Either<Void, T> invokeSupply(Supplier<T> supplier, int times) {
        if (times > MAX_RETRIES) {
            log.error("Condition MAX_RETRIES met. {} > {}", times, MAX_RETRIES);
            return Either.failure(null);
        }

        if (times < 1) throw new RuntimeException();

        if (times > 1) {
            long _milis = (long) (BACKOFF * Math.pow(EXPONENT + 1, times));
            log.warn("Back off failed. Retry={}, Time={}ms, stack trace={}", times, _milis, Thread.currentThread().getStackTrace());
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
