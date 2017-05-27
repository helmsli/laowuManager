var nodeInfo = {};
/*定义module*/
App.controller('app', ['$scope', '$ocLazyLoad', function($scope, $ocLazyLoad) {

	$scope.loadBootstrap = function() {
		var myFileList = [];
		myFileList = loadFileList(myFileList);
		//console.log("要加载的文件:"+myFileList);
		$ocLazyLoad.load(myFileList);
		pageNav.go(1, 20);
	}

	$scope.loadBootstrap();

	$scope.initTree = function() {
			menu.leftMenu("config", 'tree', 'tree', function(event, treeId, treeNode) {
				console.log(JSON.stringify(treeNode));
			});
		}
		//定义变量ID
	$scope.currentId = "";
	$scope.titleList = [
		"BTS ID",
		"网元名称",
		"网元类型",
		"操作"
	];

	var userList = [{
		"btsId": "85122852",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122853",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122854",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122855",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122856",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122857",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122858",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122859",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122860",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122861",
		"btsName": "张三峰",
		"btsType": "单板音"
	}]
	var user = [{
		"btsId": "85122852",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122853",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122854",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122855",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122856",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122857",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122858",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122859",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122860",
		"btsName": "张三峰",
		"btsType": "单板音"
	}, {
		"btsId": "85122861",
		"btsName": "张三峰",
		"btsType": "单板音"
	}]
	$scope.list = user;

	$scope.changeRow = function(index) {
		var myData = userList[index];
		/*delete myData["$$hashKey"];
		myData.index=index;
	    var newScope=getAngularScope("addUser");
	  	 newScope.user=myData;*/
		$('#creatModal').modal('show');
		$scope.$broadcast("changeRow", myData);
	}

	$scope.$on("editUser", function(e, userNew) {
		console.log("edit");
		console.log(userNew);
		$scope.list = userList;
		console.log(userList);
	})

	$("#che_0").removeAttr("checked");
	$("#che_1").removeAttr("checked");

	/*全选样式变换*/
	$scope.checkAll = function() {
		var checkbox = $("#box input[type='checkbox']")
		if(checkbox.prop("checked") == true) {
			checkbox.prop("checked", false);
			$("#che_0").prop("checked", false);
			$("#che_1").prop("checked", false);
		} else {
			checkbox.prop("checked", true);
			$("#che_0").prop("checked", true);
			$("#che_1").prop("checked", true);
		}
	}

}]);

App.controller('addUser', function($scope) {

	$scope.user = {
		"btsId": "",
		"btsName": "",
		"btsType": ""
	};

	$scope.$on("changeRow", function(e, user) {
		$scope.user = user;
		console.log("get");
		console.log(user);
	});

	$scope.editUser = function() {
		$scope.$emit("editUser", $scope.user);
		$('#creatModal').modal('hide');

	}
});