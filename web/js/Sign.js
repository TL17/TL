app.controller('sign_ctrl', ['$scope', '$rootScope', '$http', function($scope, $rootScope, $http) {
    serverUrl = "http://123.207.6.234:8080/TL";
    postCfg = {
        headers: { "Content-Type": "application/x-www-form-urlencoded;charset=utf-8" },
        transformRequest: function (data) {
            return $.param(data);
        }
    };

    /*
    TODO:
    check school ID and password format
     */
    $scope.sign_up = function(id,acc,pwd,repwd) {
        if (pwd != repwd)
            alert("Re-entered password should be the same as Password! @.@");
        // alert(id+" "+acc+" "+pwd);
        else {
            $http.post(serverUrl+"/sign_up",{schoolID: id, account:acc, password: pwd},postCfg)
                .success(function(ret){
                    if (ret.status)
                        alert("Sign up succeed~ ^_^");
                    else
                        alert(ret.info);
                });
        }
    }

    $scope.sign_in = function(acc,pwd) {
        window.localStorage['userToken'] = "hxy";
        window.localStorage['type'] = 'Teacher';
        window.location.href = "Courses.html";
        window.event.returnValue=false;

        // $http.post(serverUrl+"/sign_in",{account:acc, password:pwd},postCfg)
        //     .success(function(ret) {
        //         if (ret.status) {
        //             window.localStorage['userToken'] = ret.userToken;
        //             window.localStorage['type'] = ret.type;
        //             window.location.href = ret.type + ".html";
        //             window.event.returnValue = false;
        //         } else
        //             alert(ret.info);
        //     });
    }
}]);