app.controller("courses_ctrl", ['$scope', '$rootScope', '$http', '$compile', function($scope, $rootScope, $http, $compile) {
    serverUrl = "http://123.207.6.234:8080/TL";
    // postCfg = {
    //     headers: { "Content-Type": "application/x-www-form-urlencoded;charset=utf-8" },
    //     transformRequest: function (data) {
    //         return $.param(data);
    //     }
    // };

    $scope.search = function(keyword) {
        $http.get(serverUrl+"/course/search",{params:{keyword: keyword}})
            .success(function(ret) {
                window.localStorage['detailID'] = "";
                load_course_list(ret);
                angular.forEach(angular.element(document.querySelector(".detail_btn")), function(item){
                    $compile(item)($scope);
                    $scope.detail_btn_click = function(detail_id) {
                        window.localStorage['detailID'] = detail_id;
                        window.location.href = "Course.html";
                        window.event.returnValue=false;
                    };
                });
            });
    };

    $scope.init_courses_list = function() {
        $http.get(serverUrl+"/course/manage",{params:{userToken: window.localStorage['userToken']}})
        .success(function(ret) {
            window.localStorage['detailID'] = "";
            load_course_list(ret);
            angular.forEach(angular.element(document.querySelectorAll(".detail_btn")), function(item){
                $compile(item)($scope);
                $scope.detail_btn_click = function(detail_id) {
                    window.localStorage['detailID'] = detail_id;
                    window.location.href = "Course.html";
                    window.event.returnValue=false;
                };
            });
        });
    }
}]);