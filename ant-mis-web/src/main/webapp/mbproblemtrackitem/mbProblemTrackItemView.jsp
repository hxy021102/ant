<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbProblemTrackItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');		

    function ProcessFile() {
        var file = document.getElementById('iconFile').files[0];
        if (file) {
            var reader = new FileReader();
            reader.onload = function ( event ) {
                var txt = event.target.result;
                $('.img-preview').attr('src',txt);
            };
        }
        reader.readAsDataURL(file);
    }
    $(document).delegate('#iconFile','change',function () {
        ProcessFile();
    });
    $('.img-preview').each(function(){
        var $this = $(this);
        $this.css('height',$this.parent().attr('height'));
    });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
				<tr>
					<th><%=TmbProblemTrackItem.ALIAS_OWNER_ID%>:</th>
					<td>
						${mbProblemTrackItem.ownerId}
					</td>
					<th><%=TmbProblemTrackItem.ALIAS_LAST_OWNER_ID%>:</th>
					<td>
						${mbProblemTrackItem.lastOwnerId}
					</td>
				</tr>
			<tr>
				<th colspan="3">图片</th>
				<td >
					<input type="file" id="iconFile" name="fileName">
				</td>
			</tr>
			<tr>
				<td colspan="3" height="50">
					<img class="img-preview" src="${mbProblemTrackItem.file}"/>
				</td>
			</tr>
			    <tr>
				<th><%=TmbProblemTrackItem.ALIAS_CONTENT%>:</th>
					<td colspan="3">
						${mbProblemTrackItem.content}
					</td>
			    </tr>
		</table>
	</div>
</div>