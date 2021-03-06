
/*定义module*/
var App = angular.module("App",
    ['oc.lazyLoad','pascalprecht.translate']); 
/*国际化配置*/
App.config(function($translateProvider){
    var lang = window.localStorage.lang || 'zh-CN';
    $translateProvider.preferredLanguage(lang);
    $translateProvider.useStaticFilesLoader({
        prefix: getBasePath()+'/js/i18n/',
        suffix: '.json'
    });
});
/**
 * 权限控制service
 */
App.service("auth",function(){
	//菜单权限
    var urlPermissions = [];
    var currentPageAction = "";
    // 去后端获取菜单权限，同步立即执行
    (function(){
    	//获取菜单列表
    	getModules({},function(data){//同步获取数据
    		if (data.result==0){
    			//头部菜单数据
    			 urlPermissions = data.responseInfo.module;
		    }
    	});
    	//获取当前页面的url地址
    	var myUrl=window.location.href;
   	    if (myUrl.indexOf('userManage_edit.html')>0){
   	    	myUrl = 'userManage.html';
   	    }
   	    if (myUrl.indexOf("#")!=-1){
   	    	myUrl = myUrl.substring(0,myUrl.indexOf("#"));
   	    }
   	    var lst=myUrl.lastIndexOf("/");
   	    currentPageAction = myUrl.substring(lst+1,myUrl.length);   	   
    })();
    /**递归判断是否有权限*/
    function validatePermission(urlList,btnsn){
    	 var permissionStr = JSON.stringify(urlPermissions);
   	     if(permissionStr.indexOf(btnsn)>0){
   	    	 return true;
   	     }else{
   	    	 return false;
   	     }
    }
    return {
      /*对外公开的接口，返回菜单权限列表*/
      getMenuList:function(){
    	return urlPermissions;
      },
      /*对外公开的接口，返回当前页面的url*/
      getCurrentPageAction:function(){ 
      	    return currentPageAction;
      },
      /*对外公开的接口，判断按钮是否有权限*/
      isValidatePermission:function(btnsn){
    	 
    	  return validatePermission(urlPermissions,btnsn);
      }
    }
});
/*按钮权限设置*/
App.directive('btnPermission', ["auth",function (auth) {
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
           
           var enabledFlag = auth.isValidatePermission(attr.btnPermission);
           if(!enabledFlag){
        	   element.addClass("disabled");
        	   element.attr("disabled","disabled");
    			//a标签的disabled属性无法禁用click事件，只能通过移除click标签来处理
        		if(!element.is('button') ){
        			element.remove();
        		}
           }
        }
    };
}]);
/***
 * 定义header头部模型
 */
App.controller('head_ms', ['$scope','$ocLazyLoad','$rootScope',"auth", function($scope, $ocLazyLoad,$rootScope,auth){
	$scope.server_url = getBasePath();
	/*用户名*/
	$scope.userName = "";
	/*菜单列表*/
	$scope.menuList = [];
	/*头部菜单跳转用户资料和修改密码页面的href取绝对路径*/
	$scope.userInfoHref = getBasePath()+"/views/userCenter/userInfo.html";
	$scope.modifPwdHref = getBasePath()+"/views/userCenter/password_modify.html";
	/*是否显示头部菜单*/
	$scope.headerTypeShow = false;
	/* 初始化header头部用户信息*/
 	$scope.initHead = function(){
 		$scope.menuShow();
		$scope.getUserName();
		$scope.getMenus();
	};
	/*设置顶部菜单是否显示*/
	$scope.menuShow = function(){
		//设置头部菜单是否显示
		var headerType = config.leftMenuSkin;
		if (headerType=='head'){
			$scope.headerTypeShow = true;
		}else{
			$scope.headerTypeShow = false;
		}
	};
 	/*获取用户名*/
 	$scope.getUserName = function(){
		var userInfo = localStorage.getItem("userInfo");
		if(userInfo!=""){
			userInfo = JSON.parse(userInfo);
			$scope.userName = userInfo.fullName;
		}else{//获取用户名失败，提示重新登录
			swal('无法获取用户信息，请退出后重新登录');
		}
	}
	/*获取菜单列表*/
	$scope.getMenus = function(){
		if($scope.headerTypeShow){//当头部需要显示菜单的时候，才去加载菜单树
			$scope.menuList = auth.getMenuList();
			$scope.$applyAsync($scope.menuList);
		}
	};
 	/* 退出登录方法*/
 	$scope.toLogout = function(){
 		localStorage.removeItem("userInfo");
 	 	var url = getBasePath()+"/logout";
 	 	window.location.replace(url);
 	};
}]);
/***
 * 定义左侧菜单模型
 */
App.controller('leftMenu', ['$scope','$ocLazyLoad','$rootScope',"auth", function($scope, $ocLazyLoad,$rootScope,auth){
	$scope.server_url = getBasePath();
 	/*菜单数据列表*/
	$scope.menuList = [];
	/*示例模块数据列表*/
	$scope.demoMenuList = [];
	/*按钮权限数据*/
	$scope.functions = [];
	/*页面的url地址 */
	$scope.pageAction = "##";
	/*是否显示头部菜单*/
	$scope.headerTypeShow = false;
	/* 初始化左侧菜单项*/
 	$scope.initLeft = function(){
 		/*获取页面的url地址*/
 		$scope.pageAction = auth.getCurrentPageAction();
 		$scope.menuShow();//初始化菜单
 		$scope.getMenus();
 	};
 	/*初始化demo页面的左侧菜单*/
 	/*获取菜单列表*/
 	$scope.getMenus = function(){
 		$scope.menuList = auth.getMenuList();
		$scope.$applyAsync($scope.menuList);
	};

 	/*设置顶部菜单是否显示*/
 	$scope.menuShow = function(){
 		//设置头部菜单是否显示
 		var headerType = config.leftMenuSkin;
 		var clientWidth = document.body.clientWidth+17;
 		if(clientWidth<992){
 			headerType = "";
 		}else{
 			headerType = config.leftMenuSkin;
 		}
		if (headerType=='head'){
			$scope.headerTypeShow = true;
		}else{
			$scope.headerTypeShow = false;
		}
 	};
    /* 一级菜单是否展开*/
    $scope.parentcollapse = function(subUrl,menuName,index,functions){
    	var expand = false ;
    	if (subUrl.indexOf($scope.pageAction)>=0){
    		expand = true;
    		$("#_"+menuName).collapse('show');
    		$scope.functions = functions;
   	    }else{
    		expand = false;
    		$("#_"+menuName).collapse('hide');
    	}
    	var _expand = $scope.menuList[index].expand ;
    	var sn = $scope.menuList[index].sn;
    	$scope.menuList[index].expand = _expand?_expand:expand;//设置展开或收缩
     	return expand;
    };
}]);
/***
 * 把angular 表单提交对象转成真正提交后台的对象
 * obj = {"request":{}}===> obj = {"request.xx":XX,"request.XX":XX}
 * @param requestObj
 * @returns {___anonymous5748_5749}
 */
function getRequestOption(obj){
	var resultObj = {};
	for(var item in obj.request){
		resultObj["request."+item] = obj.request[item];
	}
	return resultObj;
}
 /**
  * 获取angularscope
  * @description:外部调用angular scope;
  * @param {string} controllerName
  * @return {object} angular scope
  * */
  function getAngularScope(controllerName)
  {
  	var appElement = document.querySelector('[ng-controller='+controllerName+']');
    var scope = angular.element(appElement).scope(); 
  	return scope;
  }

/**
 * @description ajax函数依赖jquery
 * @param {Object json} {options.url,option.ajaxData,options.callBack,berforCallback}
 * url:
 * type:{string} post/get,
 * data：参数
 * dataType：json|""/html/txt,
 * aysnc:false|true|"",
 * callBack:成功回调函数
 * errCallBack：错误回调
 * beforeCallBack：ajax响应前回调
 * */

function requestAjax(options)
{
    var url= getBasePath()+options.url;
    var ajaxData=options.data||"";
    var callBack=options.callBack;
    var beforeCallBack=options.beforeCallBack||"";
    var type=options.type||"post";
    var async=options.hasOwnProperty("async")?options.async:true;
    var dataType=options.dataType||"JSON";
    var errCallBack=options.errCallBack||"";
    $.ajax({
        url:url,
        type:type,
        data:ajaxData,
        dataType:dataType,
        async:async,
        beforeSend:function (){
            if(beforeCallBack!="")
            {
                beforeCallBack();
            }
        },
        success:function(data){
            if(callBack)
            {   //拦截data.result ,如果没有权限，直接返回false
            	if(data.result=='403'){
            		swal('没有权限访问');
            		return;
            	}
                callBack(data);
            }else{
            	 console.log("没有callback函数");
            }
        },error:function(e){
            console.log("访问服务器失败！");
            if(errCallBack!="")
            {
                errCallBack(e);
            }
        }
    });
}
var contextPath = "";
function getBasePath(){   
	
	var root=location.hostname;
	var port=location.port;
	var pathName=location.pathname;
	var fullUrl = location.href;
	var projectName = "";//项目名
	var arr =  fullUrl.split("/");
	projectName = arr[3];
	if(projectName!="" && projectName!="views" &&projectName!="login"){
		return "http://"+root+":"+port+"/"+projectName;
	}else{
		return "http://"+root+":"+port;
	}
}
/**
 * 请求国际化文件路径
 * 返回国际化文件路径
 * **/

function getI8nFilePath()
{
	var lang=getLanguage();
	var i18nFilePath;
	if(lang=="zh")
	{
		i18nFilePath="/js/i18n/i18n_zh.js"
	}else{
		i18nFilePath="/js/i18n/i18n_en.js"
	}
	return getBasePath()+i18nFilePath;
}

/***
 * 请求错误码对应的错误信息
 * 参数:errCode
 * 返回：errMsg;
 * */
function getErrMsg(errCode)
{
	var errKey=ErrCode[errCode];
	var errMsg=i18n[errKey];
	return errMsg;
}
/**
 * @请求国家及语言类型
 * 参数：null
 * 返回：语言类型
 * **/
function getLanguage()
{
	var olang=navigator.language;
	olang=olang.toLowerCase();
	return (olang=="zh-cn")?"zh":"en";
}




function loadFileList(arryFileList)
{
	try{
		var newList=arryFileList||[];
		var skinFilePath=getSkinFile();
		var i18nFilePath=getI8nFilePath();
		newList.push(skinFilePath);
		newList.push(i18nFilePath);
		return newList;
		
	}catch(e){
		console.log("请求皮肤及国际化文件失效");
	}
	
}



/**
 * 请求皮肤样式
 * 返回皮肤文件路径，依赖config配置文件
 * **/
function getSkinFile()
{
	var skinFile;
	var skinCookie=getCookie("mystyle");
	var defaultFile=config.default||"blue";
	if(skinCookie)
	{
		skinFile="/css/themes/"+skinCookie+"/skin.css";
	}else{
		skinFile="/css/themes/"+defaultFile+"/skin.css";
	}
	return getBasePath()+skinFile;
}


/**
 * 
 * 返回皮肤文件路径，依赖config配置文件
 * **/

function setConfigHeader(scope)
{
	var myScope=getAngularScope(scope);
		var headerType=config.leftMenuSkin;
        if(headerType=="head" && headerType)
        {
        	myScope.head=true;
        }else{
        	myScope.head=false;
        }
}
	


/**
 * 请求国际化文件路径
 * 返回国际化文件路径
 * **/



/**
 * 事件绑定
 * 参数:obj:要绑定的DOM,type,事件类型,handle：回调句柄
 * **/
function addEvent(obj,type,handle)
{
   	try{
        obj.addEventListener(type,handle,false);
    }catch(e){
        try{  // IE8.0及其以下版本
            obj.attachEvent('on' + type,handle);
        }catch(e){  // 早期浏览器
            obj['on' + type] = handle;
        }
    }
}

/**
 * 事件绑定
 * 参数:obj:要绑定的DOM,e,事件类型,f：回调句柄
 * **/
function removeEvent(o,e,f){
  if(window.detachEvent)
  {
  	o.detachEvent("on"+e,f);
  }else if(window.removeEventListener)
  {
  	o.removeEventListener(e,f,false);
  }
  else{
  	o["on"+e]=null;
  }
}


//是否空对象
function isEmptyObject(e) {  
    var t;  
    for (t in e)  
        return !1;  
    return !0  
}


/**
 * 上传文件显示测试示例
 * ***/

	
	function uploadFileShow(fileId,showId)
	{
		var fileObj=document.getElementById(fileId);
	   	
	    // 注意这里
	    // fileObj.files[0];
	    var fileName=fileObj.files[0].name;
	    var fileType=fileObj.files[0].type;
	     var src = window.URL.createObjectURL(fileObj.files[0]);
	     var showBox=document.getElementById(showId);
	     showBox.innerHTML="";
	    if(fileType.indexOf("image")>=0)
	    {
		    var img = document.createElement('img');
		    img.src = src;
		   showBox.appendChild(img);
	    }else{
	    	
	    	var obj=document.createElement("object");
	    	obj.data=src;
	    	 showBox.appendChild(obj);
	    }
	}


function gotoPage(url)
{
	location.href=getBasePath()+url;
}


/*
 错误提示
 * **/

function errorModel(options)
{
	//var type="error/success/info";
	var settting={
		  title: 'Oh No!'||options.title,
		  text: options.text,
		  type: options.type,
		  styling: 'bootstrap3'
		}
	new PNotify(settting);
}



/**
 * 获取界面参数;
 * **/
function parseQueryString() {
        var args = new Object();
        var query = window.location.search.substring(1);
        var pairs = query.split("&");
        for (var i = 0; i < pairs.length; i++) {
            var pos = pairs[i].indexOf("=");
            if (pos == -1) continue;
            var argname = pairs[i].substring(0, pos);
            var value = pairs[i].substring(pos + 1);

            args[argname] = value;
        }
        return args;
}



//事件绑定
function addEvent(obj,type,handle)
{
   	try{
        obj.addEventListener(type,handle,false);
    }catch(e){
        try{  // IE8.0及其以下版本
            obj.attachEvent('on' + type,handle);
        }catch(e){  // 早期浏览器
            obj['on' + type] = handle;
        }
    }
}

//取消事件绑定
function removeEvent(o,e,f){
  if(window.detachEvent)
  {
  	o.detachEvent("on"+e,f);
  }else if(window.removeEventListener)
  {
  	o.removeEventListener(e,f,false);
  }
  else{
  	o["on"+e]=null;
  }
}


//是否空对象
function isEmptyObject(e) {  
    var t;  
    for (t in e)  
        return !1;  
    return !0  
}
/**bootstrap-select国际化文件引入*/
function bootstrapSelectLangInit(lang,jsFilePath) {
	 
	var i18nPath = "";
	
	var sourcePath="defaults-zh_CN.js";
	var lowerLanguage = this.language.toLowerCase();
	//语言环境
	if(lowerLanguage.indexOf("zh")>=0){
		sourcePath="defaults-zh_CN.js";
	}else if(lowerLanguage.indexOf("es")>=0){
		sourcePath="defaults-es_CL.js";
	}else if(lowerLanguage.indexOf("en")>=0){
		sourcePath="defaults-en_US.js";
	}
	i18nPath=jsFilePath+sourcePath;
	var scriptObj = document.createElement("script"); 
		scriptObj.src = i18nPath; 
		scriptObj.type = "text/javascript"; 
	document.getElementsByTagName("head")[0].appendChild(scriptObj);
};
/*字符串转日期类型*/
function str2date(str){
	if (str==null ||str =='' ||str instanceof  Date){
		return null;
	}
    str = str.replace(/-/g,"/"); 
    return  new Date(str);
}
/*日期格式化*/
Date.prototype.pattern=function(fmt) {         
    var o = {         
    "M+" : this.getMonth()+1, //月份         
    "d+" : this.getDate(), //日         
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
    "H+" : this.getHours(), //小时         
    "m+" : this.getMinutes(), //分         
    "s+" : this.getSeconds(), //秒         
    "q+" : Math.floor((this.getMonth()+3)/3), //季度         
    "S" : this.getMilliseconds() //毫秒         
    };         
    var week = {         
    "0" : "/u65e5",         
    "1" : "/u4e00",         
    "2" : "/u4e8c",         
    "3" : "/u4e09",         
    "4" : "/u56db",         
    "5" : "/u4e94",         
    "6" : "/u516d"        
    };         
    if(/(y+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
    }         
    if(/(E+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
    }         
    for(var k in o){         
        if(new RegExp("("+ k +")").test(fmt)){         
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
        }         
    }         
    return fmt;         
}   