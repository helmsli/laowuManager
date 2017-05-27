App.controller('taskFinish', function($scope) {
	$scope.queryIsByCondition=false;
 	$scope.pageFlag=true;	
 	$scope.pageShowFlag=false;//这个控制分页显示还是不显示
	$scope.open=function(status,formNo,procInstId,taskId,approveFlag,applyService,orderType){
		var applyService=applyService||orderType;
		console.log("看看applyService");
		console.log(applyService);
	    status=status.replace(/\s/g,"");
	    if(applyService=="extension"){
	    	url = "task_extensionDetail.html?from=taskFinish.html"+"&state="+status+"&groupId="+formNo+"&procInstId="+procInstId+"&taskId="+taskId+"&approveFlag="+approveFlag;
	    }else{
	    	url = "task_openAccountDetail.html?from=taskFinish.html"+"&state="+status+"&groupId="+formNo+"&procInstId="+procInstId+"&taskId="+taskId+"&approveFlag="+approveFlag;
	    }
    	location.href=url;
    }
	//数据初始化--所有项目列表,页面一初始化就得出现
    $scope.initTab=function(pageNum){
    	$scope.queryIsByCondition=false;
    	var pageNum=pageNum||1;
        var	currentLang = navigator.language; 
        currentLang=currentLang.split("-")[0];
        console.log(currentLang);
    	var searchScope=getAngularScope("searchInfo");
        var objFinishedTsak = {
        		   "request.language":currentLang,
	    		   "page.pageNum":pageNum,
	    		   "page.pageSize":10,
	     };
	  getTaskFinishListByServer(objFinishedTsak,function(data){
		  console.log(data);
			if(data.result==0){
				var dataLists=data.responseInfo.lists;
				if(dataLists.length!=0){
					for(var i in dataLists ){
						var extData=dataLists[i].extData;
						var stateInfo=JSON.parse(extData.stateInfo);
						var applicationInfo=extData.applicationInfo;
						dataLists[i].applyDate=applicationInfo.applyDate;
						//申请人现在不显示
						dataLists[i].formNo=applicationInfo.formNo;
						dataLists[i].customerName=applicationInfo.customerName;
						dataLists[i].orderType=applicationInfo.applyService;
						dataLists[i].status=stateInfo.stateName;
						dataLists[i].procInstId=applicationInfo.procInstId;
						dataLists[i].taskId=dataLists[i].taskId;
						//待办到一个组
						if(stateInfo.assigneeList!=undefined&&stateInfo!=undefined){
							dataLists[i].reviewer=stateInfo.assigneeList[0].assigneeName;
						}
						if(stateInfo.assigneeList==undefined){
							dataLists[i].reviewer="N/A";
						}
						dataLists[i].applicant=applicationInfo.applicantInfo.firstname;
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
						console.log("看看我的分页33");
						$scope.pageFlag=false;
						pageNav.go(pageInfo.pageNum, pageInfo.pages);
					}
				}else{
					$scope.pageShowFlag=true;
				}
					
				}else{
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
        getTaskFinishListConditionByServer(RemianTsakObj,function(data){
			if(data.result==0){
					var dataLists=data.responseInfo.lists;
					if(dataLists!=null&&dataLists.length!=0){
						$scope.pageShowFlag=false;
						for(var i in dataLists ){
							var extData=dataLists[i].extData;
							var stateInfo=JSON.parse(extData.stateInfo);
							var applicationInfo=extData.applicationInfo;
							if(applicationInfo!=undefined){
								dataLists[i].applyDate=applicationInfo.applyDate;
								//申请人现在不显示
								dataLists[i].formNo=applicationInfo.formNo;
								dataLists[i].customerName=applicationInfo.customerName;
								dataLists[i].orderType=applicationInfo.applyService;
								dataLists[i].procInstId=applicationInfo.procInstId;
								dataLists[i].applicant=applicationInfo.applicantInfo.firstname;
							}
						
							dataLists[i].status=stateInfo.stateName;
							dataLists[i].taskId=dataLists[i].taskId;
							//待办到一个组
							if(stateInfo!=undefined&&stateInfo.assigneeList!=undefined&&stateInfo.assigneeList!=null){
								dataLists[i].reviewer=stateInfo.assigneeList[0].assigneeName;	
							}
							if(stateInfo.assigneeList==undefined){
								dataLists[i].reviewer="N/A";
							}
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
						$scope.dataLists=dataLists;
						$scope.pageShowFlag=true;
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
		var scope=getAngularScope("taskFinish");
		console.log("看看是不是初始化了");
		if(scope.queryIsByCondition){
			initTab(pageNum);
		}else{
			scope.initTab(pageNum);
		}
		
	 };
	 //这个是为了让前边的searchinfo使用
	var initTab=function(pageNum){
		var scope=getAngularScope("taskFinish");
		scope.queryByCondition(pageNum);
	}  
