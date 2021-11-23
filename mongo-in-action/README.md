# `MongoDB`

## 运行容器
```shell
$ docker run -itd --restart always --name mongo \
-e TZ=Asia/Shanghai \
-e MONGO_INITDB_ROOT_USERNAME=mongo \
-e MONGO_INITDB_ROOT_PASSWORD=mongo \
-v /etc/localtime:/etc/localtime:ro \
-v /opt/mongo/data:/data/db \
-p 27017:27017 \
-d mongo:4.0

## 时区跨挂载 - 避免 - 时间属性-有时差
```

## 权限
```shell
$ use mongo-in-action
$ db.createUser({user:"root",pwd:"root",roles:[{role:"dbOwner",db:"mongo-in-action"}]})
```