var DailyStatsApp = angular.module('DailyStatsApp', []);
DailyStatsApp.controller('DailyCtrl', function($scope, $http) {
    $scope.stat = {};

    $http.get('dailystats/' + currentDate()).success(populateScope);

    $scope.addDailyDailyStats = function() {
        console.log(recalculateStat());

        
        $http.put('/dailystats/' + currentDate(), 
                    recalculateStat(),
                    { headers : { 'Content-Type' : 'application/json' }}
                  ).success(populateScope);

    };

    function recalculateStat(){
        console.log("Unparsed frukost: " + $scope.stat.frukost);
        console.log("Unparsed traning: " + $scope.stat.traning);
        console.log("Unparsed fasta: " + $scope.stat.traning);
        var allMat = parseInt($scope.stat.frukost) + parseInt($scope.stat.lunch) + parseInt($scope.stat.middag) + parseInt($scope.stat.ingetsocker);
        return {"mat" : parseInt(allMat), "traning" : parseInt($scope.stat.traning), "fasta" : parseInt($scope.stat.fasta)}
    }

    function populateScope(data){
        console.log("Got data, fasta = " + data.fasta);
        $scope.stat.frukost = 0;
        $scope.stat.lunch = 0;
        $scope.stat.middag = 0;
        $scope.stat.ingetsocker = 0;

        var zeroString = 0;
        zeroString = zeroString.toString();

        if(data.fasta && data.fasta > 0){
            $scope.stat.fasta=data.fasta;
            $(document.getElementById('checkbox-6')).attr("checked", true).checkboxradio('refresh')
        } else{
            model=0;
            $(document.getElementById('checkbox-6')).attr("checked", false).checkboxradio('refresh')
        }

        if(data.traning && data.traning > 0){
            $scope.stat.traning=data.traning;
            $(document.getElementById('checkbox-5')).attr("checked", true).checkboxradio('refresh')
        } else{
            model=0;
            $(document.getElementById('checkbox-5')).attr("checked", false).checkboxradio('refresh')
        }



    }



    function currentDate(){
        if($scope.stat.date){
            console.log("Provided date: " + $scope.stat.date);
            return $scope.stat.date;
            
        }
        
        var d = new Date();
        var month = d.getMonth() + 1;
        var day = d.getDate();

        var output = d.getFullYear() + '-' +
        (month < 10 ? '0' : '') + month + '-' +
        (day < 10 ? '0' : '') + day;
        console.log("Todays date: " + output);
        return output;
    }
});