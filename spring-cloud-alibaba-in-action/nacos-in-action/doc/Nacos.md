# `Nacos`

> `2.x`



## 1.`Registry`

### 1.1.`Component`

#### 1.1.1.`ServiceInfoUpdateService`

定时查询注册中心最新数据并更新本地缓存 (`UpdateTask`) | (`ServiceInfoHolder`)

- 默认
  - 6s
  - `delayTime = serviceObj.getCacheMillis() * DEFAULT_UPDATE_CACHE_TIME_MULTIPLE;`
- 最大
  - 60s
  - `executor.schedule(this, Math.min(delayTime << failCount, DEFAULT_DELAY * 60), TimeUnit.MILLISECONDS);`

#### 1.1.2.`ConnectionManager`

连接管理, 3s(`fixed`) 一次进行心跳检测,当超过 20s(`KEEP_ALIVE_TIME`) 后执行异步探活,异常或 1s 无响应则剔除

- `ConnectionBasedClientManager`

  - `remove`
  - `ClientDisconnectEvent`
    - `ClientServiceIndexesManager`
      - 删除注册表
        - `removeSubscriberIndexes(each, client.getClientId());`
      - 删除订阅表
        - `removePublisherIndexes(each, client.getClientId());`
    - `DistroClientDataProcessor`
      - 同步集群
        - `distroProtocol.sync(distroKey, DataOperation.DELETE);`

  

  #### 1.1.3.`ClientServiceIndexesManager`

  服务注册与订阅

  - 服务变更
    - `ServiceChangedEvent`
      - 推送所有(新增)
  - 订阅变更
    - `ServiceSubscribedEvent`
      - 推送指定(新增)



## 2.`Config`

### 2.1.`NacosConfigBootstrapConfiguration`

> 自动配置

#### 2.1.1.`NacosConfigProperties`

#### 2.1.2.`NacosConfigManager`

#### 2.1.3.`NacosPropertySourceLocator`

- `locate`
  - `Shared`
  - `Extension`
  - `Application`
    - `dataid`
    - `dataid.suffix`
    - `dataid-${profile}.suffix`

#### 2.1.4.`ConfigurationPropertiesRebinder`

### 2.2.`NacosConfigAutoConfiguration`

#### 2.2.1.`NacosConfigProperties`

#### 2.2.2.`NacosRefreshHistory`

#### 2.2.3.`NacosConfigManager`

- `ConfigFactory#createConfigService`

- `ClientWorker`
  - `ConfigRpcTransportClient`
    - `#start`
      - `#startInternal`
        - `listenExecutebell.poll(5L, TimeUnit.SECONDS);`
          - 如果有推送,立即拉取最新
          - 没推送 -> 超时 -> 至多 5s -> 再次拉去最新

#### 2.2.4.`NacosContextRefresher`

- `ApplicationReadyEvent`

#### 2.2.5.`ConfigurationPropertiesRebinder`



## 3.`Summary`

### 3.1.`Config`

- `Client`
- `Server`

### 3.2.`Registry`

- `Client`
  - `registerInstance`
  - `selectInstances`
  - `NotifySubscriberRequest`
- `Server`
  - `com.alibaba.nacos.naming.remote.rpc.handler.InstanceRequestHandler`
    - `ClientServiceIndexesManager#publisherIndexes`
  - `com.alibaba.nacos.naming.remote.rpc.handler.SubscribeServiceRequestHandler`
    - `ClientServiceIndexesManager#subscriberIndexes`
  - `com.alibaba.nacos.naming.remote.rpc.handler.ServiceQueryRequestHandler`

