//信息确认
//回调函数
function approvalRefreshUi()
{  
	
	var endTime= new Date();
	if(endTime.getMonth()+1<10){
		endMonth=endTime.getMonth()+1;
		endMonth="0"+endMonth;
	}else{
		endMonth=endTime.getMonth()+1;
	}
	if(endTime.getDate()<10){
		endDate="0"+endTime.getDate()
	}else{
		endDate=endTime.getDate();
	}
	endTime=endMonth+"/"+endDate+"/"+endTime.getFullYear();
	console.log("这个啥时候调用");
	var scope=getAngularScope("projectModel");
	scope.otherDurationFlag=false;
	//确认信息需要填的内容
	if(scope.infoConfirmer_confirm!=undefined&&scope.infoConfirmer_confirm!=null){
		var phase = scope.$$phase;
		if(phase =='$apply'||phase =='$digest'){
			scope.taskInfoConform=scope.infoConfirmer_confirm;
			scope.taskInfoConform.cooTelTopupNoConformation=scope.customerInfo.cooTelTopupPhoneNo;
		}else{
			scope.$apply(function(){
				scope.taskInfoConform=scope.infoConfirmer_confirm;
				scope.taskInfoConform.cooTelTopupNoConformation=scope.customerInfo.cooTelTopupPhoneNo;
			})	
		}
	
	}else{
		
		var phase = scope.$$phase;
		if(phase =='$apply'||phase =='$digest'){
			scope.cooTelTopupNoConformation=scope.customerInfo.cooTelTopupPhoneNo;
			scope.taskInfoConform={
					reviewResult:"0",
					cooTelTopupNoConformation:scope.cooTelTopupNoConformation, 	
					contractPeriodValue:"",
			 	 	selectProduct:"",
			 	 	selectProductId:"",
			 	 	activationDate:endTime,  	 	 	 	 
			 	 	contractPeriod:"12", 		 	 	 
			 	 	paymentPeriod :"1", 	
			 	 	paymentPeriodValue:"",
			 	 	notes:"", 
			 	 	subscriberId:"",
			 }
		}else{
			scope.$apply(function(){
				scope.cooTelTopupNoConformation=scope.customerInfo.cooTelTopupPhoneNo;
				scope.taskInfoConform={
						reviewResult:"0",
						cooTelTopupNoConformation:scope.cooTelTopupNoConformation, 	
						contractPeriodValue:"",
				 	 	selectProduct:"",
				 	 	selectProductId:"",
				 	 	activationDate:endTime,  	 	 	 	 
				 	 	contractPeriod:"12", 		 	 	 
				 	 	paymentPeriod :"1", 	
				 	 	paymentPeriodValue:"",
				 	 	notes:"", 
				 	 	subscriberId:"",
				 }
			})
		}
		
	
	}
	console.log(scope.taskInfoConform);
	var state=scope.gloablParm.state;
}
//提交函数，需要提交给后台的
function submitApproval()
{
	//提交按钮置灰
	document.getElementById("submitButtonFlag").disabled=true;
	var scope=getAngularScope("projectModel");
	if(scope.taskInfoConform.contractPeriod==""||scope.taskInfoConform.contractPeriod==null){
		scope.taskInfoConform.contractPeriod=0;
		
	}
	if(scope.taskInfoConform.paymentPeriod==""||scope.taskInfoConform.paymentPeriod==null){
		scope.taskInfoConform.paymentPeriod=0;
	}
	
	
	//这个是为了发送数据的时候和后台数据做个对应
	if(scope.taskInfoConform.contractPeriod=="otherDuration"){
		scope.taskInfoConform.contractPeriod=scope.taskInfoConform.contractPeriodValue;
		scope.taskInfoConform.contractPeriodSave="otherDuration";
	};
	if(scope.taskInfoConform.paymentPeriod=="otherPeriod"){
		scope.taskInfoConform.paymentPeriod=scope.taskInfoConform.paymentPeriodValue;
		scope.taskInfoConform.paymentPeriodSave="otherPeriod";
	};
	
	
	
	scope.taskInfoConform.activationDate=$("#activationDate").val();
	console.log(scope.taskInfoConform);
	scope.taskInfoConform.activationDate=$("#activationDate").val();
	var taskInfoConform=scope.taskInfoConform;
	var data1=JSON.stringify(taskInfoConform);
  	var obj={	 
  			"request.result":taskInfoConform.reviewResult,
  			"request.groupId":scope.gloablParm.groupId,
  			"request.taskId":scope.gloablParm.taskId,
  			"request.data1":data1,
  			"request.processInstanceId":scope.gloablParm.procInstId,
	};
  	console.log(JSON.stringify(obj));
  	if(parseInt(taskInfoConform.contractPeriod)<parseInt(taskInfoConform.paymentPeriod)){
  		swal({
	    	title: "Payment Period must less than Invoice Period",
	    	timer: 2000,
	    	confirmButtonText: "Make Sure"
	    },function(){
	    	document.getElementById("submitButtonFlag").disabled=false;
	    	if(scope.taskInfoConform.paymentPeriodSave=="otherPeriod"&&scope.otherPeriodFlag==true){
	    		scope.taskInfoConform.paymentPeriod="otherPeriod";
	    		scope.otherPeriodFlag=true;
	    	}
	    	if(scope.taskInfoConform.contractPeriodSave=="otherDuration"&&scope.otherDurationFlag==true){
	    		scope.taskInfoConform.contractPeriod="otherDuration";
	    		scope.otherDurationFlag=true;
	    	}
	    });
  		return;
  	}
	var selectProductIdischoose =(scope.taskInfoConform.selectProductId==""||scope.taskInfoConform.subscriberId==""||scope.taskInfoConform.selectProductId==null);
	//如果是拒绝的话可以不用选择产品也可以提交
    if(selectProductIdischoose&&scope.taskInfoConform.reviewResult=="0"){
    		swal({
    	    	title: "please choose product",
    	    	timer: 2000,
    	    },function(){
    	    	document.getElementById("submitButtonFlag").disabled=false;
    	    	if(scope.taskInfoConform.paymentPeriodSave=="otherPeriod"&&scope.otherPeriodFlag==true){
    	    		scope.taskInfoConform.paymentPeriod="otherPeriod";
    	    		scope.otherPeriodFlag=true;
    	    	}
    	    	if(scope.taskInfoConform.contractPeriodSave=="otherDuration"&&scope.otherDurationFlag==true){
    	    		scope.taskInfoConform.contractPeriod="otherDuration";
    	    		scope.otherDurationFlag=true;
    	    	}
    	    });
        	return false;
	}else{
		submit(obj);
	}

	
	
}

function submit(obj){
	//提交按钮置灰
	document.getElementById("submitButtonFlag").disabled=true;
  	submitTaskApproveByServer(obj,function(data){
	    console.log(JSON.stringify(data));
		if(data.result == "0"){
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
/**
 * 点击+号去后台请求产品的列表
 * @param id
 */
function initProduct(){
	var scope=getAngularScope("projectModel");
	scope.project={};
	scope.basicInformation={};
	console.log(2222222222222);
	console.log(scope);
	initSetPersonData();
	
}
function initSetPersonData(pageNum){
	getUidFun();
}

/*
 *用于去后台查询UID
 */
function getUidFun(pageNum){
	var pageNum=pageNum||1;
	var scope=getAngularScope("projectModel");
	var uidObj={
			"queryInfo.queryType":1,
			"queryInfo.queryValue":scope.taskInfoConform.cooTelTopupNoConformation,
	};
	console.log(uidObj);
	getUidByServer(uidObj,function(data){
		console.log(data);
		if(data.queryResult.retCode==0){
			var childrenScope=getAngularScope("myDataController");
			var scope=getAngularScope("projectModel");
			scope.taskInfoConform.subscriberId=data.queryResult.returnObject;
			var ProductListObj={
					"subsId":data.queryResult.returnObject,
					"productName":childrenScope.queryKey,
					"pageObject":{
					"pageSize ":10,
					"currentPageNum":pageNum
						
					}
			};
			console.log(ProductListObj);
			getProductListFun(ProductListObj);
			console.log(data);
		}else{
			console.log("不成功");
		}
	})
	console.log();
}
/*
 * 用于去后台查询产品列表
 */function getProductListFun(obj){
	var childrenScope=getAngularScope("myDataController");
	 getproductListByServer(obj,function(data){
		 console.log(data);
		if(data.retCode==0){
			var phaseChildren = childrenScope.$$phase;
			if(phaseChildren =='$apply'||phaseChildren =='$digest'){
				 childrenScope.dataList=data.productList;
				 
			}else{
				childrenScope.$apply(function(){
					 childrenScope.dataList=data.productList;
				 });
			}
			var pageInfo=data.pageObject;
			if(childrenScope.pageFlag)
			{
				console.log("看看我的分页33");
				childrenScope.pageFlag=false;
				pageNav.go(pageInfo.currentPageNum, pageInfo.totalPages);
			}
		 }else{
			 console.log("返回列表不成功");
		 }
	 })
 }
/**
 * 统一列表分页渲染初始化,首先调用这个
 * */
pageNav.fn = function(pageNum){
	console.log("看看是不是初始化了");
	initSetPersonData(pageNum);
 };		









