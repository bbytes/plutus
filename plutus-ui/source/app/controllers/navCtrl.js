

angular.module('plutusApp').controller('navCtrl', function ($scope, $rootScope, $state, productService, pricingService, appNotifyService, $sessionStorage, $window, $filter, $window, $location, $uibModal, productService) {

    $scope.isActive = function (viewLocation) {
        var active = (viewLocation === $location.path());
        $scope.active = active;
        if ($scope.active == true)
        {
            $rootScope.activeTab = viewLocation;
        }
        return active;

    };

    $scope.pricing = false;
    $scope.popUp = function () {
        productService.getProduct().then(function (response) {
            if (response.success) {
                $scope.product = response.data;
            }
        });

        productService.getBillingType().then(function (response) {
            if (response.success) {
                $scope.billingTypes = response.data;
                // $scope.selectedTimePeriod =  $scope.currency[0];
            }
        });

        pricingService.getCurrency().then(function (response) {
            if (response.success) {
                $scope.currencys = response.data;
            }
        });

        pricingService.getAllPricingPlans().then(function (response) {
            if (response.success) {
                $scope.allPricingPlans = response.data;
            }
        });
        if ($rootScope.activeTab == "/products") {
            var uibModalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'app/partials/addPopup.html',
                controller: 'addPopupCtrl',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    options: function () {
                        return {
                            "title": 'Add Product',
                            "data": $scope.product,
                            "pricing": $scope.pricing
                        };
                    }
                }
            });

            uibModalInstance.result.then(function (selection) {

                $rootScope.popUpStatus = true;
                // $state.go('products');
                // alert($rootScope.popUpStatus);

            });
        } else if ($rootScope.activeTab == "/pricing-plans") {

            var uibModalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'app/partials/addPopup.html',
                controller: 'addPopupCtrl',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    options: function () {
                        return {
                            "title": 'Add Pricing',
                            "data": $scope.product,
                            "pricing": true
                        };
                    }
                }
            });

            uibModalInstance.result.then(function (selection) {

                $rootScope.popUpStatus = true;
                //  $state.go('products');
                // alert($rootScope.popUpStatus);

            });
        } else if ($rootScope.activeTab == "/admin") {

            var uibModalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'app/partials/addPopup.html',
                controller: 'addPopupCtrl',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    options: function () {
                        return {
                            "title": 'Admin',
                            "adminStatus": true

                        };
                    }
                }
            });

            uibModalInstance.result.then(function (selection) {

                $rootScope.popUpStatus = true;
                //  $state.go('products');
                // alert($rootScope.popUpStatus);

            });
        }

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