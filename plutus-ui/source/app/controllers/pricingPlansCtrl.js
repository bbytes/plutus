

angular.module('plutusApp').controller('pricingPlansCtrl', function ($scope, $rootScope, $state, productService, appNotifyService, $sessionStorage, $window, $filter,pricingService) {
    $scope.emails = [];
    $scope.update = false;
     $scope.pricingArray=[]
     $scope.newObject = {};
//loading Products and currency
    $scope.init = function () {
        $scope.update = false;
        productService.getProduct().then(function (response) {
            if (response.success) {
                $scope.product = response.data;  
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
         //To get all billing Periods
          pricingService.getAllBillings().then(function (response) {
            if (response.success) {
                $scope.billingPeriods = response.data;
            }
        });
    }
    //dispalying symbol for corresponding currency
    $scope.selectLabel = function () {
        $scope.cur=$scope.currency;
       if($scope.cur==="USD")
       {
            $scope.symbol='$';
       }else if($scope.cur==="INR")
       {
          $scope.symbol='Rs';
       }
    }
   //displaying pricing Details based on product
    $scope.selectPricingDetails = function () {
        $scope.pricingArray=[];
     $scope.productId=$scope.productName;
        pricingService.getPricingdetailsById($scope.productId).then(function (response) {
            if (response.success) {
                $scope.pricingdetails=response.data;
                 angular.forEach($scope.product, function (val) {
                     if($scope.productId==val.id)
                     {
                   $scope.type=val.billingType;
                   
                     }
                    });
                
                 var priceObject = {};
        angular.forEach($scope.pricingdetails, function (val) {
                    priceObject = {
                        "type": val,
                        "cost":0.0,
                    }
                    $scope.pricingArray.push(priceObject);
                    });
            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating project.');
        });
    };
//adding Pricing
    $scope.createPricing = function () {
        $scope.item={};
       if($scope.pricingdetails.length>0){
          angular.forEach($scope.pricingArray, function (value,key) {
            var type=value.type;
           var cost=value.cost;   
           $scope.item[type] = cost;
        });
    }else{ 
        $scope.type=$scope.pricingType;
         $scope.cost=$scope.priceCost;
           $scope.item[$scope.type] = $scope.cost;
    }
       
        $scope.productDetails=[];
       
        angular.forEach($scope.product, function (item) {
            if($scope.productName==item.id)
            {
            $scope.productDetails=item;
        }
        });
        input = {
            "id": $scope.planName,
            "name": $scope.planName,
            "product": $scope.productDetails,
            "desc": $scope.description,
            "productPlanItemToCost": $scope.item,
             "desc":$scope.description,
            "currency":$scope.currency,
            "appProfile":null,
            "billingCycle":$scope.billing,
            "discount":null
        };

        pricingService.addPricing(input).then(function (response) {
            if (response.success) {
                $scope.init();
                 $scope.clear();
               appNotifyService.success("Pricing Added Successfully");

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating project.');
        });
    };
//clear scope values
    $scope.clear = function () {
           $scope.planName = "";
                $scope.productName="";
                $scope.description ="";
                $scope.currency=""; 
                $scope.billing="";
    };
    //edit pricing
    $scope.edit = function (pricingId,productId) {
        $scope.update = true;
        $scope.email = [];
        angular.forEach($scope.allPricingPlans, function (item) {
            if (item.id == pricingId) {
                $scope.planName = item.name;
                $scope.productName=item.product.name;
                $scope.description = item.desc;
                $scope.currency=item.currency;
                $scope.billing=item.billingCycle;

            }
        });
    };
    //update pricing
    $scope.updatePricing = function () {
        
         if($scope.pricingdetails.length>0){
          angular.forEach($scope.pricingArray, function (value,key) {
            var type=value.type;
           var cost=value.cost;   
           $scope.item[type] = cost;
        });
    }else{ 
        $scope.type=$scope.pricingType;
         $scope.cost=$scope.priceCost;
           $scope.item[$scope.type] = $scope.cost;
    }
       
        angular.forEach($scope.product, function (item) {
            if($scope.productName==item.id)
            {
            $scope.productDetails=item;
        }
        });
      
        input = {
            "id": $scope.planName,
            "name": $scope.planName,
            "product": $scope.productDetails,
            "productPlanItemToCost":$scope.item,
            "desc":$scope.description,
            "currency":$scope.currency,
            "appProfile":null,
            "billingCycle":null,
            "discount":null
        };
        pricingService.updatePri(input).then(function (response) {
            if (response.success) {
                $scope.init();
                $scope.clear();
                appNotifyService.success("Pricing Updated Successfully");

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating project.');
        });
    };
    //delete pricing by id  
    $scope.delete = function (productId) {

        pricingService.deletePricing(productId).then(function (response) {
            if (response.success) {

                $scope.init();
             appNotifyService.success("Pricing Deleted Successfully");

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating project.');
        });
    };
    
   
    
  
});
