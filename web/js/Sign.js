app.controller('sign_ctrl', ['$scope', '$rootScope', '$http', function($scope, $rootScope, $http) {
    /*
    TODO:
    check school ID and password format
     */
    $scope.type_val = true;

    $scope.sign_up = function(id,acc,pwd,repwd, tyoe) {
        if (pwd != repwd)
            alert("Re-entered password should be the same as Password! @.@");
        // alert(id+" "+acc+" "+pwd);
        else {
            var ty = "";
            if (!$scope.type_val)
                ty = "teacher";
            else
                ty = "student";
            $http.post(serverUrl+"/sign_up",{schoolID: id, account:acc, password: pwd, type: ty},postCfg)
                .success(function(ret){
                    if (ret.status)
                        alert("Sign up succeed~ ^_^");
                    else
                        alert(ret.info);
                });
        }
    };

    $scope.sign_in = function(acc,pwd) {
        // window.localStorage['userToken'] = "hxy";
        // window.localStorage['account'] = acc;
        // window.location.href = "Student.html";
        // window.event.returnValue = false;

        $http.post(serverUrl+"/sign_in",{account:acc, password:pwd},postCfg)
            .success(function(ret) {
                if (ret.status) {
                    window.localStorage['userToken'] = ret.userToken;
                    window.localStorage['type'] = ret.type;
                    window.localStorage['account'] = acc;
                    if (ret.type === 'teacher')
                        window.location.href = "Teacher.html";
                    else
                        window.location.href = "Student.html";
                    window.event.returnValue = false;
                } else
                    alert(ret.info);
            });
    };
}]);