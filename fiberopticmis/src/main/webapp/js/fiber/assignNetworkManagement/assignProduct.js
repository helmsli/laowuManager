//controller作用域
App.controller('myDataController', ['$scope', '$ocLazyLoad', function($scope) { 
	$scope.pageFlag=true;
	$scope.deletFlag=false;
	//表格标题
	$scope.titleList = [
			"姓名",
			"所属部门",
			"所属单位"
	];
	$scope.dataList=[];
	$scope.arrList=[];
	$scope.pageFlag=true;
	$scope.showFlag=true;
//	$scope.dataList = data.dataList;
	$scope.initTab=function(){
		//console.log("初始化指定三级部门经理列表");
	};
	$scope.queryKey="";
	//全选列表里的checkbox
	$scope.setCheckAll=function(obj){
		var checkbox=$("#box input[data-checkBoxname='listCheckbox']");
		var checkBoxAll=$("#"+obj).prop("checked");
		checkbox.prop("checked",checkBoxAll);
		var dataList=$scope.dataList;
		//选中
		if(checkBoxAll)
		{
			for(var i=0;i<dataList.length; i++)
			{
				var isHide=$("#"+dataList[i].productId).is(':hidden');
				//如果当前checkbox 是显示状态 就添加
				if(!isHide)
				{
					//取出来roleId
					//var roles=dataList[i].roles;
					//var roleId=roles[0];
					$scope.addChecked(dataList[i].productId,dataList[i].productName);
					//console.log("hahahhahah");
					console.log(dataList[i].productName);
				}
			}
		}else{
			
			for(var i=0;i<dataList.length;i++)
			{
				var isHide=$("#"+dataList[i].productId).is(':hidden');
				if(!isHide)
				{
					$scope.deleteChecked(dataList[i].productId);
				}
			}			
		}
		
	}

	//单选中列表里的checkbox
	$scope.checkOnly=function(productId,productName)
	{
		console.log("看看这个checkbox能不能执行");
		$scope.setCheckState();
	}
	
	//已选列表里是否已存在?
	$scope.isCheckedArray=function(productId)
	{
		var hasObj=false;
		var checkedList=$scope.arrList;
		var ii=0;
		while (ii<checkedList.length)
		{
			if(checkedList[ii].productId==productId)
			{
				hasObj=true;
				break;
			}
			ii++;
		}
		return hasObj;
	}
	
	$scope.findIndexForArray=function(productId)
	{
		var findIndex=0;
		var checkedList=$scope.arrList;
		var i=0;
		while (i<checkedList.length)
		{
			if(checkedList[i].productId==productId)
			{
				findIndex=i;
				break;
			}
			i++;
		}
		return findIndex;
	}
	//选中添加到已选
	$scope.addChecked=function(productId,productName)
	{  
		var obj={productId:productId,productName:productName};
		var hasObj=$scope.isCheckedArray(productId);
		if(!hasObj)
		{
			$scope.arrList.push(obj);
		}
		//$scope.setCheckState();
	}
	//选中从已选列表里删除
	$scope.deleteChecked=function(productId)
	{
		var checkedList=$scope.arrList;
		var hasId=$scope.isCheckedArray(productId);
		if(hasId)
		{
			var findIndex=$scope.findIndexForArray(productId);
			$scope.arrList.splice(findIndex,1);
		}
		
		//$scope.setCheckState();
	}
	//数组发生变更时候都调用一下，把所有的checkbox过滤一遍，检查是否有漏掉的。
	$scope.setCheckState=function()
	{
		var dataList=$scope.arrList;
		 //console.log("$scope.arrList");
		 //console.log($scope.arrList);
		var checkboxAll=$("#box input[data-checkBoxname='listCheckbox']");
		checkboxAll.each(function(){
			var _self=$(this);
			var productId=_self.attr("id");
			var productName=_self.attr("data-checkName");
			var checkedFlag=document.getElementById(productId).checked;
			var hasId=$scope.isCheckedArray(productId);
			/*
			 * 二选一
			 * 1.以checked选中为准
			 * 2.以arrList得到的数据为准
			 * */
			//checked为准
			if(checkedFlag)
			{
				if(!hasId)
				{
					$scope.arrList.push({productId:productId,productName:productName});
				}
			}else{
				if(hasId)
				{
					var findIndex=$scope.findIndexForArray(productId);
					$scope.arrList.splice(findIndex,1);
				}
			}
			if($scope.arrList.length>0)
			{
				$("#che_2").prop("checked",true);
				$scope.showFlag=false;
			}else{
				$("#che_2").prop("checked",false);
				$scope.showFlag=true;
			}
		})
	}
	
	//删除已选中的列表
	$scope.deleteOnly=function(productId){
		var checkedList=$scope.arrList;
		$scope.deleteChecked(productId);
		var leng=checkedList.length;
		if(leng<=0)
		{
			$("#che_2").prop("checked",false);
			$scope.showFlag=true;
		}
		$("#box input[type='checkbox']").each(function(){
			var _self=$(this);
			var value=_self.val();
			if(productId==value)
			{
				_self.prop("checked", false);
			}
		})
	}
	
	//确定选择名称并返给页面input
	$scope.confirmSelectAssignPersion=function ()
	{
		var parentScope=getAngularScope("projectModel");
		console.log(parentScope);
		$('#selectedModal').modal('hide');
		var checkedList=$scope.arrList;
		parentScope.project.assignPersonText="";
		for(var i =0; i<checkedList.length ;i++)
		{
			var productId=checkedList[i].productId;
			var obj={roleType:1,privilege:0,productId:productId};
			if($scope.deletFlag==false){
				parentScope.threeLeaderIdLsit=[];
				$scope.deletFlag=true;
			}
			obj.name = checkedList[i].productName;
			parentScope.threeLeaderIdLsit.push(obj);
			//发送送给后台的指定三级部门经理
				parentScope.project.assignPersonText=checkedList[i].productName;
				parentScope.taskInfoConform.selectProduct=parentScope.project.assignPersonText;
				parentScope.taskInfoConform.selectProductId=checkedList[i].productId;
		}
	}
	
	/**
	 * 模糊查询匹配的接口
	 * @param id
	 */
	$scope.query=function(){
		initSetPersonData();
	}
	//已经选中的第二次点击选择的时候要被带回的
	$scope.getChecked=function(productId){
		var flag=false;
		var threeLeaderLsit=$scope.arrList;
		for(var i in threeLeaderLsit){
				 if(productId == threeLeaderLsit[i].productId){
					 flag= true;
					 break;
				 }
		}
		return flag;
	}
	
}]);
function unique(array){ 
	var r = []; 
	for(var i = 0, l = array.length; i < l; i++) { 
	 for(var j = i + 1; j < l; j++) 
	  if (array[i].id == array[j].id) j = ++i; 
	 r.push(array[i]); 
	 }
	 return r; 
	};

