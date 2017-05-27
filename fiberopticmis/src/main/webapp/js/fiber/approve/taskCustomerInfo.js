//customerInfo
//回调函数
function approvalRefreshUi()
{  
	//开始按钮是置灰的
	document.getElementById("submitButtonFlag").disabled=true;
	var scope=getAngularScope("projectModel");
	 scope.otherBandwidthFlag=false;
	 scope.installationAreaOptions=["Managua","Boaco","Carazo","Chinandega","Chontales","Esteli","Granada",
	                                "Jinotega","Leon","Madriz","Masaya","Matagalpa","Nueva Segovia","Rivas"," Rio San Juan"," Region Atlantico Norte","Region Atlantico Sur"];
	//用户信息，customerIfo
	 scope.fileUpload={};
	 if(scope.seller_application!=undefined&&scope.seller_application!=null){
			var phase = scope.$$phase;
			if(phase =='$apply'||phase =='$digest'){
				 scope.customerInfo=scope.seller_application;
			}else{
				 scope.$apply(function(){
					 scope.customerInfo=scope.seller_application;
					 console.log("888888888888888");
					 console.log(scope.customerInfo);
				 });
			}
		
	 }else{
		 
		 var phase = scope.$$phase;
			if(phase =='$apply'||phase =='$digest'){
				scope.customerInfo={
				     	reviewResult:"0", 		 	 	 
				 	 	customerName:"", 	 	 	 	 
				 	 	companyType :"",		 	 	 
				 	 	invoiceAddress:"", 		 	 	 
				 	 	invoicePeriod:"", 		 	 	 
				 	 	contractPeriod :"",	 	 	 
				 	 	contactName :"",		 	 	 
				 	 	contactTelNo :"",		 	 	 
				 	 	fibreOpticsBandwidth :"",		 	 	 
				 	 	selectProduct:"",  	 	 
				 	 	priceCharge:"", 		 	 	 
				 	 	iPAddress:"", 	 	 	 
				 	 	iPAddressFee:0, 	 	 	 
				 	 	customerType:"0", 		 	 	 
				 	 	installationFee:"no", 		 	 	 
				 	 	cooTelTopupPhoneNo:"", 	 	 	 
				 	 	installationArea:"Managua", 		 	 	 
				 	 	attachmentList:[], 
				 	 	RCNNo:"",
				 	 	notes:"", 		 	 	 
				 	 	totalAmount:"", 
				 	 	invoicePeriodOther:"",
				 	 	contractOtherPeriod:"",
				 	 	iPAddressOther:"",
				 	 	installationFeeOther:"",
				 	 	attatchmenResult:"",
			 }
			}else{
				scope.$apply(function(){
					scope.customerInfo={
					     	reviewResult:"0", 		 	 	 
					 	 	customerName:"", 	 	 	 	 
					 	 	companyType :"",		 	 	 
					 	 	invoiceAddress:"", 		 	 	 
					 	 	invoicePeriod:"", 		 	 	 
					 	 	contractPeriod :"",	 	 	 
					 	 	contactName :"",		 	 	 
					 	 	contactTelNo :"",		 	 	 
					 	 	fibreOpticsBandwidth :"",		 	 	 
					 	 	selectProduct:"",  	 	 
					 	 	priceCharge:"", 		 	 	 
					 	 	iPAddress:"", 	 	 	 
					 	 	iPAddressFee:0, 	 	 	 
					 	 	customerType:"0", 		 	 	 
					 	 	installationFee:"no", 		 	 	 
					 	 	cooTelTopupPhoneNo:"", 	 	 	 
					 	 	installationArea:"Managua", 		 	 	 
					 	 	attachmentList:[], 
					 	 	RCNNo:"",
					 	 	notes:"", 		 	 	 
					 	 	totalAmount:"", 
					 	 	invoicePeriodOther:"",
					 	 	contractOtherPeriod:"",
					 	 	iPAddressOther:"",
					 	 	installationFeeOther:"",
					 	 	attatchmenResult:"",
				 }
				})
			}
		 
		 
		
	 }
	

	var state=scope.gloablParm.state;
}

//点击*文件删除
var scope=getAngularScope("projectModel");
scope.deletAttachment=function(index){
	console.log("文件删除22222222");
 	var scope=getAngularScope("projectModel");
 	console.log(scope.customerInfo.attachmentList[index].attachmentId);
 	var obj={
 			"request.attachmentId":scope.customerInfo.attachmentList[index].attachmentId	
 	};
 	deleteAttachmentByServer(obj,function(data){
 		if(data.result=="0"){
 			
 			var phase = scope.$$phase;
			if(phase =='$apply'||phase =='$digest'){
				scope.customerInfo.attachmentList.splice(index,1);
			}else{
				scope.$apply(function(){
	 	    		console.log("文件删除");
	 	    		scope.customerInfo.attachmentList.splice(index,1);
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
	        scope.fileUpload[scope.gloablParm.state]=scope.customerInfo.attachmentList;
	    	console.log("**************");
	    	console.log( scope.fileUpload[scope.gloablParm.state]);
	    	var attachmentList=data.responseInfo.attachmentList;
	    	if(attachmentList[0].originalFilename!=undefined&&attachmentList[0].originalFilename!=null&&attachmentList[0].originalFilename!=""){
	    		scope.fileUpload[scope.gloablParm.state].push(attachmentList[0]);
	    	}
	    	console.log("文件上传");
	    	console.log(scope.fileUpload[scope.gloablParm.state]);
	    	
	    	var phase = scope.$$phase;
			if(phase =='$apply'||phase =='$digest'){
	    		scope.customerInfo.attachmentList=scope.fileUpload[scope.gloablParm.state];
			}else{
				scope.$apply(function(){
		    		scope.customerInfo.attachmentList=scope.fileUpload[scope.gloablParm.state];
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
	if(scope.customerInfo.invoicePeriod=="PeriodOther"){
		scope.customerInfo.invoicePeriod=scope.customerInfo.invoicePeriodOther;
	}
	if(scope.customerInfo.contractPeriod=="DurationOther"){
		scope.customerInfo.contractPeriod=scope.customerInfo.contractOtherPeriod;
	}
	if(scope.customerInfo.iPAddress=="have"){
		scope.customerInfo.iPAddress=scope.customerInfo.iPAddressOther;
	}else{
		scope.customerInfo.iPAddress=0;
		scope.customerInfo.iPAddressOther=0;
	}
	if(scope.customerInfo.installationFee=="have"){
		scope.customerInfo.installationFee=scope.customerInfo.installationFeeOther;
	}else{
		scope.customerInfo.installationFee=0;
	}
	if(scope.customerInfo.installationFee==""||scope.customerInfo.installationFee=="no"){
		scope.customerInfo.installationFee=0;
		scope.customerInfo.installationFeeOther=0;
	};
	if(scope.fileUpload[scope.gloablParm.state]!=undefined&&scope.fileUpload[scope.gloablParm.state]!=""){
		scope.customerInfo.attatchmenResult=true;
	}
	//带单位的如果不填写的话则默认为0
	if(scope.customerInfo.invoicePeriod==""){
		scope.customerInfo.invoicePeriod="0";
	}
	if(scope.customerInfo.contractPeriod==""){
		scope.customerInfo.contractPeriod="0";
	}
	if(scope.customerInfo.fibreOpticsBandwidth==""){
		scope.customerInfo.fibreOpticsBandwidth="0";
	}
	if(scope.customerInfo.priceCharge==""){
		scope.customerInfo.priceCharge="0";
	}
	if(scope.customerInfo.iPAddressFee==""){
		scope.customerInfo.iPAddressFee="0";
	}
	
	
	
	
	
	var data1=JSON.stringify(scope.customerInfo);
  	var obj={	   
  			"request.result":scope.customerInfo.reviewResult,
  			"request.groupId":scope.gloablParm.groupId,
  			"request.taskId":scope.gloablParm.taskId,
  			"request.data1":data1,
  			"request.processInstanceId":scope.gloablParm.procInstId,
	};
  	console.log(obj);
  	submitTaskApproveByServer(obj,function(data){
	    //console.log(JSON.stringify(data));
		if(data.result == 0){
			swal({
		    	title: "Sucess",
		    	timer: 2000,
		    	confirmButtonText: "确定"
		    },function(){
				var parm=parseQueryString();
				var from=parm.from;
				location.replace(from);
		    });
		}else{
			swal({
		    	title: "Submit failed",
		    	timer: 2000,
		    	confirmButtonText: "确定"
		    },function(){
		    	//提交按钮置亮
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
function checkNum(){
	var uidObj={
			"queryInfo.queryType":1,
	        "queryInfo.queryValue":scope.customerInfo.cooTelTopupPhoneNo//document.getElementById("cooTelTopupPhoneNo").value, 
	};
	getUidByServer(uidObj,function(data){
		if(data.queryResult.retCode==0){
			var scope=getAngularScope("projectModel");
			var phase = scope.$$phase;
			if(phase =='$apply'||phase =='$digest'){
				document.getElementById("submitButtonFlag").disabled=false;
			}else{
				scope.$apply(function(){
					document.getElementById("submitButtonFlag").disabled=false;
				})
			}
			console.log("此用户存在");
		}else{
			var scope=getAngularScope("projectModel");
			if(phase =='$apply'||phase =='$digest'){
				document.getElementById("submitButtonFlag").disabled=true;
			}else{
				scope.$apply(function(){
					document.getElementById("submitButtonFlag").disabled=true;
				})
			}
			swal({
		    	title: "CooTel Topup Phone No is not exist,Can't Submit",
		    	text: "",
		    	timer: 2000,
		    	confirmButtonText: "Make Sure"
		    },function(){
		    	//window.location.href = "openAccount.html";
		    });
		}
	})	
	
}










