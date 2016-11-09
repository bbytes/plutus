angular.module('plutusApp').controller('subscriptionInfoCtrl', function ($scope, $rootScope, $uibModal, subscriptionService, appNotifyService) {

    $scope.init = function () {
        subscriptionService.getSubscriptions().then(function (response) {
            if (response.success) {
                $scope.subscriptionsList = response.data;
            }
        });
    };

    $scope.getSubscriptionsForProd = function () {
        subscriptionService.getSubscriptionsForProd($scope.selectedProduct).then(function(response) {
            if (response && response.success) {
                  $scope.productSubscriptions = response.data;
            }
          
        });

    };

    $scope.deleteSubscription = function (index) {
        var shift = $scope.rowShiftCollection[index];
        var uibModalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'app/partials/admin-shift-add-modal.html',
            controller: 'addShiftModalInstanceCtrl',
            backdrop: 'static',
            resolve: {
                options: function () {
                    return {
                        "action": 'delete',
                        "data": shift
                    };
                }
            }
        });

        uibModalInstance.result.then(function (status) {
            if (status) {
                $scope.rowShiftCollection.splice(index, 1);
                appNotifyService.success('message.shiftDeleteConfirm');
            } else {
                appNotifyService.error('message.shiftDeleteError');
            }
        });
    };
});
