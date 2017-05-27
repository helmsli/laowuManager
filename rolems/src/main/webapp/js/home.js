var nodeInfo={};
/*定义module*/
App.controller('app', ['$scope','$ocLazyLoad', function($scope, $ocLazyLoad){
	$scope.loadBootstrap = function(){
		var myFileList=[];
		myFileList=loadFileList(myFileList);
        $ocLazyLoad.load(myFileList);

    }
 	
 	$scope.initHead=function()
 	{
 		 
 	}
 	
 	$scope.showInFo=function()
 	{
 		
 	}
    $scope.loadBootstrap();
    
}]);

