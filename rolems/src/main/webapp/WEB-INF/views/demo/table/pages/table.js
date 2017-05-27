/*数据源*/
var userList = [{"pages":2},
	            {"dataList":
			            [{   "btsId": "85122852",
							"btsName": "马克",
							"btsType": "奥托"
						},{ 
							"btsId": "85122853",
							"btsName": "雅各伯",
							"btsType": "松顿"
						},{
							"btsId": "85122854",
							"btsName": "拉里",
							"btsType": "鸟儿"
						},{
							"btsId": "85122855",
							"btsName": "拉里",
							"btsType": "软糖"
						},{
							"btsId": "85122856",
							"btsName": "拉里",
							"btsType": "洛瑞"
						},{
							"btsId": "85122857",
							"btsName": "马克",
							"btsType": "奥托"
						},{
							"btsId": "85122858",
							"btsName": "雅各伯",
							"btsType": "松顿"
						},{
							"btsId": "85122859",
							"btsName": "拉里",
							"btsType": "鸟儿"
						},{
							"btsId": "85122860",
							"btsName": "拉里",
							"btsType": "软糖"
						},{
							"btsId": "85122861",
							"btsName": "拉里",
							"btsType": "洛瑞"
						}]
	            	},
		            {"dataList":
			            [{  "btsId": "85122862",
							"btsName": "马克",
							"btsType": "奥托"
						},{ 
							"btsId": "85122863",
							"btsName": "雅各伯",
							"btsType": "松顿"
						}]
		            }]
	
var list = [{"pages":2},
	        {"dataList":
			            [{   "btsId": "85122852",
							"btsName": "马克",
							"btsType": "奥托"
						},{ 
							"btsId": "85122853",
							"btsName": "雅各伯",
							"btsType": "松顿"
						},{
							"btsId": "85122854",
							"btsName": "拉里",
							"btsType": "鸟儿"
						},{
							"btsId": "85122855",
							"btsName": "拉里",
							"btsType": "软糖"
						},{
							"btsId": "85122856",
							"btsName": "拉里",
							"btsType": "洛瑞"
						},{
							"btsId": "85122857",
							"btsName": "马克",
							"btsType": "奥托"
						},{
							"btsId": "85122858",
							"btsName": "雅各伯",
							"btsType": "松顿"
						},{
							"btsId": "85122859",
							"btsName": "拉里",
							"btsType": "鸟儿"
						},{
							"btsId": "85122860",
							"btsName": "拉里",
							"btsType": "软糖"
						},{
							"btsId": "85122861",
							"btsName": "拉里",
							"btsType": "洛瑞"
						}]
	            	},
		            {"dataList":
			            [{  "btsId": "85122862",
							"btsName": "马克",
							"btsType": "奥托"
						},{ 
							"btsId": "85122863",
							"btsName": "雅各伯",
							"btsType": "松顿"
						}]
		            }]


/*定义module*/
App.controller('tableCtrl', ['$scope',function($scope){
    $scope.titleList = [
		"序号",
		"用户名",
		"真实姓名",
		"操作"
	];
	
	/*初始化数据显示*/
	$scope.list = userList[1].dataList;
	var pageNum=1;
	
	/*初始化页面显示*/
	function init(){
		$(".choose-data").addClass("hide");
		$(".choose-data").removeClass("show");
		if(userList.length == 0){
			$(".dataList").addClass("hide");
			$(".dataTables_paginate").addClass("hide");
			$(".no-data").addClass("show");
			$(".no-data").removeClass("hide");
		}else{
			$(".dataList").removeClass("hide");
			$(".dataTables_paginate").removeClass("hide");
			$(".no-data").addClass("hide");
			$(".no-data").removeClass("show");
		}
	}
	
	init();
	
	/*数据合并*/
	var user=userList[1].dataList.concat(userList[2].dataList);
	var users=list[1].dataList.concat(list[2].dataList);
	/*搜索*/
	$scope.searchList=function(word){
		var seachList=[];
		for(var i in user){
			if(word == user[i].btsName || word == user[i].btsId || word == user[i].btsType){
				seachList.push(user[i]);
			}
			if(user[i].btsName.indexOf(word) != -1 || user[i].btsId.indexOf(word) != -1 || user[i].btsType.indexOf(word) != -1){
			    seachList.push(user[i]);
			}
			/*查询结果一页中显示*/
			pageNav.go(1,1);
		}
		if(word==""){
			seachList = user;
			pageNav.go(1,userList[0].pages);
		}else if(word=="undefined"){
			seachList = user;
			pageNav.go(1,userList[0].pages);
		}
		if(seachList.length == 0){
			$(".choose-data").addClass("show");
			$(".choose-data").removeClass("hide");
			$(".dataTables_paginate").removeClass("show");
			$(".dataTables_paginate").addClass("hide");
		}else{
			$(".choose-data").addClass("hide");
			$(".choose-data").removeClass("show");
			$(".dataTables_paginate").removeClass("hide");
			$(".dataTables_paginate").addClass("show");
		}
		$scope.$applyAsync($scope.list = seachList);
	}
	
	$scope.search=function(word){
		var seachList=[];
		for(var i in user){
			if(user[i].btsName.indexOf(word) != -1 && user[i].btsType.indexOf($scope.type) != -1){
				    seachList.push(user[i]);
			}
		}
		if(word==""){
			seachList = user;
			pageNav.go(1,userList[0].pages);
		}else if(word=="undefined"){
			seachList = user;
			pageNav.go(1,userList[0].pages);
		}
		if(seachList.length == 0){
			$(".choose-data").addClass("show");
			$(".choose-data").removeClass("hide");
			$(".dataTables_paginate").removeClass("show");
			$(".dataTables_paginate").addClass("hide");
		}else{
			$(".choose-data").addClass("hide");
			$(".choose-data").removeClass("show");
			$(".dataTables_paginate").removeClass("hide");
			$(".dataTables_paginate").addClass("show");
	 	}
		$scope.$applyAsync($scope.list = seachList);
	}
	
	$scope.type="单板音";
	$scope.chooseType=function(type){
		if(type==0){
			$scope.type="单板音";
		}else if(type==1){
			$scope.type="双板音";
		}
	}
	/*分页*/
	pageNav.go(1,userList[0].pages);
	pageNav.fn = function(p,pn){
		pageNum=p;
		if(p==1){
			$scope.list = user.slice(0,10);
		}else if(p==2){
			$scope.list = user.slice(10,user.length);
		}
        $scope.$applyAsync($scope.list);
        
        /*去掉checkbox全选样式*/
        $("#che_0").prop("checked", false);
		$("#che_1").prop("checked", false);
    };
    
    $scope.editUser=function(data){
    	$scope.$broadcast("editUser",data); 
    	if(pageNum==1){
			$scope.list = users.slice(0,10);
		}else if(pageNum==2){
			$scope.list = users.slice(10,user.length);
		}
    }
    
    $scope.$on("commitUser",function(e,flag){
    	if(flag==true){
    		if(pageNum==1){
				$scope.list = user.slice(0,10);
			}else if(pageNum==2){
				$scope.list = user.slice(10,user.length);
			}
    	}
	});
	
	$scope.delete=function(index){
		$scope.$broadcast("delete",index);
	}
	
	$scope.$on("deleteUser",function(e,index){
    	user = user.slice(0,index).concat(user.slice(index+1,user.length));
		$scope.list = user.slice(0,10);
		if(user.length <= 10){
			pageNav.go(1,1);
		}
		/*数据源*/
		users = users.slice(0,index).concat(users.slice(index+1,user.length));
	});   
    
}]);

App.controller('editCtrl', ['$scope',function($scope){
	$scope.$on("editUser",function(e,data){
		$scope.user=data;
	});
	
	$scope.commitUser=function(){
		$scope.$emit("commitUser",true)
	}
	
}]);

App.controller('deleteCtrl', ['$scope',function($scope){
	var deleteIndex="";
	$scope.$on("delete",function(e,index){
		deleteIndex=index;
	});
	
	$scope.deleteUser=function(){
		$scope.$emit("deleteUser",deleteIndex);
	}
	
}]);



