/*
 * User Service
 */
angular.module('plutusApp').service('adminService', function ($rootScope, $http, $q) {
//this method using for adding admin users
    this.addingUser = function (admin) {

        var deferred = $q.defer();

        $http({
            method: 'POST',
            url: $rootScope.baseUrl + 'api/v1/user/create',
            data: admin,
            headers: {
                'Content-Type': 'application/json'
            }

        }).success(function (response, status, headers, config) {

            deferred.resolve(response);
        }).error(function (response) {
            // Something went wrong.
            deferred.reject(response);
        });

        return deferred.promise;

    };
//this method using for getting all user details
    this.getAllUser = function () {

        var deferred = $q.defer();

        $http({
            method: 'GET',
            url: $rootScope.baseUrl + 'api/v1/user/all',
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

    //this method is using for deleting users
    this.deleteUserByEmail = function (email) {

        var deferred = $q.defer();

        $http({
            method: 'DELETE',
            url: $rootScope.baseUrl + 'api/v1/user/' + email,
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