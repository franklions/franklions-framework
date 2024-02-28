# XX系统协议总纲

## 协议总体说明

1. 使用HTTPS作为数据传输协议，版本为1.1
2. 使用UTF-8作为所有文本数据的编码格式
3. 使用JSON作为数据传输格式，HTTP Header中的Content-Type为application/json 如有特殊 则单独说明
4. 接口使用restful风格设计,请求方式Method仅限四种：POST（插入），PUT（更新），GET（获取），DELETE（删除），针对不同的语义使用不同的Method.

## 接口访问认证

```
    token+空格+base64({uid}:{token})
```

> **注： <code>uid</code> 和 <code>token</code> 在通过登录接口获取**

| 参数名                 |类型|必填|参数位置|描述|默认值|
|---------------------|---|---|---|---|---|
| x-dayi-saas-clientId |string|是|header|客户端|无|
| x-dayi-saas-eid     |string|是|header|企业ID|无|
| x-Authorization     |string|是|header|认证字符串|无|


## 公共错误码

错误码（Error Code） | 错误消息（Error Message） | 描述（Description）
---|---|---
400001 | PARAMETER_VALID_ERROR | 参数验证失败
400002 | NO_FOUND_RECORD | 未找到相关记录
400003 | EXIST_RECORD_ERROR | 相关记录已经存在
401004 | USER_UNAUTHORIZED | 用户未授权
401005 | USER_TOKEN_EXPIRE | 用户TOKEN过期
500001 | SERVER_ERROR | 服务器内部错误
400006 | DEVICE_NOT_EXIST  | 设备不存在
400007 | DEVICE_OFFLINE  | 设备不在线

## 返回结果
本服务的所有接口返回的http status code都为200,如果不为200时可能由服务器返回的结果，而不是由服务程序返回的。

| 参数名         |类型|说明|
|-------------|---|---|
| success     |bool|true 成功 false 失败|
| errorCode   |number|错误码|
| errorMessage |string|错误信息|
| data        | json object|返回的数据内容|
| ts          |number|时间戳|

## 分页查询请求参数

>query为查询条件，根据具体查询条件不同进行拼接查询


| 参数名   |参数类型|默认值|说明|
|-------|---|---|---|
| page  |number|1|页码|
| size  |number|10|显示条数|
| sort  |string|资源ID|排序字段|
| desc  |bool|false|true：倒序，false：正序|
| query |query string|无|具体的查询条件|

query查询和件内容为json字符串，key为查询的字段value为查询的值，使用时注意需要对该字段内容进行urlencode.

## 分页查询返回结果

下面结果包含在返回结果的data字段中.

| 参数名   |类型|说明|
|-------|---|---|
| rows  |array json object |数据集|
| total |number|总数量|

> 查询结果为集合，类似查询结果均为集合后续不再特别说明


## 访问地址

    https://127.0.0.1:10080/v0/api