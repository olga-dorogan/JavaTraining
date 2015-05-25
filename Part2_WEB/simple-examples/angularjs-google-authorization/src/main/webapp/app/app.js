/**
 * Created by olga on 24.05.15.
 */
var app = angular.module('example-angular-google', [
        'ngRoute',
        'angular-google-gapi']
);

app.controller('HomeController', function clientList($scope, GAuth, GData, $location, $http, $rootScope) {
        $scope.message = "You are on home page";
        if (GData.isLogin()) {
            $location.path('/logged');
        }
        $scope.doLogin = function () {
            console.log("doLogin");
            GAuth.login().then(function () {
                    $http
                        .get('http://localhost:8080/angular-google-1.0/role', {
                            params: {
                                email: $rootScope.gapi.user.email
                            }
                        })
                        .success(function (response) {
                            console.log("http get is successfully returned with role: "+response.role);
                            $location.path('/logged');
                        })
                        .error(function () {
                            console.log("http get is failed");
                            $location.path("/");
                        });
                }
            )
            ;
        };
    }
);

app.controller('LoggedController', function ($scope) {
    $scope.message = "You are logged";
});

app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'pages/home.html',
            controller: 'HomeController'
        })
        .when('/logged', {
            templateUrl: 'pages/logged.html',
            controller: 'LoggedController'
        })
        .otherwise({redirectTo: '/'});
});

app.run(['GAuth', 'GApi', '$rootScope', '$window', '$location',
    function (GAuth, GApi, $rootScope, $window, $location) {
        var CLIENT = '283637906279-1to8vgk462pfintjmkmbqfqq2qadld3p.apps.googleusercontent.com';
        var BASE;
        if ($window.location.hostname == 'localhost') {
            BASE = '//localhost:8080/angular-google-1.0';
        } else {
            BASE = '';
        }
        GApi.load('googleplus', 'v1', BASE);
        GAuth.setClient(CLIENT);
        GAuth.setScope('https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile');
        GAuth.checkAuth().then(
            function () {
                $location.path("/logged")
            },
            function () {
                $location.path("/")
            }
        );

        $rootScope.logout = function () {
            GAuth.logout().then(
                function () {
                    $location.path("/")
                });
        };
    }
]);
