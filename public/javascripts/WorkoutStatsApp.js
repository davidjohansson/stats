var WorkoutStatsApp = angular.module('WorkoutStatsApp', []);
WorkoutStatsApp.controller('WorkoutCtrl', function($scope, $http) {
    $scope.stat = {};

    $http.get('workoutstats/' + currentDate()).success(populateScope);

    $scope.addWorkoutStats = function() {
        $http.put(  '/workoutstats/' + currentDate(), 
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
        for(var key in data){
            console.log("Got data, " + key + "=" + data[key]);
            $scope.stat[key] = data[key];
        }
    }
});

