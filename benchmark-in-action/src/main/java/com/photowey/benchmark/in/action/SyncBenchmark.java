/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.benchmark.in.action;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * {@code SyncBenchmark}
 *
 * @author photowey
 * @date 2021/11/18
 * @see * http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/
 * @since 1.0.0
 */
//@Fork(1)
//@Warmup(iterations = 3)
//@Measurement(iterations = 5)
@BenchmarkMode(value = {Mode.AverageTime})
@OutputTimeUnit(value = TimeUnit.NANOSECONDS)
public class SyncBenchmark {

    /**
     * # JMH version: 1.21
     * # VM version: JDK 11.0.9, Java HotSpot(TM) 64-Bit Server VM, 11.0.9+7-LTS
     * # VM invoker: D:\photowey-ins\jdk11\jdk-11.0.9\bin\java.exe
     * # VM options: <none>
     * # Warmup: 3 iterations, 10 s each
     * # Measurement: 5 iterations, 10 s each
     * # Timeout: 10 min per iteration
     * # Threads: 1 thread, will synchronize iterations
     * # Benchmark mode: Average time, time/op
     * # Benchmark: com.photowey.benchmark.in.action.SyncBenchmark.methodA
     * <p>
     * # Run progress: 0.00% complete, ETA 00:02:40
     * # Fork: 1 of 1
     * # Warmup Iteration   1: 1.351 ns/op
     * # Warmup Iteration   2: 1.366 ns/op
     * # Warmup Iteration   3: 1.348 ns/op
     * Iteration   1: 1.350 ns/op
     * Iteration   2: 1.349 ns/op
     * Iteration   3: 1.352 ns/op
     * Iteration   4: 1.349 ns/op
     * Iteration   5: 1.362 ns/op
     * <p>
     * <p>
     * Result "com.photowey.benchmark.in.action.SyncBenchmark.methodA":
     * 1.352 ±(99.9%) 0.021 ns/op [Average]
     * (min, avg, max) = (1.349, 1.352, 1.362), stdev = 0.006
     * CI (99.9%): [1.331, 1.374] (assumes normal distribution)
     * <p>
     * <p>
     * # JMH version: 1.21
     * # VM version: JDK 11.0.9, Java HotSpot(TM) 64-Bit Server VM, 11.0.9+7-LTS
     * # VM invoker: D:\photowey-ins\jdk11\jdk-11.0.9\bin\java.exe
     * # VM options: <none>
     * # Warmup: 3 iterations, 10 s each
     * # Measurement: 5 iterations, 10 s each
     * # Timeout: 10 min per iteration
     * # Threads: 1 thread, will synchronize iterations
     * # Benchmark mode: Average time, time/op
     * # Benchmark: com.photowey.benchmark.in.action.SyncBenchmark.methodB
     * <p>
     * # Run progress: 50.00% complete, ETA 00:01:20
     * # Fork: 1 of 1
     * # Warmup Iteration   1: 1.367 ns/op
     * # Warmup Iteration   2: 1.365 ns/op
     * # Warmup Iteration   3: 1.364 ns/op
     * Iteration   1: 1.364 ns/op
     * Iteration   2: 1.367 ns/op
     * Iteration   3: 1.351 ns/op
     * Iteration   4: 1.346 ns/op
     * Iteration   5: 1.350 ns/op
     * <p>
     * <p>
     * Result "com.photowey.benchmark.in.action.SyncBenchmark.methodB":
     * 1.356 ±(99.9%) 0.036 ns/op [Average]
     * (min, avg, max) = (1.346, 1.356, 1.367), stdev = 0.009
     * CI (99.9%): [1.320, 1.391] (assumes normal distribution)
     * <p>
     * <p>
     * # Run complete. Total time: 00:02:40
     * <p>
     * REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
     * why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
     * experiments, perform baseline and negative tests that provide experimental control, make sure
     * the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
     * Do not assume the numbers tell you what you want them to tell.
     * <p>
     * Benchmark              Mode  Cnt  Score   Error  Units
     * SyncBenchmark.methodA  avgt    5  1.352 ± 0.021  ns/op
     * SyncBenchmark.methodB  avgt    5  1.356 ± 0.036  ns/op
     */

    private static int condition = 0;

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(SyncBenchmark.class.getName())
                .forks(1)
                .warmupIterations(3)
                .measurementIterations(5)
                .build();
        Runner runner = new Runner(options);
        runner.run();
    }

    /**
     * 方法A和方发B - 性能差不多
     * JIT 会进行-锁消除
     */

    @Benchmark
    public static void methodA() {
        condition++;
    }

    @Benchmark
    public static void methodB() {
        // 锁消除
        Object lock = new Object();
        synchronized (lock) {
            condition++;
        }
    }
}
