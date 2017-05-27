    /**
	 * 上传文件显示测试示例
	 * ***/
	function uploadImage(fileId,showId)
	{
		 var fileObj=document.getElementById(fileId);
	     // 注意这里
	     // fileObj.files[0];
	     var fileName=fileObj.files[0].name;
	     var fileType=fileObj.files[0].type;
	     var src = window.URL.createObjectURL(fileObj.files[0]);
	     var showBox=document.getElementById(showId);
	     showBox.innerHTML="";
		 var img = document.createElement('img');
	    img.src = src;
	    img.className = "img-thumbnail";
	    showBox.appendChild(img);
	    //显示删除按钮
	    var imgDeleteBtn = document.getElementById(fileId+"_delete");
	    imgDeleteBtn.setAttribute("class","ds-block img-deleted show ");
	    //上传
	    ajaxFileUpload(fileId);
	}
	/***
	 * 删除图片
	 */
	function deleteImg(fileId,showId){
		//<i class="fa fa-plus"></i>
		//清除input file框中的值
		var fileObj=document.getElementById(fileId);
		fileObj.value = '';
		//剔除img标签
		var showObj = document.getElementById(showId);
	   	showObj.innerHTML = '<i class="fa fa-plus"></i>';
	    	//隐藏删除按钮
	   	 var imgDeleteBtn = document.getElementById(fileId+"_delete");
	    imgDeleteBtn.setAttribute("class","ds-block img-deleted hide ");
	}
	/**jquery文件上传*/
	function ajaxFileUpload(fileId){
		$.ajaxFileUpload({
			url:'/imageUpload.do?timeCode='+new Date().getTime(), 
			fileElementId:fileId,
			data:{},
			dataType: 'json',
			success: function (data, Status)
			{
				swal('图片上传成功');
			},
			error: function (data, status, e)
			{
				swal('图片上传失败'+status);
				
			}
		});
	}