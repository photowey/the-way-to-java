# `Spring`

## 1.`Design pattern`

- `Exclusive`
  - `Prefix`
    - `Enable`
    - `Configuarable`
  - `Suffix`
    - `Processor`
      - `Processor`
      - `Resolver`
      - `Handler`
    - `Aware`
    - `Configuror`
    - `Selector`
      - `ImportSelector`

- `traditional`
  - `Create`
    - `Abstract Factory`
    - `Buidler`
    - `Factory Method`
    - `Prototype`
    - `Signleton`
  - `Structure`
    - `Adapter`
    - `Bridge`
    - `Composite`
    - `Decorator`
    - `Facade`
    - `Flyweight`
    - `Proxy`
  - `Behavior`
    - `Chain of Responsibility`
    - `Command`
    - `Interceptor`
    - `Iterator`
    - `Mediator`
    - `Memento`
    - `Observer`
    - `State`
    - `Strategy`
    - `Template Method`
    - `Visitor`
    
## 2.`Actuator`

2.1.`health`

```http
http://192.168.217.1:7923/actuator/health
```



2.2.`info`

```http
http://192.168.217.1:7923/actuator/info
```

```yml
# Custom the /actuator/info endpoint
info:
  app:
    name: @project.artifactId@
    encoding: @project.build.sourceEncoding@
    java:
      source: @java.version@
      target: @java.version@
```



2.3.`beans`

```http
http://192.168.217.1:7923/actuator/beans
```

