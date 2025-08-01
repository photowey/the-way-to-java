package com.photowey.spring.project.infras.in.action.rust.v2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@code OptionTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/01
 */
class OptionTest {

    @Test
    void testSome() {
        Option<Integer> maybeSome = Option.some(10086);

        Assertions.assertTrue(maybeSome.isSome());
        Assertions.assertFalse(maybeSome.isNone());

        int value = maybeSome.unwrap();
        Assertions.assertEquals(10086, value);
    }

    @Test
    void testNone() {
        Option<Integer> maybeNone = Option.none();

        Assertions.assertTrue(maybeNone.isNone());
        Assertions.assertFalse(maybeNone.isSome());
    }

    @Test
    void testOf() {
        Option<Integer> maybeSome = Option.of(10086);
        Assertions.assertTrue(maybeSome.isSome());
        Assertions.assertFalse(maybeSome.isNone());

        int value = maybeSome.unwrap();
        Assertions.assertEquals(10086, value);

        Option<Integer> maybeNone = Option.of(null);

        Assertions.assertTrue(maybeNone.isNone());
        Assertions.assertFalse(maybeNone.isSome());
    }

    @Test
    void testUnwrapOr() {
        Option<Integer> maybeSome = Option.of(10086);
        Assertions.assertTrue(maybeSome.isSome());

        Integer unwrapped = maybeSome.unwrapOr(8848);
        Assertions.assertEquals(10086, unwrapped);

        Option<Integer> maybeNone = Option.of(null);
        Integer nullUnwrapped = maybeNone.unwrapOr(8848);
        Assertions.assertEquals(8848, nullUnwrapped);
    }

    @Test
    void testUnwrapOrElse() {
        Option<Integer> maybeSome = Option.of(10086);
        Assertions.assertTrue(maybeSome.isSome());

        Integer unwrapped = maybeSome.unwrapOrElse(() -> 8848);
        Assertions.assertEquals(10086, unwrapped);

        Option<Integer> maybeNone = Option.of(null);
        Integer nullUnwrapped = maybeNone.unwrapOrElse(() -> 8848);
        Assertions.assertEquals(8848, nullUnwrapped);
    }

    @Test
    void testMap() {
        Option<Integer> maybeSome = Option.of(1024);
        Assertions.assertTrue(maybeSome.isSome());

        Option<Integer> mapped = maybeSome.map(it -> it << 1);
        Assertions.assertTrue(mapped.isSome());

        int value = mapped.unwrap();
        Assertions.assertEquals(2048, value);


        Option<Integer> mappedNull = maybeSome.map(it -> null);
        Assertions.assertTrue(mappedNull.isNone());
    }

    @Test
    void testFlatMap() {
        Option<Integer> maybeSome = Option.of(1024);
        Assertions.assertTrue(maybeSome.isSome());

        Option<Integer> mapped = maybeSome.flatMap(it -> Option.some(it << 1));
        Assertions.assertTrue(mapped.isSome());

        int value = mapped.unwrap();
        Assertions.assertEquals(2048, value);


        Option<Integer> mappedNull = maybeSome.flatMap(it -> Option.none());
        Assertions.assertTrue(mappedNull.isNone());
    }

    @Test
    void testFilter() {
        Option<Integer> maybeSome = Option.of(1024);
        Assertions.assertTrue(maybeSome.isSome());
        Option<Integer> filtered = maybeSome.filter(it -> it > 1024);
        Assertions.assertTrue(filtered.isNone());
    }

    @Test
    void testIfPresent() {
        Counter counter = Counter.builder()
            .value(0)
            .build();

        Option<Integer> maybeSome = Option.of(1024);
        Assertions.assertTrue(maybeSome.isSome());
        maybeSome.ifPresent(it -> counter.increment());
        Assertions.assertEquals(1, counter.value());

        counter.reset();

        Option<Integer> maybeNone = Option.of(null);
        Assertions.assertTrue(maybeNone.isNone());
        maybeNone.ifPresent(it -> counter.decrement());
        Assertions.assertEquals(0, counter.value());
    }

    @Test
    void testIfEmpty() {
        Counter counter = Counter.builder()
            .value(0)
            .build();

        Option<Integer> maybeSome = Option.of(1024);
        Assertions.assertTrue(maybeSome.isSome());
        maybeSome.ifEmpty(counter::increment);
        Assertions.assertEquals(0, counter.value());

        counter.reset();

        Option<Integer> maybeNone = Option.of(null);
        Assertions.assertTrue(maybeNone.isNone());
        maybeNone.ifEmpty(counter::decrement);
        Assertions.assertEquals(-1, counter.value());
    }

    @Test
    void testMatch() {
        Counter counter = Counter.builder()
            .value(0)
            .build();

        Option<Integer> maybeSome = Option.of(1024);
        Assertions.assertTrue(maybeSome.isSome());
        Integer value = maybeSome.match(it -> counter.increment(), counter::decrement);
        Assertions.assertEquals(1, value);
        Assertions.assertEquals(1, counter.value());

        counter.reset();

        Option<Integer> maybeNone = Option.of(null);
        Assertions.assertTrue(maybeNone.isNone());
        Integer nullValue = maybeNone.match(it -> counter.increment(), counter::decrement);
        Assertions.assertEquals(-1, nullValue);
        Assertions.assertEquals(-1, counter.value());
    }

    @Test
    void testMatchRun() {
        Counter counter = Counter.builder()
            .value(0)
            .build();

        Option<Integer> maybeSome = Option.of(1024);
        Assertions.assertTrue(maybeSome.isSome());
        maybeSome.matchRun(it -> counter.increment(), counter::decrement);
        Assertions.assertEquals(1, counter.value());

        counter.reset();

        Option<Integer> maybeNone = Option.of(null);
        Assertions.assertTrue(maybeNone.isNone());
        maybeNone.matchRun(it -> counter.increment(), counter::decrement);
        Assertions.assertEquals(-1, counter.value());
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private final static class Counter {

        private int value;

        // ----------------------------------------------------------------

        public int value() {
            return value;
        }

        // ----------------------------------------------------------------

        public int reset() {
            this.value = 0;

            return this.value;
        }

        // ----------------------------------------------------------------

        public int increment() {
            this.value++;

            return this.value;
        }

        public int decrement() {
            this.value--;

            return this.value;
        }
    }
}
