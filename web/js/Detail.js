app.controller("detail_ctrl", ['$scope', '$rootScope', '$http', '$modal', function ($scope, $rootScope, $http, $modal) {
    var cid = window.localStorage['detailID'];
    var token = window.localStorage['userToken'];
    $scope.isStudent = (window.localStorage['type']!='teacher');
    $scope.isSelected = false;

    $scope.init_course_detail = function () {
        $http.get(serverUrl + "/course/manage", {
            params: {
                userToken: window.localStorage['userToken'],
                account: window.localStorage['account']
            }
        })
            .success(function (ret) {
                angular.forEach(ret.courses, function(c) {
                    if (c.courseID == cid) {
                        $scope.isSelected = true;
                    }
                });
            });
        $http.get(serverUrl + "/course/detail/" + cid, {params: {courseID: cid}})
            .success(function (ret) {
                if (ret.status) {
                    angular.element(document.querySelector("#courseInfo")).text(ret.course.courseInfo);
                    angular.element(document.querySelector("#coursePlan")).text(ret.course.coursePlan);
                    angular.element(document.querySelector("#courseName")).text(ret.course.courseName);
                    angular.element(document.querySelector("#is_selected")).attr("checked", ret.course.selected);
                    angular.element(document.querySelector("#teacber_info_btn")).data("id", ret.course.teacherID);
                } else
                    alert(ret.info);
            });
    };

    $scope.select_course = function () {
        $http.post(serverUrl + "/course/select/" + cid, {
                courseID: cid,
                userToken: token,
                account: window.localStorage['account']

        }, postCfg)
            .success(function (ret) {
                ret.status = true;
                if (ret.status) {
                    angular.element(document.querySelector("#is_selected")).attr("checked", true);
                    alert("Course selected~");
                } else
                    alert(ret.info);
            });
    }

    $scope.show_comment = function () {
        $http.get(serverUrl + "/course/evaluation/" + cid, {
            params: {
                courseID: cid,
                userToken: token,
                account: window.localStorage['account']
            }
        })
            .success(function (ret) {
                //ret = {info:"succeed", status:true, evaluations: [{score:9.0, comment:"1111"}, {score:9.1, comment:"2222"}, {score:9.2, comment:"33333"}, {score:9.3, comment:"4444"},]}

                if (ret.status) {
                    var modal_ctrl = function ($scope, $modalInstance, evaluations) {
                        $scope.load_comment = function () {
                            var count = 1;
                            var total = 0.0;
                            var ele = angular.element(document.querySelector("#model_comment_body"));
                            angular.forEach(evaluations, function (e) {
                                var score = parseFloat(e.score)
                                ele.append(count + "---Score:" + score.toFixed(1) + "<br/>" + e.comment + "<hr/>");
                                total += parseFloat(e.score);
                                count++;
                            });
                            count--;
                            var avg;
                            if (count == 0) {
                                avg = 0;
                            } else {
                                avg = (total / count).toFixed(1);
                            }

                            angular.element(document.querySelector("#model_comment_avg_score")).attr("value", avg);
                        };

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
                    modalInstance.opened.then(function () {//模态窗口打开之后执行的函数

                    });
                } else
                    alert(ret.info);
            })
    };

    $scope.load_material_list = function () {
        //window.localStorage['courseID'] = 1;
            $http.get(serverUrl+"/material",{params:{
                userToken: window.localStorage['userToken'],
                account: window.localStorage['account'],
                courseID: window.localStorage['detailID']
            }})
                .success(function(ret){
                            if (ret.status) {
                                $scope.materials = ret.materials;
                            } else {
                               alert(ret.info);
                            }
                })
                .error(funcFail);


        function funcFail(ret) {
            alert(ret.info);
        }
    };

    var uploadFilePostCfg = {
        //1. 不能不加header，否则angular将其视为"application/JSON"
        //2. 不能加header为"multipart/form-data"，因为该header需要自动加（可能是“浏览器”自动加），否则触发后台的“no multipart boundary”错误
        //3. undefined不加引号
        //4. function (value, key)回调函数中的value和key不能颠倒
        headers: { "Content-Type": undefined},
        transformRequest: function(data, headersGetter){
            var formData = new FormData();
            angular.forEach(data, function (value, key) {
                formData.append(key, value);
            });
            return formData;
        }
    };

    //上传的onclick函数
    $scope.doUploadFile = function() {
        var files = document.getElementById('fileToUpload').files;
        var account = 1;
        var userToken = 1;
        var courseID = 1;
        var file = files[0];

        if (files === undefined || files.length <= 0) {
            alert("未选择文件");
            return;
        }

        $http.post(serverUrl+"/upload_material",{
            account: account,
            userToken: userToken,
            courseID: courseID,
            file: file}, uploadFilePostCfg)
            .success(funcSucc)
            .error(funcFail);

        function funcSucc(ret) {
            $http.get(serverUrl+"/material",{params:{
                account: account,
                userToken: userToken,
                courseID: courseID}})
                .success(function(ret){
                    if (ret.status) {
                        $scope.materials = ret.materials;
                    } else {
                        alert(ret.info);
                    }
                })
                .error(funcFail);
        }

        function funcFail(ret) {
            alert(ret.info);
        }
    };//$scope.doUploadFile

    $scope.init_courses_list = function () {
        $http.get(serverUrl + "/course/manage", {
            params: {
                userToken: window.localStorage['userToken'],
                account: window.localStorage['account']
            }
        })
            .success(function (ret) {
                window.localStorage['materialID'] = "";
                window.localStorage['materialName'] = "";
                var ele = angular.element(document.querySelector("#materialsList"));
                ele.empty();

                if (ret.status) {
                    angular.forEach(ret.materials, function (m) {
                        ele.append("<li style=\"text-align: center;width: 100%;\" " +
                            "ng-click=\"detail_btn_click(" + m.materialID + ", " + m.materialName + ")\">" +
                            "<a data-toggle=\"tab\">" + m.materialName + "</a></li>");
                    });
                    angular.element(document.querySelector("#materialsList>li")).attr('class', 'active');
                } else
                    alert(ret.info);

                angular.forEach(angular.element(document.querySelectorAll(".detail_btn")), function (item) {
                    $compile(item)($scope);
                    $scope.detail_btn_click = function (material_id, material_name) {
                        window.localStorage['materialID'] = material_id;
                        window.localStorage['materialName'] = material_name;
                    };
                });
            });
    };

    $scope.show_teacher_message = function () {
        window.localStorage['teacherID'] = angular.element(document.querySelector("#teacber_info_btn")).data("id");
        window.location.href = "Teacher.html";
    }
}]);
