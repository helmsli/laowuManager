var  orderInfo={
			projectName:"",
			price:"",
			produceTime:"",
			invalid:"",
			adjustDays:"",
			adjustTime:"",
			reason:""
			
	}
//回调函数(这个js是用来当是修改状态的时候要通过configPath进行配置)
function delayEditRefreshUi()
{  
	var scope=getAngularScope("projectModel");
	//产品的订购信息填写的申请信息
	getProductOrderInfo();
	
var scope=getAngularScope("projectModel");
var state=scope.gloablParm.state;
}

//向后台请求以前填写的延期申请的信息（修改的信息）
function getProductOrderInfo(){
	var obj={
		//请求时需要发给后台的信息
	};
	getProductOrderInfoByServer(obj,getProductOrderInfoCallBack);
	
}
function getProductOrderInfoCallBack(data){
	if(data.result==0){
		//给对象orderInfo的各个变量进行重新的赋值，也就是变成从后台取过来的以前的值
	}
}
var submitEditInfo=function(){
	var editInfoObj={
			//提交修改之后的信息给后台；	
	};
	submitEditInfoByServer(editInfoObj,function(data){
		if(data.result==0){
			window.location.history(-1);//需要跳转的页面
		}
	});
	
}
//调用回调函数
function initApproval()
{
	initFileStateListener(delayEditRefreshUi);
}
initApproval();






