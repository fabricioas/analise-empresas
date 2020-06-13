var app = angular.module('app', ['chart.js']);

app.controller('Empresa', function App($scope,$http) {

  $scope.labels = [];
  $scope.series = [];
  $scope.data = [];
  $scope.options = {}
  $scope.onClick = function (points, evt) {
    console.log(points, evt);
  };
  $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }];


  function setOptions( title ){
    $scope.options = {
      legendCallback: function(chart) {
        console.log(chart);
      },
      title:{
        display:true,
        text: title
      },
      scales: {
        yAxes: [
          {
            id: 'y-axis-1',
            type: 'linear',
            display: true,
            position: 'left'
          }       
        ]
      },
      legend:{
        display:true,
        fullWidth:false,
        align: 'start',
        position: 'left',
        boxWidth: '10'
      }
    };
  }
  

  init = function(dre){
    $scope.series.push(dre.serie);
    if( $scope.labels.length < dre.data.length ){
      for(var i = $scope.labels.length; i < dre.data.length;i++  ){
        $scope.labels = dre.labels;
      }
    }
    $scope.data.push(dre.data);
  }

  $http.get("./data/dre.json").then(function(response){
    var dadosEmpresa = response.data;
    var title = dadosEmpresa.cnpj +" - "+ dadosEmpresa.nome +" - " + dadosEmpresa.ramoAtividade;
    setOptions(title);
    for (var i in dadosEmpresa.dados) {
      init(dadosEmpresa.dados[i]);
    }
  });
  
});