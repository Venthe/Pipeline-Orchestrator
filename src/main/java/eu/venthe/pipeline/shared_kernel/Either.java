package eu.venthe.pipeline.shared_kernel;

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

    SUCCESS getSuccess();

    FAILURE getFailure();

    default SUCCESS orElseThrow() {
        if (isSuccess()) return getSuccess();
        throw new IllegalArgumentException();
    }

    default <T extends RuntimeException> SUCCESS orElseThrow(Supplier<? extends T> exceptionSupplier) {
        if (isSuccess()) return getSuccess();
        throw exceptionSupplier.get();
    }

}
