app.controller("student_ctrl", ['$scope', '$rootScope', '$http', '$modal', '$compile', function ($scope, $rootScope, $http, $modal, $compile) {
    var cid = "";
    $scope.courses = [];

    $scope.show_evaluate_modal = function (cid) {
        var evaluate_modal_ctrl = function ($scope, $modalInstance) {
            $scope.submit_evaluation = function (score) {
                $http.post(serverUrl + "/course/evaluation/" + cid,
                    {
                        courseID: cid, userToken: window.localStorage['userToken'],
                        score: score, account:window.localStorage['account'],
                        comment: angular.element(document.querySelector("#student_evaluate_comment")).text()
                    },
                    postCfg)
                    .success(function (ret) {
                        if (ret.status) {
                            alert("Submit succeed~");
                            $modalInstance.dismiss('submit');
                        } else
                            alert(ret.info);
                    });
            };
        };
        var modalInstance = $modal.open({
            templateUrl: 'evaluateModal.html',
            controller: evaluate_modal_ctrl
        });
        modalInstance.opened.then(function () {//模态窗口打开之后执行的函数

        });
    };

    $http.get(serverUrl + "/course/manage", {params: {userToken: window.localStorage['userToken'], account:window.localStorage['account']}})
        .success(function (ret) {
            // var ele = angular.element(document.querySelector("#selected_list > ul"));
            // ele.empty();

            if (ret.status) {
                $scope.courses = ret.courses;
            } else
                alert(ret.info);

            angular.forEach(angular.element(document.querySelectorAll(".evaluate_btn")), function (item) {
                $compile(item)($scope);

            });
        });



    $scope.detail_btn_click = function(detail_id) {
        window.localStorage['detailID'] = detail_id;
        window.location.href = "Course.html";
        window.event.returnValue=false;
    };

    $scope.load_info = function () {
        load_personal_info($http, false);
    };


    $scope.submit_info = function (name, contact) {
        set_personal_info($http, name, contact, false);
    };

    $scope.addCourse = function() {
        window.location.href = "Courses.html";
    }

}]);
