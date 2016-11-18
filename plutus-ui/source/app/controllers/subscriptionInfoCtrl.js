angular.module('plutusApp').controller('subscriptionInfoCtrl', function ($scope, $rootScope,productService, $uibModal, subscriptionService, appNotifyService) {
    /*declare variables */
    $scope.showStats = false;
     $scope.pricingPlans=[];
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
                    if(value.pricingPlan && value.pricingPlan.productPlanItemToCost){   
                     $scope.pricingPlans=(value.pricingPlan.productPlanItemToCost);
                    }
                   
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
                    angular.forEach($scope.customerStats, function (value) {
                   // $scope.pricingPlans=value.pricingPlan.productPlanItemToCost;
                    $scope.dataStats=value.stats;
                    $scope.entryDate=value.entryDate;
                     $scope.subKey=value.subscriptionKey;
                    
                   });
                 // $scope.dataStats=$scope.customerStats.stats;
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

    $scope.deActivateCust = function (subscriptionkey) {
        subscriptionService.activateDeactivate(subscriptionkey).then(function(response) {
            if (response && response.success) {
                  $scope.customerStats = response.data;
                  $scope.init();
                }
            else{
                    appNotifyService.error(response.message);
            }
        });
    };
    $scope.openWizard = function (pricingPlan,itemcost) {
        $uibModal.open({
            animation: true,
            templateUrl: 'app/partials/wizard-modal.html',
            controller: 'subCtrl',
            backdrop: 'static',
            size: 'lg',
            resolve: {
                options: function () {
                    return {
                        "title": 'Plans',
                        "list": pricingPlan,
                         "test": itemcost
                    };
                }
            }
        }).result.then(function (status) {
            if (status) {

            }
        });
    };
  
});
