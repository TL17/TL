var app = angular.module('localtest', []);
app.controller('localtest_ctrl', function($scope, $http) {

    //初始化变量
    var serverUrl = "http://localhost:8080/TL";
    var postCfg = {
        headers: { "Content-Type": "application/x-www-form-urlencoded;charset=utf-8" },
        transformRequest: function (data) {
            return $.param(data);
        }
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
        $http.post(serverUrl+"/sign_up",{schoolID:"123123",account:account,password:password},postCfg)
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
            }).error(function(){
                $scope.ret_doSetPerson = "测试失败";
            });
        return ($scope.doSetPerson == "测试成功");
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
        $http.post(serverUrl+"/sign_up",{schoolID:"123123",account:account,password:password},postCfg)
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
            }).error(function(){
                $scope.ret_doSignIn = "测试失败";
            });
        return ($scope.ret_doSignIn == "测试成功");
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
        $http.post(serverUrl+"/sign_up",{schoolID:"123123",account:account,password:"123456"},postCfg)
            .success(function(ret){
                $scope.ret_doSignUp = "测试失败";
                if (ret.status) {
                    $http.post(serverUrl+"/sign_up",{schoolID:"123123",account:account,password:"123456"},postCfg)
                        .success(function(ret){
                            if (!ret.status) {
                                $scope.ret_doSignUp = "测试成功";
                            }
                        });
                }
            }).error(function(){
                $scope.ret_doSignUp = "测试失败";
            });
        return ($scope.ret_doSignUp == "测试成功");
    };

    //测试helloworld post
    $scope.doPost = function() {
        $http.post(serverUrl+"/HelloWorld/name",{date:"date"},postCfg)
            .success(function(ret){
                $scope.ret_post = "测试失败";
                if (ret.msg == "Hello name!" && ret.date == "date")
                    $scope.ret_post = "测试成功";
            }).error(function(){
                $scope.ret_post = "测试失败";
            });
        return ($scope.ret_post == "测试成功");
    };

    //测试helloworld get
    $scope.doGet = function() {
        $http.get(serverUrl+"/HelloWorld/name",{params:{date:"date"}})
            .success(function(ret){
                $scope.ret_get = "测试失败";
                if (ret.msg == "Hello name!" && ret.date == "date")
                    $scope.ret_get = "测试成功";
            }).error(function(){
                $scope.ret_get = "测试失败";
            });
        return ($scope.ret_get == "测试成功");
    };

    //测试全部
    $scope.doAll = function() {
        $scope.doPost();
        $scope.doGet();
        $scope.doSignUp();
        $scope.doSignIn();
        $scope.doSetPerson();
    };
});
