//勘测审批
//回调函数
function taskSurveyApproveRefreshUi()
{   
	console.log("我已经调到design的回调函数了");
    var scope=getAngularScope("projectModel");
    var state=scope.gloablParm.state;
    //勘测报告信息
    if(scope.designer_design!=undefined&&scope.designer_design!=null){
    	
    	var phase = scope.$$phase;
		if(phase =='$apply'||phase =='$digest'){
			scope.designReport=scope.designer_design;
		}else{
			scope.$apply(function(){
	    		scope.designReport=scope.designer_design;
	    	});
		}
    }else{
    	var phase = scope.$$phase;
		if(phase =='$apply'||phase =='$digest'){
			scope.designReport={
				    reviewResult:"0",
				    accessBTSNo:"",//接入基站编号
					distance:"",//距离
					fibreNo:"",//光纤号码
					installationCostEstimated:"",//预计安装费用
					attachmentList:[], //附件列表
					attatchmenResult:false,
					notes:""//备注
		   };
		}else{
			scope.$apply(function(){
	    		scope.designReport={
	    			    reviewResult:"0",
	    			    accessBTSNo:"",//接入基站编号
	    				distance:"",//距离
	    				fibreNo:"",//光纤号码
	    				installationCostEstimated:"",//预计安装费用
	    				attachmentList:[], //附件列表
	    				attatchmenResult:false,
	    				notes:""//备注
	    	 };
	    	});
		}
    	
    	
    }
	
	scope.fileUpload={};
}
//点击*文件删除
var scope=getAngularScope("projectModel");
scope.deletAttachment=function(index){
	var scope=getAngularScope("projectModel");
	console.log(scope.attachmentList);
	console.log(index);
 	var obj={
 			"request.attachmentId":scope.designReport.attachmentList[index].attachmentId	
 	};
 	console.log(obj);
 	deleteAttachmentByServer(obj,function(data){
 		if(data.result=="0"){
 			var phase = scope.$$phase;
			if(phase =='$apply'||phase =='$digest'){
				scope.designReport.attachmentList.splice(index,1);
			}else{
				scope.$apply(function(){
	 	    		console.log("文件删除");
	 	    		scope.designReport.attachmentList.splice(index,1);
	 	    	});
			}
 			
 		}
 	
 	});
}


//点击上传需要绑定的函数，需要向后台提交的东西
function uploadButtonSubmit(id){
	uploadFile.ajaxFileUpload({
	    url: getBasePath()+'/attachment/upload', //用于文件上传的服务器端请求地址
	    fileElementId: id, //文件上传空间的id属性  <input type="file" id="file" name="file" />
	    //async:true,
	    success: function (data)
	    {
	    	var scope=getAngularScope("projectModel");
	    	data=JSON.parse(data);
	    	scope.fileUpload[scope.gloablParm.state]=scope.designReport.attachmentList;
	    	console.log("**************");
	    	console.log(scope.designReport);
	    	var attachmentList=data.responseInfo.attachmentList;
	    	if(attachmentList[0].originalFilename!=undefined&&attachmentList[0].originalFilename!=null&&attachmentList[0].originalFilename!=""){
	    		scope.fileUpload[scope.gloablParm.state].push(attachmentList[0]);
	    	}
	    	
	    	console.log("文件上传");
	    	console.log(scope.fileUpload[scope.gloablParm.state]);
	    	
	    	var phase = scope.$$phase;
			if(phase =='$apply'||phase =='$digest'){
				scope.designReport.attachmentList=scope.fileUpload[scope.gloablParm.state];
			}else{
				scope.$apply(function(){
					scope.designReport.attachmentList=scope.fileUpload[scope.gloablParm.state];
		    	});
			}
	    	
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
	//提交按钮置灰
	document.getElementById("submitButtonFlag").disabled=true;
	var scope=getAngularScope("projectModel");
	if(scope.fileUpload[scope.gloablParm.state]!=undefined&&scope.fileUpload[scope.gloablParm.state]!=""){
		scope.designReport.attatchmenResult=true;
	}
	//带单位的如果不填写的话则默认为0
	if(scope.designReport.distance==""){
		scope.designReport.distance="0";
	}
	var data1=JSON.stringify(scope.designReport);
  	var obj={	
  			"request.result":scope.designReport.reviewResult,
  			"request.groupId":scope.gloablParm.groupId,
  			"request.taskId":scope.gloablParm.taskId,
  			"request.data1":data1,
  			"request.processInstanceId":scope.gloablParm.procInstId,
			
	};
  	console.log(obj);
  	//提交勘测结果
  	submitTaskApproveByServer(obj,function(data){
	   console.log(data);
		if(data.result == "0"){
			swal({
		    	title: "Success",
		    	timer: 2000,
		    	confirmButtonText: "Make Sure"
		    },function(){
		    	url = "taskRemain.html";
				location.href=url;
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
function taskSurveyApprove()
{
	initFileStateListener(taskSurveyApproveRefreshUi);
}
taskSurveyApprove();











