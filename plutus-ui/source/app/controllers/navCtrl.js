

angular.module('rootApp').controller('navCtrl', function ($scope, $rootScope, $state, productService, appNotifyService, $sessionStorage, $window, $filter, $window, $location) {

    $scope.isActive = function (viewLocation) {
        var active = (viewLocation === $location.path());
        return active;

    };

    $scope.logout = function () {
        // delete $window.sessionStorage.token;
        // Clear local storage                
        $window.sessionStorage.clear();
        //  $window.localStorage.clear();
        // $rootScope.loggedStatus = false;

        // $sessionStorage.remove('userInfo');
        $state.go('login');
//        loginService.logout().then(function (response) {
//
//            $location.path("login");
//        });
    };



    $scope.setAdminTab = function (section) {
        if (section == 'billinginfo') {
            $rootScope.adminTab = 'billinginfo';
            $state.go('billinginfo');
        } else if (section == 'invoicedetails') {

            $rootScope.adminTab = 'invoicedetails';

        } else {
            $rootScope.adminTab = 'productplans';
        }
    };
    $scope.activeAdminTab = function (section) {
        return (section === $rootScope.adminTab) ? true : false;
    };


    $(document).ready(function () {
        $('.dropdown-toggle').dropdown();
    });


    $scope.open = function () {

        var modalInstance = $modal.open({
            templateUrl: 'app/partials/feedback.html',
            controller: 'feedbackCtrl',
            resolve: {
                items: function () {
                    return $scope.items;
                }
            }
        });

        modalInstance.result.then(function (selectedItem) {
            $scope.selected = selectedItem;
        }, function () {
        });
    };

});