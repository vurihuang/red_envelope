# 口令红包

## 项目框架

```
-project
|-entity      -- Model
|-exception   -- 业务异常
|-repository  -- DB操作
|-rest        -- API
|-service     -- 业务处理
|-utils       -- 工具类
```

使用技术： Spring Boot + MySQL + Redis + Swagger(用于生成 API 文档)

## API

![API](https://github.com/upeoe/red_envelope/blob/master/images/API.png)

文档访问链接: 启动项目后访问 `http://xxx/swagger-ui.html`

``` bash
# 获取 authentication token
$ curl http://xxx/api/v1/token/{userId}
# 发口令红包
$ curl -X POST -H "Authentication: Bearer <token>" http://xxx/api/v1/red_envelope/send -d "{\"money\":  100, \"number\": 10}"
```

```
URL: /api/v1/red_envelope/fetch 领取红包
Method: POST
Params:
    {
      "sign": "string" // 红包口令
    }
Response:
    {
      "code": "string",
      "data": {},
      "msg": "string"
    }

URL: /api/v1/red_envelope/send 发送口令红包
Method: POST
Params:
    {
      "number": 0,  // 红包个数
      "money": 0    // 红包金额
    }
Response:
    {
      "code": "string",
      "data": {},
      "msg": "string"
    }

URL: /api/v1/token/{userId}  获取用户授权token
Method: GET
Params:
    userId: 用户标识
Response:
    token

URL: /api/v1/user/fetched 当前用户已领取的红包列表
Method: GET
Response:
    {
      "code": "string",
      "data": {},
      "msg": "string"
    }

URL: /api/v1/user/send 当前用户已发送的红包列表
Method: GET
Response:
    {
      "code": "string",
      "data": {},
      "msg": "string"
    }

URL: /api/v1/wallet 当前用户的账户余额
Method: GET
Response:
    {
      "code": "string",
      "data": {},
      "msg": "string"
    }
```

## 想法思路

1. 口令红包的红包方案可以有两种方式，1)在创建红包的一开始就根据用户指定的红包个数和金额，直接生成每个子红包的随机金额，分别保存记录
   在红包表和子红包表中，参与者携带红包口令从提前生成好的子红包中取一个；2)在创建红包时生成一条红包记录，每一个请求的参与者从中扣除
   一次随机的金额，剩余红包个数递减。这里选择了第一种方案。
2. 考虑到 Redis 轻量级且支持持久化性能较好，原生支持分布式架构，如果单机服务拆成微服务化，拆分成本相对较低，并且基于 Redis 实现分布
   式锁 Redlock 也比较方便（在代码中也通过 ThreadLocal + Redis 的 setnx 方法实现了简易的锁，可以应用在第二种方案）；红包入库之后，
   同时将大红包和小红包缓存至 Redis，将小红包缓存在 Redis 的 list 数据结构中，利用 list 的特性可以比较方便对红包进行操作，而每个小红
   包自身都能保证唯一性；参与者请求时先从 Redis 的大红包集合中查看该口令红包是否已失效，如果没有失效，则从该口令红包的小红包队列中
   pop 一个已生成好的随机金额的红包，根据该红包 key 更新 DB 中的该小红包标记已被领取；利用 Redis 的单线程原子处理 Client 请求，解决
   线程安全问题；将领取到的金额保存至参与者的余额中，并将其添加到红包已领取的 set 队列里，缓存至 Redis 中，用于限制每个红包每个用户
   只能领取一次，同时 DB 创建联合唯一约束，红包和用户的组合只能唯一。
3. 接口的身份认证通过 Spring Boot Security + JWT Token 实现，存放在 Token 体中的用户身份信息通过过滤器获取并进行筛选认证，注入到
   控制器的请求参数中方便使用。
4. 可以改进的地方：定时轮询任务变成基于事件的触发机制？多实例并发操作有效保证修改的唯一性？红包金额随机且合理？

