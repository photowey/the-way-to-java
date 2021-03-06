# `HashMap-in-action`
> 学习 `HashMap` 的心得

## 1.解决哈希碰撞

- 重哈希
- 开放地址法
- 公共溢出区
- 链地址法



## 2.确实产生了哈希碰撞

```
如果: 确实产生了 Hash 碰撞                                                                   
1.扩容                                                                                
1.1.什么时候扩容?                                                                         
---- 先添加元素                                                                          
---- 判断是否达到阈值                                                                       
1.2.怎么扩容?                                                                           
---- 添加元素使用尾插法                                                                     
---- 如果对应角标位是单向链表,将单向链表进行数据迁移
---- 如果对应角标位是红黑树,将双向链表进行数据迁移    
---- -- 如果发现迁移完数据后,双向链表的结构小于/等于6,会将红黑树重新转回单向链表的结构        
---- 扩容后,数据迁移时,数据要么在原来位置,要么在原来位置+扩容长度
1.3.扩容后有什么问题(多线程环境)?                                                             
---- 多线程环境下会有数据丢失的问题
2.数据结构                                                                              
2.1.会形成单向链表                                                                         
---- 如果单向链表的长度大于|等于8,会转成红黑树+双向链表(扩容时使用)      
```



## 3.容量必须是2的幂次方数

```
为什么容量必须是2的幂次方数呢?                                                                                           
1.以2的幂次方数有一个特点,高位为1,后续全部为0,这样的数减一,就会变成刚才为1的位置为0,后续所有值都为1,这样减一之后的数,和任何数进行与运算,得到的结果,永远是0-2的幂次方减一,正好符合数组角标的范围 
-- 数学取模运算 10 % 16 == 10 & 15                                                                               
2.同时减一后,一定是一个奇数,末位一定是1,那么和其他数进行与运算后,得到的结果可能是奇数,也可能是偶数,那么可以充分利用数组的容量

3.2的幂次方数减一后,低位都是1,这样数组的索引位都有可能存入元素,如果低位不都是1,就会导致有些数组的索引位永远空缺,不利于数组的充分利用  

4.扩容时,便于重新定位元素的索引位,扩容的原则是原来数组的2倍,那么扩容后,数组容量还是一个2的幂次方数,原数组中的元素在新数组中,要么在原始索引位,要么在原始索引位+扩容值的位置,避免了重新hash的效率问题 
```



## 4.`put` 流程

### 4.1.`HashMap`

> 大致流程

![](.\doc\hashmap.jpg)

> Resize()

![](.\doc\resize.jpg)

### 4.2.`ConcurrentHashMap`

> 大致流程
>
> ...



## 5.树

- 节点、根节点、父节点、子节点、兄弟节点
- 一棵树可以没有任何一个节点，称为<font style="color:red">空树</font>
- 一棵树可以只有一个节点,也就是只有根节点
- 子树、左子树、右子树
- ========================================
- 节点的<font style="color:red">度</font>: 子树的个数
- 树的<font style="color:red">度</font>: 所有节点度中的最大值
- 叶子节点: <font style="color:red">度</font>为0的节点
- 非叶子节点: <font style="color:red">度</font>不为0的节点
- ========================================
- <font style="color:red">层数(level)</font>: 根节点在第一层，根节点的子节点在第二层，以此类推
- 节点的<font style="color:red">深度(depth)</font>: 从根节点到当前节点的<font style="color:red">唯一</font>路径的节点总数
- 节点的<font style="color:red">高度(height)</font>: 从当前节点到<font style="color:red">最远</font>叶子节点的路径上的节点总数
- 树的<font style="color:red">深度</font>: 所有节点深度中的<font style="color:red">最大值</font>
- 树的<font style="color:red">高度</font>: 所有节点高度中的<font style="color:red">最大值</font>
- 树的<font style="color:red">深度</font> == 树的<font style="color:red">高度</font>
- ========================================
- 有序树
  - 树种任意节点的子节点之间有顺序关系
- 无序树
  - 树种任意节点的子节点之间没有顺序关系
  - 自由树
- 森林
  - 由m(m>0)棵互不香蕉的树组成的集合

### 5.1.二叉树(`Binary Tree`)

- 特点

  - 每个节点的度最大为2(最多拥有2棵子树)
  - 左子树和右子树是有顺序的
  - 即使某节点只有一棵子树,也要区分左右子树

- 二叉树是有序树

- 性质

  - 非空二叉树的第 `i` 层最多拥有 2^(`i-1`) (`i >= 1`) 个节点
  - 在高度为 `h` 的二叉树上最多有 `2^h - 1` 个节点(`h>=1`)
  - 对于任何一棵非空二叉树,如果叶子节点的个数为: `n0`，度为2的节点个数为: `n2`,则:`n0=n2+1`

- 真二叉树(`Proper Binary Tree`)

  - 节点的度要么为0,要么为2

- 满二叉树(`Full Binary Tree`)

  - 所有节点的度要么为0,要么为2。且所有的叶子节点都在最后一层
  - 在同样高度的二叉树中,满二叉树的叶子节点数量最多,总节点数量最多
  - 满二叉树一定是真二叉树,真二叉树不一定是满二叉树
  - 满二叉树的高度为h(`h>=1`)
    - 那么第 `i` 层的节点数量为: `2^(i-1)`
    - 叶子节点的数量: `2^h - 1`
    - 总节点数: `n=2^h - 1 = 2^0 + 2^1 + ... + 2^(h-1)`
    - 总高度: h = `log2(n+1)`

- 完全二叉树(`Complete Binary Tree`)

  - 叶子节点只会出现在<font style="color:red">最后2层</font>,且<font style="color:red">最后一层</font>的<font style="color:red">叶子节点</font>都是<font style="color:red">靠左对齐</font>

  - 完全二叉树<font style="color:red">从根节点</font>到<font style="color:red">倒数第2层</font>是<font style="color:red">满二叉树</font>

  - 满二叉树一定是完全二叉树,完全二叉树不一定是满二叉树

  - 性质

    - 度为1的节点只有左子树

    - 度为1的节点要么1个,要么0个

    - 同样节点数量的二叉树,完全二叉树的高度最小

    - 假设:完全二叉树的高度为h(h>=1),那么:

      - 至少有 `2^(h-1)` 个节点`(2^0+2^1+...+2^(h-2) + 1)`
      - 至多有`2^h - 1` 个节点`(2^0+2^1+...+2^(h-1))`--满二叉树
      - 总节点数:n
        - `2^(h - 1) <= n < 2^h`
        - `h - 1 <= log2n < h`
        - `h = floor(log2n) + 1`

    - ```json
      # 一棵树有 n 个节点的完全二叉树(n>0),从上到下,从左至右对节点从1还是进行编号,对于任意第 `i` 个节点
      # 1.如果 i == 1 , 那么它是根节点
      # 2.如果 i > 1, 它的父节点编号为: floor(i/2)
      # 3.如果 2i <= n, 它的左节点编号为: 2i
      # 4.如果 2i > n, 它无左子节点
      # 5.如果 2i + 1 <= n, 它的右子节点的编号为: 2i + 1
      
      # 总结:
      # 一棵树有 n 个节点的完全二叉树
      # 1.如果 n 为偶数 --> 叶子节点的数量: n0 = n / 2
      # 2.如果 n 为奇 --> 叶子节点的数量: n0 = (n + 1) / 2
      # n0 = floor((n + 1) / 2) == ceiling(n / 2) == (n + 1) >>> 1
      ```

    - 

### 5.2.平衡二叉搜索树(`BBST`)

> `Balanced Binary Search Tree`
>
> `AVL` 树
>
> 红黑树
>
> > 自平衡的二叉搜索树(`Self-balancing Binary Search Tree`)

- 平衡

  - 当节点数量固定,左右子树的高度越接近,这个二叉树就越平衡(高度越低)

  - 改进

    - ```json
      # 节点的添加、删除顺序是无法限制的,可以认为是随机的
      # 在节点的添加和删除操作之后,相办法让二叉搜索树恢复平衡(减小树的高度)
      
      # 用尽量少的调整次数达到适度的平衡即可
      # 一个达到适度平衡的二叉搜索树,可以称之为: 平衡二叉搜索树
      ```

    - 

#### 5.2.1.`AVL` 树

- 平衡因子(`Balance Factor`) - 某节点的左右子树的高度差

- 每个节点的平衡因子只可能是: `1、0、-1` (绝对值 <=1,如果超过 1,则称之为: "失衡")

- 每个节点左右子树的高度差不超过1

- 搜索、添加、删除的时间复杂度(O(`logn`))

- 单旋

  - `LL`-右旋转-(单旋转)

    - ```json
      # n node
      # p parent
      # g grandparent
      ## ------------------
      # g.left = p.right
      # p.right = g
      # 让 p 成为: 根节点
      # 维护相应节点的 parent 属性
      # 先后更新: g、p 的高度
      ## ------------------
      
      ```

  - `RR`-左旋转-(单旋转)
  
    - ```json
      # n node
      # p parent
      # g grandparent
      ## ------------------
      # g.right = p.left
      # p.left = g
      # 让 p 成为: 根节点
      # 维护相应节点的 parent 属性
      # 先后更新: g、p 的高度
      ## ------------------
      ```
  
- 双旋

  - `LR-RR`-左旋转--`LL`-右旋转-(双旋转)
  - `RL-LL`-右旋转--`RR`-左旋转-(双旋转)

- 总结

  - 添加
    - 可能会导致<font style="color:blue">所有</font>的<font style="color:red">祖先节点</font>都失衡
    - 只要让高度最低的失衡节点恢复平衡,整棵树就恢复平衡 (仅需O(1)次调整)
  - 删除
    - 可能会导致<font style="color:red">父节点</font>失衡
    - 让<font style="color:red">父节点</font>恢复平衡后,可能会导致更高层的祖先节点失衡 (至多需要O(`logn`)次调整）
  - 时间复杂度
    - 搜索
      - O(`logn`)
    - 添加
      - O(`logn`) 仅需要O(1)次旋转操作
    - 删除
      - O`(logn)` 最多需要O(`logn`)次旋转操作

### 5.3.红黑树

> 红黑树是一种自平衡的二叉搜索树,d也平衡二叉B树

- <font style="color:red">节点</font>是 <font style="color:red">`RED`</font>或者 <font style="color:red">`BLACK`</font>
- <font style="color:red">根节点</font>为 <font style="color:red">`BLACK`</font>
- <font style="color:red">叶子节点</font>(外部节点,空节点)都是<font style="color:red"> `BLACK`</font>
- <font style="color:red">`RED`</font> 节点的子节点都是 <font style="color:red">`BLACK`</font>
  - <font style="color:red">`RED`</font> 节点的 `parent` 都是 <font style="color:red">`BLACK`</font>
  - 从 <font style="color:red">根节点</font>  到 <font style="color:red">叶子节点</font>  的所有路径上不能有`2`个连续的<font style="color:red">`RED`</font>节点
- 从<font style="color:red">任意节点</font> 到<font style="color:red"> 叶子节点</font>  的所有路径都包含相同数目的 <font style="color:red">`BLACK`</font>  节点