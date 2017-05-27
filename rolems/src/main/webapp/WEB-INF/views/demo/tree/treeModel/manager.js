$(function(){
	//默认第一个角色被选中
	$("a.curSelectedNode").click();
});
var treeDatas = {};
/**
 * 显示用户信息
 */
treeDatas.treeId = "";
treeDatas.showDataList = function(treeId){
	treeDatas.treeId  = treeId;
	return treeTabledata[treeId].list||[];
};


