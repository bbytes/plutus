angular.module('plutusApp').filter('orderObjectBy', function () {
    return function (items, field, reverse) {
        var filtered = [];
        angular.forEach(items, function (item) {
            filtered.push(item);
        });
        filtered.sort(function (a, b) {
            return (a[field] > b[field] ? 1 : -1);
        });
        if (reverse)
            filtered.reverse();
        return filtered;
    };
});

angular.module('plutusApp').filter('removeSpaces', [function () {
        return function (string) {
            if (!angular.isString(string)) {
                return string;
            }
            return string.replace(/[\s]/g, '').replace(/&/g, 'AMP').replace(/,/g, '').replace(/\//g, '').replace(/'/g, '');
        };
    }]);

angular.module('plutusApp').filter('remGreaterHex', [function () {
        return function (string) {
            if (!angular.isString(string)) {
                return string;
            }
            return string.replace(/&gt;/g, '>');
        };
    }]);

angular.module('plutusApp').filter('split', function () {
    return function (input, splitChar, splitIndex) {
        // do some bounds checking here to ensure it has that index
        return input.split(splitChar)[splitIndex];
    }
});

angular.module('plutusApp').filter('clearHtmlFilt', function () {
    return function (html) {
        var filtered = angular.element('<div>').html(html).text();
        return filtered;
    }
});

angular.module('plutusApp').filter('utcdate', ['$filter', function ($filter) {

        return function (input, format) {
            if (!angular.isDefined(format)) {
                format = 'MM-dd-yyyy HH:mm';
            }

            var date = new Date(input);
            var d = new Date();
            var _utc = new Date(date.getFullYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds());
            return $filter('date')(_utc, format)
        };
}]);

// code for drilldown popup page pagination

   angular.module('plutusApp').filter('paginate', function(Paginator) {
        return function(input, rowsPerPage) {
            if (!input) {
                return input;
            }

            if (rowsPerPage) {
                Paginator.rowsPerPage = rowsPerPage;
            }
            
            Paginator.itemCount = input.length;

            return input.slice(parseInt(Paginator.page * Paginator.rowsPerPage), parseInt((Paginator.page + 1) * Paginator.rowsPerPage + 1) - 1);
        }
    });

  angular.module('plutusApp').filter('forLoop', function() {
        return function(input, start, end) {
            input = new Array(end - start);
            for (var i = 0; start < end; start++, i++) {
                input[i] = start;
            }

            return input;
        }
    });

   /* angular.module('rootApp').filter('counterValue', function() {
         return function(value){
            var valueInt = parseInt(value);
            if(value) {
                if(value => 0) {
                    if(value < 10) {
                        return "0"+ valueInt;
                    } else {
                        return valueInt;
                    }
                }
            }
            return "";
        }
    });*/

