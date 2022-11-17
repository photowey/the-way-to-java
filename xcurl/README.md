`XCURL`



## 小册

> ```http
> https://juejin.cn/book/7146874802144280611
> ```



## 示例

```shell
CURL -X POST -H "Content-Type: application/json" -H "Accept: */*" -d "{\"hello\":\"world\"}" https://httpbin.org/post
```



## 控制台输出

```json
----------------------------------------------------------------
CURL -X POST -H "Content-Type: application/json" -H "Accept: */*" -d "{\"hello\":\"world\"}" https://httpbin.org/post
-------
Output: -------
{
"args": {
},
"headers": {
"Accept": "*/*",
"Content-Length": "17",
"Content-Type": "application/json",
		"Host":"httpbin.org",
"User-Agent": "curl/7.83.1",
"X-Amzn-Trace-Id": "Root=1-6375eb59-33f691254ae9727212f393fd"
},
	"data":"{\"hello\":\"world\"}",
	"form":{
		
	},
	"origin":"x.x.x.x",
	"files":{
		
	},
	"json":{
		"hello":"world"
	},
	"url":"https://httpbin.org/post"
}
```

