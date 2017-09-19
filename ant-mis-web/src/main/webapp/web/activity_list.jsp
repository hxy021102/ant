<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<div class="isotope-container row grid-space-20">
		<c:forEach items="${activitys}" var="activity" varStatus="vs">
			<div class="col-sm-6 col-md-3 isotope-item web-design">
				<div class="image-box">
					<div class="overlay-container">
						<img src="${pageContext.request.contextPath}/${activity.icon}" alt="" width="263" height="175" style="height: 175px;">
						<a class="overlay viewDetail" activetyId="${activity.id}" data-toggle="modal" data-target="#project-${vs.index+1}">
							<i class="fa fa-search-plus"></i>
							<span style="text-align: inherit;">
								周期：<fmt:formatDate value="${activity.startDate}" pattern="yyyy-MM-dd"/> to <fmt:formatDate value="${activity.endDate}" pattern="yyyy-MM-dd"/><br>
								出发地：${activity.startAddr}<br>
								目的地：${activity.endAddr}<br>
								状态：<c:choose>
					    			<c:when test="${activity.status == 'AT01'}">
					    				报名中
					    			</c:when>
					    			<c:when test="${activity.status == 'AT02'}">
					    				进行中
					    			</c:when>
					    			<c:when test="${activity.status == 'AT03'}">
					    				已结束
					    			</c:when>
					    		</c:choose>
							</span>
						</a>
					</div>
					<a class="btn btn-default btn-block viewDetail" activetyId="${activity.id}" data-toggle="modal" data-target="#project-${vs.index+1}">${activity.name}</a>
				</div>
				<!-- Modal -->
				<div class="modal fade" id="project-${vs.index+1}" tabindex="-1" role="dialog" aria-labelledby="project-${vs.index+1}-label" aria-hidden="true">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
								<h4 class="modal-title" id="project-${vs.index+1}-label">${activity.name}</h4>
							</div>
							<div class="modal-body">
								<h3><fmt:formatDate value="${activity.startDate}" pattern="yyyy-MM-dd"/>至<fmt:formatDate value="${activity.endDate}" pattern="yyyy-MM-dd"/> 从${activity.startAddr}出发,目的地:${activity.endAddr}</h3>
								<div class="row">
									<div class="col-md-6">
										<!-- <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Atque sed, quidem quis praesentium, ut unde. Quae sed, incidunt laudantium nesciunt, optio corporis quod earum pariatur omnis illo saepe numquam suscipit, nemo placeat dignissimos eius mollitia et quas officia doloremque ipsum labore rem deserunt vero! Magnam totam delectus accusantium voluptas et, tempora quos atque, fugiat, obcaecati voluptatibus commodi illo voluptates dolore nemo quo soluta quis.</p>
										<p>Molestiae sed enim laboriosam atque delectus voluptates rerum nostrum sapiente obcaecati molestias quasi optio exercitationem, voluptate quis consequatur libero incidunt, in, quod. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eos nobis officiis, autem earum tenetur quidem. Quae non dicta earum. Ipsum autem eaque cum dolor placeat corporis quisquam dolorum at nesciunt.</p>
										 -->
										 <iframe id="frame_${activity.id}" width="100%" height="360" border="0" frameborder="0" src="">
										 </iframe>
									</div>
									<div class="col-md-6">
										<img src="${pageContext.request.contextPath}/${activity.icon}" alt="">
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>
				</div>
				<!-- Modal end -->
			</div>
		</c:forEach>
	</div>
    <!-- Jquery and Bootstap core js files -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/web/plugins/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/web/bootstrap/js/bootstrap.min.js"></script>

		<!-- Modernizr javascript -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/web/plugins/modernizr.js"></script>

		<!-- Isotope javascript -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/web/plugins/isotope/isotope.pkgd.min.js"></script>
		
		<!-- Backstretch javascript -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/web/plugins/jquery.backstretch.min.js"></script>

		<!-- Appear javascript -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/web/plugins/jquery.appear.js"></script>

		<!-- Initialization of Plugins -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/web/js/template.js"></script>

		<!-- Custom Scripts -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/web/js/custom.js"></script>
		<script type="text/javascript">
			$(function(){
				$(".viewDetail").click(function(){
					var activetyId = $(this).attr("activetyId");
					$("#frame_" + activetyId).attr('src', '${pageContext.request.contextPath}/api/apiCommon/html?type=BT04&id='+activetyId);
				});
			});
		</script>
	
</body>
</html>