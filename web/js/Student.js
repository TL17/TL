var app = angular.module("student", []);
app.controller("student_ctrl", [$scope, $rootScope, function($scope, $rootScope, $http) {
    serverUrl = "http://123.207.6.234:8080/TL";
    postCfg = {
        headers: { "Content-Type": "application/x-www-form-urlencoded;charset=utf-8" },
        transformRequest: function (data) {
            return $.param(data);
        }
    };


}]);