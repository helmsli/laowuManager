//这个是延期的主控函数
var approveResult={
		"PASS":"Approved",
		"NOTPASS":"Rejected"
}
App.controller('projectModel', ['$scope',initScope]);
function initScope($scope) {
	$scope.commonList={};
	$scope.workOrderInfo={};
	$scope.waitReviewInfo={};
	$scope.designReport={};
	$scope.activationOperatorFlag=false;
	$scope.contractReviewerFlag=false;
	$scope.designerFlag=false;
	$scope.financeAdminFlag=false;
	$scope.infoConfirmerFlag=false;
	$scope.installerFlag=false;
	//$scope.openAccountFlag=false;
	$scope.sellerFlag=false;
	$scope.aproveFlag=false;
	$scope.templatePath="";
	$scope.viewPanel="";//当前要查看的模块
	$scope.gloablParm={};
	$scope.registerJSFlag={};
	//项目基础信息
	$scope.projectInfo={};
	//界面初决加载初始化模型
	$scope.initModel=function()
	{
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
		var parm=parseQueryString();
		var from=parm.from;
		location.replace(from);
		
	}
	/*
	 * 文件下载
	 */
	$scope.fileDownLoad=function(attatchment){
		console.log(attatchment);
		url=getBasePath()+"/attachment/fileDownLoad?attachmentName="+attatchment;
		window.location.href=url;
	};
}


/**
 *界面控制主流程
 * **/
function initMainControll()
{
	var parm = parseQueryString();
	var state= getProjectState();
	console.log("现在看看SEEstate");
	console.log(parm.seeState);
	var templatePath=getApproveFilePath(state);
	console.log("现在看看templatePath");
	console.log(templatePath);
	if(!parm.seeState)
	{
		console.log("进啦");
		initTemplatePath(templatePath);
	}
	initScriptFile(state);//因为现在是测试没有流程信息，等到有流程信息信息的时候要把这个给放开
	
}

/**
 * 获取步聚名称
 * **/
function getProjectState()
{
	var parm = parseQueryString();
	var state=parm.state;
	var status=parm.status;
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
	//加载模板
	var scope=getAngularScope("projectModel");
	var phase = scope.$$phase;
	if(phase =='$apply'||phase =='$digest'){
		scope.templatePath=filePath;
	}else{
		scope.$apply(function(){
			scope.templatePath=filePath;
		})
	}
	//如果审批存在直接定位到审批
	if(scope.templatePath){
		setTimeout(function(){
			var bodyHei=$("body").height();
			var windowHeight=$(window).height();
			console.log(bodyHei+"==="+windowHeight);
			$(document).scrollTop(bodyHei-windowHeight);
		},50);
	}
	
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
		console.log(state);
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
 * 将初始项目内容信息模型转换为需要的数组模型对象
 * modelFlag:bool 控制界面显示,modelData:原始数据
 * **/
function initProjectInfo()
{
	//获取浏览器的语言
    var	currentLang = navigator.language; 
    currentLang=currentLang.split("-")[0];
	var parm = parseQueryString();
	
	console.log(parm);
	
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
				var waitReviewInfo=JSON.parse(data.responseInfo.waitReviewInfo);
				scope.waitReviewInfo.reviewResult=waitReviewInfo.stateName;
				if(waitReviewInfo.assigneeList!=undefined){
					scope.waitReviewInfo.reviewer=waitReviewInfo.assigneeList[0].assigneeName;
				}else{
					scope.waitReviewInfo.reviewer="finish";
				}
				console.log("去后台请求信息返回的信息2222222");
				console.log(data);
				var lists=data.responseInfo.lists;
				console.log(data.responseInfo.lists);
				for(var i in lists){
					console.log(lists[i].serviceType);
					var serviceType=lists[i].serviceType;
					switch(serviceType){
					case "openAccount":
						scope.openAccountFlag=true;
						scope.applicantInfo=JSON.parse(lists[i].data1).applicantInfo;
						console.log("open");
						console.log(scope.applicantInfo);
						var basicInformation=JSON.parse(lists[i].data1).basicInformation;
						scope.workOrderInfo.workOrderNO=lists[i].groupId;
						scope.workOrderInfo.workOrderType=lists[i].serviceType;
						scope.workOrderInfo.representative=basicInformation.representative;
						scope.workOrderInfo.applicant=basicInformation.applicant;
						scope.workOrderInfo.applicantDepartment=basicInformation.applicantDepartment;
						scope.workOrderInfo.requestDate=lists[i].createTime;
						//处理等design驳回的时候信息带不回。
						break;
					case "designer_design":
						scope.designerFlag=true;
						//勘测报告信息
						var phase = scope.$$phase;
						if(phase =='$apply'||phase =='$digest'){
							scope.designReport=JSON.parse(lists[i].data1);
							scope.designReport.reviewResult=lists[i].result;
							//这个专门用来显示的
							if(scope.designReport.reviewResult=="0"){
								scope.designReport.reviewResultShow=approveResult.PASS;
							}else{
								scope.designReport.reviewResultShow=approveResult.NOTPASS;
							}
							scope.designer_design=scope.designReport;
							console.log("这个是测试的designReport");
							console.log(scope.designReport);
						}else{
							 scope.$apply(function(){
									scope.designReport=JSON.parse(lists[i].data1);
									scope.designReport.reviewResult=lists[i].result;
									//scope.attachmentList=scope.designReport.attatchmentList;
									//这个专门用来显示的
									if(scope.designReport.reviewResult=="0"){
										scope.designReport.reviewResultShow=approveResult.PASS;
									}else{
										scope.designReport.reviewResultShow=approveResult.NOTPASS;
									}
									scope.designer_design=scope.designReport;
									console.log("这个是测试的designReport");
									console.log(scope.designReport);
									//scope.attatchmentList=scope.designReport.attatchmentList;
								});
						}
						
						break;
					case "seller_application":
						scope.sellerFlag=true;
						if(parm.state=="PendingContractReview"){//代表这个只是在合同审批的时候取请求
							//去取客户的Uid并且查余额
							var uidObj={
									"queryInfo.queryType":1,
							        "queryInfo.queryValue":JSON.parse(lists[i].data1).cooTelTopupPhoneNo
							};
							console.log("去后台查询信息UId");
							console.log(uidObj);
							getUidByServer(uidObj,function(data){
								var scope=getAngularScope("projectModel");
								console.log(data);
								if(data.queryResult.retCode==0){
									var cooTelTopupPhoneNo={
											"subsId":data.queryResult.returnObject,
									};
									console.log("{{{{}}}}");
									console.log(cooTelTopupPhoneNo);
									queryBalanceByServer(cooTelTopupPhoneNo,function(data){
										console.log(data);
										if(data.retCode==0){
											var phase = scope.$$phase;
											if(phase =='$apply'||phase =='$digest'){
												scope.taskContractReview.userBalance=data.balance;
											}else{
												scope.$apply(function(){
													scope.taskContractReview.userBalance=data.balance;
												});	
											}
											
										}else{
											console.log("没有余额的相关信息");
										}
									
									});
								}else{
									console.log("此用户不存在");
								}
							})	
						}
						var phase = scope.$$phase;
						 console.log("c{}{}{}}ustomerInfo");
						if(phase =='$apply'||phase =='$digest'){
							var customerInfo=JSON.parse(lists[i].data1);
							 //scope.attatchmentList=customerInfo.attatchmentList;
							 scope.customerInfo=customerInfo;
							 console.log("c{}{}{}}ustomerInfo");
							 scope.cooTelTopupNoConformation=customerInfo.cooTelTopupPhoneNo;
							 scope.customerInfo.reviewResult=lists[i].result;
							 if(scope.customerInfo.reviewResult=="0"){
								 scope.customerInfo.reviewResultShow=approveResult.PASS;
								}else{
									scope.customerInfo.reviewResultShow=approveResult.NOTPASS;
								}
							 scope.seller_application=scope.customerInfo;
							 console.log("6666666666666");
							 console.log(scope.seller_application);
							 if(scope.customerInfo.iPAddress==0&&scope.customerInfo.iPAddressOther==0){
								 scope.customerInfo.iPAddressResult="no";
							 }else{
								 scope.customerInfo.iPAddressResult=scope.customerInfo.iPAddress;
							 };
							 //这个是处理IPaddress的
							 if(scope.customerInfo.iPAddress!=0&&scope.customerInfo.iPAddressOther!=0){
								 scope.customerInfo.iPAddress="have";
								 scope.otheriPAddressFlag=true;
							 }else{
								 scope.customerInfo.iPAddress="1";
							 }
							 //这个是处理installationFee的
							 if(scope.customerInfo.installationFee!=0){
								 scope.customerInfo.installationFee="have";
								 scope.otherInstallFlag=true;
								 
							 }else{
								 scope.customerInfo.installationFee="no"; 
							 }
							 
							 if(scope.customerInfo.installationFee==0&&scope.customerInfo.installationFeeOther==0){
								 scope.customerInfo.installationFeeShow="no";
							 }else{
								 scope.customerInfo.installationFeeShow=scope.customerInfo.installationFeeOther;
							 };
							 //这个是处理custome info的显示的
							 if(scope.customerInfo.contractOtherPeriod!=""){
								 scope.customerInfo.contractPeriodShow=scope.customerInfo.contractOtherPeriod;
							 }else{
								 scope.customerInfo.contractPeriodShow=scope.customerInfo.contractPeriod;
							 }
							 //这个是处理custome info的
							 if(scope.customerInfo.invoicePeriodOther!=""){
								 scope.customerInfo.invoicePeriodShow=scope.customerInfo.invoicePeriodOther;
							 }else{
								 console.log("***((()(***(()");
								 console.log(scope.customerInfo.invoicePeriod);
								 scope.customerInfo.invoicePeriodShow=scope.customerInfo.invoicePeriod;
								 
							 }
							 //这个是处理invoicePeriod的
							 if(scope.customerInfo.invoicePeriodOther!=""){
								 scope.customerInfo.invoicePeriod="PeriodOther";
								 scope.PeriodOtherFlag=true;
							 }
							 //这个是处理contractPeriod的
							 if(scope.customerInfo.contractOtherPeriod!=""){
								 scope.customerInfo.contractPeriod="DurationOther";
								 scope.otherDurationFlag=true;
							 }
							 if(scope.customerInfo.companyType=="1"){
								 scope.customerInfo.companyTypeShow="IVA Registered Company";
							 }
							 if(scope.customerInfo.companyType=="2"){
								 scope.customerInfo.companyTypeShow="Non-IVA Registered Company & Individual";
							 }
							 console.log("7777777777777");
							 console.log(scope.seller_application);
							}else{
							
								scope.$apply(function(){
									var customerInfo=JSON.parse(lists[i].data1);
									 //scope.attatchmentList=customerInfo.attatchmentList;
									 scope.customerInfo=customerInfo;
									 console.log("c{}{}{}}ustomerInfo");
									 scope.cooTelTopupNoConformation=customerInfo.cooTelTopupPhoneNo;
									 scope.customerInfo.reviewResult=lists[i].result;
									 if(scope.customerInfo.reviewResult=="0"){
										 scope.customerInfo.reviewResultShow=approveResult.PASS;
										}else{
											scope.customerInfo.reviewResultShow=approveResult.NOTPASS;
										}
									 scope.seller_application=scope.customerInfo;
									 console.log("6666666666666");
									 console.log(scope.seller_application);
									 //0代表home 1代表Corporation
									 if(scope.customerInfo.customerType=="0"){
										 scope.customerInfo.customerTypeResult="Home";
									 }
									 if(scope.customerInfo.customerType=="1"){
										 scope.customerInfo.customerTypeResult="Corporation";
									 }
									 if(scope.customerInfo.iPAddress==0&&scope.customerInfo.iPAddressOther==0){
										 scope.customerInfo.iPAddressResult="no";
									 }else{
										 scope.customerInfo.iPAddressResult=scope.customerInfo.iPAddress;
									 };
									 //这个是处理IPaddress的
									 if(scope.customerInfo.iPAddress!=0&&scope.customerInfo.iPAddressOther!=0){
										 scope.customerInfo.iPAddress="have";
										 scope.otheriPAddressFlag=true;
									 }else{
										 scope.customerInfo.iPAddress="1";
									 }
									 //这个是处理installationFee的
									 if(scope.customerInfo.installationFee!=0){
										 scope.customerInfo.installationFee="have";
										 scope.otherInstallFlag=true;
										 
									 }else{
										 scope.customerInfo.installationFee="no"; 
									 }
									 
									 if(scope.customerInfo.installationFee==0&&scope.customerInfo.installationFeeOther==0){
										 scope.customerInfo.installationFeeShow="no";
									 }else{
										 scope.customerInfo.installationFeeShow=scope.customerInfo.installationFeeOther;
									 };
									 //这个是处理custome info的显示的
									 if(scope.customerInfo.contractOtherPeriod!=""){
										 scope.customerInfo.contractPeriodShow=scope.customerInfo.contractOtherPeriod;
									 }else{
										 scope.customerInfo.contractPeriodShow=scope.customerInfo.contractPeriod;
									 }
									 //这个是处理custome info的
									 if(scope.customerInfo.invoicePeriodOther!=""){
										 scope.customerInfo.invoicePeriodShow=scope.customerInfo.invoicePeriodOther;
									 }else{
										 console.log("***((()(***(()");
										 console.log(scope.customerInfo.invoicePeriod);
										 scope.customerInfo.invoicePeriodShow=scope.customerInfo.invoicePeriod;
										 
									 }
									 //这个是处理invoicePeriod的
									 if(scope.customerInfo.invoicePeriodOther!=""){
										 scope.customerInfo.invoicePeriod="PeriodOther";
										 scope.PeriodOtherFlag=true;
									 }
									 //这个是处理contractPeriod的
									 if(scope.customerInfo.contractOtherPeriod!=""){
										 scope.customerInfo.contractPeriod="DurationOther";
										 scope.otherDurationFlag=true;
									 }
									 if(scope.customerInfo.companyType=="1"){
										 scope.customerInfo.companyTypeShow="IVA Registered Company";
									 }
									 if(scope.customerInfo.companyType=="2"){
										 scope.customerInfo.companyTypeShow="Non-IVA Registered Company & Individual";
									 }
									 console.log("7777777777777");
									 console.log(scope.seller_application);
									});
					    	
						}
						break;
					case "contractReviewer_review":
						scope.contractReviewerFlag=true;
						var phase = scope.$$phase;
						if(phase =='$apply'||phase =='$digest'){
		                     scope.contractReview=JSON.parse(lists[i].data1);
		                     scope.contractReview.userBalanceShow=scope.contractReview.userBalance;
		                     scope.contractReview.reviewResult=lists[i].result;
		                     if(scope.contractReview.reviewResult=="0"){
		                    	 scope.contractReview.reviewResultShow=approveResult.PASS;
								}else{
									scope.contractReview.reviewResultShow=approveResult.NOTPASS;
								}
		                     scope.contractReviewer_review=scope.contractReview;
						
						}else{
							  scope.$apply(function(){
				                     scope.contractReview=JSON.parse(lists[i].data1);
				                     scope.contractReview.userBalanceShow=scope.contractReview.userBalance;
				                     scope.contractReview.reviewResult=lists[i].result;
				                     if(scope.contractReview.reviewResult=="0"){
				                    	 scope.contractReview.reviewResultShow=approveResult.PASS;
										}else{
											scope.contractReview.reviewResultShow=approveResult.NOTPASS;
										}
				                     scope.contractReviewer_review=scope.contractReview;
								});
						}
	                  
						break;
					case "installer_installation":
						scope.installerFlag=true;
						var phase = scope.$$phase;
						if(phase =='$apply'||phase =='$digest'){
					    	scope.installationInfo=JSON.parse(lists[i].data1);
					    	scope.installationInfo.reviewResult=lists[i].result;
					    	  if(scope.installationInfo.reviewResult=="0"){
					    		  scope.installationInfo.reviewResultShow=approveResult.PASS;
									}else{
										scope.installationInfo.reviewResultShow=approveResult.NOTPASS;
									}
					    	scope.installer_installation=scope.installationInfo;
					    	console.log("结果");
							
						}else{
							 scope.$apply(function(){
							    	scope.installationInfo=JSON.parse(lists[i].data1);
							    	scope.installationInfo.reviewResult=lists[i].result;
							    	  if(scope.installationInfo.reviewResult=="0"){
							    		  scope.installationInfo.reviewResultShow=approveResult.PASS;
											}else{
												scope.installationInfo.reviewResultShow=approveResult.NOTPASS;
											}
							    	scope.installer_installation=scope.installationInfo;
							    	console.log("结果");
									});
						}
					   
						break;
					case "activationOperator_activate":
						scope.activationOperatorFlag=true;
						var phase = scope.$$phase;
						if(phase =='$apply'||phase =='$digest'){

							scope.activationInfomation=JSON.parse(lists[i].data1);
							scope.activationInfomation.reviewResult=lists[i].result;
							 if(scope.activationInfomation.reviewResult=="0"){
								 scope.activationInfomation.reviewResultShow=approveResult.PASS;
									}else{
										scope.activationInfomation.reviewResultShow=approveResult.NOTPASS;
									}
							scope.activationOperator_activate=scope.activationInfomation;
							 if(scope.activationInfomation.customerType=="0"){
								 scope.activationInfomation.customerTypeShow="Home";
							 }
							 if(scope.activationInfomation.customerType=="1"){
								 scope.activationInfomation.customerTypeShow="Corporation";
							 }
							
						
						}else{
							scope.$apply(function(){
								scope.activationInfomation=JSON.parse(lists[i].data1);
								scope.activationInfomation.reviewResult=lists[i].result;
								 if(scope.activationInfomation.reviewResult=="0"){
									 scope.activationInfomation.reviewResultShow=approveResult.PASS;
										}else{
											scope.activationInfomation.reviewResultShow=approveResult.NOTPASS;
										}
								scope.activationOperator_activate=scope.activationInfomation;
								 if(scope.activationInfomation.customerType=="0"){
									 scope.activationInfomation.customerTypeShow="Home";
								 }
								 if(scope.activationInfomation.customerType=="1"){
									 scope.activationInfomation.customerTypeShow="Corporation";
								 }
								
							});
						}
						
						break;
					case "infoConfirmer_confirm":
						scope.infoConfirmerFlag=true;
						var phase = scope.$$phase;
						if(phase =='$apply'||phase =='$digest'){
							 scope.informationConformation=JSON.parse(lists[i].data1);
							 scope.informationConformation.cooTelTopupNoConformationShow=scope.informationConformation.cooTelTopupNoConformation;
							 scope.informationConformation.reviewResult=lists[i].result;
							 if(scope.informationConformation.reviewResult=="0"){
								 scope.informationConformation.reviewResultShow=approveResult.PASS;
									}else{
										scope.informationConformation.reviewResultShow=approveResult.NOTPASS;
									}
							 scope.infoConfirmer_confirm=scope.informationConformation;
							
						}else{
							scope.$apply(function(){
								 scope.informationConformation=JSON.parse(lists[i].data1);
								 scope.informationConformation.cooTelTopupNoConformationShow=scope.informationConformation.cooTelTopupNoConformation;
								 scope.informationConformation.reviewResult=lists[i].result;
								 if(scope.informationConformation.reviewResult=="0"){
									 scope.informationConformation.reviewResultShow=approveResult.PASS;
										}else{
											scope.informationConformation.reviewResultShow=approveResult.NOTPASS;
										}
								 scope.infoConfirmer_confirm=scope.informationConformation;
								});
						}
					
						break;
					case "financeAdmin_review":
						scope.financeAdminFlag=true;
						var phase = scope.$$phase;
						if(phase =='$apply'||phase =='$digest'){

							 scope.financeInfo=JSON.parse(lists[i].data1);
							 scope.financeInfo.reviewResult=lists[i].result;
							 //是否收到存款
							 if(scope.financeInfo.received==0){
								 scope.financeInfo.receivedShow="yes" ;
							 }
							 if(scope.financeInfo.received==1){
								 scope.financeInfo.receivedShow="no" ;
							 }
							//收款方式
							 scope.financeInfo.receivedMethodShow=scope.financeInfo.receivedMethodCash+" ;"+scope.financeInfo.receivedMethodBasic;
							 if(scope.financeInfo.reviewResult=="0"){
								 scope.financeInfo.reviewResultShow=approveResult.PASS;
									}else{
										scope.financeInfo.reviewResultShow=approveResult.NOTPASS;
									}
							 scope.financeAdmin_review=scope.financeInfo;
							
						}else{
							scope.$apply(function(){
								 scope.financeInfo=JSON.parse(lists[i].data1);
								 scope.financeInfo.reviewResult=lists[i].result;
								 //是否收到存款
								 if(scope.financeInfo.received==0){
									 scope.financeInfo.receivedShow="yes" ;
								 }
								 if(scope.financeInfo.received==1){
									 scope.financeInfo.receivedShow="no" ;
								 }
								//收款方式
								 scope.financeInfo.receivedMethodShow=scope.financeInfo.receivedMethodCash+" ;"+scope.financeInfo.receivedMethodBasic;
								 if(scope.financeInfo.reviewResult=="0"){
									 scope.financeInfo.reviewResultShow=approveResult.PASS;
										}else{
											scope.financeInfo.reviewResultShow=approveResult.NOTPASS;
										}
								 scope.financeAdmin_review=scope.financeInfo;
								});
						}
						
						break;
					};
					//进行显示的特殊处理
					if(lists[i].result=="0"){
						lists[i].result=approveResult.PASS;
					}
					if(lists[i].result=="1"){
						lists[i].result=approveResult.NOTPASS;
					}
				};
				initMainControll();
			}
		});
}


 function initFileStateListener(callBack)
{
	
	var scope=getAngularScope("projectModel");
	var state=scope.gloablParm.state;
	var stateName=getStateName(state);
	 console.log(scope.registerHtmlFlag);
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
//显示光纤的基本信息
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
					commonList[i].reviewResult=approveResult.PASS;
				}
				if(commonList[i].reviewResult=="1"){
					commonList[i].reviewResult=approveResult.NOTPASS;
				}
				if(commonList[i].serviceType=="openAccount"){
					commonList[i].reviewResult="Open";
				}
				if(commonList[i].serviceType=="extension"){
					commonList[i].reviewResult="extension";
				}
				if(commonList[i].serviceType=="financeAdmin_review"){
					commonList[i].reviewResult="finish";
				}
				
			}
			var phase = scope.$$phase;
			if(phase =='$apply'||phase =='$digest'){
				scope.commonList=commonList;
			}else{
				scope.$apply(function(){
					scope.commonList=commonList;
					console.log(scope.commonList)
				});
			}
			
			
		}
		
	})
}


