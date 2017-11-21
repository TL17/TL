var app = angular.module("student", []);
app.controller("student_ctrl", [$scope, $rootScope, function($scope, $rootScope, $http) {
    serverUrl = "http://123.207.6.234:8080/TL";
    postCfg = {
        headers: { "Content-Type": "application/x-www-form-urlencoded;charset=utf-8" },
        transformRequest: function (data) {
            return $.param(data);
        }
    };

    var courseID = "";

    $scope.set_course_id = function(id) {
        courseID = id;
    };

    $scope.init_selected_list = function() {
        $http.get(serverUrl+"/course/manage",{params:{userToken: window.localStorage['userToken']}})
            .success(function(ret) {
                // window.localStorage['detailID'] = "";
                // load_course_list(ret);
                angular.forEach(angular.element(document.querySelectorAll(".detail_btn")), function(item){
                    $compile(item)($scope);
                    $scope.detail_btn_click = function(detail_id) {
                        window.localStorage['detailID'] = detail_id;
                        window.location.href = "Course.html";
                        window.event.returnValue=false;
                    };
                });
            });
    };

    $scope.evaluate = function(score, comment) {
        $http.post(serverUrl+"course/evaluation/"+courseID,
            {score:score, comment:comment, courseID:courseID, userToken:localStorage['userToken']},
            postCfg)
            .success(function(ret) {
                if (ret.status)
                    alert("Submitted~");
                else
                    alert(ret.info);
            });

    }

}]);