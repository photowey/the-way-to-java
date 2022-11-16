`XCURL`



## 小册

> ```http
> https://juejin.cn/book/7146874802144280611
> ```



## 示例

```shell
CURL -X POST -H "Content-Type: application/json" -H "Accept: */*" "https://httpbin.org/post" -d "{\"hello\":\"world\"}"
```



## 控制台输出

```json
----------------------------------------------------------------
CURL -X POST -H "Content-Type: application/json" -H "Accept: */*" "https://httpbin.org/post" -d "{\"hello\":\"world\"}"
----------------
Output:
-------
{
	"args":{
		
	},
	"headers":{
		"Accept":"*/*",
		"Content-Length":"17",
		"Content-Type":"application/json",
		"Host":"httpbin.org",
		"User-Agent":"curl/7.83.1",
		"X-Amzn-Trace-Id":"Root=1-6375108a-3974ed971f3de5cb6011a553"
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

