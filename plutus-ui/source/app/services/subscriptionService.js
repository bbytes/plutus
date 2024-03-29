angular.module('plutusApp').service('subscriptionService', function ($rootScope, $http, $q) {
    //updating feature fields in subscription page
    this.addFeatureFields = function (id, input) {
        var deferred = $q.defer();
        $http({
            method: 'POST',
            url: $rootScope.baseUrl + 'api/v1/subscription/feature/' + id,
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

    this.getSubscriptions = function () {

        var deferred = $q.defer();

        $http({
            method: 'GET',
            url: $rootScope.baseUrl + 'api/v1/subscription/all',
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response, status, headers, config) {
            deferred.resolve(response);
        }).error(function () {
            deferred.reject({'success': false, 'msg': 'Oops! Something went wrong. Please try again later.'});
        });

        return deferred.promise;
    };

    this.getSubscriptionsForProd = function (product) {

        var deferred = $q.defer();

        $http({
            method: 'GET',
            url: $rootScope.baseUrl + 'api/v1/subscription/' + product,
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response, status, headers, config) {
            deferred.resolve(response);
        }).error(function () {
            deferred.reject({'success': false, 'msg': 'Oops! Something went wrong. Please try again later.'});
        });

        return deferred.promise;
    };

    this.deleteSubscription = function (product) {

        var deferred = $q.defer();

        $http({
            method: 'DELETE',
            url: $rootScope.baseUrl + $rootScope.apiUrl + 'subscription' + id,
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response, status, headers, config) {
            deferred.resolve(response);
        }).error(function () {
            deferred.reject({'success': false, 'msg': 'Oops! Something went wrong. Please try again later.'});
        });

        return deferred.promise;
    };

    this.getAllTimePeriods = function () {

        var deferred = $q.defer();

        $http({
            method: 'GET',
            url: $rootScope.baseUrl + 'api/v1/dropdown/timePeriod',
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response, status, headers, config) {
            deferred.resolve(response);
        }).error(function () {
            deferred.reject({'success': false, 'msg': 'Oops! Something went wrong. Please try again later.'});
        });

        return deferred.promise;
    };

    this.getCustomerDetails = function (key, timeperiod) {

        var deferred = $q.defer();

        $http({
            method: 'GET',
            url: $rootScope.baseUrl + 'api/v1/product/stats/' + timeperiod + '/' + key,
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response, status, headers, config) {
            deferred.resolve(response);
        }).error(function () {
            deferred.reject({'success': false, 'msg': 'Oops! Something went wrong. Please try again later.'});
        });

        return deferred.promise;
    };

    this.activateDeactivate = function (subkey) {

        var deferred = $q.defer();

        $http({
            method: 'POST',
            url: $rootScope.baseUrl + 'api/v1/subscription/status/' + subkey,
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response, status, headers, config) {
            deferred.resolve(response);
        }).error(function () {
            deferred.reject({'success': false, 'msg': 'Oops! Something went wrong. Please try again later.'});
        });

        return deferred.promise;
    };
});