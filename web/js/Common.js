var app = angular.module('myApp', ['ui.bootstrap']);
var serverUrl = "http://123.207.6.234:8080/TL";
var postCfg = {
    headers: { "Content-Type": "application/x-www-form-urlencoded;charset=utf-8" },
    transformRequest: function (data) {
        return $.param(data);
    }
};
function load_course_list(ret) {
    var ele = angular.element(document.querySelector("#coursesList>ul"));
    ele.empty();

    if (ret.status) {
        angular.forEach(ret.courses, function(c) {
            // alert(c.courseInfo+c.coursePlan);
            ele.append("<li>\n" +
                "\t\t\t\t\t<div class=\"li_box1\">\n" +
                "\t\t\t\t\t\t <input type=\"checkbox\" class=\"li_box_che\">\n" +
                "\t\t\t\t\t\t <p class=\"li_box_p\"></p>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t<div class=\"li_box2\">\n" +
                "\t\t\t\t\t\t<div class=\"input-group\" style=\"float: right;width: 30%;\">\n" +
                "\t\t\t\t\t\t    <input type=\"text\" style=\"float: right;text-align: center;\" class=\"form-control input-mini\" value=\"Score:9.0\" readonly=\"readonly\">" +
                "<span ng-click=\"detail_btn_click(" + c.courseID + ")\" class=\"detail_btn input-group-addon btn btn-primary\">Details</span>\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t<p contenteditable=\"true\" class=\"box_div_p\">"
                + c.courseInfo
                + "</p>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</li>");
        });
    } else
        alert(ret.info);
}