var app = angular.module('app', ['chart.js']);

app.controller('Empresa', function App($scope,$http) {

  $scope.labels = [];
  $scope.series = [];
  $scope.data = [];
  $scope.onClick = function (points, evt) {
    console.log(points, evt);
  };
  $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }];
  $scope.options = {
    scales: {
      yAxes: [
        {
          id: 'y-axis-1',
          type: 'linear',
          display: true,
          position: 'left'
        }       
      ]
    }
  };
  
  

  this.init = function(data){
    $scope.series.push(data.name);
    if( $scope.labels.length < data.series.length ){
  	  for(var i = $scope.labels.length; i < data.series.length;i++  ){
  		  $scope.labels.push(i);
  	  }
    }
    $scope.data.push(data.series);
  }
  
  this.init(Brazil);
  this.init(Italy);
  this.init(Spain);
  this.init(US);
  
});