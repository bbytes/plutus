angular.module('plutusApp').controller('subscriptionInfoCtrl', function ($scope, $rootScope,productService, $uibModal, subscriptionService, appNotifyService) {
    /*declare variables */
    $scope.showStats = false;
    
//  Variales for pagination
    $scope.gap = 5;
    
    $scope.filteredItems = [];
    $scope.groupedItems = [];
    $scope.itemsPerPage = 5;
    $scope.pagedItems = [];
    $scope.currentPage = 0;
    $scope.init = function () {
        subscriptionService.getSubscriptions().then(function (response) {
            if (response.success) {
                $scope.subscriptionsList = response.data;
                   angular.forEach($scope.subscriptionsList, function (value) {
                    $scope.pricingPlans=value.pricingPlan.productPlanItemToCost;
                    $scope.custStatus=value.deactivate;
                   });
                
            };
            
     
         productService.getProduct().then(function (response) {
            if (response.success) {
                $scope.products = response.data;
                $scope.selectedProduct= $scope.products[0].id;
                //appNotifyService.info("success ");
            }
        });
        $scope.getTimePeriod();
    });
}
    

    $scope.getSubscriptionsForProd = function () {
        $scope.showStats = false;
        subscriptionService.getSubscriptionsForProd($scope.selectedProduct).then(function(response) {
            if (response && response.success) {
                  $scope.subscriptionsList = response.data;
                   angular.forEach($scope.subscriptionsList, function (value) {
                    $scope.pricingPlans=value.pricingPlan.productPlanItemToCost;
                    $scope.custStatus=value.deactivate;
                   });
            }else{
                appNotifyService.error("Something went wrong... ");
            }
          
        });

    };
    $scope.getCustomerDetails = function (subscriptionKey) {
        $scope.showStats = true;
        subscriptionService.getCustomerDetails(subscriptionKey,$scope.selectedTime).then(function(response) {
            if (response && response.success) {
                  $scope.customerStats = response.data;               
                   }   
        });

    };
    
    $scope.getTimePeriod = function () {
         $scope.showStats = false;
         subscriptionService.getAllTimePeriods().then(function(response) {
            if (response && response.success) {
                  $scope.allTimePeriods = response.data;
                    if ($scope.selectedTime == undefined) {
                    $scope.selectedTime = $scope.allTimePeriods[2];
                    $scope.selectedTime = $scope.selectedTime;
                } else {
                    $scope.selectedTime= $scope.selectedTime;
                }
            }
          
        });
    }

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
