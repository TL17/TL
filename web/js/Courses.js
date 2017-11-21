app.controller("courses_ctrl", ['$scope', '$rootScope', '$http', function($scope, $rootScope, $http) {
    serverUrl = "http://123.207.6.234:8080/TL";
    // postCfg = {
    //     headers: { "Content-Type": "application/x-www-form-urlencoded;charset=utf-8" },
    //     transformRequest: function (data) {
    //         return $.param(data);
    //     }
    // };

    $scope.init_courses_list = function() {
        $http.get(serverUrl+"/course/manage",{params:{userToken: window.localStorage['userToken']}})
        .success(function(ret) {
            var ele = angular.element(document.querySelector("#coursesList>ul"));

            if (ret.status) {
                angular.forEach(ret.courses, function(c) {
                    ele.append("<li>\n" +
                        "\t\t\t\t\t<div class=\"li_box1\">\n" +
                        "\t\t\t\t\t\t <input type=\"checkbox\" class=\"li_box_che\">\n" +
                        "\t\t\t\t\t\t <p class=\"li_box_p\"></p>\n" +
                        "\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t<div class=\"li_box2\">\n" +
                        "\t\t\t\t\t\t<div class=\"input-group\" style=\"float: right;width: 30%;\">\n" +
                        "\t\t\t\t\t\t    <input type=\"text\" style=\"float: right;text-align: center;\" class=\"form-control input-mini\" value=\"Score:9.0\" readonly=\"readonly\">" +
                        "<span data-id=\"" + c.courseID + "\" class=\"input-group-addon btn btn-primary\">Details</span>\n" +
                        "\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t<p contenteditable=\"true\" class=\"box_div_p\">" +
                        +c.courseInfo
                        + "</p>\n" +
                        "\t\t\t\t\t</div>\n" +
                        "\t\t\t\t</li>");
                });
            } else
                alert(ret.info);
        });
    }
}]);