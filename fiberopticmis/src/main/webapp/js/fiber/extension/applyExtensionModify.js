App.controller('applyExtensionModify', function($scope) {
	//在初始化的时候调用，为了限制只能输入数字
	$scope.modifyExtension=function(){
		var parm = parseQueryString();
		var scope=getAngularScope("projectModel");
		var data1={
				"fiberDelayCustomIfo":scope.fiberDelayCustomIfo,
				"fiberDelayOrderInfo":scope.fiberDelayOrderInfo,
				"reviewResult":"0"
					
		};

		var objSubmit={
				"request.groupId":parm.groupId,
				 "request.data1":JSON.stringify(data1),
				 "request.dataId":parm.dataId,
				 "request.taskId":parm.taskId,
				 "request.result":"0"
		};
		console.log(objSubmit);
		modify(objSubmit,function(data){
			document.getElementById("submitButtonFlag").disabled=true;
			if(scope.fiberDelayOrderInfo.adjustDays==""||scope.fiberDelayOrderInfo.adjustDays==null||scope.fiberDelayOrderInfo.adjustDays==0){
				swal({
			    	title: "Please enter a number greater than 0",
			    	timer: 2000,
			    	confirmButtonText: "Make Sure"
			    },function(){
			    	document.getElementById("submitButtonFlag").disabled=false;
			    });
				return;
			}
			if(data.result==0){
	    		swal({
			    	title: "Sucess",
			    	timer: 2000,
			    	confirmButtonText: "Make Sure"
			    },function(){
			    	var parm=parseQueryString();
					var from=parm.from;
					location.replace(from);
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
				console.log("修改不成功");
			}
		});
	}
})
//延期的天数只能是只能输入数字
function prevent(e) {
	e.returnValue = false;

}

function digitInput(el, e) {  
  var ee = e || window.event; // FF、Chrome IE下获取事件对象  
  var c = e.charCode || e.keyCode; //FF、Chrome IE下获取键盘码  
  var val = el.text();  
  if (c == 110 || c == 190){ // 110 (190) - 小(主)键盘上的点  
      if(val.indexOf(".") >= 0)
      {
      	prevent(e);
      	return false;
       
      }
      
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





































				 				