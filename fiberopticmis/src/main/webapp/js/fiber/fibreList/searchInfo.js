//根据条件查询列表时的方法
App.controller('searchInfo', function($scope) {
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
	var startTime= new Date();
	startTime.setMonth(startTime.getMonth()-3);
	if(startTime.getMonth()+1<10){
		startMonth=startTime.getMonth()+1;
		startMonth="0"+startMonth;
	}else{
		startMonth=startTime.getMonth()+1;
	}
	console.log(startTime.getDate());
	console.log(startTime.getMonth());
	if(startTime.getDate()<10){
		startDate="0"+startTime.getDate();
	}else{
		startDate=startTime.getDate();
	}
	startTime=startMonth+"/"+startDate+"/"+startTime.getFullYear();
	//searchObjforList开始都是空的,当点击搜索的时候才赋值去查询。（因为全是走的一个接口）
	$scope.searchObj={
			startTime:startTime,
			endTime:endTime,
			orderType:0,
			queryType:"telNo",
			numContent:""
	};
	$scope.searchObjforList={
			startTime:startTime,
			endTime:endTime,
			orderType:"",
			queryType:"",
			numContent:""
	}
	$scope.orderTypeOptions=[{id:0,name:"All"},{id:1,name:"Open"},{id:2,name:"Extension"}];
	$scope.queryTypeOptions=[ {id:"telNo",name:"Tel No."},{id:"IDNO",name:"ID No."},{id:"installationAddress",name:"Installation Address"},{id:"formNo",name:"Form No."}];
	
	$scope.serach=function(){
		$scope.searchObj.startTime=$("#startTime").val();
		$scope.searchObj.endTime=$("#endTime").val();
		$scope.searchObjforList=$scope.searchObj;
		//为了分页的标志做的特殊处理
		var scopeTaskApply=getAngularScope("taskApply");
		var scopeTaskRemain=getAngularScope("taskRemain");
		var scopeTaskFinish=getAngularScope("taskFinish");
		if(scopeTaskApply!=undefined){
			var phase = scopeTaskApply.$$phase;
			if(phase =='$apply'||phase =='$digest'){
				scopeTaskApply.pageByConditionFlag=true;
			}else{
				scopeTaskApply.$apply(function(){
					scopeTaskApply.pageByConditionFlag=true;
				})
			}
		}
		
		if(scopeTaskRemain!=undefined){
			var phase = scopeTaskRemain.$$phase;
			if(phase =='$apply'||phase =='$digest'){
				scopeTaskRemain.pageByConditionFlag=true;
			}else{
				scopeTaskRemain.$apply(function(){
					scopeTaskRemain.pageByConditionFlag=true;
				})
			}
		}
		if(scopeTaskFinish!=undefined){
			var phase = scopeTaskFinish.$$phase;
			if(phase =='$apply'||phase =='$digest'){
				scopeTaskFinish.pageByConditionFlag=true;
			}else{
				scopeTaskFinish.$apply(function(){
					scopeTaskFinish.pageByConditionFlag=true;
				})
			}
		}
		initTab();
		
	}
})
