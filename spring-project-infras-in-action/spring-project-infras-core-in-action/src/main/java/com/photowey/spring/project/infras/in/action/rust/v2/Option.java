package com.photowey.spring.project.infras.in.action.rust.v2;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * {@code Option}.
 * A Java imitation of Rust's {@code Option<T>}.
 * Represents an optional value: every Option is either Some(T) or None.
 *
 * @param <T> the type of the value
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/01
 */
@SuppressWarnings("all")
public abstract class Option<T> {

    // @formatter:off

    private Option() { }

    // @formatter:on

    // ----------------------------------------------------------------
    // Static Constructors
    // ----------------------------------------------------------------

    public static <T> Option<T> some(T value) {
        return new Some<>(Objects.requireNonNull(value, "Value must not be null"));
    }

    public static <T> Option<T> none() {
        return (Option<T>) None.NONE;
    }

    public static <T> Option<T> of(T value) {
        return ofNullable(value);
    }

    public static <T> Option<T> ofNullable(T value) {
        return Objects.isNull(value) ? none() : some(value);
    }

    // ----------------------------------------------------------------
    // Abstract Methods
    // ----------------------------------------------------------------

    /**
     * Returns true if the option is Some, false otherwise.
     *
     * @return true if the option is Some, false otherwise
     */
    public abstract boolean isSome();

    /**
     * Returns true if the option is None, false otherwise.
     *
     * @return true if the option is None, false otherwise
     */
    public abstract boolean isNone();

    /**
     * Returns the contained value.
     *
     * @return the contained value
     * @throws IllegalStateException if called on None
     */
    public abstract T unwrap();

    /**
     * Returns the contained value, or the default value if None.
     *
     * @param defaultValue the default value
     * @return the contained value, or the default value if None
     */
    public abstract T unwrapOr(T defaultValue);

    /**
     * Returns the contained value, or computes it from a supplier.
     *
     * @param supplier the supplier
     * @return the contained value, or the computed value
     */
    public abstract T unwrapOrElse(Supplier<? extends T> supplier);

    /**
     * Maps the contained value using the given function if Some.
     * Returns None if this is None.
     *
     * @param mapper the mapping function
     * @return the mapped option
     */
    public abstract <U> Option<U> map(Function<? super T, ? extends U> mapper);

    /**
     * Flat-maps the contained value into another Option.
     *
     * @param mapper the mapping function
     * @return the flat-mapped option
     */
    public abstract <U> Option<U> flatMap(Function<? super T, Option<U>> mapper);

    /**
     * Filters the value based on a predicate.
     * If predicate returns false, returns None.
     *
     * @param predicate the predicate
     * @return the filtered option
     */
    public abstract Option<T> filter(Predicate<? super T> predicate);

    /**
     * Executes the given consumer if the value is present.
     *
     * @param consumer the consumer
     */
    public abstract void ifPresent(Consumer<? super T> consumer);

    /**
     * Executes the given runnable if the value is absent.
     *
     * @param runnable the runnable
     */
    public abstract void ifEmpty(Runnable runnable);

    /**
     * Functional-style pattern matching.
     *
     * @param someCase function to apply if this is Some(T)
     * @param noneCase supplier to get result if this is None
     * @param <R>      the result type
     * @return the result of the matched case
     * @throws NullPointerException if {@code someCase} or {@code noneCase} is {@code null}
     */
    public abstract <R> R match(Function<? super T, ? extends R> someCase, Supplier<? extends R> noneCase);

    /**
     * Functional-style pattern matching.
     * Performs pattern matching-style execution by applying the appropriate action based on whether
     * this Option is {@code Some} or {@code None}.
     *
     * <p>
     * This method is useful for executing side effects (e.g., logging, state mutation, I/O)
     * without needing to return a value. It ensures both cases are handled explicitly,
     * improving code clarity and reducing the risk of missing a branch.
     *
     * @param someCase the action to execute if this Option contains a value ({@code isSome()})
     *                 The value is passed to the given {@link Consumer}
     * @param noneCase the action to execute if this Option is empty ({@code isNone()})
     *                 Represented as a {@link Runnable} since no value is available
     * @throws NullPointerException if {@code someCase} or {@code noneCase} is {@code null}
     */
    public abstract void matchRun(Consumer<? super T> someCase, Runnable noneCase);

    // ----------------------------------------------------------------
    // Optional: Interop with Java Optional
    // ----------------------------------------------------------------

    public java.util.Optional<T> toOptional() {
        return this.isSome() ? java.util.Optional.of(this.unwrap()) : java.util.Optional.empty();
    }

    // ----------------------------------------------------------------
    // Standard Methods
    // ----------------------------------------------------------------

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    // ----------------------------------------------------------------
    // Implementation: Some<T>
    // ----------------------------------------------------------------

    private static final class Some<T> extends Option<T> {

        private final T value;

        private Some(T value) {
            this.value = Objects.requireNonNull(value, "Value must not be null");
        }

        @Override
        public boolean isSome() {
            return true;
        }

        @Override
        public boolean isNone() {
            return false;
        }

        @Override
        public T unwrap() {
            return value;
        }

        @Override
        public T unwrapOr(T defaultValue) {
            return value;
        }

        @Override
        public T unwrapOrElse(Supplier<? extends T> supplier) {
            return value;
        }

        @Override
        public <U> Option<U> map(Function<? super T, ? extends U> mapper) {
            return ofNullable(Objects.requireNonNull(mapper, "Function must not be null").apply(value));
        }

        @Override
        public <U> Option<U> flatMap(Function<? super T, Option<U>> mapper) {
            return Objects.requireNonNull(mapper, "Function must not be null").apply(value);
        }

        @Override
        public Option<T> filter(Predicate<? super T> predicate) {
            return Objects.requireNonNull(predicate, "Predicate must not be null").test(value) ? this : none();
        }

        @Override
        public void ifPresent(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer, "Consumer must not be null").accept(value);
        }

        @Override
        public void ifEmpty(Runnable runnable) {
            // do nothing
        }

        @Override
        public <R> R match(Function<? super T, ? extends R> someCase, Supplier<? extends R> noneCase) {
            return Objects.requireNonNull(someCase, "Function must not be null").apply(value);
        }

        @Override
        public void matchRun(Consumer<? super T> someCase, Runnable noneCase) {
            Objects.requireNonNull(someCase, "Consumer must not be null").accept(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Some)) {
                return false;
            }
            Some<?> other = (Some<?>) obj;
            return Objects.equals(this.value, other.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Some(" + value + ")";
        }
    }

    // ----------------------------------------------------------------
    // Implementation: None<T>
    // ----------------------------------------------------------------

    private static final class None<T> extends Option<T> {

        // @formatter:off

        private None() { }

        // @formatter:on

        private static final None<?> NONE = new None<>();

        @Override
        public boolean isSome() {
            return false;
        }

        @Override
        public boolean isNone() {
            return true;
        }

        @Override
        public T unwrap() {
            throw new IllegalStateException("Called unwrap() on None");
        }

        @Override
        public T unwrapOr(T defaultValue) {
            return defaultValue;
        }

        @Override
        public T unwrapOrElse(Supplier<? extends T> supplier) {
            return Objects.requireNonNull(supplier, "Supplier must not be null").get();
        }

        @Override
        public <U> Option<U> map(Function<? super T, ? extends U> mapper) {
            return none();
        }

        @Override
        public <U> Option<U> flatMap(Function<? super T, Option<U>> mapper) {
            return none();
        }

        @Override
        public Option<T> filter(Predicate<? super T> predicate) {
            return this;
        }

        @Override
        public void ifPresent(Consumer<? super T> consumer) {
            // do nothing
        }

        @Override
        public void ifEmpty(Runnable runnable) {
            Objects.requireNonNull(runnable, "Runnable must not be null").run();
        }

        @Override
        public <R> R match(Function<? super T, ? extends R> someCase, Supplier<? extends R> noneCase) {
            return Objects.requireNonNull(noneCase, "Supplier must not be null").get();
        }

        @Override
        public void matchRun(Consumer<? super T> someCase, Runnable noneCase) {
            Objects.requireNonNull(noneCase, "Runnable must not be null").run();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof None;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString() {
            return "None";
        }
    }
}
