/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Service to authenticate
 */
angular.module('plutusApp').service('appAuthenticationService', function ($rootScope,$sessionStorage, $window) {

    this.isAuthenticated = function () {

        if ($sessionStorage.userInfo.accessToken == null) {
            return false;
        }

        // Authentication success.
        return true;
    };

});