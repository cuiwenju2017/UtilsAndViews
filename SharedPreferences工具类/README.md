## SharedPreferences工具类用法

存值：
```
SPUtil.putObject(Constants.SP_KEY_USERINFO,o.getData());
SPUtil.put(Constants.LOGIN_STATUS, true);
```

取值：
```
UserModule userModule = SPUtil.getObject(Constants.SP_KEY_USERINFO, UserModule.class);
userModule.getLoginCode();
```