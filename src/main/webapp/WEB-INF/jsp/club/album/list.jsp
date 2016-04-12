<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
</head>
<body>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<%@ include file="/WEB-INF/jspf/navtop.jspf"%>
			<%@ include file="/WEB-INF/jspf/navside.jspf"%>
		</nav>

		<div id="page-wrapper" style="position: relative">
			<sec:authorize access="hasPermission(#club,'WRITE')">
				<form id="album-form" role="form" action="/albums?clubId=${club.id}">
					<input type="hidden" id="_csrf" name="_csrf" value="${_csrf.token}"></input>
					<input type="file" id="photo" class="item-write-btn btn btn-primary btn-lg"
							name="photo[]"  accept="*" multiple="" />
				</form>
			</sec:authorize>
			<div class="panel-body">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">사진첩</h1>
					</div>
					<div id="album-list">
					</div>
				</div>
				<!-- /.row -->
				<hr>
			  <!-- /.container -->
			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /#page-wrapper -->
	
	<!-- 좀 더 좋은 방법으로 변경가능한지 고민 필요. -->
	<!-- album list -->
	<div style="display: none" class="album-component">
		<div class="album">
			<div class="col-lg-3 col-md-4 col-xs-6 album thumb">
				<a id="contentPath" class="thumbnail" href="#"> <img
					id="albumImage" class="img-responsive"
					src="http://placehold.it/400x300" alt="">
				</a>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	<script type="text/javascript">
		$(function() {
			var uploadFile = function () {
				var form = $('#album-form');
				var action = form.attr('action');
				$.ajax({
			        url: action,
			        type: 'POST',
			        data: new FormData(form[0]),
			        enctype: 'multipart/form-data',
			        processData: false,
			        contentType: false,
			        cache: false,
			        success: function (data) {
			          // Handle upload success
			          console.log(data);
			        },
			        error: function(xhr, status, err) {
						if (console) {
							console.log(xhr);
							console.log(status);
							console.log(err);
						}
			        }
			      });
		    }; // function uploadFile
		    
		    // list
			//?page=0&size=20;
			var pageable = {
				page: 0,
				size: 12,
				sort: 'id,desc',
				clubId: '${clubId}'
			};
			// 내보낸 화면의 아이디들은 여기에 보관.
			//보관되어진 id에 해당하는 데이터는 화면에 뿌려지지 않도록 하기 위함.
			var idList = [];
			// ajax 요청을 받는동안 또다른 요청을 하지 않기 위함.
			var requesting = false;

			// 공통화 필요.
			var getAlbums = function(callback) {
				requesting = true;
				var url = '/albums';
				var $lodingImg = $('#loader');
				//TODO 변경 필요 (hard coding).
				$lodingImg.show().css({
					'top' : $(document).height() - 126 + 'px',
					'left' : '45%'
				});

				$.ajax({
					url : url,
					type : 'GET',
					cache : false,
					data: pageable,
					success : function(data) {
						if (data) {
							if(console) {
								console.log(data);
							}
							//if(data.numberOfElements === data.size) {
							if(!data.last) {
								pageable.page += 1;
							}

							var albums = data.content;
							var $albumList = $('#album-list');
							$.each(albums, function(index, album) {
								if(idList.indexOf(album.id) == -1){
									idList.push(album.id);
									var $albumComponent = callback(album);
									$albumList.append($albumComponent.html());									
								}
								$('#loader').hide();
							});
							requesting = false;
						}
					}.bind(this),
					error : function(xhr, status, err) {
						if (console) {
							console.log(xhr);
							console.log(status);
							console.log(err);
						}
					}.bind(this)
				});
			};

			
			var albumComponent = function(album) {
				//private Long id;
				//private String title;
				//private Menu parent;
				//private Member writer;
				//private Date createdDate;
				//private Date updatedDate;
				// images
				if(console) {
					console.log(album);
				}
				var $albumComponent = $('.album-component .album');
				var $writer = $albumComponent.find('#writer');
				var $updatedDate = $albumComponent.find('#updatedDate');
				var $albumImage = $albumComponent.find('#albumImage');
				var $profileImage = $albumComponent.find('#profileImage');

				var $contentPath = $albumComponent.find('#contentPath');

				var writer = album.writer;
				$writer.text('');
				$updatedDate.text('');
				$contentPath.removeAttr('href');

				$writer.text(writer.name);
				$updatedDate.text(album.updatedDate);

				if($contentPath.size()) {
					$contentPath.attr('href',
							'/pages/club/main/${club.id}/menu/0/album/'
									+ album.id);
				}
				if (album.photo != null
						&& album.photo.id != null) {
					$albumImage.show();
					$albumImage.attr('src', '/upload/images/'
							+ album.photo.id);
				} else {
					$albumImage.removeAttr('src');
					$albumImage.hide();
				}

				if (writer.profileImage != null
						&& writer.profileImage.id != null) {
					$profileImage.show();
					$profileImage.attr('src', '/upload/images/'
							+ writer.profileImage.id);
				} else {
					$profileImage.removeAttr('src');
					$profileImage.hide();
				}
				//$birthday.text(album.birthday);
				//$joinDate.text(album.joinDate);

				return $albumComponent;
			}
			getAlbums(albumComponent);
			$(window).scroll(
					function() {
						if ($(window).scrollTop() == $(document).height()
								- $(window).height()) {
							if (!requesting) {
								getAlbums(albumComponent);
							}
						}
					});

		 	$('#album-form #photo').on('change', uploadFile);
		});
	</script>
</body>
</html>
