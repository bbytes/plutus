
/* Product controller*/
angular.module('plutusApp').controller('productCtrl', function ($scope, $rootScope, $state, productService, appNotifyService, $sessionStorage, $window, $filter, toaster, $uibModal) {
    $scope.emails = [];
    $scope.update = false;
    $rootScope.bodyClass = 'standalone'; //avoiding background image
//loading products
//listening product data from popup
    $scope.$on('product', function (response, data) {
        $scope.product = data;
        $state.reload();
    });
  $scope.addLicense = function (productId) {
     //  $state.go('license')
        var uibModalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'app/partials/license.html',
            controller: 'licenseCtrl',
            backdrop: 'static',
            size: 'md',
            resolve: {
                options: function () {
                    return {
                        "title": 'Add License Fields',
                        "productID":productId
                        
                    };
                }
            }
        });

        uibModalInstance.result.then(function (selection) {

            $rootScope.popUpStatus = true;
            //   $state.go('products');
            //  alert($rootScope.popUpStatus);

        });
    };
    $scope.init = function () {
        $scope.update = false;
        productService.getProduct().then(function (response) {
            if (response.success) {
                $scope.product = response.data;
            }
        });

        productService.getBillingType().then(function (response) {
            if (response.success) {
                $scope.billingTypes = response.data;
                $rootScope.billingData = $scope.billingTypes;
                // $scope.selectedTimePeriod =  $scope.currency[0];
            }
        });
    }
//adding products
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
            "productTeamEmails": $scope.emails,
            "billingType": $scope.billingType,
        };

        productService.add(input).then(function (response) {
            if (response.success) {
                $scope.init();
                $scope.clear();
                appNotifyService.success("Product Added Successfully");

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating Product.');
        });
    };
//clear scope values
    $scope.clear = function () {
        if ($scope.update == true) {
            $scope.description = '';
            $scope.email = '';
            $scope.billingType = '';
        } else {
            $scope.productName = '';
            $scope.description = '';
            $scope.email = '';
            $scope.billingType = '';
        }
    };
    //edit product
    $scope.edit = function (productId) {


        var uibModalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'app/partials/addPopup.html',
            controller: 'addPopupCtrl',
            backdrop: 'static',
            size: 'md',
            resolve: {
                options: function () {
                    return {
                        "title": 'Edit Product Details',
                        "data": $scope.product,
                        "status": true,
                        "productId": productId
                    };
                }
            }
        });

        uibModalInstance.result.then(function (selection) {

            $rootScope.popUpStatus = true;
            //   $state.go('products');
            //  alert($rootScope.popUpStatus);

        });
//        $scope.update = true;
//        $scope.email = [];
//        angular.forEach($scope.product, function (item) {
//            if (item.id == productId) {
//                $scope.productName = item.name;
//                $scope.description = item.desc;
//                $scope.billingType=item.billingType;
//                angular.forEach(item.productTeamEmails, function (value) {
//                    $scope.email.push(value);
//                });
//
//            }
//        });
    };
    //update product
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
                $scope.init();
                $scope.clear();
                appNotifyService.success("Product Updated Successfully");

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating product.');
        });
    };
    //delete product  
    $scope.delete = function (productId) {

        productService.deleteProduct(productId).then(function (response) {
            if (response.success) {

                $scope.init();
                appNotifyService.success("Product Deleted Successfully");

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating product.');
        });
    };
});
