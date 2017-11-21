var app = angular.module('sign', []);
app.controller('sign_ctrl', ['$scope', '$rootScope', function($scope, $rootScope, $http) {
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
    $scope.sign_up = function(id,acc,pwd) {
        // alert(id+" "+acc+" "+pwd);
        $http.post(serverUrl+"/sign_up",{schoolID: id, account:acc, password: pwd},postCfg)
            .success(function(ret){
                if (ret.status)
                    alert("Sign up succeed~");
                else
                    alert(ret.info);
            });
    }

    $scope.sign_in = function(acc,pwd) {
        $rootScope.userToken = "hxy";
        window.location.href = "Student.html";
        alert(acc+" "+pwd+" "+$rootScope.userToken);
        window.event.returnValue=false;
           // $http.post(serverUrl+"/sign_in",{account:acc, password:pwd},postCfg)
			// 	.success(function(ret) {
			// 	    if (ret.status) {
			// 			$rootScope.userToken = ret.userToken;
           //             $window.location.href = "Student.html";
           //         } else
			// 	        alert(ret.info);
			// 	});
    }
}]);