//这个是延期的主控函数
var ReviewerString={
		"processEndReviewer":"no"
};
var extensionReviewerResult={
		"PASS":"Approved",
		"NOTPASS":"Rejected"
}
App.controller('projectModel', ['$scope',initScope]);
function initScope($scope) {
	$scope.productName=[];
	$scope.financialReviewInformation={};
	$scope.waitReviewInfo={};
	$scope.aproveFlag=true;
	$scope.templatePath="";
	$scope.viewPanel="";//当前要查看的模块
	$scope.gloablParm={};
	$scope.registerJSFlag={};
	//项目基础信息
	$scope.projectInfo={};
	$scope.jzdcPath="";
	//界面初决加载初始化模型
	$scope.initModel=function()
	{
		initMainControll();
		initProjectInfo();
	}
	//统一提交入口
	$scope.stateDisplay=function(callBack)
	{
		subMitFnCall(callBack);
	}
	
	//关闭按扭功能
	$scope.cancelMyapplySeeWin=function()
	{
		window.history.go(-1);
		
	}
}


/**
 *界面控制主流程
 * **/
function initMainControll()
{
	var parm = parseQueryString();
	var state= getProjectState();
	console.log("现在看看state");
	console.log(state);
	var templatePath=getApproveFilePath(state);
	console.log("现在看看templatePath");
	console.log(templatePath);
	initScriptFile(state);//因为现在是测试没有流程信息，等到有流程信息信息的时候要把这个给放开
	 //initViewModel();//因为现在是测试没有流程信息，等到有流程信息信息的时候要把这个给放开
	if(!parm.seeState)
	{
		initTemplatePath(templatePath);
	}
}
//从后台取到流程信息，在前台进行显示
function initViewModel()
{
	var parm = parseQueryString();
	var obj={
			"request.projectId":parm.projectId
	}
	//获取光纤开户的基本信息
	getFiberCommonBizByServer(obj,function(data){
		if(data.result == 0){
			console.log("测试从后台取的光纤的基本的流程信息");
			console.log(data);
			setViewModel(data);
		}
	});
}

/**
 * 获取步聚名称
 * **/
function getProjectState()
{
	var parm = parseQueryString();
	var state=parm.state;
	var scope=getAngularScope("projectModel");
	scope.gloablParm=parm;
	var approveFlag=parm.approveFlag;
	//如果是我的申请或者是已完成的话则审批模块是不显示的
	if(approveFlag==1){
		scope.approveFlag=false;
	}else{
		scope.approveFlag=true;
	}
	if(parm.seeState=="true" || parm.seeState==1)
	{
		scope.aproveFlag=false;
	}else{
		scope.aproveFlag=true;
		viewPanel="aprove";
	}
	return state;
}
/**
 * 获取aprove的页面
 * **/
function getApproveFilePath(state)
{
	
	var miniSateName="STEP_"+state+"_PATH";
	return CONFIG_PATH[miniSateName];
}

/**
 * 初始化模板路径
 * 参数:模板名称
 * 渲染到scope;
 * **/
function initTemplatePath(filePath)
{
	var scope=getAngularScope("projectModel");
	
	scope.templatePath=filePath;
}

/***
 * 获取资源文件名称资源文件
 * 参数:state
 * 返回:文件路径
 * */
function getPageScriptFilePath(state) {
	var FileName="STEP_"+state+"_PATH";
	console.log("getPageScriptFilePath");
	console.log(FileName);
	console.log(SCRIPTFile[FileName]);
	
	return SCRIPTFile[FileName];
}

function getResourceFile(fileName)
{
	var filePath=getPageScriptFilePath(state);
	var basePath=getBasePath();
	filePath=basePath+filePath;
	return filePath;
}
/***
 * 加载资源文件
 * 参数:state
 * 描述:调用loadSCRIPT文件加载资源完成后回调
 * */
function initScriptFile(state)
{
	var filePath=getPageScriptFilePath(state);
	var basePath=getBasePath();
	filePath=basePath+filePath;
	var fileId="script_"+state;
	console.log("加载js的一些资源文件");
	console.log(filePath);
	loadFileScript(fileId,filePath,function(){
	});
}


/**
 * 全局HTML加载注册函数
 * 参数：sate,callBack回调函数;
 * **/
function globalRegisterHtml(state)
{
	console.log("看看会执行callBack");
	var scope=getAngularScope("projectModel");
	var stateName=getStateName(state);
	if(!scope.registerHtmlFlag)
	{
		scope.registerHtmlFlag={};
		console.log("uuuuuu");
	}
	console.log(scope.registerHtmlFlag);
	scope.registerHtmlFlag[stateName]=true;
	if(typeof scope.registerHtmlCall !="undefined" && typeof scope.registerHtmlCall[stateName] !="undefined")
		{
		
		var callBack = scope.registerHtmlCall[stateName];
			if(callBack)
				{
				 console.log("register html:*******");
					callBack();
				}
		}
}
/**
 * 全局HTML加载注册函数
 * 参数：获取state全局变量KEY值
 * 返回:相对应的state key值;
 * **/
function getStateName(state)
{
	return "state_"+state;
}

/**
 * 全局注册函数JS
 * 参数：sate,callBack回调函数;
 * **/
function globalRegisterJs(state,callBack)
{
	var scope=getAngularScope("projectModel");
	var stateName=getStateName(state);
    console.log("js加载完毕");
	if(!scope.registerJSFlag)
	{
		scope.registerJSFlag={};
	}
	
	if(!scope.registerHtmlCall)
	{
		scope.registerHtmlCall={};
		
	}
	
	scope.registerHtmlCall[stateName]=callBack;
	scope.registerJSFlag[stateName]=true;	
	
}


/**
 * 获取项目任务信息
 * modelFlag:bool 控制界面显示,modelData:原始数据
 * **/
function setViewModel(data)
{
	//遍历每个commonBizz，根据commonBizz去显示流程信息
	
	
	
}


/**
 * 将初始项目内容信息模型转换为需要的数组模型对象
 * modelFlag:bool 控制界面显示,modelData:原始数据
 * **/
function initProjectInfo()
{
	    var currentLang = navigator.language; 
	    currentLang=currentLang.split("-")[0];
		var parm = parseQueryString();
		var procInstId=parm.procInstId;
		var groupId=parm.groupId;
		var obj={
				"request.groupId":groupId,
				"request.processInstanceId":procInstId,
				"request.language":currentLang,
			};
		console.log("去后台请求信息");
		console.log(obj);

		//从后台取到数据，显示在前台as
	getBaseInformation(obj,function(data){
			if(data.result == 0){
				var scope=getAngularScope("projectModel");
				//审核信息,表示该谁审批
				console.log("这个是延期的流程");
				console.log(data);
				var waitReviewInfo=JSON.parse(data.responseInfo.waitReviewInfo);
				var phase = scope.$$phase;
				var lists=data.responseInfo.lists;
				if(phase =='$apply'||phase =='$digest'){
					scope.waitReviewInfo.reviewResult=waitReviewInfo.stateName;
					if(waitReviewInfo.assigneeList!=undefined){
						scope.waitReviewInfo.reviewer=waitReviewInfo.assigneeList[0].assigneeName;
					}
					if(scope.waitReviewInfo.reviewer==null||scope.waitReviewInfo.reviewer==""){
						scope.waitReviewInfo.reviewer=ReviewerString.processEndReviewer;
					}
					console.log(data.responseInfo.lists);
				}else{
					scope.$apply(function(){
					scope.waitReviewInfo.reviewResult=waitReviewInfo.stateName;
					if(waitReviewInfo.assigneeList!=undefined){
						scope.waitReviewInfo.reviewer=waitReviewInfo.assigneeList[0].assigneeName;
					}
					if(scope.waitReviewInfo.reviewer==null||scope.waitReviewInfo.reviewer==""){
						scope.waitReviewInfo.reviewer=ReviewerString.processEndReviewer;
					}
					console.log(data.responseInfo.lists);})
				}
				for(var i in lists){
					console.log(lists[i].serviceType);
					var serviceType=lists[i].serviceType;
					console.log("serviceType)(((*&%%%%%%%%%%%%%%");
					console.log(serviceType);
					switch(serviceType){
					case "extension":
						var phase = scope.$$phase;
						if(phase =='$apply'||phase =='$digest'){
							//如果存在修改的话，这把得到的这些信息发给后台
							
							scope.fiberDelayCustomIfo=JSON.parse(lists[i].data1).fiberDelayCustomIfo;
							scope.fiberDelayOrderInfo=JSON.parse(lists[i].data1).fiberDelayOrderInfo;
							scope.productName.push(scope.fiberDelayOrderInfo.productName);
							scope.productNameOptions=scope.productName;
							scope.fiberDelayOrderInfo.productName=scope.productName[0];
							if(scope.fiberDelayCustomIfo.userStatus=="0"){
								scope.fiberDelayCustomIfo.userStatusShow="Normal";
							}else if(scope.fiberDelayCustomIfo.userStatus=="1"){
								scope.fiberDelayCustomIfo.userStatusShow="Manually suspended";
							}else{
								scope.fiberDelayCustomIfo.userStatusShow="Inactivated";
							}
							if(scope.fiberDelayCustomIfo.idType=="0"){
								scope.fiberDelayCustomIfo.idTypeShow="Id card"
							}
							if(scope.fiberDelayCustomIfo.idType=="1"){
								scope.fiberDelayCustomIfo.idTypeShow="passport"
							}
							scope.workOrderInfo={
									workOrderNO:lists[i].dataId,//工单编号
									workOrderType:lists[i].serviceType,//工单类型
									workOrderStatus:JSON.parse(data.responseInfo.waitReviewInfo).stateName,//工单状态
									applicant:lists[i].createPersonName ,    //申请人
									requestDate:lists[i].createTime ,      //申请时间
									applicantContactNo:lists[i].createPersonTelno   ,   //申请人电话                
							}
						}else{
							scope.$apply(function(){
							scope.fiberDelayCustomIfo=JSON.parse(lists[i].data1).fiberDelayCustomIfo;
							scope.fiberDelayOrderInfo=JSON.parse(lists[i].data1).fiberDelayOrderInfo;
							scope.productName.push(scope.fiberDelayOrderInfo.productName);
							scope.productNameOptions=scope.productName;
							scope.fiberDelayOrderInfo.productName=scope.productName[0];
							if(scope.fiberDelayCustomIfo.userStatus=="0"){
								scope.fiberDelayCustomIfo.userStatusShow="Normal";
							}else if(scope.fiberDelayCustomIfo.userStatus=="1"){
								scope.fiberDelayCustomIfo.userStatusShow="Manually suspended";
							}else{
								scope.fiberDelayCustomIfo.userStatusShow="Inactivated";
							}
							if(scope.fiberDelayCustomIfo.idType=="0"){
								scope.fiberDelayCustomIfo.idTypeShow="Id card"
							}
							if(scope.fiberDelayCustomIfo.idType=="1"){
								scope.fiberDelayCustomIfo.idTypeShow="passport"
							}
							scope.workOrderInfo={
									workOrderNO:lists[i].dataId,//工单编号
									workOrderType:lists[i].serviceType,//工单类型
									workOrderStatus:JSON.parse(data.responseInfo.waitReviewInfo).stateName,//工单状态
									applicant:lists[i].createPersonName ,    //申请人
									requestDate:lists[i].createTime ,      //申请时间
									applicantContactNo:lists[i].createPersonTelno   ,   //申请人电话                
							}
							})
						}
						
						break;
					case "extension_financeAdmin_review":
						var phase = scope.$$phase;
						if(phase =='$apply'||phase =='$digest'){
							scope.financialReviewFlag=true;
							scope.financialReviewInformation=JSON.parse(lists[i].data1);
							//对后台返回来的结果进行一个转化处理
							if(scope.financialReviewInformation.reviewResult=="0"){
								scope.financialReviewInformation.reviewResultShow=extensionReviewerResult.PASS;
							}else if(scope.financialReviewInformation.reviewResult=="1"){
								scope.financialReviewInformation.reviewResultShow=extensionReviewerResult.NOTPASS;
							}else{
								scope.financialReviewInformation.reviewResultShow="transferred to Network Administrator";
							}
							
						}else{
							scope.$apply(function(){
								scope.financialReviewFlag=true;
								//对后台返回来的结果进行一个转化处理
								scope.financialReviewInformation=JSON.parse(lists[i].data1);
								if(scope.financialReviewInformation.reviewResult=="0"){
									scope.financialReviewInformation.reviewResultShow=extensionReviewerResult.PASS;
								}else if(scope.financialReviewInformation.reviewResult=="1"){
									scope.financialReviewInformation.reviewResultShow=extensionReviewerResult.NOTPASS;
								}else{
									scope.financialReviewInformation.reviewResultShow="Transferred to Network Administrator";
								}
							})
						}
						break;
					case "networkAdmin_review":
						var phase = scope.$$phase;
						if(phase =='$apply'||phase =='$digest'){
							scope.networkManagementReviewInFlag=true;
							scope.networkManagementReviewInformation=JSON.parse(lists[i].data1);
							//对后台返回来的结果进行一个转化处理
							if(scope.networkManagementReviewInformation.reviewResult=="0"){
								scope.networkManagementReviewInformation.reviewResultShow=extensionReviewerResult.PASS;
							}else{
								scope.networkManagementReviewInformation.reviewResultShow=extensionReviewerResult.NOTPASS;
							}
						}else{
							scope.$apply(function(){
								scope.networkManagementReviewInFlag=true;
								scope.networkManagementReviewInformation=JSON.parse(lists[i].data1);
								//对后台返回来的结果进行一个转化处理
								if(scope.networkManagementReviewInformation.reviewResult=="0"){
									scope.networkManagementReviewInformation.reviewResultShow=extensionReviewerResult.PASS;
								}else{
									scope.networkManagementReviewInformation.reviewResultShow=extensionReviewerResult.NOTPASS;
								}
							})
						}
						break;	
					};
				};
				
				
				
			}
		});
}


 function initFileStateListener(callBack)
{ 
	 
	 console.log("看看调用了没有callBack这个是延期的");
	var scope=getAngularScope("projectModel");
	var state=scope.gloablParm.state;
	var stateName=getStateName(state);
	if(typeof scope.registerHtmlFlag!="undefined" && typeof scope.registerHtmlFlag[stateName] !="undefined")
	{
		console.log("看看调用了没有callBack2");
		console.log("register js:*******");
		callBack(scope);
	}
	else
	{
		globalRegisterJs(scope.gloablParm.state,callBack);
	}
}
function getCommonBiz(){
	var parm = parseQueryString();
	obj={
		"request.groupId":parm.groupId
	}
	console.log(obj);
	getgetCommonBizByServer(obj,function(data){
		console.log(data);
		if(data.result=="0"){
			console.log("进来getgetCommonBizByServer")
			var scope=getAngularScope("projectModel");
			var commonList=data.responseInfo.lists;
			for( var i in commonList){
				commonList[i].reviewResult=JSON.parse(commonList[i].data1).reviewResult;
				if(commonList[i].reviewResult=="0"){
					commonList[i].reviewResult=extensionReviewerResult.PASS;
				}
				if(commonList[i].reviewResult=="1"){
					commonList[i].reviewResult=extensionReviewerResult.NOTPASS;
				}
				if(commonList[i].reviewResult=="3"){
					commonList[i].reviewResult="Transferred to Network Administrator";
				}
				if(commonList[i].reviewResult==undefined&&commonList[i].serviceType=="extension"){
					commonList[i].reviewResult="apply extension";
				}
				
			}
			scope.$apply(function(){
				scope.commonList=commonList;
				console.log(scope.commonList)
			});
			
		}
		
	})
}
