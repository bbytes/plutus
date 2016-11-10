/*
 * Product Service
 */
angular.module('plutusApp').service('pricingService', function ($rootScope, $http, $q) {

    // This method is used to Add Product
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
    // This method is used to Get all Product
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
    // This method is used to Update Product 
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
    // This method is used to Update Product 
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
    // This method is used to delete Product based on Id

    this.deleteProduct = function (id) {

        var deferred = $q.defer();

        $http({
            method: 'DELETE',
            url: $rootScope.baseUrl + 'api/v1//product/' + id,
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

});
