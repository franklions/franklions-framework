# 接口签名说明

```
    token+空格+base64({uid}:{token})
```

> **注： <code>uid</code> 和 <code>token</code> 在通过登录接口获取**

| 参数名                 |类型|必填|参数位置|描述|默认值|
|---------------------|---|---|---|---|---|
| x-dayi-saas-clientId |string|是|header|客户端|无|
| x-dayi-saas-eid     |string|是|header|企业ID|无|
| x-Authorization     |string|是|header|认证字符串|无|