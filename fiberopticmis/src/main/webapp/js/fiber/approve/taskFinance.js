//财务成本管理审核
//回调函数
function approvalRefreshUi()
{  

var scope=getAngularScope("projectModel");
if(scope.financeAdmin_review!=undefined&&scope.financeAdmin_review!=null){
	
	var phase = scope.$$phase;
	if(phase =='$apply'||phase =='$digest'){
		scope.taskFinanceObj=scope.financeAdmin_review;
	}else{
		scope.$apply(function(){
			scope.taskFinanceObj=scope.financeAdmin_review;
		})
	}
	

}else{
	
	var phase = scope.$$phase;
	if(phase =='$apply'||phase =='$digest'){
		scope.taskFinanceObj={
				invoiceNumber:"", 		 	 	 
		 	 	invoiceAmount:"", 	 	 	 	 
		 	 	invoicePeriodStartTime:"", 	 	 	 	 
		 	 	invoicePeriodEndTime:"", 		 	 	 
		 	 	receivedDate :"",		 	 	 
		 	 	received :"0",		 	 	 
		 	 	IR :"",	 	 	 
		 	 	IMI:"", 		 	 	 
		 	 	receivedAmount :"",		 	 	 
		 	 	receivedMethodCash :"",	
		 	 	receivedMethodBasic:"",
		 	 	depositAmount :"",	 	 	 	 
		 	 	notes :"",
		}
	}else{
		scope.$apply(function(){
			scope.taskFinanceObj={
					invoiceNumber:"", 		 	 	 
			 	 	invoiceAmount:"", 	 	 	 	 
			 	 	invoicePeriodStartTime:"", 	 	 	 	 
			 	 	invoicePeriodEndTime:"", 		 	 	 
			 	 	receivedDate :"",		 	 	 
			 	 	received :"0",		 	 	 
			 	 	IR :"",	 	 	 
			 	 	IMI:"", 		 	 	 
			 	 	receivedAmount :"",		 	 	 
			 	 	receivedMethodCash :"",	
			 	 	receivedMethodBasic:"",
			 	 	depositAmount :"",	 	 	 	 
			 	 	notes :"",
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
	scope.taskFinanceObj.invoicePeriodStartTime=$("#invoicePeriodStartTime").val();
	scope.taskFinanceObj.invoicePeriodEndTime=$("#invoicePeriodEndTime").val();
	scope.taskFinanceObj.receivedDate=$("#receivedDate").val();
	var taskFinanceObj=scope.taskFinanceObj;
	if(scope.taskFinanceObj.invoiceAmount==""){
		scope.taskFinanceObj.invoiceAmount="0";
	}
	if(scope.taskFinanceObj.IMI==""){
		scope.taskFinanceObj.IMI="0";
	}
	if(scope.taskFinanceObj.depositAmount==""){
		scope.taskFinanceObj.depositAmount="0";
	}
	if(scope.taskFinanceObj.receivedAmount==""){
		scope.taskFinanceObj.receivedAmount="0";
	}
	var data1=JSON.stringify(taskFinanceObj);
  	var obj={	   
  			"request.groupId":scope.gloablParm.groupId,
  			"request.taskId":scope.gloablParm.taskId,
  			"request.data1":data1,
  			"request.processInstanceId":scope.gloablParm.procInstId,
			
			
	};
  	console.log(JSON.stringify(obj));
  	submitTaskApproveByServer(obj,function(data){
	    console.log(JSON.stringify(data));
		if(data.result == "0"){
			swal({
		    	title: "Sucess",
		    	timer: 2000,
		    	confirmButtonText: "Make Sure"
		    },function(){
		    	window.location.href = "taskRemain.html";
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
}
initApproval();











