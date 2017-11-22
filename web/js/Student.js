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

    $scope.load_personal_info = function () {
        var acc = window.localStorage['account'];
        $http.get(serverUrl + "/account/" + acc, {params: {account: acc, userToken: window.localStorage['userToken']}})
            .success(function (ret) {
                ret.status = true;
                ret.perInfo = {
                    name: "String",
                    info: "String",
                    contact: "String"
                }
                if (ret.status) {
                    angular.element(document.querySelector("#name")).text(ret.perInfo.name);
                    angular.element(document.querySelector("#tel")).text(ret.perInfo.contact);
                    angular.element(document.querySelector("#info")).text(ret.perInfo.info);
                } else
                    alert(ret.info);
            })
    };


    $scope.submit_info = function (name, contact) {
        $http.post(serverUrl + "/account/" + window.localStorage['account'],
            {
                userToken: window.localStorage['userToken'], name: name, contact: contact,
                info: angular.element(document.querySelector("#student_modify_info")).text(),
                account: window.localStorage['account']
            },
            postCfg)
            .success(function (ret) {
                if (ret.status) {
                    var info = angular.element(document.querySelector("#student_modify_info")).text();
                    angular.element(document.querySelector("#name")).text(name);
                    angular.element(document.querySelector("#tel")).text(contact);
                    angular.element(document.querySelector("#info")).text(info);
                    alert("Submit succeed~");
                } else
                    alert(ret.info);
            });
    };

    $scope.addCourse = function() {
        window.location.href = "Courses.html";
    }

}]);