angular.module('rootApp').service('subscriptionService', function ($rootScope, $http, $q, appConfig) {

    this.getSubscriptions = function () {

        var deferred = $q.defer();

        $http({
            method: 'GET',
            url: $rootScope.baseUrl + $rootScope.apiUrl + 'subscription/all',
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
            url: $rootScope.baseUrl + $rootScope.apiUrl + 'subscription' +product,
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

   

   

    
   

   
});