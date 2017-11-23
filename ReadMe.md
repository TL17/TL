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
* TL后端项目源代码（Maven项目）：
* https://github.com/TruthABC/TL
* https://github.com/TL17/TL
* lib目录，其中含有以下项目依赖
```
1. javaEE的Dynamic Web库（servlet）；
2. JSONObject库（net.sf.json）；
3. Mysql java connector库。
```
* pom.xml文件，规定以后添加新的依赖或库时，借助maven，而非向lib目录中添加。

## 数据库
* URL：		jdbc:mysql://localhost:3306/tl?useSSL=false&serverTimezone=UTC
* 用户名：	root
* 密码：		root
* TL数据库结构与数据dump文件：
* 暂无

## 前端
* TL前端项目源代码：
* 暂无
