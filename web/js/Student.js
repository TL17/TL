app.controller("student_ctrl", ['$scope', '$rootScope', '$http', '$modal', '$compile', function ($scope, $rootScope, $http, $modal, $compile) {
    var cid = "";

    $scope.init_selected_list = function () {
        $http.get(serverUrl + "/course/manage", {params: {userToken: window.localStorage['userToken']}})
            .success(function (ret) {
                var ele = angular.element(document.querySelector("#selected_list > ul"));
                ele.empty();

                if (ret.status) {
                    angular.forEach(ret.courses, function (c) {
                        ele.append("<li><p class=\"pcourse\">" + c.courseName
                            + "</p><div class=\"nei\">" + c.courseInfo
                            + "</div><div class=\"nei_bottom\"><p class=\"per100\">" + c.percent //
                            + "</p><button ng-click=\"show_evaluate_modal($event)\" data-id=\""
                            + c.courseID + "\"class=\"evaluate_btn btn btn-primary\">evalute</button></div></li>");
                    });
                } else
                    alert(ret.info);

                angular.forEach(angular.element(document.querySelectorAll(".evaluate_btn")), function (item) {
                    $compile(item)($scope);
                    $scope.show_evaluate_modal = function (event) {
                        var evaluate_modal_ctrl = function ($scope, $modalInstance) {
                            cid = $(event.target).data('id');
                            $scope.submit_evaluation = function (score) {
                                $http.post(serverUrl + "/course/evaluation/" + cid,
                                    {
                                        courseID: cid, userToken: window.localStorage['userToken'], score: score,
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

                });
            });
    };

    $scope.load_info = function () {
        load_personal_info($http);
    };


    $scope.submit_info = function (name, contact) {
        set_personal_info($http, name, contact);
    };

    $scope.addCourse = function() {
        window.location.href = "Courses.html";
    }

}]);