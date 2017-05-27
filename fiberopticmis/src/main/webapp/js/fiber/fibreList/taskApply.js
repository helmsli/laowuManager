App.controller('taskApply', function($scope) {
	$scope.queryIsByCondition=false;
	$scope.pageFlag=true;
	$scope.open=function(status,formNo,procInstId,taskId,approveFlag,applyService,orderType,dataId){
		console.log("++++___))_++_)))");
		console.log(dataId);
		var applyService=applyService||orderType;
		console.log("看看applyService");
		console.log(applyService);
		status=status.replace(/\s/g,"");
		if(applyService=="extension"){
			if(status=="SellerModify"){
				url = "extensionModify.html?from=taskApply.html"+"&state="+status+"&groupId="+formNo+"&procInstId="+procInstId+"&taskId="+taskId+"&dataId="+dataId;
			}else{
				url = "task_extensionDetail.html?from=taskApply.html"+"&state="+status+"&groupId="+formNo+"&procInstId="+procInstId+"&taskId="+taskId+"&approveFlag="+approveFlag;
			}
		}
		if(applyService=="openAccount"){
		if(status=="PendingModification"){
			 url = "openAccountModify.html?from=taskApply.html"+"&state="+status+"&groupId="+formNo+"&procInstId="+procInstId+"&taskId="+taskId+"&taskKey="+status+"&dataId="+dataId;
			}else if(status=="PendingApplication"){
				url = "task_openAccountDetail.html?from=taskApply.html"+"&state="+status+"&groupId="+formNo+"&procInstId="+procInstId+"&taskId="+taskId;
			}
			else{
				url = "task_openAccountDetail.html?from=taskApply.html"+"&state="+status+"&groupId="+formNo+"&procInstId="+procInstId+"&taskId="+taskId+"&approveFlag="+approveFlag;
			}
			
		}
	    
    	location.href=url;
    }
	//数据初始化--所有项目列表,页面一初始化就得出现
    $scope.initTab=function(pageNum){
    	$scope.queryIsByCondition=false;
    	var pageNum=pageNum||1;
    	//获取浏览器的语言
        var	currentLang = navigator.language; 
        currentLang=currentLang.split("-")[0];
        console.log(currentLang);
    	var searchScope=getAngularScope("searchInfo");
        var objtaskApply = {
	    		   "request.language":currentLang,
	    		   "page.pageNum":pageNum,
	    		   "page.pageSize":10,
	     };
        console.log(objtaskApply);
        //正式的时候需要重新放开
      getTaskApplyListByServer(objtaskApply,function(data){
			if(data.result==0){
				var dataLists=data.responseInfo.lists;
				if(dataLists.length!=0){
					for(var i in dataLists ){
						var stateInfo=JSON.parse(dataLists[i].stateInfo);
						dataLists[i].taskId=stateInfo.taskId;
						//当流程结束的话stateInfo.assigneeList等于undefined
						if(stateInfo.assigneeList!=undefined){
							dataLists[i].reviewer=stateInfo.assigneeList[0].assigneeName;
						}
						if(stateInfo.assigneeList==undefined){
							dataLists[i].reviewer="N/A";
						}
						dataLists[i].status=stateInfo.stateName;
					}
					$scope.dataLists=dataLists;
					var phase = $scope.$$phase;
					if(phase =='$apply'||phase =='$digest'){
						
					}else{
						$scope.$apply(function(){
							$scope.dataLists=dataLists;
						});
					}
					
					var pageInfo=data.responseInfo.page;
					if($scope.pageFlag)
					{
						$scope.pageFlag=false;
						pageNav.go(pageInfo.pageNum, pageInfo.pages);
					}
					
				}else{
					$scope.pageShowFlag=true;
				}
				
			}else{
				console.log("获取不成功");
				$scope.pageShowFlag=true;
			}
		});
	}
    
/*
 * 按照条件进行查询
 */
  $scope.queryByCondition=function(pageNum){
	  $scope.queryIsByCondition=true;
    	var pageNum=pageNum||1;
    	//获取浏览器的语言
        var	currentLang = navigator.language; 
        currentLang=currentLang.split("-")[0];
        console.log(currentLang);
    	var searchScope=getAngularScope("searchInfo");
        var objtaskApply = {
	    		   "request.language":currentLang,
	    		   "page.pageNum":pageNum,
	    		   "page.pageSize":10,
	    		   //下边的这些是从搜索的controller得到的
	    		   
	    		   "request.startTime":searchScope.searchObjforList.startTime,
	    		   "request.endTime":searchScope.searchObjforList.endTime,
	    		   "request.orderType":searchScope.searchObjforList.orderType,
	    		   "request.queryItem":searchScope.searchObjforList.queryType,
	    		   "request.itemValue":searchScope.searchObjforList.numContent
	     };
        //正式的时候需要重新放开
      getTaskApplyListQueryByServer(objtaskApply,function(data){
			if(data.result==0){
				console.log(data);
				var dataLists=data.responseInfo.lists;
				if(dataLists.length!=0){
					$scope.pageShowFlag=false;
					for(var i in dataLists ){
						var stateInfo=JSON.parse(dataLists[i].stateInfo);
						dataLists[i].taskId=stateInfo.taskId;
						//当流程结束的话stateInfo.assigneeList等于undefined
						if(stateInfo.assigneeList!=undefined){
							dataLists[i].reviewer=stateInfo.assigneeList[0].assigneeName;
						}
						if(stateInfo.assigneeList==undefined){
							dataLists[i].reviewer="N/A";
						}
						dataLists[i].status=stateInfo.stateName;
					}
					$scope.dataLists=dataLists;
					var phase = $scope.$$phase;
					if(phase =='$apply'||phase =='$digest'){
					}else{
						$scope.$apply(function(){
							$scope.dataLists=dataLists;
						});
					}
					
					var pageInfoQuery=data.responseInfo.page;
					console.log("jjjjj");
					console.log(pageInfoQuery);
					console.log($scope.pageByConditionFlag);
					if($scope.pageByConditionFlag)
					{
						$scope.pageByConditionFlag=false;
						console.log(pageInfoQuery);
						pageNav.go(pageInfoQuery.pageNum, pageInfoQuery.pages);
					}
				}else{
					$scope.pageShowFlag=true;
					$scope.dataLists=dataLists;
					var phase = $scope.$$phase;
					if(phase =='$apply'||phase =='$digest'){
					}else{
						$scope.$apply(function(){
							$scope.pageShowFlag=true;
							$scope.dataLists=dataLists;
							});
					}
					
				}
				
			}else{
				console.log("获取不成功");
				$scope.pageShowFlag=true;
			}
		});
	}

 
})


    
    /**
	 * 统一列表分页渲染初始化,首先调用这个
	 * */
	pageNav.fn = function(pageNum){
		var scope=getAngularScope("taskApply");
		console.log("看看是不是初始化了");
		console.log(scope.queryIsByCondition);
		if(scope.queryIsByCondition){
			initTab(pageNum);
		}else{
			scope.initTab(pageNum);
		}
		
	 };
	 //这个是为了让前边的searchinfo使用
	var initTab=function(pageNum){
		var scope=getAngularScope("taskApply");
		scope.queryByCondition(pageNum);
	}  
