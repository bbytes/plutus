/*
 * User Controller
 */
angular.module('plutusApp').controller('adminCtrl', function ($scope, $rootScope, appNotifyService, adminService) {

  $rootScope.bodyClass = 'standalone'; //avoiding background image
//loading user details
    $scope.init = function () {
        $scope.update = false;
        adminService.getAllUser().then(function (response) {
            if (response.success) {
                $scope.allUsers = response.data;
            }
        });
    }
    $scope.createUser = function (isValid) {

        if (!isValid) {
            appNotifyService.error('Please enter a valid email and username.');
            return false;
        }
        if ($scope.admin === null) {
            appNotifyService.error('Please enter a valid email and username.');
        } else {

            input = {
                "email": $scope.email,
                "name": $scope.name
            };
            adminService.addingUser(input).then(function (response) {

                if (response.success) {
                    appNotifyService.success('User created successfully');
                    $scope.email='';
                    $scope.name='';
                    $scope.init();
                }
            }, function (error) {
                appNotifyService.error('Error while adding users. Please check back again!');
            });
        }
    };


    $scope.deleteUser = function (email, index) {
        adminService.deleteUserByEmail(email).then(function (response) {
            if (response.success) {
                $scope.allUsers.splice(index, 1);


                appNotifyService.success('User has been sucessfully deleted.');
            }
        });
    };


});
