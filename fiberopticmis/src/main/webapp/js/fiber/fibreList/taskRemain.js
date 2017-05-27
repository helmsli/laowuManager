App.controller('taskRemain', function($scope) {
	$scope.queryIsByCondition=false;
	$scope.pageFlag=true;
	$scope.pageShowFlag=false;//这个控制分页显示还是不显示
	$scope.open=function(status,formNo,procInstId,taskId,applyService,orderType,taskDefinitionKey,dataId){
		var applyService=applyService||orderType;
		var taskDefinitionKey=taskDefinitionKey.replace(/\s/g,"");
		 status=status.replace(/\s/g,"");
		 if(taskDefinitionKey=="seller_modify"){
			 url = "openAccountModify.html?from=taskRemain.html"+"&state="+status+"&groupId="+formNo+"&procInstId="+procInstId+"&taskId="+taskId+"&taskKey="+taskDefinitionKey+"&dataId="+dataId;
		 }else if(taskDefinitionKey=="extension_seller_modify"){
			url = "extensionModify.html?from=taskRemain.html"+"&state="+status+"&groupId="+formNo+"&procInstId="+procInstId+"&taskId="+taskId+"&taskKey="+taskDefinitionKey+"&dataId="+dataId; 
		 }else{
				if(applyService=="extension"){
					url = "task_extensionDetail.html?from=taskRemain.html"+"&state="+status+"&groupId="+formNo+"&procInstId="+procInstId+"&taskId="+taskId+"&taskKey="+taskDefinitionKey+"&dataId="+dataId; 
				   }else{
					   url = "task_openAccountDetail.html?from=taskRemain.html"+"&state="+status+"&groupId="+formNo+"&procInstId="+procInstId+"&taskId="+taskId+"&taskKey="+taskDefinitionKey+"&dataId="+dataId;
				   }
		 }
		  // console.log(url+"#firstAnchor");
		  // return ;
		   //location.hash="firstAnchor";
	    	location.href=url;
	    }
	//数据初始化--所有项目列表,页面一初始化就得出现
    $scope.initTab=function(pageNum){
    	$scope.queryIsByCondition=false;
    	var searchScope=getAngularScope("searchInfo");
    	var pageNum=pageNum||1;
    	//获取浏览器的语言
        var	currentLang = navigator.language; 
        currentLang=currentLang.split("-")[0];
        var RemianTsakObj = {
        		   "request.language":currentLang,
	    		   "page.pageNum":pageNum,
	    		   "page.pageSize":10,
	     };
     console.log(RemianTsakObj);
     getTaskRemainListByServer(RemianTsakObj,function(data){
			if(data.result==0){
				console.log(data);
				var dataLists=data.responseInfo.lists;
				if(dataLists.length!=0){
					for(var i in dataLists ){
						var applicationInfo=dataLists[i].extData.applicationInfo;
						if(dataLists[i].extData.applicationInfo!=undefined){
							console.log(dataLists[i].extData);
							dataLists[i].applyDate=applicationInfo.applyDate;
							//申请人现在不显示
							dataLists[i].formNo=applicationInfo.formNo;
							dataLists[i].customerName=applicationInfo.customerName;
							dataLists[i].orderType=applicationInfo.applyService;
							dataLists[i].status=dataLists[i].taskName;
							dataLists[i].procInstId=applicationInfo.procInstId;
							dataLists[i].taskId=dataLists[i].taskId;
							//待办到一个组
							dataLists[i].reviewer=dataLists[i].assigneeList[0].assigneeName;
							dataLists[i].applicant=applicationInfo.applicantInfo.firstname;
						}
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
     * 按照条件进行查询的函数
     */
    $scope.queryByCondition=function(pageNum){
    	$scope.queryIsByCondition=true;
    	var searchScope=getAngularScope("searchInfo");
    	console.log("按条件");
    	var pageNum=pageNum||1;
    	//获取浏览器的语言
        var	currentLang = navigator.language; 
        currentLang=currentLang.split("-")[0];
        var RemianTsakObj = {
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
        console.log(RemianTsakObj);
     getTaskRemainListQueryByServer(RemianTsakObj,function(data){
			if(data.result==0){
					var dataLists=data.responseInfo.lists;
					if(dataLists!=null&&dataLists.length!=0){
						$scope.pageShowFlag=false;
						for(var i in dataLists ){
							var applicationInfo=dataLists[i].extData.applicationInfo;
							dataLists[i].applyDate=applicationInfo.applyDate;
							//申请人现在不显示
							dataLists[i].formNo=applicationInfo.formNo;
							dataLists[i].customerName=applicationInfo.customerName;
							dataLists[i].orderType=applicationInfo.applyService;
							dataLists[i].status=dataLists[i].taskName;
							dataLists[i].procInstId=applicationInfo.procInstId;
							dataLists[i].taskId=dataLists[i].taskId;
							//待办到一个组
							dataLists[i].reviewer=dataLists[i].assigneeList[0].assigneeName;
							dataLists[i].applicant=applicationInfo.applicantInfo.firstname;
						}
						$scope.dataLists=dataLists;
						var phase = $scope.$$phase;
						if(phase =='$apply'||phase =='$digest'){
						}else{
							$scope.$apply(function(){$scope.dataLists=dataLists;});
						}
						
						
						var pageInfo=data.responseInfo.page;
						if($scope.pageByConditionFlag)
						{
							console.log("看看我的分页33");
							$scope.pageByConditionFlag=false;
							pageNav.go(pageInfo.pageNum, pageInfo.pages);
						}
					}else{
						$scope.pageShowFlag=true;
						$scope.dataLists=dataLists;
						var phase = $scope.$$phase;
						if(phase =='$apply'||phase =='$digest'){
						}else{
							$scope.$apply(function(){$scope.dataLists=dataLists;});
						}
						
						
					}
			
			}else{
				$scope.pageShowFlag=true;
			}
		});
	}
   
})
    
    /**
	 * 统一列表分页渲染初始化,首先调用这个
	 * */
	pageNav.fn = function(pageNum){
		var scope=getAngularScope("taskRemain");
		console.log("看看是不是初始化了");
		if(scope.queryIsByCondition){
			initTab(pageNum);
		}else{
			scope.initTab(pageNum);
		}
	 };
	 //这个是为了让前边的searchinfo使用
	var initTab=function(pageNum){
		var scope=getAngularScope("taskRemain");
		scope.queryByCondition(pageNum);
	}  
	
	
	
	
	
	
	

