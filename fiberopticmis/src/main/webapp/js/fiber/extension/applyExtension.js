App.controller('applyExtension', function($scope,$rootScope,translateTip) {
	$scope.productName=[];
	//查询时需要的信息
	var getFormNumberObj={};
	$scope.telOptions=[{
		"firstNum":"+505(Combodia)",
		"countryNum":"505"	
	}]
	$scope.fiberDelayCustomIfo={};
	$scope.extension=extension;
	$scope.submitBackButtons=false;
	$scope.productOrderFlag=false;
	$scope.productOrderSubmitBackButtons=false;
	$scope.productInfoSubmitBackButtons=false;
	$scope.fiberDelayOrderInfo={
		subscriberId:"",//用户Id
		productName:"",//产品名称
		adjustDays:3,//调整天数
		adjustTime:"",//调整时间
		reason:""//调整原因
	};
	
	$scope.numberObj ={
			countryNum:"505",
			number:""
	}
	$scope.customerExistFlag=false;//这个控制如果数据是不为空的话则客户的基本信息和产品的订购信息是不显示的(没搜索或者没有搜索到)
	$scope.pageShowFlag=false;
//点击查询去后台查询需要的信息
	$scope.query=function(){
		//var tatolNum=$scope.numberObj.countryNum+$scope.numberObj.number;
		var getUidObj={
			"queryInfo.queryType":1,
		    "queryInfo.queryValue":$scope.numberObj.number,
		    "queryInfo.countryCode":$scope.numberObj.countryNum
		};
		getUidByServer(getUidObj,function(data){
			console.log(data);
			if(data.queryResult.retCode==0){
				
				var phase = $scope.$$phase;
				if(phase =='$apply'||phase =='$digest'){
					$scope.subscriberId=data.queryResult.returnObject;
					$scope.fiberDelayOrderInfo.subscriberId=$scope.subscriberId;
					
				}else{
					$scope.$apply(function(){
						$scope.subscriberId=data.queryResult.returnObject;
						$scope.fiberDelayOrderInfo.subscriberId=$scope.subscriberId;
					})
				}
				var queryDelayInfoObj={
						"subsId":$scope.subscriberId
				};
				queryDelayInfoByServer(queryDelayInfoObj,function(data){
					console.log(data);
					if(data.retCode==0){
						//只要有信息返回按钮就存在
						var phase = $scope.$$phase;
						if(phase =='$apply'||phase =='$digest'){
							$scope.submitBackButtons=true;
							$scope.productInfoSubmitBackButtons=true;
						}else{
							$scope.$apply(function(){
								$scope.submitBackButtons=true;
								$scope.productInfoSubmitBackButtons=true;
							})
						}
						//产品的名字
						if(data.planProductInfo!=undefined){
							var phase = $scope.$$phase;
							if(phase =='$apply'||phase =='$digest'){
								//选择的产品只有一个
								$scope.productName.push(data.planProductInfo.productName);
								$scope.productNameOptions=($scope.productName)[0];
								$scope.fiberDelayOrderInfo.productName=$scope.productName[0];
							}else{
								$scope.$apply(function(){
									$scope.productName.push(data.planProductInfo.productName);
									$scope.productNameOptions=($scope.productName)[0];
									$scope.fiberDelayOrderInfo.productName=$scope.productName[0];
								})
							}
						
						
						}
						if(data.customerProductOrder!=undefined){
							var phase = $scope.$$phase;
							if(phase =='$apply'||phase =='$digest'){
								$scope.productOrderSubmitBackButtons=true;
								$scope.productOrderFlag=true;
							}else{
								$scope.$apply(function(){
									$scope.productOrderSubmitBackButtons=true;
									$scope.productOrderFlag=true;
								})
							}
							
							var expiryDate=new Date(data.customerProductOrder.expire_time.time);
							$scope.expiryDateStart=expiryDate;
							$("#datetimepicker1").datetimepicker("setStartDate",expiryDate);
							var expiryDate1=new Date(data.customerProductOrder.expire_time.time);
							expiryDate1.setDate(expiryDate.getDate()+$scope.fiberDelayOrderInfo.adjustDays);
							var effectiveDate=new Date(data.customerProductOrder.create_time.time);
							expiryDate=expiryDate.getMonth()+1+"/"+expiryDate.getDate()+"/"+expiryDate.getFullYear()
							effectiveDate=effectiveDate.getMonth()+1+"/"+effectiveDate.getDate()+"/"+effectiveDate.getFullYear();
							var adjustTimeInit=expiryDate1.getMonth()+1+"/"+expiryDate1.getDate()+"/"+expiryDate1.getFullYear();
							$("#extendedPeriod").val(adjustTimeInit);
						}else{
							var phase = $scope.$$phase;
							if(phase =='$apply'||phase =='$digest'){
								$scope.productOrderSubmitBackButtons=false;
								
							}else{
								$scope.$apply(function(){
									$scope.productOrderSubmitBackButtons=false;
								})
							}
							
						}
						var openAccountTime=new Date(data.fiberDelayCustomInfo.openAccountTime.time);
						openAccountTime=openAccountTime.getMonth()+1+"/"+openAccountTime.getDate()+"/"+openAccountTime.getFullYear();
						data.fiberDelayCustomInfo.openAccountTime=openAccountTime;
						// 去后台请求表单号
						getFormNumber(getFormNumberObj,function(data){
							if(data.result==0){
								console.log(data);
								$scope.formNumber=data.responseInfo.groupId;
								console.log($scope.formNumber);
							}else{
								console.log("没有formNumber");
							}
						})
						var phase = $scope.$$phase;
						if(phase =='$apply'||phase =='$digest'){
							
							$scope.pageShowFlag=false;
							$scope.customerExistFlag=true;
							$scope.fiberDelayCustomIfo=data.fiberDelayCustomInfo;
							//对后台返回的信息进行处理
							if($scope.fiberDelayCustomIfo.userStatus=="0"){
								$scope.fiberDelayCustomIfo.userStatusShow="Normal";
							}else if($scope.fiberDelayCustomIfo.userStatus=="1"){
								$scope.fiberDelayCustomIfo.userStatusShow="Manually suspended";
							}else{
								$scope.fiberDelayCustomIfo.userStatusShow="Inactivated";
							}
							if($scope.fiberDelayCustomIfo.idType=="0"){
								$scope.fiberDelayCustomIfo.idTypeShow="Id card"
							}
							if($scope.fiberDelayCustomIfo.idType=="1"){
								$scope.fiberDelayCustomIfo.idTypeShow="passport"
							}
							$scope.fiberDelayOrderInfo.effectiveDate=effectiveDate;
							if(data.customerProductOrder!=undefined){
								$scope.fiberDelayOrderInfo.productId=data.customerProductOrder.product_id;
							}
							if(data.planProductInfo!=undefined){
								$scope.fiberDelayOrderInfo.price=data.planProductInfo.listPrice;
							}
							$scope.fiberDelayOrderInfo.expiryDate=expiryDate;
							//$scope.fiberDelayOrderInfo.price=expiryDate;
						}else{
							$scope.$apply(function(){
								
								$scope.pageShowFlag=false;
								$scope.customerExistFlag=true;
								$scope.fiberDelayCustomIfo=data.fiberDelayCustomInfo;
								if($scope.fiberDelayCustomIfo.userStatus=="0"){
									$scope.fiberDelayCustomIfo.userStatusShow="Normal";
								}else if($scope.fiberDelayCustomIfo.userStatus=="1"){
									$scope.fiberDelayCustomIfo.userStatusShow="Manually suspended";
								}else{
									$scope.fiberDelayCustomIfo.userStatusShow="Inactivated";
								}
								if($scope.fiberDelayCustomIfo.idType=="0"){
									$scope.fiberDelayCustomIfo.idTypeShow="Id card"
								}
								if($scope.fiberDelayCustomIfo.idType=="1"){
									$scope.fiberDelayCustomIfo.idTypeShow="passport"
								}
								$scope.fiberDelayOrderInfo.effectiveDate=effectiveDate;
								if(data.customerProductOrder!=undefined){
									$scope.fiberDelayOrderInfo.productId=data.customerProductOrder.product_id;
								}
								$scope.fiberDelayOrderInfo.expiryDate=expiryDate;
								if(data.planProductInfo!=undefined){
									$scope.fiberDelayOrderInfo.price=data.planProductInfo.listPrice;
								}
								//$scope.fiberDelayOrderInfo.price=expiryDate;
								
							});
						}
						console.log("信息获取到了");
					}else{
						var phase = $scope.$$phase;
						if(phase =='$apply'||phase =='$digest'){
							$scope.customerExistFlag=false;;//控制客户信息显示
							$scope.pageShowFlag=true;//控制错误与显示
							
						}else{
							$scope.$apply(function(){
								$scope.customerExistFlag=false;
								$scope.pageShowFlag=true;
							})
						}
						console.log("延期需要的信息没有获取到");
						swal({
					    	title: "No data available!",
					    	timer: 2000,
					    	confirmButtonText: "Make Sure"
					    },function(){
					    	//window.location.href = "taskApply.html";
					    });
					}
					
				})
			}else{
				
				var phase = $scope.$$phase;
				if(phase =='$apply'||phase =='$digest'){
					$scope.pageShowFlag=true;
					$scope.productOrderSubmitBackButtons=false;
					$scope.productInfoSubmitBackButtons=false;
					$scope.customerExistFlag=false;
					$scope.submitBackButtons=false;
				}else{
					$scope.$apply(function(){
						$scope.pageShowFlag=true;
						$scope.productOrderSubmitBackButtons=false;
						$scope.productInfoSubmitBackButtons=false;
						$scope.customerExistFlag=false;
						$scope.submitBackButtons=false;
					})
				}
				console.log("获取uid不成功");
				swal({
			    	title: "No data available!",
			    	timer: 2000,
			    	confirmButtonText: "Make Sure"
			    },function(){
			    	//window.location.href = "taskApply.html";
			    });
			}
		})
	
		$("#adjustDays").on("keyup keydown onafterpaste",function(e){
			   return digitInputInt($(this), e); 
			   return;
		})
		  //电话只能输入小于20位
	   $("#adjustDays").on("keyup keydown onafterpaste",function(e){
		   return inputLength(this,8);
	   })
	}
//延期申请提交给后台
	$scope.submitApply=function(){
		document.getElementById("submitButtonFlag").disabled=true;
		$scope.fiberDelayOrderInfo.adjustTime=$("#extendedPeriod").val();
		var data1={
				"fiberDelayCustomIfo":$scope.fiberDelayCustomIfo,
				"fiberDelayOrderInfo":$scope.fiberDelayOrderInfo
					
		};
		if($scope.fiberDelayOrderInfo.adjustDays==""||$scope.fiberDelayOrderInfo.adjustDays==null||$scope.fiberDelayOrderInfo.adjustDays==0){
			swal({
		    	title: "Please enter a number greater than 0",
		    	timer: 2000,
		    	confirmButtonText: "Make Sure"
		    },function(){
		    	document.getElementById("submitButtonFlag").disabled=false;
		    });
			return;
		}
		console.log("&&&&&&&&&&&");
		console.log(data1);
			var objSubmit={
					"request.groupId":$scope.formNumber,
					"request.data1":JSON.stringify(data1),
			};
			console.log(objSubmit);
			submitApplyByServer(objSubmit,function(data){
				console.log(data);
				if(data.result==0){
		    		swal({
				    	title: "Sucess",
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
					console.log("开户不成功");
				}
			});
		
	}
	
	
})
var extension={
		baseIfo:false,
		order:false
}

//延期的天数只能是只能输入数字
function prevent(e) {
	e.returnValue = false;
}

//处理只允许输入数字(不带小数点)
function digitInputInt(el, e) {  
    var ee = e || window.event; // FF、Chrome IE下获取事件对象  
    var c = e.charCode || e.keyCode; //FF、Chrome IE下获取键盘码  
    var val = el.text();  
    if (c == 110 || c == 190){ // 110 (190) - 小(主)键盘上的点  
        	return false;
            prevent(e);
    } else if(c==9){
    	return true;
    	}else {
        if ((c != 8 && c != 46 && // 8 - Backspace, 46 - Delete  
            (c < 37 || c > 40) && // 37 (38) (39) (40) - Left (Up) (Right) (Down) Arrow  
            (c < 48 || c > 57) && // 48~57 - 主键盘上的0~9  
            (c < 96 || c > 105)) // 96~105 - 小键盘的0~9  
            || e.shiftKey) { // Shift键，对应的code为16  
            prevent(e); // 阻止事件传播到keydown  
        	return false;
        }  
    }  
    return true;
}   
//限制输入框的长度
function inputLength(obj,length){
	   var isCanInput=true;
	   var _this=$(obj);
	   var myValue=_this.val();
	   myValue=myValue.replace(/\s/g,"");
    if(myValue.length==length){
    	console.log(myValue.length);
    	console.log(length);
    	var e=this.event;
        var ee = e || window.event; // FF、Chrome IE下获取事件对象  
        var c = e.charCode || e.keyCode; //FF、Chrome IE下获取键盘码  
    	if(c != 8 && c != 46){//8 - Backspace, 46 - Delete 
    		prevent(e);
    		isCanInput=false;
    	}
         }
    console.log(isCanInput);
    return isCanInput;
	}   





































				 				