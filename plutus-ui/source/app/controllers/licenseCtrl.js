
/* popup controller*/
angular.module('plutusApp').controller('licenseCtrl', function ($scope, $rootScope, $state, options, $uibModalInstance, $uibModal, productService, appNotifyService, pricingService, adminService, subscriptionService) {
    $rootScope.bodyClass = 'standalone'; //avoiding background image
    $scope.title = options.title;
    $scope.productId = options.productID;
    $scope.editLicense = options.editLicense;
    $scope.subscriptionId = options.subscriptionId;
    $scope.addRows = [];
    $scope.featureFieldsdRows = [];


    $scope.init = function () {
        $scope.addRows.push({key: ''});
        productService.getFeatureFields($scope.productId).then(function (response) {
            if (response.success) {
                $scope.featureFields = response.data;
                angular.forEach($scope.featureFields, function (value, key) {
                    var field = value;
                    $scope.selectedNodeObj = {
                        value: field,
                        key: ''
                    };
                    $scope.featureFieldsdRows.push($scope.selectedNodeObj);
                });
            }
        });
    };
  //subscription feture fields updated with value 

    $scope.editFields = function ()
    {
        $scope.item = {};
        $scope.featureFieldsdArray = [];
        angular.forEach($scope.featureFieldsdRows, function (value, key) {
            var key = value.key;
            var value1 = value.value;
            $scope.item[key] = value1;
        });
        input = {
            "productFeatureFields": $scope.item
        };

        $scope.featureFieldsdArray.push($scope.item);
        subscriptionService.addFeatureFields($scope.subscriptionId, $scope.item).then(function (response) {
            if (response.success) {
                appNotifyService.success("Feture Fields Updated Successfully");

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating Product.');
        });

        $scope.ok();
    }


    // Remove Rows
    $scope.removeRows = function (index) {
        $scope.addRows.splice(index, 1);

    };

//adding dynamic rows 
    $scope.addRowsDetails = function () {

        $scope.addRows.push({key: ''});

    };
    
    //product feture fields added 
    $scope.addFields = function ()
    {
        $scope.fetureArray = [];
        angular.forEach($scope.addRows, function (value, key) {
            $scope.fetureKey = value.key;
            $scope.fetureArray.push($scope.fetureKey);
            // var cost = value.cost;
            //$scope.item[key] = cost;
        });

        productService.addFeatureFields($scope.productId, $scope.fetureArray).then(function (response) {
            if (response.success) {

                appNotifyService.success("Feture Fields Added Successfully");

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating Product.');
        });

        $scope.ok();
    }

    $scope.ok = function () {
        $uibModalInstance.close($scope.addRows);
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

});
