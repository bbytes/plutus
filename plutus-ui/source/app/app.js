// Define Angular Module with dependencies 
var plutusApp = angular.module('plutusApp',
        [
            'ui.router',
            'ui.bootstrap',
            'ngAnimate',
            'templates-main',
            'toaster',
            'ngStorage',
            'angular-md5',
            'angular-hmac-sha512',
            'angularModalService',
            'ngLetterAvatar',
            'ui.calendar',
            'mgcrea.ngStrap',
            'dm.stickyNav',
            'ngMaterial',
            'angular-confirm',
            'textAngular',
            'server-url',
            'angularInlineEdit',
            'angular.chosen',
            'ui.bootstrap.datetimepicker',
            'hm.readmore',
            'angularUtils.directives.dirPagination',
            'ngTagsInput',
            '720kb.tooltips'
        ]);

// Defining global variables
plutusApp.run([
    '$rootScope',
    '$state',
    'BASE_URL',
    function ($rootScope, $state, BASE_URL) {

        $rootScope.bodyClass = '';
        $rootScope.baseUrl = BASE_URL;
        $rootScope.apiUrl = 'api/v1';
        $rootScope.loggedStatus = false;
        $rootScope.authToken = '';
        $rootScope.currentState = '';
        $rootScope.authrization = '';

        $rootScope.authFailureReasons = [
            'auth-token-expired',
            'login-user-id-missing',
            'auth-signature-missing',
            'invalid-auth-signature',
            'user_not_found',
            'bad_credentials',
            'login-failed'
        ];

        $rootScope.$on('$stateChangeSuccess', function (ev, to, toParams, from, fromParams) {

            if (to.data && to.data.authorization !== '') {
                if (!appAuthenticationService.isAuthenticated()) {
                    $state.go(to.data.redirectTo);
                }
            }
            $rootScope.currentState = to.name;
            $rootScope.previouState = from.name;
        });

        $rootScope.$on('$stateChangeStart', function (evt, to, params, from) {
            $rootScope.$state = $state;
            $rootScope.previouState = from.name;
            $rootScope.subMenu = '';
            if (to.redirectTo) {
                evt.preventDefault();
                $state.go(to.redirectTo, params);
            }
        });

    }]);

// Angular ui-router route definitions
plutusApp.config([
    '$stateProvider',
    '$urlRouterProvider',
    '$locationProvider',
    '$httpProvider',
    function ($stateProvider, $urlRouterProvider, $locationProvider,
            $httpProvider) {

        // To Remove # tag from URL
        $locationProvider.html5Mode(true);

        $urlRouterProvider.otherwise('/login');

        $stateProvider.state('login', {
            url: '/login',
            controller: 'loginCtrl',
            templateUrl: 'app/partials/login.html'

        }).state('products', {
            url: '/products',
            views: {
                '': {
                    templateUrl: 'app/partials/home.html'
                },
                'header@products': {
                    templateUrl: 'app/partials/home-header.html'
                },
                'main@products': {
                    templateUrl: 'app/partials/product.html',
                    controller: 'productCtrl',
                },
                'footer@products': {
                    templateUrl: 'app/partials/home-footer.html'
                }
            },
             data: {
                authorization: 'products',
                redirectTo: 'login'
            }
        }).state('pricing-plans', {
            url: '/pricing-plans',
            views: {
                '': {
                    templateUrl: 'app/partials/home.html'
                },
                'header@pricing-plans': {
                    templateUrl: 'app/partials/home-header.html'
                },
                'main@pricing-plans': {
                    templateUrl: 'app/partials/pricingPlans.html',
                    controller: 'pricingPlansCtrl',
                },
                'footer@pricing-plans': {
                    templateUrl: 'app/partials/home-footer.html'
                }
            }
        }).state('subscription', {
            url: '/subscription',
            views: {
                '': {
                    templateUrl: 'app/partials/home.html'
                },
                'header@subscription': {
                    templateUrl: 'app/partials/home-header.html'
                },
                'main@subscription': {
                    templateUrl: 'app/partials/subscriptionInfo.html',
                    controller: 'subscriptionInfoCtrl',
                },
                'footer@subscription': {
                    templateUrl: 'app/partials/home-footer.html'
                }
            }
        }).state('admin', {
            url: '/admin',
            views: {
                '': {
                    templateUrl: 'app/partials/home.html'
                },
                'header@admin': {
                    templateUrl: 'app/partials/home-header.html'
                },
                'main@admin': {
                    templateUrl: 'app/partials/admin.html',
                    controller: 'adminCtrl',
                },
                'footer@admin': {
                    templateUrl: 'app/partials/home-footer.html'
                }
            }
        }).state('forgot-password', {
            url: '/forgot-password',
            views: {
                '': {
                    templateUrl: 'app/partials/forgot-password.html',
                    controller: 'forgotPasswordCtrl'
                }
            }

        }).state('dashboard', {
            url: '/dashboard',
            views: {
                '': {
                    templateUrl: 'app/partials/home.html'

                },
                'header@dashboard': {
                    templateUrl: 'app/partials/home-header.html'
                },
                'main@dashboard': {
                    templateUrl: 'app/partials/dashboard.html',
                    controller: 'dashboardCtrl'
                },
                'footer@dashboard': {
                    templateUrl: 'app/partials/home-footer.html'
                }
            },
            data: {
                authorization: 'login',
                redirectTo: 'login'
            }
        })
    }]);

	


