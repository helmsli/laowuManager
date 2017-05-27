//付款审批
var taskPayApproveObj={
		examineResult:"",    //审核结果
		customerType:"",  //客户类型
		paymentType:"",//付款方类型
		selectBandwidth:"",//选择带宽
		rechargeNumber:"",//充值号码
		openingTime:"",   //开通时间
		selectProduct:"", //选择产品
		unitPrice:"",//单价
		orderCycle:"",//订购周期
		price:"",//价格
		fixedIPFee:"",//固定IP费
		deposit:"",//押金
		invoiceHeader:"",//发票抬头
		invoiceCycle:"",//发票周期
		constructionDistance:"",//施工距离
		attchmentList:"",//附件列表
		remarks:"",//备注
}
//回调函数
function taskPayApproveRefreshUi()
{  
    var scope=getAngularScope("projectModel");
    var state=scope.gloablParm.state;
}
//提交函数，需要提交给后台的
function submitApproval()
{
	var threeLeaderIdLsit="";
	var scope=getAngularScope("projectModel");
	var result = {};
	//构造APProve的结果;
	result.result = taskPayApproveObj.examineResult;
	result.comment = taskPayApproveObj;
	result.type = "approve";
	var data2="";
	if(scope.fileUpload[scope.gloablParm.state])
	{
		result.isAttach = APP_ContainAttach.attach;
		data2= JSON.stringify(scope.fileUpload[scope.gloablParm.state]);
	}
  	var obj={	   
			"request.taskId":scope.gloablParm.taskId,			
			"request.serviceType":scope.gloablParm.state,
			"request.data2":data2,
			
	};
  	console.log(JSON.stringify(obj));
  	//提交付款办理
  	submittaskPayApproveByServer(result,obj,function(data){
	    //console.log(JSON.stringify(data));
		if(data.result == 0){
			window.history.go(-1);
		}
    });	
}
function taskPayApprove()
{
	initFileStateListener(taskSurveyApproveRefreshUi);
}
taskPayApprove();











