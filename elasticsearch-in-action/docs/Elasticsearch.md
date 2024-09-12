# `Elasticsearch`

> 1.`filter`: 不计算评分; 查询效率高; 有缓存;  - (推荐)
>
> - term: 根据词条精确匹配；
>
> - match: 模糊匹配(全文检索), 倒排索引；
>
> 2.`must`: 要计算评分; 查询效率低; 无缓存；
>
> - term: 精确匹配 , 要评分；
> - match: 模糊匹配(全文检索), 要评分；
>
> 3.`bool`: 组合查询
>
> - `must`: 必须匹配每个子查询
> - `should`: 选择性匹配子查询
> - `must_not`: 必须不匹配, 不参与算分
> - `filter`: 必须匹配, 不参与算分

## 0.接口

### 0.1.查看

```shell
# 查看单节点的 shard 分配整体情况
GET /_cat/allocation

# 查看各 shard 的详细情况
GET /_cat/shards

# 查看指定分片的详细情况
GET /_cat/shards/{index}

# 查看 master 节点信息
GET /_cat/master

# 查看所有节点信息
GET /_cat/nodes

# 查看集群中所有 index 的详细信息
GET /_cat/indices

# 查看集群中指定 index 的详细信息
GET /_cat/indices/{index}

# 查看各 index 的 segment 详细信息,包括 segment 名,所属 shard,内存(磁盘)占用大小,是否刷盘
GET /_cat/segments

# 查看指定 index 的 segment 详细信息
GET /_cat/segments/{index}

# 查看当前集群的 doc 数量
GET /_cat/count

# 查看指定索引的 doc 数量
GET /_cat/count/{index}

# 查看集群内每个 shard 的 recovery 过程,调整 replica
GET /_cat/recovery

# 查看指定索引 shard 的 recovery 过程
GET /_cat/recovery/{index}

# 查看集群当前状态: 红、黄、绿
GET /_cat/health

# 查看当前集群的 pending task
GET /_cat/pending_tasks

# 查看集群中所有 alias 信息,路由配置等
GET /_cat/aliases

 # 查看指定索引的 alias 信息
GET /_cat/aliases/{alias}

# 查看集群各节点内部不同类型的 threadpool 的统计信息
GET /_cat/thread_pool

# 查看集群各个节点上的 plugin 信息
GET /_cat/plugins

# 查看当前集群各个节点的 fielddata 内存使用情况
GET /_cat/fielddata

# 查看指定 field 的内存使用情况,里面传 field 属性对应的值
GET /_cat/fielddata/{fields}

# 查看单节点的自定义属性
GET /_cat/nodeattrs

# 输出集群中注册快照存储库
GET /_cat/repositories

# 输出当前正在存在的模板信息
GET /_cat/templates
```

### 0.2.集群

```shell
# 用于获取集群的健康状态,返回的状态信息包括集群是否绿色green/yellow/red,以及集群的节点数、数据分片等信息
GET /_cluster/health

# 提供整个集群的统计信息,包括索引、节点、分片等的详细信息
GET /_cluster/stats

# 返回集群的状态信息,包含集群的元数据、路由表、分片信息等.这个接口非常详细,用于调试和分析集群状态
GET /_cluster/state

# 获取和更新集群级别的设置,你可以通过这个接口查看当前的动态设置,也可以用来修改某些配置项
GET /_cluster/settings

# 显示集群当前等待执行的任务,这些任务通常包括分片的分配、索引的创建等
GET /_cluster/pending_tasks

# 手动触发集群的分片重新分配,可以指定分片的移动、复制等操作
GET /_cluster/reroute

# 解释为什么某些分片不能分配到节点上,对于集群故障排查和优化分片分配策略非常有帮助
GET /_cluster/allocation/explain

# 用于查看节点的 "热点" 线程,显示节点上正在消耗大量 CPU 的线程信息
GET /_cluster/nodes/hot_threads

# 用于排除投票节点,通常用于集群升级或者节点移除场景
POST /_cluster/voting_config_exclusions
DELATE /_cluster/voting_config_exclusions
```



## 1.`match`

`match` 在匹配时会对所查找的关键词进行分,然后按分词匹配查找,而 `term` 会直接对关键词进行查找

- 模糊查找
  - match

- 精确查找
  - term

### 1.1.`match_all`

### 1.2.`match`

- `operator`

  - ```json
    GET /hello/_search
    {
      "query": {
        "match": {
          "address": {
            "query": "广州白云山公园",
            "operator": "AND"
          }
        }
      }
    }
    ```

  - 

- `minimum_should_match`

  - 最小匹配数

  - ```json
    GET /hello/_search
    {
      "query": {
        "match": {
          "address": {
            "query": "广州公园",
            "minimum_should_match": 2
          }
        }
      }
    }
    ```

  - 

### 1.3.`match_phrase`

> 短语

- `slop`

  - `match_phrase` 查询词条相隔多远时仍然能够将文档视为匹配

  - ```json
    GET /hello/_search
    {
      "query": {
        "match_phrase": {
          "address": "广州白云山"
        }
      }
    }
    
    GET /hello/_search
    {
      "query": {
        "match_phrase": {
          "address": "广州白云"
        }
      }
    }
    
    GET /hello/_search
    {
      "query": {
        "match_phrase": {
          "address": {
            "query": "广州云山",
            "slop": 2
          } 
        }
      }
    }
    ```

  - 

### 1.4.`multi_match`

- **最佳字段(Best Fields)**
  - 当字段之间相互竞争，又相互关联。
  - 例如: 对于博客的 title和 body这样的字段，评分来自最匹配字段

- **多数字段(Most Fields)**
  - 处理英文内容时的一种常见的手段是，在主字段( English Analyzer)，抽取词干，加入同义词，以匹配更多的文档。
  - 相同的文本，加入子字段（Standard Analyzer），以提供更加精确的匹配。
  - 其他字段作为匹配文档提高相关度的信号，匹配字段越多则越好。

- **混合字段(Cross Field)**
  - 对于某些实体，例如人名，地址，图书信息。
  - 需要在多个字段中确定信息，单个字段只能作为整体的一部分。
  - 希望在任何这些列出的字段中找到尽可能多的词

- 多词条

  - ```json
    GET /hello/_search
    {
      "query": {
        "multi_match": {
          "query": "重庆火锅",
          "fields": [
            "address", // 重庆
            "name"     // 火锅
          ]
        }
      }
    }
    ```

  - ```json
    POST /blogs/_search
    {
      "query": {
        "multi_match": {
          "type": "best_fields", // 最佳匹配
          "query": "Quick pets",
          "fields": [
            "title",
            "body"
          ],
          "tie_breaker": 0.2    // Tie Breaker 是一个介于 0-1 之间的浮点数。0: 代表使用最佳匹配; 1: 代表所有语句同等重要。
        }
      }
    }
    ```
  
  - ```json
    DELETE /titles
    PUT /titles
    {
      "mappings": {
        "properties": {
          "title": {
            "type": "text",
            "analyzer": "english",
            "fields": {
              "std": {
                "type": "text",
                "analyzer": "standard"
              }
            }
          }
        }
      }
    }
    
    POST /titles/_bulk
    { "index": { "_id": 1 }}
    { "title": "My dog barks" }
    { "index": { "_id": 2 }}
    { "title": "I see a lot of barking dogs on the road " }
    
    # 结果与预期不匹配
    GET /titles/_search
    {
      "query": {
        "match": {
          "title": "barking dogs"
        }
      }
    }
    
    GET /titles/_search
    {
      "query": {
        "multi_match": {
          "query": "barking dogs",
          "type": "most_fields",
          "fields": [
            "title",
            "title.std" // 添加子字段
          ]
        }
      }
    }
    
    # 增加 title 的权重
    GET /titles/_search
    {
      "query": {
        "multi_match": {
          "query": "barking dogs",
          "type": "most_fields",
          "fields": [
            "title^10",
            "title.std"
          ]
        }
      }
    }
    ```
  
  - ```json
    # ---- cross_fields
    
    DELETE /address
    PUT /address
    {
        "settings" : {
            "index" : {
                "analysis.analyzer.default.type": "ik_max_word"
            }
        }
    }
    
    PUT /address/_bulk
    { "index": { "_id": "1"} }
    {"province": "湖南","city": "长沙"}
    { "index": { "_id": "2"} }
    {"province": "湖南","city": "常德"}
    { "index": { "_id": "3"} }
    {"province": "广东","city": "广州"}
    { "index": { "_id": "4"} }
    {"province": "湖南","city": "邵阳"}
    
    # 使用 most_fields 的方式结果不符合预期,支持 operator
    GET /address/_search
    {
      "query": {
        "multi_match": {
          "query": "湖南常德",
          "type": "most_fields",
          "fields": [
            "province",
            "city"
          ]
        }
      }
    }
    
    # 可以使用 cross_fields,支持 operator
    # 与 copy_to 相比,其中一个优势就是它可以在搜索时为单个字段提升权重
    GET /address/_search
    {
      "query": {
        "multi_match": {
          "query": "湖南常德",
          "type": "cross_fields",
          "operator": "and",
          "fields": [
            "province",
            "city"
          ]
        }
      }
    }
    ```
  
  - 

### 1.5.`query_string`

- 允许我们在单个查询字符串中指定 `AND | OR | NOT` 条件，同时也和 `multi_match` `query` 一样,支持多字段搜索

- 和 `match` 类似,但是 `match` 需要指定字段名 `query_string` 是在所有字段中搜索,范围更广泛

  - ```json
    GET /hello/_search
    {
      "query": {
        "query_string": {
          "default_field": "address",
          "query": "白云山 OR 橘子洲"
        }
      }
    }
    
    GET /hello/_search
    {
      "query": {
        "query_string": {
          "fields": [
            "name",
            "address"
          ],
          "query": "张三 OR (广州 AND 王五)"
        }
      }
    }
    ```

### 1.6.`simple_query_string`

类似 `Query String`,但是会忽略错误的语法,同时只支持部分查询语法,不支持 `AND OR NOT`,会当作字符串处理。

- \+ 替代 AND

- | 替代 OR

- \- 替代 NOT

  - ```json
    # simple_query_string 默认的 operator 是 OR
    GET /hello/_search
    {
      "query": {
        "simple_query_string": {
          "fields": [
            "name",
            "address"
          ],
          "query": "广州公园",
          "default_operator": "AND"
        }
      }
    }
    
    GET /hello/_search
    {
      "query": {
        "simple_query_string": {
          "fields": [
            "name",
            "address"
          ],
          "query": "广州 + 公园"
        }
      }
    }
    ```

  - 

### 1.7.`term`

`term` 用来使用关键词查询(精确匹配),还可以用来查询没有被进行分词的数据类型

- `ES` 中默认使用分词器为标准分词器(`StandardAnalyzer)`,标准分词器对于英文单词分词,对于中文单字分词

- `ES` 的 `Mapping Type` 中  `keyword,date,integer,long,double,boolean or ip` 这些类型不分词,只有 `text` 类型分词

  - ```json
    
    # 搜索不到 - 没有 广州白云 的分词
    GET /hello/_search
    {
      "query": {
        "term": {
          "address": {
            "value": "广州白云"
          }
        }
      }
    }
    
    # 搜索到 - 有 白云山 的分词
    GET /hello/_search
    {
      "query": {
        "term": {
          "address": {
            "value": "白云山"
          }
        }
      }
    }
    
    # keyword
    # match
    GET /hello/_search
    {
      "query": {
        "match": {
          "address.keyword": {
            "query": "广州白云山公园"
          }
        }
      }
    }
    
    # term
    GET /hello/_search
    {
      "query": {
        "term": {
          "address.keyword": {
            "value": "广州白云山公园"
          }
        }
      }
    }
    
    # term -> 不分词属性 -> 可以不写 {} 👇
    GET /hello/_search
    {
      "query": {
        "term": {
          "age": 28
        }
      }
    }
    
    GET /hello/_search
    {
      "query": {
        "term": {
          "age": {
            "value": 28
          }
        }
      }
    }
    ```

  - 

### 1.8.`constant_score`

可以通过 `Constant Score` 将查询转换成一个 `Filtering`,避免算分,并利用缓存,提高性能

- 将 `Query` 转成 `Filter`,忽略 `TF-IDF` 计算,避免相关性算分的开销

- `Filter` 可以有效利用缓存

  - ```json
    GET /hello/_search
    {
      "query": {
        "constant_score": {
          "filter": {
            "term": {
              "address.keyword": "广州白云山公园"
            }
          }
        }
      }
    }
    ```

  - 

### 1.9.`prefix`

原理：需要遍历所有倒排索引，并比较每个 `term` 是否已所指定的前缀开头。

它会对分词后的 `term` 进行前缀搜索。

- 它不会分析要搜索字符串,传入的前缀就是想要查找的前缀

- 默认状态下,前缀查询不做相关度分数计算,它只是将所有匹配的文档返回,然后赋予所有相关分数值为 1

- 它的行为更像是一个过滤器而不是查询

- 两者实际的区别就是过滤器是可以被缓存的,而前缀查询不行

  - ```json
    GET /hello/_search
    {
      "query": {
        "prefix": {
          "address": {
            "value": "广州"
          }
        }
      }
    }
    ```

  - 

### 1.10.`wildcard`

- 工作原理和 prefix 相同,只不过它不是只比较开头,它能支持更为复杂的匹配模式

  - ```json
    # wildcard
    GET /hello/_search
    {
      "query": {
        "wildcard": {
          "address": {
            "value": "*白*"
          }
        }
      }
    }
    ```

  - 

### 1.11.`ids`

```json
GET /hello/_search
{
  "query": {
    "ids": {
      "values": [
        1,
        2
      ]
    }
  }
}

```



### 1.12.`range`

- `gte` 大于等于

- `lte`  小于等于

- `gt` 大于

- `lt` 小于

- now 当前时间

  - ```json
    GET /hello/_search
    {
      "query": {
        "range": {
          "age": {
            "gte": 25,
            "lte": 28
          }
        }
      }
    }
    ```

  - ```json
    GET /product/_search
    {
      "query": {
        "range": {
          "date": {
            "gte": "now-2y"
          }
        }
      }
    }
    
    ```

  - 

### 1.13.`fuzzy`

在实际的搜索中，我们有时候会打错字，从而导致搜索不到。在 `Elasticsearch` 中,我们可以使用 `fuzziness` 属性来进行模糊查询,从而达到搜索有错别字的情形。

`fuzzy` 查询会用到两个很重要的参数 `fuzziness`,`prefix_length`

- 注意
  - fuzzy 模糊查询,最大模糊错误必须在 0-2 之间
  - 搜索关键词长度为 2,不允许存在模糊
  - 搜索关键词长度为 3-5,允许 1 次模糊
  - 搜索关键词长度大于 5,允许最大 2 次模糊

- fuzziness
  
- 表示输入的关键字通过几次操作可以转变成为 `ES` 库里面的对应 `field` 的字段
  
- - 操作是指：

    - 新增一个字符
    - 删除一个字符
    - 修改一个字符
    - 每次操作可以记做编辑距离为 1

  - 如 中文集团 到 中闻集团 编辑距离就是 1,只需要修改一个字符

  - 该参数默认值为0,即不开启模糊查询

  - 如果 `fuzziness` 值在这里设置成2,会把编辑距离为 2 的 东东集团 也查出来

  - ```json
    GET /hello/_search
    {
      "query": {
        "fuzzy": {
          "address": {
            "value": "白运山",
            "fuzziness": 1
          }
        }
      }
    }
    
    GET /hello/_search
    {
      "query": {
        "match": {
          "address": {
            "query": "广洲",
            "fuzziness": 1
          }
        }
      }
    }
    ```

  - 

- prefix_length
  
- 表示限制输入关键字和 `ES` 对应查询 `field` 的内容开头的第 n 个字符必须完全匹配,不允许错别字匹配
  
- - 如这里等于 1,则表示开头的字必须匹配,不匹配则不返回
  - 默认值也是 0
  - 加大 prefix_length 的值可以提高效率和准确率
  - 

### 1.14.`highlight`

- `pre_tags` 前缀标签

- `post_tags` 后缀标签

- `tags_schema` 设置为 `styled` 可以使用内置高亮样式

- `require_field_match` 多字段高亮需要设置为 false

  - 针对查询字段

  - ```json
    GET /products/_search
    {
      "query": {
        "term": {
          "name": {
            "value": "牛仔"
          }
        }
      },
      "highlight": {
        "fields": {
          "*": {}
        }
      }
    }
    
    # ---- 自定义高亮标签
    
    GET /products/_search
    {
      "query": {
        "term": {
          "name": {
            "value": "牛仔"
          }
        }
      },
      "highlight": {
        "pre_tags": [
          "<span style='color:red'>"
        ],
        "post_tags": [
          "</span>"
        ],
        "fields": {
          "*": {}
        }
      }
    }
    
    # ---- 多字段高亮
    
    GET /products/_search
    {
      "query": {
        "term": {
          "name": {
            "value": "牛仔"
          }
        }
      },
      "highlight": {
        "pre_tags": [
          "<font color='red'>"
        ],
        "post_tags": [
          "<font/>"
        ],
        "require_field_match": "false",
        "fields": {
          "name": {},
          "desc": {}
        }
      }
    }
    ```

-- -

**相关性和相关性算分**

搜索是用户和搜索引擎的对话,用户关心的是搜索结果的相关性

- 是否可以找到所有相关的内容
- 有多少不相关的内容被返回了
- 文档的打分是否合理
- 结合业务需求,平衡结果排名

如何衡量相关性：

- Precision(查准率 - 尽可能返回较少的无关文档
- Recall(查全率) - 尽量返回较多的相关文档
- Ranking - 是否能够按照相关度进行排序

-- -

## 2.`Bool`

一个 `bool` 查询,是一个或者多个查询子句的组合,总共包括4种子句,其中 2 种会影响算分,2 种不影响算分。

- must
  - 相当于 `&&` 
  - 必须匹配 - 贡献算分
- should
  - 相当于 `||` 
  - 选择性匹配 - 贡献算分
- must_not
  - 相当于 `!`  
  - 必须不能匹配 - 不贡献算分
- filter
  - 必须匹配 - 不贡献算法

在 `Elasticsearch` 中，有 `Query` 和 `Filter` 两种不同的 `Context`

- Query Context

  - 相关性算分

- Filter Context

  - 不需要算分,可以利用 `Cache`, 获得更好的性能

  

相关性并不只是全文本检索的专利，也适用于 `yes | no` 的子句，匹配的子句越多，相关性评分越高。

如果多条查询子句被合并为一条复合查询语句, 比如 `bool` 查询，则每个查询子句计算得出的评分会被合并到总的相关性评分中。

`bool` 查询语法

- 子查询可以任意顺序出现
- 可以嵌套多个查询
- 如果 `bool` 查询中,没有 `must` 条件, `should` 中必须至少满足一条查询

```json
GET /hello/_search
{
  "query": {
    "bool": {
      "must": {
        "match": {
          "remark": "java developer"
        }
      },
      "filter": {
        "term": {
          "sex": "1"
        }
      },
      "must_not": {
        "range": {
          "age": {
            "gte": 30
          }
        }
      },
      "should": [
        {
          "term": {
            "address.keyword": {
              "value": "广州天河公园"
            }
          }
        },
        {
          "term": {
            "address.keyword": {
              "value": "广州白云山公园"
            }
          }
        }
      ],
      "minimum_should_match": 1
    }
  }
}

# ---- should not

GET /hello/_search
{
  "query": {
    "bool": {
      "must": {
        "match": {
          "remark": "java developer"
        }
      },
      "should": [
        {
          "bool": {
            "must_not": [
              {
                "term": {
                  "sex": 1
                }
              }
            ]
          }
        }
      ],
      "minimum_should_match": 1
    }
  }
```

-- -

## 3.`aggs`

> 聚合查询



-- -

## 4.集群架构

分布式系统特点

- 高可用
  - 服务可用性 
    - 允许又节点停止服务
  - 数据可用性
    - 部分节点丢失,不会丢失数据
- 可扩展
  - 请求量的提升
  - 数据的不断增长
    - 将数据分布到所有的节点上

`ES` 集群架构优势

- 提供系统的可用性,部分节点停止服务,整个集群的服务不受影响

- 存储水平扩展

- 核心概念

  - 集群

    - 一个集群可以有一个和多个节点
    - 不同的集群通过不同的名称来区分,默认名称: `elasticsearch`
    - 通过配置文件修改,或者在命令行中进行设定
      - `-E cluster.name=es`

  - 节点

    - 节点是一个 `Elasticsearch` 实例
      - 本质是一个 `Java` 进程
      - 一个机器上可以运行多个 `Elasticsearch` 进程,生产环境一般建议一台机器是运行一个 `Elasticsearch` 实例
    - 每一个节点都有名字,通过配置文件配置或启动参数 `-E node.name=nodeN` 指定
    - 每一个节点启动后,会分配一个 `UID`,保存在 `data` 目录

  - 节点类型

    - `Master`

      - 主节点
      - `node.master=true`
      - 处理创建,删除索引等请求,负责索引的创建和删除
      - 决定分片被分配到哪个节点
      - 维护和更新 `Cluster State`
      - 最佳实践
        - `Master` 节点非常重要,在部署上需要解决单节点的问题
        - 为一个集群设置多个 `Master` 节点,每个接待你只承担 `Master` 的单一角色

    - `Master eligible nodes`

      - 合格节点

      - 可参与选举的节点

      - 每个节点启动后,默认是一个 `Master eligibale` 节点

      - 可以设置 `node.master=false` 禁止

      - `Master eligibale` 节点可以参加选择流程,成为 `Master` 节点

      - 当第一个节点启动的时候,它会将自己选举为 `Master` 节点

      - 每个节点上都保存了集群的状态,只有 `Master` 节点才能修改集群的状态。

      - 集群状态
        - 所有的节点信息
        - 所有的索引和其他相关的 `Mapping` 和 `Setting` 信息
        - 分片的路由信息

    - `Data node`

      - 数据节点
      - 可以保存数据的节点,负责保存分片数据,在数据扩展上起到了至关重要的作用
      - 节点启动后,默认就是数据节点,可以设置 `node.data=false` 禁止
      - 通过增加数据节点可以解决数据水平扩展和解决数据单节点问题

    - `Coordinating Node`

      - 协调节点(不存储数据)
      - 负责接受 `Client` 的请求,将请求分发到合适的节点,最终把结果汇集到一起
      - 每个节点默认都起到了 `CN` 的职责

    - 其他节点

      - `Hot & Warm Node`
        - 不同经验配置的 `Data Node`, 用来实现 `Hot & Warm` 架构,降低集群部署的成本
      - `Ingest Node`
        - 可通过 `node.ingest=true` 指定
        - 数据前置处理转换节点
        - 支持 `Pipeline` 管道设置,可以使用 `Ingest` 对数据进行过滤,转换等操作
      - `Machine Learning Node`
        - 负责跑机器学习的 `Job`, 用来做异常检测
      - `Tribe Node`
        - 连接到不同的 `Elasticsearch` 集群,并且支持将这些集群当成一个单独的集群处理

- 分片

  - 主分片
    - `Primary Shard`
    - 用以解决数据水平扩展问题,通过主分片,可以将数据分布到集群给的所有节点上
    - 一个分片是一个运行的 `Lucene` 实例
    - 主分片数在索引创建的时候指定,后续不允许修改,除非 `Reindex`
  - 副本分片
    - `Replica Shard`
    - 用以解决数据高可用问题,副分片是主分片的拷贝
    - 副分片数可以动态调整
    - 增加副本数,还可以在一定程度上提高服务的可用性(读取的吞吐)
  - 分片的设定
    - 分片数量过少
      - 导致后续无法增加接待你实现水平扩展
      - 单个分片数据量太大,导致数据重新分配耗时
    - 分片数量过多
      - 影响搜索结果的相关性打分,影响统计结果的准确性
      - 单个节点上过多分片,会导致资源的浪费,同时也会影响性能

### 建模优化

- 只是聚合不搜索的字段,不索引
  - index=false
- 不要对紫都城使用默认的 `dynamic mapping`, 字段过多,会对性能产生比较大的影响
- 使用 `index_options ` 控制在创建倒排索引时,哪些内容会被添加到倒排索引中
- 如果追求极致的写入速度,可以牺牲数据的可靠性及搜索的即时性换取
  - 牺牲可靠性
    - 将副本分片设置为: 0
    - 写入完成后再调整回去
    - 修改 `translog` 配置
  - 牺牲搜索即时性
    - 增加 `refresh interval` 的时间
      - 默认 `1s`, -1: 禁止自动刷新
      - 避免过于频繁的刷新而生成过多的 `segment` 文件
      - 降低搜索的实时性
        - 搜索的时 `commit file` 的内容
    - 增大静态配置参数
      - `indices.memory.index_buffer_size`
        - 默认: `10%` -> 导致自动触发刷新
- 降低 `translog` 写磁盘的频率,但是会降低容灾能力
  - `index.translog.durability`
    - 默认: `request` -> 每个请求都会落盘
    - 设置成 `async` 异步写入
  - `index.translog.sync_interval`
    - 设置为 `60s`
  - `index.translog.flush_threshold_size`
    - 默认: `512M`
    - 适当增大 -> 超过触发刷新
- 分片设定
  - 副本在写入时设置为 0, 完成后再增加
  - 合理设置主分片数,确保均匀分配再所有的数据节点上
  - `index.routing.allocation.total_share_per_node`
    - 限定每个索引在每个节点上可分配的主分片数
- 调整 `Bulk` 线程池和队列
  - 客户端
    - 单个 `bulk` 请求体的数量不要太大,建议 `5-15M`
    - 写入端的 `bulk` 请求超时时间需要足够长,建议 `60s` 以上
    - 写如端尽量将数据轮询打到不同的节点上
  - 服务端 
    - 索引创建属于计算密集型任务,应该使用固定大小的线程池来配置
      - 来不及处理的放入到任务队列
      - `core == NCPU + 1`, 避免过多的上下文切换
    - 队列大小可以适当增加,不要过大<否则占用的内存会成为 `GC` 的负担

-- -

## 5.高阶

-- -

