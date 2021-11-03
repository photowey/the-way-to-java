# Redis-Notes

> Redis 一个开源的基于键值对(Key-Value) `NoSQL` 数据库。
>
> 使用 `ANSI C` 语言 编写、支持网络、基于内存但支持持久化。
>
> 性能优秀,并提供多种语言的 API。



## 1.`Redis` 应用场景

- 1.缓存

  - ```json
    # Redis 提供了键值过期 时间设置,并且也提供了灵活控制最大内存和内存溢出后的淘汰策略。
    ```

- 2.排行榜系统

  - ```json
    # 按照热度排名的排行榜,按照发布 时间的排行榜,按照各种复杂维度计算出的排行榜,Redis 提供了列表和有序集 合数据结构,合理地使用这些数据结构可以很方便地构建各种排行榜系统。
    ```

- 3.计数器应用

  - ```json
    # 计数器在网站中的作用至关重要,例如视频网站有播放数、电商网站有浏览 数,为了保证数据的实时性,每一次播放和浏览都要做加 1 的操作,如果并发量 很大对于传统关系型数据的性能是一种挑战。Redis 天然支持计数功能而且计数 的性能也非常好,可以说是计数器系统的重要选择。
    ```

- 4.社交网络

  - ```json
    # 赞/踩、粉丝、共同好友/喜好、推送、下拉刷新等是社交网站的必备功能, 由于社交网站访问量通常比较大,而且传统的关系型数据不太适合保存这种类型 的数据,Redis 提供的数据结构可以相对比较容易地实现这些功能。
    ```

- 5.消息队列系统

  - ```json
    # Redis 提供了发布订阅功能和阻塞队列的功能,虽然和 专业的消息队列比还不够足够强大,但是对于一般的消息队列功能基本可以满足。
    ```



## 2.`Redis` 特性

- 1.速度快

- 2.基于键值对的数据结构服务器

- 3.丰富的功能

  - 3.1.键过期功能 -> 缓存
  - 3.2.发布订阅功能 -> 消息系统
  - 支持 Lua 脚本
  - 流水线( Pipeline)功能

- 4.简单稳定

- 5.客户端语言多

  - ```json
    # Redis 提供了简单的 TCP 通信协议,很多编程语言可以很方便地接人到 Redis。
    ```

  - 

- 6.持久化

  - ```json
    # Redis提供了两种持久化方式:RDB 和 AOF,即可以用两种策略将内存的数据保存到硬盘中,这样就保证了数据的可持久性。
    ```

  - 

- 7.主从复制

  - ```json
    # Redis 提供了复制功能,实现了多个相同数据的 Redis 副本,复制功能是分布式 Redis 的基础。
    ```

- 8.高可用和分布式

  - ```json
    # Redis Sentinel,它能够保证 Redis 节点的故障发现和故障自动转移。
    # Redis 从 3.0 版本正式提供了分布式实现 Redis Cluster,它是 Redis 真正的分布式实现,提供了高可用、读写和容量的扩展性。
    ```



## 3.`Redis` 版本和可执行文件 

> 在 Redis 的版本计划中,版本号第二位为奇数,为非稳定版本,如:2.7；
>
> 版本号第二为偶数,为稳定版本如: 3.0；
>
> 一般来说当前奇数版本 是下一个稳定版本的开发版本,如 2.9 是 3.0 的开发版本。

**可执行文件**

| 可执行文件             | 作用                           |
| ---------------------- | ------------------------------ |
| `redis-server`         | 启动 `redis`                   |
| `redis-cli`            | `redis`命令行 客户端           |
| `redis-benchmark`      | 基准测试工具                   |
| `redis-check-aof`      | `AOF` 持久化文件检测和修复工具 |
| `redis-check-rdb`      | `RDB` 持久化文件检测和修复工具 |
| `redis-check-sentinel` | 启动哨兵                       |
| `redis-trib`           | `cluster` 集群构建工具         |



## 4.操作 `Redis`

- 1.启动

  - 默认配置

    - ```shell
      $ redis-server
      ```

  - 带参数启动

    - ```shell
      $ redis-server --port 6380
      ```

  -  配置文件启动

    - ```shell
      $ ./redis-server ../conf/redis-6380.conf
      # 注意:这里对配置文件使用了相对路径,绝对路径也是可以的。
      ```

    - ```json
      # 在 Redis 中有两种修改配置的方法
      # 一种是修改配置文件
      # 另一种是使用 config set 命令动态修改。如果要 Redis 将配置持久化到本地配置文件,需要执行 config rewrite 命令。
      ```

- 2.操作

  - ```json
    # Redis 服务启动完成后,就可以使用 redis-cli 连接和操作 Redis 服务。redis-cli 可以使用两种方式连接 Redis 服务器。
    
    # 1.通过 redis-cli -h (host} -p {port}的方式连接到 Redis 服务,之后所有的操作都 是通过控制台进行。
    
    # 我们没有写-h 参数,那么默认连接 127.0.0.1;
    # 如果不写-p,那么默认 6379 端口,也就是说如果-h 和-p 都没写就是连接 127.0.0.1:6379 这个 Redis 实例。
    
    # 2.单次命令方式
    # 用redis-cli -h ip {host} -p{port} {command} 就可以直接得到 命令的返回结果。
    ```

- 3.停止

  - ```shell
    $ redis-cli -p 6380 shutdown
    
    $ redis-cli -p 6380 shutdown onsave|save
    # 缺省是 save,生成持久化文件,如果是 nosave 则不生成持久化文件
    ```



## 5.`Redis` 全局命令

- 1.查看所有键

  - 查看所有

    - ```shell
      $ keys *
      
      127.0.0.1:6379> keys *
      1) "photowey"
      2) "service.registry.id:877648677144018944"
      
      # 而 keys 命令会遍历所有键,所以它的时间复杂度是 o(n),当 Redis 保存了大量键时线上环境禁止使用 keys 命令。
      ```

  - 支持统配
    - ```shell
      $ keys ph*
      
      127.0.0.1:6379> keys ph*
      1) "photowey"
      ```

- 2.数据库-键总数

  - 键总数

    - ```shell
      $ dbsize
      
      127.0.0.1:6379> dbsize
      (integer) 2
      
      # dbsize 命令在计算键总数时不会遍历所有键,而是直接获取 Redis 内置的键 总数变量,所以 dbsize 命令的时间复杂度是 O(1)。
      ```

    - 

- 3.检查键是否存在

  - >检查键是否存在,存在返回 1,不存在返回 0
    >
    >```shell
    >$ exists <key>
    >127.0.0.1:6379> exists hello
    >(integer) 0
    >
    >127.0.0.1:6379> exists photowey
    >(integer) 1
    >```
    >
    >

- 4.删除键

  - >删除键,无论值是什么数据结构类型,del 命令都可以将其删除。
    >
    >返回删除键个数,删除不存在键返回 0。
    >
    >同时 del 命令可以支持删除多个键。
    >
    >```shell
    >$ del <key1> <key2> ... <keyn>
    >127.0.0.1:6379> del photowey
    >(integer) 1
    >
    >127.0.0.1:6379> keys ph*
    >1) "ph2"
    >2) "ph1
    >
    >$ del ph1 ph2
    >127.0.0.1:6379> del ph1 ph2
    >(integer) 2
    >
    >127.0.0.1:6379> keys ph*
    >(empty array)
    >```
    >
    >

- 5.**expire**

  - ```json
    # Redis 支持对键添加过期时间,当超过过期时间后,会自动删除键,时间单位秒。
    ```

  - ```shell
    $ keys ph*
    127.0.0.1:6379> keys ph*
    (empty array)
    
    $ set photowey redis
    127.0.0.1:6379> set photowey redis
    OK
    
    $ expire photowey 1
    127.0.0.1:6379> expire photowey 1
    (integer) 1
    
    $ exists photowey
    127.0.0.1:6379> exists photowey
    (integer) 0
    ```

  - `ttl`

    - > ttl 命令会返回键的剩余过期时间,它有 3 种返回值:
      >
      > 大于等于 0 的整数: 键剩余的过期时间。 
      >
      > -1:键没设置过期时间。 
      >
      > -2:键不存在

    - ```shell
      $ keys ph*
      127.0.0.1:6379> keys ph*
      (empty array)
      
      $ set photowey redis
      127.0.0.1:6379> set photowey redis
      OK
      
      $ ttl photowey
      127.0.0.1:6379> ttl photowey
      (integer) -1
      
      $ expire photowey 60
      127.0.0.1:6379> expire photowey 60
      (integer) 1
      
      $ ttl photowey
      127.0.0.1:6379> ttl photowey
      (integer) 42
      
      127.0.0.1:6379> ttl photowey
      (integer) 0
      
      127.0.0.1:6379> ttl photowey
      (integer) -2
      $ 
      ```

    - > Redis 还提供了 expireat、pexpire,pexpireat、pttl、 
      >
      > persist 等一系列命令
      >
      > 
      >
      > expireat key timestamp:  键在秒级时间截 timestamp 后过期。
      >
      > ttl 命令和 pttl 都可以查询键的剩余过期时间,但是 pttl 精度更高可以达到毫 秒级别,有 3 种返回值: 
      >
      > 大于等于 0 的整数: 键剩余的过期时间(ttl 是秒,pttl 是毫秒)。 
      >
      > -1:键没有设置过期时间。 
      >
      > -2:键不存在。 
      >
      > 
      >
      > pexpire key milliseconds: 键在 milliseconds 毫秒后过期。
      >
      > pexpireat key milliseconds-timestamp: 键在毫秒级时间戳 timestamp 后过期。 
      >
      > 
      >
      > 在使用 Redis 相关过期命令时,需要注意以下几点。 
      >
      > 1) 如果 expire key 的键不存在,返回结果为 0;
      >
      > 2) 如果过期时间为负值,键会立即被删除,犹如使用 del 命令一样;
      >
      > 3) persist 命令可以将键的过期时间清除;
      >
      > 4) 对于字符串类型键,执行 set 命令会去掉过期时间,这个问题很容易在开发中被忽视;
      >
      > 5) Redis 不支持二级数据结构(例如哈希、列表)内部元素的过期功能。
      >
      > 例如: 不能对列表类型的一个元素做过期时间设置。

- 6.**type**

  - > 返回键的数据结构类型。
    >
    > 例如键 hello 是字符串类型,返回结果为 string；
    >
    > 键 mylist 是列表类型,返回结果为 list；
    >
    > 键不存在返回 none 。

  - ```shell
    $ set photowey ph1
    127.0.0.1:6379> set photowey ph1
    OK
    
    $ 127.0.0.1:6379> type photowey
    string
    
    # $ lpush mylist
    $ lpush mylist 1 2 3 4 5
    127.0.0.1:6379> lpush mylist 1 2 3 4 5
    (integer) 5
    
    # $ lrange mylist
    $ lrange mylist 0 -1
    127.0.0.1:6379> lrange mylist 0 -1
    1) "5"
    2) "4"
    3) "3"
    4) "2"
    5) "1"
    
    $ type mylist
    127.0.0.1:6379> type mylist
    list
    
    $ type not-exists-in-redis
    127.0.0.1:6379> type not-exists-in-redis
    none
    ```

  - 

- 7.**randomkey**

  - ```shell
    $ randomkey
    127.0.0.1:6379> randomkey
    "mylist"
    127.0.0.1:6379> randomkey
    "mylist"
    127.0.0.1:6379> randomkey
    "mylist"
    127.0.0.1:6379> randomkey
    "service.registry.id:877648677144018944"
    127.0.0.1:6379> randomkey
    "photowey"
    127.0.0.1:6379> randomkey
    "photowey"
    127.0.0.1:6379> randomkey
    "service.registry.id:877648677144018944"
    127.0.0.1:6379> randomkey
    "mylist"
    127.0.0.1:6379> randomkey
    "service.registry.id:877648677144018944"
    127.0.0.1:6379> randomkey
    "mylist"
    ```

  - 

- 8.**rename**

  - > 键重命名

  - ```shell
    $ set oldkey old
    127.0.0.1:6379> set oldkey old
    OK
    127.0.0.1:6379> get oldkey
    "old"
    
    $ rename oldkey newkey
    127.0.0.1:6379> rename oldkey newkey
    OK
    127.0.0.1:6379> get oldkey
    (nil)
    127.0.0.1:6379> get newkey
    "old"
    
    # 注意,如果在 rename 之前,新键已经存在,那么它的值也将被覆盖。
    # 为了防止被强行 rename,Redis 提供了 renamenx 命令,确保只有 newKey 不存在时候才被覆盖。
    
    # 由于重命名键期间会执行 del 命令删除旧的键,如果 键对应的值比较大,会存在阻塞 Redis 的可能性。
    ```

  - ```shell
    # 键名的生产实践
    # 推荐的方式是使用"业务名:对象:id:[属性]"作为键名(也可以不是冒号)。
    ```



## 6.`Redis` 常用数据结构

> Redis 提供了一些数据结构供我们往 Redis 中存取数据,最常用的的有 5 种。

### 1.字符串(String)

- 1.字符串(String)

  - > 字符串类型是 Redis 最基础的数据结构。
    >
    > > 值最大不能超过 **`512MB`**

  - ```shell
    $ set key value [ex seconds] [px milliseconds] [nx | xx]
    
    $ set hello redis
    # 设置键为 hello,值为 redis 的键值对,返回结果为 OK 代表设置成功。 
    # set 命令有几个选项: 
    # ex seconds:为键设置秒级过期时间。 
    # px milliseconds:为键设置毫秒级过期时间。 
    # nx:键必须不存在,才可以设置成功,用于添加。 
    # xx:与 nx 相反,键必须存在,才可以设置成功,用于更新。
    
    # ex 参数和 expire 命令基本一样。
    # 还有一个需要特别注意的地方是如果一个字符串已经设置了过期时间,然后你调用了 set 方法修改了它,它的过期时间会消失。
    
    # Redis 还提供了 setex 和 setnx 两个命令: 
    # $ setex key seconds value 
    # $ setnx key value
    
    # setex 和 setnx 的作用和 ex 和 nx 选项是一样的。
    # 也就是:
    # setex 为键设置秒级过期时间,
    # setnx 设置时键必须不存在,才可以设置成功。
    ```

  - ```shell
    $ get
    # 如果要获取的键不存在,则返回 nil(空)
    
    127.0.0.1:6379> keys *
    1) "service.registry.id:877648677144018944"
    2) "mylist"
    3) "photowey"
    4) "newkey"
    127.0.0.1:6379> get photowey
    "ph1"
    127.0.0.1:6379> get not-exist-in-redis
    (nil)
    ```

  - ```shell
    $ mset 批量设置值
    $ mset a 1 b 2 c 3 d 4
    127.0.0.1:6379> mset a 1 b 2 c 3 d 4
    OK
    ```

  - ```shell
    $ mget 批量获取值
    127.0.0.1:6379> mget a b c d
    1) "1"
    2) "2"
    3) "3"
    4) "4"
    ```

  - ```shell
    $ incr
    # 数字运算
    # incr 命令用于对值做自增操作,返回结果分为三种情况: 
    # 值不是整数,返回错误。 
    # 值是整数,返回自增后的结果。 
    # 键不存在,按照值为0 自增,返回结果为 1。
    
    $ exists incrkey
    127.0.0.1:6379> exists incrkey
    (integer) 0
    127.0.0.1:6379> incr incrkey
    (integer) 1
    127.0.0.1:6379> incr incrkey
    (integer) 2
    127.0.0.1:6379> incr incrkey
    (integer) 3
    
    # 除了 incr 命令,Redis 提供了 decr(自减)、 incrby(自增指定数字)、decrby(自减指定数字)、incrbyfloat(自增浮点数)。
    ```

  - ```shell
    $ append
    # append 可以向字符串尾部追加值
    
    127.0.0.1:6379> set hello study
    OK
    127.0.0.1:6379> get hello
    "study"
    127.0.0.1:6379> append hello redis
    (integer) 10
    127.0.0.1:6379> get hello
    "studyredis"
    ```

  - ```shell
    $ strlen
    # strlen 字符串长度
    
    127.0.0.1:6379> strlen hello
    (integer) 10
    ```

  - ```shell
    $ getset
    # getset 和 set 一样会设置值,但是不同的是,它同时会返回键原来的值
    
    127.0.0.1:6379> getset getset getsetvalue
    (nil)
    127.0.0.1:6379> getset getset1 getset1
    (nil)
    127.0.0.1:6379> getset getset getsetnewvalue
    "getsetvalue"
    127.0.0.1:6379> getset getset1 getset1newvalue
    "getset1"
    ```

  - ```shell
    $ setrange
    # setrange 设置指定位置的字符
    # 下标从 0 开始计算。
    
    $ get photowey
    127.0.0.1:6379> get photowey
    "ph1"
    127.0.0.1:6379> setrange photowey 2 setrangevalue
    (integer) 15
    127.0.0.1:6379> get photowey
    "phsetrangevalue"
    ```

  - ```shell
    $ getrange
    # getrange 截取字符串
    
    127.0.0.1:6379> get photowey
    "phsetrangevalue"
    127.0.0.1:6379> get photowey
    "phsetrangevalue"
    127.0.0.1:6379> getrange photowey 2 5
    "setr"
    127.0.0.1:6379> getrange photowey 2 4
    "set"
    ```
  
  - ```json
    # 字符串这些命令中,除了 del 、mset、 mget 支持多个键的批量操作,时间复杂度和键的个数相关,为 O(n),getrange 和字符串长度相关,也是 O(n),其余的命令基本上都是 O(1)的时间复杂度,在速度上还是非常快的。
    ```
  
### 2.哈希(Hash)

- 哈希(Hash)

  - >哈希类型中的映射关系叫作 field-value,注意这里的 value 是指 field 
    >
    >对应的值,不是键对应的值。

    >key-{field-value}

  - ```shell
  $ hset <{field-value}>
    # 如果设置成功会返回 1,反之会返回 0。
    # 此外 Redis 提供了 hsetnx 命令,它 们的关系就像 set 和 setnx 命令一样,只不过作用域由键变为 field。
    
    $ hset user:9527 name photowey
    
    127.0.0.1:6379> hset user:9527 name photowey
    (integer) 1
    127.0.0.1:6379> hget user:9527
    (error) ERR wrong number of arguments for 'hget' command
    127.0.0.1:6379> hget user:9527 name
    "photowey"
    ```
  
  - ```shell
  $ hget <key> <field>
    # 如果键或 field 不存在,会返回 nil。
    
    127.0.0.1:6379> hget user:9527 name
    "photowey"
    ```
  
  - ```shell
    $ hdel
    # 删除 field
    # hdel 会删除一个或多个 field,返回结果为成功删除 field 的个数
    
    127.0.0.1:6379> hdel user:9527 name 
    (integer) 1
    ```
  
  - ```shell
    $ hlen
    # hlen 计算 field 个数
    
    127.0.0.1:6379> hlen user:9527
    (integer) 2
    ```
  
  - ```shell
    $ hmset
    # 批量设值
    
    127.0.0.1:6379> hmset user:9527 contry zhCN province CQ
    OK
    ```
  
  - ```shell
    $ hmget
    # 批量取值
    
    127.0.0.1:6379> hmget user:9527 address contry  province
    1) "CQ"
    2) "zhCN"
    3) "CQ
    ```
  
  - ```shell
    $ hexists
    # hexists 判断 field 是否存在
    
    127.0.0.1:6379> hexists user:9527 province
    (integer) 1
    ```
  
  - ```shell
    $ hkeys
    # hkeys 获取所有 field
    
    127.0.0.1:6379> hkeys user:9527
    1) "age"
    2) "address"
    3) "contry"
    4) "province"
    ```
  
  - ```shell
    $ hvals 
    # hvals 获取所有 value
    
    127.0.0.1:6379> hvals user:9527
    1) "18"
    2) "CQ"
    3) "zhCN"
    4) "CQ"
    ```
  
  - ```shell
    $ hincrby
    # 增加
    
    127.0.0.1:6379> hincrby user:9527 age 10
    (integer) 28
    ```
    
  - ```shell
    $ hstrlen
    # 计算 value 的字符串长度
    
    127.0.0.1:6379> hstrlen user:9527 province
    (integer) 2
    127.0.0.1:6379> hstrlen user:9527 contry
  (integer) 4
    ```
    
  - ```shell
    $ hgetall
    # hgetall 获取所有 field 与 value
    # 在使用 hgetall 时,如果哈希元素个数比较多,会存在阻塞 Redis 的可能。如 果只需要获取部分 field,可以使用 hmget,如果一定要获取全部 field-value,可 以使用 hscan 命令,该命令会渐进式遍历哈希类型
    
    127.0.0.1:6379> hgetall user:9527
    1) "age"
    2) "18"
    3) "address"
  4) "CQ"
    5) "contry"
    6) "zhCN"
    7) "province"
    8) "CQ"
    ```
    
  - ```json
    # 哈希类型的操作命令中:
    # hdel,hmget,hmset 的时间复杂度和命令所带的 field 的个数相关 O(k)。
    # hkeys,hgetall,hvals 和存储的 field 的总数相关,O(N)。
    # 其余的命 令时间复杂度都是 O(1)。
    ```

### 3.列表(List)
- 3.列表(List)

  - ```json
    # 列表( list)类型是用来存储多个有序的字符串,元素从左到右组成了一个有序的列表,列表中的每个字符串称为元素(element)。
    # 在 Redis 中,可以对列表两端插入( push)和弹出(pop), 还可以获取指定范围的元素列表、获取指定索引下标的元素等。
    # 列表是一种比较灵活的数据结构,它可以充当栈和队列的角色,在实际开发上有很多应用场景。
    
    # 两个特点:
    # 第一、列表中的元素是有序的,可以通过索引下标获取某个元素或者某个范围内的元素列表。
    # 第二、列表中的元素可以是 重复的
    ```

  - ```shell
    $ lrange key start end
    
    # lrange 获取指定范围内的元素列表
    # 索引下标特点: 从左到右为 0 到 N-1
    # lrange key 0 -1 命令可以从左到右获取列表的所有元素
    
    127.0.0.1:6379> lrange mylist 0 -1
    1) "5"
    2) "4"
    3) "3"
    4) "2"
    5) "1"
    
    127.0.0.1:6379> lrange mylist 0 2
    1) "5"
    2) "4"
    3) "3"
    ```

  - ```shell
    $ rpush key 
    # 从右向左插入
    # 同时 rpush 支持同时插入多个元素
    
    127.0.0.1:6379> rpush mylist 0
    (integer) 6
    127.0.0.1:6379> lrange mylist 0 -1
    1) "5"
    2) "4"
    3) "3"
    4) "2"
    5) "1"
    6) "0"
    ```

  - ```shell
    $ lpush key
    # 从左向右插入
    # lpush 支持同时插入多个元素
    # 同↑
    ```

  - ```shell
    $ linsert <key> <BEFORE|AFTER> <povit> <element>
    # 在某个元素前或后插入新元素
    
    127.0.0.1:6379> linsert mylist BEFORE 4 11
    (integer) 7
    127.0.0.1:6379> linsert mylist AFTER 3 12
    (integer) 8
    127.0.0.1:6379> lrange mylist 0 -1
    1) "5"
    2) "11"
    3) "4"
    4) "3"
    5) "12"
    6) "2"
    7) "1"
    8) "0"
    ```

  - ```shell
    $ lpop
    # 从列表左侧弹出
    
    127.0.0.1:6379> lpop mylist
    "5"
    127.0.0.1:6379> rpop mylist
    "0"
    127.0.0.1:6379> lrange mylist 0 -1
    1) "11"
    2) "4"
    3) "3"
    4) "12"
    5) "2"
    6) "1"
    ```

  - ```shell
    $ rpop
    # 从列表右侧弹出
    # 同↑
    ```

  - ```shell
    $ lrem <key> <count> <element>
    # 对指定元素进行删除
    # lrem 命令会从列表中找到等于 value 的元素进行删除,根据 count 的不同分 为三种情况: 
    # count > 0,从左到右,删除最多 count 个元素。 
    # count < 0,从右到左,删除最多 count 绝对值个元素。 
    # count = 0,删除所有。
    
    127.0.0.1:6379> lrem mylist 1 11
    (integer) 1
    127.0.0.1:6379> lrange mylist 0 -1
    1) "4"
    2) "3"
    3) "12"
    4) "2"
    5) "1"
    ```

  - ```shell
    $ ltirm
    # 按照索引范围修剪列表
    
    127.0.0.1:6379> ltrim mylist 1 3
    OK
    127.0.0.1:6379> lrange mylist 0 -1
    1) "3"
    2) "12"
    3) "2"
    ```

  - ```shell
    $ lset <key> <index> <new-value>
    # 修改指定索引下标的元素
    
    127.0.0.1:6379> lset mylist 1 9527
    OK
    127.0.0.1:6379> lrange mylist 0 -1
    1) "3"
    2) "9527"
    3) "2"
    ```

  - ```shell
    $ lindex
    # 获取列表指定索引下标的元素
    
    127.0.0.1:6379> lindex mylist 1
    "9527"
    ```

  - ```shell
    $ llen
    # 获取列表长度
    
    127.0.0.1:6379> llen mylist
    (integer) 3
    ```

  - ```shell
    $ blpop
    $ brpop
    # 阻塞式弹出元素
    127.0.0.1:6379> blpop mylist 3
    1) "mylist"
    2) "3"
    127.0.0.1:6379> blpop mylist 3
    1) "mylist"
    2) "9527"
    127.0.0.1:6379> blpop mylist 3
    1) "mylist"
    2) "2"
    127.0.0.1:6379> blpop mylist 3
    (nil)
    (3.08s)
    
    -- A Client
    127.0.0.1:6379> blpop mylist 0
    1) "mylist"
    2) "8848"
    (3.24s)
    
    -- B Cient
    127.0.0.1:6379> lpush mylist 8848
    (integer) 1
    
    ## 如果多个客户端对同一个键执行 brpop,那么最先执行 brpop 命令的客户端可以获取到弹出的值,其余的客户端依然处于阻塞。
    ```

  - ```json
    # 列表类型的操作命令中,llen,lpop,rpop,blpop 和 brpop 命令时间复杂度都是 O(1).
    # 其余的命令的时间复杂度都是 O(n),只不过 n 的值根据命令不同而不同,比如:
    # lset 和 lindex 时间复杂度和命令后的索引值大小相关。
    # rpush 和 lpush 和插入元素的个数相关等。
    ```

  - ```json
    # 使用场景
    # 1.消息队列
    # Redis 的 lpush + brpop 命令组合即可实现阻塞队列,生产者客户 端使用 lrpush 从列表左侧插入元素,多个消费者客户端使用 brpop 命令阻塞式的 “抢”列表尾部的元素,多个客户端保证了消费的负载均衡和高可用性。
    
    # 文章列表
    # 每个用户有属于自己的文章列表,现需要分页展示文章列表。此时可以考虑 使用列表,因为列表不但是有序的,同时支持按照索引范围获取元素
    
    # 实现其他数据结构
    # 栈 - lpush + lpop = Stack
    # 队列 - lpush + rpop = Queue
    # 有限集合 - lpush + ltrim = Capped Collection
    # 消息队列 - lpush + brpop = Message Queue
    ```
### 4.集合(Set)
- 4.集合(Set)

  - ```json
    # 集合(set) 类型也是用来保存多个的字符串元素,但和列表类型不一样的是, 集合中不允许有重复元素,并且集合中的元素是无序的,不能通过索引下标获取元 素。
    
    # 一个集合最多可以存储 2^32 -1 个元素。
    # Redis 除了支持集合内的增删改查,同时还支持多个集合取交集、并集、差集,合理地使用好集合类型,能在实际开发中解决很多实际问题。
    ```

  - ```shell
    $ sadd 
    # 添加元素
    # 允许添加多个,返回结果为添加成功的元素个数
    
    127.0.0.1:6379> sadd myset 1 2 3 4 5 5 3 4 
    (integer) 5
    127.0.0.1:6379> smembers myset
    1) "1"
    2) "2"
    3) "3"
    4) "4"
    5) "5"
    ```

  - ```shell
    $ srem 
    # 删除元素
    # 允许删除多个,返回结果为成功删除元素个数
    
    127.0.0.1:6379> srem myset 2 5
    (integer) 2
    127.0.0.1:6379> smembers myset
    1) "1"
    2) "3"
    3) "4"
    ```

  - ```shell
    $ scard 
    # 计算元素个数
    
    127.0.0.1:6379> scard myset
    (integer) 3
    ```

  - ```shell
    $ sismember
    # 判断元素是否在集合中
    # 如果给定元素 element 在集合内返回 1,反之返回 0
    
    127.0.0.1:6379> sismember myset 5
    (integer) 0
    127.0.0.1:6379> sismember myset 1
    (integer) 1
    ```

  - ```shell
    $ srandmember 
    # 随机从集合返回指定个数元素
    # 指定个数如果不写默认为 1
    
    127.0.0.1:6379> srandmember myset 2
    1) "4"
    2) "3"
    127.0.0.1:6379> srandmember myset 2
    1) "1"
    2) "3"
    ```

  - ```shell
    $ spop
    # 从集合随机弹出元素
    
    # 同样可以指定个数,如果不写默认为:1。
    # 注意,既然是弹出,spop 命令执行后,元素会从集合中删除,而srandmember 不会
    
    127.0.0.1:6379> spop myset 2
    1) "1"
    2) "4"
    127.0.0.1:6379> smembers myset
    1) "3"
    ```

  - ```shell
    $ smembers
    # 获取所有元素 - 返回结果是无序的
    
    127.0.0.1:6379> smembers myset
    1) "3"
    ```

  - ```shell
    $ sinter
    # 求多个集合的交集
    
    127.0.0.1:6379> sadd myset 1 2 4 5
    (integer) 4
    127.0.0.1:6379> smembers myset
    1) "1"
    2) "2"
    3) "3"
    4) "4"
    5) "5"
    127.0.0.1:6379> sadd myset2 1 3 5
    (integer) 3
    127.0.0.1:6379> smembers myset2
    1) "1"
    2) "3"
    3) "5"
    127.0.0.1:6379> sinter myset myset2
    1) "1"
    2) "3"
    3) "5"
    ```

  - ```shell
    $ suinon
    # 求多个集合的并集
    
    127.0.0.1:6379> sadd myset2 6 7 8
    (integer) 3 
    127.0.0.1:6379> smembers myset2
    1) "1"
    2) "3"
    3) "5"
    4) "6"
    5) "7"
    6) "8"
    127.0.0.1:6379> sunion myset myset2
    1) "1"
    2) "2"
    3) "3"
    4) "4"
    5) "5"
    6) "6"
    7) "7"
    8) "8"
    ```

  - ```shell
    $ sdiff
    # 求多个集合的差集
    
    127.0.0.1:6379> sdiff myset myset2
    1) "2"
    2) "4"
    127.0.0.1:6379> smembers myset
    1) "1"
    2) "2"
    3) "3"
    4) "4"
    5) "5"
    127.0.0.1:6379> smembers myset2
    1) "1"
    2) "3"
    3) "5"
    4) "6"
    5) "7"
    6) "8"
    ```

  - ```shell
    # 交集、并集、差集的结果保存
    $ sinterstore destination key [key ...]
    $ sinterstore mysetinter myset myset2
    127.0.0.1:6379> sinterstore mysetinter myset myset2
    (integer) 3
    127.0.0.1:6379> smembers mysetinter
    1) "1"
    2) "3"
    3) "5"
    
    $ sunionstore destination key [key ...]
    $ sunionstore mysetunion myset myset2
    127.0.0.1:6379> sunionstore mysetunion myset myset2
    (integer) 8
    127.0.0.1:6379> smembers mysetunion
    1) "1"
    2) "2"
    3) "3"
    4) "4"
    5) "5"
    6) "6"
    7) "7"
    8) "8"
    
    $ sdiffstore destination key [key ...]
    $ sdiffstore mysetdiff myset myset2
    127.0.0.1:6379> sdiffstore mysetdiff myset myset2
    (integer) 2
    127.0.0.1:6379> smembers mysetdiff
    1) "2"
    2) "4"
    
    # 集合间的运算在元素较多的情况下会比较耗时,所以 Redis 提供了上面三个命令(原命令+store)将集合间交集、并集、差集的结果保存在 destination key 中。
    ```

  - ```json
    # 命令的时间复杂度
    # scard,sismember 时间复杂度为 O(1),
    # 其余的命令时间复杂度为 O(n),
    # 其中 sadd,srem 和命令后所带的元素个数相关。
    # spop,srandmember 和命令后所带 count 值相关。
    # 交集运算 O(m*k),k 是多个集合中元素最少的个数,m 是键个数, 并集、差集和所有集合的元素个数和相关。
    ```

  - ```json
    # 使用场景
    # 集合类型比较典型的使用场景是标签( tag)。例如一个用户可能对娱乐、体 育比较感兴趣,另一个用户可能对历史、新闻比较感兴趣,这些兴趣点就是标签。 有了这些数据就可以得到喜欢同一个标签的人,以及用户的共同喜好的标签,这 些数据对于用户体验以及增强用户黏度比较重要。
    ```

  - 

### 5.有序集合(ZSet)

- 5.有序集合(ZSet)

  - ```json
    # 有序集合相对于哈希、列表、集合来说会有一点点陌生,但既然叫有序集合, 那么它和集合必然有着联系,它保留了集合不能有重复成员的特性,但不同的是, 有序集合中的元素可以排序。
    # 但是它和列表使用索引下标作为排序依据不同的是, 它给每个元素设置一个分数( score)作为排序的依据。
    ```

  - ```shell
    $ zadd <key> [NX|XX] [CH] [INCR] <score> <member>
    # 添加成员
    # 返回结果代表成功添加成员的个数
    
    127.0.0.1:6379> zadd myzset NX CH 10 ph1
    (integer) 1
    
    # zadd 命令还有四个选项 nx、xx、ch、incr 四个选项 
    # nx; member 必须不存在,才可以设置成功,用于添加。 
    # xx: member 必须存在,才可以设置成功,用于更新。
    # ch:返回此次操作后,有序集合元素和分数发生变化的个数 
    # incr:对 score 做增加,相当于后面介绍的 zincrby
    ```

  - ```shell
    $ zcard 
    # 计算成员个数
    
    127.0.0.1:6379> zcard myzset
    (integer) 1
    ```

  - ```shell
    $ zscore
    # 计算某个成员的分数
    # 如果成员不存在则返回 nil
    
    127.0.0.1:6379> zscore myzset ph1
      "10"
    127.0.0.1:6379> zscore myzset ph2
      (nil)
    ```

  - ```shell
    $ zrank
    # zrank 是从分数从低到高返回排名
    # $ zrevrank 反之
    # 排名从 0 开始计算
    
    127.0.0.1:6379> zadd myzset NX CH 20 ph2
    (integer) 1
    127.0.0.1:6379> zadd myzset NX CH 5 ph3
    (integer) 1
    127.0.0.1:6379> zrank myzset ph1
    (integer) 1
    127.0.0.1:6379> zrank myzset ph3
    (integer) 0
    127.0.0.1:6379> zrank myzset ph2
    (integer) 2
    
    # -- -
    
    127.0.0.1:6379> zrevrank myzset ph2
    (integer) 0
    127.0.0.1:6379> zrevrank myzset ph1
    (integer) 1
    ```

  - ```shell
    $ zrem <key> <member>
    # 删除成员
    # 一次允许删除多个成员
    
    127.0.0.1:6379> zrem myzset ph3
    (integer) 1
    ```
  
- ```shell
    $ zincrby <key> <increment> <member>
    # 增加成员的分数
    
    127.0.0.1:6379> zincrby myzset 10 ph3
    "15"
    127.0.0.1:6379> zrank myzset ph3
    (integer) 1
    ```
  
- ```shell
    $ zrange <key> <start> <stop> [WITHSCORES]
    $ zrevrange 
    
    127.0.0.1:6379> zrange myzset 0 1 WITHSCORES
    1) "ph1"
    2) "10"
    3) "ph3"
    4) "15"
    
    127.0.0.1:6379> zrevrange myzset 0 1 WITHSCORES
    1) "ph2"
    2) "20"
    3) "ph3"
    4) "15"
    
    # 返回指定排名范围的成员
    # 有序集合是按照分值排名的,zrange 是从低到高返回,zrevrange 反之。如果 加上 withscores 选项,同时会返回成员的分数
    ```
  
- ```shell
    $ zrangebyscore
    # 返回指定分数范围的成员
    $ zrangebyscore key min max [WITHSCORES] [LIMIT offset count]
    $ zrevrangebyscore key max min [WITHSCORES][LIMIT offset count]
    
    127.0.0.1:6379> zrangebyscore myzset (0 +inf WITHSCORES
    1) "ph1"
    2) "10"
    3) "ph3"
    4) "15"
    5) "ph2"
    6) "20"
    127.0.0.1:6379> zrangebyscore myzset (0 +inf WITHSCORES LIMIT 0 1
    1) "ph1"
    2) "10"
    127.0.0.1:6379> zrangebyscore myzset (0 +inf WITHSCORES LIMIT 0 2
    1) "ph1"
    2) "10"
    3) "ph3"
    4) "15"
    
    127.0.0.1:6379> zrevrangebyscore myzset +inf 0 WITHSCORES LIMIT 0 1
    1) "ph2"
    2) "20"
    
    # 其中 zrangebyscore 按照分数从低到高返回,zrevrangebyscore 反之。
    # 同时 min 和 max 还支持开区间(小括号）和闭区间(中括号),-inf 和+inf 分别 代表无限小和无限大
    ```
  
- ```shell
    $ zcount key min max
    # 返回指定分数范围成员个数
    
    127.0.0.1:6379> zcount myzset 0 25
    (integer) 3
    ```
  
- ```shell
    $ zremrangebyrank key start end 
    $ zremrangebyrank myzset 0 1
    127.0.0.1:6379> zremrangebyrank myzset 0 1
    (integer) 2
    
    # 按升序删除指定排名内的元素
    ```
  
- ```shell
    $ zremrangebyscore key min max
    $ zremrangebyscore myzset 0 1
    127.0.0.1:6379> zremrangebyscore myzset 0 1
    (integer) 0
    127.0.0.1:6379> zremrangebyscore myzset 0 25
    (integer) 1
    127.0.0.1:6379> zcard myzset
    (integer) 0
    
    # 删除指定分数范围的成员
    ```
  
- ```shell
    # 集合间操作命令
    
    $ zinterstore
    $ zinterstore destination numkeys key [key ...] [weights weight [weight ...]] [aggregate sum | min | max]
    
    $ zinterstore myzsetinter 2 myzset myzset2
    127.0.0.1:6379> zinterstore myzsetinter 2 myzset myzset2
    (integer) 5
    127.0.0.1:6379> zrange myzsetinter 0 4 WITHSCORES
     1) "ph3"
     2) "20"
      3) "ph2"
     4) "25"
     5) "ph1"
     6) "30"
     7) "ph5"
     8) "32"
     9) "ph4"
    10) "40"
    
    # 交集
    # 这个命令参数较多,下面分别进行说明:
    # destination:交集计算结果保存到这个键。 
    # numkeys:需要做交集计算键的个数。 
    # key [key ...]:需要做交集计算的键。
    # weights weight [weight ...]:每个键的权重,在做交集计算时,每个键中的每个 member 会将自己分数乘以这个权重,每个键的权重默认是 1。 
    # aggregate sum|min|max:计算成员交集后,分值可以按照 sum(和)、min(最小值)、max(最大值)做汇总,默认值是 sum。
    ```
  
- ```shell
    $ zunionstore
    $ zunionstore destination numkeys key [key ...] [weights weight [weight ...]] [ aggregate sum|min|max]
    
    $ zunionstore myzsetunion 2 myzset myzset2
    127.0.0.1:6379> zunionstore myzsetunion 2 myzset myzset2
    (integer) 5
    127.0.0.1:6379> zrange myzsetunion 0 4 WITHSCORES
    1) "ph3"
    2) "20"
    3) "ph2"
    4) "25"
    5) "ph1"
    6) "30"
    7) "ph5"
    8) "32"
    9) "ph4"
    10) "40"
    ```
  
- ```json
    # 命令的时间复杂度
    # zadd key score member [score member ...] O(k*log(n)),k 是添加成员的个 数,n 是当前有序集合成员个数。
    
    # zcard key O(1)
    # zscore key member O(1) 
    
    # zrank key member、zrevrank key member O(log(n)),n 是当前有序集合成员个数。
    
    # zrem key member [member ...] O(k*1og(n)),k 是删除成员的个数,n 是当前 有序集合成员个数。
    
    # zincrby key increment member O(log(n)),n 是当前有序集合成员个数。
    
    # zrange key start end[ WITHSCORES]和 
    # zrevrange key start end [WITHSCORES] O(log(n)+k),k 是要获取的成员个数,n 是当前有序集合成员个数。
    
    # zrangebyscore key min max [ WITHSCORES]和 
    # zrevrangebyscore key max min [WITHSCORES] O(log(n)+k),k 是要获取的成员个数,n 是当前有序集合成员个数。
    
    # zcount O(log(n)),n 是当前有序集合成员个数。
    
    # zremrangebyrank key start end 和 zremrangebyscore key min max O(log(n)+k), k 是要删除的成员个数,n 是当前有序集合成员个数 。
    
    # zinterstore destination numkeys key [key ...] O(n*K)+O(m*log(m)),n是成员数最小的有序集合成员个数,k 是有序集合的个数,m 是结果集中成员个数 
    
    # zunionstore destination numkeys key [key ...] O(n)+O(m*log(m)),n 是所有有序集合成员个数和,m 是结果集中成员个数。
    ```



### 6.`Bitmaps`

> Bitmaps 本身不是一种数据结构，实际上它就是字符串，但是它可以对字符 
>
> 串的位进行操作

#### 1.命令

- `setbit`

  - ```shell
    $ setbit key offset value
    # key-prefix - u:v:
    # u:v:1024
    # user 9527
    # 浏览一次
    
    127.0.0.1:6379> setbit u:v:1024 9527 1
    (integer) 0
    127.0.0.1:6379> setbit u:v:1024 9528 1
    (integer) 0
    127.0.0.1:6379> setbit u:v:1024 9529 1
    (integer) 0
    127.0.0.1:6379> setbit u:v:1024 8848 1
    (integer) 0
    ```

- `getbit`

  - ```shell
    $ getbit key offset
    
    $ getbit u:v:1024 9527
    
    127.0.0.1:6379> getbit u:v:1024 9527
    (integer) 1
    127.0.0.1:6379> getbit u:v:1024 9526
    (integer) 0 # 9526 不存在
    127.0.0.1:6379> getbit u:v:1024 8848
    (integer) 1
    
    # offset 是不存在的，也会返回 0
    ```

- `bitcount`

  - ```shell
    $ bitcount [start] [end]
    # [start]和[end]代表起始和结束字节数
    
    $ bitcount u:v:1024
    127.0.0.1:6379> bitcount u:v:1024
    (integer) 4
    ```

- `bitop`

  - ```shell
    $ bitop op destkey key [key . ...]
    # Bitmaps 间的运算
    # bitop 是一个复合操作，它可以做多个 Bitmaps 的 and(交集)or(并集)not(非)xor(异或)操作并将结果保存在 destkey 中。
    ```

- `bitpos`

  - ```shell
    $ bitpos key targetBit [start] [end]
    # bitops 有两个选项[start]和[end]，分别代表起始字节和结束字节
    
    127.0.0.1:6379> bitpos u:v:1024 1
    (integer) 8848
    127.0.0.1:6379> bitpos u:v:1024 0
    (integer) 0
    
    ```

  - 

- 



### 7.`HyperLogLog`

> HyperLogLog 提供不精确的去重计数方案，虽然不精 
>
> 确但是也不是非常不精确，Redis 官方给出标准误差是 0.81%.
>
> 
>
> 基于概率论中伯努利试验并结合了极大似然估算方法，并做了 
>
> 分桶优化
>
> 
>
> Redis 的实现中，HyperLogLog 占据 12KB 的大小，共设有 16384 个桶，即： 
>
> 2^14 = 16384，每个桶有 6 位，每个桶可以表达的最大数字是：25+24+...+1 = 63 ， 
>
> 二进制为： 111 111.
>
> 
>
> 对于命令：pfadd key value 
>
> ​	在存入时，value 
>
> ​	会被 hash 成 64 位，即 64 bit 的比特字符串，前 14 位用来分桶，剩下 50 位用来记录第一个 1 出现的位置。之所以选 14 位 来表达桶编号是因为，分了 16384 个桶，而 2^14 = 16384，刚好地，最大的时候可以把桶利用完，不造成浪费。假设一个字符串的前 14 位 是：00 0000 0000 0010 (从右往左看) ，其十进制值为 2。那么 value 对应转化后的值放到编号为 2 的桶。 
>
> ​	index 的转化规则： 
>
> 首先因为完整的 value 比特字符串是 64 位形式，减去 14 后，剩下 50 位， 
>
> 假设极端情况，出现 1 的位置，是在第 50 位，即位置是 50。此时 index = 50。 
>
> 此时先将 index 转为 2 进制，它是：110010 。因为 16384 个桶中，每个桶是 6 bit 组成的。于是 110010 就被设置到了 第 2 号桶中去了。请注意，50 已经是最坏的情况，且它都被容纳进去了。那么 其他的不用想也肯定能被容纳进去。
>
> 
>
> 64 位转为十进制就是：2^64，HyperLogLog 仅用了：16384 * 6 /8 / 1024 =12K 存储空间就能统计多达 2^64 个数



#### 1.命令

- `pfadd`

  - ```shell
    $ pfadd key element [element …]
    # pfadd 用于向 HyperLogLog 添加元素,如果添加成功返回 1
    
    $ pfadd u:id:1024 "u1" "u2" "u3" "u4"
    127.0.0.1:6379> pfadd u:id:1024 "u1" "u2" "u3" "u4"
    (integer) 1
    
    
    $ pfadd u:id:1025 "u3" "u4" "u5" "u6"
    127.0.0.1:6379> pfadd u:id:1025 "u3" "u4" "u5" "u6"
    (integer) 1
    127.0.0.1:6379> pfcount u:id:1025
    (integer) 4
    ```

  - 

- `pfcount`

  - ```shell
    $ pfcount key [key …]
    
    $ pfcount u:id:1024
    127.0.0.1:6379> pfcount u:id:1024
    (integer) 4
    ```

  - 

- `pfmerge`

  - ```shell
    $ pfmerge destkey sourcekey [sourcekey ... ]
    # pfmerge 可以求出多个 HyperLogLog 的并集并赋值给 destkey
    
    $ pfmerge u:id:merge:1025 u:id:1024 u:id:1025
    127.0.0.1:6379> pfmerge u:id:merge:1025 u:id:1024 u:id:1025
    OK
    
    $ pfcount u:id:merge:1025
    127.0.0.1:6379> pfcount u:id:merge:1025
    (integer) 6
    ```

  - 



### 8.`GEO`

> 地理信息定位
>
> @since 3.2
>
> 地图元素的位置数据使用二维的经纬度表示，经度范围 (-180, 180]，纬度范围 (-90, 90]，纬度正负以赤道为界，北正南负，经度正负以本初子午线 (英国格林尼治天文台) 为界，东正西负.



#### 1.命令

- ```shell
  $ geoadd key longitude latitude member [longitude latitude member ...]
  # longitude、latitude、member 分别是该地理位置的经度、纬度、成员
  
  $ geoadd cities:locations 116.28 39.55 beijing
  
  127.0.0.1:6379> geoadd cities:locations 116.28 39.55 beijing
  (integer) 1
  
  $ geoadd cities:locations 117.12 39.08 tianjin 114.29 38.02 shijiazhuang 118.01 39.38 tangshan 115.29 38.51 baoding
  
  127.0.0.1:6379> geoadd cities:locations 117.12 39.08 tianjin 114.29 38.02 shijiazhuang 118.01 39.38 tangshan 115.29 38.51 baoding
  (integer) 4
  
  ```

  - | 城市   | 经度   | 纬度  | 成员         |
    | ------ | ------ | ----- | ------------ |
    | 北京   | 116.28 | 39.55 | beijing      |
    | 天津   | 117.12 | 39.08 | tianjin      |
    | 石家庄 | 114.29 | 38.02 | shijiazhuang |
    | 唐山   | 118.01 | 39.38 | tangshan     |
    | 保定   | 115.29 | 38.51 | baoding      |

  

- ```shell
  $ geopos key member [member ...]
  # 获取地理位置信息
  
  $ geopos cities:locations beijing tianjin
  
  127.0.0.1:6379> geopos cities:locations beijing tianjin
  1) 1) "116.28000229597091675"
     2) "39.5500007245470826"
  2) 1) "117.12000042200088501"
     2) "39.0800000535766543"
  ```

- ```shell
  $ geodist key member1 member2 [unit]
  # 获取两个地理位置的距离
  # m (meters)代表米。 
  # km (kilometers)代表公里。 
  # ft (feet)代表尺。
  # mi (miles)代表英里。 
  
  $ geodist cities:locations beijing tianjin km
  127.0.0.1:6379> geodist cities:locations beijing tianjin km
  "89.2061"
  ```

- ```shell
  $ georadius key longitude latitude radius m|km|ft|mi [withcoord][withdist] [withhash][COUNT count] [ascldesc] [store key] [storedist key] georadiusbymember key member radius m|km|ft|mi [withcoord][withdist] [withhash] [COUNT count][ascldesc] [store key] [storedist key]
  
  # 获取指定位置范围内的地理信息位置集合
  # georadius 和 georadiusbymember 两个命令的作用是一样的，都是以一个地 理位置为中心算出指定半径内的其他地理信息位置，不同的是 georadius 命令的 中心位置给出了具体的经纬度，georadiusbymember 只需给出成员即可。其中 radius m | km |ft |mi 是必需参数，指定了半径(带单位)。 
  
  # 这两个命令有很多可选参数，如下所示: withcoord:返回结果中包含经纬度。 
  # withdist:返回结果中包含离中心节点位置的距离。
  # withhash:返回结果中包含 geohash，有关 geohash 后面介绍。 
  # COUNT count:指定返回结果的数量。 
  # asc | desc:返回结果按照离中心节点的距离做升序或者降序。 
  # store key:将返回结果的地理位置信息保存到指定键。 
  # storedist key:将返回结果离中心节点的距离保存到指定键。
  
  $ georadiusbymember cities:locations beijing 150 km
  127.0.0.1:6379> georadiusbymember cities:locations beijing 150 km
  1) "beijing"
  2) "tianjin"
  3) "tangshan"
  4) "baoding"
  
  ```

- ```shell
  $ geohash key member [member ...]
  # Redis 使用 geohash 将二维经纬度转换为一维字符串
  
  127.0.0.1:6379> geohash cities:locations beijing
  1) "wx48ypbe2q0"
  # 字符串越长,表示的位置更精确
  # geohash 长度为 9 时,精度在 2 米左右， 
  # geohash 长度为 8 时,精度在 20 米左右。
  # 两个字符串越相似,它们之间的距离越近,Redis 利用字符串前缀匹配算法实现相关的命令
  
  # geohash 编码和经纬度是可以相互转换的
  ```

- ```shell
  $ zrem key member
  # 删除地理位置信息
  # GEO 没有提供删除成员的命令，但是因为 GEO 的底层实现是 zset，所以可以借用 zrem 命令实现对地理位置信息的删除.
  ```

- 





## 7.队列

### 1.List

```json
# List 

# lpush + brpop

# 足够简单，消费消息延迟几乎为零，但是需要处理空闲连接的问题

# 如果线程一直阻塞在那里，Redis 客户端的连接就成了闲置连接，闲置过久，服务器一般会主动断开连接，减少闲置资源占用，这个时候 blpop 和 brpop 或抛 出异常，所以在编写客户端消费者的时候要小心，如果捕获到异常，还有重试。

# 其他缺点包括： 
# 1.做消费者确认 ACK 麻烦，不能保证消费者消费消息后是否成功处理的问题(宕机或处理异常等)，通常需要维护一个 Pending 列表，保证消息处理确认； 
# 2.不能做广播模式，如 pub/sub，消息发布/订阅模型；
# 3.不能重复消费，一旦消费就 会被删除；
# 4.不支持分组消费。
```



### 2.ZSet

```json
# message == value
# expire-time == score

# 用多个线程轮询 zset 获取到期的任务进行处理
# 消费者无法阻塞的获取消息，只能轮询，不允许重复消息

# Redis 的 zrem 方法是多线程多进程争抢任务的关键，它的返回值决定了当 前实例有没有抢到任务，因为 获取消息的方法可能会被多个线程、多个进程调 用，同一个任务可能会被多个进程线程抢到，迪过 zrem 来决定唯一的属主
```

### 3.Pub/Sub

```json
# 优点：
# 典型的广播模式，一个消息可以发布到多个消费者；
# 多信道订阅，消费者可 以同时订阅多个信道，从而接收多类消息；
# 消息即时发送，消息不用等待消费者读取，消费者会自动接收到信道发布的消息。

# 缺点：
# 消息一旦发布，不能接收。换句话就是发布时若客户端不在线，则消息丢失， 不能寻回；
# 不能保证每个消费者接收的时间是一致的；
# 若消费者客户端出现消息积压，到一定程度，会被强制断开，导致消息意外丢失。通常发生在消息的生产 远大于消费速度时；

# 可见，Pub/Sub 模式不适合做消息存储，消息积压类的业务， 而是擅长处理广播，即时通讯，即时反馈的业务。
```



### 4.Stream

> 图片来源: https://www.runoob.com/redis/redis-stream.html

![](.\doc\stream.png)

> @since 5.0
>
> ​		一个新的强大的支持多播的可持久化的消息队列，作者声明 Redis Stream 地借鉴了 Kafka 的设计.
>
> ​		每一个Stream都有一个消息链表，将所有加入的消息都串起来，每个消息都有一个唯一的 ID 和对应的内容。消息是持久化的， Redis 重启后，内容还在。每个 Stream 都有唯一的名称，它就是 Redis 的 key，在我们首次使用 `xadd` 指令 追加消息时自动创建。
>
> ​		每个 Stream 都可以挂多个消费组，每个消费组会有个游标 `last_delivered_id` 在 Stream 数组之上往前移动，表示当前消费组已经消费到哪条消息了。每个消费组都有一个 Stream 内唯一的名称，消费组不会自动创建，它需要单独的指令 `xgroup create` 进行创建，需要指定从 Stream 的某个消息 ID 开始消费，这个 ID 用来初始化 `last_delivered_id` 变量。
>
> ​		每个消费组 (`Consumer Group`) 的状态都是独立的，相互不受影响。也就是说同一份 Stream 内部的消息会被每个消费组都消费到。 同一个消费组 (`Consumer Group`) 可以挂接多个消费者 (`Consumer`)，这些消费者之间是竞争关系，任意一个消费者读取了消息都会使游标 `last_delivered_id` 往前移动。
>
> ​		每个消费者有一个组内唯一名称。消费者 (`Consumer`) 内部会有个状态变量 `pending_ids`，它记录了当前已经被客户端读取,但是还没有 `ack `的消息。如果客户端没有 `ack`，这个变量里面的消息 ID 会越来越多，一旦某个消息被 `ack`，它就开始减少。这个 `pending_ids `变量在 Redis 官方被称之为 `PEL`，也就是 `Pending Entries List`，这是一个很核心的数据结构，它用来确保客户端至少消费了消息一次，而不会在网络传输的中途丢失了没处理。
>
> ​		消息 ID 的形式是 `timestampInMillis-sequence`，例如 1527846880572-5，它表示当前的消息在毫米时间戳 1527846880572 时产生，并且是该毫秒内产生的第5 条消息。
>
> ​		消息 ID 可以由服务器自动生成，也可以由客户端自己指定，但是形式必须是整数-整数，而且必须是后面加入的消息的 ID 要大于前面的消息 ID。 
>
> ​		消息内容就是键值对，形如 hash 结构的键值对，这没什么特别之处。
>
> -- -
>
> ​		要是消息积累太多，Stream 的链表岂不是很长，内容会不会爆掉?xdel 指令又不会删除消息，它只是给消息做了个标志位。 
>
> ​		Redis 自然考虑到了这一点，所以它提供了一个定长 Stream 功能。在 xadd 的指令提供一个定长长度 maxlen，就可以将老的消息干掉，确保最多不超过指定长度。
>
> -- -
>
> ***消息如果忘记*** ***ACK*** ***会怎样******?***
>
> --
>
> ​		Stream 在每个消费者结构中保存了正在处理中的消息 ID 列表 PEL，如果消费者收到了消息处理完了但是没有回复 ack，就会导致 PEL 列表不断增长，如果有很多消费组的话，那么这个 PEL 占用的内存就会放大。所以消息要尽可能的快速消费并确认。
>
> -- -
>
> ***PEL*** ***如何避免消息丢失******?***
>
> -- 
>
> 在客户端消费者读取 Stream 消息时，Redis 服务器将消息回复给客户端的过程中，客户端突然断开了连接，消息就丢失了。但是 PEL 里已经保存了发出去的消息 ID。待客户端重新连上之后，可以再次收到 PEL 中的消息 ID 列表。不过此时 xreadgroup 的起始消息 ID 不能为参数 `>`，而必须是任意有效的消息ID，一般将参数设为 0-0，表示读取所有的 PEL 消息以及自 `last_delivered_id `之后的新消息。
>
> -- -
>
> ***死信问题***
>
> -- 
>
> 如果某个消息，不能被消费者处理，也就是不能被 `xack`，这是要长时间处于 `Pending `列表中，即使被反复的转移给各个消费者也是如此。此时该消息的 `delivery counter`（通过 `xpending`可以查询到）就会累加，当累加到某个我们预设的临界值时，我们就认为是坏消息（也叫死信，`DeadLetter`，无法投递的消息），由于有了判定条件，我们将坏消息处理掉即可，删除即可。删除一个消息，使用 `xdel`语法，注意，这个命令并没有删除 `Pending `中的消息，因此查看 `Pending`，消息还会在，可以在执行执行 `xdel`之后，`xack` 这个消息标识其处理完毕。
>
> -- -
>
> ***Stream*** ***的高可用***
>
> ​		Stream 的高可用是建立主从复制基础上的，它和其它数据结构的复制机制没有区别，也就是说在 Sentinel 和 Cluster 集群环境下 Stream 是可以支持高可用的。不过鉴于 Redis 的指令复制是异步的，在 failover 发生时，Redis 可能会丢失极小部分数据，这点 Redis 的其它数据结构也是一样的。 
>
> -- -
>
> ***分区*** ***Partition***
>
> -- 
>
> Redis 的服务器没有原生支持分区能力，如果想要使用分区，那就需要分配多个 Stream，然后在客户端使用一定的策略来生产消息到不同的 Stream。
>
> -- -
>
> **Stream** **小结**
>
> ​		Stream 的消费模型借鉴了 Kafka 的消费分组的概念，它弥补了 Redis Pub/Sub 不能持久化消息的缺陷。但是它又不同于 kafka，Kafka 的消息可以分 partition，而 Stream 不行。如果非要分 parition 的话，得在客户端做，提供不同的 Stream 名称，对消息进行 hash 取模来选择往哪个 Stream 里塞。 
>
> ​		所以总的来说，如果在工作中已经使用了 Redis，在业务量不是很大，而又需要消息中间件功能的情况下，可以考虑使用 Redis 的 Stream 功能。但是如果并发量很高，还是以专业的消息的中间件，比如 RocketMQ、Kafka 等来支持业务更好.



#### 1.生产端

- ```shell
  $ xadd
  # 追加消息
  $ xdel
  # 删除消息，这里的删除仅仅是设置了标志位，不会实际删除消息
  $ xrange
  # 获取消息列表，会自动过滤已经删除的消息
  $ xlen
  # 消息长度
  $ del
  # 删除 Stream
  -- -
  $ xadd streamtest * name photowey age 18
  # * 号表示服务器自动生成 ID，后面顺序跟着一堆 key/value 对
  127.0.0.1:6379> xadd streamtest * name photowey age 18
  "1635426145408-0"
  # 1635426145408-0 - 则是生成的消息 ID，由两部分组成：时间戳-序号
  # 时间戳-是毫秒级单位，是生成消息的 Redis 服务器时间，它是个 64 位整型。
  # 序号-是在这个毫秒时间点内的消息序号。它也是个 64 位整型。
  
  # 为了保证消息是有序的，因此 Redis 生成的 ID 是单调递增有序的。
  # 由于 ID 中包含时间戳部分，为了避免服务器时间错误而带来的问题(例如服务器时间延后了)，
  # Redis 的每个 Stream 类型数据都维护一个 latest_generated_id 属性，用于记录最后一个消息的 ID。
  
  # 若发现当前时间戳退后(小于 latest_generated_id 所记录的)，则采用时间戳不变而序号递增的方案来作为新消息 ID(这也是序号 为什么使用 int64 的原因，保证有足够多的的序号)，从而保证 ID 的单调递增性质
  
  $ xadd streamtest * name hello age 18
  $ xadd streamtest * name greeting age 18
  
  127.0.0.1:6379> xadd streamtest * name hello age 18
  "1635426452461-0"
  127.0.0.1:6379> xadd streamtest * name greeting age 18
  "1635426460018-0"
  
  $ xrange streamtest - +
  # 其中-表示最小值 , + 表示最大值
  
  127.0.0.1:6379> xrange streamtest - +
  1) 1) "1635426145408-0"
     2) 1) "name"
        2) "photowey"
        3) "age"
        4) "18"
  2) 1) "1635426452461-0"
     2) 1) "name"
        2) "hello"
        3) "age"
        4) "18"
  3) 1) "1635426460018-0"
     2) 1) "name"
        2) "greeting"
        3) "age"
        4) "18"
       
  # 指定消息 ID 的列表     
  $ xrange streamtest - 1635426452461-0   
  127.0.0.1:6379> xrange streamtest - 1635426452461-0
  1) 1) "1635426145408-0"
     2) 1) "name"
        2) "photowey"
        3) "age"
        4) "18"
  2) 1) "1635426452461-0"
     2) 1) "name"
        2) "hello"
        3) "age"
        4) "18"
  
        
  $ xlen streamtest
  127.0.0.1:6379> xlen streamtest
  (integer) 3
  
  $ xdel streamtest 1635426452461-0
  127.0.0.1:6379> xdel streamtest 1635426452461-0
  (integer) 1
  127.0.0.1:6379> xlen streamtest
  (integer) 2
  
  $ del streamtest
  127.0.0.1:6379> del streamtest
  (integer) 1
  127.0.0.1:6379> xlen streamtest
  (integer) 0
  
  ```

- 

#### 2.消费端

> ​		虽然 Stream 中有消费者组的概念，但是可以在不定义消费组的情况下进行 Stream 消息的独立消费，当 Stream 没有新消息时，甚至可以阻塞等待。Redis 设 计了一个单独的消费指令 xread，可以将 Stream 当成普通的消息队列 (list) 来使用。使用 xread 时，我们可以完全忽略消费组 (Consumer Group) 的存在，就好比 Stream 就是一个普通的列表 (list)。

- ```shell
  -- Client A
  $ xadd
  127.0.0.1:6379> xadd streamtest * name photowey age 18
  "1635427588602-0"
  127.0.0.1:6379> xadd streamtest * name hello age 18
  "1635427596878-0"
  127.0.0.1:6379> xadd streamtest * name greeting age 18
  "1635427601836-0"
  
  127.0.0.1:6379> xlen streamtest
  (integer) 3
  
  
  -- -
  
  $ xread count 1 streams streamtest 0-0
  # 表示从 Stream 头部读取 1 条消息，0-0 指从头开始
  127.0.0.1:6379> xread count 1 streams streamtest 0-0
  1) 1) "streamtest"
     2) 1) 1) "1635427588602-0"
           2) 1) "name"
              2) "photowey"
              3) "age"
              4) "18"
              
  $ xread count 2 streams streamtest 1635427596878-0
  # 也可以指定从 streams 的消息 Id 开始(不包括命令中的消息 id)
  127.0.0.1:6379> xread count 2 streams streamtest 1635427596878-0
  1) 1) "streamtest"
     2) 1) 1) "1635427601836-0"
           2) 1) "name"
              2) "greeting"
              3) "age"
              4) "18"
              
  $ xread count 1 streams streamtest $
  # $ 代表从尾部读取，上面的意思就是从尾部读取最新的一条消息,此时默认不返回任何消息
  127.0.0.1:6379> xread count 1 streams streamtest $
  (nil)
  
  
  $ xread block 0 count 1 streams streamtest $
  # 应该以阻塞的方式读取尾部最新的一条消息，直到新的消息的到来
  # block 后面的数字代表阻塞时间，单位毫秒
  
  -- Client B
  $ xadd streamtest * name blocking-name age 18
  127.0.0.1:6379> xadd streamtest * name blocking-name age 18
  "1635427853878-0"
  
  -- Client A
  1) 1) "streamtest"
     2) 1) 1) "1635427853878-0"
           2) 1) "name"
              2) "blocking-name"
              3) "age"
              4) "18"
  (44.68s)
  ```

- ```shell
  $ xgroup create streamtest cg1 0-0
  127.0.0.1:6379> xgroup create streamtest cg1 0-0
  OK
  
  $ xgroup create streamtest cg2 $
  # $ 表示从尾部开始消费，只接受新消息，当前 Stream 消息会全部忽略
  127.0.0.1:6379> xgroup create streamtest cg2 $
  OK
  
  $ xinfo stream streamtest
  127.0.0.1:6379> xinfo stream streamtest
   1) "length"
   2) (integer) 4
   3) "radix-tree-keys"
   4) (integer) 1
   5) "radix-tree-nodes"
   6) (integer) 2
   7) "last-generated-id"
   8) "1635427853878-0"
   9) "groups"
  10) (integer) 2
  11) "first-entry"
  12) 1) "1635427588602-0"
      2) 1) "name"
         2) "photowey"
         3) "age"
         4) "18"
  13) "last-entry"
  14) 1) "1635427853878-0"
      2) 1) "name"
         2) "blocking-name"
         3) "age"
         4) "18"
  
  # 有了消费组，自然还需要消费者，Stream 提供了 xreadgroup 指令可以进行 消费组的组内消费，需要提供消费组名称、消费者名称和起始消息 ID。
  # 它同 xread 一样，也可以阻塞等待新消息。读到新消息后，对应的消息 ID 就会进入消费者的 PEL(正在处理的消息) 结构里，客户端处理完毕后使用 xack 指令通知服务器，本条消息已经处理完毕，该消息 ID 就会从 PEL 中移除。
  
  $ xreadgroup GROUP cg1 c1 count 1 streams streamtest >
  1) 1) "streamtest"
     2) 1) 1) "1635427588602-0"
           2) 1) "name"
              2) "photowey"
              3) "age"
              4) "18"
  127.0.0.1:6379> xreadgroup GROUP cg1 c1 count 1 streams streamtest >
  1) 1) "streamtest"
     2) 1) 1) "1635427596878-0"
           2) 1) "name"
              2) "hello"
              3) "age"
              4) "18"
  127.0.0.1:6379> xreadgroup GROUP cg1 c1 count 1 streams streamtest >
  1) 1) "streamtest"
     2) 1) 1) "1635427601836-0"
           2) 1) "name"
              2) "greeting"
              3) "age"
              4) "18"
  127.0.0.1:6379> xreadgroup GROUP cg1 c1 count 1 streams streamtest >
  1) 1) "streamtest"
     2) 1) 1) "1635427853878-0"
           2) 1) "name"
              2) "blocking-name"
              3) "age"
              4) "18"
  127.0.0.1:6379> xreadgroup GROUP cg1 c1 count 1 streams streamtest >
  (nil)
  
  -- Client A
  $ xreadgroup GROUP cg1 c1 block 0 count 1 streams streamtest >
  
  # > 号表示从当前消费组的 last_delivered_id 后面开始读，每当消费者读取一 条消息，last_delivered_id 变量就会前进
  
  ## 新开一个客户端
  -- Client B
  $ xadd streamtest * name test-blocking-message score 98
  127.0.0.1:6379> xadd streamtest * name test-blocking-message score 98
  "1635428112283-0"
  
  -- Client A
  1) 1) "streamtest"
     2) 1) 1) "1635428112283-0"
           2) 1) "name"
              2) "test-blocking-message"
              3) "score"
              4) "98"
  (36.36s)
  
  ```

- ```shell
  # 查看消费者组-信息
  $ xinfo groups streamtest
  127.0.0.1:6379> xinfo groups streamtest
  1) 1) "name"
     2) "cg1"
     3) "consumers"
     4) (integer) 1    # 1 个消费者
     5) "pending"
     6) (integer) 5    # 有 5 条消息未 ACK
     7) "last-delivered-id"
     8) "1635428112283-0"
  2) 1) "name"
     2) "cg2"
     3) "consumers"
     4) (integer) 0
     5) "pending"
     6) (integer) 0
     7) "last-delivered-id"
     8) "1635427853878-0"
     
  # 查看消费者-信息
  $ xinfo consumers streamtest cg1
  127.0.0.1:6379> xinfo consumers streamtest cg1
  1) 1) "name"
     2) "c1"
     3) "pending"
     4) (integer) 5    # 有 5 条消息未 ACK
     5) "idle"
     6) (integer) 190203   # 空闲了 190203 ms
     
     
  # 确认一条消息
  $ xack streamtest cg1 1635428112283-0
  127.0.0.1:6379> xack streamtest cg1 1635428112283-0
  (integer) 1
  127.0.0.1:6379> xinfo consumers streamtest cg1
  1) 1) "name"
     2) "c1"
     3) "pending"
     4) (integer) 4
     5) "idle"
     6) (integer) 409126
  127.0.0.1:6379> xinfo groups streamtest
  1) 1) "name"
     2) "cg1"
     3) "consumers"
     4) (integer) 1
     5) "pending"
     6) (integer) 4
     7) "last-delivered-id"
     8) "1635428112283-0"
  2) 1) "name"
     2) "cg2"
     3) "consumers"
     4) (integer) 0
     5) "pending"
     6) (integer) 0
     7) "last-delivered-id"
     8) "1635427853878-0"
     
     
  # xack 允许带多个消息 id
  $ xack streamtest cg1 1635428112283-0 1635427853878-0 1635427601836-0 1635427596878-0 1635427588602-0
  127.0.0.1:6379> clear
  127.0.0.1:6379> xack streamtest cg1 1635428112283-0 1635427853878-0 1635427601836-0 1635427596878-0 1635427588602-0
  (integer) 4
  
  127.0.0.1:6379> xinfo consumers streamtest cg1
  1) 1) "name"
     2) "c1"
     3) "pending"
     4) (integer) 0
     5) "idle"
     6) (integer) 651971
  ```

- ```shell
  # Stream 还提供了命令 xpending 用来获消费组或消费内消费者的未处理完毕的消息，每个 Pending 的消息有 4 个属性： 
  # 1.消息 ID 
  # 2.所属消费者 
  # 3.IDLE，已读取时长 
  # 4.delivery counter，消息被读取次数
  # 命令 xclaim 用以进行消息转移的操作，将某个消息转移到自己的 Pending 列表中。需要设置组、转移的目标消费者和消息 ID，同时需要提供 IDLE（已被 读取时长），只有超过这个时长，才能被转移
  
  -- Client A
  $ xpending key group [start end count] [consumer]
  $ xpending streamtest cg1
  127.0.0.1:6379> xpending streamtest cg1
  1) (integer) 0
  2) (nil)
  3) (nil)
  4) (nil)
  
  -- Client B
  $ xadd streamtest * name test-xpending-message age 18
  127.0.0.1:6379> xadd streamtest * name test-xpending-message age 18
  "1635429160936-0"
  
  -- Client A
  127.0.0.1:6379> xinfo consumers streamtest cg1
  1) 1) "name"
     2) "c1"
     3) "pending"
     4) (integer) 0
     5) "idle"
     6) (integer) 1133288
  127.0.0.1:6379> xreadgroup GROUP cg1 c1 count 1 streams streamtest >
  1) 1) "streamtest"
     2) 1) 1) "1635429160936-0"
           2) 1) "name"
              2) "test-xpending-message"
              3) "age"
              4) "18"
  
  # 当消费过后
  127.0.0.1:6379> xpending streamtest cg1
  1) (integer) 1
  2) "1635429160936-0"
  3) "1635429160936-0"
  4) 1) 1) "c1"
        2) "1"
  ```

- 



## 8.`Redis` 高级特性

### 1.慢查询

> `Redis` 命令执行流程
>
> 1.发送命令 -> 2.命令排队 -> 3.命令执行 -> 4.返回结果
>
> -- 慢查询只统计 步骤 3
>
> 
>
> Redis 提供了 `slowlog-log-slower-than` 和 `slowlog-max-len` 配置来解决这两个问 
>
> 题。
>
> 1.slowlog-log-slower-than 就是那个预设阀值，它的单位是微秒(1 秒= 1000 毫 秒= 1 000 000 微秒)，默认值是 10 000，假如执行了一条“很慢”的命令（例如 keys *)，如果它的执行时间超过了 10 000 微秒，也就是 10 毫秒，那么它将被记 录在慢查询日志中.
>
> 如果 `slowlog-log-slower-than`=0 表示会记录所有的命令， 
>
> `slowlog-log-slower-than`<0 对于任何命令都不会进行记录.
>
> 
>
> 2.slowlog-max-len 用来设置慢查询日志最多存储多少条，并没有说明存放在哪。 实际上 Redis 使用了一个列表来存储慢查询日志，slowlog-max-len 就是列表的最 大长度。当慢查询日志列表被填满后，新的慢查询命令则会继续入队，队列中的第一条数据就会出列。
>
> 
>
> 3.虽然慢查询日志是存放在 Redis 内存列表中的，但是 Redis 并没有告诉我们这里列表是什么,而是通过一组命令来实现对慢查询日志的访问和管理。
>
> -- -
>
> slowlog-max-len 配置建议；
>
> 建议调大慢查询列表，记录慢查询时 Redis 会对长命令做截断操作，并不会占用大量内存。增大慢查询列表可以减缓慢查询被剔 除的可能,例如线上可设置为 1000 以上。 
>
> slowlog-log-slower-than 配置建议:
>
> 默认值超过 10 毫秒判定为慢查询，需要根 据 Redis 并发量调整该值。由于 Redis 采用单线程响应命令，对于高流量的场景, 如果命令执行时间在 1 毫秒以上，那么 Redis 最多可支撑 OPS 不到 1000。因此对 于高 OPS 场景的 Redis 建议设置为 1 毫秒或者更低比如 100 微秒。 慢查询只记录命令执行时间，并不包括命令排队和网络传输时间
>
> -- -
>
> 可能会丢失部分慢查询命令，为了防止这种情况发生，可以定期执行 `slow get` 命令将慢查询日志持久化到其他存储中。

- 命令

  - ```shell
    $ slowlog get [n] 
    # 获取慢查询日志,参数 n 可以指定查
    
    127.0.0.1:6379> slowlog get 10
    1) 1) (integer) 1
       2) (integer) 1635410458
       3) (integer) 13562
       4) 1) "geodist"
          2) "cities:locations"
          3) "beijing"
          4) "tianjin"
          5) "km"
       5) "127.0.0.1:33604"
       6) ""
    2) 1) (integer) 0
       2) (integer) 1635410303
       3) (integer) 10840
       4) 1) "geopos"
          2) "cities:locations"
          3) "beijing"
          4) "tianjin"
       5) "127.0.0.1:33604"
       6) ""
       
    
    $ slowlog len 
    # 获取慢查询日志列表当前的长度
    $ slowlog reset 
    # 慢查询日志重置
    ```

  - 



### 2.`Pipline`

> Redis` 命令执行流程
>
> 1.发送命令 -> 2.命令排队 -> 3.命令执行 -> 4.返回结果
>
> 其中 1+4 称为 Round Trip Time (RTT,往返时间)，也就是数据在网络上传输的 时间。
>
> 
>
> Pipeline（流水线) 机制能改善上面这类问题,它能将一组 Redis 命令进行组装, 通过一次 RTT 传输给 Redis,再将这组 Redis 命令的执行结果按顺序返回给客户端。
>
> 没有使用 Pipeline 执行了 n 条命令,整个过程需要 n 次 RTT。
>
> 使用 Pipeline 执行了 n 次命令，整个过程需要 1 次 RTT。

> 本机测试:
>
> 非pipeline操作1000次字符串数据类型set写入，耗时：593毫秒
> pipeline操作1000次字符串数据类型set写入，耗时：69毫秒
>
> -- -
>
> 非pipeline操作1000次字符串数据类型set写入，耗时：812毫秒
> pipeline操作1000次字符串数据类型set写入，耗时：22毫秒
>
> -- -
>
> 非pipeline操作1000次字符串数据类型set写入，耗时：575毫秒
> pipeline操作1000次字符串数据类型set写入，耗时：22毫秒
>
> -- -
>
> -- -
>
> Pipeline 虽然好用,但是每次 Pipeline 组装的命令个数不能没有节制，否则一 次组装 Pipeline 数据量过大，一方面会增加客户端的等待时间，另一方面会造成 一定的网络阻塞,可以将一次包含大量命令的 Pipeline 拆分成多次较小的 Pipeline 来完成，比如可以将 Pipeline 的总发送大小控制在内核输入输出缓冲区大小之内或者控制在 TCP 包的大小 1460 字节之内。



### 3.事务

> `Redis `提供了简单的事务功能，将一组需要一起执行的命令放到 multi 和 exec 两个命令之间。
>
> multi 命令代表事务开始，
>
> exec 命令代表事务结束，
>
> 如果要停止事务的执行，可以使用 discard 命令代替 exec 命令即可.

- 1.命令

- ```shell
  # 测试 A 关注 B 场景
  $ sadd u:a:follow ub
  $ sadd u:b:fans ua
  
  -- Client A
  $ multi
  $ sadd u:a:follow ub
  $ sadd u:b:fans ua
  127.0.0.1:6379> multi
  OK
  127.0.0.1:6379> sadd u:a:follow ub
  QUEUED
  127.0.0.1:6379> sadd u:b:fans ua
  QUEUED
  
  
  -- client B
  $ sismember u:a:follow ub
  $ sismember u:b:fans ua
  127.0.0.1:6379> sismember u:a:follow ub
  (integer) 0
  127.0.0.1:6379> sismember u:b:fans ua
  (integer) 0
  
  
  -- Client A
  $ exec
  127.0.0.1:6379> exec
  1) (integer) 1
  2) (integer) 1
  
  -- client B
  127.0.0.1:6379> sismember u:a:follow ub
  (integer) 1
  127.0.0.1:6379> sismember u:b:fans ua
  (integer) 1
  
  ```

- 总结 `Pipline` 和 事务的区别

  - ```json
    # 1.pipeline 是客户端的行为，对于服务器来说是透明的，可以认为服务器无 法区分客户端发送来的查询命令是以普通命令的形式还是以 pipeline 的形式发送 到服务器的；
    
    # 2.事务则是实现在服务器端的行为，用户执行 MULTI 命令时，服务器会将 对应这个用户的客户端对象设置为一个特殊的状态，在这个状态下后续用户执行 的查询命令不会被真的执行，而是被服务器缓存起来，直到用户执行 EXEC 命令 为止，服务器会将这个用户对应的客户端对象中缓存的命令按照提交的顺序依次 执行。
    
    # 3.应用 pipeline 可以提服务器的吞吐能力，并提高 Redis 处理查询请求的能力。但是这里存在一个问题，当通过 pipeline 提交的查询命令数据较少，可以被 内核缓冲区所容纳时，Redis 可以保证这些命令执行的原子性。然而一旦数据量 过大，超过了内核缓冲区的接收大小，那么命令的执行将会被打断，原子性也就 无法得到保证。因此 pipeline 只是一种提升服务器吞吐能力的机制，如果想要命 令以事务的方式原子性的被执行，还是需要事务机制，或者使用更高级的脚本功 能以及模块功能。
    
    # 4.可以将事务和 pipeline 结合起来使用，减少事务的命令在网络上的传输 时间，将多次网络 IO 缩减为一次网络 IO。
    
    -- -
    # Redis 提供了简单的事务，之所以说它简单，主要是因为它不支持事务中的 回滚特性,同时无法实现命令之间的逻辑关系计算，当然也体现了 Redis 的"keep it simple" 的特性
    ```

### 4.`LUA`

> `Lua ` 语言是在 1993 年由巴西一个大学研究小组发明,其设计目标是作为嵌入 式程序移植到其他应用程序,它是由 C 语言实现的.
>
> ```shell
> 127.0.0.1:6379> eval 'return "hello "..KEYS[1]' 1 world
> "hello world"
> 
> ```
>
> 在 Redis 使用 LUA 脚本的好处包括： 
>
> 1、减少网络开销，在 Lua 脚本中可以把多个命令放在同一个脚本中运行； 
>
> 2、原子操作，Redis 会将整个脚本作为一个整体执行，中间不会被其他命令插入。换句话说，编写脚本的过程中无需担心会出现竞态条件； 
>
> 3、复用性，客户端发送的脚本会存储在 Redis 中，这意味着其他客户端可以复用这一脚本来完成同样的逻辑 

1.命令

- ```shell
  $ EVAL script numkeys key [key ...] arg [arg ...]
  $ eval 'return "hello "..KEYS[1]' 1 world
  "hello world"
  
  $ script flush ：清除所有脚本缓存。 
  $ script exists ：根据给定的脚本校验，检查指定的脚本是否存在于脚本缓存。 
  $ script load ：将一个脚本装入脚本缓存，返回 SHA1 摘要，但并不立即运行 它。
  $ script kill ：杀死当前正在运行的脚本。 这里的 SCRIPT LOAD 命令就可以用来生成
  
  $ script load "return redis.call('set',KEYS[1],ARGV[1])"
  127.0.0.1:6379> script load "return redis.call('set',KEYS[1],ARGV[1])"
  "c686f316aaf1eb01d5a4de1b0b63cd233010e63d"
  127.0.0.1:6379> 
  
  $ evalsha
  $ evalsha "c686f316aaf1eb01d5a4de1b0b63cd233010e63d" 1 scriptSet test-script-set
  $ get scriptSet
  
  127.0.0.1:6379> evalsha "c686f316aaf1eb01d5a4de1b0b63cd233010e63d" 1 scriptSet test-script-set
  OK
  127.0.0.1:6379> get scriptSet
  "test-script-set"
  ```

### 5.发布/订阅

> Redis 提供了基于“发布/订阅”模式的消息机制，此种模式下，消息发布者和订阅者不进行直接通信,发布者客户端向指定的频道( channel)发布消息，订阅 该频道的每个客户端都可以收到该消息。
>
> 
>
> 需要消息解耦又并不关注消息可靠性的地方都可以使用发布订阅模式。

#### 1.命令

- ```shell
  $ publish channel message
  # 发布消息
  
  $ subscribe channel [channel ...]
  # 订阅者可以订阅一个或多个频道，如果此时另一个客户端发布一条消息，当前订阅者客户端会收到消息。
  # 如果有多个客户端同时订阅了同一个频道，都会收到消息。
  
  -- Client A
  127.0.0.1:6379> publish testchannel hello-channel
  (integer) 0
  
  127.0.0.1:6379> publish testchannel hello-channel-2
  (integer) 1
  
  127.0.0.1:6379> publish testchannel hello-channel-3
  (integer) 1
  
  -- Client B
  127.0.0.1:6379> subscribe testchannel
  Reading messages... (press Ctrl-C to quit)
  1) "subscribe"
  2) "testchannel"
  3) (integer) 1
  
  1) "message"
  2) "testchannel"
  3) "hello-channel-2"
  
  1) "message"
  2) "testchannel"
  3) "hello-channel-3"
  ```

- ```shell
  $ pubsub channels [argument [argument ...]]
  # 查看活跃的频道
  127.0.0.1:6379> pubsub channels testchannel
  1) "testchannel"
  
  $ pubsub numsub channel
  # 查看频道订阅数
  127.0.0.1:6379> pubsub numsub testchannel
  1) "testchannel"
  2) (integer) 1
  
  $ pubsub numpat [argument [argument ...]]
  # 查看模式订阅数
  127.0.0.1:6379> pubsub numpat
  (integer) 0
  
  ```



## 9.持久化

> Redis 虽然是个内存数据库，但是 Redis 支持 RDB 和 AOF 两种持久化机制，将数据写往磁盘，可以有效地避免因进程退出造成的数据丢失问题，当下次重启时利用之前持久化的文件即可实现数据恢复。

### 1.`RDB`

> RDB 持久化是把当前进程数据生成快照保存到硬盘的过程，触发 RDB 持久化过程分为手动触发和自动触发。
>
> ***触发机制***
>
> 手动触发分别对应 save 和 bgsave 命令.
>
> ​		save 命令: 阻塞当前 Redis 服务器，直到 RDB 过程完成为止，对于内存比较大的实例会造成长时间阻塞，线上环境不建议使用。 
>
> ​		bgsave 命令: Redis 进程执行 fork 操作创建子进程，RDB 持久化过程由子进程负责，完成后自动结束。阻塞只发生在 fork 阶段，一般时间很短。显然 bgsave 命令是针对 save 阻塞问题做的优化。因此 Redis 内部所有的涉及 RDB 的操作都采用 bgsave 的方式。
>
> > 关键词 Fork 、 COW(`Copy On Write`) 写时复制技术
>
> -- -
>
> 除了执行命令手动触发之外，Redis 内部还存在自动触发 RDB 的持久化机制， 
>
> 例如以下场景: 
>
> 1)、使用 save 相关配置,如"save m n"。表示 m 秒内数据集存在 n 次修改时，自动触发 bgsave。 
>
> 2)、如果从节点执行全量复制操作，主节点自动执行 bgsave 生成 RDB 文件并发送给从节点。 
>
> 3)、执行 debug reload 命令重新加载 Redis 时，也会自动触发 save 操作。 
>
> 4)、默认情况下执行 shutdown 命令时，如果没有开启 AOF 持久化功能则自动执行 bgsave。 
>
> -- -
>
> 关闭 RDB 持久化，在课程讲述的 Redis 版本（6.2.4）上，是将配置文件中的 
>
> save 配置改为: save ""
>
> -- -
>
> ***RDB*** ***文件***
>
> ​		RDB 文件保存在 dir 配置指定的目录下，文件名通过dbfilename 配置指定。可以通过执行 
>
> config set dir {newDir} 和 config set dbfilename (newFileName} 
>
> 运行期动态执行,当下次运行时 RDB 文件会保存到新目录。 
>
> ​		Redis 默认采用 LZF 算法对生成的 RDB 文件做压缩处理，压缩后的文件远远小于内存大小，默认开启，可以通过参数 
>
> config set rdbcompression { yes | no}动态修改。
>
> 虽然压缩 RDB 会消耗 CPU，但可大幅降低文件的体积，方便保存到硬盘或通过网维示络发送给从节点,因此线上建议开启。 
>
> ​		如果 Redis 加载损坏的 RDB 文件时拒绝启动,并打印如下日志: \# Short read or 0OM loading DB. Unrecoverable error，aborting now. 这时可以使用 Redis 提供的 redis-check-dump 工具检测 RDB 文件并获取对应的错误报告。
>
> -- -
>
> ***RDB*** ***的优缺点***
>
> RDB 的优点:
>
> ​		RDB 是一个紧凑压缩的二进制文件，代表 Redis 在某个时间点上的数据快照。非常适用于备份,全量复制等场景。 
>
> ​		比如每隔几小时执行 bgsave 备份，并把 RDB 文件拷贝到远程机器或者文件系统中(如 hdfs),用于灾难恢复。 
>
> ​	Redis 加载 RDB 恢复数据远远快于 AOF 的方式。 
>
> 
>
> RDB 的缺点:
>
> ​		RDB 方式数据没办法做到实时持久化/秒级持久化。因为 bgsave 每次运行都要执行 fork 操作创建子进程,属于重量级操作,频繁执行成本过高。 
>
> ​		RDB 文件使用特定二进制格式保存，Redis 版本演进过程中有多个格式的 RDB 版本，存在老版本 Redis 服务无法兼容新版 RDB 格式的问题。 
>
> ​		针对 RDB 不适合实时持久化的问题,Redis 提供了 AOF 持久化方式来解决。

- `bgsave`

  - ```json
    # basave 执行流程
    # 1)、执行 bgsave 命令，Redis 父进程判断当前是否存在正在执行的子进程，如 RDB/AOF 子进程,如果存在，bgsave 命令直接返回。 
    
    # 2)、父进程执行 fork 操作创建子进程，fork 操作过程中父进程会阻塞，通过 info stats 命令查看 latest_fork_usec 选项，可以获取最近一个 fork 操作的耗时，单位为微秒。
    
    # 3)、父进程 fork 完成后，bgsave 命令返回"Background saving started"信息并不再阻塞父进程,可以继续响应其他命令。 
    
    # 4)、子进程创建 RDB 文件，根据父进程内存生成临时快照文件，完成后对原有文件进行原子替换。执行 lastsave 命令可以获取最后一次生成 RDB 的时间，对应 info 统计的 rdb_last_save_time 选项。 
    
    # 5)、进程发送信号给父进程表示完成，父进程更新统计信息，具体见 info Persistence 下的 rdb_*相关选项。
    ```

  - 

### 2.`AOF`

> AOF(append only file)持久化:
>
> 以独立日志的方式记录每次写命令，重启时再重新执行 AOF 文件中的命令达到恢复数据的目的。
>
> AOF 的主要作用是解决了数据持久化的实时性,目前已经是 Redis 持久化的主流方式。理解掌握好 AOF 持久化机制对我们兼顾数据安全性和性能非常有帮助。
>
> -- -
>
> ***使用*** ***AOF***
>
> 开启 AOF 功能需要设置配置:appendonly yes，默认不开启。
>
> 
>
> AOF 文件名通过 appendfilename 配置设置，默认文件名是 appendonly.aof。保存路径同 RDB 持久化方式一致，通过 dir 配置指定。
>
> 
>
> AOF 的工作流程操作:
>
> 命令写入( append)、文件同步( sync)、文件重写(rewrite)、重启加载( load)。
>
> ![](.\doc\aof.jpg)
>
> 1)、 所有的写入命令会追加到 aof_buf(缓冲区)中。 
>
> 2)、AOF 缓冲区根据对应的策略向硬盘做同步操作。 
>
> 3)、随着 AOF 文件越来越大,需要定期对 AOF 文件进行重写,达到压缩的目的。 
>
> 4)、当 Redis 服务器重启时,可以加载 AOF 文件进行数据恢复。
>
> -- -
>
> ***命令写入***
>
> > AOF 命令写入的内容直接是 RESP 文本协议格式
>
> 1)、AOF 为什么直接采用文本协议格式?
>
> ```json
> # 文本协议具有很好的兼容性。
> # 开启 AOF 后，所有写入命令都包含追加操作，直接采用协议格式，避免了二次处理开销。文本协议具有可读性,方便直接修改和处理。
> ```
>
> -- -
>
> ***AOF缓存***
>
> 1)、AOF 为什么把命令追加到 aof_buf 中?
>
> ```json
> # Redis 使用单线程响应命令，如果每次写 AOF 文件命令都直接追加到硬盘，那么性能完全取决于当前硬盘负载。
> # 先写入缓冲区 aof_buf 中，还有另一个好处，Redis 可以提供多种缓冲区同步硬盘的策略，在性能和安全性方面做出平衡。
> 
> # Redis 提供了多种 AOF 缓冲区同步文件策略，由参数 appendfsync 控制。 
> # 1.always
> # 写入 aof_buf 后调用系统 fsync 操作同步到 AOF 文件，fsync 完成后线程返回命令 fsync 同步文件。 
> 
> # 2.everysec 
> # 写入 aof_buf 后调用系统 write 操作，write 完成后线程返回。操作由专门线程每秒调用一次 fsync 命令。
> 
> # 3.no
> # 写入 aof_buf 后调用系统 write 操作，不对 AOF 文件做 fsync 同步，同步硬盘操作由操作系统负责,通常同步周期最长 30 秒。
> 
> # TIPS:系统调用 write 和 fsync 说明 
> # write 操作会触发延迟写(delayed write)机制。Linux 在内核提供页缓冲区用来提高硬盘 IO 性能。write 操作在写入系统缓冲区后直接返回。同步硬盘操作依赖于系统调度机制。
> # 例如:缓冲区页空间写满或达到特定时间周期。同步文件之前，如果此时系统故障宕机,缓冲区内数据将丢失。 
> 
> # fsync 针对单个文件操作(比如 AOF 文件)，做强制硬盘同步，fsync 将阻塞直到写入硬盘完成后返回,保证了数据持久化。
> 
> -- -
> 
> # 很明显，配置为 always 时，每次写入都要同步 AOF 文件，在一般的 SATA 硬盘上，Redis 只能支持大约几百 TPS 写入,显然跟 Redis 高性能特性背道而驰,不建议配置。
> 
> # 配置为 no，由于操作系统每次同步 AOF 文件的周期不可控,而且会加大每次同步硬盘的数据量,虽然提升了性能,但数据安全性无法保证。 
> 
> # 配置为 everysec，是建议的同步策略，也是默认配置，做到兼顾性能和数据安全性。理论上只有在系统突然宕机的情况下丢失 1 秒的数据。(严格来说最多丢失 1 秒数据是不准确的)
> ```
>
> -- -
>
> ***重写机制***
>
> 随着命令不断写入 AOF，文件会越来越大，为了解决这个问题，Redis 引入 AOF 重写机制压缩文件体积。AOF 文件重写是把 Redis 进程内的数据转化为写命令同步到新 AOF 文件的过程。
>
> -- 
>
> 重写后的 AOF 文件为什么可以变小?
>
> 1)、进程内已经超时的数据不再写入文件。 
>
> 2)、旧的 AOF 文件含有无效命令，如 set a 111、set a 222 等。重写使用进程内数据直接生成，这样新的 AOF 文件只保留最终数据的写入命令。
>
> 3)、多条写命令可以合并为一个，如:lpush list a、lpush list b、lpush listc 可以转化为: lpush list a b c。为了防止单条命令过大造成客户端缓冲区溢出，对于 list、set、hash、zset 等类型操作，以 64 个元素为界拆分为多条。
>
> --
>
> AOF 重写降低了文件占用空间，除此之外，另一个目的是:更小的 AOF 文件可以更快地被 Redis 加载。
>
> -- -
>
> AOF 重写过程可以手动触发和自动触发:  
>
> 手动触发: 直接调用 bgrewriteaof 命令。
>
> 自动触发: 根据 auto-aof-rewrite-min-size 和 auto-aof-rewrite-percentage 参数确定自动触发时机。
>
> auto-aof-rewrite-min-size: 表示运行 AOF 重写时文件最小体积，默认为 64MB。 
>
> auto-aof-rewrite-percentage: 代表当前 AOF 文件空间(aof_currentsize）和上一次重写后 AOF 文件空间(aof_base_size)的比值。
>
> -- -
>
> 当触发 AOF 重写时,内部做了哪些事呢?
>
> 流程说明: 
>
> 1)、执行 AOF 重写请求。 
>
> 
>
> 2)、父进程执行 fork 创建子进程,开销等同于 bgsave 过程。
>
> 
>
> 3.1)、主进程 fork 操作完成后，继续响应其他命令。所有修改命令依然写入AOF 缓冲区并根据 appendfsync 策略同步到硬盘，保证原有 AOF 机制正确性。
>
> 3.2)、由于 fork 操作运用写时复制技术，子进程只能共享 fork 操作时的内存数据。由于父进程依然响应命令，Redis 使用"AOF 重写缓冲区"保存这部分新数据，防止新 AOF 文件生成期间丢失这部分数据。
>
> 
>
> 4)、子进程根据内存快照，按照命令合并规则写入到新的 AOF 文件。每次批量写入硬盘数据量由配置 aof-rewrite-incremental-fsync 控制，默认为 32MB，防止单次刷盘数据过多造成硬盘阻塞。
>
> 
>
> 5.1)、新 AOF 文件写入完成后，子进程发送信号给父进程,父进程更新统计信息，具体见 info persistence 下的 aof_*相关统计。
>
> 5.2)、父进程把 AOF 重写缓冲区的数据写入到新的 AOF 文件。
>
> 5.3)、使用新 AOF 文件替换老文件,完成 AOF 重写。
>
> -- -
>
> ***重启加载***
>
> AOF 和 RDB 文件都可以用于服务器重启时的数据恢复。redis 重启时加载AOF 与 RDB 的顺序是怎么样的呢？ 
>
> 1，当 AOF 和 RDB 文件同时存在时，优先加载 AOF 
>
> 2，若关闭了 AOF，加载 RDB 文件 
>
> 3，加载 AOF/RDB 成功，redis 重启成功 
>
> 4，AOF/RDB 存在错误，启动失败打印错误信息
>
> ![](.\doc\aof-rdb-load.jpg)
>
> -- -
>
> ***文件校验***
>
> 加载损坏的 AOF 文件时会拒绝启动，对于错误格式的 AOF 文件，先进行备份，然后采用:
>
>  redis-check-aof --fix 命令进行修复，对比数据的差异，找出丢失的数据，有些可以人工修改补全。 
>
> AOF 文件可能存在结尾不完整的情况，比如机器突然掉电导致 AOF 尾部文件命令写入不全。Redis 为我们提供了 aof-load-truncated 配置来兼容这种情况，默认开启。加载 AOF 时当遇到此问题时会忽略并继续启动,同时如下警告日志。
>
> -- -
>
> 如何改善 fork 操作的耗时:
>
> 1)、优先使用物理机或者高效支持 fork 操作的虚拟化技术
>
> 2)、控制 Redis 实例最大可用内存，fork 耗时跟内存量成正比,线上建议每个 Redis 实例内存控制在 10GB 以内。
>
> 3)、降低 fork 操作的频率，如适度放宽 AOF 自动触发时机，避免不必要的全量复制等。



-- -



## 10.分布式锁

### 1.命令

- ```shell
  $ SETNX 
  # SET if Not eXists
  ```

### 2.死锁

- 程序处理业务逻辑异常，没及时释放锁
- 进程挂了，没机会释放锁

#### 2.1.避免死锁

> 在申请锁时，给这把锁设置一个「租期」
>
> 在 Redis 中实现时，就是给这个 key 设置一个「过期时间」。
>
> ```shell
> $ SETNX <key> <value>
> $ EXPIRE <key> <expire-time>
> # -> 带来的问题 -> 操作不是原子操作
> # -- 场景再现:
> # SETNX 执行成功，执行 EXPIRE 时由于网络问题，执行失败;
> # SETNX 执行成功，Redis 异常宕机，EXPIRE 没有机会执行;
> # SETNX 执行成功，客户端异常崩溃，EXPIRE 也没有机会执行;
> 
> # Redis 2.6.12 之后，Redis 扩展了 SET 命令的参数，用这一条命令就可以.
> 
> $ SET <key> <value> EX <expire-time> NX
> 
> # 锁过期依然存在问题:
> # 客户端 1 操作共享资源耗时太久，导致锁被自动释放，之后被客户端 2 持有释；
> # 客户端 1 操作共享资源完成后，却又释放了客户端 2 的锁。
> ```



#### 2.2.释放别人的锁

>  解决办法是:
>
> 客户端在加锁时，设置一个只有自己知道的「唯一标识」进去。
>
> ```shell
> $ SET <key> $uuid EX <expire-time> NX
> # 在释放锁时，要先判断这把锁是否还归自己持有
> # 伪代码
> # if redis.get("lock") == $uuid
> # then 
> #     redis.del("lock")
> 
> # 新的问题
> # GET 和 DEL 不是 原子操作
> # 怎么实现原子?
> # 采用 LUA
> ```
>
> ```lua
> ---
> --- Generated by Luanalysis
> --- Created by photowey.
> --- DateTime: 2021/10/27 15:09
> ---
> if redis.call('get', KEYS[1]) == ARGV[1]
> then
>     return redis.call('del', KEYS[1])
> else
>     return 0
> end
> ```
>
> 

```json
# 基于 Redis 实现的分布式锁，一个严谨的的流程如下:
# 加锁：SET lock_key $unique_id EX $expire_time NX
# 操作共享资源
# 释放锁：Lua 脚本，先 GET 判断锁是否归属自己，再 DEL 释放锁
```



#### 2.3.过期时间不好评估

> 锁的过期时间如果评估不好，这个锁就会有「提前」过期的风险。
>
> 妥协方案:
>
> 尽量「冗余」过期时间，降低锁提前过期的概率。
>
> 是否可以设计这样的方案：
>
> 加锁时，先设置一个过期时间，然后我们开启一个「守护线程」，定时去检测这个锁的失效时间，如果锁快要过期了，操作共享资源还未完成，那么就自动对锁进行「续期」，重新设置过期时间。
>
> 
>
> 无限续期问题:
>
> JVM内进程:
>
> 本进程内的其他线程在 finally 释放锁之后是可以清楚锁的-不存在无限期-续期问题。
>
> JVM 外进程:
>
> 其他服务器上其他进程内的线程怎么办呢？如果出现问题的进程以后没有分布锁的需求了，这个锁还是会出现无限续期的情况。
>
> 解决方案:
>
> 手动清楚dead线程线程持有的锁。



#### 2.4.总结

> 基于 Redis 的实现分布式锁，前面遇到的问题，以及对应的解决方案：
>
> - 1、死锁：设置过期时间;
>
> - 2、过期时间评估不好，锁提前过期：守护线程，自动续期;
>
> - 3、锁被别人释放：锁写入唯一标识，释放锁先检查标识，再释放;
>
> - 以上的前提都是单机Redis,在集群环境(**<u>主从切换</u>**)下还是有问题。
>
> - Redis 官方的解决方案: `RedLock`
>
> - > `RedLock` 依旧解决不了 `NPC` 场景下的问题。



### 3.`RedLock`

> 前提:
>
> 不再需要部署从库和哨兵实例，只部署主库；
>
> 主库要部署多个，官方推荐至少 5 个实例。
>
> > 注意：不是部署 Redis Cluster，就是部署 5 个简单的 Redis 实例。 

#### 3.1.流程

> 1、客户端先获取「当前时间戳 T1」。
>
> 2、客户端依次向这 5 个 Redis 实例发起加锁请求（用前面讲到的 SET 命令），且每个请求会设置超时时间（毫秒级，要远小于锁的有效时间），如果某一个实例加锁失败（包括网络超时、锁被其它人持有等各种异常情况），就立即向下一个 Redis 实例申请加锁。
>
> 3、如果客户端从 >=3 个（大多数）以上 Redis 实例加锁成功，则再次获取「当前时间戳 T2」，
>
> 如果 T2 - T1 < 锁的过期时间，此时，认为客户端加锁成功，否则认为加锁失败。
>
> 4、加锁成功，去操作共享资源。
>
> 5、加锁失败，向「全部节点」发起释放锁请求。
>
> -- -
>
> 总结：
>
> - 客户端在多个 Redis 实例上申请加锁；
>
> - 必须保证大多数节点加锁成功；
>
> - 大多数节点加锁的总耗时，要小于锁设置的过期时间；
>
> - 释放锁，要向全部节点发起释放锁请求。 
>
> -- -
> 为什么?
>
> 为什么要在多个实例上加锁？
>
> -- 本质上是为了「容错」，部分实例异常宕机，剩余的实例加锁成功，整个锁 
>
> 服务依旧可用。
>
> 
>
> 为什么大多数加锁成功，才算成功？
>
> 多个 Redis 实例一起来用，其实就组成了一个「分布式系统」。
>
> -- 在分布式系统中，即使存在「故障」节点，只要大多数节点正常，那么整个系统依旧是可以提供正确服务的。
>
> 
>
> 为什么步骤 3 加锁成功后，还要计算加锁的累计耗时？
>
> -- 大多数节点加锁成功，但如果加锁的累计耗时已经「超过」了锁的过期时间，那此时有些实例上的锁可能已经失效了，这个锁就没有意义了。
>
> 
>
> 为什么释放锁，要操作所有节点？
>
> 释放锁时，不管之前有没有加锁成功，需要释放「所有节点」的锁，以保证清理节点上「残留」的锁。



## 11.主从复制

### 1.配置

- 1.配置文件

  - ```json
    # 在配置文件中加入 slaveof {masterHost} {masterPort} 随 Redis 启动生效
    ```

- 2.启动参数

  - ```shell
    # 在 redis-server 启动命令后加入--slaveof {masterHost} {masterPort} 生效。
    $ redis-server --slaveof {masterHost} {masterPort}
    ```

- 3.命令行

  - ```shell
    $ slaveof {masterHost} { masterPort} # 生效
    ```

  - 切主

    - ```shell
      $ slaveof {newMasterIp} {newMasterPort}
      ```

    - ```json
      # 切主内部流程如下: 
      # 1).断开与旧主节点复制关系。
      # 2).与新主节点建立复制关系。
      # 3).删除从节点当前所有数据。
      # 4).对新主节点进行复制操作。
      ```

    - 

### 2.断开复制

```shell
$ slaveof no one
# 从节点断开复制后并不会抛弃原有数据，只是无法再获取主节点上的数据变化。
```



### 3.只读

```json
# 默认情况下，从节点使用 slave-read-only=yes 配置为只读模式。
# 由于复制只 能从主节点到从节点，对于从节点的任何修改主节点都无法感知，修改从节点会造成主从数据不一致。

# 因此建议线上不要修改从节点的只读模式。
```



### 4.拓扑

```json
# Redis 的复制拓扑结构可以支持单层或多层复制关系，根据拓扑复杂性可以分为以下三种:
# 一主一从、
# 一主多从、
# 树状主从结构。
```

#### 4.1.一主一从

```json
# 一主一从结构是最简单的复制拓扑结构，用于主节点出现宕机时从节点提供故障转移支持。
# 注意事项
# 当应用写命令并发量较高且需要持久化时,可以只在从节点上开启 AOF,这样既保证数据安全性同时也避免了持久化对主节点的性能干扰。但需要注意的是，当主节点关闭持久化功能时，如果主节点脱机要避免自动重启操作。 

# 因为主节点之前没有开启持久化功能自动重启后数据集为空,这时从节点如果继续复制主节点会导致从节点数据也被清空的情况,丧失了持久化的意义。安全的做法是在从节点上执行 slaveof no one 断开与主节点的复制关系，再重启主节点从而避免这一问题。
```

#### 4.2.一主多从

```json
# 一主多从结构(又称为星形拓扑结构）使得应用端可以利用多个从节点实现读写分离。

# 对于读占比较大的场景，可以把读命令发送到从节点来分担主节点压力。同时在日常开发中如果需要执行一些比较耗时的读命令，如:keys、sort 等，可以在其中一台从节点上执行，防止慢查询对主节点造成阻塞从而影响线上服务的稳定性。

# 对于写并发量较高的场景,多个从节点会导致主节点写命令的多次发送从而过度消耗网络带宽，同时也加重了主节点的负载影响服务稳定性。
```

#### 4.3.树状主从

```json
# 树状主从结构(又称为树状拓扑结构）使得从节点不但可以复制主节点数据，同时可以作为其他从节点的主节点继续向下层复制。

# 通过引入复制中间层，可以有效降低主节点负载和需要传送给从节点的数据量。
```



### 5.复制流程

```json
# 在从节点执行 slaveof 命令后，复制过程便开始运作。 
# 1) 保存主节点(master)信息 执行 slaveof 后从节点只保存主节点的地址信息便直接返回，这时建立复制流程还没有开始。 

# 2) 从节点(slave)内部通过每秒运行的定时任务维护复制相关逻辑，当定时任务发现存在新的主节点后，会尝试与该节点建立网络连接。
# 从节点会建立一个 socket 套接字，专门用于接受主节点发送的复制命令。从节点连接成功后打印日志。如果从节点无法建立连接，定时任务会无限重试直到连接成功或者执行 slaveof no one 取消复制。
# 关于连接失败，可以在从节点执行 info replication 查看 master_link_down_since_seconds 指标，它会记录与主节点连接失败的系统时间。从节点连接主节点失败时也会每秒打印日志。 

# 3) 发送 ping 命令。 
# 连接建立成功后从节点发送 ping 请求进行首次通信，ping 请求主要目的： 
# 检测主从之间网络套接字是否可用、检测主节点当前是否可接受处理命令。 
# 从节点发送的 ping 命令成功返回，Redis 打印日志，并继续后续复制流程: Master replied to PING,replication can continue...

# 4) 权限验证。
# 如果主节点设置了 requirepass 参数，则需要密码验证，从节点必须配置 masterauth 参数保证与主节点相同的密码才能通过验证;
# 如果验证失 败复制将终止，从节点重新发起复制流程。 

# 5) 同步数据集。
# 主从复制连接正常通信后，对于首次建立复制的场景,主节点会把持有的数据全部发送给从节点，这部分操作是耗时最长的步骤。Redis 在 2.8 版本以后采用新复制命令 psync 进行数据同步，原来的 sync 命令依然支持，保 证新旧版本的兼容性。新版同步划分两种情况:全量同步和部分同步。

# 6) 命令持续复制。
# 当主节点把当前的数据同步给从节点后，便完成了复制的建立流程。接下来主节点会持续地把写命令发送给从节点,保证主从数据一致性。

# psync 命令运行需要以下支持: 
# 主从节点各自复制偏移量。
# 主节点复制积压缓冲区。
# 主节点运行 id。
```



### 6.数据同步

#### 6.1.复制偏移量

```json
# 参与复制的主从节点都会维护自身复制偏移量。主节点( master)在处理完写 入命令后，会把命令的字节长度做累加记录,统计信息在 info relication 中的 master_repl_offset 指标中.

# 从节点(slave)每秒钟上报自身的复制偏移量给主节点，因此主节点也会保存 从节点的复制偏移量。从节点在接收到主节点发送的命令后，也会累加记录自身的偏移量。统计信 息在 info relication 中的 slave_repl_offset 指标中.

# 通过对比主从节点的复制偏移 量,可以判断主从节点数据是否一致。
```



#### 6.2.复制积压缓冲区

```json
# 复制积压缓冲区是保存在主节点上的一个固定长度的队列，默认大小为 1MB，
# 当主节点有连接的从节点(slave)时被创建，这时主节点( master)响应写命令时， 不但会把命令发送给从节点,还会写入复制积压缓冲区。 
# 由于缓冲区本质上是先进先出的定长队列，所以能实现保存最近已复制数据的功能，用于部分复制和复制命令丢失的数据补救。

# 复制缓冲区相关统计信息保存在主节点的 info replication 中: 
# repl_backlog_active: 1                     # 开启复制缓冲区 
# repl_backlog_size: 1048576                 # 缓冲区最大长度 
# repl_backlog_first_byte_offset: 7479       # 起始偏移量,计算当前缓冲区可用范围 
# repl_backlog_histlen: 1048576              # 已保存数据的有效长度。
```



#### 6.3.主节点运行ID

```json
# 每个 Redis 节点启动后都会动态分配一个 40 位的十六进制字符串作为运行 ID。
# 运行 ID 的主要作用是用来唯一识别 Redis 节点,比如从节点保存主节点的运行 ID 识别自己正在复制的是哪个主节点。

# 如果只使用 ip+port 的方式识别主节点, 那么主节点重启变更了整体数据集(如替换 RDB/AOF 文件),从节点再基于偏移量复制数据将是不安全的，因此当运行 ID 变化后从节点将做全量复制。
# 可以运行 info server 命令查看当前节点的运行 ID.

# run_id:798db8e5fdaae24133dc03931db8a00c6686e177 
# 需要注意的是 Redis 关闭再启动后，运行 ID 会随之改变。
```



#### 6.4.`psync` 命令

```shell
# 从节点使用 psync 命令完成部分复制和全量复制功能，命令格式: 

$ psync {runId} {offset}
# runId: 从节点所复制主节点的运行 id。
# offset: 当前从节点已复制的数据偏移量。

# 流程说明: 
# 1) 从节点(slave)发送 psync 命令给主节点，参数 runId 是当前从节点保存的主节点运行 ID,如果没有则默认值为?，参数 offset 是当前从节点保存的复制偏移量, 如果是第一次参与复制则默认值为-1。 
$ psync ? -1

# 2) 主节点(master)根据 psync 参数和自身数据情况决定响应结果: 
# 如果回复:+FULLRESYNC {runId} {offset},那么从节点将触发全量复制流程。 
# 如果回复: +CONTINUE，从节点将触发部分复制流程。

# 如果回复 +ERR,说明主节点版本低于 Redis 2.8，无法识别 psync 命令，从节点将发送旧版的 sync 命令触发全量复制流程。
```



#### 6.5.全量复制

```json
# 全量复制是 Redis 最早支持的复制方式,也是主从第一次建立复制时必须经 历的阶段。触发全量复制的命令是 sync 和 psync。
# psync 全量复制流程,它与 2.8 以前的 sync 全量复制机制基本一致。 

# 流程说明: 
# 1)发送 psync 命令进行数据同步，由于是第一次进行复制，从节点没有复制偏移量和主节点的运行 ID,所以发送 psync ? -1。 

# 2）主节点根据 psync ? -1 解析出当前为全量复制，回复 +FULLRESYNC 响应。 

# 3)从节点接收主节点的响应数据保存运行 ID 和偏移量 offset，并打印日志。 

# 4）主节点执行 bgsave 保存 RDB 文件到本地。 

# 5）主节点发送 RDB 文件给从节点，从节点把接收的 RDB 文件保存在本地并 直接作为从节点的数据文件,接收完 RDB 后从节点打印相关日志，可以在日志中 查看主节点发送的数据量。需要注意,对于数据量较大的主节点,比如生成的 RDB 文件超过 6GB 以上时 要格外小心。传输文件这一步操作非常耗时，速度取决于主从节点之间网络带宽， 通过细致分析 Full resync 和 MASTER<-> SLAVE 这两行日志的时间差，可以算出 RDB 文件从创建到传输完毕消耗的总时间。如果总时间超过 repl-timeout 所配置的值（默认 60 秒)，从节点将放弃接受 RDB 文件并清理已经下载的临时文件，导 致全量复制失败。 针对数据量较大的节点，建议调大 repl-timeout 参数防止出现全量同步数据超时。例如对于千兆网卡的机器，网卡带宽理论峰值大约每秒传输 100MB，在不考虑其他进程消耗带宽的情况下，6GB 的 RDB 文件至少需要 60 秒传输时间， 默认配置下，极易出现主从数据同步超时。 

# 6）对于从节点开始接收 RDB 快照到接收完成期间，主节点仍然响应读写命令，因此主节点会把这期间写命令数据保存在复制客户端缓冲区内，当从节点加载完 RDB 文件后，主节点再把缓冲区内的数据发送给从节点,保证主从之间数据 一致性。如果主节点创建和传输 RDB 的时间过长，对于高流量写入场景非常容 易造成主节点复制客户端缓冲区溢出。
# 默认配置为： client-output-buffer-limit slave 256MB 64MB 60 
# 意思是如果 60 秒内缓冲区消耗持续大于 64MB 或者直接超过 256MB 时，
# 主节点将直接关闭复制客户端连接，造成全量同步失败。 
# 对于主节点，当发送完所有的数据后就认为全量复制完成,打印成功日志，但是对于从节点全量复制依然没有完成，还有后续步骤需要处理。 

# 7)从节点接收完主节点传送来的全部数据后会清空自身旧数据 

# 8）从节点清空数据后开始加载 RDB 文件，对于较大的 RDB 文件，这一步操 作依然比较耗时。


# 9)从节点成功加载完 RDB 后，如果当前节点开启了 AOF 持久化功能,它会立 刻做 bgrewriteaof 操作，为了保证全量复制后 AOF 持久化文件立刻可用。

## 时间开销
# 主节点 bgsave 时间。 
# RDB 文件网络传输时间。 
# 从节点清空数据时间。 
# 从节点加载 RDB 的时间。 
# 可能的 AOF 重写时间。

# 经验表明：6G 内存 大约需要 2min 左右。
```



#### 6.6.部分复制

```json
# 部分复制主要是 Redis 针对全量复制的过高开销做出的一种优化措施,使用 
# $ psync {runId} {offset} 命令实现。
# 当从节点(slave)正在复制主节点(master)时，如果出现网络闪断或者命令丢失等异常情况时，从节点会向主节点要求补发丢失的命令数据，如果主节点的复制积压缓冲区内存在这部分数据则直接发送给从节点，这样就可以保持主从节点复制的一致性。补发的这部分数据一般远远小于全量数据,所以开销很小。 

# 流程说明： 
# 1) 当主从节点之间网络出现中断时，如果超过 repl-timeout 时间，主节点会认为从节点故障并中断复制连接,打印日志。
# 如果此时从节点没有宕机，也会打印与主节点连接丢失日志。 

# 2) 主从连接中断期间主节点依然响应命令，但因复制连接中断命令无法发送给从节点,不过主节点内部存在的复制积压缓冲区，依然可以保存最近一段时间的写命令数据，默认最大缓存 1MB。 

# 3) 当主从节点网络恢复后,从节点会再次连上主节点,打印日志。 

# 4) 当主从连接恢复后，由于从节点之前保存了自身已复制的偏移量和主节点的运行 ID。因此会把它们当作 psync 参数发送给主节点，要求进行部分复制操作。

# 5) 主节点接到 psync 命令后首先核对参数 runId 是否与自身一致,如果一致， 说明之前复制的是当前主节点;之后根据参数 offset 在自身复制积压缓冲区查找. 
# 如果偏移量之后的数据存在缓冲区中，则对从节点发送+CONTINUE 响应，表示可以进行部分复制。
# 如果不再，则退化为全量复制。


# 6) 主节点根据偏移量把复制积压缓冲区里的数据发送给从节点，保证主从复制进入正常状态。发送的数据量可以在主节点的日志，传递的数据远远小于全量数据。
```



#### 6.7.心跳

```json
# 主从节点在建立复制后,它们之间维护着长连接并彼此发送心跳命令。 
# 主从心跳判断机制: 
# 1) 主从节点彼此都有心跳检测机制，各自模拟成对方的客户端进行通信,通过 client list 命令查看复制相关客户端信息，主节点的连接状态为 flags=M，从节点 连接状态为 flags=S。 

# 2) 主节点默认每隔 10 秒对从节点发送 ping 命令，判断从节点的存活性和 连接状态。可通过参数 repl-ping-slave-period 控制发送频率。 

# 3) 从节点在主线程中每隔 1 秒发送 replconf ack {offset} 命令，给主节点上报自身当前的复制偏移量。replconf 命令主要作用如下: 
# 实时监测主从节点网络状态； 
# 上报自身复制偏移量,检查复制数据是否丢失,如果从节点数据丢失，再从主节点的复制缓冲区中拉取丢失数据;
# 主节点根据 replconf 命令判断从节点超时时间，体现在 info replication 统计 中的 lag 信息中，lag 表示与从节点最后一次通信延迟的秒数，正常延迟应该在 0 和 1 之间。如果超过 repl-timeout 配置的值(默认60秒)，则判定从节点下线并断开复制客户端连接。即使主节点判定从节点下线后,如果从节点重新恢复，心跳检测会继续进行。
```



#### 6.8.异步复制

```json
# 主节点不但负责数据读写，还负责把写命令同步给从节点。
# 写命令的发送过 程是异步完成,也就是说主节点自身处理完写命令后直接返回给客户端,并不等待从节点复制完成。 

# 由于主从复制过程是异步的，就会造成从节点的数据相对主节点存在延迟。 
# 具体延迟多少字节,我们可以在主节点执行 info replication 命令查看相关指标获得。

# 在统计信息中可以看到从节点 slave 信息，分别记录了从节点的 ip 和 port，从节点的状态，offset 表示当前从节点的复制偏移量，master_repl_offset 表示当前主节点的复制偏移量，两者的差值就是当前从节点复制延迟量。
# Redis 的复制速度取决于主从之间网络环境，repl-disable-tcp-nodelay，命令处理速度等。正常情况下，延迟在 1 秒以内。
```



#### 6.9.主从复制问题

```json
# 1、一旦主节点出现故障，需要手动将一个从节点晋升为主节点，同时需要 修改应用方的主节点地址，还需要命令其他从节点去复制新的主节点,整个过程 都需要人工干预。

# 2、主节点的写能力受到单机的限制。

# 3、主节点的存储能力受到单机的限制。
```





## 12.`Redis Sentinel ` 高可用性

> ​		Redis Sentinel 是一个分布式架构，其中包含若干个 Sentinel 节点和 Redis 数据节点，每个 Sentinel 节点会对数据节点和其余 Sentinel 节点进行监控，当它发现节点不可达时，会对节点做下线标识。如果被标识的是主节点，它还会和其他Sentinel 节点进行"协商"，当大多数 Sentinel 节点都认为主节点不可达时，它们会选举出一个 Sentinel 节点来完成自动故障转移的工作，同时会将这个变化实时通知给 Redis 应用方。
>
> ​		整个过程完全是自动的，不需要人工来介入，所以这套方案很有效地解决了 Redis 的高可用问题。 
>
> 注这里的分布式是指:
>
> Redis 数据节点、Sentinel 节点集合、客户端分布在多个物理节点的架构。

```json
# 下面以 1 个主节点、2 个从节点、3 个 Sentinel 节点组成的 Redis Sentinel 为例子进行说明

# 整个故障转移的处理逻辑有下面 4 个步骤: 
# 1) 主节点出现故障,此时两个从节点与主节点失去连接,主从复制失败。 
# 2) 每个 Sentinel 节点通过定期监控发现主节点出现了故障。 
# 3) 多个 Sentinel 节点对主节点的故障达成一致,选举出 sentinel-3 节点作为领导者负责故障转移。 
# 4) Sentinel 领导者节点执行了自动化故障转移，包括通知客户端，重新选择 主节点，建立新的复制关系等等。

# 通过上面介绍的Redis Sentinel逻辑架构以及故障转移的处理,可以看出Redis Sentinel 具有以下几个功能:

# 监控：Sentinel 节点会定期检测 Redis 数据节点、其余 Sentinel 节点是否可达。
# 通知: Sentinel 节点会将故障转移的结果通知给应用方。 
# 主节点故障转移：实现从节点晋升为主节点并维护后续正确的主从关系。 
# 配置提供者：在 Redis Sentinel 结构中，客户端在初始化的时候连接的是 Sentinel 节点集合，从中获取主节点信息。
```



```json
# 同时看到，Redis Sentinel 包含了若个 Sentinel 节点，这样做也带来了两个好处:
# 1.对于节点的故障判断是由多个 Sentinel 节点共同完成,这样可以有效地防止误判。
# 2.Sentinel 节点集合是由若干个 Sentinel 节点组成的,这样即使个别 Sentinel 节点不可用，整个 Sentinel 节点集合依然是健壮的。 

# 但是 Sentinel 节点本身就是独立的 Redis 节点，只不过它们有一些特殊，它们不存储数据，只支持部分命令。
```



### 1.部署建议

- 1） Sentinel 节点不应该部署在一台物理"机器"上。

- 2）部署至少三个且奇数个的 Sentinel 节点。

  - ```json
    # 3 个以上是通过增加 Sentinel 节点的个数提高对于故障判定的准确性，因为领导者选举需要至少一半加 1 个节点，奇数个节点可以在满足该条件的基础上节省一个节点。
    ```

  - 

- 3）只有一套 Sentinel，还是每个主节点配置一套 Sentinel ?

  - ```json
    # Sentinel 节点集合可以只监控一个主节点，也可以监控多个主节点。
    
    # 方案一，一套 Sentinel，很明显这种方案在一定程度上降低了维护成本，因 为只需要维护固定个数的 Sentinel 节点，集中对多个 Redis 数据节点进行管理就 可以了。但是这同时也是它的缺点，如果这套 Sentinel 节点集合出现异常，可能 会对多个 Redis 数据节点造成影响。还有如果监控的 Redis 数据节点较多，会造 成 Sentinel 节点产生过多的网络连接，也会有一定的影响。
    
    # 方案二，多套 Sentinel，显然这种方案的优点和缺点和上面是相反的，每个 Redis 主节点都有自己的 Sentinel 节点集合，会造成资源浪费。但是优点也很明显，每套 Redis Sentinel 都是彼此隔离的。 
    
    # 如果 Sentinel 节点集合监控的是同一个业务的多个主节点集合，那么使用方案一、否则一般建议采用方案二。
    ```

### 2.监控 `API`

```shell
$ sentinel masters
# 展示所有被监控的主节点状态以及相关的统计信息

$ sentinel master <host-name>
# 展示指定<host-name>的主节点状态以及相关的统计信息

$ sentinel slaves <host-name>
# 展示指定<host-name>的从节点状态以及相关的统计信息

$ sentinel sentinels <host-name>
# 展示指定<host-name>的 Sentinel 节点集合(不包含当前 Sentinel 节点)

$ sentinel get-master-addr-by-name <host-name>
# 返回指定<host-name>主节点的 IP 地址和端口

$ sentinel reset <pattern>
# 当前 Sentinel 节点对符合<pattern> (通配符风格)主节点的配置进行重置，包含清除主节点的相关状态(例如故障转移)，重新发现从节点和 Sentinel 节点。

$ sentinel failover <host-name>
# 对指定<host-name>主节点进行强制故障转移(没有和其他 Sentinel 节点"协商"),当故障转移完成后，其他 Sentinel 节点按照故障转移的结果更新自身配置， 这个命令在 Redis Sentinel 的日常运维中非常有用。

$ sentinel ckquorum <host-name>
# 检测当前可达的 Sentinel 节点总数是否达到<quorum>的个数。
# 例如 quorum = 3，而当前可达的 Sentinel 节点个数为 2 个，那么将无法进行故障转移， Redis Sentinel 的高可用特性也将失去。

$ sentinel flushconfig
# 将 Sentinel 节点的配置强制刷到磁盘上，这个命令 Sentinel 节点自身用得比较多，对于开发和运维人员只有当外部原因(例如磁盘损坏)造成配置文件损坏 或者丢失时,这个命令是很有用的

$ sentinel remove <host-name>
# 取消当前 Sentine 节点对于指定<host-name>主节点的监控。

$ sentinel monitor <host-name> <ip><port> <quorum>
# 这个命令和配置文件中的含义是完全一样的,只不过是通过命令的形式来完 成 Sentinel 节点对主节点的监控。

$ sentinel set <host-name>
# 动态修改 Sentinel 节点配置选项。

$ sentinel is-master-down-by-addr
# Sentinel 节点之间用来交换对主节点是否下线的判断，根据参数的不同，还可以作为 Sentinel 领导者选举的通信方式
```



### 3.实现原理

```json
# 实现原理
# Redis Sentinel 的基本实现中包含以下： 
# Redis Sentinel 的定时任务;
# 主观下线和客观下线;
# Sentinel 领导者选举;
# 故障转移等.
```

#### 3.1.三个定时监控任务

```json
# 1) 每隔 10 秒，每个 Sentinel 节点会向主节点和从节点发送 info 命令获取最新的拓扑结构，Sentinel 节点通过对上述结果进行解析就可以找到相应的从节点。

# 2) 每隔 2 秒,每个 Sentinel 节点会向 Redis 数据节点的__sentinel__:hello 频道上发送该 Sentinel 节点对于主节点的判断以及当前 Sentinel 节点的信息，同时每个 Sentinel 节点也会订阅该频道，来了解其他 Sentinel 节点以及它们对主节点的判断,所以这个定时任务可以完成以下两个工作: 
# 1.发现新的 Sentinel 节点:
# 通过订阅主节点的__sentinel__:hello 了解其他的 Sentinel 节点信息，如果是新加入的 Sentinel 节点，将该 Sentinel 节点信息保存起来,并与该 Sentinel 节点创建连接。 
# 2.Sentinel 节点之间交换主节点的状态，作为后面客观下线以及领导者选举的依据。

# 3) 每隔 1 秒，每个 Sentinel 节点会向主节点、从节点、其余 Sentinel 节点 发送一条 ping 命令做一次心跳检测，来确认这些节点当前是否可达。
```



#### 3.2.主观下线

```json
# 每个 Sentinel 节点会每隔 1 秒对主节点、 从节点、其他 Sentinel 节点发送 ping 命令做心跳检测,当这些节点超过 down-after-milliseconds 没有进行有效回复，Sentinel 节点就会对该节点做失败判 定，这个行为叫做主观下线。

# 从字面意思也可以很容易看出主观下线是当前 Sentinel 节点的一家之言,存在误判的可能。
```



#### 3.3.客观下线

```json
# 当 Sentinel 主观下线的节点是主节点时，该 Sentinel 节点会通过 
# sentinel is-master-down-by-addr 命令向其他 Sentinel 节点询问对主节点的判断，当超过 <quorum> 个数,Sentinel 节点认为主节点确实有问题，这时该 Sentinel 节点会做出客观下线的决定，这样客观下线的含义是比较明显了，也就是大部分 Sentinel 节 点都对主节点的下线做了同意的判定，那么这个判定就是客观的。
```



#### 3.4.`Sentinel` 领导选举

```json
# Redis 使用了 Raft 算法实现领导者选举
# 大致思路如下:
# 1) 每个在线的 Sentinel 节点都有资格成为领导者，当它确认主节点主观下线时候，会向其他 Sentinel 节点发送 sentinel is-master-down-by-addr 命令，要求将自己设置为领导者。 
# 2) 收到命令的 Sentinel 节点，如果没有同意过其他 Sentinel 节点的 sentinel is-master-down-by-addr 命令,将同意该请求,否则拒绝。
# 3) 如果该 Sentinel 节点发现自己的票数已经大于等于 
# max (quorum，num(sentinels)/2+1）,那么它将成为领导者。 
# 4) 如果此过程没有选举出领导者,将进入下一次选举。 

# 选举的过程非常快,基本上谁先完成客观下线,谁就是领导者。
```



#### 3.5.故障转移

```json
# 领导者选举出的 Sentinel 节点负责故障转移，具体步骤如下:

# 1) 在从节点列表中选出一个节点作为新的主节点,选择方法如下:
## a) 过滤"不健康"(主观下线、断线)、5 秒内没有回复过 Sentinel 节点 ping 响应、与主节点失联超过 down-after-milliseconds * 10 秒。 
## b) 选择 slave-priority (从节点优先级)最高的从节点列表，如果存在则返回,不存在则继续。 
## c) 选择复制偏移量最大的从节点(复制的最完整)，如果存在则返回,不存在则继续。
## d) 选择 runid 最小的从节点。

# 2) Sentinel 领导者节点会对第一步选出来的从节点执行 slaveof no one 命令 让其成为主节点。

# 3) Sentinel 领导者节点会向剩余的从节点发送命令，让它们成为新主节点的从节点,复制规则和 parallel-syncs 参数有关。 

# 4) Sentinel 节点集合会将原来的主节点更新为从节点，并保持着对其关注，当其恢复后命令它去复制新的主节点。
```



## 13.`Redis Cluster` 集群

> Redis Cluster 是 Redis 的分布式解决方案，在 3.0 版本正式推出，有效地解决了 Redis 分布式方面的需求。当遇到单机内存、并发、流量等瓶颈时，可以采用 Cluster 架构方案达到负载均衡的目的。

### 1.数据分布

- 数据分布理论

  - 哈希分区

    - 离散度好

    - 数据分布业务无关

    - 无法顺序访问

    - 分区规则:

      - 节点取余分区

        - ```json
          # 使用特定的数据，如 Redis 的键或用户 ID，再根据节点数量 N 使用公式: hash(key)%N 计算出哈希值，用来决定数据映射到哪一个节点上。这种方案存在一个问题:当节点数量变化时，如扩容或收缩节点，数据节点映射关系需要重新计算，会导致数据的重新迁移。
          ```

        - 

      - 一致性哈希分区

        - ```json
          # 加入和删除节点只影响哈希环中相 邻的节点，对其他节点无影响。
          
          #问题:
          # 1.加减节点会造成哈希环中部分数据无法命中，需要手动处理或者忽略这部分数据，因此一致性哈希常用于缓存场景。
          # 2.当使用少量节点时，节点变化将大范围影响哈希环中数据映射，因此这种方 式不适合少量数据节点的分布式方案。
          # 3.增加节点只能对下一个相邻节点有比较好的负载分担效果，类似地，删除节点会导致下一个相邻节点负载增加，而其他节点却不能有效分担负载压力。
          ```

        - 

      - 虚拟一致性哈希分区

        - ```json
          # 为了在增删节点的时候，各节点能够保持动态的均衡，将每个真实节点虚拟 出若干个虚拟节点，再将这些虚拟节点随机映射到环上。此时每个真实节点不再 映射到环上，真实节点只是用来存储键值对，它负责接应各自的一组环上虚拟节点。当对键值对进行存取路由时，首先路由到虚拟节点上，再由虚拟节点找到真实的节点。
          
          # 每个真实节点所负责的 hash 空间不再是连续的一段，而是分散在环上的各处，这样就可以将局部的压力均衡到不同的节点，虚拟节点越多，分散性越好，理论上负载就越倾向均匀。
          ```

        - 

      - 虚拟槽分区

        - ```json
          # Redis 则是利用了虚拟槽分区，可以算上面虚拟一致性哈希分区的变种，它使用分散度良好的哈希函数把所有数据映射到一个固定范围的整数集合中，整数定义为槽(slot)。这个范围一般远远大于节点数，比如RedisCluster 槽范围是 0～16383。槽是集群内数据管理和迁移的基本单位。采用大范围槽的主要目的是为了方便数据拆分和集群扩展。每个节点会负责一定数量的槽。
          
          # 比如集群有 5 个节点，则每个节点平均大约负责 3276 个槽。由于采用高质 量的哈希算法，每个槽所映射的数据通常比较均匀，将数据平均划分到 5 个节点进行数据分区。
          
          # Redis 主节点的配置信息中，它所负责的哈希槽是通过一张 bitmap 的形 式来保存的，在传输过程中，会对 bitmap 进行压缩，但是如果 bitmap 的填充率 slots / N 很高的话(N 表示节点数)，也就是节点数很少，而哈希槽数量很多的话， bitmap 的压缩率就很低，也会浪费资源。
          ```

        - 

  - 顺序分区

    - 离散度易倾斜
    - 数据分布业务相关
    - 可顺序访问

### 2.数据分区

> Redis Cluser 采用虚拟槽分区，所有的键根据哈希函数映射到 0 ~16383 整数槽内，计算公式:slot=CRC16(key) &16383。每一个节点负责维护―部分槽以及槽所映射的键值数据。

- 虚拟槽分区的特点

  - ```json
    # Redis 虚拟槽分区的特点:
    # 1.解耦数据和节点之间的关系,简化了节点扩容和收缩难度。
    # 2.节点自身维护槽的映射关系，不需要客户端或者代理服务维护槽分区元数据。 支持节点、槽、键之间的映射查询,用于数据路由、在线伸缩等场景。
    ```

- 集群限制

  - ```json
    # key 批量操作支持有限。如 mset、mget，目前只支持具有相同 slot 值的 key 执行批量操作。对于映射为不同 slot 值的 key 由于执行 mget、mget 等操作可能存在于多个节点上因此不被支持。
    ```

  - ```json
    # key 事务操作支持有限。同理只支持多 key 在同一节点上的事务操作，当 多个 key 分布在不同的节点上时无法使用事务功能。
    ```

  - ```json
    # key 作为数据分区的最小粒度，因此不能将一个大的键值对象如 hash、list 等映射到不同的节点。
    ```

  - ```json
    # 不支持多数据库空间。单机下的 Redis 可以支持 16 个数据库，集群模式 下只能使用一个数据库空间,即 db 0。
    ```

  - ```json
    # 复制结构只支持一层，从节点只能复制主节点，不支持嵌套树状复制结构
    
    ```

  - 

### 3.`Smart` 客户端

> 大多数开发语言的 Redis 客户端都采用 Smart 客户端支持集群协议。Smart 客户端通过在内部维护 slot →node 的映射关系，本地就可实现键到节点的查找， 从而保证 IO 效率的最大化，而 MOVED 重定向负责协助 Smart 客户端更新 slot → node 映射。我们以 Java 的 Jedis 为例,说明 Smart 客户端操作集群的流程。

```json
# 我们以 Java 的 Jedis 为例,说明 Smart 客户端操作集群的流程

# 1）首先在 JedisCluster 初始化时会选择一个运行节点，初始化槽和节点映射 关系，使用 cluster slots 命令完成。

# 2 ) JedisCluster 解析 cluster slots 结果缓存在本地，并为每个节点创建唯一的 JedisPool 连接池。映射关系在 JedisClusterInfoCache 类中。
# 3 ) JedisCluster 执行键命令的过程有些复杂，键命令执行流程:
#  a) 计算 slot 并根据 slots 缓存获取目标节点连接,发送命令。
#  b) 如果出现连接错误，使用随机连接重新执行键命令，每次命令重试对 redi-rections 参数减 1。
#  c) 捕获到 MOVED 重定向错误，使用 cluster slots 命令更新 slots 缓存 (renewslotCache 方法)
#  d) 重复执行 1)~3)步，直到命令执行成功，或者当 redi-rections<=0 时抛出 JedisClusterMaxRedirectionsException 异常。
```



### 4.`ASK ` 重定向

```json
# 客户端 ASK 重定向流程

# Redis 集群支持在线迁移槽(slot)和数据来完成水平伸缩，当 slot 对应的数据从源节点到目标节点迁移过程中，客户端需要做到智能识别，保证键命令可正 常执行。例如当一个 slot 数据从源节点迁移到目标节点时，期间可能出现一部分 数据在源节点，而另一部分在目标节点。
# 当出现上述情况时,客户端键命令执行流程将发生变化:

# 1) 客户端根据本地 slots 缓存发送命令到源节点，如果存在键对象则直接执行并返回结果给客户端。 

# 2) 如果键对象不存在，则可能存在于目标节点，这时源节点会回复 ASK 重 定向异常。格式如下:(error) ASK {slot} {targetIP}:{targetPort}。

# 3) 客户端从 ASK 重定向异常提取出目标节点信息，发送 asking 命令到目标节点打开客户端连接标识，再执行键命令。如果存在则执行,不存在则返回不存在信息。

# ASK 与 MOVED 虽然都是对客户端的重定向控制，但是有着本质区别。ASK 重定向说明集群正在进行 slot 数据迁移，客户端无法知道什么时候迁移完成，因 此只能是临时性的重定向，客户端不会更新 slots 缓存。但是 MOVED 重定向说明键对应的槽已经明确指定到新的节点,因此需要更新 slots 缓存。
```



### 5.`Gossip`消息

```json
# Gossip 协议的主要职责就是信息交换。信息交换的载体就是节点彼此发送的 Gossip 消息，了解这些消息有助于我们理解集群如何完成信息交换。 
# 常用的 Gossip 消息可分为:
# ping 消息、pong 消息、meet 消息、fail 消息等。

# meet 消息:用于通知新节点加入。消息发送者通知接收者加入到当前集群， meet 消息通信正常完成后，接收节点会加入到集群中并进行周期性的 ping、pong 消息交换。 

# ping 消息:集群内交换最频繁的消息，集群内每个节点每秒向多个其他节点 发送 ping 消息,用于检测节点是否在线和交换彼此状态信息。ping 消息发送封装了自身节点和部分其他节点的状态数据。 

# pong 消息:当接收到 ping、meet 消息时，作为响应消息回复给发送方确认消息正常通信。pong 消息内部封装了自身状态数据。节点也可以向集群内广播自身的 pong 消息来通知整个集群对自身状态进行更新。 

# fail 消息:当节点判定集群内另一个节点下线时，会向集群内广播一个 fail 消息,其他节点接收到 fail 消息之后把对应节点更新为下线状态。

# Redis 集群内节点通信采用固定频率(定时任务每秒执行 10 次)。

# 消息交换的成本主要体现在单位时间选择发送消息的节点数量和每个消息 携带的数据量。

# 1.选择发送消息的节点数量
# 集群内每个节点维护定时任务默认间隔 1 秒，每秒执行 10 次，定时任务里 每秒随机选取 5 个节点，找出最久没有通信的节点发送 ping 消息，用于保证 Gossip 信息交换的随机性。同时每 100 毫秒都会扫描本地节点列表，如果发现节点最近一次接受 pong 消息的时间大于 cluster_node_timeout/2，则立刻发送 ping 消息，防止该节点信息太长时间未更新。

# 根据以上规则得出每个节点每秒需要发送 ping 消息的数量
# count = 1 + 10 * num(node.pong_received > cluster_node_timeout/2)，
# 因此 cluster_node_timeout 参数对消息发送的节点数量影响非常大。当我们的带宽资源紧张时，可以适当调大这个参数，如从默认 15 秒改为 30 秒来降低带宽占用率。过度调大 cluster_node_timeout 会影响消息交换的频率从而影响故障转移、槽信息更新、新节点发现的速度。因此需要根据业务容忍度和资源消耗进行平衡。同时整个集群消息总交换量也跟节点数成正比。

# ⒉消息数据量
# 每个 ping 消息的数据量体现在消息头和消息体中,其中消息头主要占用空间的字段是 
# myslots [CLUSTER_SLOTS/8]，占用 2KB，这块空间占用相对固定。消息体会携带一定数量的其他节点信息用于信息交换。 
# 根消息体携带数据量跟集群的节点数息息相关，更大的集群每次消息通信的成本也就更高，因此对于 Redis 集群来说并不是大而全的集群更好。
```



### 6.故障发现

```json
# 当集群内某个节点出现问题时，需要通过一种健壮的方式保证识别出节点是 否发生了故障。Redis 集群内节点通过 ping/pong 消息实现节点通信，消息不但 可以传播节点槽信息，还可以传播其他状态如:主从状态、节点故障等。因此故 障发现也是通过消息传播机制实现的,主要环节包括:
# 1.主观下线(pfail):
# 指某个节点认为另一个节点不可用，即下线状态，这个状态并不是最终的故障判定,只能代表一个节点的意见,可能存在误判情况。

# 2.客观下线(fail):
# 指标记一个节点真正的下线，集群内多个节点都认为该节点不可用,从而达成共识的结果。如果是持有槽的主节点故障，需要为该节点进行故障转移。
```

#### 6.1.主观下线

```json
# 集群中每个节点都会定期向其他节点发送 ping 消息，接收节点回复 pong 消息作为响应。
# 如果在 cluster-node-timeout 时间内通信一直失败,则发送节点会认为接收节点存在故障，把接收节点标记为主观下线(pfail)状态。

# 流程说明:
# 1) 节点 a 发送 ping 消息给节点 b，如果通信正常将接收到 pong 消息，节点 a 更新最近一次与节点 b 的通信时间。 

# 2) 如果节点 a 与节点 b 通信出现问题则断开连接，下次会进行重连。如果一直通信失败,则节点 a 记录的与节点 b 最后通信时间将无法更新。 

# 3) 节点 a 内的定时任务检测到与节点 b 最后通信时间超高 cluster-node-timeout 时，更新本地对节点 b 的状态为主观下线(pfail)。 

# 主观下线: 简单来讲就是，当 cluster-note-timeout 时间内某节点无法与另一个节点顺利完成 ping 消息通信时，则将该节点标记为主观下线状态。每个节点内的 clusterstate 结构都需要保存其他节点信息,用于从自身视角判断其他节点的状态。
```



#### 6.2.客观下线

```json
# 当某个节点判断另一个节点主观下线后，相应的节点状态会跟随消息在集群内传播。

# ping/pong 消息的消息体会携带集群 1/10 的其他节点状态数据，当接受节点 发现消息体中含有主观下线的节点状态时，会在本地找到故障节点的 ClusterNode 结构，保存到下线报告链表中。

# 通过 Gossip 消息传播，集群内节点不断收集到故障节点的下线报告。当半 数以上持有槽的主节点都认为某个节点是主观下线时。触发客观下线流程。这里有两个问题: 

# 1) 为什么必须是负责槽的主节点参与故障发现决策?
# 因为集群模式下只有处理槽的主节点才负责读写请求和集群槽等关键信息维护，而从节点只进行主节点数据和状态信息的复制。 

# 2) 为什么半数以上处理槽的主节点?
# 必须半数以上是为了应对网络分区等原因造成的集群分割情况，被分割的小集群因为无法完成从主观下线到客观下线这一关键过程，从而防止小集群完成故障转移之后继续对外提供服务。

# 尝试客观下线
# 集群中的节点每次接收到其他节点的 pfail 状态，都会尝试触发客观下线。
# 流程说明: 
# 1) 首先统计有效的下线报告数量,如果小于集群内持有槽的主节点总数的一半则退出。 
# 2) 当下线报告大于槽主节点数量一半时，标记对应故障节点为客观下线状态。
# 3) 向集群广播一条 fail 消息，通知所有的节点将故障节点标记为客观下线,fail 消息的消息体只包含故障节点的 ID。 

# 广播 fail 消息是客观下线的最后一步,它承担着非常重要的职责: 
# 1.通知集群内所有的节点标记故障节点为客观下线状态并立刻生效。 
# 2.通知故障节点的从节点触发故障转移流程。
```



#### 6.3.故障回复

```json
# 故障节点变为客观下线后,如果下线节点是持有槽的主节点则需要在它的从 节点中选出一个替换它,从而保证集群的高可用。下线主节点的所有从节点承担 故障恢复的义务，当从节点通过内部定时任务发现自身复制的主节点进入客观下 线时,将会触发故障恢复流程。

# 1.资格检查
# 每个从节点都要检查最后与主节点断线时间，判断是否有资格替换故障的主 节点。如果从节点与主节点断线时间超过 cluster-node-time * cluster-slave-validity-factor，
# 则当前从节点不具备故障转移资格。
# 参数 cluster-slave-validity-factor 用于从节点的有效因子，默认为 10。

# 2.准备选举时间
# 当从节点符合故障转移资格后，更新触发故障选举的时间，只有到达该时间后才能执行后续流程。
# 这里之所以采用延迟触发机制，主要是通过对多个从节点使用不同的延迟选举时间来支持优先级问题。
# 复制偏移量越大说明从节点延迟越低，那么它应该具有更高的优先级来替换故障主节点。 
# 所有的从节点中复制偏移量最大的将提前触发故障选举流程，以保证复制延迟低的从节点优先发起选举。

# 3.发起选举
# 当从节点定时任务检测到达故障选举时间(failover_auth_time) 到达后，发起选举流程如下:
# 3.1.更新配置纪元
# 配置纪元是一个只增不减的整数，每个主节点自身维护一个配置纪元 (clusterNode.configEpoch)标示当前主节点的版本，所有主节点的配置纪元都不相等，从节点会复制主节点的配置纪元。
# 整个集群又维护一个全局的配置纪元(clusterstate.currentEpoch)，用于记录集群内所有主节点配置纪元的最大版本。 
# 执行 cluster info 命令可以查看配置纪元信息.
# 配置纪元的主要作用:
# 1.标示集群内每个主节点的不同版本和当前集群最大的版本。
# 2.每次集群发生重要事件时，这里的重要事件指出现新的主节点(新加入的或者由从节点转换而来)，从节点竞争选举。都会递增集群全局的配置纪元并赋值给相关主节点,用于记录这一关键事件。
# 主节点具有更大的配置纪元代表了更新的集群状态，因此当节点间进行 ping/pong 消息交换时，如出现 slots 等关键信息不一致时，以配置纪元更大的一方为准，防止过时的消息状态污染集群。

# 配置纪元的应用场景有:
# 新节点加入。
# 槽节点映射冲突检测。
# 从节点投票选举冲突检测。

# 4.选举投票
# 只有持有槽的主节点才会处理故障选举消息(FAILOVER_AUTH_REQUEST)，因为每个持有槽的节点在一个配置纪元内都有唯一的一张选票，当接到第一个请求 投票的从节点消息时回复 FAILOVER_AUTH_ACK 消息作为投票，之后相同配置纪元内其他从节点的选举消息将忽略。 
# 投票过程其实是一个领导者选举的过程，如集群内有 N 个持有槽的主节点 代表有 N 张选票。由于在每个配置纪元内持有槽的主节点只能投票给一个从节 点，因此只能有一个从节点获得 N/2+1 的选票,保证能够找出唯一的从节点。
# -- 为什么是持有槽的主节点参与投票?
# Redis 集群没有直接使用从节点进行领导者选举，主要因为从节点数必须大于等于 3 个才能保证凑够 N/2+1 个节点，将导致从节点资源浪费。使用集群内所有持有槽的主节点进行领导者选举,即使只有一个从节点也可以完成选举过程。
# 当从节点收集到 N/2+1 个持有槽的主节点投票时，从节点可以执行替换主节点操作。
# 例如集群内有 5 个持有槽的主节点，主节点 b 故障后还有 4 个，当其中一个从节点收集到 3 张投票时代表获得了足够的选票可以进行替换主节点操作。 

# 投票作废:每个配置纪元代表了一次选举周期,如果在开始投票之后的 cluster-node-timeout*2 时间内从节点没有获取足够数量的投票，则本次选举作废。从节点对配置纪元自增并发起下一轮投票,直到选举成功为止。

# 5.替换主节点
# 当从节点收集到足够的选票之后,触发替换主节点操作:
# 1) 当前从节点取消复制变为主节点。
# 2) 执行 clusterDelslot 操作撤销故障主节点负责的槽，并执行 clusterAddSlot 把这些槽委派给自己。
# 3) 向集群广播自己的 pong 消息，通知集群内所有的节点当前从节点变为主节点并接管了故障主节点的槽信息。
```



#### 6.4.故障转移时间

```json
# 1) 主观下线(pfail）识别时间=cluster-node-timeout。
# 2) 主观下线状态消息传播时间<=cluster-node-timeout/2。消息通信机制对超 过 cluster-node-timeout/2 未通信节点会发起 ping 消息，消息体在选择包含哪些 节点时会优先选取下线状态节点，所以通常这段时间内能够收集到半数以上主节 点的 pfail 报告从而完成故障发现。
# 3) 从节点转移时间<=1000 毫秒。由于存在延迟发起选举机制,偏移量最大的 从节点会最多延迟 1 秒发起选举。通常第一次选举就会成功，所以从节点执行转 移时间在 1 秒以内。

# 根据以上分析可以预估出故障转移时间，如下: 
# failover-time(毫秒) ≤ cluster-node-timeout + cluster-node-timeout/2 + 1000 
# 因此，故障转移时间跟 cluster-node-timeout 参数息息相关，
# 默认 15 秒。配置时可以根据业务容忍度做出适当调整，但不是越小越好。
```



#### 6.5.集群不可用判定

```json
# 为了保证集群完整性，默认情况下当集群 16384 个槽任何一个没有指派到节 点时整个集群不可用。执行任何键命令返回(error)CLUSTERDOWN Hash slot not served 错误。这是对集群完整性的一种保护措施，保证所有的槽都指派给在线的节点。
# 但是当持有槽的主节点下线时，从故障发现到自动完成转移期间整个集群是不可用状态，对于大多数业务无法容忍这种情况，因此可以将参数 cluster-require-full-coverage 配置为 no，当主节点故障时只影响它负责槽的相关命令执行，不会影响其他主节点的可用性。 

# 但是从集群的故障转移的原理来说，集群会出现不可用，当： 
# 1、当访问一个 Master 和 Slave 节点都挂了的时候， cluster-require-full-coverage=yes，会报槽无法获取。 
# 2、集群主库半数宕机(根据 failover 原理，fail 掉一个主需要一半以上主都 投票通过才可以)。 
# 另外，当集群 Master 节点个数小于 3 个的时候，或者集群可用节点个数为偶数的时候，基于 fail 的这种选举机制的自动主从切换过程可能会不能正常工作，一个是标记 fail 的过程，一个是选举新的 master 的过程，都有可能异常。
```



## 14.`Redis` 缓存

### 1.数据一致性

> 只要使用到缓存，无论是本地内存做缓存还是使用 redis 做缓存，那么就会存在数据同步的问题。 
>
> > 先读缓存数据，缓存数据有，则立即返回结果；如果没有数据，则从数据库读数据，并且把读到的数据同步到缓存里，提供下次读请求返回数据。

> 数据不一致问题--解决方案

- 更新类
  - 先更新缓存，后更新数据库 （不考虑）

  - 先更新数据库，后更新缓存 （不考虑）

  - ```json
    # 说到底是选择更新缓存还是淘汰缓存呢，主要取决于"更新缓存的复杂度"， 
    # 更新缓存的代价很小，此时我们应该更倾向于更新缓存，以保证更高的缓存命中率，
    # 更新缓存的代价很大，此时我们应该更倾向于淘汰缓存。
    
    # 但是淘汰缓存操作简单，并且带来的副作用只是增加了一次 cache miss，所以一般作为通用的处理方式。
    ```

  - 

- 删除类

  - 先删除缓存，后更新数据库

  - ```json
    # 延时双删策略 
    # 1.先淘汰缓存
    # 2.再写数据库
    # 3.休眠 N 秒，再次淘汰缓存
    # 自行评估自己的项目的读数据业务逻辑的耗时。
    # 然后写数据的休眠时间则在读数据业务逻辑的耗时基础上加几百 ms 即可。
    # 这么做的目的，就是确保读请求结束，写请求可以删除读请求造成的缓存脏数据。
    
    # 其实，在这期间-读数据造成的缓存脏数据-也导致了数据不一致的问题
    # A 写 B 读并且更新脏数据
    # 在 Ns 内 C 读到了 B 的脏数据缓存
    
    # 在读写分离的场景下主从同步之间也会有时间差
    # 解决方案
    # 1.还是使用双删延时策略。只是，睡眠时间修改为在主从同步的延时时间基础上，加几百 ms。
    # 2.就是如果是对 Redis 进行填充数据的查询数据库操作，那么就强制将其指向主库进行查询
    
    # 第二次-删除操作在高吞吐的场景下可以改为: 异步删除
    ```

  - 先更新数据库，后删除缓存 

  - ```json
    # 这种方式，被称为 Cache Aside Pattern，读的时候，先读缓存，缓存没有的话，就读数据库，然后取出数据后放入缓存，同时返回响应。更新的时候，先更新数据库，然后再删除缓存。
    # 并发问题复现:
    # 一个请求 A 做查询操作，一个请求 B 做 更新操作
    # 1.缓存刚好失效
    # 2.请求 A 查询数据库，得一个旧值
    # 3.请求 B 将新值写入数据库
    # 4.请求 B 删除缓存
    # 5.请求 A 将查到的旧值写入缓存
    
    # 有一个先决条件: 更新操作比查询操作更快
    # 但是 读操作 远远快于写操作，所以,情况很难出现。
    
    -- -
    # 解决方案
    # 1.给缓存设有效时间是一种方案
    # 2.采用异步延时删除策略
    # 更新数据库成功了，但是在删除缓存的阶段出错了没有删除成功怎么办?
    # -- 对应的解决方案
    # -- 1.利用消息队列进行删除的补偿
    # ---- 1.请求 A 先对数据库进行更新操作
    # ---- 2.在对 Redis 进行删除操作的时候发现报错，删除失败
    # ---- 3.此时将 Redis 的 key 作为消息体发送到消息队列中
    # ---- 4.系统接收到消息队列发送的消息
    # ---- 5.再次对 Redis 进行删除操作
    # -- 2.基于 MySQL binlog 实现对缓存的操作
    # ---- 1.请求 A 先对数据库进行更新操作
    # ---- 2.在对 Redis 进行删除操作的时候发现报错，删除失败
    # ---- 3.订阅 binlog 将删除消息发送到消息队列
    # ---- 4.系统接收到消息队列发送的消息
    # ---- 5.再次对 Redis 进行删除操作
    ```

### 2.缓存更新的设计模式

- `Cache aside`

  - ```json
    # 具体逻辑如下：
    # 失效：应用程序先从 cache 取数据，没有得到，则从数据库中取数据，成功 后，放到缓存中。
    # 命中：应用程序从 cache 中取数据，取到后返回。
    # 更新：先把数据存到数据库中，成功后，再让缓存失效。
    
    # 这种模式下，没有了删除 cache 数据的操作了，而是先更新了数据库中的数据，此时，缓存依然有效，所以，并发的查询操作拿的是没有更新的数据，但是，更新操作马上让缓存的失效了，后续的查询操作再把数据从数据库中拉出来不会存在后续的查询操作一直都在取老的数据。
    ```

  - 

- `Read through`

  - ```json
    # Read Through 套路就是在查询操作中更新缓存，也就是说，当缓存失效的时候(过期或 LRU 换出)，Cache Aside 是由调用方负责把数据加载入缓存，而 Read Through 则用缓存服务自己来加载，从而对应用方是透明的。
    ```

  - 

- `Write through`

  - ```json
    # Write Through 套路和 Read Through 相仿，不过是在更新数据时发生。
    # 当有数据更新的时候，如果没有命中缓存，直接更新数据库，然后返回。
    # 如果命中了 缓存，则更新缓存，然后再由 Cache 自己更新数据库。
    ```

  - 

- `Write behind caching`

  - ```json
    # Write Behind 又叫 Write Back。Linux 文件系统的 Page Cache 也是同样算法。
    # Write Back 套路，一句说就是，在更新数据的时候，只更新缓存，不更新数 据库，而我们的缓存会异步地批量更新数据库。这个设计的好处就是让数据的 I/O 操作飞快无比，因为异步，write back 还可以合并对同一个数据的多次操作，所 以性能的提高是相当可观的。
    
    # 但是，其带来的问题是，数据不是强一致性的，而且可能会丢失。
    ```

  - 

### 3.缓存穿透、击穿、雪崩

- 缓存穿透

  - ```json
    # 是指查询一个根本不存在的数据，缓存层和存储层都不会命中，如果从存储层查不到数据则不写入缓存层。
    
    # 缓存穿透将导致不存在的数据每次请求都要到存储层去查询，失去了缓存保护后端存储的义。
    
    # 通常可以在程序中分别统计总调用数、缓存层 命中数、存储层命中数，如果发现大量存储层空命中，可能就是出现了缓存穿透问题。
    
    # 下如何解决缓存穿透问题?
    # 1.缓存空对象
    # 当存储层不命中后，仍然将空对象保留到缓存层中，之后再访问这个数据将会从缓存中获取,这样就保护了后端数据源。
    # 缓存空对象会有两个问题:
    # 第一，空值做了缓存，意味着缓存层中存了更多的键，需要更多的内存空间(如果是攻击，问题更严重),比较有效的方法是针对这类数据设置一个较短的过期时间，让其自动剔除。
    # 第二，缓存层和存储层的数据会有一段时间窗口的不一致，可能会对业务有一定影响。
    # -- 例如过期时间设置为 5 分钟，如果此时存储层添加了这个数据，那此段时间就会出现缓存层和存储层数据的不一致，此时可以利用前面所说的数据一致性方案处理。
    
    # 2.布隆过滤器拦截
    # 在访问缓存层和存储层之前,将存在的 key 用布隆过滤器提前保存起来,做第一层拦截。
    # 例如:一个推荐系统有 4 亿个用户id，每个小时算法工程师会根据每个用户之前历史行为计算出推荐数据放到存储层中,但是最新的用户由于没有历史行为,就会发生缓存穿透的行为,为此可以将所有推荐数据的用户做成布隆过滤器。
    # 如果布隆过滤器认为该用户 id 不存在,那么就不会访问存储层,在一定程度保护了存储层。 
    # 这种方法适用于数据命中不高、数据相对固定、实时性低(通常是数据集较大)的应用场景,代码维护较为复杂,但是缓存空间占用少。
    ```

  - 

- 缓存击穿

  - ```json
    # 缓存击穿是指一个热点 Key，大并发集中对这一个点进行访问，当这个 Key 在失效的瞬间，持续的大并发就穿破缓存，直接请求数据库。 
    # 解决缓存击穿的
    # 1.设置热点数据永远不过期。
    # -- 这里的“永远不过期”包含两层意思
    # -- 从 redis 上看，确实没有设置过期时间，这就保证了，不会出现热点 key 过期问题，也就是“物理”不过期。
    # -- 从功能上看，如果不过期，那不就成静态的了吗？所以我们把过期时间存在 key 对应的 value 里，如果发现要过期了，通过一个后台的异步线程进行缓 存的构建，也就是"逻辑"过期。
    # 2.加上互斥锁。
    ```

  - 

- 缓存雪崩

  - ```json
    # 由于缓存层承载着大量请求,有效地保护了存储层,但是如果缓存层由于某些原因不能提供服务，比如同一时间缓存数据大面积失效，那一瞬间 Redis 跟没有一样，于是所有的请求都会达到存储层，存储层的调用量会暴增，造成存储层也会级联宕机的情况。
    # 缓存雪崩的英文原意是 stampeding herd(奔逃的野牛)，指的是缓存层宕掉后，流量会像奔逃的野牛一样,打向后端存储。
    
    # 预防和解决缓存雪崩问题,可以从以下三个方面进行着手。
    # 1) 保证缓存层服务高可用性。和飞机都有多个引擎一样，如果缓存层设计成高可用的,即使个别节点、个别机器、甚至是机房宕掉，依然可以提供服务。 
    # 2) 依赖隔离组件为后端限流并降级。无论是缓存层还是存储层都会有出错的概率，可以将它们视同为资源。作为并发量较大的系统，假如有一个资源不可用，可能会造成线程全部阻塞(hang)在这个资源上，造成整个系统不可用。降级机制在高并发系统中是非常普遍的。 
    # 3) 提前演练。在项目上线前，演练缓存层宕掉后，应用以及后端的负载情况以及可能出现的问题,在此基础上做一些预案设定。 
    # 4) 将缓存失效时间分散开，比如我们可以在原有的失效时间基础上增加一个随机值，比如 1-5 分钟随机，这样每一个缓存的过期时间的重复率就会降低，就很难引发集体失效的事件。 
    
    # 缓存雪崩和缓存击穿的区别在于缓存击穿针对某一 key 缓存，缓存雪崩则是 很多 key。
    ```

### 4.热点`Key`

```json
# 危害
# 1.流量集中，达到物理网卡上限
# 2.请求过多，缓存分片服务被打垮
# 3.DB 击穿，引起业务雪崩

## 造成 访问量倾斜
```



### 5.`BigKey`

```json
# bigkey 的危害
# 1.内存空间不均匀;
# 2.超时阻塞
# 3.网络拥塞

# $ redis-cli --bigkeys 可以命令统计 bigkey 的分布
# $ debug object key 查看 serializedlength 属性即可，它表示 key 对应的 value 序列化之后的字节数。

## 造成 数据量倾斜
```

