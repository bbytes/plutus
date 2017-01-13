
/* popup controller*/
angular.module('plutusApp').controller('licenseCtrl', function ($scope, $rootScope, $state, options, $uibModalInstance, $uibModal, productService, appNotifyService, pricingService, adminService) {
    $rootScope.bodyClass = 'standalone'; //avoiding background image
    $scope.title = options.title;
    $scope.productId = options.productId;
    $scope.editLicense = options.editLicense;
    $scope.meteredRows = [];
    $scope.init = function () {
        $scope.meteredRows.push({key: ''});

    };
    // Remove Rows
    $scope.removeMeteredRows = function (index) {
        $scope.meteredRows.splice(index, 1);

    };

//adding dynamic rows 
    $scope.addMeteredDetails = function () {

        $scope.meteredRows.push({key: ''});

    };
    $scope.addFields = function ()
    {
        alert($scope.meteredRows);
        $scope.ok();
    }

    $scope.ok = function () {
        $uibModalInstance.close($scope.meteredRows);
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

});
