# 项目管理API定义文档

后端提供Restful形式API（服务部署在http://123.207.6.234:8080/TL/ 下），前端调用后端提供的API

API接口定义如下：

### 1. 注册（Sign up）
`http://123.207.6.234:8080/TL/sign_up`

in： 

* schoolID：学号/工号，对每个用户来说是唯一的，用于注册。实际情况根据学校内部数据来定，而目前由于无法拿到学校的内部数据，模拟验证即可；
* account: 用户自己定义的账号，用于登录，对每个用户来说是唯一的；
* password：用户定义的密码，与账户对应；

return:

* {status: boolean, info: String}反馈状态，若schoolID通过验证，account唯一，则返回一个注册成功的反馈状态，否则，返回注册失败信息；

---

### 2. 登陆（Sign in）
`http://123.207.6.234:8080/apis/sign_in`

in：

* account：账号；
* password：密码；

return：

* usertoken：服务器对应该账号的token，用于验证身份，与用户一一对应

---

### 3. 设置个人信息（Set personal information）
`http://123.207.6.234:8080/apis/set_per_info`

in：

* usertoken：用于验证用户登录状态；
* per-info：个人信息，包括：name（姓名），info（简介），contact-way（联系方式）

return:

* status: 反馈状态；

---

### 4. 获取个人信息（Get personal information）
`http://123.207.6.234:8080/apis/get_per_info`

in：

* usertoken：用于验证用户登录状态；
* account：个人信息的拥有者账号

return:

* per-info：个人信息，包括：name（姓名），info（个人简介），contact-way（联系方式）

---

### 5. 开课（Add course - For teacher）
`http://123.207.6.234:8080/apis/add_course`

in：

* usertoken：用于验证用户登录状态，获取用户account；
* course：课程，包括：course-name（课程名称），course-info（课程简介），course-plan（课程大纲）；

return：

* status：反馈状态，开课是否成功；

---

### 6. 管理课程（Manage courses - For all users）
`http://123.207.6.234:8080/apis/manage_course`

in：

* usertoken：用于验证用户登录状态，获取用户account；

return：

* courses：对于教师，返回对应开课列表；对于学生，返回对应选课列表；

---

### 7. 搜索课程（Search course）
`http://123.207.6.234:8080/apis/search_course`

in：

* keyword：关键词

return：

* courses：搜索到的课程列表

---

### 8. 获取课程信息（Get course details）
`http://123.207.6.234:8080/apis/get_course_detail`

in：

* courseID：课程ID，这是在开课时应为每个课程生成的一个唯一的ID号；

return：

* course：对应课程

---

### 9. 选课（Select course）
`http://123.207.6.234:8080/apis/select_course`
in：

* usertoken：识别account；
* courseID：课程ID，这是在开课时应为每个课程生成的一个唯一的ID号；

return：

* status：选课状态；

---

### 10. 上传资源（Upload materials）
`http://123.207.6.234:8080/apis/upload_material`

in：

* usertoken：识别account；
* courseID：课程ID；
* material：上传的资源，（可能具有类型、资源地址等，目前暂定为PPT的形式）

return：

* status：上传状态

---

### 11. 获取资源（Get materials）
`http://123.207.6.234:8080/apis/get_material`

in：

* usertoken：识别account；
* courseID：课程ID；
* materialID：资源唯一的ID

return：

* material：资源

---

### 12. 评教（Evaluate course）
`http://123.207.6.234:8080/apis/evaluate_course`

in：

* usertoken：识别account；
* courseID：课程ID；
* evaluation：评教内容，包括：score评教分数，comment评论

return：

* status：反馈状态，e.g. 评教成功

---

### 13. 获取课程评教信息（Get evaluation of course）
`http://123.207.6.234:8080/apis/get_evaluate`

in：

* courseID：课程ID；

return：

* evaluations：对应课程的评教列表

---

