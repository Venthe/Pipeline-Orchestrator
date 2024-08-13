package eu.venthe.platform.shared_kernel;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class EitherSuccess<FAILURE, SUCCESS> implements Either<FAILURE, SUCCESS> {
    private final SUCCESS value;

    @Override
    public <T> T orElseGet(Function<? super FAILURE, ? extends T> leftFn, Function<? super SUCCESS, ? extends T> rightFn) {
        return rightFn.apply(value);
    }

    @Override
    public <T> Either<T, SUCCESS> mapFailure(Function<? super FAILURE, ? extends T> mapper) {
        return Either.success(value);
    }

    @Override
    public <T> Either<FAILURE, T> mapSuccess(Function<? super SUCCESS, ? extends T> mapper) {
        return Either.success(mapper.apply(value));
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public SUCCESS getSuccess() {
        return value;
    }

    @Override
    public FAILURE getFailure() {
        throw new RuntimeException();
    }
}
