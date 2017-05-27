/**模拟查询请求获得的数据*/
var response = {
	"result":0,
	"userInfo":{
		"name":"zhangsan",
		"sex":"0",//0：man;1 woman
		"depart":"运营支撑与业务应用研发部",
		"telPhone":18588888888,
		"email":"123@163.com",
		"birthday":"2010-01-13",
		"cardtype":"0",
		"cardno":"25649894566x"
	}
}
/*定义module*/
App.controller('app', ['$scope','$ocLazyLoad', function($scope, $ocLazyLoad){
	$('#datetimepicker').datetimepicker({collapse: true}).on("hide",function(){
	   var $input = $(this).find("input");
	   $scope.$apply(function(){
	   	   var objName = $input.attr('ng-model');
	   	   var objKeys = objName.split(".");
	       $scope[objKeys[0]][objKeys[1]][objKeys[2]] = $input.val();    
	   });
	});
	$scope.loadBootstrap = function(){
		var myFileList=[];
		myFileList=loadFileList(myFileList);
        $ocLazyLoad.load(myFileList);
    }
    
     $scope.loadBootstrap();
    
	//用户数据
	$scope.submitObj = {"request":response.userInfo};//后台获取
    
    
    
	//触发保存
	$scope.saveUserInfo = function(){
		var  submitObj = $scope.submitObj;//获取表单数据
		 var request = getRequestOption(submitObj);
		alert(JSON.stringify(request));
		$.ajax({
	        url:'/save.do',
	        type:'post',
	        data:request,
	        dataType:'json',
	        aysnc:true,
	        beforeSend:function (){
	            
	        },
	        success:function(data){
	            
	        },error:function(){
	            console.log("访问服务器失败！");
	        }
	    });
	};
	
	$scope.uploadFile = function(uploadFile,showImg){
		if ($scope.edit){//编辑状态
			
			var fileObj=document.getElementById(uploadFile);
		    // 注意这里
		    // fileObj.files[0];
		    var fileName=fileObj.files[0].name;
			$scope.userInfo.imgUrl = fileName;
			uploadFileShow(uploadFile,showImg);
		}
	}
}]);