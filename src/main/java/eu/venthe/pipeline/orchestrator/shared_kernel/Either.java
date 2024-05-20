package eu.venthe.pipeline.orchestrator.shared_kernel;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Either<FAILURE, SUCCESS> {
    <T> T orElseGet(Function<? super FAILURE, ? extends T> leftFn, Function<? super SUCCESS, ? extends T> rightFn);

    static <L, R> Either<L, R> failure(L value) {
        return new EitherFailure<>(value);
    }

    static <L, R> Either<L, R> success(R value) {
        return new EitherSuccess<>(value);
    }

    <T> Either<T, SUCCESS> mapFailure(Function<? super FAILURE, ? extends T> mapper);

    <T> Either<FAILURE, T> mapSuccess(Function<? super SUCCESS, ? extends T> mapper);

    boolean isFailure();

    boolean isSuccess();

    default void orElseThrow() {
        throw new IllegalArgumentException();
    }

    default <T extends RuntimeException> void orElseThrow(Supplier<? extends T> exceptionSupplier) {
        throw exceptionSupplier.get();
    }

}
