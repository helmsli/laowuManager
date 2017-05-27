//回调函数
function approvalRefreshUi()
{  
	console.log("测试是不是调用回调了");
	var scope=getAngularScope("projectModel");
	//如果是网管审核的话没有转网管
	scope.netWorkRadio=true;
	scope.project={
			assignPersonText:"",
	};
	scope.extensionReview={
			reviewResult:"0",		 	 	 
		 	notes:"", 	 
	 }
	scope.suggestion={
		title:"审批",
		result:0,
		networkFlag:false,//控制+号是不是显示的
		notes:"",
	}
	var scope=getAngularScope("projectModel");
	var state=scope.gloablParm.state;
	if(state=="NetworkAdminReview"){
		scope.netWorkRadio=false;
	}
	getRuleId();
	monitorRadio();
}
//提交函数，需要提交给后台的
function submitApproval()
{
	document.getElementById("submitButtonFlag").disabled=true;
	var parm = parseQueryString();
	var scope=getAngularScope("projectModel");
	var assignee= {"assigneeType":1,"id":scope.extensionReview.netWorkId};
	console.log(scope.extensionReview);
  	var obj={	   
  			 "request.result":scope.extensionReview.reviewResult,
  			 "request.groupId":parm.groupId,
  			 "request.taskId":parm.taskId,
			 "request.data1":JSON.stringify(scope.extensionReview),
			 "request.resultExt":JSON.stringify(assignee)
			
	};
  	console.log(JSON.stringify(obj));
  	submitTaskApproveByServer(obj,function(data){
	    console.log(JSON.stringify(data));
		if(data.result == 0){
    		swal({
		    	title: "Sucess",
		    	timer: 2000,
		    	confirmButtonText: "Make sure"
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
			console.log("审批不成功");
		
		}
    });	
}
//当选择转网管的时候会出现+号，当选择不是转网管的时候则+号是消失的
function monitorRadio(){
	var scope=getAngularScope("projectModel");
	$("#radio_3").change(function(){
		_this=$(this);
		checkedValue=_this.val();
		if(checkedValue==true){
			scope.suggestion.networkFlag=true;	
		}else{
			scope.suggestion.networkFlag=false;	
		}
	})
}


function initApproval()
{
	initFileStateListener(approvalRefreshUi);
}
initApproval();

/**
 * 点击选择三级部门经理初始化数据从后台去取三级项目经理的列表
 * @param id
 */
function getRuleId(){
	var scope=getAngularScope("projectModel");
	//去后台查询roleId
	var getRoleIdObj={
			"request.roleName":"networkAdmin",
		};
	getRoleId(getRoleIdObj,function(data){
		if(data.result==0){
			scope.$apply(function(){
				scope.roleId=data.responseInfo.roleId;
			})
			console.log("formNumber成功");
			console.log(scope.roleId);
		}else{
			console.log("没有formNumber");
		}
	})
	
}
function initThreeLeader(){
	var scope=getAngularScope("projectModel");
	var childrenScope=getAngularScope("myDataController");
	var obj={
			"request.role_id":scope.roleId,
			"page.pageNum":1,
			"page.pageSize":10,
			"request.keyWords":childrenScope.queryKey//重新赋值
		};
	console.log(obj);
	//去查询列表
	getGepresentative(obj,function(data){
		console.log(data);
	 	if(data.result == "0"){
	 		console.log(4444444);
	 		console.log(data);
	 		scope.ThreeLeaderLsit=data.responseInfo.lists;
			scope.$apply(scope.ThreeLeaderLsit);
			
			var childrenScope=getAngularScope("myDataController");
			//查询出来的专家列表
			childrenScope.dataList=scope.ThreeLeaderLsit;
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
}
/**
 * 统一列表分页渲染初始化,首先调用这个
 * */
pageNav.fn = function(pageNum){
	console.log("看看是不是初始化了");
	initThreeLeader(pageNum);
 };		









