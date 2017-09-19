<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		    var rows = ITEM_INTRODUCE.rows;
			function addRow(url){
				rows.push({"image":url});
				$('#introduceDataGrid').datagrid('loadData',rows);
			}
		ITEM_INTRODUCE.upRow = function(index){
			if(index<1)return;
			var temp = rows[index-1];
			rows[index-1] = rows[index];
			rows[index] = temp;
			$('#introduceDataGrid').datagrid('loadData',rows);
		}
		ITEM_INTRODUCE.downRow = function(index){
			if(index>=rows.length)return;
			var temp = rows[index+1];
			rows[index+1] = rows[index];
			rows[index] = temp;
			$('#introduceDataGrid').datagrid('loadData',rows);
		}
		ITEM_INTRODUCE.delRow = function(index){
			rows.splice(index,1);
			$('#introduceDataGrid').datagrid('loadData',rows);
		}

			$('#introduceDataGrid').datagrid({
				fit:true,
				fitColumns: true,
				border: false,
				data:rows,
				checkOnSelect: false,
				selectOnCheck: false,
				nowrap: false,
				striped: true,
				rownumbers: true,
				singleSelect: true,
				columns: [[{
					field: 'image',
					title: '图片',
					width: 100,
					formatter:function(value){

						return "<img src='"+value+"' width=100 height=80>";
					}
				}, {
					field: 'image1',
					title: '操作',
					formatter:function(value,row, index){

						return '<input type="button" value="上移" onclick="ITEM_INTRODUCE.upRow('+index+')">&nbsp;<input type="button" value="下移" onclick="ITEM_INTRODUCE.downRow('+index+')">&nbsp;<input type="button" value="删除" onclick="ITEM_INTRODUCE.delRow('+index+')">';
					},
					width: 30
				}]]
			});




		var host = 'http://post-test.oss-cn-hangzhou.aliyuncs.com';

		var policyBase64;
		var g_dirname = '';
		var g_object_name = '';
		var g_object_name_type = '';
		var now = timestamp = Date.parse(new Date()) / 1000;

		var signature ='';

		function check_object_radio() {
			var tt = document.getElementsByName('myradio');
			for (var i = 0; i < tt.length ; i++ )
			{
				if(tt[i].checked)
				{
					g_object_name_type = tt[i].value;
					break;
				}
			}
		}

		function get_dirname()
		{
			dir = document.getElementById("dirname").value;
			if (dir != '' && dir.indexOf('/') != dir.length - 1)
			{
				dir = dir + '/'
			}
			//alert(dir)
			g_dirname = dir
		}

		function random_string(len) {
			len = len || 32;
			var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
			var maxPos = chars.length;
			var pwd = '';
			for (i = 0; i < len; i++) {
				pwd += chars.charAt(Math.floor(Math.random() * maxPos));
			}
			return pwd;
		}

		function get_suffix(filename) {
			pos = filename.lastIndexOf('.')
			suffix = ''
			if (pos != -1) {
				suffix = filename.substring(pos)
			}
			return suffix;
		}

		function calculate_object_name(filename)
		{
			if (g_object_name_type == 'local_name')
			{
				g_object_name += "adfd"
			}
			else if (g_object_name_type == 'random_name')
			{
				suffix = get_suffix(filename)
				g_object_name = g_dirname + random_string(10) + suffix
			}
			return ''
		}

		function get_uploaded_object_name(filename)
		{
			if (g_object_name_type == 'local_name')
			{
				tmp_name = g_object_name
				tmp_name = tmp_name.replace("${filename}", filename);
				return tmp_name
			}
			else if(g_object_name_type == 'random_name')
			{
				return g_object_name
			}
		}

		function set_upload_param(up, filename, ret)
		{
			g_object_name = g_dirname;
			if (filename != '') {
				suffix = get_suffix(filename)
				calculate_object_name(filename)
			}
			new_multipart_params = {
				'key' : g_object_name,
				'policy': policyBase64,
				'OSSAccessKeyId': accessid,
				'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
				'signature': signature,
			};

			up.setOption({
				'url': host,
				'multipart_params': new_multipart_params
			});

			up.start();
		}

		var uploader = new plupload.Uploader({
			runtimes : 'html5,flash,silverlight,html4',
			browse_button : 'selectfiles',
			//multi_selection: false,
			container: document.getElementById('container'),
			flash_swf_url : 'lib/plupload-2.1.2/js/Moxie.swf',
			silverlight_xap_url : 'lib/plupload-2.1.2/js/Moxie.xap',
			url : 'http://oss.aliyuncs.com',

			init: {
				PostInit: function() {
					document.getElementById('ossfile').innerHTML = '';
					document.getElementById('postfiles').onclick = function() {
						$.post('${pageContext.request.contextPath}/api/apiBaseDataController/uploadSign', {

						}, function(result) {
							parent.$.messager.progress('close');
							if(result.success){
								var cfg = result.obj;
								//console.log(cfg);
								accessid = cfg.accessid;
								$("#dirname").val(cfg.dir);
								signature = cfg.signature;
								policyBase64 = cfg.policy;
								host= cfg.host;
								set_upload_param(uploader, '', false);
							}
						}, 'JSON');

						return false;
					};
				},

				FilesAdded: function(up, files) {
					plupload.each(files, function(file) {
						document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
								+'<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
								+'</div>';
					});
				},

				BeforeUpload: function(up, file) {
					check_object_radio();
					get_dirname();
					set_upload_param(up, file.name, true);
				},

				UploadProgress: function(up, file) {
					var d = document.getElementById(file.id);
					d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
					var prog = d.getElementsByTagName('div')[0];
					var progBar = prog.getElementsByTagName('div')[0]
					progBar.style.width= 2*file.percent+'px';
					progBar.setAttribute('aria-valuenow', file.percent);
				},

				FileUploaded: function(up, file, info) {
					if (info.status == 200){
						console.log(get_uploaded_object_name(file.name))
						addRow(host+get_uploaded_object_name(file.name));
						document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = 'upload to oss success, object name:' + get_uploaded_object_name(file.name);
					}
					else {
						document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
					}
				},

				Error: function(up, err) {
					document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
				}
			}
		});

		uploader.init();
	});
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',border:false" style="height: 350px;">
		<table id="introduceDataGrid"></table>
	</div>
	<div data-options="region:'center',border:false">
		<div id="container">
			<a id="selectfiles" href="javascript:void(0);" class='btn'>选择文件</a>
			<a id="postfiles" href="javascript:void(0);" class='btn'>开始上传</a>
		</div>
		<div >
			<form name=theform style="display: none">
				<input type="radio" name="myradio" value="local_name" /> 上传文件名字保持本地文件名字
				<input type="radio" name="myradio" value="random_name" checked=true/> 上传文件名字是随机文件名
				<br/>
				上传到指定目录:<input type="text" id='dirname' placeholder="如果不填，默认是上传到根目录" size=50>
			</form>

			<h4>您所选择的文件列表：</h4>
			<div id="ossfile">你的浏览器不支持flash,Silverlight或者HTML5！</div>

			<br/>
			<pre id="console"></pre>
		</div>
	</div>
</div>


