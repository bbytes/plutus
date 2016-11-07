angular.module('rootApp').controller('homeCtrl', function ($rootScope, $scope, $state) {

 
    $scope.partTypes = [];
    $scope.status = {
        isopen: false
    };
    $scope.partTypeInline=[];


    $scope.headerInit = function () {
        // Do not load view modes in vm stand alone page
        if ($rootScope.vmStandAloneMode) {partTypes
            return;
        }
        if ($rootScope.showWelcomeMessage && $rootScope.userName) {
            if ($rootScope.localeService === "es") {
                appNotifyService.info('Bienvenido' + ' ' + $rootScope.userName);
                $rootScope.showWelcomeMessage = false;
            } else {

                appNotifyService.info($translate.instant('message.welcome') + ' ' + $rootScope.userName);
                $rootScope.showWelcomeMessage = false;
            }
        }

            if ($rootScope.OnlangChangeModes == true) {
                $rootScope.viewModes = [];
            }

            if ($rootScope.viewModes == undefined || $rootScope.viewModes.length == 0) {
                appCommonService.getViewModes().then(function (response) {
                    if (response.success) {
                        $rootScope.viewModes = response.data;
                        if ($rootScope.OnlangChangeModes == true) {
                            angular.forEach($rootScope.viewModes, function (value, key) {
                                if (value.id == $rootScope.viewMode.id) {
                                    $rootScope.viewMode = value;
                                }
                            });
                        } else if ($rootScope.viewMode == null && $rootScope.viewModes.length > 1) {
                            $rootScope.viewMode = $rootScope.viewModes[0];
                            $localStorage.userInfo.viewMode = $rootScope.viewModes[0];
                        }
                    } else {
                        if (response.data) {
                            appNotifyService.info(response.data);
                        }
                    }
                });
            }
        

        appCommonService.getPartType().then(function (response) {
            if (response.success) {
                $scope.partTypes = response.data;
                if($rootScope.currentState == 'regression'){
                 angular.forEach($scope.partTypes, function (value, key) {
                                if (value.offline== false) {
                                    $scope.partTypeInline.push(value);
                                   
                                }
                            });
                    if ($rootScope.selectedRegressionPartType == null && $scope.partTypeInline.length > 1) {
                    $rootScope.selectedRegressionPartType = $scope.partTypeInline[0];
                    $localStorage.userInfo.selectedRegressionPartType = $scope.partTypeInline[0];
                }
                          //   $rootScope.selectedRegressionPartType = $scope.partTypeInline[0];
                                    
            }
            
                if ($rootScope.selectedPartType == null && $scope.partTypes.length > 1) {
                    $rootScope.selectedPartType = $scope.partTypes[0];
                    $localStorage.userInfo.selectedPartType = $scope.partTypes[0];
                }
           
            } else {
                if (response.data) {
                    appNotifyService.info(response.data);
                }
            }
        });
    };

    $scope.toggleDropdown = function ($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.status.isopen = !$scope.status.isopen;
    };

    $scope.changeLanguage = function (locale) {
        $rootScope.OnlangChangeModes = true;
        $rootScope.locale = locale;
        $rootScope.currentLocale = locale;
        appLocaleService.setLocale(locale);

    };

    $scope.changeViewMode = function (mode) {
        $rootScope.OnlangChangeModes = true;
        if (mode && mode != $rootScope.viewMode) {
            $rootScope.viewMode = mode;

            // Update view mode in session
            $localStorage.userInfo.viewMode = mode;
            if ($rootScope.currentState == 'admin.plantstructure') {
                $rootScope.$broadcast('processTreeNode:active', mode);
            } else {
                $rootScope.$broadcast('viewmode:changed', mode);
            }

        }
    };

    $scope.changePartType = function (partType) {
        if (partType) {
             if($rootScope.currentState == 'regression'){
                   $rootScope.selectedRegressionPartType = partType;
            // Update part type in session
            $localStorage.userInfo.selectedPartType = partType;
            
             }else{
            $rootScope.selectedPartType = partType;
            // Update part type in session
            $localStorage.userInfo.selectedPartType = partType;
        }
              if ($rootScope.currentState == 'admin.plantstructure') {
                $rootScope.$broadcast('processTreeNode:active', partType);
            } else {
                $rootScope.$broadcast('parttype:changed', partType);
            }         
        }
    };
    $scope.goToPlantView = function () {
        visualMeasurementService.getSelectedNodeId($localStorage.activeTreeNode.entityType, $localStorage.activeTreeNode.mesId).then(function (response) {
            if (response.success) {
                $rootScope.nodeToBeActivated = response.data;
                var input = $rootScope.nodeToBeActivated;
                // var url = $state.href('plantview({ cid :$rootScope.nodeToBeActivated})');
                // window.open(url,'_target');
            } else {
                if (response.data) {
                    appNotifyService.info(response.data);
                }
            }
        });
    };

    $scope.logout = function () {
        loginService.logout().then(function (response) {
            if (response.success == true) {

                // Clear local storage                
                $window.sessionStorage.clear();
                $window.localStorage.clear();
                appAuthorizeService.clearAttemptedUrl();

                // REV-4213 : Multi tab reveal page open with auth token logic
                // This localStorage item addition is just to trigger event to logout all active tabs from browser
                // will be cleared later in app.js
                /*localStorage.setItem('deleteSessionStorage', 'foobar');
                localStorage.removeItem('deleteSessionStorage', 'foobar');*/

                $rootScope.loggedStatus = false;
                $rootScope.authToken = '';
                $rootScope.userName = '';
                $rootScope.loggedIn = '';

                if ($rootScope.vmStandAloneMode) {
                    $window.location = '/vmStandAlone/' + visualMeasurementService.sourceType + '/' + visualMeasurementService.mesId;
                } else {
                    $window.location = '/login';
                }
            }
        });
    };
    /*Functions to load files according to selection of languages */
    $scope.loadVmPdf = function () {
        if ($rootScope.locale) {
            $window.location = '/assets/reveal-help/Visual_Measurement_Display_with_Upstream_Compare_' + $rootScope.locale + '.pdf';
        }

    }

    $scope.loadRefPdf = function () {
        if ($rootScope.locale) {
            $window.location = '/assets/reveal-help/QS_Referemce_Guide_6-16-15_update_' + $rootScope.locale + '.pdf';
        }
    }

    $scope.loadHelp = function () {
        if ($rootScope.locale) {
            $window.location = '/assets/reveal-help/webframe_' + $rootScope.locale + '.html';
        }
    }
});
