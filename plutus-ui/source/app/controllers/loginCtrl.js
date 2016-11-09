/*
 *  Login Controller
 */
angular.module('plutusApp').controller('loginCtrl', function ($scope, $rootScope, $state, loginService, appNotifyService, $sessionStorage, $window, $filter) {

    $rootScope.bodyClass = 'body-standalone';
    $rootScope.feedbackClass = 'feedback-log';

    $scope.submitLoginForm = function (isValid) {

        // Validating login form
        if (!isValid) {
            appNotifyService.error('Please enter username and password');
            return false;
        }
        // Calling login service
        loginService.login($scope.username, $scope.password).then(function (response) {
                if (response.headers["auth-token"]) {
                $rootScope.authToken = response.headers["auth-token"];
                $rootScope.loggedStatus = true;
                $rootScope.loggedInUser = $scope.username;
                $rootScope.userRole = response.data.userRole;
                $rootScope.userName = response.data.email;
                $rootScope.authToken = response.headers["auth-token"];             

                var userInfo = {
                    accessToken: response.headers["auth-token"],
                    email: $rootScope.loggedInUser,
                    name: $rootScope.userName,
                    userRoles: $rootScope.userRole,                 
                };

                $sessionStorage.userInfo = userInfo;
                $rootScope.showWelcomeMessage = true;
                $state.go('products');

      
        }else {
                //Login failed. Showing error notification
             
                $scope.loginButtonText = 'Login';
                $scope.loginButtonEnabled = true;
            }

        }, function (error) {
            appNotifyService.error('Invalid Username or Password');
        });
    };

//    $scope.logout = function () {
//        delete $window.sessionStorage.token;
//
//        $sessionStorage.remove('userInfo');
//        loginService.logout().then(function (response) {
//
//            $location.path("login");
//        });
//    };
});
