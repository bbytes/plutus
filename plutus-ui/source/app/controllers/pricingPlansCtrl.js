

angular.module('plutusApp').controller('pricingPlansCtrl', function ($scope, $rootScope, $state, productService, appNotifyService, $sessionStorage, $window, $filter,pricingService) {
    $scope.emails = [];
    $scope.update = false;
//loading products
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
                // $scope.selectedTimePeriod =  $scope.currency[0];
            }
        });
         pricingService.getPricingCycle().then(function (response) {
            if (response.success) {
                $scope.PricingCycle = response.data;
                // $scope.selectedTimePeriod =  $scope.currency[0];
            }
        });
         pricingService.getPaymentMode().then(function (response) {
            if (response.success) {
                $scope.paymentMode = response.data;
                // $scope.selectedTimePeriod =  $scope.currency[0];
            }
        });
    }
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
   //adding products
    $scope.selectPricingDetails = function () {
        
     $scope.productId=$scope.productName;
        pricingService.getPricingdetailsById($scope.productId).then(function (response) {
            if (response.success) {
                $scope.pricingdetails=response.data;

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating project.');
        });
    };
//adding Pricing
    $scope.createPricing = function () {
        $scope.productDetails=[];
       
        angular.forEach($scope.product, function (item) {
            if($scope.productName==item.id)
            {
            $scope.productDetails=item;
        }
        });
        $scope.test=$scope.pricingdetails;
         

        input = {
            "id": $scope.planName,
            "name": $scope.planName,
            "product": $scope.productDetails,
            "productPlanItemToCost": {},
            "currency":$scope.currency,
            "appProfile":null,
            "billingCycle":null,
            "discount":null
        };

        pricingService.addPricing(input).then(function (response) {
            if (response.success) {
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
                appNotifyService.info("success ");

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating project.');
        });
    };
    //delete product  
    $scope.delete = function (productId) {

        productService.deleteProduct(productId).then(function (response) {
            if (response.success) {

                $scope.init();
                appNotifyService.info("success ");

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating project.');
        });
    };
});
