var BodyStatsApp = angular.module('BodyStatsApp', []);
BodyStatsApp.controller('BodyCtrl', function($scope, $http) {
    $scope.stat = {};

    $http.get('bodystats/' + currentDate()).success(populateScope);

    $scope.addDailyBodyStats = function() {
        $http.put(  '/bodystats/' + currentDate(), 
            $scope.stat,
            { headers : { 'Content-Type' : 'application/json' }}
            ).success(populateScope);
        };

    function currentDate(){
        var d = new Date();
        var month = d.getMonth() + 1;
        var day = d.getDate();

        var output = d.getFullYear() + '-' +
        (month < 10 ? '0' : '') + month + '-' +
        (day < 10 ? '0' : '') + day;
        return output;
    }

    function populateScope(data){
        console.log("Got data, vikt = " + data.vikt);
        $scope.stat.vikt = data.vikt;
        $scope.stat.fett2 = data.fett2;
        $scope.stat.mage = data.mage;
        $scope.stat.brost = data.brost;
        $scope.stat.nacke = data.nacke;
    }
});

