angular.module('plutusApp').controller('subCtrl', function ($scope, options,$rootScope, $uibModal,$uibModalInstance) {
    /*declare variables */
    $scope.pricing=options.test;
    
     
    $scope.cancel = function () {

        $uibModalInstance.close();

    };
    
});
