//点击上传需要绑定的函数，需要向后台提交的东西
function uploadButtonSubmit(id){
	uploadFile.ajaxFileUpload({
	    url: getBasePath()+'/projectAnnex/upload', //用于文件上传的服务器端请求地址
	    fileElementId: id, //文件上传空间的id属性  <input type="file" id="file" name="file" />
	    //async:true,
	    success: function (data)
	    {
	    	var scope=getAngularScope("projectModel");
	    	data=JSON.parse(data);
	    	if(!scope.fileUpload)
	    	{
	    		scope.fileUpload=new Object();
	    	}
	    	console.log("**************");
	    	console.log(data);
	    	scope.fileUpload[scope.gloablParm.state]= data.responseInfo.projectAnnexs;
	    	scope.projectAnnexs=data.responseInfo.projectAnnexs;
	    	//console.log(JSON.stringify(data.responseInfo.projectAnnexs));
	    	scope.$apply(scope.projectAnnexs);
	    },
	    error: function (data, status, e)//服务器响应失败处理函数  
	    {
	        alert(e);
	    }
	});
}
//回调函数(这个js是用来当是修改状态的时候要通过configPath进行配置)
function openAccountEditRefreshUi()
{  
	var scope=getAngularScope("projectModel");
	//申请开户的用户信息
	var scope=getAngularScope("projectModel");
	console.log("看看进行到这里了么");
	scope.applicantInfo={              //这个到时可以删掉
		    customerName:"我就是那个修改的人",      //客户姓名
			contactName:"test",          //联系人
			contactNumber:"test",     //联系电话
			expectedBandwidth:"test", //申请带宽
			expectedBandwidthByself:"test",//自定义申请宽带
			installationAddress:"test",	      //安装地址
			customerType:{
			customerType:"信威的类型",
			},                              //用户的类型
			serviceLength:"test",        //服务时长
			installationArea :"信威",      //安装区域
		                                 
			fibreType:"fibreType",       //光纤类型
			notes :"notes"   ,                 //备注    
	};
	//得到上次开户时用户填写的信息
	getOpenAccountInfo();
	
var scope=getAngularScope("projectModel");
var state=scope.gloablParm.state;
}
//向后台请求以前填写的开户的信息（修改的信息）
function getOpenAccountInfo(){
	var obj={
		//请求时需要发给后台的信息
	};
	//getOpenAccountInfoByServer(obj,getOpenAccountInfoCallBack);
	
}
function getOpenAccountInfoCallBack(data){

	if(data.result==0){
		
		
		//给对象orderInfo的各个变量进行重新的赋值，也就是变成从后台取过来的以前的值
	}
}
var submitOpenAccountEditInfo=function(){
	var editInfoObj={
			//提交修改之后的信息给后台；	
	};
	submitOpenAccountEditInfoByServer(editInfoObj,function(data){
		if(data.result==0){
			window.location.history(-1);//需要跳转的页面
		}
	});
	
}
//调用回调函数
function initApproval()
{
	initFileStateListener(openAccountEditRefreshUi);
}
initApproval();






