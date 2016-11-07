/* DIRECTIVES */
angular.module("rootApp").directive('sglclick', ['$parse', function($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
            var fn = $parse(attr['sglclick']);
            var delay = 300,
                clicks = 0,
                timer = null;
            element.on('click', function(event) {
                clicks++; //count clicks
                if (clicks === 1) {
                    timer = setTimeout(function() {
                        scope.$apply(function() {
                            fn(scope, {
                                $event: event
                            });
                        });
                        clicks = 0; //after action performed, reset counter
                    }, delay);
                } else {
                    clearTimeout(timer); //prevent single-click action
                    clicks = 0; //after action performed, reset counter
                }
            });
        }
    };
}]);

//makes the input to get model values in two digits appending 0 if lower than 10 calling  'counterValue' filter
/*angular.module("rootApp").directive('twoDigit', function($filter, $browser) {
    return {
        require: 'ngModel',
        link: function($scope, $element, $attrs, ngModelCtrl) {
            var listener = function() {
                var value = $element.val().replace(/[^0-9]/g, '');
                $element.val($filter('counterValue')(value, false));
            };

            // This runs when we update the text field
            ngModelCtrl.$parsers.push(function(viewValue) {
                return viewValue.replace(/[^0-9]/g, '').slice(0,10);
            });

            // This runs when the model gets updated on the scope directly and keeps our view in sync
            ngModelCtrl.$render = function() {
                $element.val($filter('counterValue')(ngModelCtrl.$viewValue, false));
            };

            $element.bind('change', listener);
            $element.bind('keydown', function(event) {
                var key = event.keyCode;
                // If the keys include the CTRL, SHIFT, ALT, or META keys, or the arrow keys, do nothing.
                // This lets us support copy and paste too
                if (key == 91 || (15 < key && key < 19) || (37 <= key && key <= 40)){
                    return;
                }
                $browser.defer(listener); // Have to do this or changes don't get picked up properly
            });

            $element.bind('paste cut', function() {
                $browser.defer(listener);
            });
        }

    };
});*/

angular.module("rootApp").directive('loading', function() {
    return {
        restrict: 'E',
        replace: true,
        template: '<div class="loading"><img src="./img/gray-preload.gif" width="20" height="20" />LOADING...</div>',
        link: function(scope, element, attr) {
            scope.$watch('loading', function(val) {
                if (val)
                    scope.loadingStatus = 'true';
                else
                    scope.loadingStatus = 'false';
            });
        }
    };
});

angular.module("rootApp").directive('ngEnter', function() {
    return function(scope, element, attrs) {
        element.bind("keydown keypress", function(event) {
            if (event.which === 13) {
                scope.$apply(function() {
                    scope.$eval(attrs.ngEnter);
                });
                event.preventDefault();
            }
        });
    };
});

angular.module("rootApp").directive('focusMe', function($timeout) {
    return {
        link: function(scope, element, attrs, model) {
            $timeout(function() {
                element[0].focus();
            }, 1);
        }
    };
});

angular.module("rootApp").directive('fancytree', function($timeout) {
    return {
        restrict: 'E',
        transclude: true,
        replace: true,
        scope: {
            nodeActiveFn: '&',
            nodeSelectFn: '&',
            menuSelectionFn: '&',
            treeSource: '=',
            showCheckbox: '=',
            selectMode: '=',
            setRootActive: '@',
            persistState: '@',
            showContextMenu: '@',
            selectedNodes: '=',
            control: '=',
            overrideSource: '='
        },
        template: '<div id="tree"></div>',
        link: function(scope, element, attrs) {

            scope.internalControl = scope.control || {};

            // Custom node icons for REVEAL application
            var getRevealIconClass = function(data) {
                // Set node custom image class based on entityType and enabled status                        
                var iconClass = 'fancytree-icon-' + data.entityType + '-';
                if (!data.enabled) {
                    iconClass += 'disabled';
                } else if (data.inactive) {
                    iconClass += 'inactive';
                } else {
                    iconClass += 'enabled';
                }
                return iconClass;
            };

            var setIcon = function(event, data) {
                if (data.node.data) {
                    // Set name as node title
                    data.node.tooltip = data.node.data.name;
                    data.node.title = data.node.data.name;

                    // Expand all nodes except Activity and Station by default
                    if (data.node.data.entityType == 'Activity') {
                        data.node.expanded = false;
                    }
                    if (data.node.data.isNodeActive) {
                        $timeout(function() {
                            data.node.setActive();
                        }, 500);
                    }

                    //Override expand behaviour with server side value
                    data.node.expanded = data.node.data.expand;

                    return getRevealIconClass(data.node.data);
                }
            };

            var extensions = ['filter'];

            if (scope.showContextMenu === 'true') {
                extensions.push('contextMenu');
            }

            var tree = element.fancytree({
                extensions: extensions,
                source: scope.treeSource,
                checkbox: scope.showCheckbox,
                selectMode: scope.selectMode,
                autoScroll: true,
                clickFolderMode: 2,
                debugLevel: 1,
                icon: setIcon,
                filter: {
                    autoApply: true, // Re-apply last filter if lazy data is loaded
                    counter: false, // Show a badge with number of matching child nodes near parent icons
                    hideExpandedCounter: true, // Hide counter badge, when parent is expanded
                    mode: "hide" // "dimm": Grayout unmatched nodes, "hide": remove unmatched nodes
                },
                contextMenu: {
                    menu: {
                        'add': { 'name': 'Add Child', 'icon': 'add' },
                        'enable': { 'name': 'Enable/Disable', 'icon': 'enable' }
                    },
                    actions: function(node, action, options) {
                        scope.menuSelectionFn({ action: action, node: node });
                    }
                },
                activate: function(event, data) {
                    scope.nodeActiveFn({ event: event, data: data });
                },
                click: function(event, data) {
                    var node = data.node;
                    var tt = $.ui.fancytree.getEventTargetType(event.originalEvent);
                },
                select: function(event, data) {
                    scope.nodeSelectFn({ event: event, data: data });
                }
            });

            // Set top node active            
            if (scope.setRootActive === 'true') {
                element.fancytree("getRootNode").getFirstChild().setActive();
                scope.setRootActive = "false";
            }

            scope.internalControl.updateNode = function(node) {
                node.setActive(true);
                node.icon = getRevealIconClass(node.data);
                node.render(true);
            };

            scope.internalControl.reloadTree = function(data) {
                // Store previous selected node and key
                var activeNode = element.fancytree("getActiveNode");

                var tree = element.fancytree("getTree");
                var treeCount = tree.count();

                tree.clearFilter();

                tree.reload(data).done(function() {
                    if (activeNode) {
                        var activeNodeKey = activeNode.key.replace(/\D/g, '');
                        var node = tree.getNodeByKey("_" + (treeCount + parseInt(activeNodeKey)));
                        if (node) {
                            node.setActive(true);
                        }
                    }
                });
            };

            scope.internalControl.filterTree = function(txt) {
                return element.fancytree("getTree").filterBranches(txt, { autoExpand: true, leavesOnly: true });
            };

            scope.internalControl.collapseAll = function() {

                element.fancytree("getRootNode").visit(function(node) {
                    node.setExpanded(false);

                });

            };

            //select mode selection for fancytree
            scope.internalControl.setSelectionMode = function(mode) {
                element.fancytree({ selectMode: mode });
            };

            scope.internalControl.expandAll = function() {
                element.fancytree("getRootNode").visit(function(node) {
                    // Expand all nodes except Activity and Station by default
                    if (node.data.entityType !== 'Activity' && node.data.entityType !== 'Station') {
                        node.setExpanded(true);
                    }
                });
            };

            scope.internalControl.addChild = function(node, data) {

                var newNode = node.addNode({
                    title: data.name,
                    data: data,
                    icon: getRevealIconClass(data)
                });
                newNode.setActive();
            };

            scope.internalControl.getParents = function(node) {
                return node.getParentList(false, true);
            };

            scope.internalControl.getParent = function(node) {
                return node.getParent();
            };

            scope.internalControl.selectNodeByKey = function(key) {
                var tree = element.fancytree("getTree");
                var node = tree.getNodeByKey(key);
                if (node) {
                    node.setActive(true);
                }
            };

            scope.internalControl.selectNodeBySourceId = function(id) {
                element.fancytree("getRootNode").visit(function(node) {
                    if (node.data.id == id) {
                        node.setActive(true);
                    }
                });
            };

            scope.internalControl.getNodeBySourceId = function(id) {
                var tree;
                element.fancytree("getRootNode").visit(function(node) {
                    if (node.data.id == id) {
                        tree = node;

                    }
                });
                return tree;
            };

            scope.internalControl.getSelectedNodes = function() {
                var tree = element.fancytree("getTree");
                return tree.getSelectedNodes();
            };

            scope.internalControl.deselectAll = function() {
                element.fancytree("getRootNode").visit(function(node) {
                    node.setSelected(false);
                });
            };
            scope.internalControl.deselectSingle = function(key) {
                var tree = element.fancytree("getTree");
                var node = tree.getNodeByKey(key);
                if (node) {
                    node.setSelected(false);
                }
            };

        }
    };
});

// Directive to show/hide links based user page permissions
angular.module("rootApp").directive('revealModulePermission', function(appAuthorizeService) {
    return {
        link: function(scope, element, attrs) {
            if (!appAuthorizeService.canAccess(attrs.revealModulePermission)) {
                element.remove();
            }
        }
    };
});

// Directive to show/hide links based user page permissions
angular.module("rootApp").directive('revealFeaturePermission', function(appAuthorizeService) {
    return {
        link: function(scope, element, attrs) {
            if (!appAuthorizeService.canPerform(attrs.revealFeaturePermission)) {
                element.remove();
            }
        }
    };
});

//Directive to make angular ui modal dialog draggable
angular.module("rootApp").directive('uibModalWindow', function() {
    return {
        restrict: 'EA',
        link: function(scope, element) {
            $(".modal-dialog").draggable({
                handle: ".modal-header"
            });
        }
    };
});

/*tooltip*/
angular.module("rootApp").directive('myDirec', ['$log', '$templateCache', '$compile', function($log, $templateCache, $compile) {
    return {
        restrict: 'A',
        priority: 1000,
        link: function(scope, element, attr) {
            element.children().attr('data-toggle', 'tooltip');
            element.children().attr('data-placement', 'tooltip');
            element.children().attr('title', 'hello tool tip');

            $compile(element)(scope);
        }
    };
}]);

angular.module("rootApp").directive("scrollToTopWhen", ['$timeout', function($timeout) {
    function link(scope, element, attrs) {
        scope.$on(attrs.scrollToTopWhen, function() {
            $timeout(function() {
                angular.element(element)[0].scrollTop = 0;
            });
        });
    }
}]);

/* 
 * Directive to stop page scroll durring ajax request.
 */
angular.module("rootApp").directive('stopPageScrollOnAjax', ['$http', '$window', function($http, $window) {
    return {
        restrict: 'A',
        link: function(scope, elm, attrs) {
            scope.isLoading = function() {
                return $http.pendingRequests.length > 0;
            };

            scope.$watch(scope.isLoading, function(v) {
                function preventDefault(e) {
                    e = e || window.event;
                    if (e.preventDefault)
                        e.preventDefault();
                    e.returnValue = false;
                }

                if (v) {
                    if ($window.addEventListener) // older FF
                        $window.addEventListener('DOMMouseScroll', preventDefault, false);
                    $window.onwheel = preventDefault; // modern standard
                    $window.onmousewheel = document.onmousewheel = preventDefault; // older browsers, IE
                    $window.ontouchmove = preventDefault; // mobile

                } else {
                    if ($window.removeEventListener)
                        $window.removeEventListener('DOMMouseScroll', preventDefault, false);
                    $window.onmousewheel = document.onmousewheel = null;
                    $window.onwheel = null;
                    $window.ontouchmove = null;
                }
            });
        }
    };
}]);

angular.module("rootApp").directive('fchart', function(chartExportService, $rootScope,$timeout, $uibModal, chartDrillDownService, appNotifyService) {
    return {
        restrict: "E",
        scope: {
            cid: '@',
            dataformat: '@',
            width: '@',
            height: '@',
            dataSource: '@',
            type: '@',
            charts: '=',
            control: '=',
            data: '@'
        },
        link: function(scope, element, attrs) {
            scope.internalControl = scope.control || {};

            var chart = null;
            var exportList = [];
            var agentData = {};
            scope.actionAfterExport = 'print';

            var chartConfigObject = {
                type: scope.type,
                width: scope.width,
                height: scope.height,
                renderAt: element[0],
                id: scope.cid,
                dataFormat: scope.dataformat || 'json',
                dataSource: attrs.datasource,
                exportFormat: "pdf",
                events: {
                    "beforeExport": function(evtObj, argObj) {
                        var exportStatus = document.getElementById('chart-export-status');
                        if (exportStatus) {
                            exportStatus.style.display = 'inline';                           
                        }
                    },
                    "exported": function(eventObj, dataObj) {
                        var exportStatus = document.getElementById('chart-export-status');
                        if (exportStatus) {
                            exportStatus.style.display = 'none';                            
                        }
                    },
                "dataplotclick": function(ev, props) {
                    console.log("imaa drilldown");

                    },
                     "renderComplete": function (e, a) {
                   
                   // Cross-browser event listening(IE issue)
                   var addListener = function (elem, evt, fn) {
                       if (elem && elem.addEventListener) {
                           elem.addEventListener(evt, fn);
                       }
                       else if (elem && elem.attachEvent) {
                           elem.attachEvent("on" + evt, fn);
                       } 
                       else {
                           elem["on" + evt] = fn;
                       }
                   };
               }
                }
            };

            // Register methods for chart drill down calls from fusion chart
            // As fusion chart look for those methods in window scope, these
            // functions needs to be registered in window scope
            window.chartDrillDown = function(info) {
                if(!window.chartDrillDownStatus){
                    chartDrillDownService.doDrillDown('chartDrillDown', info);
                    window.chartDrillDown = true;
                }
            };

            window.popupforTop10StnAndFisPCharts = function(info) {
                
                if(!window.popupforTop10StnAndFisPChartsStatus){   //prventing multiple event calling
                    chartDrillDownService.doDrillDownFisPChart('popupforTop10StnAndFisPCharts', info);
                    window.popupforTop10StnAndFisPChartsStatus = true;
                }
            };

            window.activityCountJs = function(info) {
                if(!window.activityCountJsStatus){
                  chartDrillDownService.doDrillDown('activityCountJs', info);
                    window.activityCountJsStatus = true;
                }
                
            };

            window.chartPopUpForTop10ByStationCharts = function(info) {

                if(!window.popupforTop10StnAndFisPChartsStatus){
                   chartDrillDownService.doDrillDownFisPChart('chartPopUpForTop10ByStationCharts', info);
                    window.popupforTop10StnAndFisPChartsStatus = true;
                }

                
            };

            window.chartDrillDownForLabels = function(info) {
                chartDrillDownService.doTreeDrillDown('chartDrillDownForLabels', info);
            };

            window.Reveal = {};
            window.Reveal.PlantView = { DrillHandler: {} };
            window.Reveal.Compare = { DrillHandler: {} };

            window.Reveal.PlantView.DrillHandler.PopupActivity = function(info) {
                 if(!window.Reveal.PlantView.DrillHandler.PopupActivityStatus){
               chartDrillDownService.doDrillDown('PlantView-PopupActivity', info);
                    window.Reveal.PlantView.DrillHandler.PopupActivityStatus = true;
                }
               
            };

            window.Reveal.PlantView.DrillHandler.DrillActivity = function(info) {
                 if(!window.Reveal.PlantView.DrillHandler.DrillActivityStatus){
              chartDrillDownService.doDrillDown('PlantView-DrillActivity', info);
                    window.Reveal.PlantView.DrillHandler.DrillActivityStatus = true;
                }
                
            };

            window.Reveal.Compare.DrillHandler.DrillHours = function(info) {
              
                if(!window.Reveal.Compare.DrillHandler.DrillStatus){
              chartDrillDownService.doDrillHours('Compare-DrillHours', info);
                    window.Reveal.Compare.DrillHandler.DrillStatus = true;
                }
            };

            window.Reveal.Compare.DrillHandler.DrillWeekly = function(info) {
              

                  if(!window.Reveal.Compare.DrillHandler.DrillStatus){
                  chartDrillDownService.doDrillWeekly('Compare-DrillWeekly', info);
                    window.Reveal.Compare.DrillHandler.DrillStatus = true;
                }
            };

            window.Reveal.Compare.DrillHandler.DrillDay = function(info) {
                  if(!window.Reveal.Compare.DrillHandler.DrillStatus){
                  chartDrillDownService.doDrillDay('Compare-DrillDay', info);
                    window.Reveal.Compare.DrillHandler.DrillStatus = true;
                }
               
            };

            var createFCChart = function() {
                // dispose if previous chart exists
                if (chart && chart.dispose) {
                    chart.dispose();
                }

                // Setting to make FusionCharts rendering properly when <base> tag included in HTML head 
                FusionCharts.options.SVGDefinitionURL = 'absolute';

                chart = new FusionCharts(chartConfigObject);

                /* @todo validate the ready function whether it can be replaced in a better way */
                angular.element(document).ready(function() {
                    element.ready(function() {
                        // Render the chart only when angular is done compiling the element and DOM.
                        chart.showBorder = 0;
                        chart = chart.render();
                        chart.uniqueId = scope.data || null;

                        if (scope.charts) {
                            scope.charts.push(chart);
                        }
                    });
                });
            };

            // Cross-browser event listening
                   scope.internalControl.addListener = function (elem, evt, fn) {
                       if (elem && elem.addEventListener) {
                           elem.addEventListener(evt, fn);
                       }
                       else if (elem && elem.attachEvent) {
                           elem.attachEvent("on" + evt, fn);
                       } 
                       else {
                           elem["on" + evt] = fn;
                       }
                   };

            scope.internalControl.exportFC = function(chartData, action, section, mailData) {
                agentData = chartData;
                scope.actionAfterExport = action;
                scope.section = section;
                scope.mailData = mailData;
                var chartLink = chartExportService.getExportHandler();
                var callBack = 'FCChart_Exported';
              
                if(section === 'vm' && mailData === "Upstream Compare Charts" && action === 'print' ){
                        var ch = scope.charts[0];
                     if (chartData.windowId) {
                    if (ch.uniqueId == chartData.windowId) {
                        if (ch.hasRendered()) {
                            callBack = (section == 'vm') ? 'VM_FCChart_Exported' : callBack;
                            ch.exportChart({ exportHandler: chartLink, exportCallback: callBack });
                        }
                    }
                } else if (ch.hasRendered()) {
                    ch.exportChart({ exportHandler: chartLink, exportCallback: callBack });
                }
                
            }
            else if(section === 'vm' && mailData !== "Upstream Compare Charts" && action === 'print' ){
            angular.forEach(scope.charts, function (ch) {
                if (chartData.windowId) {
                    if (ch.uniqueId == chartData.windowId) {
                        if (ch.hasRendered()) {
                            callBack = (section == 'vm') ? 'VM_FCChart_Exported' : callBack;
                            ch.exportChart({ exportHandler: chartLink, exportCallback: callBack });
                        }
                    }
                } else if (ch.hasRendered()) {
                    ch.exportChart({ exportHandler: chartLink, exportCallback: callBack });
                }
            });
            }
            else{
                  var ch = scope.charts[0];
                     if (chartData.windowId) {
                    if (ch.uniqueId == chartData.windowId) {
                        if (ch.hasRendered()) {
                            callBack = (section == 'vm') ? 'VM_FCChart_Exported' : callBack;
                            ch.exportChart({ exportHandler: chartLink, exportCallback: callBack });
                        }
                    }
                } else if (ch.hasRendered()) {
                    ch.exportChart({ exportHandler: chartLink, exportCallback: callBack });
                }
            }
            };

            window.FCChart_Exported = function(data) {

                if (data.statusCode == 1) {
                    exportList.push(data);
                } else {
                    scope.$apply(function() {
                        appNotifyService.error(data.statusMessage);
                    });
                }

                if (exportList.length >= scope.charts.length) {
                    if (scope.actionAfterExport === 'print') {
                        chartExportService.getPdfFile(agentData, exportList, scope.section).then(function(response) {
                            if (response.success) {
                                $rootScope.showPrintIcon=true;
                                popupCenter(response.data, 'Print', 550, 550);
                            }
                            exportList = [];
                        });
                    } else if (scope.actionAfterExport === 'mail') {
                        chartExportService.sendMail(agentData, exportList, scope.section, scope.mailData).then(function(response) {
                            exportList = [];
                        });
                    }
                } else if (data.statusCode == 1) {
                    var chartLink = chartExportService.getExportHandler();
                    var callBack = 'FCChart_Exported';
                    var ch = scope.charts[exportList.length];
                    if (ch.hasRendered()) {
                        ch.exportChart({ exportHandler: chartLink, exportCallback: callBack });
                    }
                }
            };
            window.VM_FCChart_Exported = function(data) {
                if (data.statusCode == 1) {
                    exportList.push(data);
                } else {
                    scope.$apply(function() {
                        appNotifyService.error(data.statusMessage);
                    });
                }

                if (scope.actionAfterExport === 'print') {
                    chartExportService.getPdfFile(agentData, exportList, scope.section).then(function(response) {
                        if (response.success) {
                            $rootScope.showPrintIcon=true;
                            popupCenter(response.data, 'Print', 550, 550);
                        }
                        exportList = [];
                    });
                } else if (scope.actionAfterExport === 'mail') {
                    chartExportService.sendMail(agentData, exportList, scope.section, scope.mailData).then(function(response) {
                        exportList = [];
                    });
                }
            };

            function popupCenter(url, title, w, h) {
                var left = (screen.width / 2) - (w / 2);
                var top = (screen.height / 2) - (h / 2);
                var newWin = window.open(url, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=1, copyhistory=no, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);

                if (newWin) {
                    $rootScope.showPrintIcon=false;
                    newWin.focus();
                    newWin.print();
                }
            };

            createFCChart();
        }
    };
});

angular.module('rootApp').directive('revealCsvDownload', function($document, $timeout, agentService) {
    return {
        restrict: 'AC',
        scope: {
            csvData: '=revealCsvDownload'
        },
        link: function(scope, element, attrs) {

            function doClick() {
                agentService.getAgentCsvData(scope.csvData).then(function(response) {
                    scope.csv = response.data;
                    var charset = scope.charset || "utf-8";
                    var blob = new Blob([scope.csv], {
                        type: "text/csv;charset=" + charset + ";"
                    });

                    if (window.navigator.msSaveOrOpenBlob) {
                        navigator.msSaveBlob(blob, response.fileName);
                    } else {
                        var downloadContainer = angular.element('<div data-tap-disabled="true"><a></a></div>');
                        var downloadLink = angular.element(downloadContainer.children()[0]);
                        downloadLink.attr('href', window.URL.createObjectURL(blob));
                        downloadLink.attr('download', response.fileName);
                        downloadLink.attr('target', '_blank');

                        $document.find('body').append(downloadContainer);

                        $timeout(function() {
                            downloadLink[0].click();
                            downloadLink.remove();
                        }, null);
                    }
                });
            }

            element.bind('click', function(e) {
                if (scope.csvData) {
                    doClick();
                }
            });
        }
    };
});/*General Directive to edit content used in Vm page */
angular.module('rootApp').directive("contenteditable", function() {
	return {
        restrict: "A",
        require: "ngModel",
        link: function(scope, element, attrs, ngModel) {

            function read() {
                // view -> model
                var html = element.html();
                html = html.replace(/&nbsp;/g, "\u00a0");
                ngModel.$setViewValue(html);
            }
            // model -> view
            ngModel.$render = function() {
                element.html(ngModel.$viewValue || "");
            };

            element.bind("blur", function() {
                scope.$apply(read);
            });
            element.bind("keydown keypress", function (event) {
                if(event.which === 13) {
                    this.blur();
                    event.preventDefault();
                }
            });
        }
	};
});
angular.module('rootApp').service('Paginator', function($rootScope) {
    this.page = 0;
    this.rowsPerPage = 15;
    this.itemCount = 0;
    this.limitPerPage = 10;

    this.setPage = function(page) {
        if (page > this.pageCount()) {
            return;
        }

        this.page = page;
    };

    this.nextPage = function() {
        if (this.isLastPage()) {
            return;
        }

        this.page++;
    };

    this.perviousPage = function() {
        if (this.isFirstPage()) {
            return;
        }

        this.page--;
    };

    this.firstPage = function() {
        this.page = 0;
    };

    this.lastPage = function() {
        this.page = this.pageCount() - 1;
    };

    this.isFirstPage = function() {
        return this.page == 0;
    };

    this.isLastPage = function() {
        return this.page == this.pageCount() - 1;
    };

    this.pageCount = function() {
        return Math.ceil(parseInt(this.itemCount) / parseInt(this.rowsPerPage));
    };
    this.lowerLimit = function() {
        var pageCountLimitPerPageDiff = this.pageCount() - this.limitPerPage;

        if (pageCountLimitPerPageDiff < 0) {
            return 0;
        }

        if (this.page > pageCountLimitPerPageDiff + 1) {
            return pageCountLimitPerPageDiff;
        }

        var low = this.page - (Math.ceil(this.limitPerPage / 2) - 1);

        return Math.max(low, 0);
    };
});



angular.module('rootApp').directive('paginator', function factory() {
    return {
        restrict: 'E',
        controller: function($scope, Paginator) {
            $scope.paginator = Paginator;
        },
        templateUrl: 'app/partials/paginationControl.html'
    };
});

/* Signup page password and confirm password match checking*/
angular.module('rootApp')
    .directive('pwCheck', [function () {
    return {
        require: 'ngModel',
        link: function (scope, elem, attrs, ctrl) {
            var firstPassword = '#' + attrs.pwCheck;
            elem.add(firstPassword).on('keyup', function () {
                scope.$apply(function () {
                    ctrl.$setValidity('pwmatch', elem.val() === $(firstPassword).val());
                });
            });
        }
    }
}]);
