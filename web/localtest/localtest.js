var app = angular.module('localtest', []);
app.controller('localtest_ctrl', function($scope, $http) {

    //初始化变量
    var serverUrl = "http://123.207.6.234:8080/TL";
    var postCfg = {
        headers: { "Content-Type": "application/x-www-form-urlencoded;charset=utf-8" },
        transformRequest: function (data) {
            return $.param(data);
        }
    };

    /**
     ### 12. 评教（Evaluate course）
     `http POST http://123.207.6.234:8080/TL/course/evaluation/{courseID}`

     in：

     * url参数 - courseID：课程ID；
     * userToken：验证account；
     * account：验证身份。
     * (evaluation)score评教分数+comment评论

     return：

     * status：反馈状态，e.g. 评教成功
     ```
     {"info": String,
      "status": boolean
     }
     ```

     ### 13. 获取课程评教信息（Get evaluation of course）
     `http GET http://123.207.6.234:8080/TL/course/evaluation/{courseID}`

     in：

     * url参数 - courseID：课程ID；
     * userToken：验证account；
     * account：验证身份。

     return：

     * evaluations：对应课程的评教列表
     ```
     {"info":"获取评教成功",
      "status":true,
      "evaluations":[
         {"score":"1.0","comment":"1课的评论1"},
         {"score":"1.0","comment":"1课的评论2"},
         {"score":"1.0","comment":"1课的评论3"},
         {"score":"1.0","comment":"1课的评论4"},
         {"score":"1.0","comment":"1课的评论5"}
       ]
     }
     ```
     */
    $scope.doCourseEvaluation = function() {
        var account = new Date().getTime().toString();
        var password = "123456";
        var userToken;
        var courseID;
        var courseName = "项目管理" + new Date().getTime().toString();
        var courseInfo = "要写文档";
        var coursePlan = "不看代码";
        var score = 5;
        var comment = "没毛病";
        $scope.ret_doCourseEvaluation = "测试失败";
        $http.post(serverUrl+"/sign_up",{schoolID:account,account:account,password:password},postCfg)
            .success(fun2);
        function fun2(ret) {
            $http.post(serverUrl + "/sign_in", {account: account, password: password}, postCfg)
                .success(fun3);
        }
        function fun3(ret) {
            userToken = ret.userToken;
            $http.post(serverUrl+"/course/add", {
                userToken: userToken,
                account: account,
                courseName: courseName,
                courseInfo: courseInfo,
                coursePlan: coursePlan
            },postCfg)
                .success(fun4);
        }
        function fun4(ret) {
            courseID = ret.courseID;
            $http.post(serverUrl+"/course/select/"+courseID, {
                userToken: userToken,
                account: account}, postCfg)
                .success(func5);
        }
        function func5(ret) {
            $http.post(serverUrl+"/course/evaluation/"+courseID, {
                userToken: userToken,
                account: account,
                score: score,
                comment: comment}, postCfg)
                .success(func6);
        }
        function func6(ret) {
            $http.get(serverUrl+"/course/evaluation/"+courseID, {params:{
                userToken: userToken,
                account: account}})
                .success(function(ret) {
                    if (ret.evaluations !== undefined) {
                        $scope.ret_doCourseEvaluation = "测试成功";
                    }
                });
        }
    };

    /**
     ### 9. 选课（Select course）
     `http POST http://123.207.6.234:8080/TL/course/select/{courseID}`
     in：

     * url参数 - courseID：课程ID，这是在开课时应为每个课程生成的一个唯一的ID号；
     * userToken：验证account；
     * account：验证身份。

     return：

     * status：选课状态；
     * 返回数据规范：
     ```
     {"info": String,
      "status": boolean
     }
     ```
     */
    $scope.doSelectCourse = function() {
        var account = new Date().getTime().toString();
        var password = "123456";
        var userToken;
        var courseID;
        var courseName = "英美电竞文化" + new Date().getTime().toString();
        var courseInfo = "打得不错";
        var coursePlan = "很自然的错误";
        $scope.ret_doSelectCourse = "测试失败";
        $http.post(serverUrl+"/sign_up",{schoolID:account,account:account,password:password},postCfg)
            .success(fun2);
        function fun2(ret) {
            $http.post(serverUrl + "/sign_in", {account: account, password: password}, postCfg)
                .success(fun3);
        }
        function fun3(ret) {
            userToken = ret.userToken;
            $http.post(serverUrl+"/course/add", {
                userToken: userToken,
                account: account,
                courseName: courseName,
                courseInfo: courseInfo,
                coursePlan: coursePlan
            },postCfg)
                .success(fun4);
        }
        function fun4(ret) {
            courseID = ret.courseID;
            $http.post(serverUrl+"/course/select/"+courseID, {
                userToken: userToken,
                account: account}, postCfg)
                .success(func5);
        }
        function func5(ret) {
            if (ret.status) {
                $http.get(serverUrl+"/course/manage", {params:{
                    userToken: userToken,
                    account: account}})
                    .success(function(ret){
                        if (ret.status && ret.courses !== undefined) {
                            $scope.ret_doSelectCourse = "测试成功";
                        }
                    });
            }
        }
    };

    /**
     ### 8. 获取课程信息（Get course details）
     `http GET http://123.207.6.234:8080/TL/course/detail/{courseID}`

     in：

     * url参数 - courseID：课程ID，这是在开课时应为每个课程生成的一个唯一的ID号；

     return：

     * course：对应课程
     * 返回数据规范：
     ```
     {"info": String,
      "status": boolean,
      "course":{"coursePlan":"课程大纲1",
                "courseName":"课程名称1",
                "courseInfo":"课程介绍1",
                "courseID":1
                }
     }
     ```
     */
    $scope.doGetCourse = function() {
        var account = new Date().getTime().toString();
        var password = "123456";
        var userToken;
        var courseName = "高级电竞" + new Date().getTime().toString();
        var courseInfo = "碾碎他们";
        var coursePlan = "敌军还有30秒到达战场";
        $scope.ret_doGetCourse = "测试失败";
        $http.post(serverUrl+"/sign_up",{schoolID:account,account:account,password:password},postCfg)
            .success(function(ret){
                $http.post(serverUrl+"/sign_in",{account:account,password:password},postCfg)
                    .success(function(ret){
                        if (ret.status && ret.userToken !== undefined) {
                            userToken = ret.userToken;
                            $http.post(serverUrl+"/course/add", {
                                userToken: userToken,
                                account: account,
                                courseName: courseName,
                                courseInfo: courseInfo,
                                coursePlan: coursePlan},postCfg)
                                .success(function(ret){
                                    if (ret.status && ret.courseID !== undefined) {
                                        var courseID = ret.courseID;
                                        $http.get(serverUrl+"/course/detail/"+courseID)
                                            .success(function(ret){
                                                if (ret.status && ret.course.courseName == courseName) {
                                                    $scope.ret_doGetCourse = "测试成功";
                                                }
                                            });
                                    }
                                });
                        }
                    });
            });
    };

    /**
     ### 7. 搜索课程（Search course）
     `http GET http://123.207.6.234:8080/TL/course/search`

     in：

     * keyword：关键词

     return：

     * courses：搜索到的课程列表
     * 返回数据规范：
     ```
     {"info": String,
      "status": boolean,
      "courses":[
         {"coursePlan":"课程大纲1","courseName":"课程名称1","courseInfo":"课程介绍1","courseID":1},
         {"coursePlan":"课程大纲2","courseName":"课程名称2","courseInfo":"课程介绍2","courseID":2},
         {"coursePlan":"课程大纲3","courseName":"课程名称3","courseInfo":"课程介绍3","courseID":3},
         {"coursePlan":"课程大纲4","courseName":"课程名称4","courseInfo":"课程介绍4","courseID":4},
         {"coursePlan":"课程大纲5","courseName":"课程名称5","courseInfo":"课程介绍5","courseID":5}
      ]
     }
     ```
     */
    $scope.doSearchCourse = function() {
        var keyword = "电竞";
        $scope.ret_doSearchCourse = "测试失败";
        $http.get(serverUrl+"/course/search", {params:{
            keyword: keyword}})
            .success(function(ret){
                if (ret.status && ret.courses !== undefined ) {
                    $scope.ret_doSearchCourse = "测试成功";
                }
            });
    };

    /**
     ### 6. 管理课程（Manage courses - For all users）
     `http GET http://123.207.6.234:8080/TL/course/manage`

     in：

     * usertoken：用于验证用户登录状态，验证account；
     * account：验证身份。

     return：

     * courses：对于教师，返回对应开课列表；对于学生，返回对应选课列表；
     * 返回数据规范：
     ```
     {"info": String,
      "status": boolean,
      "courses":[
         {"coursePlan":"课程大纲1","courseName":"课程名称1","courseInfo":"课程介绍1","courseID":1},
         {"coursePlan":"课程大纲2","courseName":"课程名称2","courseInfo":"课程介绍2","courseID":2},
         {"coursePlan":"课程大纲3","courseName":"课程名称3","courseInfo":"课程介绍3","courseID":3},
         {"coursePlan":"课程大纲4","courseName":"课程名称4","courseInfo":"课程介绍4","courseID":4},
         {"coursePlan":"课程大纲5","courseName":"课程名称5","courseInfo":"课程介绍5","courseID":5}
      ]
     }
     ```
     */
    $scope.doManageCourse = function() {
        var account = new Date().getTime().toString();
        var password = "123456";
        var userToken;
        var courseName = "电竞" + new Date().getTime().toString();
        var courseInfo = "亡者农药";
        var coursePlan = "今晚吃鸭";
        $scope.ret_doManageCourse = "测试失败";
        $http.post(serverUrl+"/sign_up",{schoolID:account,account:account,password:password},postCfg)
            .success(function(ret){
                $http.post(serverUrl+"/sign_in",{account:account,password:password},postCfg)
                    .success(function(ret){
                        if (ret.status && ret.userToken !== undefined) {
                            userToken = ret.userToken;
                            $http.post(serverUrl+"/course/add", {
                                userToken: userToken,
                                account: account,
                                courseName: courseName,
                                courseInfo: courseInfo,
                                coursePlan: coursePlan},postCfg)
                                .success(function(ret){
                                    if (ret.status && ret.courseID !== undefined) {
                                        var courseID = ret.courseID;
                                        $http.get(serverUrl+"/course/manage", {params:{
                                            userToken: userToken,
                                            account: account}})
                                            .success(function(ret){
                                                if (ret.status && ret.courses[0].courseID == courseID) {
                                                    $scope.ret_doManageCourse = "测试成功";
                                                }
                                            });
                                    }
                                });
                        }
                    });
            });
    };

    /**
     ### 5. 开课（Add course - For teacher）
     `http POST http://123.207.6.234:8080/TL/course/add`

     in：

     * userToken：用于验证用户登录状态，验证account；
     * account：验证身份。
     * courseName（课程名称），courseInfo（课程简介），coursePlan（课程大纲）；

     return：

     * {status: boolean, info: String, courseID: int}反馈状态，开课是否成功；
     * 如果开课成功有自动分配的courseID返回
     */
    $scope.doAddCourse = function() {
        var account = new Date().getTime().toString();
        var password = "123456";
        var userToken;
        var courseName = "电竞" + new Date().getTime().toString();
        var courseInfo = "亡者农药";
        var coursePlan = "今晚吃鸭";
        $scope.ret_doAddCourse = "测试失败";
        $http.post(serverUrl+"/sign_up",{schoolID:account,account:account,password:password},postCfg)
            .success(function(ret){
                $http.post(serverUrl+"/sign_in",{account:account,password:password},postCfg)
                    .success(function(ret){
                        if (ret.status && ret.userToken !== undefined) {
                            userToken = ret.userToken;
                            $http.post(serverUrl+"/course/add", {
                                userToken: userToken,
                                account: account,
                                courseName: courseName,
                                courseInfo: courseInfo,
                                coursePlan: coursePlan
                            },postCfg)
                                .success(function(ret){
                                    if (ret.status && ret.courseID !== undefined) {
                                        $scope.ret_doAddCourse = "测试成功";
                                    }
                                });
                        }
                    });
            });
    };

    /**
     ### 4. 获取个人信息（Get personal information）
     `http GET http://123.207.6.234:8080/TL/account/{account}`

     in：

     * url参数 account: 账号
     * 参数userToken：用于验证用户登录状态；

     return:
     ```
     {status: boolean,
      info: String,
      perInfo:{name: String,
               info: String,
               contact: String
              }
     }
     ```
     */
    $scope.doGetPerson = function() {
        var account = new Date().getTime().toString();
        var password = "123456";
        var userToken;
        $scope.ret_doGetPerson = "测试失败";
        $http.post(serverUrl+"/sign_up",{schoolID:account,account:account,password:password},postCfg)
            .success(function(ret){
                $http.post(serverUrl+"/sign_in",{account:account,password:password},postCfg)
                    .success(function(ret){
                        if (ret.status && ret.userToken !== undefined) {
                            userToken = ret.userToken;
                            $http.get(serverUrl+"/account/"+account,{params:{userToken:userToken}})
                                .success(function(ret){
                                    if (ret.status && ret.perInfo !== undefined) {
                                        $scope.ret_doGetPerson = "测试成功";
                                    }
                                });
                        }
                    });
            });
    };

    /**
     ### 3. 设置个人信息（Set personal information）
     `http POST http://123.207.6.234:8080/TL/account/{account}`

     in：

     * url参数 account: 账号
     * 参数userToken：用于验证用户登录状态；
     * 参数name（姓名）， info（简介），contact（联系方式）

     return:

     * {status: boolean, info: String}: 反馈状态；
     */
    $scope.doSetPerson = function() {
        var account = new Date().getTime().toString();
        var password = "123456";
        var userToken;
        var name = "shijianliu";
        var info = "info";
        var contact = "110";
        $http.post(serverUrl+"/sign_up",{schoolID:account,account:account,password:password},postCfg)
            .success(function(ret){
                $scope.ret_doSetPerson = "测试失败";
                $http.post(serverUrl+"/sign_in",{account:account,password:password},postCfg)
                    .success(function(ret){
                        if (ret.status && ret.userToken !== undefined) {
                            userToken = ret.userToken;
                            $http.post(serverUrl+"/account/"+account,{userToken:userToken,name:name,info:info,contact:contact},postCfg)
                                .success(function(ret){
                                    if (ret.status) {
                                        $scope.ret_doSetPerson = "测试成功";
                                    }
                                });
                        }
                    });
            })
            .error(function(){
                $scope.ret_doSetPerson = "测试失败";
            });
    };

    /**
     ### 2. 登陆（Sign in）
     `http POST http://123.207.6.234:8080/TL/sign_in`

     in：

     * account：账号；
     * password：密码；

     return：

     * {userToken: String, status: boolean, info: String} userToken：服务器对应该账号的token，用于验证身份，与用户一一对应
     */
    $scope.doSignIn = function() {
        var account = new Date().getTime().toString();
        var password = "123456";
        var wrongpassword = "654321";
        $http.post(serverUrl+"/sign_up",{schoolID:account,account:account,password:password},postCfg)
            .success(function(ret){
                $scope.ret_doSignIn = "测试失败";
                $http.post(serverUrl+"/sign_in",{account:account,password:password},postCfg)
                    .success(function(ret){
                        if (ret.status && ret.userToken !== undefined) {
                            $http.post(serverUrl+"/sign_in",{account:account,password:wrongpassword},postCfg)
                                .success(function(ret){
                                    if (!ret.status) {
                                        $scope.ret_doSignIn = "测试成功";
                                    }
                                });
                        }
                    });
            })
            .error(function(){
                $scope.ret_doSignIn = "测试失败";
            });
    };

    /**
     ### 1. 注册（Sign up）
     `http POST http://123.207.6.234:8080/TL/sign_up`

     in：

     * schoolID：学号/工号，对每个用户来说是唯一的，用于注册。实际情况根据学校内部数据来定，而目前由于无法拿到学校的内部数据，模拟验证即可；
     * account: 用户自己定义的账号，用于登录，对每个用户来说是唯一的；
     * password：用户定义的密码，与账户对应；

     return:

     * {status: boolean, info: String} 反馈状态，若schoolID通过验证，account唯一，则返回一个注册成功的反馈状态，否则，返回注册失败信息；
     */
    $scope.doSignUp = function() {
        var account = new Date().getTime().toString();
        $http.post(serverUrl+"/sign_up",{schoolID:account,account:account,password:"123456"},postCfg)
            .success(function(ret){
                $scope.ret_doSignUp = "测试失败";
                if (ret.status) {
                    $http.post(serverUrl+"/sign_up",{schoolID:account,account:account,password:"123456"},postCfg)
                        .success(function(ret){
                            if (!ret.status) {
                                $scope.ret_doSignUp = "测试成功";
                            }
                        });
                }
            })
            .error(function(){
                $scope.ret_doSignUp = "测试失败";
            });
    };

    //测试helloworld post
    $scope.doPost = function() {
        $http.post(serverUrl+"/HelloWorld/name",{date:"date"},postCfg)
            .success(function(ret){
                $scope.ret_post = "测试失败";
                if (ret.msg == "Hello name!" && ret.date == "date")
                    $scope.ret_post = "测试成功";
            })
            .error(function(){
                $scope.ret_post = "测试失败";
            });
    };

    //测试helloworld get
    $scope.doGet = function() {
        $http.get(serverUrl+"/HelloWorld/name",{params:{date:"date"}})
            .success(function(ret){
                $scope.ret_get = "测试失败";
                if (ret.msg == "Hello name!" && ret.date == "date")
                    $scope.ret_get = "测试成功";
            })
            .error(function(){
                $scope.ret_get = "测试失败";
            });
    };

});