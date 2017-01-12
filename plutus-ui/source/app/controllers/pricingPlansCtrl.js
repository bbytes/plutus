

angular.module('plutusApp').controller('pricingPlansCtrl', function ($scope, $uibModal, $rootScope, $state, productService, appNotifyService, $sessionStorage, $window, $filter, pricingService) {
    $scope.emails = [];
    $scope.update = false;
    $scope.pricingArray = [];
    $scope.fixedRows = [];
    $scope.meteredRows = [];
    $scope.newObject = {};
    $rootScope.bodyClass = 'standalone'; //avoiding background image
    // variable to store the all selected billing values
    var billingValue = [];
    var billingValueMetered = [];
    $scope.count = 1;
    //listening   product data from popup
    $scope.$on('pricing', function (response, data) {
        $scope.allPricingPlans = data;
        $state.reload();
    });
//adding dynamic rows for metered  details
    $scope.addMeteredDetails = function () {
        if ($scope.count < $scope.billingPeriodsCount.length) {
            $scope.count++;
            $scope.meteredRows.push({key: '', cost: ''});
        } else {
            appNotifyService.error('There is no time period to add');
        }
    };

    //checking already existing time period drop down value for fixed
//    $scope.selectBilling = function (index, value) {
//        var isBillingValueExist = billingValue.indexOf(value);
//
//        if (isBillingValueExist === -1) {
//            billingValue[index] = value;
//        } else {
//            appNotifyService.error('Selected billing cycle already there');
//            $scope.fixedRows[index].billing = "";
//        }
//    };

    // existing value cleared
    $scope.selectBillingMetered = function (index, value) {

        angular.forEach($scope.meteredRows, function (val) {
            val.cost = '';
            val.key = '';
        });
    };

    // Remove meteredRows
    $scope.removeMeteredRows = function (index) {
        $scope.count--;
        $scope.meteredRows.splice(index, 1);

    };
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
                $scope.billingPeriodsCount = $scope.billingPeriods;
            }
        });

    }
    //dispalying symbol for corresponding currency
    $scope.selectLabel = function () {
        $scope.cur = $scope.currency;
        if ($scope.cur === "USD")
        {
            $scope.symbol = '$';
        } else if ($scope.cur === "INR")
        {
            $scope.symbol = 'Rs';
        }
    }
    //displaying pricing Details based on product
    $scope.selectPricingDetails = function () {
        $scope.fixedRows = [];
        $scope.fixedRows1 = [];
        $scope.meteredRows = [];
        $scope.pricingArray = [];
        $scope.productId = $scope.productName;
        pricingService.getPricingdetailsById($scope.productId).then(function (response) {
            if (response.success) {
                $scope.pricingdetails = response.data;
                angular.forEach($scope.product, function (val) {
                    if ($scope.productId == val.id)
                    {
                        $scope.type = val.billingType;

                    }
                });
                if ($scope.type == 'Fixed') {
                    $scope.fixedRows.push({cost: '', key: 'Price Per Item'});

                } else {
                    $scope.meteredRows.push({key: '', cost: ''});
                }

                var priceObject = {};
                angular.forEach($scope.pricingdetails, function (val) {
                    priceObject = {
                        "type": val,
                        "cost": 0.0,
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
    $scope.createPricing = function (pricingdetails) {
        if (!pricingdetails) {
            appNotifyService.error('Please enter a valid product name');
            return false;
        }
        $scope.item = {};


        if ($scope.pricingdetails.length > 0) {
            angular.forEach($scope.meteredRows, function (value, key) {
                var key = value.key;
                var cost = value.cost;
                $scope.item[key] = cost;
            });
        } else {
            angular.forEach($scope.fixedRows, function (value, key) {
                var key = value.key;
                var cost = value.cost;
                $scope.item[key] = cost;
            });
        }

        $scope.productDetails = [];

        angular.forEach($scope.product, function (item) {
            if ($scope.productName == item.id)
            {
                $scope.productDetails = item;
            }
        });
        input = {
            "id": $scope.planName,
            "name": $scope.planName,
            "product": $scope.productDetails,
            "desc": $scope.description,
            "productPlanItemToCost": $scope.item,
            "desc":$scope.description,
                    "currency": $scope.currency,
            "appProfile": null,
            "billingCycle": $scope.billing,
            "discount": null
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
        $scope.productName = "";
        $scope.description = "";
        $scope.currency = "";
        $scope.billing = "";
    };
    //edit pricing
    $scope.edit = function (pricingId, productId) {


        var uibModalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'app/partials/addPopup.html',
            controller: 'addPopupCtrl',
            backdrop: 'static',
            size: 'md',
            resolve: {
                options: function () {
                    return {
                        "title": 'Edit Pricing Details',
                        "data": $scope.product,
                        "billEditStatus": true,
                        "productId": productId,
                        "pricingId": pricingId,
                        "pricing": true,
                        "allPricingPlans": $scope.allPricingPlans

                    };
                }
            }
        });

        uibModalInstance.result.then(function (selection) {

            $rootScope.popUpStatus = true;
            //   $state.go('products');
            //alert($rootScope.popUpStatus);

        });




//        $scope.update = true;
//        $scope.item1 = [];
//        $scope.proId = productId;
//        $scope.meteredRowsforEdit = [];
//        $scope.fixedRowsForEdit = [];
//        angular.forEach($scope.allPricingPlans, function (item) {
//            if (item.id == pricingId) {
//                $scope.planName = item.name;
//                $scope.productName = item.product.name;
//                $scope.description = item.desc;
//                $scope.currency = item.currency;
//                $scope.billing = item.billingCycle;
//                angular.forEach($scope.product, function (val) {
//                    if ($scope.proId == val.id)
//                    {
//                        $scope.type = val.billingType;
//
//                    }
//                });
//                if ($scope.type == 'Fixed')
//                {
//                    $scope.fixedRows = item.productPlanItemToCost;
//                    angular.forEach($scope.fixedRows, function (value, key) {
//                        $scope.selectedNodeObj = {
//                            key: key,
//                            cost: value
//                        };
//                        $scope.fixedRowsForEdit.push($scope.selectedNodeObj);
//                        $scope.fixedRows = $scope.fixedRowsForEdit;
//                    });
//                } else {
//                    $scope.meteredRows = item.productPlanItemToCost;
//                    angular.forEach($scope.meteredRows, function (value, key) {
//                        $scope.selectedNodeObj = {
//                            key: key,
//                            cost: value
//                        };
//                        $scope.meteredRowsforEdit.push($scope.selectedNodeObj);
//                        $scope.meteredRows = $scope.meteredRowsforEdit;
//                    });
//                }
//            }
//        });
    };
    //update pricing
    $scope.updatePricing = function () {
        $scope.item = {};
        if ($scope.type == "Metered") {
            angular.forEach($scope.meteredRows, function (value, key) {
                var key = value.key;
                var cost = value.cost;
                $scope.item[key] = cost;
            });
        } else {
            angular.forEach($scope.fixedRows, function (value, key) {
                var key = value.key;
                var cost = value.cost;
                $scope.item[key] = cost;
            });
        }

        angular.forEach($scope.product, function (item) {
            if ($scope.productName == item.id)
            {
                $scope.productDetails = item;
            }
        });

        input = {
            "id": $scope.planName,
            "name": $scope.planName,
            "product": $scope.productDetails,
            "productPlanItemToCost": $scope.item,
            "desc": $scope.description,
            "currency": $scope.currency,
            "appProfile": null,
            "billingCycle": $scope.billing,
            "discount": null
        };
        pricingService.updatePri(input).then(function (response) {
            if (response.success) {
                $scope.init();
                $scope.clear();
                appNotifyService.success("Pricing Updated Successfully");
                $scope.type = "";

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
