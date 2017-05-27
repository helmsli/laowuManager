var nodeInfo={};
/*定义module*/
App.controller('app', ['$scope','$ocLazyLoad', function($scope, $ocLazyLoad){
	
	$scope.loadBootstrap = function(){
		var myFileList=[];
		myFileList=loadFileList(myFileList);
		//console.log("要加载的文件:"+myFileList);
        $ocLazyLoad.load(myFileList);
         pageNav.go(1,1);
    };
    
    $scope.loadBootstrap();
	
	$scope.initTree=function()
	{
		menu.leftMenu("treeData", 'tree', 'tree', function(event, treeId, treeNode) {
			if(typeof treeDatas!=='undefined'){
				var treeId = treeNode.id;
				$scope.treeId  = treeId;
				var dataList= treeDatas.showDataList(treeId);//显示角色列表
				$scope.list = dataList;
				$scope.$applyAsync($scope.list);
				if(dataList.length == 0){
					$(".action-group").removeClass("show").addClass("hide");
				}else{
					$(".action-group").removeClass("hide").addClass("show");
				}
			}
		});
	};
	//定义变量ID
	$scope.treeId = "";
	$scope.titleList = [
		"ID",
		"姓名",
		"性别",
		"E-mail"
	];
	$scope.list=[];
}]);


