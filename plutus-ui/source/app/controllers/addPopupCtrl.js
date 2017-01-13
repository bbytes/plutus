
/* popup controller*/
angular.module('plutusApp').controller('addPopupCtrl', function ($scope, $rootScope, $state, options, $uibModalInstance, $uibModal, productService, appNotifyService, pricingService, adminService) {
    $rootScope.bodyClass = 'standalone'; //avoiding background image
    $scope.emails = [];
    $scope.update = false;
    $scope.billingTypes = $rootScope.billingData;
    $scope.product = options.data;
    $scope.status = options.status;
    $scope.billEditStatus = options.billEditStatus;
    $scope.allPricingPlans = options.allPricingPlans;
    $scope.pricingId = options.pricingId;
    $scope.pricing = options.pricing;
    $scope.productId = options.productId;
    $scope.title = options.title;
    $scope.adminStatus = options.adminStatus;
    $scope.pricingArray = [];
    $scope.fixedRows = [];
    $scope.meteredRows = [];
    $scope.newObject = {};
    var billingValue = [];
    var billingValueMetered = [];
    $scope.count = 1;


    $scope.init = function () {

        productService.getProduct().then(function (response) {
            if (response.success) {
                $scope.product = response.data;
            }
        });

        productService.getBillingType().then(function (response) {
            if (response.success) {
                $scope.billingTypes = response.data;
                // $scope.selectedTimePeriod =  $scope.currency[0];
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
    //product functions
    if ($scope.status == true)
    {
        $scope.update = true;
        $scope.email = [];
        angular.forEach($scope.product, function (item) {
            if (item.id == $scope.productId) {
                $scope.productName = item.name;
                $scope.description = item.desc;
                $scope.billingType = item.billingType;
                angular.forEach(item.productTeamEmails, function (value) {
                    $scope.email.push(value);
                });

            }
        });
    }

    //update product
    $scope.updateProduct = function () {
        $scope.update = true;
//        if (!product) {
//            appNotifyService.error('Please enter a valid product name');
//            return false;
//        }
        angular.forEach($scope.email, function (item) {
            $scope.emails.push(item.text);
        });
        input = {
            "id": $scope.productName,
            "name": $scope.productName,
            "desc": $scope.description,
            "productTeamEmails": $scope.emails,
            "billingType": $scope.billingType
        };
        productService.updatePro(input).then(function (response) {
            if (response.success) {
                $scope.product = response.data;
                $scope.init();
                $rootScope.$broadcast('product', $scope.product);
                $scope.ok();

                appNotifyService.success("Product Updated Successfully");

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating product.');
        });
    };



    //adding products
    $scope.createProduct = function () {
//        if (!product) {
//            appNotifyService.error('Please enter a valid product name');
//            return false;
//        }
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
                $scope.product = response.data;
                $rootScope.$broadcast('product', $scope.product);


                $scope.ok();

                appNotifyService.success("Product Added Successfully");

            } else {
                appNotifyService.error(response.message);
            }
        }, function (error) {
            appNotifyService.error('Error while creating Product.');
        });
    };
//clear scope values
    $scope.clearProducts = function () {
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
    ////////////end of product functions///////////////


    ////pricing functions

    //adding dynamic rows for metered  details
    $scope.addMeteredDetails = function () {
        if ($scope.count < $scope.billingPeriodsCount.length) {
            $scope.count++;
            $scope.meteredRows.push({key: '', cost: ''});
        } else {
            appNotifyService.error('There is no time period to add');
        }
    };

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

    //adding Pricing

    $scope.createPricing = function () {
//        if (!pricingdetails) {
//            appNotifyService.error('Please enter a valid product name');
//            return false;
//        }
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
                $scope.allPricingPlans = response.data;
                $rootScope.$broadcast('pricing', $scope.allPricingPlans);
                $scope.init();
                $scope.ok();
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


    if ($scope.billEditStatus == true)
    {

        $scope.pricing == true
        $scope.update = true;
        $scope.item1 = [];
        $scope.proId = $scope.productId;
        $scope.meteredRowsforEdit = [];
        $scope.fixedRowsForEdit = [];
        angular.forEach($scope.allPricingPlans, function (item) {
            if (item.id == $scope.pricingId) {
                $scope.planName = item.name;
                $scope.productName = item.product.name;
                $scope.description = item.desc;
                $scope.currency = item.currency;
                $scope.billing = item.billingCycle;
                angular.forEach($scope.product, function (val) {
                    if ($scope.proId == val.id)
                    {
                        $scope.type = val.billingType;

                    }
                });
                if ($scope.type == 'Fixed')
                {
                    $scope.fixedRows = item.productPlanItemToCost;
                    angular.forEach($scope.fixedRows, function (value, key) {
                        $scope.selectedNodeObj = {
                            key: key,
                            cost: value
                        };
                        $scope.fixedRowsForEdit.push($scope.selectedNodeObj);
                        $scope.fixedRows = $scope.fixedRowsForEdit;
                    });
                } else {
                    $scope.meteredRows = item.productPlanItemToCost;
                    angular.forEach($scope.meteredRows, function (value, key) {
                        $scope.selectedNodeObj = {
                            key: key,
                            cost: value
                        };
                        $scope.meteredRowsforEdit.push($scope.selectedNodeObj);
                        $scope.meteredRows = $scope.meteredRowsforEdit;
                    });
                }
            }
        });

    }

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
                $scope.allPricingPlans = response.data;
                $rootScope.$broadcast('pricing', $scope.allPricingPlans);
                $scope.init();
                $scope.ok();
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

//// end of pricing functions///////////

//admin functions
    $scope.createUser = function (isValid) {

        if (!isValid) {
            appNotifyService.error('Please enter a valid email and username.');
            return false;
        }
        if ($scope.admin === null) {
            appNotifyService.error('Please enter a valid email and username.');
        } else {

            input = {
                "email": $scope.email,
                "name": $scope.name
            };
            adminService.addingUser(input).then(function (response) {

                if (response.success) {
                    $scope.allUsers = response.data;
                    $rootScope.$broadcast('user', $scope.allUsers);
                    appNotifyService.success('User created successfully');
                    $scope.email = '';
                    $scope.name = '';
                    $scope.init();
                    $scope.ok();
                }
            }, function (error) {
                appNotifyService.error('Error while adding users. Please check back again!');
            });
        }
    };

    $scope.ok = function () {
        $uibModalInstance.close($scope.product);
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

});
