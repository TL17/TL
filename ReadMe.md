## 测试TODO
* 数据库编码问题，无法通过servlet插入中文
* account应有主键或unique属性，
* ~~3,4接口测试失败（3，4错误已修复）~~
* 接口5后端已更新，通过测试
* 171211测试进度，next:接口6
* 接口6测到选课后需要额外测试
* 接口3,4后端已更新，通过测试
* 171212测试进度，next:接口7搜索课程
* 接口7微调：增加do-while前置判断
* 接口9微调：selection.studentid改为user.account
* 跳过10，11测试（尚未开发）
* 171213测试进度，next:接口12 & 13评教
* 接口12，13微调：恢复doGet，studentid认为是account
* 171214测试进度：接口测试完成
* 171215测试进度：上传、下载功能完毕，已测试
* 至171215，后台开发与测试完成
* 20171219，前端测试，登录注册微调对其
* 需要在注册或登录时确认用户身份type，涉及接口1,2

## 服务器
* 服务器IP: ???.???.???.???，联系刘石坚；
* ID: 联系刘石坚；
* Password: 联系刘石坚；
* 说明：后端部署在此服务器。可以用windows自带的“远程桌面连接”程序，连接至此。

## 服务器后端
* TL后端部署在：
* http://???.???.???.???:8080/TL
* 说明1：对TL后端发送http POST请求，参数应在请求的body中，并需要附加请求header，见ng_test.html，参数的格式等同于http GET的参数格式，即POST请求的body应为：“param1=p1&param2=p2”
* 说明2：http POST header: “Content-Type: application/x-www-form-urlencoded;charset=utf-8”
* 说明3：http POST 传参不通过url后加“?”完成，而是通过body，body形如：“name=shijian&date=today&userToken=233”

## 服务器后端代码
* 20171129更新： 项目转化为纯Maven项目
* TL后端项目源代码（Maven项目）：
* https://github.com/TruthABC/TL
* https://github.com/TL17/TL
* ~~lib目录，其中含有以下项目依赖~~
```
1. javaEE的Dynamic Web库（servlet）；
2. JSONObject库（net.sf.json）；
3. Mysql java connector库。
```
* pom.xml文件，规定以后添加新的依赖或库时，借助maven，而非向lib目录中添加。

## 数据库
* URL：		jdbc:mysql://localhost:3306/tl?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
* 用户名：	root
* 密码：		root
* TL数据库结构与数据dump文件：
* 暂无

## 前端
* TL前端项目源代码：
* 暂无

## 文档
请见更新后的Documents List.
