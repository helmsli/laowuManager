//customerInfo
//回调函数
function approvalRefreshUi()
{  
	console.log("这个啥时候调用");
	var scope=getAngularScope("projectModel");
	if(scope.contractReviewer_review!=undefined&&scope.contractReviewer_review!=null){
		var phase = scope.$$phase;
		if(phase =='$apply'||phase =='$digest'){
			scope.taskContractReview=scope.contractReviewer_review;
		}else{
			scope.$apply(function(){
				scope.taskContractReview=scope.contractReviewer_review;
			})
		}
		
	}else{
		var phase = scope.$$phase;
		if(phase =='$apply'||phase =='$digest'){
			scope.taskContractReview={
					reviewResult:"0",		 	 	 
				 	totalAmount:"", 	 	 
				 	installationFee:"", 		 	 	 
				 	productFee:"", 	 	 	 
				 	userBalance:"", 	 	 	 
				 	notes:"", 	 
			 }
		
		}else{
				scope.$apply(function(){
					scope.taskContractReview={
							reviewResult:"0",		 	 	 
						 	totalAmount:"", 	 	 
						 	installationFee:"", 		 	 	 
						 	productFee:"", 	 	 	 
						 	userBalance:"", 	 	 	 
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
	scope.fileUpload={};
	var data1=JSON.stringify(scope.taskContractReview);
	var data2="";
	if(scope.fileUpload[scope.gloablParm.state])
	{
		result.isAttach = APP_ContainAttach.attach;
		data2= JSON.stringify(scope.fileUpload[scope.gloablParm.state]);
	}
  	var obj={	   
  			"request.result":scope.taskContractReview.reviewResult,
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











