app.controller("detail_ctrl", ['$scope', '$rootScope', '$http', '$modal', function($scope, $rootScope, $http, $modal) {
    var cid = window.localStorage['detailID'];

    $scope.init_course_detail = function() {
        $http.get(serverUrl+"/course/detail/"+cid,{params:{courseID: cid}})
            .success(function(ret) {
                if (ret.status) {
                    angular.element(document.querySelector("#courseInfo")).text(ret.course.courseInfo);
                    angular.element(document.querySelector("#coursePlan")).text(ret.course.coursePlan);
                    angular.element(document.querySelector("#courseName")).text(ret.course.courseName);
                } else
                    alert(ret.info);
            });
    }

    $scope.show_comment = function() {
        $http.get(serverUrl+"/course/evaluation/"+cid, {params: {courseID: cid, userToken: window.localStorage['detailID']}})
            .success(function(ret) {
                ret = {info:"succeed", status:true, evaluations: [{score:9.0, comment:"1111"}, {score:9.1, comment:"2222"}, {score:9.2, comment:"33333"}, {score:9.3, comment:"4444"},]}

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
            })
    }
}]);
