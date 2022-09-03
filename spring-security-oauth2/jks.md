# `JWK`



> 生成 jks

```shell
$ keytool -genkey -alias jose -keyalg RSA \
-storetype PKCS12 -keysize 2048 -validity 365 \
-keystore jose.jks -storepass photowey \
-dname "CN=(W), OU=(Hi), O=(Hicoo), L=(CQ), ST=(CQ), C=(CN)"

#您的名字与姓氏是什么?
#  [Unknown]:  W
#您的组织单位名称是什么?
#  [Unknown]:  Hi
#您的组织名称是什么?
#  [Unknown]:  Hicoo
#您所在的城市或区域名称是什么?
#  [Unknown]:  CQ
#您所在的省/市/自治区名称是什么?
#  [Unknown]:  CQ
#该单位的双字母国家/地区代码是什么?
#  [Unknown]:  CN
#CN=W, OU=Hi, O=Hicoo, L=CQ, ST=CQ, C=CN是否正确?
```

```shell
$ keytool -genkey -alias client -keyalg RSA \
-storetype PKCS12 -keysize 2048 -validity 365 \
-keystore client.jks -storepass photowey \
-dname "CN=(W), OU=(Hi), O=(Hicoo), L=(CQ), ST=(CQ), C=(CN)"
```


> 导出证书

```shell
$ keytool -export -alias jose -keystore jose.jks -file jose.crt
```

