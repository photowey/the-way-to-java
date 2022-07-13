# `PostgreSQL`

```shell
$ docker pull postgres:14.4

$ docker run -itd \
--name postgres \
--restart always \
--privileged \
-p 5432:5432 \
-e POSTGRES_PASSWORD=postgres \
-v /opt/postgres/data:/var/lib/postgresql/data \
postgres:14.4
```