/*
 * Product Service
 */
angular.module('plutusApp').service('productService', function ($rootScope, $http, $q) {

    // This method is used to Add feature fields
    this.addFeatureFields = function (id, input) {
        var deferred = $q.defer();
        $http({
            method: 'POST',
            url: $rootScope.baseUrl + 'api/v1/product/feature/' + id,
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
// This method is used to Get all feture fields
    this.getFeatureFields = function (id) {

        var deferred = $q.defer();

        $http({
            method: 'GET',
            url: $rootScope.baseUrl + 'api/v1/product/feature/' + id,
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
    // This method is used to Add Product
    this.add = function (input) {
        var deferred = $q.defer();
        $http({
            method: 'POST',
            url: $rootScope.baseUrl + 'api/v1/product/create',
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
    this.getProduct = function () {

        var deferred = $q.defer();

        $http({
            method: 'GET',
            url: $rootScope.baseUrl + 'api/v1/product/all',
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
            url: $rootScope.baseUrl + 'api/v1/product/update',
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

    this.getBillingType = function () {

        var deferred = $q.defer();

        $http({
            method: 'GET',
            url: $rootScope.baseUrl + 'api/v1/dropdown/billingType',
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
