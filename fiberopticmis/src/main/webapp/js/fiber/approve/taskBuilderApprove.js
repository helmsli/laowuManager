//施工审批
var suggestion={
		result:0,//结果
		attachmentLsit:[],//附件列表
		remarks:"",//备注
		constructionTime:"",//施工时间
		completionTime:"",//完成时间
};
//回调函数
function approvalRefreshUi()
{  
	var scope=getAngularScope("projectModel");
	scope.suggestion=suggestion;
	var state=scope.gloablParm.state;
}


//点击上传需要绑定的函数，需要向后台提交的东西
function uploadButtonSubmit(id){
	uploadFile.ajaxFileUpload({
	    url: getBasePath()+'/projectAnnex/upload', //用于文件上传的服务器端请求地址
	    fileElementId: id, //文件上传空间的id属性  <input type="file" id="file" name="file" />
	    //async:true,
	    success: function (data)
	    {
	    	var scope=getAngularScope("projectModel");
	    	data=JSON.parse(data);
	    	if(!scope.fileUpload)
	    	{
	    		scope.fileUpload=new Object();
	    	}
	    	console.log("**************");
	    	console.log(data);
	    	scope.fileUpload[scope.gloablParm.state]= data.responseInfo.projectAnnexs;
	    	scope.projectAnnexs=data.responseInfo.projectAnnexs;
	    	//console.log(JSON.stringify(data.responseInfo.projectAnnexs));
	    	scope.$apply(scope.projectAnnexs);
	    },
	    error: function (data, status, e)//服务器响应失败处理函数  
	    {
	        alert(e);
	    }
	});
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
			"request.data2":data2,
			
	};
  	console.log(JSON.stringify(obj));
  	taskBuilderApproveByServer(result,obj,function(data){
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











