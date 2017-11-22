app.controller("student_ctrl", ['$scope', '$rootScope', '$http', '$modal', '$compile', function($scope, $rootScope, $http, $modal, $compile) {
    var cid = "";

    $scope.init_selected_list = function() {
        $http.get(serverUrl+"/course/manage",{params:{userToken: window.localStorage['userToken']}})
            .success(function(ret) {
                var ele = angular.element(document.querySelector("#selected_list > ul"));
                ele.empty();

                if (ret.status) {
                    angular.forEach(ret.courses, function(c) {
                        ele.append("<li><p class=\"pcourse\">"+c.courseName
                            +"</p><div class=\"nei\">"+c.courseInfo
                            +"</div><div class=\"nei_bottom\"><p class=\"per100\">"+c.percent //
                            +"</p><button ng-click=\"show_evaluate_modal($event)\" data-id=\""
                            +c.courseID+"\"class=\"evaluate_btn btn btn-primary\">evalute</button></div></li>");
                    });
                } else
                    alert(ret.info);

                angular.forEach(angular.element(document.querySelectorAll(".evaluate_btn")), function(item){
                    $compile(item)($scope);
                    $scope.show_evaluate_modal = function(event) {
                        var evaluate_modal_ctrl = function($scope, $modalInstance) {
                            cid = $(event.target).data('id');
                            $scope.submit_evaluation = function(score) {
                                $http.post(serverUrl+"/course/evaluation/"+cid,
                                    {courseID:cid, userToken:window.localStorage['userToken'], score:score,
                                        comment:angular.element(document.querySelector("#student_evaluate_comment")).text()},
                                    postCfg)
                                    .success(function(ret) {
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
                        modalInstance.opened.then(function(){//模态窗口打开之后执行的函数

                        });
                    };

                });
            });
    };

    $scope.evaluate = function(score, comment) {
        $http.post(serverUrl+"course/evaluation/"+cid,
            {score:score, comment:comment, courseID:cid, userToken:window.localStorage['userToken']},
            postCfg)
            .success(function(ret) {
                if (ret.status) {
                    var modal_ctrl = function($scope, $modalInstance, evaluations) {
                        $scope.load_comment = function() {
                            var count = 1;
                            var total = 0;
                            var ele = angular.element(document.querySelector("#model_comment_body"));
                            angular.forEach(evaluations,function(e) {
                                ele.append(count+"---Score:"+e.score.toFixed(1)+"<br/>"+e.comment+"<hr/>");
                                total += e.score;
                                count ++;
                            });
                            count--;
                            var avg = (total/count).toFixed(1);
                            angular.element(document.querySelector("#model_comment_avg_score")).attr("value", avg);
                        }

                        $scope.cancel = function () {
                            $modalInstance.dismiss('cancel');
                        };
                    };

                    var modalInstance = $modal.open({
                        templateUrl: 'commentModal.html',
                        controller: modal_ctrl,
                        resolve: {
                            evaluations: function () {
                                return ret.evaluations;
                            }
                        }
                    });
                    modalInstance.opened.then(function(){//模态窗口打开之后执行的函数

                    });
                } else
                    alert(ret.info);
            });

    }

    $scope.load_personal_info = function() {
        var acc = window.localStorage['account'];
        $http.get(serverUrl+"/account/"+acc, {params: {account: acc, userToken: window.localStorage['userToken']}})
            .success(function (ret) {
                ret.status = true;
                ret.perInfo = {name: "String",
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
    }

}]);