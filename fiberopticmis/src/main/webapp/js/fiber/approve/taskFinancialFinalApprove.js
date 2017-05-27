//财务终审办理的审批
//回调函数
function approvalRefreshUi()
{  
	var scope=getAngularScope("projectModel");
	scope.suggestion=suggestion;
    var state=scope.gloablParm.state;
  //把财务终审办理审核时需要知道的费用的基本信息给带回来。
    var getApproveNeedInfoObj={
    		//请求这些数据时需要发给后台的数据
    }
    getTaskFinancialFinalApproveNeedInfoByServer(getApproveNeedInfoObj,function(data){
    	if(data.result==0){
    		//把这些获得数据绑定到前端的页面上去。
    	}
    });
}
//提交函数，需要提交给后台的
function submitApproval()
{
	var threeLeaderIdLsit="";
	var scope=getAngularScope("projectModel");
	var projectManagerModelScope=getAngularScope("projectManagerModel");
	var result = {};
	//构造APProve的结果;
	result.result = scope.suggestion.result;
	result.comment = scope.suggestion.comment;
	result.type = "approve";
	var data2="";
	if(scope.fileUpload[scope.gloablParm.state])
	{
		result.isAttach = APP_ContainAttach.attach;
		data2= JSON.stringify(scope.fileUpload[scope.gloablParm.state]);
	}
	if(projectManagerModelScope!=undefined){
		threeLeaderIdLsit=JSON.stringify(projectManagerModelScope.threeLeaderIdLsit);
	}
	scope.setCycleTimeObj.unit=changeEnghlish(scope.setCycleTimeObj.unit);
  	var obj={	   
			"request.taskId":scope.gloablParm.taskId,			
			"request.serviceType":scope.gloablParm.state,
			"request.data1":threeLeaderIdLsit,//指定三级部门经理的
			
	};
  	console.log(JSON.stringify(obj));
	completeTask(result,obj,function(data){
	    //console.log(JSON.stringify(data));
		if(data.result == 0){
			window.history.go(-1);
		}
    });	
}


function initApproval()
{
	initFileStateListener(approvalRefreshUi);
}
initApproval();










