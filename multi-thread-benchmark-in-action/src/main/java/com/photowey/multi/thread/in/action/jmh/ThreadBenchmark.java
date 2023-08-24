/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.multi.thread.in.action.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(value = {Mode.AverageTime})
@OutputTimeUnit(value = TimeUnit.NANOSECONDS)
public class ThreadBenchmark {

    private static int[] ARRAY = new int[100_000_000];

    static {
        Arrays.fill(ARRAY, 1);
    }

    /**
     * 单核:
     * [root@photowey jmh]# java -jar ./multi-thread-benchmark.jar
     * # JMH version: 1.21
     * # VM version: JDK 1.8.0_232, OpenJDK 64-Bit Server VM, 25.232-b09
     * # VM invoker: /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.232.b09-0.el7_7.x86_64/jre/bin/java
     * # VM options: <none>
     * # Warmup: 3 iterations, 10 s each
     * # Measurement: 5 iterations, 10 s each
     * # Timeout: 10 min per iteration
     * # Threads: 1 thread, will synchronize iterations
     * # Benchmark mode: Average time, time/op
     * # Benchmark: com.photowey.jmh.ThreadBenchmark.testMultiThread
     *
     * # Run progress: 0.00% complete, ETA 00:02:40
     * # Fork: 1 of 1
     * # Warmup Iteration   1: 41098170.730 ns/op
     * # Warmup Iteration   2: 40935926.706 ns/op
     * # Warmup Iteration   3: 43582386.839 ns/op
     * Iteration   1: 42067774.807 ns/op
     * Iteration   2: 40679295.591 ns/op
     * Iteration   3: 40994226.939 ns/op
     * Iteration   4: 43092536.923 ns/op
     * Iteration   5: 39423846.535 ns/op
     *
     *
     * Result "com.photowey.jmh.ThreadBenchmark.testMultiThread":
     *   41251536.159 ±(99.9%) 5373561.532 ns/op [Average]
     *   (min, avg, max) = (39423846.535, 41251536.159, 43092536.923), stdev = 1395496.865
     *   CI (99.9%): [35877974.627, 46625097.691] (assumes normal distribution)
     *
     *
     * # JMH version: 1.21
     * # VM version: JDK 1.8.0_232, OpenJDK 64-Bit Server VM, 25.232-b09
     * # VM invoker: /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.232.b09-0.el7_7.x86_64/jre/bin/java
     * # VM options: <none>
     * # Warmup: 3 iterations, 10 s each
     * # Measurement: 5 iterations, 10 s each
     * # Timeout: 10 min per iteration
     * # Threads: 1 thread, will synchronize iterations
     * # Benchmark mode: Average time, time/op
     * # Benchmark: com.photowey.jmh.ThreadBenchmark.testSingleThread
     *
     * # Run progress: 50.00% complete, ETA 00:01:22
     * # Fork: 1 of 1
     * # Warmup Iteration   1: 39788283.897 ns/op
     * # Warmup Iteration   2: 39833198.734 ns/op
     * # Warmup Iteration   3: 39272378.243 ns/op
     * Iteration   1: 39407528.567 ns/op
     * Iteration   2: 39579225.423 ns/op
     * Iteration   3: 40006402.784 ns/op
     * Iteration   4: 38910016.085 ns/op
     * Iteration   5: 38579401.865 ns/op
     *
     *
     * Result "com.photowey.jmh.ThreadBenchmark.testSingleThread":
     *   39296514.945 ±(99.9%) 2161016.332 ns/op [Average]
     *   (min, avg, max) = (38579401.865, 39296514.945, 40006402.784), stdev = 561209.079
     *   CI (99.9%): [37135498.613, 41457531.276] (assumes normal distribution)
     *
     *
     * # Run complete. Total time: 00:02:43
     *
     * REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
     * why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
     * experiments, perform baseline and negative tests that provide experimental control, make sure
     * the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
     * Do not assume the numbers tell you what you want them to tell.
     *
     * Benchmark                         Mode  Cnt         Score         Error  Units
     * ThreadBenchmark.testMultiThread   avgt    5  41251536.159 ± 5373561.532  ns/op
     * ThreadBenchmark.testSingleThread  avgt    5  39296514.945 ± 2161016.332  ns/op
     */

    /**
     * 多核:
     * # JMH version: 1.21
     * # VM version: JDK 11.0.9, Java HotSpot(TM) 64-Bit Server VM, 11.0.9+7-LTS
     * # VM invoker: D:\photowey-ins\jdk11\jdk-11.0.9\bin\java.exe
     * # VM options: <none>
     * # Warmup: 3 iterations, 10 s each
     * # Measurement: 5 iterations, 10 s each
     * # Timeout: 10 min per iteration
     * # Threads: 1 thread, will synchronize iterations
     * # Benchmark mode: Average time, time/op
     * # Benchmark: com.photowey.jmh.ThreadBenchmark.testMultiThread
     * <p>
     * # Run progress: 0.00% complete, ETA 00:02:40
     * # Fork: 1 of 1
     * # Warmup Iteration   1: 17411859.722 ns/op
     * # Warmup Iteration   2: 16998655.857 ns/op
     * # Warmup Iteration   3: 16992461.290 ns/op
     * Iteration   1: 16979183.898 ns/op
     * Iteration   2: 17013243.124 ns/op
     * Iteration   3: 17017873.469 ns/op
     * Iteration   4: 17129125.470 ns/op
     * Iteration   5: 19183641.379 ns/op
     * <p>
     * <p>
     * Result "com.photowey.jmh.ThreadBenchmark.testMultiThread":
     * 17464613.468 ±(99.9%) 3706713.749 ns/op [Average]
     * (min, avg, max) = (16979183.898, 17464613.468, 19183641.379), stdev = 962621.789
     * CI (99.9%): [13757899.719, 21171327.217] (assumes normal distribution)
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
     * # Benchmark: com.photowey.jmh.ThreadBenchmark.testSingleThread
     * <p>
     * # Run progress: 50.00% complete, ETA 00:01:20
     * # Fork: 1 of 1
     * # Warmup Iteration   1: 41200804.918 ns/op
     * # Warmup Iteration   2: 41668439.004 ns/op
     * # Warmup Iteration   3: 48729524.757 ns/op
     * Iteration   1: 52714384.737 ns/op
     * Iteration   2: 41670139.583 ns/op
     * Iteration   3: 38384186.590 ns/op
     * Iteration   4: 38408752.490 ns/op
     * Iteration   5: 38375547.510 ns/op
     * <p>
     * <p>
     * Result "com.photowey.jmh.ThreadBenchmark.testSingleThread":
     * 41910602.182 ±(99.9%) 23890655.287 ns/op [Average]
     * (min, avg, max) = (38375547.510, 41910602.182, 52714384.737), stdev = 6204327.310
     * CI (99.9%): [18019946.895, 65801257.469] (assumes normal distribution)
     * <p>
     * <p>
     * # Run complete. Total time: 00:02:41
     * <p>
     * REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
     * why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
     * experiments, perform baseline and negative tests that provide experimental control, make sure
     * the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
     * Do not assume the numbers tell you what you want them to tell.
     * <p>
     * Benchmark                         Mode  Cnt         Score          Error  Units
     * ThreadBenchmark.testMultiThread   avgt    5  17464613.468 ±  3706713.749  ns/op
     * ThreadBenchmark.testSingleThread  avgt    5  41910602.182 ± 23890655.287  ns/op
     *
     * @param args
     * @throws RunnerException
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(ThreadBenchmark.class.getName())
                .forks(1)
                .warmupIterations(3)
                .measurementIterations(5)
                .build();
        Runner runner = new Runner(options);
        runner.run();
    }

    @Benchmark
    public int testMultiThread() {
        int[] array = ARRAY;
        FutureTask<Integer> t1 = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 0; i < 25_000_000; i++) {
                sum += array[0 + i];
            }

            return sum;
        });

        FutureTask<Integer> t2 = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 0; i < 25_000_000; i++) {
                sum += array[25_000_000 + i];
            }

            return sum;
        });

        FutureTask<Integer> t3 = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 0; i < 25_000_000; i++) {
                sum += array[50_000_000 + i];
            }

            return sum;
        });

        FutureTask<Integer> t4 = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 0; i < 25_000_000; i++) {
                sum += array[75_000_000 + i];
            }

            return sum;
        });

        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t3).start();
        new Thread(t4).start();

        int sum = 0;
        try {
            sum = t1.get() + t2.get() + t3.get() + t4.get();
        } catch (Exception e) {
        }
        return sum;
    }

    @Benchmark
    public int testSingleThread() {
        int[] array = ARRAY;
        FutureTask<Integer> t1 = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 0; i < 100_000_000; i++) {
                sum += array[i];
            }

            return sum;
        });

        new Thread(t1).start();
        int sum = 0;
        try {
            sum = t1.get();
        } catch (Exception e) {
        }

        return sum;
    }

}
