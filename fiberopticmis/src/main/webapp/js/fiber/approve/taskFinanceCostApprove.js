//财务成本管理审核
//回调函数
var suggestion={
		result:0,
		remarks:"",
	}
function approvalRefreshUi()
{  
//把财务成本管理审核时需要知道的费用的基本信息给带回来。
var getApproveNeedInfoObj={
		//请求这些数据时需要发给后台的数据
}
getTaskFinanceCostApproveNeedInfoByServer(getApproveNeedInfoObj,function(data){
	//把这些获得数据绑定到前端的页面上去。
});
var scope=getAngularScope("projectModel");
var state=scope.gloablParm.state;
}
//提交函数，需要提交给后台的
function submitApproval()
{
	var scope=getAngularScope("projectModel");
	var result = {};
	//构造APProve的结果;
	result.result = scope.suggestion.result;
	result.remarks = scope.suggestion.remarks;
	result.type = "approve";
	scope.setCycleTimeObj.unit=changeEnghlish(scope.setCycleTimeObj.unit);
  	var obj={	   
			"request.taskId":scope.gloablParm.taskId,			
			"request.serviceType":scope.gloablParm.state,
			"request.data2":data2,
			"request.data10":threeLeaderIdLsit,//指定三级部门经理的
			
	};
  	console.log(JSON.stringify(obj));
  	taskFinanceCostApproveByServer(result,obj,function(data){
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











