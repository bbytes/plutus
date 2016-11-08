

angular.module('rootApp').controller('productCtrl', function ($scope, $rootScope, $state, productService, appNotifyService, $sessionStorage, $window, $filter) {


    $scope.emails = [];
    $scope.update = false;

    $scope.init = function () {

        productService.getProduct().then(function (response) {
            if (response.success) {
                $scope.product = response.data;
//                $scope.allprojects.unshift($scope.project);
//                $scope.project = '';
//                $scope.newList = '';
                appNotifyService.info("success ");

            }
        });

    }

    $scope.createProduct = function (product) {
        if (!product) {
            appNotifyService.error('Please enter a valid product name');
            return false;
        }
        angular.forEach($scope.email, function (item) {

            $scope.emails.push(item.text);
        });

        input = {
            "id": $scope.productName,
            "name": $scope.productName,
            "desc": $scope.description,
            "productTeamEmails": $scope.emails
        };

        productService.add(input).then(function (response) {
            if (response.success) {
                $scope.product = response;
//                $scope.allprojects.unshift($scope.project);
//                $scope.project = '';
//                $scope.newList = '';
                appNotifyService.info("success ");

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating project.');
        });
    };

    $scope.clear = function () {

        $scope.productName = '';
        $scope.description = '';
        $scope.email = '';
        $scope.billingType = '';

    };
    $scope.edit = function (productId) {
        $scope.update = true;
        $scope.email = [];
        angular.forEach($scope.product, function (item) {
            if (item.id == productId) {
                $scope.productName = item.name;
                $scope.description = item.desc;

                angular.forEach(item.productTeamEmails, function (value) {
                    $scope.email.push(value);
                });

            }
        });

    };
     $scope.updateProduct = function (product) {
        if (!product) {
            appNotifyService.error('Please enter a valid product name');
            return false;
        }
        angular.forEach($scope.email, function (item) {

            $scope.emails.push(item.text);
        });

        input = {
            "id": $scope.productName,
            "name": $scope.productName,
            "desc": $scope.description,
            "productTeamEmails": $scope.emails
        };
        productService.updatePro(input).then(function (response) {
            if (response.success) {
              
//                $scope.allprojects.unshift($scope.project);
//                $scope.project = '';
//                $scope.newList = '';
                appNotifyService.info("success ");

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating project.');
        });
    };
    
     $scope.delete = function (productId) {
      
        productService.deleteProduct(productId).then(function (response) {
            if (response.success) {
              
//                $scope.allprojects.unshift($scope.project);
//                $scope.project = '';
//                $scope.newList = '';
                appNotifyService.info("success ");

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating project.');
        });
    };
});
