//安装审批（勘测审批）
//回调函数
function approvalRefreshUi()
{  
	console.log("这个啥时候调用");
	var scope=getAngularScope("projectModel");
	//合同审批
	if(scope.installer_installation!=undefined&&scope.installer_installation!=null){
		var phase = scope.$$phase;
		if(phase =='$apply'||phase =='$digest'){
			scope.taskInstallate=scope.installer_installation;
		}else{
			scope.$apply(function(){
				scope.taskInstallate=scope.installer_installation;
			})
		}
	}else{
		
		var phase = scope.$$phase;
		if(phase =='$apply'||phase =='$digest'){
			scope.taskInstallate={
					reviewResult:"0",
					startingDate:"",  	 	 
			 	 	completionDate:"",  	 	 
			 	 	notes:"",
			 }
		}else{
			scope.$apply(function(){
				scope.taskInstallate={
						reviewResult:"0",
						startingDate:"",  	 	 
				 	 	completionDate:"",  	 	 
				 	 	notes:"",
				 }
			})
		}
	}

	var state=scope.gloablParm.state;
}
//提交函数，需要提交给后台的
function submitApproval()
{
	//提交按钮置灰
	document.getElementById("submitButtonFlag").disabled=true;
	var scope=getAngularScope("projectModel");
	scope.taskInstallate.startingDate=$("#startingDate").val();
	scope.taskInstallate.completionDate=$("#completionDate").val();
	var taskInstallate=scope.taskInstallate;
	var data1=JSON.stringify(taskInstallate);
	var data2="";
  	var obj={	   
			"request.result":taskInstallate.reviewResult,
  			"request.groupId":scope.gloablParm.groupId,
  			"request.taskId":scope.gloablParm.taskId,
  			"request.data1":data1,
  			"request.data2": data2,
  			"request.processInstanceId":scope.gloablParm.procInstId,
			
	};
  	console.log(JSON.stringify(obj));
  	submitTaskApproveByServer(obj,function(data){
	    console.log(JSON.stringify(data));
		if(data.result == 0){
			swal({
		    	title: "Sucess",
		    	timer: 2000,
		    	confirmButtonText: "Make Sure"
		    },function(){
		    	window.history.go(-1);
		    });
		}else{
			swal({
		    	title: "Submit failed",
		    	timer: 2000,
		    	confirmButtonText: "Make Sure"
		    },function(){
		    	document.getElementById("submitButtonFlag").disabled=false;
		    });
		
		}
    });	
}
function initApproval()
{
	initFileStateListener(approvalRefreshUi);
	console.log("看看initApproval执行了么");
}
initApproval();











