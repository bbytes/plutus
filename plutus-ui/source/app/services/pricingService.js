/*
 * Product Service
 */
angular.module('plutusApp').service('pricingService', function ($rootScope, $http, $q) {

    // This method is used to Add pricing
    this.addPricing = function (input) {
        var deferred = $q.defer();
        $http({
            method: 'POST',
            url: $rootScope.baseUrl + 'api/v1/pricingPlan/create',
            data: input,
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response, status, headers, config) {

            deferred.resolve(response);
        }).error(function (response) {
            deferred.reject(response);
        });

        return deferred.promise;
    };
    // This method is used to Geting currency
    this.getCurrency = function () {
        var deferred = $q.defer();
        $http({
            method: 'GET',
            url: $rootScope.baseUrl + 'api/v1/dropdown/currency',
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response, status, headers, config) {
            deferred.resolve(response);
        }).error(function () {
            deferred.reject({
                'success': false,
                'msg': 'Oops! Something went wrong. Please try again later.'
            });
        });

        return deferred.promise;
    };
    
     this.getPricingCycle = function () {

        var deferred = $q.defer();

        $http({
            method: 'GET',
            url: $rootScope.baseUrl + 'api/v1/dropdown/billingCycle',
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response, status, headers, config) {
            deferred.resolve(response);
        }).error(function () {
            deferred.reject({
                'success': false,
                'msg': 'Oops! Something went wrong. Please try again later.'
            });
        });

        return deferred.promise;
    };
    // This method is used to pricing details by id
     this.getPricingdetailsById = function (id) {

        var deferred = $q.defer();

        $http({
            method: 'GET',
            url: $rootScope.baseUrl + 'api/v1/dropdown//billingParams/' + id,
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response, status, headers, config) {
            deferred.resolve(response);
        }).error(function () {
            deferred.reject({
                'success': false,
                'msg': 'Oops! Something went wrong. Please try again later.'
            });
        });

        return deferred.promise;
    };
    // This method is used to Update pricing 
    this.updatePro = function (input) {

        var deferred = $q.defer();
        $http({
            method: 'POST',
            url: $rootScope.baseUrl + 'api/v1//product/update',
            data: input,
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response, status, headers, config) {

            deferred.resolve(response);
        }).error(function (response) {
            deferred.reject(response);
        });

        return deferred.promise;
    };
     // This method is used to getting payment mode 
this.getPaymentMode = function () {

        var deferred = $q.defer();

        $http({
            method: 'GET',
            url: $rootScope.baseUrl + 'api/v1/dropdown/paymentMode',
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response, status, headers, config) {
            deferred.resolve(response);
        }).error(function () {
            deferred.reject({
                'success': false,
                'msg': 'Oops! Something went wrong. Please try again later.'
            });
        });

        return deferred.promise;
    };
     // This method is used to getting all pricing plans 
    this.getAllPricingPlans = function () {

        var deferred = $q.defer();

        $http({
            method: 'GET',
            url: $rootScope.baseUrl + 'api/v1/pricingPlan/all',
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response, status, headers, config) {
            deferred.resolve(response);
        }).error(function () {
            deferred.reject({
                'success': false,
                'msg': 'Oops! Something went wrong. Please try again later.'
            });
        });

        return deferred.promise;
    };
    // This method is used to Update pricing 
    this.updatePri = function (input) {

        var deferred = $q.defer();
        $http({
            method: 'POST',
            url: $rootScope.baseUrl + 'api/v1/pricingPlan/update',
            data: input,
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response, status, headers, config) {

            deferred.resolve(response);
        }).error(function (response) {
            deferred.reject(response);
        });

        return deferred.promise;
    };
    // This method is used to delete pricing based on Id

    this.deletePricing = function (id) {

        var deferred = $q.defer();

        $http({
            method: 'DELETE',
            url: $rootScope.baseUrl + 'api/v1/pricingPlan/' + id,
            headers: {
                'Content-Type': 'application/json'
            }

        }).success(function (response, status, headers, config) {

            deferred.resolve(response);
        }).error(function () {
            // Something went wrong.
            deferred.reject({
                'success': false,
                'msg': 'Oops! Something went wrong. Please try again later.'
            });
        });

        return deferred.promise;

    };
    
      // This method is used to getting all billingPeriods
    this.getAllBillings = function () {

        var deferred = $q.defer();

        $http({
            method: 'GET',
            url: $rootScope.baseUrl + 'api/v1/dropdown/billingCycle',
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response, status, headers, config) {
            deferred.resolve(response);
        }).error(function () {
            deferred.reject({
                'success': false,
                'msg': 'Oops! Something went wrong. Please try again later.'
            });
        });

        return deferred.promise;
    };

});
