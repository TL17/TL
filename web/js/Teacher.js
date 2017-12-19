app.controller("teacher_ctrl", ['$scope', '$rootScope', '$http', '$modal', '$compile', function ($scope, $rootScope, $http, $modal, $compile) {
    $scope.submit_info = function (name, contact) {
        set_personal_info($http, name, contact);
    };

    $scope.load_info = function () {
        if (window.localStorage['type'] === 'teacher') {
            $("#change_info_btn").show();
            $("#add_course_btn").show();
        }else{
            $("#change_info_btn").hide();
            $("#add_course_btn").hide();
        }

        load_personal_info($http);
    };

    $scope.submit_course = function(){
        if($("#courseName").val().length<=0||$("#courseInfo").html().length<=0||$("#coursePlan").html().length<=0){
            alert("请补全信息");
        }else{
            $http.post(serverUrl+"/course/add",
                {
                    userToken:window.localStorage['userToken'],
                    account:window.localStorage['account'],
                    courseName:$("#courseName").val(),
                    courseInfo:$("#courseInfo").html(),
                    coursePlan:$("#coursePlan").html()
                }, postCfg)
                .success(function(ret) {
                    if (ret.status) {
                        addCourse($("#courseName").val(), ret.courseID);
                    } else
                        alert(ret.info);
                });

        }
    };

    $scope.load_courses = function() {
        $http.post(serverUrl+"/course/manage", {userToken:window.localStorage['userToken'], account:window.localStorage['account']}, postCfg)
            .success(function(ret) {
                if (ret.status)
                    addCourse(ret.courses.courseName, ret.courses.courseName);
                else
                    alert(ret.info);
            });
    }
}]);

function addCourse(text, id){
    $(".content #c_b_b").append("<p class=\"c_d_p\" data-id=\""+id+"\">"+text+"</p>");
}
