# `RocketMQ`

## 1.消息中间件的定义

> **消息中间件属于分布式系统中一个子系统,关注于数据的发送和接收,利用高效可靠的异步消息传递机制对分布式系统中的其余各个子系统进行集成.**
>
> - 高效
>   - 对于消息的处理速度快.
> - 可靠
>   - 有消息持久化机制和其他的机制确保消息不丢失.
> - 异步
>   - 送完一个请求,不需要等待返回,随时可以再发送下一个请求.

![](.\doc\mq.png)

## 2.消息中间件应用场景

### 1.应用解耦

> 系统的耦合性越高,容错性就越低.

![](.\doc\app-decoupling.png)

### 2.流量削峰

> 应用系统如果遇到系统请求流量的瞬间猛增,有可能会将系统压垮.
>
> 有了消息队列可以将大量请求缓存起来,分散到很长一段时间处理,这样可以大大提到系统的稳定性和用户体验.

![](.\doc\buffer-flow.png)

### 3.数据分发

> 通过消息队列可以让数据在多个系统之间进行流通.
>
> 数据的产生方不需要关心谁来使用数据,只需要将数据发送到消息队列,数据使用方直接在消息队列中直接获取数据即可.

> 加入 `MQ` 之前

![](.\doc\data-distribution.png)

> 加入 `MQ` 之后

![](.\doc\data-distribution-mq.png)



## 3.`RocketMQ`

> `RocketMQ `的设计基于主题的发布与订阅模式,其核心功能包括消息发送、消息存储(Broker)、消息消费,整体设计追求简单与性能第一.

![](.\doc\rocketmq.png)

### 3.1.核心概率

#### 3.1.1.`NameServer`

```json
# NameServer 是整个 RocketMQ 的"大脑",它是 RocketMQ 的服务注册中心,所以RocketMQ 需要先启动 NameServer  再启动 RocketMQ 中的 Broker.

# Broker 在启动时向所有 NameServer 注册(主要是服务器地址等),生产者在发送消息之前先从 NameServer 获取 Broker 服务器地址列表(消费者一样),然后根据负载均衡算法从列表中选择一台服务器进行消息发送.

# NameServer 与每台 Broker 服务保持长连接,并间隔 30S 检查 Broker 是否存活,如果检测到 Broker 宕机,则从路由注册表中将其移除.这样就可以实现 RocketMQ 的高可用.
```



#### 3.1.2.生产者(`Producer`)

```json
# 生产者: 也称为消息发布者,负责生产并发送消息至 RocketMQ.
```



#### 3.1.3.消费者(`Consumer`)

```json
# 消费者: 也称为消息订阅者,负责从 RocketMQ 接收并消费消息.
```



#### 3.1.3.消息(`Message`)

```json
# 消息:生产或消费的数据,对于 RocketMQ 来说,消息就是字节数组.
```



#### 3.1.4.主机(`Broker`)

```json
# RocketMQ 的核心,用于暂存和传输消息.
```



### 3.2.整体运转

- 1.`NameServer` 启动
- 2.`Broker` 启动且向 `NameServer ` 注册
- 3.生产者在发送某个主题的消息之前先从 `NamerServer` 获取 `Broker` 服务器地址列表(有可能是集群),然后根据负载均衡算法从列表中选择一台 `Broker `进行消息发送.
- 4.`NameServer ` 与每台 Broker 服务器保持长连接,并间隔 30S 检测 `Broker`是否存活,如果检测到 Broker宕机(使用心跳机制,如果检测超过120S),则从路由注册表中将其移除.
- 5.消费者在订阅某个主题的消息之前从 `NamerServer ` 获取 `Broker`服务器地址列表(有可能是集群),但是消费者选择从 `Broker`中订阅消息,订阅规则由 `Broker`配置决定.



### 3.3.概念模型

- 分组(`Group`)

  - 生产者

    - ```json
      # 标识发送同一类消息的 Producer,通常发送逻辑一致.
      # 发送普通消息的时候,仅标识使用,并无特别用处.
      # 主要作用用于事务消息:
      # 事务消息中如果某条发送某条消息的 Producer-A宕机,使得事务消息一直处于 PREPARED 状态并超时,则 Broker 会回查同一个 Group 的其它 Producer,确认这条消息应该 Commit 还是 Rollback
      ```

    - 

  - 消费者

    - ```json
      # 标识一类 Consumer 的集合名称,这类 Consumer 通常消费一类消息,且消费逻辑一致.同一个Consumer Group 下的各个实例将共同消费 Topic 的消息,起到负载均衡的作用.
      
      # 消费进度以 Consumer Group 为粒度管理,不同C onsumer Group 之间消费进度彼此不受影响,即消息A被  Consumer Group1消费过,也会再给 Consumer Group2 消费.
      ```

    - 

- 主题(`Topic`)

  - ```json
    # 标识一类消息的逻辑名字,消息的逻辑管理单位.
    # 无论消息生产还是消费,都需要指定 Topic.
    
    # 区分消息的种类:
    # 一个发送者可以发送消息给一个或者多个 Topic;
    # 一个消息的接收者可以订阅一个或者多个 Topic 消息.
    ```

  - 

- 标签(`Tag`)

  - ```json
    # RocketMQ 支持给在发送的时候给 Topic 打 Tag,同一个 Topic 的消息虽然逻辑管理是一样的.但是消费 Topic 的时候,如果你消费订阅的时候指定的是tagA,那么 tagB 的消息将不会投递.
    ```

  - 

- 消息队列(`Message Queue`)

  - ```json
    # 简称 Queue 或 Q.消息物理管理单位.一个 Topic 将有若干个 Q.
    # 若一个 Topic 创建在不同的 Broker 上,则不同的 Broker 上都有若干Q,消息将物理地存储落在不同 Broker 结点上,具有水平扩展的能力.
    
    # 无论生产者还是消费者,实际的生产和消费都是针对Q级别.
    # 例如 Producer 发送消息的时候,会预先选择(默认轮询)好该 Topic 下面的某一条Q发送;
    # Consumer 消费的时候也会负载均衡地分配若干个Q,只拉取对应Q的消息.
    
    # 每一条 Message Queue 均会对应一个文件,这个文件存储了实际消息的索引信息.并且即使文件被删除,也能通过实际纯粹的消息文件(Commit Log)恢复回来.
    ```

  - 

- 偏移量(`Offset`)

  - ```json
    # RocketMQ 中,有很多 Offset 的概念.
    # 一般我们只关心暴露到客户端的 Offset.不指定的话,就是指 Message Queue 下面的 Offset.
    
    # Message Queue 是无限长的数组.
    # 一条消息进来下标就会涨1,而这个数组的下标就是 Offset,Message Queue中的 Max Offset 表示消息的最大 Offset
    
    # Consumer Offset 可以理解为标记 Consumer Group 在一条逻辑 Message Queue 上,消息消费到哪里即消费进度.
    
    # 但从源码上看,这个数值是消费过的最新消费的消息 Offset + 1,即实际上表示的是下次拉取的 Offset 位置.
    ```

  - 

### 3.4.流程

#### 3.4.1.消息发送

- 1.创建消息生产者 `Producer`,并指定生产者组名(`Producer Group`)
- 2.指定 `NameServer` 地址
- 3.启动 `Producer`
- 4.创建消息对象
  - 消息体(`Body`)
  - 主题(`Topic`)
  - 标签(`Tag`)
  - `KEY`
- 5.发送消息
- 6.关闭生产者

#### 3.4.2.消息消费

- 1.创建消费者 `Consumer`,指定消费者组名(`Consumer Group`)
- 2.指定 `NameServer` 地址
- 3.执订订阅主题 `Topic  ` 和 `Tag`
- 4.可以设置回调函数,处理消息
- 5.启动消费者 `Consumer`



### 3.5.消息发送

#### 3.5.1.同步发送

```json
# 这种可靠性同步地发送方式使用的比较广泛,比如:重要的消息通知,短信通知.
# 同步发送是指消息发送方发出数据后,同步等待,直到收到接收方发回响应之后才发下一个请求.
```



#### 3.5.2.异步发送

```json
# 异步消息通常用在对响应时间敏感的业务场景,即发送端不能容忍长时间地等待 Broker 的响应.
```



#### 3.5.3.单向发送

```json
# 这种方式主要用在不特别关心发送结果的场景,例如日志发送.
```



#### 3.5.4.响应结果

```json
{
  "messageQueue": {
    "brokerName": "photowey",
    "queueId": 2,
    "topic": "rocketmq-topic-test"
  },
  "msgId": "7F00000135CC2437C6DC9E229D7D0002",
  "offsetMsgId": "C0A8000500002A9F0000000000005D8C",
  "queueOffset": 25,
  "regionId": "DefaultRegion",
  "sendStatus": "SEND_OK",
  "traceOn": true
}
```

- `MessageId`

  - ```json
    # 消息的全局唯一标识(内部机制的ID生成是使用机器IP和消息偏移量的组成,所以有可能重复,如果是幂等性还是最好考虑Key),由消息队列 MQ 系统自动生成,唯一标识某条消息.
    ```

  - 

- `SendStatus`

  - ```json
    # 发送的标识.成功,失败等.
    ```

  - 

- `MessageQueue`

  - ```json
    # 相当于是 Topic 的分区;用于并行发送和接收消息.
    ```



#### 3.5.5消息权衡

| 发送方式 | 发送 `TPS` | 发送结果反馈 | 可靠性   | 适应场景                                                     |
| -------- | ---------- | ------------ | -------- | ------------------------------------------------------------ |
| 同步发送 | 快         | 有           | 不丢失   | 重要通知邮件、报名短信通知、营销系统等                       |
| 异步发送 | 快         | 有           | 不丢失   | 用户视频上传后通知转码服务,转码完成后通知转码结果           |
| 单向发送 | 最快       | 无           | 可能丢失 | 适用于某些耗时非常短、但对可靠性要求不高的场景,例如: 日志采集 |




### 3.6.消息消费

#### 3.6.1.集群消息

```json
# 消费者的一种消费模式.
# 一个 Consumer Group 中的各个 Consumer 实例分摊去消费消息,即一条消息只会投递到一个 Consumer Group 下面的一个实例.

# 实际上,每个 Consumer 是平均分摊 Message Queue 的去做拉取消费.
# 例如某个 Topic 有3条Q,其中一个 Consumer Group 有 3 个实例(可能是 3 个进程,或者 3 台机器),那么每个实例只消费其中的1条Q.

# 而由 Producer 发送消息的时候是轮询所有的Q,所以消息会平均散落在不同的Q上,可以认为Q上的消息是平均的.那么实例也就平均地消费消息了.

# 这种模式下,消费进度(Consumer Offset)的存储会持久化到 Broker.
```

![](.\doc\cluster-consume.png)

#### 3.6.2.广播消息

```json
# 消费者的一种消费模式.
# 消息将对一个 Consumer Group 下的各个 Consumer 实例都投递一遍.
# 即使这些 Consumer 属于同一个 Consumer Group,消息也会被 Consumer Group 中的每个 Consumer 都消费一次.

# 实际上,是一个消费组下的每个消费者实例都获取到了 Topic 下面的每个Message Queue 去拉取消费.
# 所以消息会投递到每个消费者实例.

# 这种模式下,消费进度(Consumer Offset)会存储持久化到实例本地.
```

![](.\doc\broadcast-consume.png)

```json
# 消息消费时的权衡
# 集群模式:适用场景 & 注意事项
# 1.消费端集群化部署,每条消息只需要被处理一次.
# 2.由于消费进度在服务端维护,可靠性更高.
# 3.集群消费模式下,每一条消息都只会被分发到一台机器上处理.如果需要被集群下的每一台机器都处理,请使用广播模式.
# 4.集群消费模式下,不保证每一次失败重投的消息路由到同一台机器上,因此处理消息时不应该做任何确定性假设.

# 广播模式:适用场景&注意事项 
# 1.广播消费模式下不支持顺序消息.
# 2.广播消费模式下不支持重置消费位点.
# 3.每条消息都需要被相同逻辑的多台机器处理.
# 4.消费进度在客户端维护,出现重复的概率稍大于集群模式.
# 5.广播模式下,消息队列 RocketMQ 保证每条消息至少被每台客户端消费一次,但是并不会对消费失败的消息进行失败重投,因此业务方需要关注消费失败的情况.
# 6.广播模式下,客户端每一次重启都会从最新消息消费.客户端在被停止期间发送至服务端的消息将会被自动跳过,请谨慎选择.
# 7.广播模式下,每条消息都会被大量的客户端重复处理,因此推荐尽可能使用集群模式.

# 广播模式下服务端不维护消费进度,所以消息队列 RocketMQ 控制台不支持消息堆积查询、消息堆积报警和订阅关系查询功能.
```



#### 3.6.3.顺序消息

```json
# 消息有序指的是可以按照消息的发送顺序来消费(FIFO).RocketMQ 可以严格的保证消息有序,可以分为分区有序或者全局有序.

# 顺序消费的原理解析,在默认的情况下消息发送会采取 Round Robin 轮询方式把消息发送到不同的Queue(分区队列);而消费消息的时候从多个 Queue 上拉取消息,这种情况发送和消费是不能保证顺序.但是如果控制发送的顺序消息只依次发送到同一个 Queue 中,消费的时候只从这个 Queue 上依次拉取,则就保证了顺序.

# 当发送和消费参与的 Queue 只有一个,则是全局有序;
# 如果多个 Queue 参与,则为分区有序,即相对每个 Queue,消息都是有序的.
```



- 全局顺序
  
  - ![](.\doc\global-order.png)
- 部分顺序
  
- ![](.\doc\part-order.png)
  
- ```json
  # 使用顺序消息:
  # 首先要保证消息是有序进入MQ的,消息放入MQ之前,对id等关键字进行取模,
  # 放入指定 MessageQueue,Consume 消费消息失败时,不能返回 RECONSUME_LATER,这样会导致乱序应该返回 SUSPEND_CURRENT_QUEUE_A_MOMENT ,意思是先等一会,一会儿再处理这批消息,而不是放到重试队列里.
  
  # 消费时,同一个id等关键字获取到的肯定是同一个队列.从而确保一个订单中处理的顺序
  ```

- 

#### 3.6.4.延时消息

```json
# 延时消息:
# Producer 将消息发送到消息队列 RocketMQ 服务端,但并不期望这条消息立马投递,而是延迟一定时间后才投递到 Consumer 进行消费,该消息即延时消息.

# 适用场景
# 消息生产和消费有时间窗口要求:
# 比如在电商交易中超时未支付关闭订单的场景,在订单创建时会发送一条延时消息.这条消息将会在 30 分钟以后投递给消费者,消费者收到此消息后需要判断对应的订单是否已完成支付. 如支付未完成,则关闭订单.如已完成支付则忽略.
```



#### 3.6.5.批量消息

```json
# 批量发送消息能显著提高传递小消息的性能.
# 限制是这些批量消息应该有相同的 Topic,相同的 waitStoreMsgOK ,而且不能是延时消息.此外,这一批消息的总大小不应超过4 MB.

# 批量切分
# 如果消息的总长度可能大于4MB时,这时候最好把消息进行分割
```



#### 3.6.6.过滤消息

- `Tag` 过滤

- `SQL` 语法过滤

  - ```json
    # RocketMQ定义了一些基本语法来支持这个特性.你也可以很容易地扩展它.
    # 只有使用push模式的消费者才能用使用SQL92标准的sql语句.
    # 常用的语句如下:
    # 数值比较:比如:>,>=,<,<=,BETWEEN,=;
    # 字符比较:比如:=,<>,IN;
    # IS NULL 或者 IS NOT NULL;
    # 逻辑符号:AND,OR,NOT;
    
    # 常量支持类型为:
    # 数值,比如:123,3.1415;
    # 字符,比如:'RocketMQ',必须用单引号包裹起来;
    # NULL,特殊的常量;
    # 布尔值,TRUE 或 FALSE.
    
    
    # 需要修改Broker.conf配置文件
    # 加入 enablePropertyFilter=true 然后重启 Broker 服务
    ```



#### 3.6.7.事务消息

```json
# 其中分为两个流程:正常事务消息的发送及提交、事务消息的补偿流程.

# 正常事务流程
# (1) 发送消息(半事务消息).
# (2) 服务端响应消息写入结果.
# (3) 根据发送结果执行本地事务(如果写入失败,此时half消息对下游消费业务不可见,本地逻辑不执行).
# (4) 根据本地事务状态执行 Commit 或者 Rollback (Commit操作生成消息索引,消息对消费者可见)

# 事务补偿流程
# (1) 对没有 Commit/Rollback 的事务消息(Pending 状态的消息),从服务端发起一次"回查".
# (2) Producer 收到回查消息,检查回查消息对应的本地事务的状态.
# (3) 根据本地事务状态,重新 Commit 或者 Rollback.

# 其中,补偿阶段用于解决消息 Commit 或者 Rollback 发生超时或者失败的情况.

# 事务消息共有三种状态,提交状态、回滚状态、中间状态:
# COMMIT_MESSAGE: 提交状态,它允许消费者消费此消息.
# ROLLBACK_MESSAGE: 回滚状态,它代表该消息将被删除,不允许被消费.
# UNKNOW: 中间状态,它代表需要检查消息队列来确定状态.


# 使用场景
# 用户提交订单后,扣减库存成功、扣减优惠券成功、使用余额成功,但是在确认订单操作失败,需要对库存、库存、余额进行回退.

# 如何保证数据的完整性?
# 可以使用 RocketMQ 的分布式事务保证在下单失败后系统数据的完整性

-- -

# 使用限制
# 1.事务消息不支持延时消息和批量消息.

# 2.事务回查的间隔时间:BrokerConfig.transactionCheckInterval  通过 Broker 的配置文件设置好.

# 3.为了避免单个消息被检查太多次而导致半队列消息累积,默认将单个消息的检查次数限制为 15 次,但是用户可以通过 Broker 配置文件的 transactionCheckMax 参数来修改此限制.如果已经检查某条消息超过 N 次的话( N = transactionCheckMax ) 则 Broker 将丢弃此消息,并在默认情况下同时打印错误日志.

# 4.事务消息将在 Broker 配置文件中的参数 transactionMsgTimeout 这样的特定时间长度之后被检查.当发送事务消息时,用户还可以通过设置用户属性 CHECK_IMMUNITY_TIME_IN_SECONDS 来改变这个限制,该参数优先于 transactionMsgTimeout 参数.

# 5.事务性消息可能不止一次被检查或消费.

# 6.事务性消息中用到了生产者群组,这种就是一种高可用机制,用来确保事务消息的可靠性.

# 7.提交给用户的目标主题消息可能会失败,目前这依日志的记录而定.它的高可用性通过 RocketMQ 本身的高可用性机制来保证,如果希望确保事务消息不丢失、并且事务完整性得到保证,建议使用同步的双重写入机制.

# 8.事务消息的生产者 ID 不能与其他类型消息的生产者 ID 共享.与其他类型的消息不同,事务消息允许反向查询、MQ服务器能通过它们的生产者 ID 查询到消费者.
```

![](.\doc\transaction-message.png)



### 3.7.消息确认

```json
# 业务实现消费回调的时候,当且仅当此回调函数返回ConsumeConcurrentlyStatus.CONSUME_SUCCESS,RocketMQ 才会认为这批消息(默认是1条) 是消费完成的.中途断电,抛出异常等都不会认为成功——即都会重新投递.

# 返回ConsumeConcurrentlyStatus.RECONSUME_LATER,RocketMQ 就会认为这批消息消费失败了.如果业务的回调没有处理好而抛出异常,会认为是消费失败ConsumeConcurrentlyStatus.RECONSUME_LATER处理.

# 为了保证消息是肯定被至少消费成功一次,RocketMQ 会把这批消息重发回Broker( Topic 不是原 Topic 而是这个消费组的 RETRY Topic),在延迟的某个时间点(默认是10秒,业务可设置) 后,再次投递到这个ConsumerGroup.而如果一直这样重复消费都持续失败到一定次数(默认16次),就会投递到DLQ死信队列.应用可以监控死信队列来做人工干预.

# 另外如果使用顺序消费的回调 MessageListenerOrderly 时,由于顺序消费是要前者消费成功才能继续消费,所以没有 RECONSUME_LATER 的这个状态,只有 SUSPEND_CURRENT_QUEUE_A_MOMENT 来暂停队列的其余消费,直到原消息不断重试成功为止才能继续消费
```



### 3.8.`RocketMQ` 存储设计

#### 1.`Domain Model`

```json
# 领域模型(Domain Model)是对领域内的概念类或现实世界中对象的可视化表示.又称概念模型、领域对象模型、分析对象模型.
# 它专注于分析问题领域本身,发掘重要的业务领域概念,并建立业务领域概念之间的关系.
```

![](.\doc\domain.png)

- `Message`

  - ```json
    # Message 是 RocketMQ 消息引擎中的主体.
    # MessageId是全局唯一的.
    # MessageKey 是业务系统(生产者)生成的,所以如果要结合业务,可以使用 MessageKey 作为业务系统的唯一索引.
    
    # 另外Message中的equals方法和hashCode主要是为了完成消息只处理一次(Exactly-Once).
    
    # Exactly-Once 是指发送到消息系统的消息只能被消费端处理且仅处理一次,即使生产端重试消息发送导致某消息重复投递,该消息在消费端也只被消费一次.
    ```

  - 

- `Topic`

  - ```json
    # Tags是在同一Topic中对消息进行分类
    
    # subTopics == Message Queue,其实在内存逻辑中,subTopics 是对 Topics 的一个拓展,尤其是在 MQTT 这种协议下,在 Topic 底下会有很多 subTopics.
    ```

  - 

- `Queue`

  - ```json
    # Queue 是消息物理管理单位,比如在 RocketMQ 的控制台中,就可以看到每一个queue中的情况(比如消息的堆积情况、消息的TPS、QPS)
    ```

  - 

- `Offset`

  - ```json
    # 对于每一个 Queue 来说都有 Offset,这个是消费位点.
    ```

  - 

- `Group`

  - ```json
    # 业务场景中,如果有一堆发送者,一堆消费者,所以这里使用Group 的概念进行管理.
    ```

  - 

#### 2.`Store`

```json
# ├─commitlog
# ├─config
# ├─consumequeue
# └─index

# commitLog:消息存储目录
# config:运行期间一些配置信息
# consumerqueue:消息消费队列存储目录
# index:消息索引文件存储目录
# -- -
# abort:如果存在改文件则 Broker 非正常关闭
# checkpoint:文件检查点,存储:
# -- commitlog 文件最后一次刷盘时间戳、
# -- consumerqueue 最后一次刷盘时间,
# -- index 索引文件最后一次刷盘时间戳.
```



##### 2.1.消息存储结构

```json
# RocketMQ 消息的存储是由 ConsumeQueue 和 CommitLog 配合完成 的,消息真正的物理存储文件是 CommitLog,ConsumeQueue 是消息的逻辑队列,类似数据库的索引文件,存储的是指向物理存储的地址.
# 每个 Topic 下的每个 Message Queue 都有一个对应的ConsumeQueue文件.

# CommitLog:存储消息的元数据
# ConsumerQueue:存储消息在CommitLog的索引
# IndexFile:为了消息查询提供了一种通过key或时间区间来查询消息的方法,这种通过 IndexFile 来查找消息的方法不影响发送与消费消息的主流程.
```



![](.\doc\message-structure.png)

![](.\doc\message-structure-detail.png)

##### 2.2.过期文件删除

```json
# 由于 RocketMQ 操作 CommitLog,ConsumeQueue文件是基于内存映射机制并在启动的时候会加载 commitlog,ConsumeQueue 目录下的所有文件,为了避免内存与磁盘的浪费,不可能将消息永久存储在消息服务器上,所以需要引入一种机制来删除己过期的文件.

# 删除过程分别执行清理消息存储文件( Commitlog )与消息消费 队列文件( ConsumeQueue 文件), 消息消费队列文件与消息存储文件( Commitlog )共用一套过期文件机制.

# RocketMQ 清除过期文件的方法是:
# 1.如果非当前写文件在一定时间间隔内没有再次被更新,则认为是过期文件,可以被删除, RocketMQ 不会关注这个文件上的消息是否全部被消费.默认每个文件的过期时间为 48小时(不同版本的默认值不同,这里以4.8.0为例) ,通过在 Broker 配置文件中设置 fileReservedTime 来改变过期时间,单位为小时.

# 触发文件清除操作的是一个定时任务,而且只有定时任务,文件过期删除定时任务的周期由该删除决定,默认每10s执行一次.
```



##### 2.3.过期判断

```json
# 文件删除主要是由这个配置属性:fileReservedTime:文件保留时间.
# 也就是从最后一次更新时间到现在,如果超过了该时间,则认为是过期文件,可以删除.

# 另外还有其他两个配置参数:
# deletePhysicFilesInterval:删除物理文件的时间间隔(默认是100MS),在一次定时任务触发时,可能会有多个物理文件超过过期时间可被删除,因此删除一个文件后需要间隔 deletePhysicFilesInterval 这个时间再删除另外一个文件,由于删除文件是一个非常耗费IO的操作,会引起消息插入消费的延迟(相比于正常情况下),所以不建议直接删除所有过期文件.

# destroyMapedFileIntervalForcibly:在删除文件时,如果该文件还被线程引用,此时会阻止此次删除操作,同时将该文件标记不可用并且纪录当前时间戳destroyMapedFileIntervalForcibly这个表示文件在第一次删除拒绝后,文件保存的最大时间,在此时间内一直会被拒绝删除,当超过这个时间时,会将引用每次减少1000,直到引用 小于等于 0为止,即可删除该文件.
```



##### 2.4.删除条件

```json
# 1) 指定删除文件的时间点, RocketMQ 通过 deleteWhen 设置一天的固定时间执行一次.删除过期文件操作, 默认为凌晨4点.
# 2) 磁盘空间是否充足,如果磁盘空间不充足(DiskSpaceCleanForciblyRatio.磁盘空间强制删除文件水位.默认是85),会触发过期文件删除操作.

# 另外还有 RocketMQ 的磁盘配置参数:
# 1) 物理使用率大于 diskSpaceWarningLevelRatio (默认90%可通过参数设置),则会阻止新消息的插入.
# 2) 物理磁盘使用率小于 diskMaxUsedSpaceRatio (默认75%) 表示磁盘使用正常.
```



#### 3.零拷贝

```json
# 零拷贝(英语: Zero-copy) 技术是指计算机执行操作时，CPU不需要先将数据从某处内存复制到另一个特定区域。这种技术通常用于通过网络传输文件时节省CPU周期和内存带宽。

# 零拷贝技术可以减少数据拷贝和共享总线操作的次数，消除传输数据在存储器之间不必要的中间拷贝次数，从而有效地提高数据传输效率
# 零拷贝技术减少了用户进程地址空间和内核地址空间之间因为上下文切换而带来的开销。可以看出没有说不需要拷贝，只是说减少冗余[不必要]的拷贝。
```



##### 3.1.传统拷贝

```json
# 1) 第一次：将磁盘文件，读取到操作系统内核缓冲区；
# 2) 第二次：将内核缓冲区的数据，copy 到应用程序的 buffer；
# 3) 第三步：将 application 应用程序 buffer 中的数据，copy 到 socket 网络发送缓冲区(属于操作系统内核的缓冲区)；
# 4) 第四次：将 socket buffer 的数据，copy 到网卡，由网卡进行网络传输。

# 分析上述的过程，虽然引入DMA来接管CPU的中断请求，但四次copy是存在"不必要的拷贝"的。实际上并不需要第二个和第三个数据副本。
# 应用程序除了缓存数据并将其传输回套接字缓冲区之外什么都不做。
# 相反，数据可以直接从读缓冲区传输到套接字缓冲区。

# 显然，第二次和第三次数据 copy 其实在这种场景下没有什么帮助反而带来开销(DMA拷贝速度一般比CPU拷贝速度快一个数量级)，这也正是零拷贝出现的背景和意义。

# 总结下，传统的数据传送所消耗的成本：4次拷贝，4次上下文切换。
# 4次拷贝，其中两次是DMA copy，两次是CPU copy。
```

![](.\doc\traditional-copy.png)



##### 3.2.`MMAP`

```json
# 硬盘上文件的位置和应用程序缓冲区(application buffers)进行映射（建立一种一一对应关系），由于mmap()将文件直接映射到用户空间，所以实际文件读取时根据这个映射关系，直接将文件从硬盘拷贝到用户空间，只进行了一次数据拷贝，不再有文件内容从硬盘拷贝到内核空间的一个缓冲区。

# mmap 内存映射将会经历：3次拷贝: 1次cpu copy，2次DMA copy；
# mmap()是在 <sys/mman.h> 中定义的一个函数，此函数的作用是创建一个新的虚拟内存区域，并将指定的对象映射到此区域。mmap 其实就是通过内存映射的机制来进行文件操作。
```

![](.\doc\mmap-copy.png)



#### 4.存储总结

##### 4.1.消息生产与消息消费相互分离

```json
# Producer端发送消息最终写入的是 CommitLog（消息存储的日志数据文件），Consumer 端先从 ConsumeQueue（消息逻辑队列）读取持久化消息的起始物理位置偏移量 offset、大小size和消息Tag的HashCode值，随后再从 CommitLog 中进行读取待拉取消费消息的真正实体内容部分；
```



##### 4.2.`CommitLog`文件采用混合型存储

```json
# 所有的 Topic 下的消息队列共用同一个 CommitLog 的日志数据文件，并通过建立类似索引文件--ConsumeQueue 的方式来区分不同 Topic  下面的不同 MessageQueue 的消息，同时为消费消息起到一定的缓冲作用（异步服务先生成了 ConsumeQueue 队列的信息后，Consumer 端才能进行消费）。这样，只要消息写入并刷盘至 CommitLog 文件后，消息就不会丢失，即使C onsumeQueue 中的数据丢失，也可以通过 CommitLog 来恢复。
```



##### 4.3.读写顺序

```json
# 发送消息时，生产者端的消息确实是顺序写入 CommitLog；
# 订阅消息时，消费者端也是顺序读取ConsumeQueue，然而根据其中的起始物理位置偏移量 offset 读取消息真实内容却是随机读取 CommitLog。 

# 所以在 RocketMQ 集群整体的吞吐量、并发量非常高的情况下，随机读取文件带来的性能开销影响还是比较大的。
```



### 3.9.`RocketMQ` 高可用

```json
# brokerId=0                 代表主
# brokerId=1                 代表从（大于0都代表从）
# brokerRole=SYNC_MASTER     同步复制（主从）
# brokerRole=ASYNC_MASTER    异步复制（主从）
# flushDiskType=SYNC_FLUSH   同步刷盘
# flushDiskType=ASYNC_FLUSH  异步刷盘
```



```json
# RocketMQ 分布式集群是通过 Master 和 Slave 的配合达到高可用性的。

# Master 和 Slave 的区别：
# 在 Broker 的配置文件中，参数 brokerId=0 表明这个 Broker 是Master，大于0表明这个Broker是 Slave，同时brokerRole参数也会说明这个 Broker 是 Master 还是 Slave。

# Master 角色的 Broker 支持读和写，Slave 角色的 Broker 仅支持读，也就是 Producer 只能和 Master 角色的 Broker 连接写入消息；Consumer 可以连接 Master 角色的 Broker，也可以连接 Slave 角色的Broker 来读取消息。
```



#### 3.9.1.集群部署模式

- 单 `Master`

  - ```json
    # 也就是只有一个 Master 节点，称不上是集群，一旦这个 Master 节点宕机，那么整个服务就不可用。
    ```

  - 

- 多 `Master`

  - ```json
    # 多个 Master 节点组成集群，单个 Master 节点宕机或者重启对应用没有影响。
    
    # 优点：所有模式中性能最高（一个 Topic 可以分布在不同的master，进行横向拓展）在多主多从的架构体系下，无论使用客户端还是管理界面创建主题，一个主题都会创建多份队列在多主中（默认是4个的话，双主就会有8个队列，每台主4个队列，所以双主可以提高性能，一个 Topic 的分布在不同的 Master，方便进行横向拓展。
    
    # 缺点：单个 Master 节点宕机期间，未被消费的消息在节点恢复之前不可用，消息的实时性就受到影响。
    ```

  - 

- 多 `Master` 多 `Slave` 异步复制

  - ```json
    # 从节点(Slave)就是复制主节点的数据，对于生产者完全感知不到，对于消费者正常情况下也感知不到。（只有当 Master 不可用或者繁忙的时候，Consumer 会被自动切换到从 Slave 读）
    # 在多 Master 模式的基础上，每个 Master 节点都有至少一个对应的 Slave。Master 节点可读可写，但是 Slave 只能读不能写，类似于 MySQL 的主备模式。
    
    # 优点： 一般情况下都是 Master 消费，在 Master 宕机或超过负载时，消费者可以从 Slave 读取消息，消息的实时性不会受影响，性能几乎和多 Master 一样。
    # 缺点：使用异步复制的同步方式有可能会有消息丢失的问题。（Master宕机后，生产者发送的消息没有消费完，同时到 Slave 节点的数据也没有同步完）
    ```

  - 

- 多 `Master` 多 `Slave` 同步复制 + 异步刷盘

  - ```json
    # 优点：主从同步复制模式能保证数据不丢失。
    # 缺点：发送单个消息响应时间会略长，性能相比异步复制低10%左右。
    
    # 对数据要求较高的场景，主从同步复制方式，保存数据热备份，通过异步刷盘方式，保证 RocketMQ 高吞吐量。
    ```

#### 3.9.2.刷盘方式

- 同步刷盘

  - ```json
    # SYNC_FLUSH（同步刷盘）：
    # 生产者发送的每一条消息都在保存到磁盘成功后才返回告诉生产者成功。这种方式不会存在消息丢失的问题，但是有很大的磁盘IO开销，性能有一定影响。
    ```

  - 

- 异步刷盘

  - ```json
    # ASYNC_FLUSH（异步刷盘）：
    # 生产者发送的每一条消息并不是立即保存到磁盘，而是暂时缓存起来，然后就返回生产者成功。随后再异步的将缓存数据保存到磁盘，有两种情况：
    # 1.是定期将缓存中更新的数据进行刷盘;
    # 2.是当缓存中更新的数据条数达到某一设定值后进行刷盘。
    # 这种异步的方式会存在消息丢失（在还未来得及同步到磁盘的时候宕机），但是性能很好。默认是这种模式。
    ```

  - 

#### 3.9.3.主从复制

```json
# 集群环境下需要部署多个 Broker，Broker 分为两种角色：
# 一种是 Master，即可以写也可以读，其 brokerId=0，只能有一个；
# 另外一种是 Slave，只允许读，其 brokerId 为非0。

# 一个 Master 与多个 Slave 通过指定相同的 brokerClusterName 被归为1个 broker set（broker集）。通常生产环境中，我们至少需要2个 broker set。Master 的数据。

# 一个 Broker 组有 Master 和 Slave ，消息需要从 Master 复制到 Slave 上，有同步和异步两种复制方式。

# 同步复制和异步复制是通过 Broker 配置文件里的 brokerRole 参数进行设置的，这个参数可以被设置成ASYNC_MASTER、 SYNC_MASTER、SLAVE 三个值中的一个。
```

- 同步复制

  - ```json
    # 主从同步复制方式（Sync Broker）：
    # 生产者发送的每一条消息都至少同步复制到一个 Slave 后才返回告诉生产者成功，即"同步双写"在同步复制方式下，如果 Master 出故障， Slave 上有全部的备份数据，容易恢复，但是同步复制会增大数据写入延迟，降低系统吞吐量。
    ```

  - 

- 异步复制

  - ```json
    # 主从异步复制方式（Async Broker）：
    # 生产者发送的每一条消息只要写入 Master 就返回告诉生产者成功。然后再"异步复制"到 Slave。
    
    # 在异步复制方式下，系统拥有较低的延迟和较高的吞吐量，但是如果 Master 出了故障，有些数据因为没有被写入 Slave，有可能会丢失；
    ```

  - 

#### 3.9.4.消息生产高可用机制

```json
# 在创建 Topic 的时候，把 Topic 的多个 Message Queue 创建在多个 Broker 组上（相同 Broker 名称，不同 brokerId的 机器组成一个 Broker 组），这样当一个 Broker 组的 Master 不可用后，其他组的 Maste r仍然可用，Producer 仍然可以发送消息。 

# RocketMQ 目前不支持把 Slave 自动转成 Master，如果机器资源不足， 需要把 Slave 转成 Master，则要手动停止 Slave 角色的 Broker，更改配置文件，用新的配置文件启动Broker。
```

##### 3.9.4.1.高可用消息生产流程

![](.\doc\produce-ha.png)

```json
# 1) TopicA 创建在双主中，BrokerA 和 BrokerB 中，每一个 Broker 中有4个队列
# 2) 选择队列是，默认是使用轮训的方式，比如发送一条消息A时，选择 BrokerA 中的 Q4
# 3) 如果发送成功，消息A发结束。
# 4) 如果消息发送失败，默认会采用重试机制
# -- retryTimesWhenSendFailed 同步模式下内部尝试发送消息的最大次数,默认值是2
# -- retryTimesWhenSendAsyncFailed 异步模式下内部尝试发送消息的最大次数,默认值是2

# 5) 如果发生了消息发送失败，这里有一个规避策略（默认配置）
# 5.1) 默认不启用 Broker 故障延迟机制（规避策略）：如果是 BrokerA 宕机，上一次路由选择的是BrokerA 中的 Q4，那么再次重发的队列选择是 BrokerA 中的 Q1。但是这里的问题就是消息发送很大可能再次失败，引发再次重复失败，带来不必要的性能损耗。

# 注意: 这里的规避仅仅只针对消息重试，例如在一次消息发送过程中如果遇到消息发送失败，规避 Broker-A，但是在下一次消息发送时，即再次调用 DefaultMQProducer 的 send 方法发送消息时，还是会选择 Broker-A 的消息进行发送，只有继续发送失败后，重试时再次规避 Broker-A。

# 为什么会默认这么设计??
# 1、某一时间段，从 NameServer 中读到的路由中包含了不可用的主机
# 2、不正常的路由信息也是只是一个短暂的时间而已。
# 生产者每隔30s更新一次路由信息，而 NameServer 认为 Broker 不可用需要经过120s。
# 所以生产者要发送时认为 Broker 不正常（从NameServer拿到）和实际 Broker 不正常有延迟。


# 5.2) 启用Broker故障延迟机制
# 开启延迟规避机制，一旦消息发送失败（不是重试的）会将 Broker-A "悲观"地认为在接下来的一段时间内该 Broker 不可用，在为未来某一段时间内所有的客户端不会向该 Broker 发送消息。这个延迟时间就是通过 notAvailableDuration、latencyMax 共同计算的，就首先先计算本次消息发送失败所耗的时延，然后对应 latencyMax 中哪个区间，即计算在 latencyMax 的下标，然后返回 notAvailableDuration 同一个下标对应的延迟值。

# 这个里面涉及到一个算法,比如：在发送失败后，在接下来的固定时间（比如5分钟）内，发生错误的 Broker-A 中的队列将不再参加队列负载，发送时只选择 Broker-B 服务器上的队列。
# 如果所有的 Broker 都触发了故障规避，并且 Broker 只是那一瞬间压力大，那岂不是明明存在可用的 Broker，但经过你这样规避，反倒是没有 Broker 可用来，那岂不是更糟糕了。

# RocketMQ 默认不启用 Broker 故障延迟机制。
```



#### 3.9.5.消息消费高可用机制

##### 3.9.5.1.主从的高可用原理

```json
# 在 Consumer 的配置文件中，并不需要设置是从 Master 读还是从 Slave 读，当 Master 不可用或者繁忙的时候，Consumer 会被自动切换到从 Slave 读。有了自动切换 Consumer 这种机制，当一个 Master 角色的机器出现故障后，Consumer 仍然可以从 Slave 读取消息，不影响 Consumer 程序。这就达到了消费端的高可用性。

# 当前需要拉取的消息已经超过常驻内存的大小，表示主服务器繁忙，此时才建议从从服务器拉取。
# org.apache.rocketmq.store. DefaultMessageStore#getMessage

nextBeginOffset = offset + (i / ConsumeQueue.CQ_STORE_UNIT_SIZE);
// TODO 当前未被拉取到消费端的消息长度
long diff = maxOffsetPy - maxPhyOffsetPulling;
// TODO RocketMQ 消息常驻内存的大小
long memory = (long) (StoreUtil.TOTAL_PHYSICAL_MEMORY_SIZE
* (this.messageStoreConfig.getAccessMessageInMemoryMaxRatio() / 100.0));
// TODO 设置从服务器拉取
getResult.setSuggestPullingFromSlave(diff > memory);
```



##### 3.9.5.2.消息消费的重试

```json
# 消费端如果发生消息失败，没有提交成功，消息默认情况下会进入重试队列中。
# 注意重试队列的名字其实是跟消费群组有关，不是主题，因为一个主题可以有多个群组消费，所以要注意。
```



- 顺序消息重试

  - ```json
    # 对于顺序消息，当消费者消费消息失败后，消息队列 RocketMQ 会自动不断进行消息重试（每次间隔时间为 1 秒），这时，应用会出现消息消费被阻塞的情况。因此，在使用顺序消息时，务必保证应用能够及时监控并处理消费失败的情况，避免阻塞现象的发生。
    
    # 所以玩顺序消息时。Consumer 消费消息失败时，不能返回 RECONSUME_LATER，这样会导致乱序，应该返回 SUSPEND_CURRENT_QUEUE_A_MOMENT,意思是先等一会，一会儿再处理这批消息，而不是放到重试队列里。
    ```

  - 

- 无序消息重试

  - ```json
    # 对于无序消息（普通、延时、事务消息等），当消费者消费消息失败时，可以通过设置返回状态达到消息重试的结果。
    
    # 无序消息的重试只针对集群消费方式生效；
    
    # 广播方式不提供失败重试特性，即消费失败后，失败消息不再重试，继续消费新的消息。
    ```

- 重试次数

| **第几次重试** | **与上次重试的间隔时间** | **第几次重试** | **与上次重试的间隔时间** |
| -------------- | ------------------------ | -------------- | ------------------------ |
| 1              | 10 秒                    | 9              | 7 分钟                   |
| 2              | 30 秒                    | 10             | 8 分钟                   |
| 3              | 1 分钟                   | 11             | 9 分钟                   |
| 4              | 2 分钟                   | 12             | 10 分钟                  |
| 5              | 3 分钟                   | 13             | 20 分钟                  |
| 6              | 4 分钟                   | 14             | 30 分钟                  |
| 7              | 5 分钟                   | 15             | 1 小时                   |
| 8              | 6 分钟                   | 16             | 2 小时                   |

```json
# 如果消息重试 16 次后仍然失败，消息将不再投递。
# 如果严格按照上述重试时间间隔计算，某条消息在一直消费失败的前提下，将会在接下来的 4 小时 46 分钟之内进行 16 次重试，超过这个时间范围消息将不再重试投递。

# 注意：一条消息无论重试多少次，这些重试消息的 Message ID 不会改变。

# 集群消费方式下，消息消费失败后期望消息重试，需要在消息监听器接口的实现中明确进行配置
# 三种方式任选一种:
# > 返回 RECONSUME_LATER (推荐)
# > 返回 Null
# > 抛出异常

# 集群消费方式下，消息失败后期望消息不重试，需要捕获消费逻辑中可能抛出的异常，最终返回 CONSUME_SUCCESS，此后这条消息将不会再重试。
```



##### 3.9.5.3.自定义消息最大重试次数

```json
# 消息队列 RocketMQ 允许 Consumer 启动的时候设置最大重试次数，重试时间间隔将按照如下策略：
# > 最大重试次数小于等于 16 次，则重试时间间隔同上表描述。
# > 最大重试次数大于 16 次，超过 16 次的重试时间间隔均为每次 2 小时。

# 注意:⭐⭐⭐
# 消息最大重试次数的设置对相同 Group ID 下的所有 Consumer 实例有效。
# 如果只对相同 Group ID 下两个 Consumer 实例中的其中一个设置了 MaxReconsumeTimes，那么该配置对两个 Consumer 实例均生效。
# 配置采用覆盖的方式生效，即最后启动的 Consumer 实例会覆盖之前的启动实例的配置
```



##### 3.9.5.4.死信队列

```json
# 当一条消息初次消费失败，消息队列 RocketMQ 会自动进行消息重试；
# 达到最大重试次数后，若消费依然失败，则表明消费者在正常情况下无法正确地消费该消息，此时，消息队列 RocketMQ 不会立刻将消息丢弃，而是将其发送到该消费者对应的特殊队列中。
# 在消息队列 RocketMQ 中，这种正常情况下无法被消费的消息称为死信消息(Dead-Letter Message)，存储死信消息的特殊队列称为死信队列(Dead-Letter Queue)。

# 死信特性
# 1.死信消息具有以下特性：
# -- > 不会再被消费者正常消费。
# -- > 有效期与正常消息相同，均为 3 天，3 天后会被自动删除。因此，请在死信消息产生后的 3 天内及时处理。

# 2.死信队列具有以下特性：
# -- > 不会再被消费者正常消费。
# -- > 一个死信队列对应一个 Group ID， 而不是对应单个消费者实例。
# -- > 如果一个 Group ID 未产生死信消息，消息队列 RocketMQ 不会为其创建相应的死信队列。
# -- > 一个死信队列包含了对应 Group ID 产生的所有死信消息，不论该消息属于哪个 Topic。
```



#### 3.9.6.负载均衡

##### 3.9.6.1.`Producer `负载均衡

```json
# Producer 端，每个实例在发消息的时候，默认会轮询所有的 Message Queue 发送，以达到让消息平均落在不同的queue上。而由于 Queue 可以散落在不同的 Broker，所以消息就发送到不同的 broker 下.
# 发布方会把第一条消息发送至 Queue 0，然后第二条消息发送至 Queue 1，以此类推...
```

![](.\doc\produce-banlance.png)

##### 3.9.6.2.`Consumer` 负载均衡

- 集群模式

  - ```json
    # 在集群消费模式下，每条消息只需要投递到订阅这个topic的Consumer Group下的一个实例即可。RocketMQ采用主动拉取的方式拉取并消费消息，在拉取的时候需要明确指定拉取哪一条message queue。而每当实例的数量有变更，都会触发一次所有实例的负载均衡，这时候会按照queue的数量和实例的数量平均分配queue给每个实例。
    
    # 默认的分配算法是: AllocateMessageQueueAveragely 
    # 还有另外一种平均的算法是 AllocateMessageQueueAveragelyByCircle，Queue，只是以环状轮流分 Queue 的形式.
    
    # -- -
    
    # 需要注意的是，集群模式下，Queue 都是只允许分配一个实例，这是由于如果多个实例同时消费一个 Queue 的消息，由于拉取哪些消息是 Consumer 主动控制的，那样会导致同一个消息在不同的实例下被消费多次，所以算法上都是一个 Queue 只分给一个 Consumer 实例，一个 Consumer Queue。
    
    # 通过增加 Consumer 实例去分摊 Queue 的消费，可以起到水平扩展的消费能力的作用。而有实例下线的时候，会重新触发负载均衡，这时候原来分配到的 Queue 将分配到其他实例上继续消费。但是如果 Consumer 实例的数量比 Message Queue 的总数量还多的话，多出来的 Consumer 实例将无法分到 Queue，也就无法消费到消息，也就无法起到分摊负载的作用了。所以需要控制让 Queue 的总数量大于等于 Consumer 的数量。
    ```

- 广播模式

  - ```json
    # 由于广播模式下要求一条消息需要投递到一个消费组下面所有的消费者实例，所以也就没有消息被分摊消费的说法。
    # 在实现上，其中一个不同就是在 Consumer 分配 Queue 的时候，所有 Consumer 都分到所有的 Queue。
    ```

![](.\doc\consume-banlance.png)



## 问题思考

- 1.`NameServer`、`Broker`、`Producer`、`Consumer` 的连通性
- 2.`Producer`、`Consumer` 连接的建立时机，有何关系？
- 3.`NameServer` 存储哪些信息，如何存储？
- 4.`Topic `的持久化存储是在`NameServer`中还是在`Broker`?

### 架构

![](.\doc\rocketmq-cluster.png)

```json
# NameServer 集群中它们相互之间是不通讯
# > 生产者同一时间，与 NameServer 集群中其中一台建立长连接。
# > 生产者与 Broker 之间的 Master 保持长连接。
# > 消费者同一时间，与 NameServer 集群中其中一台建立长连接。
# > 消费者与所有 Broker 建立长连接。
```

### 核心组件

![](.\doc\rocketmq-components.png)

```json
# NameServer 要作用是为消息生产者、消息消费者提供关于主题 Topic 的路由信息，NameServer 除了要存储路由的基础信息，还要能够管理 Broker 节点，包括路由注册、路由删除等功能。

# Broker 它能接收 Producer 和 Consumer 的请求，并调用store层服务对消息进行处理。
# HA服务的基本单元，支持同步双写，异步双写等模式。

# Netty Remoting Server 与 Netty Remoting Client
# 基于 netty 的底层通信实现，所有服务间的交互都基于此模块。
# 也区分服务端和客户端。
```



### `NameServer`

```json
# topicQueueTable：             Topic 消息队列路由信息，消息发送时根据路由表进行负载均衡
# brokerAddrTable：             Broker 基础信息，包括 brokerName、所属集群名称、主备 Broker 地址
# clusterAddrTable：            Broker 集群信息，存储集群中所有 Broker 名称
# brokerLiveTable：             Broker 状态信息，NameServer 每次收到心跳包是会替换该信息
# filterServerTable：           Broker 上的 FilterServer 列表，用于类模式消息过滤。

# NameServer 的实现基于内存，NameServer 并不会持久化路由信息，持久化的重任是交给 Broker 来完成。
```



### Producer

#### 消息发送流程

- 验证消息

  - ```java
    Validators.checkMessage(msg, this.defaultMQProducer);
    ```

  - 

- 查找路由

  - ```java
    TopicPublishInfo topicPublishInfo = this.tryToFindTopicPublishInfo(msg.getTopic());
    ```

  - 

- 选择队列

  - ```java
    MessageQueue mqSelected = this.selectOneMessageQueue(topicPublishInfo, lastBrokerName);
    ```

  - 

- 消息发送

  - ```java
    sendResult = this.sendKernelImpl(msg, mq, communicationMode, sendCallback, topicPublishInfo, timeout - costTime);
    ```

  - 



### 存储

#### 存储设计层次

![](.\doc\store.png)





### Store架构设计之消息消费

![](.\doc\store-consume.png)



### `CommitLog ` 的"随机读"对性能的影响？

```json
# RocketMQ 中，所有的队列存储一个文件（commitlog）中，所以 Rocketmq 是顺序写I/O。
# 随机读，每次读消息时先读逻辑队列consumQue中的元数据，再从commitlog中找到消息体。
# 增加了开销。
# 那么在RocketMQ中是怎么优化的?
# > 1) 本身无论是Commitlog文件还是Consumequeue文件，都通过MMAP内存映射。
# > 2) 本身存储Commitlog采用写时复制的容器处理，实现读写分离，所以很大程度上可以提高一些效率。
```



### 对外内存

```json
# 我们根据之前了解可以，一般情况下RocketMQ是通过MMAP内存映射，生产时消息写入内存映射文件，然后消费的时候再读。

# 但是RocketMQ还提供了一种机制。
# TransientStorePool,短暂的存储池(堆外内存)。
# RocketMQ单独创建一个ByteBuffer内存缓存池，用来临时存储数据，数据先写入该内存映射中，然后由commit线程定时将数据从该内存复制到与目标物理文件对应的内存映射中。

# RocketMQ引入该机制主要的原因是提供一种内存锁定，将当前堆外内存一直锁定在内存中，避免被进程将内存交换到磁盘。同时因为是堆外内存，这么设计可以避免频繁的GC

# -- -

# 两种方式的对比
# 1) 默认方式，Mmap+PageCache 的方式，读写消息都走的是 PageCache，这样子读写都在 PageCache 里面不可避免会有锁的问题，在并发的读写操作情况下，会出现缺页中断降低，内存加锁，污染页的回写(脏页面)。

# 2) 堆外缓冲区，DirectByteBuffer(堆外内存)+PageCache 的两层架构方式，这样子可以实现读写消息分离，写入消息时候写到的是DirectByteBuffer——堆外内存中,读消息走的是 PageCache(对于,DirectByteBuffer是两步刷盘，一步是刷到 PageCache，还有一步是刷到磁盘文件中)，带来的好处就是，避免了内存操作的很多容易堵的地方，降低了时延，比如说缺页中断降低，内存加锁，污染页的回写。

# 所以使用堆外缓冲区的方式相对来说会比较好，但是肯定的是，需要消耗一定的内存，如果服务器内存吃紧就不推荐这种模式，同时的话，堆外缓冲区的话也需要配合异步刷盘才能使用。
```

![](.\doc\transient-pool.png)



### `ConsumeQueue`

![](.\doc\consume-queue.png)

```json
# 连续发送5条消息，消息是不定长，首先所有信息先放入 Commitlog中，每一条消息放入Commitlog的时候都需要上锁，确保顺序的写入。

# 当Commitlog写成功了之后。数据再同步到ConsunmeQueue中。并且数据一条条分发，这个是一个典型的轮训。

# Queue Offset 代表一个Queue中的第几条消息
# Logic Offset就是 Queue Offset * 20  因为每一条ConsumeQueue中的消息长度都是20.
# Physical Offset,这个是在 Commitlog中每一条消息偏移量。

# 这种设计非常的巧妙：
# 查找消息的时候，可以直按根据队列的消息序号，计算出索引的全局位置（比如序号2，就知道偏移量是40），然后直接读取这条索引，再根据索引中记录的消息的全局位置，找到消息、这里面比较耗时两个操作就是分别找到索引和消息所在文件，这两次查找是差不多的，都可以抽象成:

# 1) 因为每个索引文件或者消息文件的长度的是固定的，对于每一组文件，都维护了一个由小到大有序的文件数组。查找文件的时候，直接通过计算即可获取文件在数组中的序号:

# 2) 文件在数组中的序号=(全局位置-第一个文件的文件名)/文件固定大小在通过序号在数组中获取数据的时间复杂度是0(1)，二次查找文件的时间复杂度也是是:0(1)+0(1) =0 (1)，所以消费时查找数据的时间复杂度也是O(1)。
```



### `Rebalance`

> 触发时机

- 消费者启动
- 消费者加入或者退出消费组
- 定时触发`Rebalance`(10s)



### 并发消费

```json
# 1.在消费者启动之后，第一步都要从NameServer中获取Topic相关信息
# GET_ROUTEINFO_BY_TOPIC

# 2.消费者拿到topic相关信息之后，第2步需要知道Topic中有哪些queue,并且消费的时候还跟消费者分组相关。所以这里就需要根据group获取相关信息。
# GET_CONSUMER_LIST_BY_GROUP

# 3.当我们拿到了消费者Group下的所有信息之后，这个就可以做分配，可以分配到比如自己这台消费者的应该要消费哪些主机上的哪些队列。

# 确定了消费者的group、topic、还有queue之后，还需要知道从哪个位置开始消费。于是还需要获取Queue Offset。
# QUERY_CONSUMER_OFFSET

# 确定了消费者的group、topic、还有queue和需要获取Queue的Offset，就要正式开始拉取消息了。
# 拉到消息后，消费者就要进行消息的消费了。消费完了之后，要更新offset，这个时候也要发起调用
# UPDATE_CONSUMER_OFFSET
# 这个地方要注意有两种方式：
# 1) 定时，默认5s提交
# 2) 前面步骤的拉取消息时会带入参数: commitoffset,这个时候也会更新。

# 最后的话，消费者关闭的话，也会调用
# UNREGISTER_CLIENT
```



### 顺序消费

```json
# 顺序消费的主体步骤和并发消费差不多，主要的差别就是有一个加锁和解锁的过程。
# 只要确定了是拉哪个queue。
# 这个地方要加锁，加锁的目的就可以达到顺序性。在一个queue中消息是顺序的，当一个消费者确定了一个 queue 进行消费时，使用一个分布式锁机制，是不是就可以确定这个消费者的顺序性。

# 加锁 QUEUE
# LOCK_BATCH_MQ

# 解锁 QUEUE
# UNLOCK_BATCH_MQ
```

