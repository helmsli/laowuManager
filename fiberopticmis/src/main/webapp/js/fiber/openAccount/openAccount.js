App.controller('projectManagerModel', function($scope) {
	var userInfo=localStorage.getItem("userInfo");
	console.log(userInfo);
	var userInfo=JSON.parse(userInfo);
	var uid=userInfo.uid;
	var applicant=userInfo.fullName;
	var applicantDepartment=userInfo.companyName;
	var applicantContactNo=userInfo.phoneNo;
	$scope.formNumber="";
	var getGepresentativeObj={};
	$scope.basicInformation={};
	var requestDate=new Date();
	requestDate=requestDate.getMonth()+1+"/"+requestDate.getDate()+"/"+requestDate.getFullYear(); 
	//申请人信息
	$scope.basicInformation={
			    formNumber:$scope.formNumber,//表单编号
			    representative:applicant,//销售代表默认显示的是登录人
			    representativeId:uid,
				requestDate:requestDate,	      //申请时间
				applicant:applicant,       //申请人
				applicantDepartment:applicantDepartment,   //申请人部门
				applicantContactNo:applicantContactNo           //申请人联系电话
				
		};
	var getRoleIdObj={
		"request.roleName":"seller",
	};

	/*
	 * 去后台查询角色ID
	 */
	getRoleId(getRoleIdObj,function(data){
		if(data.result==0){
			 roleId=data.responseInfo.roleId;
			console.log("formNumber成功");
			console.log(roleId);
		}else{
			console.log("没有formNumber");
		}
	})
	$scope.project={
			assignPersonText:"",
	};
	//用来存放三级部门的id的数组
	$scope.threeLeaderIdLsit=[];
	//点击查询的时候从后台去取三级项目经理的列表,如果要是三级部门经理的话，去获取详细的信息
	$scope.initThreeLeader=function(){
		$scope.initSetPersonData($scope.id);
		
	}
	$scope.customerTypeOptions=["Massive Customer","Corporate Customer(Normal)"," Corporate Customer(project)","Small Business"];
	$scope.installationAreaOptions=["Managua","Boaco","Carazo","Chinandega","Chontales","Esteli","Granada",
  "Jinotega","Leon","Madriz","Masaya","Matagalpa","Nueva Segovia","Rivas"," Rio San Juan"," Region Atlantico Norte","Region Atlantico Sur"];
	$scope.fibreTypeOptions=["Fiber User","Network Bridge User"];
	$scope.applicantInfo={};
	//申请开户的用户信息
	$scope.applicantInfo={
			IDNO:"",
		    customerName:"",      //客户姓名
			contactName:"",          //联系人
			contactNumber:"",     //联系电话
			expectedBandwidth:"", //申请带宽
			expectedBandwidthByself:"",//自定义申请宽带
			installationAddress:"",	      //安装地址
			customerType:"Massive Customer",                            //用户的类型
			serviceLength:"",        //服务时长
			installationArea :"Managua",
		                             //安装区域
			fibreType:"Fiber User",                          //光纤类型
			notes :"",                 //备注    
	};
	/*
	 * 点击加号去后台查询销售代表
	 */		

	$scope.initSetPersonData=function(pageNum){
		var childrenScope=getAngularScope("myDataController");
		var pageNum=pageNum||1;
		getGepresentativeObj={
				"page.pageNum":pageNum,
				"page.pageSize":10,
				"request.role_id":roleId,	
				"request.keyWords":childrenScope.queryKey 
		};
		console.log(getGepresentativeObj);
		//去查询列表
		getGepresentative(getGepresentativeObj,function(data){
		 	if(data.result == "0"){
		 		console.log(data);
		 		$scope.ThreeLeaderLsit=data.responseInfo.lists;
				$scope.$apply($scope.ThreeLeaderLsit);
				var childrenScope=getAngularScope("myDataController");
				//查询出来的专家列表
				childrenScope.dataList=$scope.ThreeLeaderLsit;
				childrenScope.$apply(childrenScope.dataList);
				var pageInfo=data.responseInfo.page;
				console.log(pageInfo);
				 if(childrenScope.pageFlag)
				{
					console.log("看看我的分页33");
					childrenScope.pageFlag=false;
					pageNav.go(pageInfo.pageNum, pageInfo.pages);
				}
				
			}
		 });
		childrenScope.dataList=$scope.ThreeLeaderLsit;
		//childrenScope.$apply(childrenScope.ThreeLeaderLsit);
		
		
	}
	var parm = parseQueryString();
	console.log(parm.state);
//如果是驳回的话去后台请求填过的信息
	if(parm.taskKey){
	    var	currentLang = navigator.language; 
	    currentLang=currentLang.split("-")[0];
		var procInstId=parm.procInstId;
		var groupId=parm.groupId;
		var obj={
				"request.groupId":groupId,
				"request.processInstanceId":procInstId,
				"request.language":currentLang,
			};
		
		console.log(obj);
			//从后台取到数据，显示在前台as
			getBaseInformation(obj,function(data){
				if(data.result == 0){
					var scope=getAngularScope("projectManagerModel");
					console.log("去后台请求信息返回的信息2222222");
					console.log(data);
					var lists=data.responseInfo.lists;
					console.log(data.responseInfo.lists);
					for(var i in lists){
						console.log(lists[i].serviceType);
						var serviceType=lists[i].serviceType;
						switch(serviceType){
						case "openAccount":
							scope.customerInfo=JSON.parse(lists[i].data1).customerInfo;
							scope.basicInformation=JSON.parse(lists[i].data1).basicInformation;
							break;
						};
					};
				}
			});

	}else{
		/*
		 * 如果不是驳回的话去后台请求表单号，如果是驳回的话就不去请求表单号
		 */
		var getFormNumberObj={
				//"request.ServiceType":"fiberOpenAccount"
		};
		getFormNumber(getFormNumberObj,function(data){
			if(data.result==0){
				console.log(data);
				$scope.formNumber=data.responseInfo.groupId;
				$scope.basicInformation.formNumber=$scope.formNumber;
				console.log($scope.formNumber);
			}else{
				console.log("没有formNumber");
			}
		})
	}
})

//提交函数
function submitApproval()
{
	document.getElementById("submitButtonFlag").disabled=true;
	var parm = parseQueryString();
	var scope=getAngularScope("projectManagerModel");
	scope.threeLeaderIdLsit=unique(scope.threeLeaderIdLsit);
	if(scope.applicantInfo.expectedBandwidth=="Corpate Customer"){
		scope.applicantInfo.expectedBandwidth=scope.applicantInfo.expectedBandwidthByself;
	}
	//带单位的如果不填写的话则默认为0
	if(scope.applicantInfo.expectedBandwidth==""){
		
		scope.applicantInfo.expectedBandwidth="0";
	}
	console.log(scope.applicantInfo);
	console.log(scope.basicInformation);
	//向后台提交数据
	var data1={
			basicInformation:scope.basicInformation,
			applicantInfo:scope.applicantInfo,
			reviewResult:"0"
		};
	if(parm.taskKey){
		//调申请的接口
		var objopenAccount={
				 "request.groupId":scope.basicInformation.formNumber,
				 "request.data1":JSON.stringify(data1),
				 "request.dataId":parm.dataId,
				 "request.taskId":parm.taskId,
				 "request.result":"0"
			};
		//提交按钮置灰
		console.log(objopenAccount);
  modify(objopenAccount,function(data){
			console.log(data);
			if(data.result==0){
	    		swal({
			    	title: "Success",
			    	timer: 2000,
			    	confirmButtonText: "Make Sure"
			    },function(){
			    	window.location.href = "taskApply.html";
			    });
	    	
				console.log("修改成功");
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
	}else{
		//调开户的接口
		var objopenAccount={
				 "request.groupId":scope.formNumber,
				 "request.data1":JSON.stringify(data1)
			};
			console.log(objopenAccount);
			openAccountSubmitByServer(objopenAccount,function(data){
				console.log(data);
				if(data.result==0){
		    		swal({
				    	title: "Success",
				    	timer: 2000,
				    	confirmButtonText: "Make Sure"
				    },function(){
				    	window.location.href = "taskApply.html";
				    });
		    	
					console.log("开户成功");
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
	
		
}

/**
 * 数组去重
 */
function unique(array){ 
	var r = []; 
	for(var i = 0, l = array.length; i < l; i++) { 
	 for(var j = i + 1; j < l; j++) 
	  if (array[i].id == array[j].id) j = ++i; 
	 r.push(array[i]); 
	 }
	 return r; 
	};


  /**
 * 统一列表分页渲染初始化,首先调用这个
 * */
if(pageNav!=undefined){
	pageNav.fn = function(pageNum){
		var scope=getAngularScope("projectManagerModel");
		console.log("看看是不是初始化了");
		scope.initSetPersonData(pageNum);
	 };
	}
