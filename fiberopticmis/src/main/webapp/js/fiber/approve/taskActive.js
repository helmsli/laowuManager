//施工办理的审批(激活的审批)
//回调函数
function approvalRefreshUi()
{  
	console.log("这个啥时候调用");
	var scope=getAngularScope("projectModel");
	//激活需要填入的数字
	if(scope.activationOperator_activate!=undefined&&scope.activationOperator_activate!=null){
		var phase = scope.$$phase;
		if(phase =='$apply'||phase =='$digest'){
			scope.taskActive=scope.activationOperator_activate;
		}else{
			scope.$apply(function(){
				scope.taskActive=scope.activationOperator_activate;
			})
		}
	}else{
		var phase = scope.$$phase;
		if(phase =='$apply'||phase =='$digest'){
			scope.taskActive={
					activationDate:"", 	
					reviewResult:"0",
			 	 	customerType:"0",  	 	 	 
			 	 	gateway:"", 	 	 	 
			 	 	VLAN:"", 	 	 
			 	 	DNS:"", 	 	 
			 	 	switchIP:"",  	 	 
			 	 	switchPort:"", 	 	 	 	 
			 	 	userIP:"",	 	 
			 	 	notes:"",
			 }
		}else{
			scope.$apply(function(){
				scope.taskActive={
						activationDate:"", 	
						reviewResult:"0",
				 	 	customerType:"0",  	 	 	 
				 	 	gateway:"", 	 	 	 
				 	 	VLAN:"", 	 	 
				 	 	DNS:"", 	 	 
				 	 	switchIP:"",  	 	 
				 	 	switchPort:"", 	 	 	 	 
				 	 	userIP:"",	 	 
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
	scope.taskActive.activationDate=$("#activeDate").val();
	var taskActive=scope.taskActive;
	var data1=JSON.stringify(taskActive);
	var data2="";
	var obj={	
  			"request.result":taskActive.reviewResult,
  			"request.groupId":scope.gloablParm.groupId,
  			"request.taskId":scope.gloablParm.taskId,
  			"request.data1":data1,
  			"request.data2": data2,
  			"request.processInstanceId":scope.gloablParm.procInstId,
			
	};
  	console.log(JSON.stringify(obj));
  	submitTaskApproveByServer(obj,function(data){
  		
	    //console.log(JSON.stringify(data));
		if(data.result == 0){
			swal({
		    	title: "Success",
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











