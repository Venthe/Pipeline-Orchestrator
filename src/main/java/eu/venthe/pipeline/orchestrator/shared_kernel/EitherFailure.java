package eu.venthe.pipeline.orchestrator.shared_kernel;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class EitherFailure<FAILURE, SUCCESS> implements Either<FAILURE, SUCCESS> {
    private final FAILURE value;

    @Override
    public <T> T orElseGet(Function<? super FAILURE, ? extends T> leftFn, Function<? super SUCCESS, ? extends T> rightFn) {
        return leftFn.apply(value);
    }

    @Override
    public <T> Either<T, SUCCESS> mapFailure(Function<? super FAILURE, ? extends T> mapper) {
        return Either.failure(mapper.apply(value));
    }

    @Override
    public <T> Either<FAILURE, T> mapSuccess(Function<? super SUCCESS, ? extends T> mapper) {
        return Either.failure(value);
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}
