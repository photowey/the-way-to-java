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
/**
 * {@code com.photowey.juc.in.action.queue}
 * 阻塞队列
 *
 * @author photowey
 * @date 2022/08/14
 * @since 1.0.0
 */
package com.photowey.juc.in.action.queue;

/**
 * 阻塞队列
 * 1.数组阻塞队列(ArrayBlockingQueue)
 * - 底层基于数组的有界阻塞队列，初始化时需要指定队列大小；
 * <p>
 * 2.链表阻塞队列(LinkedBlockingQueue)
 * - 以链表来存储元素，理论上只要存储空间够大，就是无界的；
 * <p>
 * 3.同步阻塞队列(SynchronousQueue)
 * - 队列中不存储元素，队列中放入元素后，只有该元素被消费完成，才能重修放入元素；
 * <p>
 * 4.优先级无界阻塞队列(PriorityBlockingQueue)
 * - 底层基于数组的无界队列，支持队列内部按照指定元素排序；
 * <p>
 * 5.链表阻塞双端队列(LinkedBlockingDeque)
 * - 底层基于链表的有界双端阻塞队列；
 * <p>
 * 6.延迟无界阻塞队列(DelayQueue)
 * - 底层是基于数组的无界延迟队列，它是在PriorityQueue基础上实现的，先按延迟优先级排序，延迟时间短的排在队列前面；
 * <p>
 * 7.链表阻塞队列与同步阻塞队列结合(LinkedTransferQueue)
 * - 基于链表的无界阻塞队列；
 */