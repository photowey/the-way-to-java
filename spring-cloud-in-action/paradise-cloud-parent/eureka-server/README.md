# Eureka

## 1.`Eureka` 核心概念

> 整体上可以分为两个主体: Eureka Server 和 `Eureka Client`



### 1.1.`Eureka Server`

- **服务注册**

  - ```json
    # 服务提供者启动时,会通过 `Eureka Client` 向 Eureka Server 注册信息,Eureka Server 会存储该服务的信息,Eureka Server 内部有二层缓存机制来维护整个注册表.
    ```

- **提供注册表**

  - ```json
    # 服务消费者在调用服务时,如果 `Eureka Client` 没有缓存注册表的话,会从 Eureka Server 获取最新的注册表.
    ```

- **同步状态**

  - ```json
    # `Eureka Client` 通过注册、心跳机制和 Eureka Server 同步当前客户端的状态.
    ```


### 1.2.`Eureka  Client`

```json
# `Eureka Client` 是一个 Java 客户端,用于简化与 Eureka Server 的交互.`Eureka Client` 会拉取、更新和缓存 Eureka Server 中的信息.因此当所有的 Eureka Server 节点都宕掉,服务消费者依然可以使用缓存中的信息找到服务提供者,但是当服务有更改的时候会出现信息不一致.
```



### 1.3.`Register`

```json
# 服务的提供者,将自身注册到注册中心,服务提供者也是一个 `Eureka Client`.当 `Eureka Client` 向 Eureka Server 注册时,它提供自身的元数据,比如 IP 地址、端口,运行状况指示符 URL,主页等.
```



### 1.4.`Renew`

```json
# `Eureka Client` 会每隔 30 秒发送一次心跳来续约. 通过续约来告知 Eureka Server 该 `Eureka Client` 运行正常,没有出现问题. 默认情况下,如果 Eureka Server 在 90 秒内没有收到 `Eureka Client` 的续约,Server 端会将实例从其注册表中删除,此时间可配置,一般情况不建议更改.
```



### 1.5.`Eviction`

```json
# 当 `Eureka Client` 和 Eureka Server 不再有心跳时,Eureka Server 会将该服务实例从服务注册列表中删除,即服务剔除.
```



### 1.6.`GetRegisty`

```json
# `Eureka Client` 从服务器获取注册表信息,并将其缓存在本地.客户端会使用该信息查找其他服务,从而进行远程调用.该注册列表信息定期(每30秒钟)更新一次.每次返回注册列表信息可能与 `Eureka Client` 的缓存信息不同,`Eureka Client` 自动处理.

# 如果由于某种原因导致注册列表信息不能及时匹配,`Eureka Client` 则会重新获取整个注册表信息. Eureka Server 缓存注册列表信息,整个注册表以及每个应用程序的信息进行了压缩,压缩内容和没有压缩的内容完全相同.`Eureka Client` 和 Eureka Server 可以使用 JSON/XML 格式进行通讯.在默认情况下 `Eureka Client` 使用压缩 JSON 格式来获取注册列表的信息.
```



### 1.7.`Remote Call`

```json
# 当 `Eureka Client` 从注册中心获取到服务提供者信息后,就可以通过 Http 请求调用对应的服务;服务提供者有多个时,`Eureka Client` 客户端会通过 Ribbon 自动进行负载均衡.
```



### 1.8.自我保护机制

```json
# 默认情况下,如果 Eureka Server 在一定的 90s 内没有接收到某个微服务实例的心跳,会注销该实例.但是在微服务架构下服务之间通常都是跨进程调用,网络通信往往会面临着各种问题,比如微服务状态正常,网络分区故障,导致此实例被注销.

# 固定时间内大量实例被注销,可能会严重威胁整个微服务架构的可用性.为了解决这个问题,Eureka 开发了自我保护机制,那么什么是自我保护机制呢？

# Eureka Server 在运行期间会去统计心跳失败比例在 15 分钟之内是否低于 85%,如果低于 85%,Eureka Server 即会进入自我保护机制.

# (1) Eureka 不再从注册列表中移除因为长时间没收到心跳而应该过期的服务
# (2) Eureka 仍然能够接受新服务的注册和查询请求,但是不会被同步到其它节点上(即保证当前节点依然可用)
# (3) 当网络稳定时,当前实例新的注册信息会被同步到其它节点中

# Eureka 自我保护机制是为了防止误杀服务而提供的一个机制.当个别客户端出现心跳失联时,则认为是客户端的问题,剔除掉客户端;当 Eureka 捕获到大量的心跳失败时,则认为可能是网络问题,进入自我保护机制;当客户端心跳恢复时,Eureka 会自动退出自我保护机制.

# 如果在保护期内刚好这个服务提供者非正常下线了,此时服务消费者就会拿到一个无效的服务实例,即会调用失败.对于这个问题需要服务消费者端要有一些容错机制,如重试,断路器等.
```



## 2.`Eurka`工作流程

- 1.`Eureka Server`启动成功,等待服务端注册.在启动过程中如果配置了集群,集群之间定时通过 Replicate 同步注册表,每个 `Eureka Server` 都存在独立完整的服务注册表信息.
- 2.`Eureka Client` 启动时根据配置的 `Eureka Server` 地址去注册中心注册服务.
- 3.`Eureka Client` 会每 30s 向 `Eureka Server` 发送一次心跳请求,证明客户端服务正常.
- 4.当 Eureka Server 90s 内没有收到 `Eureka Client` 的心跳,注册中心则认为该节点失效,会注销该实例.
- 5.单位时间内 `Eureka Server` 统计到有大量的 `Eureka Client` 没有上送心跳,则认为可能为网络异常,进入自我保护机制,不再剔除没有上送心跳的客户端.
- 6.当 `Eureka Client` 心跳请求恢复正常之后,`Eureka Server` 自动退出自我保护模式.
- 7.`Eureka Client` 定时全量或者增量从注册中心获取服务注册表,并且将获取到的信息缓存到本地.
- 8.服务调用时,`Eureka Client` 会先从本地缓存找寻调取的服务.如果获取不到,先从注册中心刷新注册表,再同步到本地缓存.
- 9.`Eureka Client` 获取到目标服务器信息,发起服务调用.
- 10.`Eureka Client` 程序关闭时向 `Eureka Server` 发送取消请求,`Eureka Server` 将实例从注册表中删除.